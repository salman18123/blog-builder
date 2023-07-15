package com.tree.blogs.models

import com.tree.blogs.baseinterfaces.BO
import com.tree.blogs.request.ImageBlockRO

class ImageBlock(name:String):Block(name,BlockType.IMAGE_PARAGRAPH) {

    override fun toRO(): ImageBlockRO {
        TODO("Not yet implemented")
    }
}