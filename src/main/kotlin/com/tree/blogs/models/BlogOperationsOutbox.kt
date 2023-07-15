package com.tree.blogs.models


import com.tree.blogs.request.Delta
import lombok.Builder
import lombok.Data
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document



@Document("blog_operations_outbox")
@Builder
@Data
class BlogOperationsOutbox(
    var operation:List<Delta>,
    var blogId:String,
    var revision:Int,
    var status: BlogOperationStatus=BlogOperationStatus.QUEUED
) {
    @Id
    lateinit var id:String;
}