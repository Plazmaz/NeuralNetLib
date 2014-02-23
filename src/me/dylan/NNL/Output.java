package me.dylan.NNL;

import java.awt.Color;

import me.dylan.NNL.NNLib.NodeType;

public class Output extends Node {
	/**
	 * Initialize a blank output, with no initial value
	 */
	public Output() {
		this(new Value());
	}

	/**
	 * Initialize an output with the specified value
	 * 
	 * @param value
	 */
	public Output(Value value) {
		this.information = value;
		nodeVariety = NodeType.OUTPUT;
		this.setDisplayColor(Color.GREEN);
	}
	/**
	 * Get the value of this output
	 * @return value - This is the power/value of the output.
	 */
	public Value getOutputValue() {
		return information;
	}

	/**
	 * Set the value of this output
	 * @param value - This is the power/value of the output.
	 */
	public void setValue(Value value) {
		this.information = value;
	}
}
