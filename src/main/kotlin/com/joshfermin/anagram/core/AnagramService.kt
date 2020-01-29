package com.joshfermin.anagram.core

import com.joshfermin.anagram.models.AnagramWord
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AnagramService(private val anagramRepo: AnagramRepo) {
    fun upload(words: List<String>): List<AnagramWord> {
        val anagramWords = words.map {
            val sorted = it.toCharArray().sorted().toString()
            AnagramWord(UUID.randomUUID(), it, sorted, it.length)
        }

        return anagramRepo.saveAll(anagramWords)
    }

}
