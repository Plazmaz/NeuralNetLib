package me.dylan.NNL;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import me.dylan.NNL.NNLib.NodeType;
import me.dylan.NNL.Utils.NetworkUtil;
import me.dylan.NNL.Visualizer.Display;
import me.dylan.NNL.Visualizer.NodePaint;

public class Node {
	private ArrayList<Synapse> connections = new ArrayList<Synapse>();
	protected NodeType nodeVariety;
	public static final int NODE_DRAW_SIZE = 10;
	public NodePaint graphicsRepresentationObject = new NodePaint(
			NODE_DRAW_SIZE, Color.GREEN);

	public NodeType getNodeVariety() {
		return nodeVariety;
	}

	/**
	 * Connect to a destination node with a randomized weight
	 * 
	 * @param destination
	 */
	public void connectWithRandomWeight(Node destination) {
		this.connections.add(new Synapse(this, destination, NNLib.GLOBAL_RANDOM
				.nextInt(NNLib.MAX_CONNECTION_WEIGHT)));
	}

	public void connectNodeToNode(Node destination, int weight) {
		this.connections.add(new Synapse(this, destination, weight));
		destination.connections.add(new Synapse(destination, this, weight));
	}

//	public void paint(int x, int y, int maxnodes) {
//		if(this.nodeVariety == NodeType.OUTPUT) {
//			graphicsRepresentationObject.paintNode(x, y, Color.MAGENTA);
//		} else if(this.nodeVariety == NodeType.INPUT) {
//			graphicsRepresentationObject.paintNode(x, y, Color.YELLOW);
//		} else {
//			graphicsRepresentationObject.paintNode(x, y, NetworkUtil
//					.returnNodeWeightColor(getNodeConnections().size(), maxnodes));
//		}
//
//	}
	public void paint(int x, int y) {
		if(this.nodeVariety == NodeType.OUTPUT) {
			graphicsRepresentationObject.paintNode(x, y, Color.MAGENTA);
		} else if(this.nodeVariety == NodeType.INPUT) {
			graphicsRepresentationObject.paintNode(x, y, Color.YELLOW);
		} else {
			Color c = Color.WHITE;
			graphicsRepresentationObject.paintNode(x, y, c);
		}
//		
	}

	public void setDisplayColor(Color c) {
		graphicsRepresentationObject.setColor(c);
	}

	public ArrayList<Synapse> getNodeConnections() {
		return connections;
	}
}
