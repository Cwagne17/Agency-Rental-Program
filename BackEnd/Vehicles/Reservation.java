package BackEnd.Vehicles;

import UI.Services.ReservationDetails;

public class Reservation {
	
	private String creditCardNum;
	private int vehicleType;
	private String description;
	private TimeSpan rentalPeriod;
	private boolean insuranceSelected;
	
	//Constructor 
	public Reservation(String creditCardNum, int vehicleType, String description, TimeSpan rentalPeriod, boolean insuranceSelected) {
		this.creditCardNum = creditCardNum;
		this.vehicleType = vehicleType;
		this.description = description;
		this.rentalPeriod = rentalPeriod;
		this.insuranceSelected = insuranceSelected;
	}
	
	public Reservation(ReservationDetails res_details, Vehicle vehicle) {
		this.creditCardNum = res_details.getCreditCardNum();
		this.rentalPeriod = res_details.getRenatlPeriod();
		this.insuranceSelected = res_details.getIsuranceSelected();
		
		if(vehicle!=null) {
			if(vehicle instanceof Car)
				this.vehicleType = 1;
			else if(vehicle instanceof Suv)
				this.vehicleType = 2;
			else if(vehicle instanceof Truck)
				this.vehicleType = 3;
			
			this.description = vehicle.getDescription();
		}
		
	}
	
	public String getCreditCardNum() {return creditCardNum;}
	public int getVehicleType() {return this.vehicleType;}
	public String getDescription() {return this.description;}
	public TimeSpan getRentalPeriod() {return rentalPeriod;}
	public boolean getInsuranceSelected() {return insuranceSelected;}
	
	public String toString() {
		return "The reservation is under the card Number: "+creditCardNum+"\nThe vehicle is reserved for: "+getRentalPeriod()+"\nThe option for daily insurance was set to: "+insuranceSelected; 
	}
}
