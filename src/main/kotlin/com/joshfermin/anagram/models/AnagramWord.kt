package com.joshfermin.anagram.models

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(name = "anagram_words", indexes = [
    Index(name = "anagram_hash_ndx", columnList = "anagram_hash"),
    Index(name = "word_ndx", columnList = "word")
])
data class AnagramWord(
    @Id val id: UUID,
    @Column(name = "word") val word: String,
    @Column(name = "anagram_hash") val anagramHash: String,
    val length: Int
) {
    constructor(): this(UUID.randomUUID(), "", "", 0)
}
