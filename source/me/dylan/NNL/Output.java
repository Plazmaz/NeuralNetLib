package me.dylan.NNL;

public class Output {
	private Value value;

	/**
	 * Initialize a blank output, with no initial value
	 */
	public Output() {
		value = new Value();
	}

	/**
	 * Initialize an output with the specified value
	 * 
	 * @param value
	 */
	public Output(Value value) {
		this.value = value;
	}

	/**
	 * Get the power of this output
	 * 
	 * @return value - This is the power/value of the output.
	 */
	public Value getValue() {
		return value;
	}

	/**
	 * Set the power of this output
	 * 
	 * @param value
	 *            - This is the power/value of the output.
	 */
	public void setValue(Value value) {
		this.value = value;
	}
}
