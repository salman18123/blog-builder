package com.tree.blogs.Listeners





import com.fasterxml.jackson.databind.ObjectMapper
import com.tree.blogs.models.BlogOperationsOutbox
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.tree.blogs.dao.BlogsCustomDao
import com.tree.blogs.processors.DeltaProcessorsFactory

import io.vavr.control.Try
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Component

@Component
class BlogOperationsSyncWorker(
    private val syncQueueNamesList:Array<String>,
    private val kotlinObjectMapper: ObjectMapper = jacksonObjectMapper(),
    private val blogsCustomDao: BlogsCustomDao,
    private val deltaProcessorsFactory: DeltaProcessorsFactory

) {
  companion object {
      private val log = LoggerFactory.getLogger(BlogOperationsSyncWorker::class.java)
  }

    @RabbitListener(queues = ["#{syncQueueNamesList}"])
    fun processBlogOperations(message: Message) {
    log.info("inside listener");
        Try.of {
            val blogOperations:BlogOperationsOutbox  =kotlinObjectMapper.readValue(message.body)
            val blogParagraphs= blogsCustomDao.fetchParagraphsOfBlog(blogOperations.blogId,
                blogOperations.revision).get()
            blogOperations.operation.stream().forEach { deltaProcessorsFactory
                .getProcessorFromType(it.type)?.processDelta(it,blogParagraphs.paragraphs) }
            blogsCustomDao.updateParagraphsAndRevisionOfBlog(
                blogId = blogOperations.blogId,
                paragraphs = blogParagraphs.paragraphs,
                revision = blogOperations.revision).get()
        }

    }
}