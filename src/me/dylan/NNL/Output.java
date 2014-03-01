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
     * 
     * @return value - This is the power/value of the output.
     */
    public Value getOutputValue() {
	return information;
    }

    public void putOrMove(String value) {
	String infoData = information.getData();
	if (!value.isEmpty() && information.getData().contains(value)) {
	    int valueIndex = infoData.indexOf(value);
	    information.setValue(infoData.substring(valueIndex, valueIndex+value.length()));
	    information.appendToValue(new Value(value));
	    setNodeData(value);
	} else {
	    setNodeData(information.getData() + value);
	}
    }
}
