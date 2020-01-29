package com.joshfermin.anagram

import com.joshfermin.anagram.models.AnagramResponse
import com.joshfermin.anagram.models.AnagramUploadRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnagramApplicationTests {

	@Autowired
	private lateinit var restTemplate: TestRestTemplate

	@Test
	fun `can upload words and retrieve all anagrams of a given word`() {
		val createResp = restTemplate
			.postForEntity<String>(
				"/words.json",
				AnagramUploadRequest(listOf("read", "dear", "dare", "dog", "god"))
			)
		assertThat(createResp.statusCode).isEqualTo(HttpStatus.CREATED)

		val getResp = restTemplate
			.getForEntity(
				"/anagrams/read.json",
					AnagramResponse::class.java
			)
		assertThat(getResp.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(getResp.body!!.anagrams).containsExactlyInAnyOrder("dear", "dare")
	}

}
