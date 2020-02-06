package com.joshfermin.anagram.security

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class InvalidWordHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(InvalidWordException::class)
    fun handleInvalidWord(exception: InvalidWordException): ResponseEntity<Any> {
        val headers = HttpHeaders().also {
            it.contentType = MediaType.APPLICATION_JSON
        }
        val body = mapOf("error" to "${exception.message}")
        return ResponseEntity(body, headers, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}

class InvalidWordException(message: String) : RuntimeException(message)
