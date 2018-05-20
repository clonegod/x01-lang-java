package commons.regexp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网页爬虫- 提取邮件地址
 *
 */
public class Test05WebSpider {
	public static void main(String[] args) throws Exception {
	
		String url = "http://jingyan.baidu.com/article/647f01157ea4ca7f2148a8ce.html";
		
		String html = fetchHTML(url);
		
		System.out.println(html);
		
		Set<String> mails = getMails(html);
		
		System.out.println("共获取到邮箱地址："+mails.size()+"个");
		for(String mail : mails) {
			System.out.println(mail);
		}
	}
	
	public static String fetchHTML(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection)conn;
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		
        
        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }
        
        InputStream inputStream = null;
        BufferedReader bufr = null;
        StringBuffer buf = new StringBuffer();
        
        try {
            inputStream = httpURLConnection.getInputStream();
            bufr = new BufferedReader(new InputStreamReader(inputStream));
            
            String tempLine = null;
            while ((tempLine = bufr.readLine()) != null) {
            	buf.append(tempLine);
            	buf.append(System.getProperty("line.separator"));
            }
        } finally {
            if (bufr != null) {
                bufr.close();
            }
        }
        
        return buf.toString();
    }
	
	
	static final String MAIL_REG = "\\w+@\\w+(\\.\\w+)+";
	static final Pattern p = Pattern.compile(MAIL_REG);
	public static Set<String> getMails(String html) {
		
		Set<String> mailSet = new HashSet<String>();
		
		Matcher m = p.matcher(html);
		
		while(m.find()) {
			mailSet.add(m.group());
		}
		
		return mailSet;
	}
}
