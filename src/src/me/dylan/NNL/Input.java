package src.me.dylan.NNL;

import java.util.ArrayList;

public class Input {
	private ArrayList<Value> data = new ArrayList<Value>();
	private Value output = new Value();

	public Input() {

	}

	/**
	 * Retrieve the raw data collected by the neuron
	 * 
	 * @return data A dump of all data collected by neuron this tick
	 */
	public ArrayList<Value> getData() {
		return data;
	}

	public Value getOutput() {
		return output;
	}

	public void setOutput(Value output) {
		this.output = output;
	}

	public void addData(Value value) {
		data.add(value);

	}
}
