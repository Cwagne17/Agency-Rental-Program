package BackEnd.Vehicles;

public class TimeSpan {
	private String timeUnit;
	private int numUnits;
	
	
	public TimeSpan(String timeUnit, int numUnits) {
		this.timeUnit = timeUnit;
		this.numUnits = numUnits;
	}
	
	public String toString() {return timeUnit+numUnits;}
	public String getTimeUnit() {return timeUnit;}
	public int getNumUnits() {return numUnits;}
}