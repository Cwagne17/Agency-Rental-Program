package UI.Services;

import BackEnd.Vehicles.TimeSpan;

public class ReservationDetails {
	private String vin;
	private String creditCardNum; // credit card number on file for company account
	private TimeSpan rentalPeriod; // e.g., TimeSpan type stores, e.g, 3, ‘D’ (3 days)
	private boolean insuranceSelected; // set to true if optional daily insurance wanted
	
	public ReservationDetails(String vin, String creditCardNum, TimeSpan rentalPeriod, boolean insuranceSelected) {
		this.vin = vin;
		this.creditCardNum = creditCardNum;
		this.rentalPeriod = rentalPeriod;
		this.insuranceSelected = insuranceSelected;
	}
	
	public String getVin() {return this.vin;}
	public String getCreditCardNum() {return this.creditCardNum;}
	public TimeSpan getRenatlPeriod() {return this.rentalPeriod;}
	public boolean getIsuranceSelected() {return this.insuranceSelected;}
	
}