package BackEnd.Rates;

public abstract class VehicleRates {
	protected double daily_rate;
	protected double weekly_rate;
	protected double monthly_rate;
	protected double mileage_charge;
	protected double daily_insurance_rate;
	
	public VehicleRates(double daily_rate, double weekly_rate, double monthly_rate, double mileage_chrg,double daily_insur_rate) {
		this.daily_rate = daily_rate;
		this.weekly_rate = weekly_rate;
		this.monthly_rate = monthly_rate;
		this.mileage_charge = mileage_chrg;
		this.daily_insurance_rate = daily_insur_rate;
	}
	
	public double getDailyRate() {return daily_rate;} // cost per day
	public double getWeeklyRate() {return weekly_rate;} // cost per week
	public double getMonthlyRate() {return monthly_rate;} // cost per month
	public double getMileageChrg() {return mileage_charge;} // cost per mile, based on vehicle type
	public double getDailyInsurRate() {return daily_insurance_rate;}// insurance cost (per day)
	public abstract String toString(); // forces subclasses to provide an appropriate toString method
}
