package com.tree.blogs.dao

import com.tree.blogs.models.BlogsRevisionInfo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface BlogRevisionInfoDao : MongoRepository<BlogsRevisionInfo, String>
