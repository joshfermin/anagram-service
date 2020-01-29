package com.joshfermin.anagram.api

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class AnagramController(
    private val anagramService: AnagramService
) {
    @PostMapping("/words.json")
    fun uploadWords(@RequestBody body: AnagramUploadRequest) {
        anagramService.upload(body.words)
    }
    
}
