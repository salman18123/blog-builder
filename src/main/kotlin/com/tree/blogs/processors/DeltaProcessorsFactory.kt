package com.tree.blogs.processors

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service


@Service
class DeltaProcessorsFactory(
    private val deltaProcessors: List<DeltaProcessor>,
    private var typeToProcessorMap:MutableMap<Int,DeltaProcessor>
) {

    @PostConstruct
    fun init() {
       deltaProcessors.stream()
           .forEach { typeToProcessorMap.put(it.supportingType(),it) }
    }
    fun getProcessorFromType(type:Int):DeltaProcessor? {
        return typeToProcessorMap[type]
    }
}