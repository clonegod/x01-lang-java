package clonegod.dubbo.user.dto;

import java.io.Serializable;

public class DebitRequest implements Serializable{

    private static final long serialVersionUID = 8929861836033196694L;

    private long debit;
    
	public DebitRequest() {
		super();
	}

	public DebitRequest(long debit) {
		super();
		this.debit = debit;
	}

	public long getDebit() {
		return debit;
	}

	public void setDebit(long debit) {
		this.debit = debit;
	}

}
