package BackEnd.Transactions;
import java.util.ArrayList;

public class Transactions {
	private ArrayList<Transaction> transactions;
	
	public Transactions() {
		transactions = new ArrayList<Transaction>();
	}
	
	public void add(Transaction transaction) {
		if(transaction!=null)
			transactions.add(transaction);
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
}