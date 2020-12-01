package BackEnd.Vehicles;

import BackEnd.Errors.VINNotFoundException;

public class Vehicles {
	
	private VehicleNode head;
	VehicleNode current;
	
	public Vehicles() {
		head=null;
		current = head;
	}
	
	//Methods 
	public void add(Vehicle vehicle) {
		if(isEmpty())
			head = new VehicleNode(vehicle, null);
		else {
			reset();
			while(current.getNext()!=null)
				iterate();
			current.setNext(new VehicleNode(vehicle, null));
		}
	}
	
	public Vehicle getVehicle(String VIN) 
					throws VINNotFoundException{
		if(!isEmpty()) {
			reset();
			while(hasNext()) {
				if(getNext().getVin().equals(VIN))
					return getNext();
				iterate();
			}
		}
		throw new VINNotFoundException();
	}
	
	public boolean isEmpty() {return head==null;}
	public void reset() {current = head;}
	public boolean hasNext() {return current!=null;}
	public Vehicle getNext() {return current.getVehicle();}
	public void iterate() {current= current.getNext();}
}
