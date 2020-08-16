package datediff.controller;

import java.time.LocalDateTime;

import datediff.model.DaysResponse;
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
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d1") LocalDateTime d1,
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d2") LocalDateTime d2) {
		return dateDiff.getDays(d1, d2);
	}

}