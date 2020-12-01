package UI;

import BackEnd.Rates.*;
import BackEnd.Vehicles.*;
import BackEnd.Accounts.*;
import BackEnd.Transactions.*;
import UI.Services.*;

public class SystemInterface {
	
	private static CurrentRates agency_rates;
	private static Vehicles agency_vehicles;
	private static Accounts accounts;
	private static Transactions transactions_history;
	
	// used to init static variables (in place of a constructor)
	public static void initSystem(CurrentRates r, Vehicles v, Accounts a, Transactions t) {
		agency_rates = r;
		agency_vehicles = v;
		accounts = a;
		transactions_history = t;
	}
	
	public static boolean initialized() {
		return agency_rates != null;
			
	}
	
	// Note that methods makeReservation, cancelReservation, addAccount, and updateXXXRates return
	// an acknowledgement of successful completion of the requested action (e.g. “Vehicle QK3FL4273ME
	// successfully reserved”). Method processReturnedVehicle returns the final cost for the returned
	// vehicle (e.g., “Total charge for VIN QK3FL4273ME for 3 days, 233 miles @ 0.15/mile and daily
	// insurance @ 14.95/day (with 100 miles credit as Prime customer) = $xxx.xx.)
	// Current Rates Related Methods
	public static String[] getCarRates() {
		String[] carRates = new String[1];
		carRates[0] = agency_rates.getCarRates().toString();
		return carRates;
	}
	public static String[] getSUVRates() {
		String[] suvRates = new String[1];
		suvRates[0] = agency_rates.getSUVRates().toString();
		return suvRates;
	}
	public static String[] getTruckRates() {
		String[] truckRates = new String[1];
		truckRates[0] = agency_rates.getTruckRates().toString();
		return truckRates;
	}
	
	public static String[] updateCarRates(VehicleRates r) {
		String[] confirm = new String[1];
		agency_rates.setCarRates(r);
		
		//Check to make sure that it was updated correctly
		if(agency_rates.getCarRates() == r)
			confirm[0] = "Car Rates Succesfully Updated with new Car Rates\nCurrent Rates\n"+agency_rates.getCarRates();
		else
			confirm[0] = "Car Rates Failed to Update\nCurrent Rates\n"+agency_rates.getCarRates();
		return confirm;
	}
	public static String[] updateSUVRates(VehicleRates r) {
		String[] confirm = new String[1];
		agency_rates.setSUVRates(r);
		
		//Check to make sure that it was updated correctly
		if(agency_rates.getSUVRates() == r)
			confirm[0] = "SUV Rates Succesfully Updated with new SUV Rates\nCurrent Rates\n"+agency_rates.getSUVRates();
		else
			confirm[0] = "SUV Rates Failed to Update\nCurrent Rates\n"+agency_rates.getSUVRates();
		return confirm;
	}
	public static String[] updateTruckRates(VehicleRates r) {
		String[] confirm = new String[1];
		if(r!=null)
			agency_rates.setTruckRates(r);
		
		//Check to make sure that it was updated correctly
		if(agency_rates.getTruckRates() == r)
			confirm[0] = "Truck Rates Succesfully Updated with new Truck Rates\nCurrent Rates\n"+agency_rates.getTruckRates();
		else
			confirm[0] = "Truck Rates Failed to Update\nCurrent Rates\n"+agency_rates.getTruckRates();
		return confirm;
	}
	
	public static String[] estimatedRentalCost(RentalDetails details) {
		String[] rental_cost = new String[1];
		
		if(details!=null)
			rental_cost[0] = Double.toString(agency_rates.calcEstimatedCost(details.getVehicleType(), details.getEstimatedRentalPeriod(), details.getEstimatedNumMiles(), details.getDailyInsur(), details.getPrimeCustomer()));
		else
			rental_cost[0] = "*Error*\tNo rental details found.";
		return rental_cost;
	}
	
	public static String[] processReturnedVehicle(String vin, int num_days_used, int num_miles_driven) {
		String[] rental_cost = new String[1];
		
		//Vehicle Details
		Vehicle vehicle = agency_vehicles.getVehicle(vin);
		
		//Reservation Details
		Reservation resv = vehicle.getReservation();
		String credit_card_num = resv.getCreditCardNum();
		
		//Account Details, Finds whether isPrimeCustomer
		Account account = null;
		boolean primeCustomer = false;
		if(accounts.getAccount(credit_card_num)!=null) {
			account=accounts.getAccount(credit_card_num);
			primeCustomer = account.primeCustomer();
		}
		/*
		 * Issue is that to make a reservation there needs to be an account to process a reserved vehicle
		 * 1. Get a name if no account is available
		 * 
		 */
		double cost = agency_rates.calcActualCost(vehicle.getQuotedRates(), num_days_used, num_miles_driven, resv.getInsuranceSelected(),primeCustomer);
		transactions_history.add(new Transaction(credit_card_num, account.getName(), vehicle.getDescription(), resv.getRentalPeriod().toString(), cost+""));
		rental_cost[0] = "Total charge for VIN "+vin+ " is $"+cost+" for "+resv.getRentalPeriod().toString()+", "+num_miles_driven+" miles @ "+vehicle.getQuotedRates().getMileageChrg()+"/mile and daily insurance @ "+vehicle.getQuotedRates().getDailyInsurRate()+"/day";
		
		return rental_cost;
	}
	
