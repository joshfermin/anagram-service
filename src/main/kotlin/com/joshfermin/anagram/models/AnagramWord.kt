package com.joshfermin.anagram.models

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(name = "anagram_words")
data class AnagramWord(
    @Id val id: UUID,
    val word: String,
    val anagramHash: String,
    val length: Int
) {
    constructor(): this(UUID.randomUUID(), "", "", 0)
}
