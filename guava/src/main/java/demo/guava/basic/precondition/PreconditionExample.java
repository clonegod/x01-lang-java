package demo.guava.basic.precondition;

import com.google.common.base.Preconditions;

public class PreconditionExample {
	private String label;
	private int[] values = new int[5];
	private int currentIndex;

	public PreconditionExample(String label) {
		// returns value of object if not null
		this.label = Preconditions.checkNotNull(label, "Label can''t be null");
	}

	public void updateCurrentIndexValue(int index, int valueToSet) {
		//Check index valid first
		this.currentIndex = Preconditions.checkElementIndex(index, values.length, "Index out of bounds for values");
		//Validate valueToSet
		Preconditions.checkArgument(valueToSet <= 100,"Value can't be more than 100");
		values[this.currentIndex] = valueToSet;
	}

	public void doOperation() {
		Preconditions.checkState(validateObjectState(), "Can't perform operation");
	}

	private boolean validateObjectState() {
		return this.label.equalsIgnoreCase("open") && values[this.currentIndex] == 10;
	}
}