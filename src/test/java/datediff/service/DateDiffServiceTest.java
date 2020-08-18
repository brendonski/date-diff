package datediff.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import datediff.model.DaysResponse;
import datediff.model.WeekDaysResponse;
import datediff.model.WeeksResponse;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateDiffServiceTest {

	@Test
	public void testGetDaysStartDateBeforeEndDate() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 11, 29, 17, 15);
		LocalDateTime dt2 = LocalDateTime.of(2020, 11, 30, 18, 44);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		DaysResponse days = service.getDays(zdt1, zdt2);
		assertEquals(1L, days.getDays());
	}

	@Test
	public void testGetDaysEndDateBeforeStartDate() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 11, 30, 18, 44);
		LocalDateTime dt2 = LocalDateTime.of(2020, 11, 29, 17, 15);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		DaysResponse days = service.getDays(zdt1, zdt2);
		assertEquals(1L, days.getDays());
	}

	@Test
	public void testGetDaysNoDatesException() {
		assertThrows(IllegalArgumentException.class, () -> new DateDiffService().getDays(null, null));
	}

	@Test
	public void testGetWeekDaysStartDateBeforeEndDate() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 8, 17, 17, 15);
		LocalDateTime dt2 = LocalDateTime.of(2020, 8, 28, 17, 15);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		WeekDaysResponse response = service.getWeekDays(zdt1, zdt2);
		assertEquals(9L, response.getWeekDays());
	}

	@Test
	public void testGetWeekDaysEndDateBeforeStartDate() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 8, 17, 17, 15);
		LocalDateTime dt2 = LocalDateTime.of(2020, 8, 28, 17, 15);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		WeekDaysResponse response = service.getWeekDays(zdt2, zdt1);
		assertEquals(9L, response.getWeekDays());
	}

	@Test
	public void testGetWeekDaysSpanWeekEndPartialWeekDay() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 8, 14, 17, 00);
		LocalDateTime dt2 = LocalDateTime.of(2020, 8, 17, 12, 00);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		WeekDaysResponse response = service.getWeekDays(zdt1, zdt2);
		assertEquals(0, response.getWeekDays());
	}

	@Test
	public void testGetWeekDaysMondayToFriday() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 8, 10, 9, 00);
		LocalDateTime dt2 = LocalDateTime.of(2020, 8, 17, 17, 00);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		WeekDaysResponse response = service.getWeekDays(zdt1, zdt2);
		assertEquals(5L, response.getWeekDays());
	}

	@Test
	public void testZeroCompleteWeeks() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 8, 10, 9, 00);
		LocalDateTime dt2 = LocalDateTime.of(2020, 8, 11, 9, 00);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		WeeksResponse response = service.getCompleteWeeks(zdt1, zdt2);
		assertEquals(0, response.getWeeks());
	}

	@Test
	public void testCompleteWeeks() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 8, 10, 9, 00);
		LocalDateTime dt2 = LocalDateTime.of(2020, 8, 24, 9, 00);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		WeeksResponse response = service.getCompleteWeeks(zdt1, zdt2);
		assertEquals(1L, response.getWeeks());
	}

	@Test
	public void testCompleteWeeks2() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 8, 16, 0, 00);
		LocalDateTime dt2 = LocalDateTime.of(2020, 8, 23, 0, 00);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		WeeksResponse response = service.getCompleteWeeks(zdt1, zdt2);
		assertEquals(1L, response.getWeeks());
	}

	@Test
	public void testCompleteWeeksOverYear() {
		DateDiffService service = new DateDiffService();
		LocalDateTime dt1 = LocalDateTime.of(2020, 8, 5, 0, 00);
		LocalDateTime dt2 = LocalDateTime.of(2020, 8, 26, 0, 00);
		ZonedDateTime zdt1 = dt1.atZone(ZoneId.systemDefault());
		ZonedDateTime zdt2 = dt2.atZone(ZoneId.systemDefault());
		WeeksResponse response = service.getCompleteWeeks(zdt1, zdt2);
		assertEquals(2L, response.getWeeks());
	}

}
