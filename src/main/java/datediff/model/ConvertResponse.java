package datediff.model;

public class ConvertResponse {

	private long value;

	private ConvertUnit unit;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public ConvertUnit getUnit() {
		return unit;
	}

	public void setUnit(ConvertUnit unit) {
		this.unit = unit;
	}
}
