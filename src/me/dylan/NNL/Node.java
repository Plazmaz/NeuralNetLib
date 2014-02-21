package me.dylan.NNL;

import java.util.ArrayList;

public class Node {
	private ArrayList<Synapse> connections = new ArrayList<Synapse>();
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
	public ArrayList<Synapse> getNodeConnections() {
		return connections;
	}
}