	// Note that the rates to be used are retrieved from the VehicleRates object stored in the specific rented
	// vehicle object, the daily insurance option is retrieved from the Reservation object of the rented
	// vehicle, and whether they are a Prime customer is retrieved from the Customer Account object vehicle
	// associated with the Reservation object associated with the specific rented vehicle.
	// Vehicle Related Methods
	public static String[] getAvailCars() {
		// count the number of available cars
		int num_Car = 0;
		if(!agency_vehicles.isEmpty()) {
			agency_vehicles.reset();
			while(agency_vehicles.hasNext()) {
				if(agency_vehicles.getNext() instanceof Car && !agency_vehicles.getNext().isReserved())
						num_Car++;
				agency_vehicles.iterate();
			}
		}
		
		// create appropriate size array
		String[] avail_cars = new String[num_Car];
		
		// populate the array with available cars
		int i = 0;
		Vehicle tempPtr;
		agency_vehicles.reset();
		while(agency_vehicles.hasNext()) {
			tempPtr = agency_vehicles.getNext();
			if(tempPtr instanceof Car && !agency_vehicles.getNext().isReserved())
					avail_cars[i++] = tempPtr.toString();
			agency_vehicles.iterate();
		}
		return avail_cars;
	}
	
	public static String[] getAvailSUVs() {
		// count the number of available Suvs
		int num_suvs = 0;
		if(!agency_vehicles.isEmpty()) {
			agency_vehicles.reset();
			while(agency_vehicles.hasNext()) {
				if(agency_vehicles.getNext() instanceof Suv && !agency_vehicles.getNext().isReserved())
						num_suvs++;
				agency_vehicles.iterate();
			}
		}
		
		// create appropriate size array
		String[] avail_suvs = new String[num_suvs];
		
		// populate the array with available Suvs
		int i = 0;
		Vehicle tempPtr;
		agency_vehicles.reset();
		while(agency_vehicles.hasNext()) {
			tempPtr = agency_vehicles.getNext();
			if(tempPtr instanceof Suv && !agency_vehicles.getNext().isReserved())
					avail_suvs[i++] = tempPtr.toString();
			agency_vehicles.iterate();
		}
		return avail_suvs;
	}
	
	public static String[] getAvailTrucks() {
		// count the number of available trucks
		int num_trucks = 0;
		if(!agency_vehicles.isEmpty()) {
			agency_vehicles.reset();
			while(agency_vehicles.hasNext()) {
				if(agency_vehicles.getNext() instanceof Truck && !agency_vehicles.getNext().isReserved())
						num_trucks++;
				agency_vehicles.iterate();
			}
		}
		
		// create appropriate size array
		String[] avail_trucks = new String[num_trucks];
		
		// populate the array with available trucks
		int i = 0;
		Vehicle tempPtr;
		agency_vehicles.reset();
		while(agency_vehicles.hasNext()) {
			tempPtr = agency_vehicles.getNext();
			if(tempPtr instanceof Truck && !agency_vehicles.getNext().isReserved())
					avail_trucks[i++] = tempPtr.toString();
			agency_vehicles.iterate();
		}
		return avail_trucks;
	}
	
	public static String[] getAllVehicles() {
		//count # of vehicles
		int num_vehicles = 0;
		agency_vehicles.reset();
		while(agency_vehicles.hasNext()) {
					num_vehicles++;
					agency_vehicles.iterate();
		}
		// create appropriate size array
		String[] avail_vehicles = new String[num_vehicles];
		
		// populate the array with available trucks
		int i = 0;
		Vehicle tempPtr;
		agency_vehicles.reset();
		while(agency_vehicles.hasNext()) {
			tempPtr = agency_vehicles.getNext();
			avail_vehicles[i++] = tempPtr.toString();
			agency_vehicles.iterate();
		}
		return avail_vehicles;
	}
	
	public static String[] makeReservation(ReservationDetails details) {
		String[] message = new String[1];
		
		//Find Vehicle (NEEDS TRY & CATCH)
		Vehicle vehicle = agency_vehicles.getVehicle(details.getVin());
		
		//Makes Reservation 
		Reservation resv = new Reservation(details, vehicle);
		
		//Reserves Vehicle (NEEDS TRY & CATCH)
		vehicle.reserve(resv);
		
		//Set Quoted Rates
		if(vehicle instanceof Car)
			vehicle.setQuotedRates(agency_rates.getCarRates());
		else if(vehicle instanceof Suv)
			vehicle.setQuotedRates(agency_rates.getSUVRates());
		else if(vehicle instanceof Truck)
			vehicle.setQuotedRates(agency_rates.getTruckRates());
		
		
		//Create Message
		if(vehicle.isReserved())
			message[0] = "Vehicle: "+vehicle+" was Reserved.";
		else
			message[0] = "Error creating reservation";
		
		return message;
	}
	
