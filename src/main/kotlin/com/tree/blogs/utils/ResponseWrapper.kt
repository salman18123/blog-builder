package com.tree.blogs.utils

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import java.util.*

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class ResponseWrapper<T>( var data:T, var responseErrors:List<ResponseError>):Serializable {

    constructor(data: T) : this(data, Arrays.asList())


    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    class ResponseError(val code:Long,
         val message:String) {


    }
}