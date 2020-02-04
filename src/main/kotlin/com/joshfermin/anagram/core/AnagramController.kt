package com.joshfermin.anagram.core

import com.joshfermin.anagram.models.AnagramResponse
import com.joshfermin.anagram.models.AnagramUploadRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
class AnagramController(
    private val anagramService: AnagramService
) {
    @PostMapping("/words.json")
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadWords(@RequestBody body: AnagramUploadRequest) {
        anagramService.upload(body.words)
    }

    @GetMapping("/anagrams/{word}.json")
    fun getAnagrams(@PathVariable word: String, @RequestParam(required = false, defaultValue = "1000") limit: Int): ResponseEntity<AnagramResponse> {
        val response = AnagramResponse(anagramService.findAnagramsForWord(word, limit).map { it.word })
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/words/{word}.json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAnagramWord(@PathVariable word: String, @RequestParam(required = false, defaultValue = "false") deleteAllAnagrams: Boolean) {
        anagramService.deleteWord(word, deleteAllAnagrams)
    }

    @DeleteMapping("/words.json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllAnagramWords() {
        anagramService.deleteAll()
    }

    @GetMapping("/anagrams/top")
    fun getWordsWithMostAnagrams(@RequestParam(required = false, defaultValue = "10") limit: Int): ResponseEntity<AnagramResponse> {
        val response = AnagramResponse(anagramService.findWordsWithMostAnagrams().map { it.word })
        return ResponseEntity.ok(response)
    }

    @GetMapping("/anagrams/groups")
    fun getWordsWithAnagramGroupSize(@RequestParam(required = false, defaultValue = "7") groupSize: Int): ResponseEntity<AnagramResponse> {
        // TODO: can probably clean this up by creating another response class with { groups: {'<anagram hash>': [<words>] } }
        // TODO: add limit to response
        val response = AnagramResponse(anagramService.findWordsWithAnagramGroupSize(groupSize).map { it.word })
        return ResponseEntity.ok(response)
    }
}
