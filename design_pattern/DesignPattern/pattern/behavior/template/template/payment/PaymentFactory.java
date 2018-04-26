package template.payment;

public class PaymentFactory {
	
	/**
	 * 选择支付渠道 --- 选择的动作即为策略模式的体现
	 */
	public static Payment getPayment(PaymentChannel channel) {
		switch(channel) {
			case ALIPAY: return new AliPay();
			case WXPAY: return new WXPay();
		}
		throw new RuntimeException("Not support channel:" + channel.name());
	}
	
}
