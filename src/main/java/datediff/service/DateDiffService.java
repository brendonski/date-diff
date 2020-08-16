package datediff.service;

import javax.inject.Singleton;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import datediff.model.DaysResponse;
import datediff.model.WeekDaysResponse;

import static java.time.temporal.ChronoField.INSTANT_SECONDS;

@Singleton
public class DateDiffService implements DateDiff {

	@Override
	public DaysResponse getDays(ZonedDateTime dt1, ZonedDateTime dt2) {
		validateDates(dt1, dt2);
		long daysBetween = ChronoUnit.DAYS.between(dt1, dt2);
		DaysResponse response = new DaysResponse();
		response.setDays(daysBetween < 0 ? daysBetween * -1 : daysBetween);
		return response;
	}

	@Override
	public WeekDaysResponse getWeekDays(ZonedDateTime dt1, ZonedDateTime dt2) {
		validateDates(dt1, dt2);
		WeekDaysResponse response = new WeekDaysResponse();
		long weekDaysBetween = calculateWeekDaysBetween(dt1, dt2);
		response.setWeekDays(weekDaysBetween);
		return response;
	}

	private void validateDates(ZonedDateTime dt1, ZonedDateTime dt2) {
		if (dt1 == null || dt2 == null) {
			throw new IllegalArgumentException("both datetimes must be specified");
		}
	}

	private long calculateWeekDaysBetween(ZonedDateTime dt1, ZonedDateTime dt2) {
		long dt1Millis = dt1.getLong(INSTANT_SECONDS);
		long dt2Millis = dt2.getLong(INSTANT_SECONDS);
		if (dt1Millis == dt2Millis) {
			return 0;
		}
		// switch datetimes around if dt1 > dt2
		ZonedDateTime start = dt1Millis < dt2Millis ? dt1 : dt2;
		ZonedDateTime end = dt1Millis > dt2Millis ? dt1 : dt2;
		long daysBetween = ChronoUnit.DAYS.between(start, end);
		long weekEndDays = 0;
		while (start.getLong(INSTANT_SECONDS) < end.getLong(INSTANT_SECONDS)) {
			start = start.plus(1L, ChronoUnit.DAYS);
			DayOfWeek day = DayOfWeek.of(start.get(ChronoField.DAY_OF_WEEK));
			if (DayOfWeek.SATURDAY.equals(day) || DayOfWeek.SUNDAY.equals(day)) {
				weekEndDays++;
			}
		}
		return daysBetween - weekEndDays;
	}

}
