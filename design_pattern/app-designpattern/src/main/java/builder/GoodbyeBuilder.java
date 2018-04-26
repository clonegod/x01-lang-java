package builder;


public class GoodbyeBuilder extends Builder {
	
	private static final String SUBJECT = "Thank you for being with us!";

	@Override
	public void buildeSubject() {
		mail.subject = SUBJECT;
	}

	@Override
	public void buildeBody() {
		String body = "Oops! You have chosen to leave.";
		mail.body = body;
	}

}
