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
	private ArrayList<HiddenNode> networkHidden = new ArrayList<HiddenNode>();
	private ArrayList<Synapse> networkSynapses = new ArrayList<Synapse>();
	private ArrayList<Input> networkInputs = new ArrayList<Input>();
	private ArrayList<Output> networkoutputs = new ArrayList<Output>();
	private boolean isLearningMode = false;

	public NNetwork(ArrayList<Input> inputs, ArrayList<HiddenNode> connections,
			ArrayList<Output> outputs) {
		this.networkHidden = connections;
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

	public void addManyHiddenNodesToNetwork(ArrayList<HiddenNode> Hiddens) {
		for (HiddenNode Hidden : Hiddens) {
			addHiddenNodeToNetwork(Hidden);
		}
	}

	public ArrayList<Node> getNodesInNetwork() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.addAll(networkHidden);
		nodes.addAll(networkInputs);
		nodes.addAll(networkoutputs);
		return nodes;
	}

	public ArrayList<HiddenNode> getHiddenNodesInNetwork() {
		return networkHidden;
	}

	public ArrayList<Input> getInputNodesInNetwork() {
		return networkInputs;
	}

	public ArrayList<Output> getOutputNodesInNetwork() {
		return networkoutputs;
	}

	public void addHiddenNodeToNetwork(HiddenNode Hidden) {
		for(Synapse synapse : Hidden.getNodeConnections()) {
			if (!networkSynapses.contains(synapse))
				networkSynapses.add(synapse);			
		}
		networkHidden.add(Hidden);
	}

	public void randomizeConnections() {
		networkSynapses.clear();
		for (HiddenNode hidden : networkHidden) {
				hidden.randomizeNodeConnections(this);
				for (Synapse synapse : hidden.getNodeConnections()) {
					if (!networkSynapses.contains(synapse))
						networkSynapses.add(synapse);
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
