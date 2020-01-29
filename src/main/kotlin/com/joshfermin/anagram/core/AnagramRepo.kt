package com.joshfermin.anagram.core

import com.joshfermin.anagram.models.AnagramWord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AnagramRepo: JpaRepository<AnagramWord, UUID>
