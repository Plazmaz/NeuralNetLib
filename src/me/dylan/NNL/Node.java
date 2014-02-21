package me.dylan.NNL;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import me.dylan.NNL.NNLib.NodeType;
import me.dylan.NNL.Visualizer.Display;
import me.dylan.NNL.Visualizer.NodePaint;

public class Node {
	private ArrayList<Synapse> connections = new ArrayList<Synapse>();
	protected NodeType nodeVariety;
	public static final int NODE_DRAW_SIZE = 15;
	public NodePaint graphicsRepresentationObject = new NodePaint(NODE_DRAW_SIZE,
			Color.RED);

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
				.nextInt(NNLib.MAX_NODE_WEIGHT) + 1));
	}

	public void connectNeuronToNode(Node destination, int weight) {
		this.connections.add(new Synapse(this, destination, weight));
	}

	public void paint(int x, int y) {
		graphicsRepresentationObject.paintNode(x, y);
		
	}
	public void setDisplayColor(Color c) {
		graphicsRepresentationObject.setColor(c);
	}
	public ArrayList<Synapse> getNodeConnections() {
		return connections;
	}
}
