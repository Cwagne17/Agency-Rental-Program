package UI;
import java.util.Scanner;

import BackEnd.Vehicles.TimeSpan;
import UI.Interfaces.UserInterface;
import UI.Services.RentalDetails;
import UI.Services.ReservationDetails;

public class EmployeeUI implements UserInterface {
	// no constructor needed, calls static methods of the SystemInterface
	// starts a “command loop” that repeatedly: (a) displays a menu of options, (b) gets the selected
	// option from the user, and (c) executes the corresponding command.
	private boolean quit = false;
	
	public void start(Scanner input) {
		
		int selection;
		
		// command loop
		while(!quit) {
			displayMenu();
			selection = getSelection(input);
			execute(selection, input);
		}
	}
	 
	private void execute(int selection, Scanner input){
		int veh_type, num_day_used = 0, num_miles_driven = 0;
		String vin, creditcard_num; 
		String[] display_lines = null;
		RentalDetails rental_details; 
		ReservationDetails reserv_details;
		
		switch(selection) {
			// display rental rates
			case 1: 
				veh_type = getVehicleType(input);
				switch(veh_type){
				 case 1: display_lines = SystemInterface.getCarRates(); break;
				 case 2: display_lines = SystemInterface.getSUVRates(); break;
				 case 3: display_lines = SystemInterface.getTruckRates(); break;
				}
				displayResults(display_lines);
				break;
				
			// display available vehicles
			case 2: 
				veh_type = getVehicleType(input);
				switch(veh_type){
				 case 1: display_lines = SystemInterface.getAvailCars(); break;
				 case 2: display_lines = SystemInterface.getAvailSUVs(); break;
				 case 3: display_lines = SystemInterface.getAvailTrucks(); break;
				}
				displayResults(display_lines);
				break;
				
			// display estimated rental cost
			case 3: 
				rental_details = getRentalDetails(input);
				display_lines = SystemInterface.estimatedRentalCost(rental_details);
				displayResults(display_lines);
				break;
			 
			// make a reservation
			case 4: 
				reserv_details = getReservationDetails(input);
				display_lines = SystemInterface.makeReservation(reserv_details);
				displayResults(display_lines);
				break;
				
			// cancel a reservation
			case 5: 
				vin = getVIN(input);
				creditcard_num = getCreditCardNum(input);
				display_lines = SystemInterface.cancelReservation(creditcard_num, vin);
				displayResults(display_lines);
				break;
				
			// view corporate account (and company reservations)
			case 6: 
				creditcard_num = getCreditCardNum(input);
				display_lines = SystemInterface.getAccountReservations(creditcard_num);
				displayResults(display_lines);
				break;
				
			// process returned vehicle 
			case 7: 
				vin = getVIN(input);
				creditcard_num = getCreditCardNum(input);
				num_day_used = getNumDays(input);
				num_miles_driven = getNumMiles(input, 1);
				display_lines = SystemInterface.processReturnedVehicle(vin,num_day_used,num_miles_driven);
				displayResults(display_lines);
				break;
				
			// quit program
			case 8: quit = true;
		}
	}
		
	// ------- private methods
	private void displayMenu() {
		// displays the menu of options
		System.out.println("\n\t\t\tMAIN MENU - EMPLOYEE\n\n"
				+ "1 - View Current Rates          ...  displays rental (and insurance rates)\n"
				+ "                                     for one of cars, SUVs, or trucks\n\n"
				+ "2 - View Available Vehicles     ...  displays available vehicles (cars, SUVs, or\n"
				+ "                                     trucks)\n\n"
				+ "3 - Calc Estimated Rental Cost  ...  displays estimated rental cost for given vehicle\n"
				+ "                                     type, rental period, expected miles driven,\n"
				+ "                                     optional daily insurance, and if Prime Customer\n\n"
				+ "4 - Make a Reservation          ...  creates a reservation for VIN, credit card num,\n" 
				+ "                                     rental period, and insurance option\n\n"
				+ "5 - Cancel Reservation          ...  cancels a reservation by VIN\n\n" 
				+ "6 – View Corporate Account      ...  displays account information for a given account\n" 
				+ "                                     number, including all current reservations\n\n" 
				+ "7 – Process Returned Vehicle    ...  requests VIN and actual number of miles driven\n" 
				+ "                                     and processes returned vehicle and displays\n" 
				+ "                                     total charge\n"  
				+ "8 - Quit\n");
	}
	
	private int getSelection(Scanner input) {
		int selection;
		boolean flag = false;
		
		// prompts user for selection from menu (continues to prompt is selection < 1 or selection > 8)
		do{
			if(flag)
				System.out.println("\nError: Invalid Input\n       Input must be between 1 and 8 (inclusive)\n");
			
			System.out.println("Which selection would you like to choose?");
			selection = input.nextInt();
			flag = true; //Flag that initiates error message if input is invalid
		}while(selection<1 || selection>8);
		
		return selection;
	}
	
