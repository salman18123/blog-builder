package com.tree.blogs.processors

import com.tree.blogs.models.Block
import com.tree.blogs.request.Delta

interface DeltaProcessor {

    fun supportingType():Int

    fun processDelta(delta: Delta,paragraphs:MutableList<Block>):MutableList<Block>
}