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
	public static int NODE_COUNT = 0;
	int maxDataStorage = 5000; // this is in words delimited by spaces
	public NodePaint graphicsRepresentationObject = new NodePaint(
			NODE_DRAW_SIZE, Color.GREEN);
	protected Value information = new Value();
	protected Value originalValue;
	private boolean active = false;
	public int pulsesSeen;
	public int nodeID = 0;

	public NodeType getNodeVariety() {
		return nodeVariety;
	}

	public Node() {
		nodeID = NODE_COUNT++;
	}

	/**
	 * Connect to a destination node with a randomized weight
	 * 
	 * @param destination
	 *            The node to be connected
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

	/**
	 * Connects the nodes to each other
	 * 
	 * @param destination
	 *            TODO: Is destination being used to represent the node its
	 *            being connected to? If so, why do we not take in the origin
	 *            node as well?
	 * @param weight
	 *            The value that will be used to determine the weight of the
	 *            synapse connecting the nodes
	 * @param parentNetwork
	 *            The network that the node will be added to.
	 */
	// TODO: DONT WE CONNECT NODES ELSEWHERE?
	// TODO: WE SHOULD BE WEIGHT CHECKING ELSEWHERE -- WHY HERE?
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

	/**
	 * Takes the passed synapse and disconnects it from the two nodes its
	 * connected to
	 * 
	 * @param bridge
	 *            The synapse that is to be disconnected on both sides
	 */
	public void disconnectNode(Synapse bridge) {
		bridge.getConnectionDestination().connections.remove(bridge);
		bridge.getConnectionOrigin().connections.remove(bridge);
	}

	/**
	 * Takes the passed node and disconnects it from all other nodes by
	 * disconnecting the synapses
	 * 
	 * @param nodeToDisconnect
	 *            The node that is to be disconnected from all other nodes
	 */
	// TODO: If we are disconnecting this node from all other nodes, shouldn't
	// we delete the node?
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

	/**
	 * Returns an arraylist of all connections that are associated with the node
	 * 
	 * @return connections The arraylist that contains all of the current
	 *         connections for the node.
	 */
	public ArrayList<Synapse> getNodeConnections() {
		return connections;
	}

	// TODO: Not sure what information is.. and not sure where it gets set. Not
	// sure what the "Value" type is either.
	public Value getNodeInfo() {
		return information;
	}

	/**
	 * Takes in the information that will be assigned to a hidden node. Checks
	 * to make sure that the node does not currently have a value, then assigns
	 * it the value of info
	 * 
	 * @param info
	 *            The string containing the information that the node will
	 *            contain
	 */
	public void setNodeData(String info) {
		this.information = new Value(info);
		if (this.originalValue == null) {
			this.originalValue = new Value(info);
		}
	}
	/**
	 * 
	 * @param backwards
	 * @return
	 */
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
	/**
	 * 
	 * @param backwards
	 * @param parentNetwork
	 * @return
	 */
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

	/**
	 * Determines the best node to pulse to, based on weight, and then pushes
	 * the data from itself to that node. The node that is determined to have
	 * the highest weight is activated and then the data is pushed to it.
	 * Afterwards, the sending node, is deactivated
	 * 
	 * @param dataLine
	 *            The current synapse that is being checked, and then assigned
	 *            data, to carry to the next node
	 */

	// TODO: SHOULDNT WE BE CHECKING IF THE NODE HAS PULSED, NOT THE SYNAPSE
	// LINE? -- MAY BE ISSUE BECAUSE SYNAPSES CAN PULSE BOTH WAYS, SO IT COULD
	// HAVE PULSED BUT NEEDS TO ALSO RECEIVE A PULSE
	public void spikeWithInput(Synapse dataLine) {
		if (dataLine.hasPulsedInTick)
			return;
		String originalNodeInfo = getNodeInfo().getData();
		double mostRecentStringMatchPercentage = StringUtil
				.calculateStringSimilarityPercentage(getNodeInfo().getData(),
						dataLine.desiredOutput);
		Synapse newDataLine;
		if (mostRecentStringMatchPercentage < dataLine.percentMatchAtOrigin) {
			if (!dataLine.doesPulseBack()) {
				dataLine.setPulseBack(true);
			}
			// dataLine.setPulseBack(!dataLine.doesPulseBack())

			// TODO: Pretty sure we could combine from here down to the 'TO
			// HERE' into a function. Lots of repeated code
			dataLine.setSynapseWeight(dataLine.getSynapseWeight()
					- NNLib.WEIGHT_DECREASE_ON_MISMATCH);
			setNodeData(originalNodeInfo);
			cleanupDamage(dataLine);
			dataLine.hasPulsedInTick = true;
			sortConnectionsByWeight();
			newDataLine = getNodeConnections().get(0);
			newDataLine.getConnectionOrigin().setActive(false);
			newDataLine.getConnectionDestination().setActive(true);
			System.out.println("Traced Back.");
		} else {
			dataLine.hasPulsedInTick = true;
			dataLine.setSynapseWeight(dataLine.getSynapseWeight()
					+ NNLib.WEIGHT_INCREASE_ON_MATCH);
			//
			sortConnectionsByWeight();
			newDataLine = getNodeConnections().get(0);
			newDataLine.getConnectionDestination().setActive(true);
			newDataLine.getConnectionOrigin().setActive(false);
			// TODO: TO HERE

		}
		setActive(true);
	}

	/**
	 * Takes the passed synapse and resets the state to not having pulsed, and
	 * not
	 * 
	 * @param connectionToIgnore
	 */
	public void cleanupDamage(Synapse connectionToIgnore) {

		for (Synapse netConnection : getNodeConnections()) {
			if (!netConnection.equals(connectionToIgnore)) {
				netConnection.hasPulsedInTick = false;
				netConnection.setPulseBack(false);
			}
		}
	}

	/**
	 * Takes all of the current synapses and arranges them in a list by their
	 * weight. This does not affect the connections of the synapses
	 */
	public void sortConnectionsByWeight() {
		Collections.sort(getNodeConnections(), new Comparator<Synapse>() {

			@Override
			public int compare(Synapse synA, Synapse synB) {
				return (int) (synA.getSynapseWeight() - synB.getSynapseWeight());
			}
		});
	}

	/**
	 * Returns the state of the node
	 * 
	 * @return active The boolean variable that stores the nodes
	 *         active/deactivated state
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the state of the node
	 * 
	 * @param active
	 *            The state that we want to adjust our node to
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	// @Deprecated
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
