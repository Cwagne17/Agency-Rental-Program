package BackEnd.Rates;

public class CurrentRates {
	
	// 0: Car , 1: SUV , 2: Truck
	private VehicleRates[] rates = new VehicleRates[3]; // array of size 3 to store rates for three types of vehicles
	
	//constructor – passed CarRates, SUVRates and TruckRates objects to assign in vehicle_rates array.
	public CurrentRates(CarRates car_rates, SUVRates suv_rates, TruckRates truck_rates) {
		rates[0] = car_rates;
		rates[1] = suv_rates;
		rates[2] = truck_rates;
	}
	
	public VehicleRates getCarRates() {return rates[0];}
	public VehicleRates getSUVRates() {return rates[1];}
	public VehicleRates getTruckRates() {return rates[2];}
	
	public void setCarRates(VehicleRates r) {rates[0] = r;}
	public void setSUVRates(VehicleRates r) {rates[1] = r;}
	public void setTruckRates(VehicleRates r) {rates[2] = r;}
	
	/*
	 * called to calculate a cost quote for a customer given an estimated number of days will use and 
	 * estimated number of miles will drive, whether the daily insurance wanted, and whether 
	 * a prime customer or not.
	 * 
	 * uses the CURRENT standard rates for the vehicle type (car, SUV or truck)
	 * 
	 * passed encoded estimated rental period e.g., D4 (four days), and W2, (two weeks), M1 (one month).
	 */
	public double calcEstimatedCost(int vehicleType, String estimatedRentalPeriod, int estimatedNumMiles,
			boolean dailyInsur, boolean primeCustomer) {
		double total=0;
		
		//Initialize Rate Information Needed
		VehicleRates rate = rates[vehicleType]; //0,1,2 As choices for vehicle types
		int rental_duration = Integer.parseInt(estimatedRentalPeriod.substring(1)); //Number of weeks days or months in integer form
		String time_unit = estimatedRentalPeriod.substring(0,1); //Determines whether Day, Week, Month as Time Unit 
		if(primeCustomer) //Updates Mileage if Prime Customer
			estimatedNumMiles-=100;
		
		//Calculates Info
		if(time_unit.equalsIgnoreCase("d")) { //Calculates both Insurance Cost and General Vehicle Cost
			total+= (rate.daily_rate*rental_duration);
			if(dailyInsur)
				total+= (rate.daily_insurance_rate*rental_duration);
		}else if(time_unit.equalsIgnoreCase("w")) {
			total+= (rate.weekly_rate*rental_duration*7);
			if(dailyInsur)	
				total+= (rate.daily_insurance_rate*rental_duration*7);
		}else if(time_unit.equalsIgnoreCase("m")) {
			total+= (rate.daily_rate*rental_duration*31);
			if(dailyInsur)	
				total+= (rate.daily_insurance_rate*rental_duration*30);
		}//else 
		//Should throw error
		
		if(estimatedNumMiles>0) //Checks if the prime members mileage is greater then 100
			total+=(estimatedNumMiles* rate.mileage_charge); // Calculates Mileage Cost 
		
		return total;
	}
	
	
	/*
	 * called to calculate charge of a returned rented vehicle (for number of days used, num miles driven, whether
	 * daily insurance was selected, and whether a prime customer or not).
	 * 
	 * uses the QUOTED RATE, the rates as they were at the time of the reservation.
	 * 
	 * if the number of days is less than 7, then charged the daily rate; if number of days is greater than 7 and
	 * less than 30, then charged the weekly rate (with remaining days a pro-rated weekly rate); otherwise, get the
	 * monthly rate (with remaining days a pro-rated monthly rate)
	 */
	public double calcActualCost(VehicleRates rates, int numDaysUsed, int numMilesDriven,
	 boolean dailyInsur, boolean primeCustomer) { 
		double total = 0;
		if(primeCustomer) //Updates Mileage if Prime Customer
			numMilesDriven-=100;
		if(numMilesDriven>0)//Makes sure if prime customer used less then 100 miles that they dont get money back.
			total+= (numMilesDriven*rates.getMileageChrg());
		
		//General Usage Cost 
		if(dailyInsur)//Adds Daily Insurance Price 
			total+= (numDaysUsed*rates.getDailyInsurRate());
		
		//General Cost
		if(numDaysUsed>=30) {
			int months = numDaysUsed/30; //integer division, drops remainder
			int days = numDaysUsed%30;
			total+= ((months*rates.getMonthlyRate()) +(days*rates.getDailyRate()));
		}
		else if(numDaysUsed>=7) {
			int weeks = numDaysUsed/7;
			int days = numDaysUsed%7;
			total+=((weeks*rates.getWeeklyRate()) + (days*rates.getDailyRate()));
		}
		else if(numDaysUsed>0) { 
			total+=(numDaysUsed*rates.getDailyRate());
		}
		
		return total;
	}
	
	
	 
}