	private String getCreditCardNum(Scanner input) {
		String creditCardNum;
		boolean flag = false;
		boolean valid = false;
		
		do{
			if(flag)
				System.out.println("\nError: Invalid Input\n       Input must be 16 integer digits long\n");
			
			System.out.println("Enter a Credit Card Number");
			creditCardNum = input.next();
			
			valid = validCreditCardNum(creditCardNum); //Valid is true when creditCardNum passes check
			
			flag = true; //Flag that initiates error message if input is invalid
		}while(!valid);
			
		return creditCardNum;
	}
	
	private String getVIN(Scanner input){
		// prompts user to enter VIN for a given vehicle (does not do any error checking on the input)  
		System.out.println("Enter a VIN number for a Vehicle");
		String vin = input.next();
		return vin;
	}
	
	private int getVehicleType(Scanner input) {
		int veh_type;
		boolean flag = false;
		
		// prompts user to enter 1, 2, or 3, and returns (continues to prompt user if invalid input given)
		do {
			if(flag)
				System.out.println("\nError: Invalid Input\n       Input must be 1, 2, or 3");
			
			System.out.println("Enter Vehicle Type:\n"
						+ "1 - Car \n"
						+ "2 - SUV \n"
						+ "3 - Truck");
			veh_type = input.nextInt();
			flag = true;
		}while(veh_type < 1 || veh_type>3);
		
		return veh_type;
	}
	
	private RentalDetails getRentalDetails(Scanner input) {
		// prompts user to enter required information for an estimated rental cost (vehicle type, estimated
		// number of miles expected to be driven, rental period (number of days, weeks or months), and
		// insurance option, returning the result packaged as a RentalDetails object (to be passed in method
		// calls to the SystemInterface) 
		
		int vehicleType = getVehicleType(input)-1;
		int numMiles = getNumMiles(input, 0);
		String rentalPeriod = estimatedRentalPeriod(input);
		boolean insurance = getInsuranceOption(input);
		
		boolean primeCustomer = false;
		System.out.println("Are you a Prime Customer?\n1 - Yes\n2 - No");
		int option = input.nextInt();
		if(option == 1) 
			primeCustomer = true;
		
		return new RentalDetails(vehicleType, rentalPeriod, numMiles, insurance, primeCustomer);

	}

	
	private ReservationDetails getReservationDetails(Scanner input) {
		// prompts user to enter required information for making a reservation (VIN of vehicle to reserve,
		// credit card num, rental period, and insurance option), returning the result packaged as a
		// ReservationDetails object (to be passed in method calls to the SystemInterface) 
		
		String vin = getVIN(input);
		String creditCardNum = getCreditCardNum(input);
		boolean insuranceOption = getInsuranceOption(input);
		
		//Gets TimeSpan Characteristics
		System.out.println("What time unit will you be using?\n1 - Day\n2 - Week\n3 - Month");
		int timeunit  = input.nextInt();
		String metric = "";
		if(timeunit == 1)
			metric = "D";
		else if(timeunit == 2)
			metric = "W";
		else if(timeunit == 3)
			metric = "M";
		System.out.println("How many of that time unit will you be using the vehicle for?");
		int numUnits = input.nextInt();
	
		
		return new ReservationDetails(vin, creditCardNum, new TimeSpan(metric, numUnits), insuranceOption);
		
	}
	
	private void displayResults(String[] lines) {
		// displays the array of strings passed, one string per screen line
		for(String item: lines)
			System.out.println(item);
	}
	
	private boolean validCreditCardNum(String creditCardNum) {
		boolean valid = true;
		
		//Tests whether all chars are int
        for(int i = 0; i<creditCardNum.length(); i++) {
        	if(!Character.isDigit(creditCardNum.charAt(i))){
        			valid=false;
        			break;
        	}
        }
        //Tests whether length and input meets requirements 
    	if(creditCardNum.length()!=16)
    		valid= false;
    	
    	return valid;
	}
	
	private boolean getInsuranceOption(Scanner input) {
		System.out.println("Would you like insurance?\n1 - Yes\n2 - No");
		int option = input.nextInt();
		boolean choice = false;
		if(option == 1)
			choice = true;
		
		return choice;
	}
	
	private String estimatedRentalPeriod(Scanner input) {
		System.out.println("Enter a rental Period i.e. D4 (Four Days)  W2 (Two Weeks) M3 (Three Months)");
		
		String rentalPeriod = input.next();
		return rentalPeriod;
	}
	
	private int getNumDays(Scanner input) {
		System.out.println("How many days was the vehicle used for?");
		
		int numDays = input.nextInt();
		return numDays;
	}
	
	private int getNumMiles(Scanner input, int which) {
		
		if(which==0)
			System.out.println("How many miles will be driven?");
		else 
			System.out.println("How many miles have been driven?");
		
		int miles = input.nextInt();
		return miles;
	}
}
