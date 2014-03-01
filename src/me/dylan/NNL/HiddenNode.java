package me.dylan.NNL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import me.dylan.NNL.NNLib.NodeType;
import me.dylan.NNL.Utils.StringUtil;

public class HiddenNode extends Node {
    public ArrayList<Output> dataout = new ArrayList<Output>();
    public ArrayList<Input> datain = new ArrayList<Input>();
    ArrayList<HiddenNode> connectedHiddenNodes = new ArrayList<HiddenNode>();
    int pulsesSeen = 0;

    public HiddenNode() {
	nodeVariety = NodeType.HIDDEN;
    }

    public void addSingleInputNode(Input input, NNetwork parentNet) {
	connectNodeToNode(input, NNLib.MAX_CONNECTION_WEIGHT / 2, parentNet);
	datain.add(input);
    }

    public void addManyInputNodes(ArrayList<Input> inputs, NNetwork parentNet) {
	for (Input i : inputs) {
	    addSingleInputNode(i, parentNet);
	}
    }

    public void addSingleOutputNode(Output output, NNetwork parentNet) {
	connectNodeToNode(output, NNLib.MAX_CONNECTION_WEIGHT / 2, parentNet);
	dataout.add(output);
    }

    public void addManyOutputNodes(ArrayList<Output> outputs, NNetwork parentNet) {
	for (Output out : outputs) {
	    addSingleOutputNode(out, parentNet);
	}
    }

    public void sendPulseToAppendData(Node sender, Synapse dataLine) {
	pulsesSeen++;

	// if (pulsesSeen % 2 == 0 && originalValue != null) {
	// setNodeInfo(originalValue.getValue());
	// return;
	// }
	String originalNodeInfo = getNodeInfo().getData();
	if (originalNodeInfo.length() + sender.getNodeInfo().getData().length() > maxDataStorage) {
	    String tmp = sender.getNodeInfo().getData();
	    getNodeInfo().setValue("");
	    for (int i = tmp.split(" ").length - 1; i > maxDataStorage; i--) {
		setNodeData(getNodeInfo().appendToValue(
			new Value(tmp.split(" ")[i])).getData());
	    }
	}
	sender.setNodeData(sender.originalValue.getData());
	spikeWithInput(dataLine);
	// setHiddenValueInNode(vInNode, sender.getNodeVariety());
	// if (sender.originalValue != null)
	// sender.setNodeInfo(sender.originalValue.getValue());
	for (Output out : dataout) {
	    out.putOrMove(getNodeInfo().getData());
	}
//	    sender.setActive(false);
	// setHiddenValueInNode(, sender.getNodeVariety());
    }

    public void doTick() {
	Collections.sort(getNodeConnections(), new Comparator<Synapse>() {

	    @Override
	    public int compare(Synapse synA, Synapse synB) {
		return (int) (synA.getSynapseWeight() - synB.getSynapseWeight());
	    }

	});
	int i = 0;
	Synapse outLine = null;
	while (outLine == null || outLine.hasPulsedInTick || outLine.getConnectionDestination().equals(this)) {
	    outLine = getNodeConnections().get(i);
	    if (i >= getNodeConnections().size())
		break;
	    i++;
	}
	if (outLine.getConnectionDestination().getNodeVariety() == NodeType.HIDDEN) {
	    if (this.isActive()) {
		outLine.getConnectionDestination().spikeWithInput(outLine);
		System.out.println("Pulsed to "+outLine.getConnectionDestination().getNodeInfo() + " from "+getNodeInfo());
//		((HiddenNode) outLine.getConnectionDestination())
		// .sendPulseToAppendData(this, outLine);
	    }
	} else if (outLine.getConnectionDestination().getNodeVariety() == NodeType.OUTPUT) {
	    if (this.isActive()) {
		outLine.hasPulsedInTick = true;
		System.out.println("Pulsed to "+outLine.getConnectionDestination().getNodeInfo() + " from "+getNodeInfo());
		String nodeValue = getNodeInfo().getData();
		((Output) outLine.getConnectionDestination())
			.putOrMove(nodeValue);
		// ((Output) synapse.getConnectionDestination())
		// .setNodeInfo(synapse.getConnectionDestination()
		// .getNodeInfo().getValue()
		// + getNodeInfo().getValue());
		// this.setActive(active);
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

}
