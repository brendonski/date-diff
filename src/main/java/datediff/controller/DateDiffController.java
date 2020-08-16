package datediff.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import datediff.model.DaysResponse;
import datediff.model.WeekDaysResponse;
import datediff.service.DateDiff;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller("/date-diff")
public class DateDiffController {

	private DateDiff dateDiff;

	public DateDiffController(DateDiff dateDiff) {
		this.dateDiff = dateDiff;
	}

	@Get("/days")
	public DaysResponse days(
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d1") LocalDateTime dt1,
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d2") LocalDateTime dt2) {
		return dateDiff.getDays(dt1.atZone(ZoneId.systemDefault()), dt2.atZone(ZoneId.systemDefault()));
	}

	@Get("/weekdays")
	public WeekDaysResponse weekDays(
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d1") LocalDateTime dt1,
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d2") LocalDateTime dt2) {
		return dateDiff.getWeekDays(dt1.atZone(ZoneId.systemDefault()), dt2.atZone(ZoneId.systemDefault()));
	}

}