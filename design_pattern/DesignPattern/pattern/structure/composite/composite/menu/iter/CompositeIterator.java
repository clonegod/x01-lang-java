package composite.menu.iter;

import java.util.Iterator;
import java.util.Stack;

import composite.menu.component.Menu;
import composite.menu.component.MenuComponent;

/**
 * 组合迭代器：遍历组件内部的所有菜单项，确保所有的菜单和子菜单都被遍历到。
 *
 *	CompositeIterator同样使用了组合的方式，内部通过Stack封装Sub_CompositeIterator。
 */
public class CompositeIterator implements Iterator<MenuComponent> {

	Stack<Iterator<MenuComponent>> stack = new MyStack<>();
	
	public CompositeIterator(Iterator<MenuComponent> iter) {
		this.stack.push(iter);
	}

	/**
	 * 重要：解决重复打印的问题！！！具体可根据控制台打印的stack push数据进行分析.
	 * ALL_MENUS => DINNER_MENU => DESSERT_MENU 解决第三级餐单DESSERT_MENU入栈2次，导致重复打印的问题！！！
	 * 同一个CompositeIterator分别在在不同的Stack对象中进行了入栈.所以将最新的那个堆栈上的元素作废。
	 */
	@Override
	public boolean hasNext() {
		if(stack.isEmpty()) {
			return false;
		} else {
			Iterator<MenuComponent> iter = stack.peek(); 
			if(!iter.hasNext()) {
				stack.pop(); // 出栈
				return hasNext(); // 继续判断堆栈中下一个迭代器对象
			} else if(stack.size() > 2) { // 解决重复打印的问题
				Iterator<MenuComponent> popIter = stack.pop(); 
				System.out.println("丢弃重复的迭代器："+popIter);
				return hasNext();
			}else {
				return true;
			}
		}
	}

	@Override
	public MenuComponent next() {
		//if(hasNext()) {
			Iterator<MenuComponent> iter = stack.peek();
			MenuComponent mc = iter.next();
			if(mc instanceof Menu) {
				stack.push(mc.createIterator());
				System.out.println(stack+" push menu: " + mc);
			}
			return mc;
		//}
		//return null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}

/**
 * 扩展自定义堆栈，用于输出堆栈的名称，这样可以确定出不同CompositeIterator对象内部的Stack对象是不同的。
 * 即-每个CompositeIterator对象都有自己的一个Stack对象用来存储Iterator。
 */
class MyStack<E> extends Stack<E> {

	private static final long serialVersionUID = -6997697022234705195L;
	
	@Override
	public String toString() {
		return "MyStack [toString()=" +System.currentTimeMillis() + super.toString() + "]";
	}
	
	
}