package com.tree.blogs.models


import com.tree.blogs.baseinterfaces.BO
import com.tree.blogs.request.BlockRO
import java.io.Serializable



abstract class Block(
    protected var name:String,
    protected var blockType: BlockType
):Serializable, BO<BlockRO> {
}