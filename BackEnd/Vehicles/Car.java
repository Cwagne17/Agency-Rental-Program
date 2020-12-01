package BackEnd.Vehicles;

public class Car extends Vehicle{
	
	private int seatCapacity;
	
	//Constructor
	public Car(String description, int mpg, int seatCapacity, String vin) { 
		super(description,mpg,vin);
		this.seatCapacity = seatCapacity;
	}
	
	//Get Method
	public int getSeatCapacity() {return seatCapacity;}
	
	@Override
	public String toString() {
		return description+" (Car)  MPG: "+mpg+"  Seating: "+seatCapacity+"  VIN: "+vin;
	}
}
