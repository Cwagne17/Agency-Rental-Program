package BackEnd.Rates;

public class CarRates extends VehicleRates{
	
	public CarRates(double daily_rate, double weekly_rate, double monthly_rate, double mileage_chrg,double daily_insur_rate) {
		super(daily_rate, weekly_rate, monthly_rate, mileage_chrg, daily_insur_rate);
	}
	
	public String toString() {
		return "****************************\n"
				+"\tCar Rates\n\n"
				+ "Daily Rate: "+daily_rate+"\n"
				+"Weekly Rate: "+weekly_rate+"\n"
				+"Monthly Rate: "+monthly_rate+"\n"
				+"Mileage Charge: "+mileage_charge+"\n"
				+"Daily Insurance Rate: "+daily_insurance_rate+"\n"
				+"****************************";
	}
}
