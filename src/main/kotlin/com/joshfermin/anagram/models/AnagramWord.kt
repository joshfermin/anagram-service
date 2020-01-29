package com.joshfermin.anagram.models

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class AnagramWord(
    @Id val id: UUID,
    val word: String,
    val anagramHash: String,
    val length: Int
) {
    constructor(): this(UUID.randomUUID(), "", "", 0)
}
