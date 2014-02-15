package src.me.dylan.NNL;

import java.util.ArrayList;

import src.me.dylan.NNL.NNLib.NodeType;

public class Neuron {
	public ArrayList<Output> dataout = new ArrayList<Output>();
	public ArrayList<Input> datain = new ArrayList<Input>();
	ArrayList<Neuron> connected = new ArrayList<Neuron>();
	Value value = new Value(0, 100, 0);

	public Neuron() {

	}

	public void addSingleInput(Input input) {
		datain.add(input);
	}

	public void addManyInputs(ArrayList<Input> inputs) {
		datain.addAll(inputs);
	}

	public void addSingleOutput(Output output) {
		dataout.add(output);
	}

	public void addManyOutputs(ArrayList<Output> outputs) {
		dataout.addAll(outputs);
	}

	public void doTick() {
		value.setValue(0);
		for (Input in : datain) {
			value.avg(in.getOutput());
		}
		for (Neuron neuron : connected) {
			neuron.sendPulse(neuron.value, NodeType.HIDDEN);
		}
		for (Output out : dataout) {
			value.avg(out.getValue());
			out.setValue(value);
		}
	}

	public void sendPulse(Value value, NodeType senderType) {
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
	}
}
