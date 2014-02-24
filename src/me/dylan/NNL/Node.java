package me.dylan.NNL;

import java.awt.Color;
import java.util.ArrayList;

import me.dylan.NNL.NNLib.NodeType;
import me.dylan.NNL.Visualizer.NodePaint;

public class Node {
    private ArrayList<Synapse> connections = new ArrayList<Synapse>();
    protected NodeType nodeVariety;
    public static final int NODE_DRAW_SIZE = 10;
    public NodePaint graphicsRepresentationObject = new NodePaint(
	    NODE_DRAW_SIZE, Color.GREEN);
    protected Value information = new Value();

    public NodeType getNodeVariety() {
	return nodeVariety;
    }

    /**
     * Connect to a destination node with a randomized weight
     * 
     * @param destination
     */
    public void connectWithRandomWeight(Node destination) {
	int weight = NNLib.GLOBAL_RANDOM.nextInt(NNLib.MAX_CONNECTION_WEIGHT);
	if (weight > NNLib.MAX_CONNECTION_WEIGHT) {
	    weight = NNLib.MAX_CONNECTION_WEIGHT;
	}
	this.connections.add(new Synapse(this, destination, weight));
	destination.connections.add(new Synapse(destination, this, weight));
    }

    public void connectNodeToNode(Node destination, int weight) {
	if (weight > NNLib.MAX_CONNECTION_WEIGHT) {
	    weight = NNLib.MAX_CONNECTION_WEIGHT;
	}
	this.connections.add(new Synapse(this, destination, weight));
	destination.connections.add(new Synapse(destination, this, weight));
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

    public void setNodeInfo(String info) {
	this.information = new Value(info);
    }
}
