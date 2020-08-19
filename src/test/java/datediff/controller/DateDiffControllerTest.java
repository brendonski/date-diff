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
	void testDaysResponseWithConversion() {
		String response = client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/days?d1=31-10-2000%2001:30:00&d2=01-11-2000%2001:31:00&u=MINUTES"));
		assertEquals("{\"days\":1,\"convert\":{\"value\":1440,\"unit\":\"MINUTES\"}}", response);
	}

	@Test
	void testDaysResponseWithConversionAndTimezone() {
		String response = client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/days?d1=18-08-2020%2005:00:00&d2=19-08-2020%2012:30:00&u=MINUTES&tz1=Europe/Paris&tz2=Australia/Adelaide"));
		assertEquals("{\"days\":1,\"convert\":{\"value\":1440,\"unit\":\"MINUTES\"}}", response);
	}

	@Test
	void testDaysResponseWithConversionAndTimezoneLessThanDay() {
		String response = client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/days?d1=18-08-2020%2005:01:00&d2=19-08-2020%2012:30:00&u=MINUTES&tz1=Europe/Paris&tz2=Australia/Adelaide"));
		assertEquals("{\"days\":0,\"convert\":{\"value\":0,\"unit\":\"MINUTES\"}}", response);
	}

	@Test
	void testDaysResponseWithConversionAndTimezone1() {
		String response = client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/days?d1=18-08-2020%2005:00:00&d2=19-08-2020%2012:30:00&u=MINUTES&tz1=Europe/Paris"));
		assertEquals("{\"days\":1,\"convert\":{\"value\":1440,\"unit\":\"MINUTES\"}}", response);
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

	@Test
	void testWeekDaysResponseWithConversion() {
		String response = client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/weekdays?d1=10-08-2020%2009:30:00&d2=17-08-2020%2017:30:00&u=HOURS"));
		assertEquals("{\"weekDays\":5,\"convert\":{\"value\":120,\"unit\":\"HOURS\"}}", response);
	}

	@Test
	void testWeeksResponse() {
		String response = client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/weeks?d1=08-08-2020%2009:30:00&d2=25-08-2020%2017:30:00"));
		assertEquals("{\"weeks\":2}", response);
	}

	@Test
	void testWeeksResponseWithConversion() {
		String response = client.toBlocking()
				.retrieve(HttpRequest.GET("/date-diff/weeks?d1=08-08-2020%2009:30:00&d2=25-08-2020%2017:30:00&u=MINUTES"));
		assertEquals("{\"weeks\":2,\"convert\":{\"value\":20160,\"unit\":\"MINUTES\"}}", response);
	}

}