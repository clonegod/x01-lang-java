package guava.collection;

import org.junit.Test;

import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.ImmutableMap;

/**
 * perform type-safe operations and avoid casting
 *
 */
public class Test11NewClassToInstanceMap {

	@Test
	public void test01() {
		ImmutableMap<Class<?>, Action> map = ImmutableMap.<Class<?>, Action>builder()
														 .put(Save.class, new Save())
														 .put(Open.class, new Open())
														 .put(Delete.class, new Delete())
														 .build();
		Action saveAction = map.get(Save.class);
		((Save) saveAction).save();
		
		Action openAction = map.get(Open.class);
		((Open) openAction).open();
		
		Action deleteAction = map.get(Delete.class);
		((Delete) deleteAction).delete();
		
	}
	
	@Test
	public void test02() {
		ImmutableClassToInstanceMap<Action> instanceMap =  
				ImmutableClassToInstanceMap.<Action>builder()
										  .put(Save.class, new Save())
										  .put(Open.class, new Open())
										  .put(Delete.class, new Delete())
										  .build();
		
		// 不再需要强制类型转换
		Open open = instanceMap.getInstance(Open.class);
		Save save = instanceMap.getInstance(Save.class);
		Delete delete = instanceMap.getInstance(Delete.class);
		open.open();
		save.save();
		delete.delete();
	}
	
	static class Action {}
	
	static class Save extends Action {
		public void save() {
			System.out.println("save...");
		}
	}
	static class Open extends Action {
		public void open() {
			System.out.println("open...");
		}
	}
	static class Delete extends Action {
		public void delete() {
			System.out.println("delete...");
		}
	}
	
}
