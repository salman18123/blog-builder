package com.tree.blogs.models


import com.tree.blogs.request.TextBlockRO



class TextBlock(name:String,var text:String):Block(name,BlockType.TEXT_PARAGRAPH) {

    override fun toRO(): TextBlockRO {
        TODO("Not yet implemented")
    }
}