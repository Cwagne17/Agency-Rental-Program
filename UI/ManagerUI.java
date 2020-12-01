package UI;
import java.util.Scanner;

import BackEnd.Rates.CarRates;
import BackEnd.Rates.SUVRates;
import BackEnd.Rates.TruckRates;
import BackEnd.Rates.VehicleRates;
import UI.Interfaces.UserInterface;
import UI.Services.RentalDetails;
import UI.Services.ReservationDetails;

public class ManagerUI implements UserInterface {
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
			// View/Update Rates
			case 1: 
				veh_type = getVehicleType(input);
				VehicleRates veh_rates = setVehicleRates(input, veh_type);
				switch(veh_type){
				 case 1: display_lines = SystemInterface.updateCarRates(veh_rates); break;
				 case 2: display_lines = SystemInterface.updateSUVRates(veh_rates); break;
				 case 3: display_lines = SystemInterface.updateTruckRates(veh_rates); break;
				}
				displayResults(display_lines);
				break;
				
			// View All Vehicles
			case 2: 
				display_lines = SystemInterface.getAllVehicles();
				displayResults(display_lines);
				break;
				
			// Add Account
			case 3: 
				creditcard_num = getCreditCardNum(input);
				String company_name = getName(input);
				boolean prime_cust = getPrimeCust(input);
				display_lines = SystemInterface.addAccount(creditcard_num, company_name, prime_cust);
				displayResults(display_lines);
				break;
			 
			// View All Reservations
			case 4: 
				display_lines = SystemInterface.getAllReservations();
				displayResults(display_lines);
				break;
				
			// View All Accounts
			case 5: 
				display_lines = SystemInterface.getAllAccounts();
				displayResults(display_lines);
				break;
				
			// View Transactions
			case 6: 
				display_lines = SystemInterface.getAllTransactions();
				displayResults(display_lines);
				break;
				
			// quit program
			case 7: quit = true;
		}
	}
	
	// ------- private methods
	private void displayMenu() {
		// displays the menu of options
		System.out.println("\n\t\t\tMAIN MENU - MANAGER\n\n"
				+ "1 - View/Update Rates         ...  allows updating of rental and insurance rates\n\n"
				+ "2 - View All Vehicles         ...  displays all vehicles of the agency\n\n"
				+ "3 - Add Account               ...  allows entry of a new customer account\n\n"
				+ "4 - View All Reservations     ...  displays all current reservations\n\n" 
				+ "5 - View All Accounts         ...  displays all customer accounts\n\n" 
				+ "6 – View Transactions         ...  displays all transactions\n\n" 
				+ "7 - Quit\n");
	}
	
	private int getSelection(Scanner input) {
		int selection;
		boolean flag = false;
		
		// prompts user for selection from menu (continues to prompt is selection < 1 or selection > 8)
		do{
			if(flag)
				System.out.println("\nError: Invalid Input\n       Input must be between 1 and 7 (inclusive)\n");
			
			System.out.println("Which selection would you like to choose?");
			selection = input.nextInt();
			flag = true; //Flag that initiates error message if input is invalid
		}while(selection<1 || selection>7);
		
		return selection;
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
	
	private VehicleRates setVehicleRates(Scanner input, int veh_type) {
		double daily_rate;
		double weekly_rate;
		double monthly_rate;
		double mileage_charge;
		double daily_insurance_rate;
		VehicleRates rates = null;
		
		//Gets Selection
		System.out.println("New Daily Rate?");
		daily_rate = input.nextDouble();
		System.out.println("New Weekly Rate?");
		weekly_rate = input.nextDouble();
		System.out.println("New Monthly Rate?");
		monthly_rate = input.nextDouble();
		System.out.println("New Mileage Charge?");
		mileage_charge = input.nextDouble();
		System.out.println("New Daily Insurance Rate?");
		daily_insurance_rate = input.nextDouble();
		
		//Creates (Vehicle)Rates Obj
		switch(veh_type) {
			case 1:
				rates = new CarRates(daily_rate, weekly_rate, monthly_rate, mileage_charge, daily_insurance_rate);
				break;
			case 2:
				rates = new SUVRates(daily_rate, weekly_rate, monthly_rate, mileage_charge, daily_insurance_rate);
				break;
			case 3:
				rates = new TruckRates(daily_rate, weekly_rate, monthly_rate, mileage_charge, daily_insurance_rate);
				break;
		}
		 
		return rates;
	}
	
	private String getName(Scanner input) {
		System.out.println("What is the name of the Company? (NO Spaces)");
		String name = input.next();
		return name;
	}
	
	private boolean getPrimeCust(Scanner input) {
		System.out.println("Would you like to be a Prime Customer?\n1 - Yes\n2 - No");
		int choice = input.nextInt();
		return choice==1; 
	}
	
	private void displayResults(String[] lines) {
		// displays the array of strings passed, one string per screen line
		for(String item: lines)
			System.out.println(item);
	}
}
