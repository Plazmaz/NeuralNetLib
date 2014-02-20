package me.dylan.NNL;

import java.util.ArrayList;

import me.dylan.NNL.Utils.StringUtil;

/**
 * A wrapper around an entire neural network.
 * 
 * @author Dylan
 * 
 */
public class NNetwork {
	private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	ArrayList<Synapse> synapses = new ArrayList<Synapse>();
	private ArrayList<Input> inputs = new ArrayList<Input>();
	private ArrayList<Output> outputs = new ArrayList<Output>();
	private boolean isLearningMode = false;

	public NNetwork(ArrayList<Input> inputs, ArrayList<Neuron> connections,
			ArrayList<Output> outputs) {
		this.neurons = connections;
		this.inputs = inputs;
		this.outputs = outputs;
	}

	public NNetwork() {
	}

	public void addOutput(Output out) {
		outputs.add(out);
	}

	public void addInput(Input in) {
		inputs.add(in);
	}

	public void addManyInputs(ArrayList<Input> inputs) {
		for (Input input : inputs) {
			addInput(input);
		}
	}

	public void addManyOutputs(ArrayList<Output> outputs) {
		for (Output output : outputs) {
			addOutput(output);
		}
	}

	public void addManyNeurons(ArrayList<Neuron> neurons) {
		for (Neuron neuron : neurons) {
			addNeuron(neuron);
		}
	}

	public ArrayList<Node> getNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.addAll(neurons);
		nodes.addAll(inputs);
		nodes.addAll(outputs);
		return nodes;
	}

	public ArrayList<Neuron> getNeurons() {
		return neurons;
	}

	public ArrayList<Input> getInputs() {
		return inputs;
	}

	public ArrayList<Output> getOutputs() {
		return outputs;
	}

	public void addNeuron(Neuron neuron) {
		neurons.add(neuron);
	}

	public void randomize() {
		for (Neuron hidden : neurons) {
			hidden.randomize(this);
			ArrayList<Neuron> connected = (ArrayList<Neuron>) hidden.connected.clone();
			for (Neuron n : connected) {
				n.randomize(this);
			}
		}
	}

	public String getResult() {
		String result = "";
		for (Output out : getOutputs()) {
			result += out.getValue().data;
		}
		return result;
	}

	public int getErrorPercentage(String desiredOutput) {
		return StringUtil.calculateStringDifference(getResult(), desiredOutput) * 100;
	}

	public boolean isLearningMode() {
		return isLearningMode;
	}

	public void setLearningMode(boolean isLearningMode) {
		this.isLearningMode = isLearningMode;
	}
}
