package singleton.multi;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.hqh.util.AppUtil;

/**
 * 多例模式
 */
public class LingualResource {
	
	private String localeCode = "en_US";
	
	private static final String FILE_NAME = "res";
	
	private Locale locale = null;
	private ResourceBundle resourceBundle = null;
	
	// 每个地区绑定1个LingualResource实例
	private static final HashMap<String, LingualResource> instances = new HashMap<String, LingualResource>(19);

	private LingualResource(String language, String region) {
		this.localeCode = getLocalCode(language, region);
		
		locale = new Locale(language, region);
		resourceBundle = ResourceBundle.getBundle(FILE_NAME, locale);
		
		instances.put(localeCode, this); // 加入聚集中
	}
	
	private static String getLocalCode(String language, String region) {
		return language + "_" + region;
	}
	
	public synchronized static LingualResource getInstance(String language, String region) {
		String key = getLocalCode(language, region);
		if(instances.containsKey(key)) {
			return instances.get(key);
		}
		return new LingualResource(language, region);
	}
	
	public String getLocaleString(String code) {
		String string = resourceBundle.getString(code);
		if("zh".equals(resourceBundle.getLocale().getLanguage())) {
			string = (String) AppUtil.toChineseCharacter(string);
		}
		return string;
	}
	
}
