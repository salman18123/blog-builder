package com.tree.blogs.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.tree.blogs.baseinterfaces.RO
import com.tree.blogs.models.Block
import com.tree.blogs.models.BlockType
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "blockType",
    defaultImpl = TextBlockRO::class,
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(value = TextBlockRO::class, name = "TEXT_PARAGRAPH")
)
@Data
@AllArgsConstructor
@NoArgsConstructor
abstract class BlockRO( val name:String, val blockType: BlockType):Serializable,RO<Block> {
}