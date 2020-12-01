package UI.Services;

public class RentalDetails {
	
	private int vehicleType;
	private String estimatedRentalPeriod;
	private int estimatedNumMiles;
	private boolean dailyInsur;
	private boolean primeCustomer;
	
	public RentalDetails(int vehicleType, String estimatedRentalPeriod, int estimatedNumMiles,
			boolean dailyInsur, boolean primeCustomer) {
		this.vehicleType = vehicleType;
		this.estimatedRentalPeriod = estimatedRentalPeriod;
		this.estimatedNumMiles = estimatedNumMiles;
		this.dailyInsur = dailyInsur;
		this.primeCustomer = primeCustomer;
	}
	
	public int getVehicleType() {return vehicleType;}
	public String getEstimatedRentalPeriod() {return estimatedRentalPeriod;}
	public int getEstimatedNumMiles() {return this.estimatedNumMiles;}
	public boolean getDailyInsur() {return this.dailyInsur;}
	public boolean getPrimeCustomer() {return this.primeCustomer;}
	
}
