package com.joshfermin.anagram.core

import com.joshfermin.anagram.models.AnagramWord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AnagramRepo: JpaRepository<AnagramWord, UUID> {
    @Query(
        nativeQuery = true,
        value = "SELECT * FROM anagram_words WHERE anagram_hash = :anagramHash AND word != :word ORDER BY word LIMIT :limit "
    )
    fun findMatchingAnagrams(anagramHash: String, word: String, limit: Int): List<AnagramWord>

    fun findAllByWordIn(word: List<String>): List<AnagramWord>

    fun deleteByWord(word: String)

    @Modifying
    @Query(
        nativeQuery = true,
        value = "DELETE FROM anagram_words"
    )
    fun deleteAllAnagrams()
}
