package BackEnd.Vehicles;

public class Suv extends Vehicle{
	
	private int seatCapacity;
	private int cargoCapacity;
	
	//Constructor
	public Suv(String description, int mpg, int seatCapacity, int cargoCapacity, String vin) { 
		super(description,mpg,vin);
		this.seatCapacity = seatCapacity;
		this.cargoCapacity = cargoCapacity;
	}
	
	//Get Method
	public int getSeatCapacity() {return seatCapacity;}
	public int cargoCapacity() {return cargoCapacity;}
	
	@Override
	public String toString() {
		return description+" (SUV)  MPG: "+mpg+"  Seating: "+seatCapacity+"  Storage: "+cargoCapacity+" cu. ft.  VIN: "+vin;
	}
	

}
