package BackEnd.Vehicles;

import BackEnd.Errors.ReservedVehicleException;
import BackEnd.Errors.UnreservedVehicleException;
import BackEnd.Rates.VehicleRates;

public abstract class Vehicle {
	
	protected String description;
	protected int mpg;
	protected String vin;
	protected Reservation reservation;
	protected VehicleRates rates; //points to object when reserved
	
	//Constructor
	public Vehicle(String description, int mpg, String vin) {
		this.description = description;
		this.mpg = mpg;
		this.vin = vin;
		reservation = null;
	}
	
	//Get Methods
	public String getDescription() {return description;}
	public int getMpg() {return mpg;}
	public String getVin() {return vin;}
	public Reservation getReservation() {return reservation;}
	public VehicleRates getQuotedRates() {
		if(isReserved())
			return rates;
		return null;
	}
	

	
	//Reservation Methods
	public boolean isReserved() {
		//Returns whether a reservation object is created for the vehicle
		return reservation != null;
	}
	public void reserve(Reservation reservation) throws ReservedVehicleException{
		if(!isReserved())
			this.reservation = reservation;
		else
		  	throw new ReservedVehicleException();
	}
	public void setQuotedRates(VehicleRates cost) throws UnreservedVehicleException{
		if(isReserved())
			rates = cost;
		else
		 	throw new UnreservedVehicleException();
	}
	public void cancelReservation() {
		reservation = null;
		rates = null;
	}
		
	//Abstract Method
	public abstract String toString();
}