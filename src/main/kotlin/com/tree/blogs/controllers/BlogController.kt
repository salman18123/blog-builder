package com.tree.blogs.controllers

import com.tree.blogs.models.Blog
import com.tree.blogs.request.Delta
import com.tree.blogs.services.BlogsService
import com.tree.blogs.utils.ResponseWrapper
import lombok.extern.log4j.Log4j2
import lombok.extern.slf4j.Slf4j
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/blogs")
@Profile("API","TEST")
class BlogController(
    private val blogsService: BlogsService
) {

    @PostMapping("/new-story",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun initialiseBlogWithId():ResponseEntity<ResponseWrapper<Blog>> {
        val blog=blogsService.initialiseBlogWithId();
        val responseEntity = ResponseWrapper(blog);

        return ResponseEntity.ok(responseEntity);
    }

    @PostMapping("/{blogId}/delta",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
        )
    fun updateBlog(@PathVariable blogId:String,
                  @RequestBody operation:List<Delta>,
                   @RequestParam revision:Int):ResponseEntity<ResponseWrapper<Int>> {
        blogsService.processBlogOperation(blogId,operation,revision)
        return ResponseEntity.ok(ResponseWrapper(revision+1));
    }





}