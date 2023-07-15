package com.tree.blogs.baseinterfaces

import java.io.Serializable

interface BO<out RO>:Serializable {

    fun toRO():RO
}