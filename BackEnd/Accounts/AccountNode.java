package BackEnd.Accounts;

public class AccountNode {
	private Account account;
    private AccountNode next;

    public AccountNode(Account account, AccountNode next){
        this.account = account;
        this.next = next;
    }

    public Account getAccount(){return account;}
    public AccountNode getNext(){return next;}

    public void setNext(AccountNode next){this.next = next;}
}

