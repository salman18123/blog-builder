package com.tree.blogs.configuration


import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.util.*

@Configuration
class RMQConfig (
    @Value("\${sync.queue.count:5}")
    private val syncQueueCount:Int,
    @Value("\${sync.queue.routing.key.exchange.points:20}")
    private val syncQueuesRoutingKeyExchangePoints:String



) {
  companion object {
      @JvmStatic
      val syncBlogOperationsExchange: String = "BLOG_OPERATIONS_SYNC_EXCHANGE"

  }

    @Bean
    fun rabbitAdmin(rabbitTemplate: RabbitTemplate):RabbitAdmin {
        return RabbitAdmin(rabbitTemplate)
    }


    @Bean
    @Qualifier("blogOperationsSyncQueues")
    fun blogOperationsSyncQueue(rabbitAdmin: RabbitAdmin,
                                @Qualifier("blogOperationsSyncExchange") exchange:ConsistentHashExchange): List<Queue> {

    val syncQueuePrefix:String="BLOG_OPERATIONS_SYNC_QUEUE";
    var i:Int=0;
    val syncQueueList= mutableListOf<Queue>();
        while(i<syncQueueCount) {

        val queue = QueueBuilder.durable(syncQueuePrefix + "_" + i).build()
            rabbitAdmin.declareQueue(queue)
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(syncQueuesRoutingKeyExchangePoints).noargs())
        syncQueueList.add(queue);
        i++;
    }
        return syncQueueList;
    }

    @Bean
    fun syncQueueNamesList(@Qualifier("blogOperationsSyncQueues") queueList:List<Queue>):Array<String> {
        return queueList.map { it.name }.toTypedArray();
    }

    @Bean
    @Qualifier("blogOperationsSyncExchange")
    fun blogOperationsSyncExchange():ConsistentHashExchange {
        return ConsistentHashExchange(RMQConfig.syncBlogOperationsExchange);
    }







}