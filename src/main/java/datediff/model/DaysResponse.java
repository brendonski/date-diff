package datediff.model;

public class DaysResponse implements Convertible {

	private long days;

	private ConvertResponse convert;

	public long getDays() {
		return days;
	}

	public void setDays(long days) {
		this.days = days;
	}

	public ConvertResponse getConvert() {
		return convert;
	}

	public void setConvert(ConvertResponse convert) {
		this.convert = convert;
	}
}
