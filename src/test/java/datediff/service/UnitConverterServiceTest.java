package datediff.service;

import org.junit.jupiter.api.Test;

import datediff.model.ConvertUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitConverterServiceTest {

	@Test
	public void testConvertDaysNoUnit() {
		assertThrows(IllegalArgumentException.class,
				() -> new UnitConverterService().convertDays(5L, null));
	}

	@Test
	public void testConvertDaysToHoursNegativeValue() {
		UnitConverterService service = new UnitConverterService();
		assertEquals(120L, service.convertDays(-5L, ConvertUnit.HOURS));
	}

	@Test
	public void testConvertDaysToHours() {
		UnitConverterService service = new UnitConverterService();
		assertEquals(120L, service.convertDays(5L, ConvertUnit.HOURS));
	}

	@Test
	public void testConvertDaysToMinutes() {
		UnitConverterService service = new UnitConverterService();
		assertEquals(2880L, service.convertDays(2L, ConvertUnit.MINUTES));
	}

	@Test
	public void testConvertDaysToSeconds() {
		UnitConverterService service = new UnitConverterService();
		assertEquals(172800L, service.convertDays(2L, ConvertUnit.SECONDS));
	}

	@Test
	public void testConvertDaysToYearsLessThanOneYear() {
		UnitConverterService service = new UnitConverterService();
		assertEquals(0, service.convertDays(2L, ConvertUnit.YEARS));
	}

	@Test
	public void testConvertDaysToYearsMoreThanOneYear() {
		UnitConverterService service = new UnitConverterService();
		assertEquals(2L, service.convertDays(1000L, ConvertUnit.YEARS));
	}

	@Test
	public void testConvertWeeksToHours() {
		UnitConverterService service = new UnitConverterService();
		assertEquals(168L, service.convertWeeks(1L, ConvertUnit.HOURS));
	}
}
