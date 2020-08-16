package datediff.service;

import java.time.temporal.Temporal;

import datediff.model.DaysResponse;

public interface DateDiff {

	DaysResponse getDays(Temporal t1, Temporal t2);

}
