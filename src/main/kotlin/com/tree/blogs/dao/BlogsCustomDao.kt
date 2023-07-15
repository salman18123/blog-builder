package com.tree.blogs.dao

import com.tree.blogs.models.Block
import com.tree.blogs.models.Blog
import com.tree.blogs.utils.MongoHelper
import io.vavr.collection.HashMap
import io.vavr.control.Try
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date


@Service
class BlogsCustomDao(
    private val mongoTemplate: MongoTemplate,
    private val mongoHelper: MongoHelper
) {
    companion object {
        private val log = LoggerFactory.getLogger(BlogsCustomDao::class.java)
    }

    fun fetchParagraphsOfBlog(blogId:String,currentRevision:Int):Try<Blog> = Try.of {
        val blogObjectId= ObjectId(blogId)
       val criteria= Criteria.where("_id").`is`(blogObjectId)
          .and("lastRevisionConsumed")
         .`is`(currentRevision-1)
       val query= Query.query(criteria)
                 query.fields().include("paragraphs")
                    query.fields().include("lastRevisionConsumed")

        val blog = mongoTemplate.find(query,Blog::class.java)
        return@of blog[0]
    }

    fun updateParagraphsAndRevisionOfBlog(
        blogId: String, paragraphs:MutableList<Block>, revision:Int):Try<Unit> = Try.of {
        val blogObjectId= ObjectId(blogId)
        val criteria = Criteria.where("_id").`is`(blogObjectId)
        val updateMap =HashMap.of(
            "paragraphs",paragraphs,
            "lastRevisionConsumed",revision,
            "updatedOn",Date.from(Instant.now())
        )
        mongoHelper.updateCollection(criteria,updateMap,Blog::class.java).get()
    }
}