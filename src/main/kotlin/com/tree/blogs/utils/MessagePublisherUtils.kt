package com.tree.blogs.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.vavr.Tuple3
import io.vavr.control.Try
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class MessagePublisherUtils(private val rabbitTemplate: RabbitTemplate,
    private val kotlinObjectMapper: ObjectMapper= jacksonObjectMapper()
) {

    fun publishMessageToExchange(routingKey:String,exchangeName:String,message:Message):
            Try<Tuple3<String,String,Message>> = Try.of{
        rabbitTemplate.convertAndSend(exchangeName,routingKey,message);
         Tuple3(routingKey,exchangeName,message)

    }

    fun <T> publishToExchange(routingKey: String,exchangeName: String,data:T):Try<T> = Try.of{
        var message:Message = if(data !is Message)
            Message(kotlinObjectMapper.writeValueAsBytes(data)) else data
        rabbitTemplate.convertAndSend(exchangeName,routingKey,message);
        return@of data
    }



}