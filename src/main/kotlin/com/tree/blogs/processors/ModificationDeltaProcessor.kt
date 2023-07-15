package com.tree.blogs.processors

import com.tree.blogs.models.Block
import com.tree.blogs.request.Delta
import org.springframework.stereotype.Service


@Service
class ModificationDeltaProcessor:DeltaProcessor {

    override fun supportingType(): Int {
        return 3;
    }

    override fun processDelta(delta: Delta, paragraphs: MutableList<Block>): MutableList<Block> {
        paragraphs[delta.index] = delta.paragraph?.toBO()!!
        return paragraphs

    }
}