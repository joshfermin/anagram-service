package com.joshfermin.anagram.core

import org.springframework.stereotype.Service

@Service
class AnagramService(private val anagramRepo: AnagramRepo) {
    fun upload(words: List<String>) {
        words.forEach {

        }
    }
    
}
