package jvm.oom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

/**
 * 永久区溢出
 * => java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "main"
 * 
 * 解决办法：增大Perm区的内存空间；允许回收永久区的Class（设置哪个jvm参数呢？）
 * 
 */
public class PermOutOfMemoryTest {
	public static void main(String[] args) throws Exception {
    
		for(int i=0; i<1000000; i++) {
			// 设置类成员属性    
			HashMap<String,Class<?>> propertyMap = new HashMap<>();    
			propertyMap.put("id"+i, Class.forName("java.lang.Integer"));    
			propertyMap.put("name"+i, Class.forName("java.lang.String"));    
			propertyMap.put("address"+i, Class.forName("java.lang.String"));    
			// 生成动态 Bean    
			CglibBean bean = new CglibBean(propertyMap);    
			// 给 Bean 设置值    
			bean.setValue("id"+i, new Integer(123));    
			bean.setValue("name"+i, "454");    
			bean.setValue("address"+i, "789");
			//System.out.println(bean.getClass().hashCode());
		}
	}

	static class CglibBean {

		/** * 实体Object */
		private Object object = null;

		/** * 属性map */
		private BeanMap beanMap = null;

		public CglibBean() {
			super();
		}

		public CglibBean(Map<String,Class<?>> propertyMap) {
			this.object = generateBean(propertyMap);
			this.beanMap = BeanMap.create(this.object);
		}

		/** * @param propertyMap * @return */
		private Object generateBean(Map<String,Class<?>> propertyMap) {
			BeanGenerator generator = new BeanGenerator();
			Set<String> keySet = propertyMap.keySet();
			for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
				String key = i.next();
				generator.addProperty(key,  propertyMap.get(key));
			}
			// generator.addProperties(generator, propertyMap); // 简写的方式
			return generator.create();
		}

		/** * 给bean属性赋值 * @param property 属性名 * @param value 值 */
		public void setValue(String property, Object value) {
			beanMap.put(property, value);
		}

		/** * 通过属性名得到属性值 * @param property 属性名 * @return 值 */

		public Object getValue(String property) {
			return beanMap.get(property);
		}

		/** * 得到该实体bean对象 * @return */
		public Object getObject() {
			return this.object;
		}

	}

}
