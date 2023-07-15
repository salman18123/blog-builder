package com.tree.blogs.models


import lombok.Builder
import lombok.Data
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.time.Instant
import java.util.*


@Builder
@Data
@Document("blog")
class Blog (

):Serializable{
    @Id
    lateinit var id:String;
    var paragraphs:MutableList<Block> = mutableListOf();
    lateinit var createdOn: Date;
    lateinit var updatedOn:Date;
    var createdBy:String? = null
    var lastRevisionConsumed:Int? = null

}