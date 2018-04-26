package builder;

import java.util.Date;

import com.hqh.util.AppUtil;

public class Mail {
	
	public String subject;
	public String body;
	public String from;
	public String to;
	public Date sendDate;
	
	@Override
	public String toString() {
		return "Mail [subject=" + subject + ", body=" + body + ", from=" + from
				+ ", to=" + to + ", sendDate=" + AppUtil.parseCurrentDateTime(sendDate) + "]";
	}
	
	
	
}
