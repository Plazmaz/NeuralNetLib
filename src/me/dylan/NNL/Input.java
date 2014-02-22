package me.dylan.NNL;

import java.awt.Color;

import me.dylan.NNL.NNLib.NodeType;

/**
 * Input nodes, essentially
 * @author Dylan
 *
 */
public class Input extends Node {
	private Value information = new Value();
	public Input() {
		nodeVariety = NodeType.INPUT;
	}
	/**
	 * Retrieve the raw data collected by the Hidden
	 * 
	 * @return data A dump of all data collected by Hidden this tick
	 */
	public Value getInputData() {
		return information;
	}

	public void setInformation(Value info) {
		this.information = info;
	}
	
	public void appendInfo(String info) {
		this.information.setValue(info);
	}

}
