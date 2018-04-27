package demo.guava.functional.function;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

import demo.guava.functional.State;

/**
 * 根据输入的input，计算并返回一个结果
 * 
 * @author clonegod@163.com
 *
 */
public class StateToCityStringFunction implements Function<State, String> {
	@Override
	public String apply(State input) {
		return Joiner.on(",").join(input.getMainCities());
	}
}