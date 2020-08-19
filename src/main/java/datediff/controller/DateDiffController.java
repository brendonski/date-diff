package datediff.controller;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;

import datediff.model.ConvertResponse;
import datediff.model.ConvertUnit;
import datediff.model.Convertible;
import datediff.model.DaysResponse;
import datediff.model.WeekDaysResponse;
import datediff.model.WeeksResponse;
import datediff.service.DateDiff;
import datediff.service.UnitConverter;
import io.micronaut.core.convert.format.Format;
import io.micronaut.core.util.StringUtils;
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
			@Nullable @QueryValue("u") ConvertUnit unit,
			@Nullable @QueryValue("tz1") String timeZone1,
			@Nullable @QueryValue("tz2") String timeZone2) {
		DaysResponse days = dateDiff.getDays(dt1.atZone(getZoneId(timeZone1)), dt2.atZone(getZoneId(timeZone2)));
		if (unit != null) {
			setConversion(days, unitConverter.convertDays(days.getDays(), unit), unit);
		}
		return days;
	}

	@Get("/weekdays")
	public WeekDaysResponse weekDays(
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d1") LocalDateTime dt1,
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d2") LocalDateTime dt2,
			@Nullable @QueryValue("u") ConvertUnit unit,
			@Nullable @QueryValue("tz1") String timeZone1,
			@Nullable @QueryValue("tz2") String timeZone2) {
		WeekDaysResponse weekDays = dateDiff.getWeekDays(dt1.atZone(getZoneId(timeZone1)),
				dt2.atZone(getZoneId(timeZone2)));
		if (unit != null) {
			setConversion(weekDays, unitConverter.convertDays(weekDays.getWeekDays(), unit), unit);
		}
		return weekDays;
	}

	@Get("/weeks")
	public WeeksResponse weeks(
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d1") LocalDateTime dt1,
			@Format("dd-MM-yyyy HH:mm:ss") @QueryValue("d2") LocalDateTime dt2,
			@Nullable @QueryValue("u") ConvertUnit unit,
			@Nullable @QueryValue("tz1") String timeZone1,
			@Nullable @QueryValue("tz2") String timeZone2) {
		WeeksResponse weeks = dateDiff.getCompleteWeeks(dt1.atZone(getZoneId(timeZone1)),
				dt2.atZone(getZoneId(timeZone2)));
		if (unit != null) {
			setConversion(weeks, unitConverter.convertWeeks(weeks.getWeeks(), unit), unit);
		}
		return weeks;
	}

	private ZoneId getZoneId(String timeZone1) {
		return StringUtils.isNotEmpty(timeZone1) ? ZoneId.of(timeZone1) : ZoneId.systemDefault();
	}

	private void setConversion(Convertible c, long value, ConvertUnit unit) {
		ConvertResponse convert = new ConvertResponse();
		convert.setUnit(unit);
		convert.setValue(value);
		c.setConvert(convert);
	}
}