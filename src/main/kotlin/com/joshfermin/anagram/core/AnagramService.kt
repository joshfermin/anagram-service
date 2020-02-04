package com.joshfermin.anagram.core

import com.joshfermin.anagram.models.AnagramWord
import org.springframework.stereotype.Service
import java.util.UUID
import javax.transaction.Transactional

@Service
class AnagramService(private val anagramRepo: AnagramRepo) {
    @Transactional
    fun upload(words: List<String>): List<AnagramWord> {
        val existingWords = anagramRepo.findAllByWordIn(words)
        if (existingWords.isNotEmpty()) throw WordAlreadyExistsException(existingWords.joinToString { it.word })


        val anagramWords = words.map {
            val sorted = it.sortByCharsAsc()
            AnagramWord(UUID.randomUUID(), it, sorted, it.length)
        }

        return anagramRepo.saveAll(anagramWords)
    }

    fun findAnagramsForWord(word: String, limit: Int): List<AnagramWord> {
        return anagramRepo.findMatchingAnagrams(word.sortByCharsAsc(), word, limit)
    }

    @Transactional
    fun deleteWord(word: String) {
        return anagramRepo.deleteByWord(word)
    }

    @Transactional
    fun deleteAll() {
        return anagramRepo.deleteAllAnagrams()
    }

    fun findWordsWithMostAnagrams(): List<AnagramWord> {
        val anagramHash = anagramRepo.findAnagramWordsWithMostMatches()
        return anagramRepo.findAllByAnagramHash(anagramHash)
    }

    fun findWordsWithAnagramGroupSize(groupSize: Int): List<AnagramWord> {
        val anagramHashes = anagramRepo.findAnagramHashesByGroupSize(groupSize)
        return anagramRepo.findAllByAnagramHashIn(anagramHashes)
    }
}
