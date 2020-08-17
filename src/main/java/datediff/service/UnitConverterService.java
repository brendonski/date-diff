package datediff.service;

import javax.inject.Singleton;

import datediff.model.ConvertUnit;

import static datediff.model.ConvertUnit.*;

@Singleton
public class UnitConverterService implements UnitConverter {

	public long convertDays(long value, ConvertUnit unit) {
        if (unit == null) {
        	throw new IllegalArgumentException("Unit must be supplied");
        }
        value = value < 0 ? -value : value;
		if (YEARS.equals(unit)) {
			return Math.floorDiv(value, 365);
		}
		long hours = value * 24;
		if (HOURS.equals(unit)) {
			return hours;
		}
		long minutes = hours * 60;
        if (MINUTES.equals(unit)) {
        	return minutes;
        }
		return minutes * 60;
	}

}
