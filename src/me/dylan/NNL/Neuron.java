package me.dylan.NNL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.dylan.NNL.NNLib.NodeType;

public class Neuron extends Node {
	public float weight;
	public HashMap<Output, Integer> dataout = new HashMap<Output, Integer>();
	public HashMap<Input, Integer> datain = new HashMap<Input, Integer>();
	ArrayList<Neuron> connectedNodes = new ArrayList<Neuron>();
	Value neuronValue = new Value();
	NodeType nodeVariety = NodeType.HIDDEN;

	public void addSingleInputNode(Input input) {
		connectWithRandomWeight(input);
		datain.put(input, 0);
	}

	public void addManyInputNodes(ArrayList<Input> inputs) {
		for (Input i : inputs) {
			addSingleInputNode(i);
		}
	}

	public void addSingleOutputNode(Output output) {
		connectWithRandomWeight(output);
		dataout.put(output, 0);
	}

	public void addManyOutputNodes(ArrayList<Output> outputs) {
		for (Output out : outputs) {
			addSingleOutputNode(out);
		}
	}

	public void doTick() {
		neuronValue.setValue("");
		for (Input in : datain.keySet()) {
			setNeuronValueInNode(in.getInformation(), NodeType.INPUT);
		}
		for (Neuron neuron : connectedNodes) {
			neuron.setNeuronValueInNode(neuron.neuronValue, NodeType.HIDDEN);
		}
		for (Output out : dataout.keySet()) {
			out.setValue(new Value(neuronValue.getValue()));
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

	public void randomizeNodeConnections(NNetwork parentNet) {
		Random rand = NNLib.GLOBAL_RANDOM;
		datain.clear();
		dataout.clear();
		for (int i = 0; i <= rand.nextInt(parentNet.getInputNodesInNetwork()
				.size()); i++) {
			addSingleInputNode(parentNet.getInputNodesInNetwork().get(i));
		}

		for (int i = 0; i <= rand.nextInt(parentNet.getOutputNodesInNetwork()
				.size()); i++) {
			addSingleOutputNode(parentNet.getOutputNodesInNetwork().get(i));
		}

		for (int i = 0; i <= rand.nextInt(parentNet.getNeuronsInNetwork()
				.size()); i++) {
			this.connectedNodes.add(parentNet.getNeuronsInNetwork().get(i));
			connectWithRandomWeight(parentNet.getNeuronsInNetwork().get(i));
		}

	}

	public void setNeuronValueInNode(Value value, NodeType senderType) {
		switch (senderType) {
		case HIDDEN:
			this.neuronValue = value;
			break;
		case INPUT:
			this.neuronValue = value;
			break;
		case OUTPUT:
			break;
		default:
			break;
		}
	}

	public ArrayList<Node> getConnectedNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.addAll(connectedNodes);
		nodes.addAll(datain.keySet());
		nodes.addAll(dataout.keySet());
		return nodes;
	}

	public void connectNeuronToNode(Node destination, int weight) {
		if (destination instanceof Input)
			addSingleInputNode((Input) destination);
		if (destination instanceof Output)
			addSingleOutputNode((Output) destination);
		if (destination instanceof Neuron)
			connectedNodes.add((Neuron) destination);
	}

}