	public static String[] cancelReservation(String creditcard_num, String vin) {
		String[] message = new String[1];
		
		//Find Vehicle (NEEDS TRY & CATCH)
		Vehicle vehicle = agency_vehicles.getVehicle(vin);
		
		//Cancels & creates message
		if(vehicle.isReserved()) {
			Reservation resv = vehicle.getReservation();
			String creditCard = resv.getCreditCardNum();
			if(creditCard.equals(creditcard_num)) {
				vehicle.cancelReservation();
				message[0] = "\nReservation was cancelled.\nVehicle: "+vehicle;
			}
			else 
				message[0] = "Error: RESERVATION NOT CANCELLED\nReserved Under Different Credit Card";
		}
		else 
			message[0] = "Error: No Reservation for Vehicle";
		return message;
	}
	
	public static String[] getReservation(String vin) {
		String[] reservation = new String[1];
		
		//Find Vehicle (NEEDS TRY & CATCH)
		Vehicle vehicle = agency_vehicles.getVehicle(vin);
		
		if(vehicle.isReserved())
			reservation[0] = vehicle.getReservation().toString();
		else
			reservation[0] = "Vehicle with VIN: "+vin+" is not reserved.";
		
		return reservation;
	}
	
	public static String[] getAllReservations() {
		//count # of vehicles with reservations
		int num_vehicles = 0;
		agency_vehicles.reset();
		while(agency_vehicles.hasNext()) {
			if(agency_vehicles.getNext().isReserved())		
				num_vehicles++;
			agency_vehicles.iterate();
		}
		// create appropriate size array
		String[] reserved_vehicles;
		if(num_vehicles>0) {
			reserved_vehicles = new String[num_vehicles];
			// populate the array with available trucks
			int i = 0;
			Vehicle tempPtr;
			agency_vehicles.reset();
			while(agency_vehicles.hasNext()) {
				tempPtr = agency_vehicles.getNext();
				if(tempPtr.isReserved())
					reserved_vehicles[i++] = tempPtr.toString();
				agency_vehicles.iterate();
			}
		}
		else {
			reserved_vehicles = new String[1]; 
			reserved_vehicles[0] = "No Vehicles With Reservations";
		}
		return reserved_vehicles;
	}
	
	// Customer Accounts Related Methods
	public static String[] addAccount(String creditcard, String company_name, boolean prime_cust) {
		Account account = new Account(creditcard, company_name, prime_cust);
		
		//(NEEDS TRY & CATCH)
		accounts.add(account);
		
		String[] message = new String[1];
		message[0] = "Account added successfully.\n"+account.toString();
		return message;
	}
	
	public static String[] getAccount(String creditcard_num) {
		String[] account = new String[1];
		
		account[0] = accounts.getAccount(creditcard_num).toString();
		
		return account;
	}
	
	public static String[] getAllAccounts() {
		//count # of accounts
		int num_accounts = 0;
		accounts.reset();
		while(accounts.hasNext()) {		
				num_accounts++;
		}
		// create appropriate size array
		String[] account_list = new String[num_accounts];
		
		//Retrieve all Accounts
		int i = 0;
		Account tempPtr;
		accounts.reset();
		while(accounts.hasNext()) {
			tempPtr = accounts.getNext();
			account_list[i++] = tempPtr.toString();
			agency_vehicles.iterate();
		}
		return account_list;
	}
	
	//Gets all reservations for an account
	public static String[] getAccountReservations(String creditcard_num) {
		Account account =  accounts.getAccount(creditcard_num);
		int num_resv = 0;
		if(!agency_vehicles.isEmpty()) {
			agency_vehicles.reset();
			while(agency_vehicles.hasNext()) {
				if(agency_vehicles.getNext().isReserved()) {
					if(agency_vehicles.getNext().getReservation().getCreditCardNum().equals(creditcard_num))
						num_resv++;
				}
				agency_vehicles.iterate();
			}
		}
		
		// create appropriate size array
		String[] account_resv = new String[num_resv+1];

		// populate the array with resv of account
		int i = 0;
		account_resv[i++] = account.toString();
		
		Vehicle tempPtr;
		agency_vehicles.reset();
		while(agency_vehicles.hasNext()) {
			tempPtr = agency_vehicles.getNext();
			if(tempPtr.isReserved()) {
				if(tempPtr.getReservation().getCreditCardNum().equals(creditcard_num))
					account_resv[i++] = tempPtr.getReservation().toString();
			}
			agency_vehicles.iterate();
		}
		return account_resv;
	}
	
	// transactions-related methods
	public static String[] getAllTransactions() {
		//Uses toArray to convert Arraylist<> to array
		String[] transactions = transactions_history.getTransactions().toArray(new String[0]);
		
		return transactions;
	}
}
