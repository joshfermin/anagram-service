package com.joshfermin.anagram.core

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class WordAlreadyExistsHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(WordAlreadyExistsException::class)
    fun handleWordAlreadyExists(exception: WordAlreadyExistsException): ResponseEntity<Any> {
        val headers = HttpHeaders().also {
            it.contentType = MediaType.APPLICATION_JSON
        }
        val body = mapOf("error" to "The following words: '${exception.message}' already exists in the data store")
        return ResponseEntity(body, headers, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}

class WordAlreadyExistsException(message: String) : RuntimeException(message)
