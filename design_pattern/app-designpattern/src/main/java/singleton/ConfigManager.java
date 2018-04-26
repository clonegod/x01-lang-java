package singleton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.hqh.util.AppUtil;

public class ConfigManager {
	
	private static final String PFILE = "conf.properties";
			//System.getProperty("user.dir") + File.separator + "conf.properties";
	
	private File file = null;
	
	private InputStream is = null;
	
	private long lastModifiedTime = 0;
	
	private Properties props = null;
	
	private static ConfigManager instance = new ConfigManager();
	
	private ConfigManager() {
		init();
	}

	private void init() {
		file = new File(AppUtil.getFileURI(PFILE));
		lastModifiedTime = file.lastModified();
		if(lastModifiedTime == 0) {
			throw new RuntimeException("file doesn't exists! \n"+file.getAbsolutePath());
		}
		props = new Properties();
		try {
			is = AppUtil.loadFileAsInputStream(PFILE);
			props.load(is);
		} catch (Exception e) {
			throw new RuntimeException("load error", e);
		}
	}
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	final synchronized public Object getConfigItem(String name, Object defaultVal) {
		long newTime = file.lastModified();
		if(newTime == 0) {
			if(lastModifiedTime == 0)
				throw new RuntimeException("File doesn't exist!");
			throw new RuntimeException("File was deleted!");
		}
		if(newTime > lastModifiedTime) {
			System.out.println("Reloading [" + PFILE + "], at: " + AppUtil.parseCurrentDateTime());
			props.clear();
			try {
				is.close();
				is = AppUtil.loadFileAsInputStream(PFILE);
				props.load(is);
				lastModifiedTime = newTime;
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("reload error", e);
			}
		}
		
		Object val = props.get(name);
		
		val = AppUtil.toChineseCharacter(val);
		
		return val == null ? defaultVal : val;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		
		for(int i=0; i<10; i++) {
			System.out.println(System.getProperty("user.dir"));
			
			Object value = ConfigManager.getInstance().getConfigItem("name", "未知");
			System.out.println("name="+value);
			
			Object age = ConfigManager.getInstance().getConfigItem("age", -1);
			System.out.println("age="+age);
			
			value = ConfigManager.getInstance().getConfigItem("appName", "未知");
			System.out.println("appName="+value);
			
			TimeUnit.SECONDS.sleep(3);
		}
		
	}
	
}
