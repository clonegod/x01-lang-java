package expression;

import java.util.ArrayList;
import java.util.List;

public class TestInterpreter {
	public static void main(String[] args) {
		String str = "ab";
		Context context = new Context(str);

		List<Expression> list = new ArrayList<Expression>();
		list.add(new AExpression());
		list.add(new BExpression());

		for (Expression ex : list) {
			ex.interpret(context);

		}

		System.out.println(context.getOutput());
	}
}