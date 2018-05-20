package clonegod.utils.monitor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * 1. 使用 Apache commons 的 PropertiesConfiguration 来加载xxx.properties配置文件 <br/>
 * 2. 并且当文件更新后，可以配置策略来实现自动重新读取配置。<br/>
 * 3. 还支持将内存中写入的属性保存到配置文件中。
 */
public class PropertiesConfigManager {

	private static final PropertiesConfigManager SINGLE_INSTANCE = new PropertiesConfigManager();

	public static PropertiesConfigManager getInstance() {
		return SINGLE_INSTANCE;
	}

	private PropertiesConfigManager() {
		Path path = Paths.get(System.getProperty("user.dir"), "src/main/java/clonegod/commons/monitor","config.properties");
		System.out.println(path.toAbsolutePath());
		File file = path.toFile();
		monitor(file);
	}

	private static PropertiesConfiguration propertiesConfiguration;

	private void monitor(File propertiesFile) {
		try {
			propertiesConfiguration = new PropertiesConfiguration(propertiesFile);
			// 实现文件更新后自动加载
			propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
			// 支持将内存中修改的属性保存到对应的配置文件中
			propertiesConfiguration.setAutoSave(Boolean.TRUE);
			System.err.println("initial successful.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getString(String key) {
		return propertiesConfiguration.getString(key);
	}

	public static void printAll() {
		Iterator<String> iter = propertiesConfiguration.getKeys();
		while (iter.hasNext()) {
			String key = iter.next();
			System.err.println(String.format("key=%s, value=%s", key, propertiesConfiguration.getString(key)));
		}
	}

	public static void printLatestConfig() {

		System.out.println(PropertiesConfigManager.getInstance().getString("app.name"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(() -> {
			while (true) {
				printLatestConfig();
				printAll();
			}
		}).start();
	}

}
