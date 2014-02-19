package me.dylan.NNL;

import java.util.ArrayList;

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
		for(Input input : inputs) {
			addInput(input);
		}
	}
	
	public void addManyOutputs(ArrayList<Output> outputs) {
		for(Output output : outputs) {
			addOutput(output);
		}
	}
	
	public void addManyNeurons(ArrayList<Neuron> neurons) {
		for(Neuron neuron : neurons) {
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
			for (Neuron n : hidden.connected) {
				n.randomize(this);
			}
		}
	}
}
