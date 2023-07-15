package com.tree.blogs.request

import com.tree.blogs.baseinterfaces.RO
import com.tree.blogs.models.BlockType
import com.tree.blogs.models.TextBlock
import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter


class TextBlockRO(name:String,blockType: BlockType,val text:String): BlockRO(name,blockType) {

    override fun toBO(): TextBlock {
        return TextBlock(name,text)
    }
}