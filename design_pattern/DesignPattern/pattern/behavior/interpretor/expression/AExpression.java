package expression;

class AExpression extends Expression {
	@Override
	public void interpret(Context context) {
		System.out.println("a expression");
		String input = context.getInput();

		context.setInput(input.substring(1));
		context.setOutput(context.getOutput() + "1");
	}

}