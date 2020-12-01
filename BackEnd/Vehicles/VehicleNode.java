package BackEnd.Vehicles;

public class VehicleNode {
	private Vehicle vehicle;
	private VehicleNode next;
	
	public VehicleNode(Vehicle vehicle, VehicleNode next) {
		this.vehicle = vehicle;
		this.next = next;
	}
	
	public VehicleNode getNext() {return next;}
	public Vehicle getVehicle() {return vehicle;}
	
	public void setNext(VehicleNode next) {this.next = next;}
}