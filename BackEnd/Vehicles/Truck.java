package BackEnd.Vehicles;

public class Truck extends Vehicle{
	
	private int loadCapacity;
	
	//Constructor
	public Truck(String description, int mpg, int loadCapacity, String vin) { 
		super(description,mpg,vin); 
		this.loadCapacity = loadCapacity;
	}
	
	//Get Method
	public int loadCapacity() {return loadCapacity;}
	
	@Override
	public String toString() {
		return description +" (Truck)  MPG: "+mpg+"  Load Capacity: "+loadCapacity+" lbs.  VIN: "+vin;
	}
}
