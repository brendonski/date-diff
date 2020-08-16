package datediff.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import datediff.model.DaysResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateDiffServiceTest {

	@Test
	public void testGetDaysStartDateBeforeEndDate() {
		DateDiffService service = new DateDiffService();
		LocalDateTime start = LocalDateTime.of(2020, 11, 29, 17, 15);
		LocalDateTime end = LocalDateTime.of(2020, 11, 30, 18, 44);
		DaysResponse days = service.getDays(start, end);
		assertEquals(1L, days.getDays());
	}

	@Test
	public void testGetDaysEndDateBeforeStartDate() {
		DateDiffService service = new DateDiffService();
		LocalDateTime start = LocalDateTime.of(2020, 11, 30, 18, 44);
		LocalDateTime end = LocalDateTime.of(2020, 11, 29, 17, 15);
		DaysResponse days = service.getDays(start, end);
		assertEquals(1L, days.getDays());
	}

}
