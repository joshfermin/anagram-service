package com.joshfermin.anagram.core

import com.joshfermin.anagram.models.AnagramWord
import org.springframework.stereotype.Service
import java.util.UUID
import javax.transaction.Transactional

@Service
class AnagramService(private val anagramRepo: AnagramRepo) {
    @Transactional
    fun upload(words: List<String>): List<AnagramWord> {
        val anagramWords = words.map {
            val sorted = it.sortByCharsAsc()
            AnagramWord(UUID.randomUUID(), it, sorted, it.length)
        }

        return anagramRepo.saveAll(anagramWords)
    }

    fun findAnagramsForWord(word: String): List<AnagramWord> {
        return anagramRepo.findAllByAnagramHashEquals(word.sortByCharsAsc())
    }
}
