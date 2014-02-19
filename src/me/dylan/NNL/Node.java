package me.dylan.NNL;

import java.util.ArrayList;

public class Node {
	private ArrayList<Synapse> connections = new ArrayList<Synapse>();
	
	/**
	 * Connect to a destination node with a randomized weight
	 * 
	 * @param destination
	 */
	public void connectRandom(Node destination) {
		connect(destination, NNLib.rand
				.nextInt(NNLib.MAX_NODE_WEIGHT) + 1);
	}

	public void connect(Node destination, int weight) {
		this.connections.add(new Synapse(this, destination, weight));
	}
	public ArrayList<Synapse> getConnections() {
		return connections;
	}
}
