package demo.guava.eventbus;

import com.google.common.base.MoreObjects;

public class TradeAccount {
	
	private String cardNo;
	
	public TradeAccount(String cardNo) {
		super();
		this.cardNo = cardNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("cardNo", cardNo)
				.toString();
				
	}
	
	
}
