package BackEnd.Accounts;

public class Account {
    private String creditCard_num;
    private String company_name;
    private boolean prime_customer;

    public Account(String creditCard, String company, boolean prime_customer){
         creditCard_num = creditCard;
         company_name = company;
         this.prime_customer = prime_customer;
    }
    
    public String getName() {return company_name;}
    public String getCreditCardNum() { return creditCard_num; }
    public boolean primeCustomer() { return prime_customer;}
    public String toString() { return company_name+"\nCredit Card Number: "+creditCard_num+"\nIs Prime Customer: "+prime_customer;}
}
