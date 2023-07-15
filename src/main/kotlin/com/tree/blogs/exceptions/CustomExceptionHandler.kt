package com.tree.blogs.exceptions


import com.tree.blogs.utils.ResponseWrapper
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
@RestController
@Order(Ordered.HIGHEST_PRECEDENCE)
class CustomExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(DuplicateKeyException::class,)
    fun handleDuplicateKeyException():ResponseEntity<ResponseWrapper<String>> {
        val responseWrapper =
            ResponseWrapper("", Arrays.asList(ResponseWrapper.ResponseError(1001, "ConcurrentException")));
         return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(responseWrapper);

    }
}