package BackEnd.Accounts;

import BackEnd.Errors.AccountNotFoundException;
import BackEnd.Errors.InvalidCreditCardException;

public class Accounts {
    private AccountNode head;
    private AccountNode current;

    public Accounts(){
        head=null;
        current=head;
    }

    public void add(Account account)throws InvalidCreditCardException{
        //Tests whether valid credit card before adding account
    	if(!creditCardCheck(account.getCreditCardNum()))
        	throw new InvalidCreditCardException();
        
    	if(isEmpty())
            head = new AccountNode(account, null);
        else{
            reset();
            while(current.getNext()!=null)
                iterate();
            current.setNext(new AccountNode(account, null));
        }
    }

    public Account getAccount(String creditCard_num) throws InvalidCreditCardException, AccountNotFoundException{
    	
    	//Private check for valid credit Card
        if(!creditCardCheck(creditCard_num))
    		throw new InvalidCreditCardException();
    	
    	if(!isEmpty()) {
        	reset();
            while(hasNext()) {
                if(getNext().getCreditCardNum().equals(creditCard_num))
                    return getNext();
                iterate();
            }
            throw new AccountNotFoundException();
        }
    	return new Account("","",false);
        
    }
    
    private boolean creditCardCheck(String creditCard_num) {
    	boolean valid = true;
    	//Tests whether all chars are int
        for(int i = 0; i<creditCard_num.length(); i++) {
        	if(!Character.isDigit(creditCard_num.charAt(i))){
        			valid=false;
        			break;
        	}
        }
        //Tests whether length and input meets requirements 
    	if(creditCard_num.length()!=16)
    		valid= false;
    	
    	return valid;
    }
    public boolean isEmpty(){return head==null;}
    public void reset(){current=head;}
	public boolean hasNext() {return current!=null;}
	public Account getNext() {return current.getAccount();}
	public void iterate() {current= current.getNext();}

}
