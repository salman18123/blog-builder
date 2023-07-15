package com.tree.blogs.dao

import com.tree.blogs.models.Blog
import org.springframework.data.mongodb.repository.MongoRepository

import org.springframework.stereotype.Repository

@Repository
interface BlogsDao:MongoRepository<Blog,String> {
}