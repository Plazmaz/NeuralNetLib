package me.dylan.NNL;

import java.util.ArrayList;
import java.util.Random;

import me.dylan.NNL.NNLib.NodeType;

public class HiddenNode extends Node {
    public ArrayList<Output> dataout = new ArrayList<Output>();
    public ArrayList<Input> datain = new ArrayList<Input>();
    ArrayList<HiddenNode> connectedHiddenNodes = new ArrayList<HiddenNode>();
    private boolean active = false;
    int pulsesSeen = 0;

    public HiddenNode() {
	nodeVariety = NodeType.HIDDEN;
    }

    public void addSingleInputNode(Input input, NNetwork parentNet) {
	connectWithRandomWeight(input, parentNet);
	datain.add(input);
    }

    public void addManyInputNodes(ArrayList<Input> inputs, NNetwork parentNet) {
	for (Input i : inputs) {
	    addSingleInputNode(i, parentNet);
	}
    }

    public void addSingleOutputNode(Output output, NNetwork parentNet) {
	connectWithRandomWeight(output, parentNet);
	dataout.add(output);
    }

    public void addManyOutputNodes(ArrayList<Output> outputs, NNetwork parentNet) {
	for (Output out : outputs) {
	    addSingleOutputNode(out, parentNet);
	}
    }

    public void sendPulseToAppendData(Node sender) {
	pulsesSeen++;
	// if (pulsesSeen % 2 == 0 && originalValue != null) {
	// setNodeInfo(originalValue.getValue());
	// return;
	// }

	if (getNodeInfo().getValue().length()
		+ sender.getNodeInfo().getValue().length() > maxDataStorage) {
	    String tmp = sender.getNodeInfo().getValue();
	    getNodeInfo().setValue("");
	    for (int i = tmp.split(" ").length - 1; i > maxDataStorage; i--) {
		setNodeInfo(getNodeInfo().appendToValue(
			new Value(tmp.split(" ")[i])).getValue());
	    }
	}
	// Value vInNode = getNodeInfo().appendToValue(sender.getNodeInfo());
	// sender.setNodeInfo("");
	setActive(true);
	// setHiddenValueInNode(vInNode, sender.getNodeVariety());
	// if (sender.originalValue != null)
	// sender.setNodeInfo(sender.originalValue.getValue());
	for (Output out : dataout) {
	    out.setNodeInfo(getNodeInfo().getValue());
	}
	if (sender.getNodeVariety() == NodeType.HIDDEN)
	    ((HiddenNode) sender).setActive(false);
	// setHiddenValueInNode(, sender.getNodeVariety());
    }

    public void doTick() {
	for (Synapse synapse : getNodeConnections()) {
	    if (synapse.getConnectionOrigin().getNodeVariety() == NodeType.INPUT) {
		Input in = (Input) synapse.getConnectionOrigin();
		if (in.active) {
		    this.setActive(true);
		    // in.active = false;
		}
	    } else if (synapse.getConnectionDestination().getNodeVariety() == NodeType.HIDDEN) {
		if (this.isActive())
		    ((HiddenNode) synapse.getConnectionDestination())
			    .sendPulseToAppendData(this);
	    } else if (synapse.getConnectionDestination().getNodeVariety() == NodeType.OUTPUT) {
		if (this.isActive())
		    ((Output) synapse.getConnectionDestination())
			    .setNodeInfo(getNodeInfo().getValue());
	    }
	}
    }

    public void randomizeNodeConnections(NNetwork parentNet) {
	Random rand = NNLib.GLOBAL_RANDOM;
	for (Input in : datain) {
	    in.disconnectNode(this);
	}
	for (Output out : dataout) {
	    out.disconnectNode(this);
	}
	// datain.clear();
	// dataout.clear();
	connectedHiddenNodes.clear();
	if (!parentNet.getInputNodesInNetwork().isEmpty()) {
	    for (int i = 0; i < parentNet.getInputNodesInNetwork().size(); i++) {
		if (rand.nextInt(101) <= NNLib.CHANCE_FOR_IO_CONNECTION
			|| parentNet.getInputNodesInNetwork().get(i)
				.getNodeConnections().isEmpty()) {

		    addSingleInputNode(parentNet.getInputNodesInNetwork()
			    .get(i), parentNet);
		}
	    }
	}

	if (!parentNet.getOutputNodesInNetwork().isEmpty()) {
	    for (int i = 0; i < parentNet.getOutputNodesInNetwork().size(); i++) {
		if (rand.nextInt(101) <= NNLib.CHANCE_FOR_IO_CONNECTION
			|| parentNet.getOutputNodesInNetwork().get(i)
				.getNodeConnections().isEmpty()) {

		    addSingleOutputNode(parentNet.getOutputNodesInNetwork()
			    .get(i), parentNet);
		}
	    }
	}
	if (!parentNet.getHiddenNodesInNetwork().isEmpty())
	    for (int i = 0; i < parentNet.getHiddenNodesInNetwork().size(); i++) {
		if (rand.nextInt(101) <= NNLib.CHANCE_FOR_HIDDEN_CONNECTION)
		    connectWithRandomWeight(parentNet.getHiddenNodesInNetwork()
			    .get(i), parentNet);
	    }

    }

    public void setHiddenValueInNode(Value value, NodeType senderType) {
	switch (senderType) {
	case HIDDEN:
	    this.information = value;
	    break;
	case INPUT:
	    this.information = value;
	    break;
	case OUTPUT:
	    break;
	default:
	    break;
	}
    }

    public ArrayList<Node> getConnectedNodes() {
	ArrayList<Node> nodes = new ArrayList<Node>();
	nodes.addAll(connectedHiddenNodes);
	nodes.addAll(datain);
	nodes.addAll(dataout);
	return nodes;
    }

    // @Override
    // public void connectNodeToNode(Node destination, double d) {
    // if (destination instanceof Input)
    // addSingleInputNode((Input) destination, parentNet);
    // if (destination instanceof Output)
    // dataout.add((Output)destination)
    // if (destination instanceof HiddenNode)
    // connectedHiddenNodes.add((HiddenNode) destination);
    // }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

}
