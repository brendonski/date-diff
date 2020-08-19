package datediff.model;

public class WeekDaysResponse implements Convertible {

	private long weekDays;

	private ConvertResponse convert;

	public long getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(long weekDays) {
		this.weekDays = weekDays;
	}

	public ConvertResponse getConvert() {
		return convert;
	}

	public void setConvert(ConvertResponse convert) {
		this.convert = convert;
	}
}
