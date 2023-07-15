package com.tree.blogs.models



import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document



@Document("blog_revision_info")
@CompoundIndexes(CompoundIndex(name = "aid_bid_idx", def = "{'blogId' : 1, 'revision' : 1}", unique = true, background = true))
open class BlogsRevisionInfo(
     var blogId: String,
     var revision: Int
) {

    @Id
    lateinit var id: String
}