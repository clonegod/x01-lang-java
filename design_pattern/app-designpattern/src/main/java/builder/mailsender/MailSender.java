
package builder.mailsender;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import builder.Mail;


public class MailSender {
	
	private MailSender() {}
	private static MailSender sender = new MailSender();
	public static MailSender getInstance() {
		return sender;
	}
	
	// SMTP服务器地址
	private static final String SMPT_HOST = "";
	protected static final String USERNAME = null;
	protected static final String PASSWORD = null;
	
	public void send(Mail... mails) throws Exception {
		
		Properties props = new Properties();
		props.put("mail.smtp.host", SMPT_HOST);
		
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
			
		});
		MimeMessage message = new MimeMessage(session);
		
		for(Mail mail : mails) {
			send(message, mail);
		}
	}
	
	private void send(MimeMessage message, Mail mail) throws Exception {
		
		message.setFrom(new InternetAddress(mail.from));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.to));
		message.setSubject(mail.subject);
		message.setText(mail.body);
		message.setSentDate(mail.sendDate);
		
		//Transport.send(message);
		
		System.out.println("Send mail from " + mail.from + " to " + mail.to);
		System.out.println(mail);
	}
	
	
}
