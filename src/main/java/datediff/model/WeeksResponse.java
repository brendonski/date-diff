package datediff.model;

public class WeeksResponse implements Convertible {

	private long weeks;

	private ConvertResponse convert;

	public long getWeeks() {
		return weeks;
	}

	public void setWeeks(long weeks) {
		this.weeks = weeks;
	}

	public ConvertResponse getConvert() {
		return convert;
	}

	public void setConvert(ConvertResponse convert) {
		this.convert = convert;
	}
}
