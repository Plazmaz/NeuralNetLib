package me.dylan.NNL;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public int pulsesSeen;

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

    public void sendPulseToAppendData(Synapse dataLine) {
	pulsesSeen++;

	// if (pulsesSeen % 2 == 0 && originalValue != null) {
	// setNodeInfo(originalValue.getValue());
	// return;
	// }
	Node sender = dataLine.getConnectionOrigin();
	String originalNodeInfo = getNodeInfo().getData();
	if (originalNodeInfo.length() + sender.getNodeInfo().getData().length() > maxDataStorage) {
	    String tmp = sender.getNodeInfo().getData();
	    getNodeInfo().setValue("");
	    for (int i = tmp.split(" ").length - 1; i > maxDataStorage; i--) {
		setNodeData(getNodeInfo().appendToValue(
			new Value(tmp.split(" ")[i])).getData());
	    }

	}
	// dataLine.setPulseBack(!dataLine.doesPulseBack());
	// sender.setNodeData(sender.originalValue.getData());
	spikeWithInput(dataLine);
	setActive(true);
	// dataLine.setPulseBack(!dataLine.doesPulseBack());
	// setHiddenValueInNode(vInNode, sender.getNodeVariety());
	// if (sender.originalValue != null)
	// sender.setNodeInfo(sender.originalValue.getValue());
	// sender.setActive(false);
	// setHiddenValueInNode(, sender.getNodeVariety());
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
	if (dataLine.hasPulsedInTick)
	    return;
	String originalNodeInfo = getNodeInfo().getData();
	double mostRecentStringMatchPercentage = StringUtil
		.calculateStringSimilarityPercentage(getNodeInfo().getData(),
			dataLine.desiredOutput);
	Synapse newDataLine;
	if (mostRecentStringMatchPercentage < dataLine.percentMatchAtOrigin) {
//	    if (!dataLine.doesPulseBack())
//		dataLine.setPulseBack(true);
	    dataLine.setPulseBack(!dataLine.doesPulseBack());
	    dataLine.setSynapseWeight(dataLine.getSynapseWeight()
		    - NNLib.WEIGHT_DECREASE_ON_MISMATCH);
	    setNodeData(originalNodeInfo);
	    cleanupDamage(dataLine);
	    dataLine.getConnectionOrigin().setActive(false);
	    dataLine.hasPulsedInTick = true;
	    sortConnectionsByWeight();
	    newDataLine = getNodeConnections().get(0);
	    newDataLine.getConnectionDestination().setActive(true);
	    System.out.println("Traced Back.");
	} else {
	    dataLine.hasPulsedInTick = true;
	    dataLine.setSynapseWeight(dataLine.getSynapseWeight()
		    + NNLib.WEIGHT_INCREASE_ON_MATCH);
	    dataLine.getConnectionOrigin().setActive(false);
	    sortConnectionsByWeight();
	    newDataLine = getNodeConnections().get(0);
	     newDataLine.getConnectionDestination().setActive(true);
	}
    }

    public void cleanupDamage(Synapse connectionToIgnore) {

	for (Synapse netConnection : getNodeConnections()) {
	    if (!netConnection.equals(connectionToIgnore)) {
		netConnection.hasPulsedInTick = false;
		 netConnection.setPulseBack(false);
	    }
	}
    }

    public void sortConnectionsByWeight() {
	Collections.sort(getNodeConnections(), new Comparator<Synapse>() {

	    @Override
	    public int compare(Synapse synA, Synapse synB) {
		return (int) (synA.getSynapseWeight() - synB.getSynapseWeight());
	    }

	});
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
