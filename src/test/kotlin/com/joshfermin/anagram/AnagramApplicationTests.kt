package com.joshfermin.anagram

import com.joshfermin.anagram.models.AnagramResponse
import com.joshfermin.anagram.models.AnagramUploadRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnagramApplicationTests {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `can upload words and retrieve all anagrams of a given word`() {
        val createResp = restTemplate
            .postForEntity<String>(
                "/words.json",
                AnagramUploadRequest(listOf("read", "dear", "dare", "dog", "god", "lake", "leak", "kale"))
            )
        assertThat(createResp.statusCode).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
        assertThat(createResp.body!!).containsSequence("{\"error\":\"The following words: 'dare, dear, dog, god, kale, lake, leak, read' already exists in the data store\"}")

        val getReadResp = restTemplate
            .getForEntity(
                "/anagrams/read.json",
                AnagramResponse::class.java
            )
        assertThat(getReadResp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(getReadResp.body!!.anagrams).containsExactlyInAnyOrder("ared", "daer", "dear", "dare")

		val getLakeResp = restTemplate
			.getForEntity(
				"/anagrams/lake.json?limit=1",
				AnagramResponse::class.java
			)
		assertThat(getLakeResp.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(getLakeResp.body!!.anagrams).containsExactlyInAnyOrder("kale")
	}

}
