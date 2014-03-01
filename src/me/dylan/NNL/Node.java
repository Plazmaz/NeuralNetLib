package me.dylan.NNL;

import java.awt.Color;
import java.util.ArrayList;

import me.dylan.NNL.NNLib.NodeType;
import me.dylan.NNL.Utils.StringUtil;
import me.dylan.NNL.Visualizer.NodePaint;

public class Node {
    private ArrayList<Synapse> connections = new ArrayList<Synapse>();
    protected NodeType nodeVariety;
    public static final int NODE_DRAW_SIZE = 10;
    int maxDataStorage = 5000; // this is in words delimited by spaces
    public NodePaint graphicsRepresentationObject = new NodePaint(
	    NODE_DRAW_SIZE, Color.GREEN);
    protected Value information = new Value();
    protected Value originalValue;
    private boolean active = false;

    public NodeType getNodeVariety() {
	return nodeVariety;
    }

    /**
     * Connect to a destination node with a randomized weight
     * 
     * @param destination
     */
    public void connectWithRandomWeight(Node destination, NNetwork parentNetwork) {
	int weight = NNLib.GLOBAL_RANDOM.nextInt(NNLib.MAX_CONNECTION_WEIGHT);
	if (weight > NNLib.MAX_CONNECTION_WEIGHT) {
	    weight = NNLib.MAX_CONNECTION_WEIGHT;
	}
	Synapse connection = new Synapse(this, destination, weight,
		parentNetwork.desiredOutput);
	this.connections.add(connection);
	destination.connections.add(connection);
	parentNetwork.addSynapse(connection);
    }

    public void connectNodeToNode(Node destination, double weight,
	    NNetwork parentNetwork) {
	if (weight > NNLib.MAX_CONNECTION_WEIGHT) {
	    weight = NNLib.MAX_CONNECTION_WEIGHT;
	}
	Synapse connection = new Synapse(this, destination, weight,
		parentNetwork.desiredOutput);
	this.connections.add(connection);
	parentNetwork.addSynapse(connection);
	destination.connections.add(connection);
    }

    public void disconnectNode(Synapse bridge) {
	bridge.getConnectionDestination().connections.remove(bridge);
	bridge.getConnectionOrigin().connections.remove(bridge);
    }

    public void disconnectNode(Node nodeToDisconnect) {
	ArrayList<Synapse> synapsesClone = (ArrayList<Synapse>) connections
		.clone();
	for (Synapse nodeBridge : synapsesClone) {
	    if (nodeBridge.getConnectionDestination().equals(nodeToDisconnect)) {
		nodeBridge.getConnectionOrigin().disconnectNode(nodeBridge);
		connections.remove(nodeBridge);
	    }
	}
    }

    // public void paint(int x, int y, int maxnodes) {
    // if(this.nodeVariety == NodeType.OUTPUT) {
    // graphicsRepresentationObject.paintNode(x, y, Color.MAGENTA);
    // } else if(this.nodeVariety == NodeType.INPUT) {
    // graphicsRepresentationObject.paintNode(x, y, Color.YELLOW);
    // } else {
    // graphicsRepresentationObject.paintNode(x, y, NetworkUtil
    // .returnNodeWeightColor(getNodeConnections().size(), maxnodes));
    // }
    //
    // }
    public void paint(int x, int y) {
	if (this.nodeVariety == NodeType.OUTPUT) {
	    graphicsRepresentationObject.setColor(Color.MAGENTA);
	} else if (this.nodeVariety == NodeType.INPUT) {
	    graphicsRepresentationObject.setColor(Color.YELLOW);
	} else {
	    graphicsRepresentationObject.setColor(Color.GRAY);
	}
	graphicsRepresentationObject.paintNode(x, y, this);
	//
    }

    public void setDisplayColor(Color c) {
	graphicsRepresentationObject.setColor(c);
    }

    public ArrayList<Synapse> getNodeConnections() {
	return connections;
    }

    public Value getNodeInfo() {
	return information;
    }

    public void setNodeData(String info) {
	this.information = new Value(info);
	if (this.originalValue == null) {
	    this.originalValue = new Value(info);
	}
    }

    public ArrayList<Node> traceBack(boolean backwards) {
	ArrayList<Node> trace = new ArrayList<Node>();
	trace.add(this);
	for (Synapse connection : getNodeConnections()) {
	    Node node = backwards ? connection.getConnectionDestination()
		    : connection.getConnectionOrigin();
	    if (!trace.contains(node))
		trace.addAll(node.traceBack(backwards));
	}
	return trace;
    }

    public ArrayList<Synapse> traceBackSynapses(boolean backwards,
	    NNetwork parentNetwork) {
	ArrayList<Synapse> validConnections = new ArrayList<Synapse>();
	parentNetwork.removeUnusedSynapses();
	for (Synapse synapse : parentNetwork.getNetworkSynapses()) {
	    if ((!backwards && synapse.getConnectionOrigin().equals(
		    synapse.getConnectionOrigin()))
		    || (backwards && synapse.getConnectionDestination().equals(
			    synapse.getConnectionDestination()))) {
		validConnections.add(synapse);
	    }
	}
	return validConnections;
    }

    public void spikeWithInput(Synapse dataLine) {
//	if(dataLine.getConnectionOrigin().equals(this))
//	    return;
	if(dataLine.hasPulsedInTick)
	    return;
	String originalNodeInfo = getNodeInfo().getData();
	double mostRecentStringMatchPercentage = StringUtil
		.calculateStringSimilarityPercentage(this.getNodeInfo()
			.getData(), dataLine.desiredOutput);
	if (mostRecentStringMatchPercentage < dataLine.percentMatchAtOrigin) {
	    dataLine.setSynapseWeight(dataLine.getSynapseWeight()
		    - NNLib.WEIGHT_DECREASE_ON_MISMATCH);
	    setNodeData(originalNodeInfo);
	    System.out.println("Traced Back.");
	    return;
	}
	dataLine.hasPulsedInTick = true;
	dataLine.setSynapseWeight(dataLine.getSynapseWeight() + NNLib.WEIGHT_INCREASE_ON_MATCH);
	setActive(true);
    }
    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }
    // public ArrayList<Synapse> traceBackSynapses(boolean backwards) {
    // boolean hasConnection = false;
    // ArrayList<Synapse> trace = new ArrayList<Synapse>();
    // for (Synapse connection : getNodeConnections()) {
    // hasConnection = true;
    // Node node = backwards ? connection.getConnectionDestination()
    // : connection.getConnectionOrigin();
    // if (!trace.contains(connection)) {
    // trace.add(connection);
    // ArrayList<Synapse> returnTrace = node.traceBackSynapses(backwards);
    // if(returnTrace.isEmpty())
    // return trace;
    // trace.addAll(returnTrace);
    // }
    // }
    // if(!hasConnection)
    // return null;
    // return trace;
    // }
}
