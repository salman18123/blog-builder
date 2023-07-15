package com.tree.blogs.baseinterfaces

import java.io.Serializable

interface RO< out BO>:Serializable {

    fun toBO():BO;
}