package com.tree.blogs.request

import com.tree.blogs.baseinterfaces.RO
import com.tree.blogs.models.BlockType
import com.tree.blogs.models.ImageBlock

class ImageBlockRO(name:String): BlockRO(name,BlockType.IMAGE_PARAGRAPH) {

    override fun toBO(): ImageBlock {
        TODO("Not yet implemented")
    }
}