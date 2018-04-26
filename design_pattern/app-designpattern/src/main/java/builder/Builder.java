package builder;

import java.util.Date;

public abstract class Builder {
	
	protected Mail mail = new Mail();
	
	public Builder() {}

	public abstract void buildeSubject();
	
	public abstract void buildeBody();

	public final void buildeFrom(String fromAddress) {
		mail.from = fromAddress;
	}

	public final void buildeTo(String toAddress) {
		mail.to = toAddress;
	}

	public final void buildeSendDate() {
		mail.sendDate = new Date();
	}

	public Mail retrieveMail() {
		return mail;
	}

}
