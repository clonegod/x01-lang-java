package bean;

public class UserAccount {
	
	private String address;
	private int balance;
	public UserAccount(String address, int balance) {
		super();
		this.address = address;
		this.balance = balance;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "UserAccount [address=" + address + ", balance=" + balance + "]";
	}

}
