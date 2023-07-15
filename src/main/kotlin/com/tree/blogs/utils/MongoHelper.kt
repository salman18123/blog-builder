package com.tree.blogs.utils

import io.vavr.collection.Map
import io.vavr.control.Try
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class MongoHelper(private val mongoTemplate: MongoTemplate) {

    fun updateCollection(criteria: Criteria,
                         updateValues:Map<String,*>,
                         clazz: Class<*>): Try<Unit> = Try.of {
        val updateQuery = Query.query(criteria)
        val update = Update()
        updateValues.forEach { k, v -> update.set(k,v) }
        mongoTemplate.updateFirst(updateQuery,update,clazz)
        return@of
        }
}