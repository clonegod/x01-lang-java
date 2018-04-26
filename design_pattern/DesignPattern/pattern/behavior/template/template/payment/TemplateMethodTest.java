package template.payment;

public class TemplateMethodTest {
	
	public static void main(String[] args) {
		Payment payment = PaymentFactory.getPayment(PaymentChannel.ALIPAY);
		PayResponse response = payment.pay(new PayRequest());
		System.out.println(response);
		
	}
	
}
