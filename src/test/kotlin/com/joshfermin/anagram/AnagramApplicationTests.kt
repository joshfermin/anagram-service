package com.joshfermin.anagram

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnagramApplicationTests {

	@LocalServerPort
	var port: Int = 0

	@Test
	fun `can upload words and retrieve all anagrams of a given word`() {

	}

}
