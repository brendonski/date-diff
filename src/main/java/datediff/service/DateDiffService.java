package datediff.service;

import javax.inject.Singleton;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import datediff.model.DaysResponse;

@Singleton
public class DateDiffService implements DateDiff {

	@Override
	public DaysResponse getDays(Temporal t1, Temporal t2) {
		if (t1 == null || t2 == null) {
			throw new IllegalArgumentException("both datetimes must be specified");
		}
		long between = ChronoUnit.DAYS.between(t1, t2);
		DaysResponse response = new DaysResponse();
		response.setDays(between < 0 ? between * -1 : between);
		return response;
	}

}
