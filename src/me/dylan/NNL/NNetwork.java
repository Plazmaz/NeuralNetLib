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
	private ArrayList<Neuron> networkNeurons = new ArrayList<Neuron>();
	private ArrayList<Synapse> networkSynapses = new ArrayList<Synapse>();
	private ArrayList<Input> networkInputs = new ArrayList<Input>();
	private ArrayList<Output> networkoutputs = new ArrayList<Output>();
	private boolean isLearningMode = false;

	public NNetwork(ArrayList<Input> inputs, ArrayList<Neuron> connections,
			ArrayList<Output> outputs) {
		this.networkNeurons = connections;
		this.networkInputs = inputs;
		this.networkoutputs = outputs;
	}

	public NNetwork() {
	}

	public void addOutputNodeToNetwork(Output out) {
		networkoutputs.add(out);
	}

	public void addInputNodeToNetwork(Input in) {
		networkInputs.add(in);
	}

	public void addManyInputNodesToNetwork(ArrayList<Input> inputs) {
		for (Input input : inputs) {
			addInputNodeToNetwork(input);
		}
	}

	public void addManyOutputNodesToNetwork(ArrayList<Output> outputs) {
		for (Output output : outputs) {
			addOutputNodeToNetwork(output);
		}
	}

	public void addManyNeuronsToNetwork(ArrayList<Neuron> neurons) {
		for (Neuron neuron : neurons) {
			addNeuronToNetwork(neuron);
		}
	}

	public ArrayList<Node> getNodesInNetwork() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.addAll(networkNeurons);
		nodes.addAll(networkInputs);
		nodes.addAll(networkoutputs);
		return nodes;
	}

	public ArrayList<Neuron> getNeuronsInNetwork() {
		return networkNeurons;
	}

	public ArrayList<Input> getInputNodesInNetwork() {
		return networkInputs;
	}

	public ArrayList<Output> getOutputNodesInNetwork() {
		return networkoutputs;
	}

	public void addNeuronToNetwork(Neuron neuron) {
		for(Synapse synapse : neuron.getNodeConnections()) {
			if (!networkSynapses.contains(synapse))
				networkSynapses.add(synapse);			
		}
		networkNeurons.add(neuron);
	}

	public void randomizeConnections() {
		networkSynapses.clear();
		for (Neuron hidden : networkNeurons) {
			hidden.randomizeNodeConnections(this);
			ArrayList<Neuron> connected = (ArrayList<Neuron>) hidden.connectedNeurons
					.clone();
			for (Neuron n : connected) {
				if(n.equals(hidden))
					continue;
				n.randomizeNodeConnections(this);
				for (Synapse synapse : n.getNodeConnections()) {
					if (!networkSynapses.contains(synapse))
						networkSynapses.add(synapse);
				}
			}
		}
	}

	public String getNetworkOutput() {
		String result = "";
		for (Output out : getOutputNodesInNetwork()) {
			result += out.getOutputValue().data;
		}
		return result;
	}

	public int getNetworkErrorPercentage(String desiredOutput) {
		return StringUtil.calculateStringDifferenceCount(getNetworkOutput(),
				desiredOutput) * 100;
	}

	public boolean isNetworkInLearningMode() {
		return isLearningMode;
	}

	public void setNetworkLearningMode(boolean isLearningMode) {
		this.isLearningMode = isLearningMode;
	}

	public ArrayList<Synapse> getNetworkSynapses() {
		return networkSynapses;
	}

	public void setNetworkSynapses(ArrayList<Synapse> networkSynapses) {
		this.networkSynapses = networkSynapses;
	}
}
