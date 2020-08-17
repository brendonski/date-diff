package datediff.controller;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;

import datediff.model.ConvertResponse;
import datediff.model.ConvertUnit;
import datediff.model.DaysResponse;
import datediff.model.WeekDaysResponse;
import datediff.model.WeeksResponse;
import datediff.service.DateDiff;
import datediff.service.UnitConverter;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller("/date-diff")
public class DateDiffController {

	private DateDiff dateDiff;

	private UnitConverter unitConverter;

	public DateDiffController(DateDiff dateDiff, UnitConverter unitConverter) {
		this.dateDiff = dateDiff;
		this.unitConverter = unitConverter;
	}

	@Get("/days")
	public DaysResponse days(
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d1") LocalDateTime dt1,
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d2") LocalDateTime dt2,
			@Nullable @QueryValue("u") ConvertUnit unit) {
		DaysResponse days = dateDiff.getDays(dt1.atZone(ZoneId.systemDefault()), dt2.atZone(ZoneId.systemDefault()));
		if (unit != null) {
			ConvertResponse convert = new ConvertResponse();
			convert.setUnit(unit);
			convert.setValue(unitConverter.convertDays(days.getDays(), unit));
			days.setConvert(convert);
		}
		return days;
	}

	@Get("/weekdays")
	public WeekDaysResponse weekDays(
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d1") LocalDateTime dt1,
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d2") LocalDateTime dt2) {
		return dateDiff.getWeekDays(dt1.atZone(ZoneId.systemDefault()), dt2.atZone(ZoneId.systemDefault()));
	}

	@Get("/weeks")
	public WeeksResponse weeks(
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d1") LocalDateTime dt1,
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d2") LocalDateTime dt2) {
		return dateDiff.getWeeks(dt1.atZone(ZoneId.systemDefault()), dt2.atZone(ZoneId.systemDefault()));
	}

}