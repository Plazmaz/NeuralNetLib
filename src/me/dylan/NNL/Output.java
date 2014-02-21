package me.dylan.NNL;

public class Output extends Node {
	private Value outputValue;

	/**
	 * Initialize a blank output, with no initial value
	 */
	public Output() {
		outputValue = new Value();
	}

	/**
	 * Initialize an output with the specified value
	 * 
	 * @param value
	 */
	public Output(Value value) {
		this.outputValue = value;
	}
	/**
	 * Get the value of this output
	 * @return value - This is the power/value of the output.
	 */
	public Value getOutputValue() {
		return outputValue;
	}

	/**
	 * Set the value of this output
	 * @param value - This is the power/value of the output.
	 */
	public void setValue(Value value) {
		this.outputValue = value;
	}
}
