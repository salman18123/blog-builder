package com.tree.blogs.configuration

import org.springframework.amqp.core.AbstractExchange

class ConsistentHashExchange(name:String): AbstractExchange(name) {


    override fun getType(): String {
        return "x-consistent-hash";
    }
}