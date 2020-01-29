package com.joshfermin.anagram

import com.joshfermin.anagram.core.sortByCharsAsc
import org.junit.jupiter.api.Test

class StringExtensionFunctionTest {
    @Test
    fun `sortByCharsAsc sorts string by ascending characters`() {
        assert("ibotta".sortByCharsAsc() == "abiott")
        assert("dog".sortByCharsAsc() == "dgo")
        assert("god".sortByCharsAsc() == "dgo")
    }
}
