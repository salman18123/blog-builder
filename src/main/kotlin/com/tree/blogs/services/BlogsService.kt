package com.tree.blogs.services

import com.tree.blogs.dao.BlogOperationsOutboxDao
import com.tree.blogs.dao.BlogRevisionInfoDao
import com.tree.blogs.dao.BlogsDao
import com.tree.blogs.models.Blog
import com.tree.blogs.models.BlogOperationsOutbox
import com.tree.blogs.models.BlogsRevisionInfo

import com.tree.blogs.request.Delta
import io.vavr.control.Try
import lombok.extern.log4j.Log4j2
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class BlogsService(private val blogsDao: BlogsDao,
                   private val blogOperationsOutboxDao: BlogOperationsOutboxDao,
                   private val blogRevisionInfoDao: BlogRevisionInfoDao
) {


    fun initialiseBlogWithId():Blog {
        var blog:Blog = Blog();
        blog.lastRevisionConsumed=0;
        blog.createdOn= Date.from(Instant.now())
        blog.updatedOn= Date.from(Instant.now())
        return blogsDao.save(blog);

    }

    @Transactional
    fun processBlogOperation(blogId: String,operations:List<Delta>,revision:Int) {
        //TODO add validations to check if it is a valid block id maybe cache blogid in redis
        val dbOperations= Try.of {
            blogRevisionInfoDao.save(BlogsRevisionInfo(blogId,revision));
            blogOperationsOutboxDao.save(BlogOperationsOutbox(operations, blogId, revision))
        }
        if(dbOperations.isFailure) {
            throw dbOperations.cause;
        }



    }

}