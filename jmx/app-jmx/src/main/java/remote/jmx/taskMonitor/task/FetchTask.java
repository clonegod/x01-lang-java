package remote.jmx.taskMonitor.task;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class FetchTask implements Runnable {
    private final String name;
    private final String url;

    public FetchTask(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return "FetchTask: " + name;
    }

    public void run() {  
    	try {
			InputStream is = new URL(url).openStream();
			String html = IOUtils.toString(is);
			System.out.println(html.substring(0, 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}