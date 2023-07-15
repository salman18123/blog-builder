package com.tree.blogs.scheduler


import com.tree.blogs.configuration.RMQConfig
import com.tree.blogs.dao.BlogOperationsOutboxDao
import com.tree.blogs.models.BlogOperationStatus
import com.tree.blogs.models.BlogOperationsOutbox
import com.tree.blogs.utils.MessagePublisherUtils
import com.tree.blogs.utils.MongoHelper
import io.vavr.collection.HashMap
import io.vavr.collection.List
import io.vavr.control.Try
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Supplier


@Profile("SCHEDULER","TEST")
@Component
class BlogOperationsSynScheduler(
    private var blogOperationsOutboxDao: BlogOperationsOutboxDao,
    private val mongoTemplate: MongoTemplate,
    private var isRunning:AtomicBoolean= AtomicBoolean(false),
    private var messagePublisherUtils: MessagePublisherUtils,
    private val mongoHelper: MongoHelper
) {
    companion object {
        private val log = LoggerFactory.getLogger(BlogOperationsSynScheduler::class.java)
    }


    @Scheduled(cron = "\${sync.scheduler.interval:*/30 * * * * *}")
     fun triggerSyncRun() {
    log.info("inside scheduler.....................................................")
        startConditionalRun {
            processOperations()
        }.onFailure { log.error("run failed") }
            .onSuccess { log.info("successfully published messages") }
     }

    private fun processOperations():Try<Unit> = Try.of {
        val criteria = Criteria.where("status").`is`(BlogOperationStatus.QUEUED)
        val query: Query = Query.query(criteria).limit(100)
        val publishedMessagesList = mutableListOf<String>()
        Try.of {
            val queuedStatusBlogOperations = mongoTemplate.find(query, BlogOperationsOutbox::class.java)
            List.ofAll(queuedStatusBlogOperations)
                .forEach {
                     messagePublisherUtils.publishToExchange(
                        routingKey = it.blogId,
                        exchangeName = RMQConfig.syncBlogOperationsExchange,
                        data = it
                    )
                        .onFailure { log.error("failure in publishing to queue", it.message) }
                        .onSuccess{publishedMessagesList.add(it.id)}
                }
        }.onFailure { log.error("failed to fetch operations from DB") }
        if(publishedMessagesList.size==0) return@of
        Try.of {
            val updateCriteria = Criteria.where("id").`in`(publishedMessagesList)
            val updateFields= HashMap.of("status",BlogOperationStatus.PUBLISHED)
            mongoHelper.updateCollection(updateCriteria,updateFields,BlogOperationsOutbox::class.java).get()
        }.onFailure { log.error("failed to update the status to published for operations {}",publishedMessagesList) }
        return@of
    }


    private fun startConditionalRun(supplier: Supplier<Try<Unit>>):Try<Unit> = Try.of {
        if(isRunning.get()) return@of
        isRunning.set(true);
        supplier.get().get()
        isRunning.set(false);

    }
}