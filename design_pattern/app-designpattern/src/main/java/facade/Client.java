package facade;

public class Client {
	public static void main(String[] args) {
		 
		SecurityFacade security = new SecurityFacade();
		
		security.active();
		
		security.deActive();
	}
}
