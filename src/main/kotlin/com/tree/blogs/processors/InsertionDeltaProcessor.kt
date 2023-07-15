package com.tree.blogs.processors

import com.tree.blogs.models.Block
import com.tree.blogs.request.Delta
import org.springframework.stereotype.Service


@Service
class InsertionDeltaProcessor:DeltaProcessor {

    override fun supportingType(): Int {
        return 1;
    }

    override fun processDelta(delta: Delta, paragraphs: MutableList<Block>): MutableList<Block> {
        delta.paragraph?.let { paragraphs.add(delta.index, it.toBO()) }
        return paragraphs
    }
}