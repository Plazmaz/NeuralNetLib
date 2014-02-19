package me.dylan.NNL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.dylan.NNL.NNLib.NodeType;

public class Neuron extends Node {
	public float weight;
	public HashMap<Output, Integer> dataout = new HashMap<Output, Integer>();
	public HashMap<Input, Integer> datain = new HashMap<Input, Integer>();
	ArrayList<Neuron> connected = new ArrayList<Neuron>();
	ArrayList<Synapse> synapses = new ArrayList<Synapse>();
	Value neuronValue = new Value();

	public void addSingleInput(Input input) {
		connectRandom(input);
		datain.put(input, 0);
	}

	public void addManyInputs(ArrayList<Input> inputs) {
		for (Input i : inputs) {
			addSingleInput(i);
		}
	}

	public void addSingleOutput(Output output) {
		connectRandom(output);
		dataout.put(output, 0);
	}

	public void addManyOutputs(ArrayList<Output> outputs) {
		for (Output out : outputs) {
			addSingleOutput(out);
		}
	}

	public void doTick() {
		neuronValue.setValue("");
		for (Input in : datain.keySet()) {
			sendPulse(in.getOutput(), NodeType.INPUT);
		}
		for (Neuron neuron : connected) {
			neuron.sendPulse(neuron.neuronValue, NodeType.HIDDEN);
		}

		// Old regex implementation:
		// String[] regexes = RegParams.regParamsDel.split(", ");
		// for (Output out : dataout.keySet()) {
		// // value.avg(out.getValue());
		// neuronValue = new Value(neuronValue.data.replaceAll(
		// RegParams.regParamsDel, ""));
		// out.setValue(neuronValue);
		// }
	}

	public void randomize(NNetwork parentNet) {
		Random rand = NNLib.rand;

		for (int i = 0; i < rand.nextInt(parentNet.getInputs().size()); i++) {
			addSingleInput(parentNet.getInputs().get(i));
		}

		for (int i = 0; i < rand.nextInt(parentNet.getOutputs().size()); i++) {
			addSingleOutput(parentNet.getOutputs().get(i));
		}

		for (int i = 0; i < rand.nextInt(parentNet.getNeurons().size()); i++) {
			this.connected.add(parentNet.getNeurons().get(i));
			connectRandom(parentNet.getNeurons().get(i));
		}

	}

	public void sendPulse(Value value, NodeType senderType) {
		switch (senderType) {
		case HIDDEN:
			this.neuronValue.avg(value);
			break;
		case INPUT:
			this.neuronValue.avg(value);
			break;
		case OUTPUT:
			break;
		default:
			break;
		}
	}

	public ArrayList<Node> getNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.addAll(connected);
		nodes.addAll(datain.keySet());
		nodes.addAll(dataout.keySet());
		return nodes;
	}

	public void connect(Node destination, int weight) {
		if (destination instanceof Input)
			addSingleInput((Input) destination);
		if (destination instanceof Output)
			addSingleOutput((Output) destination);
		if (destination instanceof Neuron)
			connected.add((Neuron) destination);
	}

}
