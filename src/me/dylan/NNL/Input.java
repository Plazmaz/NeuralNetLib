package me.dylan.NNL;
/**
 * Input nodes, essentially
 * @author Dylan
 *
 */
public class Input extends Node {
	private Value information = new Value();
	/**
	 * Retrieve the raw data collected by the neuron
	 * 
	 * @return data A dump of all data collected by neuron this tick
	 */
	
	public Value getInformation() {
		return information;
	}

	public void setInformation(Value info) {
		this.information = info;
	}

}
