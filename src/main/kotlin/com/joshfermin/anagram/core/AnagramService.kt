package com.joshfermin.anagram.core

import com.joshfermin.anagram.models.AnagramRequest
import com.joshfermin.anagram.models.AnagramWord
import com.joshfermin.anagram.security.InvalidWordException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.UUID
import javax.transaction.Transactional

@Service
class AnagramService(private val anagramRepo: AnagramRepo) {
    @Value( "\${anagram.service.max-word-length}" )
    private var maxWordLength: Int = 100

    @Transactional
    fun upload(words: List<String>): List<AnagramWord> {
        val existingWords = anagramRepo.findAllByWordIn(words)
        if (existingWords.isNotEmpty()) throw InvalidWordException("The following words: [${existingWords.joinToString { it.word }}] already exist in the data store")

        val anagramWords = words.map {
            if (it.length > maxWordLength) throw InvalidWordException("Word you are trying to upload is over the max word length of: $maxWordLength")
            val sorted = it.sortByCharsAsc()
            AnagramWord(UUID.randomUUID(), it, sorted, it.length)
        }

        return anagramRepo.saveAll(anagramWords)
    }

    fun findAnagramsForWord(word: String, limit: Int): List<AnagramWord> {
        return anagramRepo.findMatchingAnagrams(word.sortByCharsAsc(), word, limit)
    }

    @Transactional
    fun deleteWord(word: String, deleteAllAnagrams: Boolean) {
        return when(deleteAllAnagrams) {
            true -> {
                val wordsToDelete = anagramRepo.findAllByAnagramHash(word.sortByCharsAsc())
                wordsToDelete.forEach { anagramRepo.deleteByWord(it.word) }
            }
            false -> anagramRepo.deleteByWord(word)
        }
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

    fun validateWordsAreAnagrams(anagramWords: AnagramRequest): Boolean {
        if (anagramWords.words.isEmpty()) return true

        val uniqueAnagrams = mutableSetOf<String>()

        anagramWords.words.forEach {
            uniqueAnagrams.add(it.sortByCharsAsc())
            if (uniqueAnagrams.size > 1) return false
        }

        return true
    }
}
