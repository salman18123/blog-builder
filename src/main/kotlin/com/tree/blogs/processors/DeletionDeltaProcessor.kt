package com.tree.blogs.processors

import com.tree.blogs.models.Block
import com.tree.blogs.request.Delta
import org.springframework.stereotype.Service


@Service
class DeletionDeltaProcessor:DeltaProcessor {
    override fun supportingType(): Int {
        return 2
    }

    override fun processDelta(delta: Delta, paragraphs: MutableList<Block>): MutableList<Block> {
        paragraphs.removeAt(delta.index)
        return paragraphs
    }
}