package me.dylan.NNL;

import java.util.ArrayList;

import me.dylan.NNL.NNLib.NodeType;
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
    private ArrayList<Output> networkOutputs = new ArrayList<Output>();
    private boolean isLearningMode = false;
    String desiredOutput = "";

    public NNetwork(ArrayList<Input> inputs, ArrayList<HiddenNode> connections,
	    ArrayList<Output> outputs, String desiredOutput) {
	this.networkHidden = connections;
	this.networkInputs = inputs;
	this.networkOutputs = outputs;
	this.desiredOutput = desiredOutput;
    }

    public NNetwork(String desiredOutput) {
	this.desiredOutput = desiredOutput;
    }

    public void addOutputNodeToNetwork(Output out) {
	networkOutputs.add(out);
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

    public void addManyHiddenNodesToNetwork(ArrayList<HiddenNode> HiddenNodes) {
	for (HiddenNode Hidden : HiddenNodes) {
	    addHiddenNodeToNetwork(Hidden);
	}
    }

    public ArrayList<Node> getNodesInNetwork() {
	ArrayList<Node> nodes = new ArrayList<Node>();
	nodes.addAll(networkHidden);
	nodes.addAll(networkInputs);
	nodes.addAll(networkOutputs);
	return nodes;
    }

    public ArrayList<HiddenNode> getHiddenNodesInNetwork() {
	return networkHidden;
    }

    public ArrayList<Input> getInputNodesInNetwork() {
	return networkInputs;
    }

    public ArrayList<Output> getOutputNodesInNetwork() {
	return networkOutputs;
    }

    public void addHiddenNodeToNetwork(HiddenNode Hidden) {
	for (Synapse synapse : Hidden.getNodeConnections()) {
	    if (!networkSynapses.contains(synapse))
		networkSynapses.add(synapse);
	}
	networkHidden.add(Hidden);
    }

    @Deprecated
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

    public void connectAll() {
	for (Node node : getNodesInNetwork()) {
	    for (Node node2 : getNodesInNetwork()) {
		boolean allowProgression = !(node.getNodeVariety() == NodeType.INPUT || node2
			.getNodeVariety() == NodeType.INPUT)
			&& !(node.getNodeVariety() == NodeType.OUTPUT || node2
				.getNodeVariety() == NodeType.OUTPUT)
			&& (node.getNodeVariety() != node2.getNodeVariety() || node
				.getNodeVariety() == NodeType.HIDDEN)
			&& !node.equals(node2);

		if (allowProgression)
		    node.connectNodeToNode(node2,
			    NNLib.MAX_CONNECTION_WEIGHT / 2, this);
	    }
	    for (Input inNode : getInputNodesInNetwork()) {
		if (inNode.getNodeVariety() != node.getNodeVariety()
			&& node.getNodeVariety() != NodeType.OUTPUT) {
		    
		    node.connectNodeToNode(inNode,
			    NNLib.MAX_CONNECTION_WEIGHT / 2, this);
		    if(node.getNodeVariety() == NodeType.INPUT)
			System.out.println();
		}
	    }
	}

    }

    public String getNetworkOutput() {
	String result = "";
	for (Output out : getOutputNodesInNetwork()) {
	    String outPulse = out.getOutputValue().getData();
	    if (!outPulse.isEmpty())
		result += "|||||" + outPulse;
	}
	return result;
    }

    public double getNetworkSimilarityPercentage() {
	String netOut = getNetworkOutput();
	if (netOut.isEmpty())
	    return 0;
	double percentMatch = StringUtil.calculateStringSimilarityPercentage(
		netOut, desiredOutput);
	return percentMatch * 100;
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

    public void removeUnusedSynapses() {
	ArrayList<Synapse> netSynapsesClone = (ArrayList<Synapse>) getNetworkSynapses()
		.clone();
	for (Synapse synapse : netSynapsesClone) {
	    if (synapse == null) {
		removeSynapse(synapse);
		continue;
	    }
	    if (synapse.getConnectionDestination() == null
		    || synapse.getConnectionOrigin() == null) {
		removeSynapse(synapse);
	    }
	}
    }

    public void clearSynapses() {
	networkSynapses.clear();
    }

    public void addSynapse(Synapse synapse) {
	networkSynapses.add(synapse);
    }

    public void removeSynapse(Synapse connection) {
	if (connection != null)
	    connection.severConnections();
	networkSynapses.remove(connection);
    }
}
