package com.tree.blogs.dao

import com.tree.blogs.models.BlogOperationsOutbox
import org.springframework.data.mongodb.repository.MongoRepository

import org.springframework.stereotype.Repository

@Repository
interface BlogOperationsOutboxDao:MongoRepository<BlogOperationsOutbox,String> {

}