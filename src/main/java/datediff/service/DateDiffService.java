package datediff.service;

import javax.inject.Singleton;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datediff.model.DaysResponse;
import datediff.model.WeekDaysResponse;
import datediff.model.WeeksResponse;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@Singleton
public class DateDiffService implements DateDiff {

	private static final Logger LOG = LoggerFactory.getLogger(DateDiffService.class);

	@Override
	public DaysResponse getDays(ZonedDateTime dt1, ZonedDateTime dt2) {
		long daysBetween = getBetween(dt1, dt2, ChronoUnit.DAYS);
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

	@Override
	public WeeksResponse getCompleteWeeks(ZonedDateTime dt1, ZonedDateTime dt2) {
		validateDates(dt1, dt2);
		WeeksResponse response = new WeeksResponse();
		long weeksBetween = calculateWeeksBetween(dt1, dt2);
		response.setWeeks(weeksBetween);
		return response;
	}

	private long calculateWeeksBetween(ZonedDateTime dt1, ZonedDateTime dt2) {
		// switch datetimes around if dt1 > dt2
		int compare = dt1.compareTo(dt2);
		if (compare == 0) {
			return 0;
		}
		long daysBetween = getBetween(dt1, dt2, ChronoUnit.DAYS);
		if (daysBetween < 7) {
			return 0;
		}
		ZonedDateTime start = compare < 0 ? dt1 : dt2;
		ZonedDateTime end = compare < 0 ? dt2 : dt1;
		DayOfWeek firstDayOfWeek = getFirstDayOfWeek();
		DayOfWeek lastDayOfWeek = getLastDayOfWeek();
		ZonedDateTime startOfNextWeek = start.with(nextOrSame(firstDayOfWeek));
		if (startOfNextWeek.compareTo(start) > 0) {
			// check if start date was moved to the next occurrence of first day of week
			// and reset time to start of day
			startOfNextWeek = startOfNextWeek.toLocalDate().atStartOfDay(start.getZone());
		}
		ZonedDateTime endOfPreviousWeek = end.with(previousOrSame(lastDayOfWeek));
		LOG.debug("111 end {}", endOfPreviousWeek);
		if (endOfPreviousWeek.compareTo(end) < 0) {
			// check if end date was moved to the previous occurrence of last day of week
			// and reset time to end of day
			endOfPreviousWeek = ZonedDateTime.of(
					endOfPreviousWeek.toLocalDate().atTime(23, 59, 59), end.getZone());
		}
		long numWeeks = 0;
		LOG.debug("start {}", startOfNextWeek);
		LOG.debug("end {}", endOfPreviousWeek);
		while (startOfNextWeek.compareTo(endOfPreviousWeek) < 0) {
			numWeeks++;
			startOfNextWeek = startOfNextWeek.plusDays(7);
		}
		return numWeeks;
	}

	private DayOfWeek getFirstDayOfWeek() {
		DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
		LOG.debug("first day of week {}", firstDayOfWeek);
		return firstDayOfWeek;
	}

	private DayOfWeek getLastDayOfWeek() {
		DayOfWeek firstDayOfWeek = getFirstDayOfWeek();
		int i = (firstDayOfWeek.getValue() - 1) == 0 ? 7 : (firstDayOfWeek.getValue() - 1);
		DayOfWeek lastDayOfWeek = DayOfWeek.of(i);
		LOG.debug("last day of week {}", lastDayOfWeek);
		return lastDayOfWeek;
	}

	private void validateDates(ZonedDateTime dt1, ZonedDateTime dt2) {
		if (dt1 == null || dt2 == null) {
			throw new IllegalArgumentException("both datetimes must be specified");
		}
	}

	private long getBetween(ZonedDateTime dt1, ZonedDateTime dt2, ChronoUnit unit) {
		validateDates(dt1, dt2);
		return unit.between(dt1, dt2);
	}

	private long calculateWeekDaysBetween(ZonedDateTime dt1, ZonedDateTime dt2) {
		int compare = dt1.compareTo(dt2);
		if (compare == 0) {
			return 0;
		}
		ZonedDateTime start = compare < 0 ? dt1 : dt2;
		ZonedDateTime end = compare < 0 ? dt2 : dt1;
		long daysBetween = ChronoUnit.DAYS.between(start, end);
		long weekEndDays = 0;
		while (start.compareTo(end) < 0) {
			start = start.plus(1L, ChronoUnit.DAYS);
			DayOfWeek day = DayOfWeek.of(start.get(ChronoField.DAY_OF_WEEK));
			if (DayOfWeek.SATURDAY.equals(day) || DayOfWeek.SUNDAY.equals(day)) {
				weekEndDays++;
			}
		}
		return daysBetween - weekEndDays;
	}

}
