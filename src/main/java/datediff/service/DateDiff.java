package datediff.service;

import java.time.ZonedDateTime;

import datediff.model.DaysResponse;
import datediff.model.WeekDaysResponse;

public interface DateDiff {

	DaysResponse getDays(ZonedDateTime dt1, ZonedDateTime dt2);

	WeekDaysResponse getWeekDays(ZonedDateTime dt1, ZonedDateTime dt2);

}
