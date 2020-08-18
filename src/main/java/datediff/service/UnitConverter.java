package datediff.service;

import datediff.model.ConvertUnit;

public interface UnitConverter {

	long convertDays(long value, ConvertUnit unit);

	long convertWeeks(long value, ConvertUnit unit);
}
