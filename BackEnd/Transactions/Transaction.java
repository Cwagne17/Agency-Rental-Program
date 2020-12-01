package BackEnd.Transactions;

public class Transaction {
	
	private String creditCard_num;
	private String company_name;
	private String vehicle_type; // car, SUV or Truck
	private String rental_period; // days, week, months
	private String rental_cost;
	
	public Transaction(String creditCard_num, String company_name, String vehicle_type, String rental_period, String rental_cost) {
		this.creditCard_num = creditCard_num;
		this.company_name = company_name;
		this.vehicle_type = vehicle_type;
		this.rental_period = rental_period;
		this.rental_cost = rental_cost;
	}


	public String toString() {
		return 	company_name+"\n"
				+ "Credit Card Number: "+creditCard_num+"\n"
				+ "Vehicle Type: "+vehicle_type+"\n"
				+ "Rental Period: "+rental_period+"\n"
				+ "Rental Cost: "+rental_cost;
	}
}
