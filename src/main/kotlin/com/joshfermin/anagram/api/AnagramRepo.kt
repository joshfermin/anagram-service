package com.joshfermin.anagram.api

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AnagramRepo: JpaRepository<AnagramWord, UUID>
