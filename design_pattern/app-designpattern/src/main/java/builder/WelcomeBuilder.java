package builder;


public class WelcomeBuilder extends Builder {
	
	private static final String SUBJECT = "Welcome to java pattern school!";

	@Override
	public void buildeSubject() {
		mail.subject = SUBJECT;
	}

	@Override
	public void buildeBody() {
		String body = "You just make the right choice";
		mail.body = body;
	}


}
