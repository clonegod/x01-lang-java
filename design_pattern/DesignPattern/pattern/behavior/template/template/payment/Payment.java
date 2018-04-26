package template.payment;

public interface Payment {
	
	PayResponse pay(PayRequest payRequest);
	
}
