package me.dylan.NNL;

import java.util.ArrayList;
import java.util.HashMap;

import me.dylan.NNL.NNLib.NodeType;

public class Neuron {
	public HashMap<Output, Integer> dataout = new HashMap<Output, Integer>();
	public HashMap<Input, Integer> datain = new HashMap<Input, Integer>();
	ArrayList<Neuron> connected = new ArrayList<Neuron>();
	Value value = new Value();

	public Neuron() {

	}

	public void addSingleInput(Input input) {
		datain.put(input, 0);
	}

	public void addManyInputs(ArrayList<Input> inputs) {
		for(Input i : inputs) {
			datain.put(i, 0);
		}
	}

	public void addSingleOutput(Output output) {
		dataout.put(output, 0);
	}

	public void addManyOutputs(ArrayList<Output> outputs) {
		for(Output out : outputs) {
			dataout.put(out, 0);
		}
	}

	public void doTick() {
//		value.setValue("");
//		for (Input in : datain.values()) {
//			value.avg(in.getOutput());
//		}
//		for (Neuron neuron : connected) {
//			neuron.sendPulse(neuron.value, NodeType.HIDDEN);
//		}
		for (Output out : dataout.keySet()) {
//			value.avg(out.getValue());
			out.setValue(value);
			
		}
	}

	/*public void sendPulse(Value value, NodeType senderType) {
		switch (senderType) {
		case HIDDEN:
			this.value.avg(value);
			break;
		case INPUT:
			this.value.avg(value);
			break;
		case OUTPUT:
			break;
		default:
			break;
		}
	}*/
}
