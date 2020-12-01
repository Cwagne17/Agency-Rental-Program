
import java.util.Scanner;

import BackEnd.Rates.*;
import BackEnd.Accounts.*;
import BackEnd.Transactions.*;
import BackEnd.Vehicles.*;
import UI.EmployeeUI;
import UI.ManagerUI;
import UI.SystemInterface;
import UI.Interfaces.*;

public class AgencyRentalProgram {
	
	public static void main(String[ ] args) {
		
		// Provide Hard-coded Current Agency Rates
		CurrentRates agency_rates = new CurrentRates(new CarRates(24.95,159.95,514.95,.15,14.95),
				 									 new SUVRates(29.95,189.95,679.95,.15,14.95),
				 									 new TruckRates(35.95,224.95,787.95,.26,22.95));
		
		// Create an Initially Empty Vehicles Collection, and Populate
		Vehicles agency_vehicles = new Vehicles();
		populate(agency_vehicles); // supporting private static method (to be added)
		
		// Create Initially Empty Accounts and Transactions Objects
		Accounts accounts = new Accounts();
		Transactions transactions = new Transactions();
		
		// Establish User Interface
		Scanner input = new Scanner(System.in);
		UserInterface ui;
		boolean quit = false;
		
		// Create Requested UI and Begin Execution
		while(!quit) { // (allows switching between Employee and Manager user interfaces while running)
			ui = getUI(input);
			if(ui == null)
				quit = true;
			else {
				// Init System Interface with Agency Data (if not already initialized)
				if(!SystemInterface.initialized())
					SystemInterface.initSystem(agency_rates, agency_vehicles, accounts, transactions);
				
				// Start User Interface
				ui.start(input);
			}
		}
	}
	
	public static UserInterface getUI(Scanner input) {
		boolean valid_selection = false;
		UserInterface ui = null;
		
		while(!valid_selection) {
			
			System.out.print("Rental Agency System\n\n"
					+ "Enter Interface Selection\n"
					+ "1 – Employee \n2 – Manager \n3 – Quit\n");
			int selection = input.nextInt();
			
			if(selection == 1) {
				ui = new EmployeeUI();
				valid_selection = true;
			}
			else if(selection == 2) {
				ui = new ManagerUI();
				valid_selection = true;
			}
			else if(selection == 3) {
				ui = null;
				valid_selection = true;
			}
			else
				System.out.println("Invalid Selection - Please Reenter Input");
		}
		return ui;
	}
	
	public static void populate(Vehicles vehicles) {
		vehicles.add(new Car("Chevrolet Camaro - 2018", 30, 2, "HK4GM4564GD"));
		vehicles.add(new Car("Ford Fusion - 2018", 34, 4, "AB4EG5689GM"));
		vehicles.add(new Car("Ford Fusion Hybrid - 2017", 32, 4, "KU4EG3245RW"));
		vehicles.add(new Car("Chevrolet Impala - 2018", 30, 4, "RK3BM4366YH"));
		
		vehicles.add(new Suv("Honda Odyssey - 2020", 28, 7, 6,"VN9RE2635TD"));
		vehicles.add(new Suv("Dodge Caravan - 2019", 25, 5, 4,"QK3FL4273ME"));
		vehicles.add(new Suv("Ford Expedition - 2018", 20, 5, 3,"JK2RT9264HY"));
		
		vehicles.add(new Truck("Ten-Foot", 12, 2810, "EJ5KU2435BC"));
		vehicles.add(new Truck("Eighteent-Foot", 10, 5930, "KG4DM5472RK"));
		vehicles.add(new Truck("Twenty-Four-Foot", 8, 6500, "EB2WR3082QB"));
		vehicles.add(new Truck("Twenty-Four-Foot", 8, 6500, "TV3GH4280EK"));
	}
}
