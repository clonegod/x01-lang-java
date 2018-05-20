package EnumMap;

import java.util.EnumMap;

public class TestEnumMap {
	enum Keys {
		NEED_SMS_CODE, 
		NEED_ID_NUMBER,
		NEED_NAME,
		NEDD_IMAGE_CODE
	}
	
	public static void main(String[] args) {
		EnumMap<Keys, String> enumMap = new EnumMap<>(Keys.class);
		
		enumMap.put(Keys.NEED_SMS_CODE, "请输入短信验证码");
		enumMap.put(Keys.NEED_ID_NUMBER, "请输入身份证号码");
		enumMap.put(Keys.NEED_NAME, "请输入姓名");
		enumMap.put(Keys.NEDD_IMAGE_CODE, "请输入图片验证码");
		
		System.out.println(enumMap);
		
	}
}
