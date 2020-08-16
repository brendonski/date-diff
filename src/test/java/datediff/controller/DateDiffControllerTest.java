package datediff.controller;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class DateDiffControllerTest {

	@Inject
	@Client("/")
	HttpClient client;

	@Test
	void testDaysResponse() {
		String response = client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/days?d1=31-10-2000%2001:30:00&d2=01-11-2000%2001:31:00"));
		assertEquals("{\"days\":1}", response);
	}

	@Test
	void testDaysResponseMissingParams() {
		assertThrows(HttpClientResponseException.class, () -> client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/days?d1=&d2=")));
	}

	@Test
	void testWeekDaysResponse() {
		String response = client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/weekdays?d1=10-08-2020%2009:30:00&d2=17-08-2020%2017:30:00"));
		assertEquals("{\"weekDays\":5}", response);
	}

}