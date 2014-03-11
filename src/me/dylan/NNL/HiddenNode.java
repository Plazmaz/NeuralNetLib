package me.dylan.NNL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import me.dylan.NNL.NNLib.NodeType;

public class HiddenNode extends Node {
	public ArrayList<Output> dataout = new ArrayList<Output>();
	public ArrayList<Input> datain = new ArrayList<Input>();
	int pulsesSeen = 0;

	public HiddenNode() {
		nodeVariety = NodeType.HIDDEN;
	}

	/**
	 * Takes an input node, and a network, and adds the input node to the
	 * network
	 * 
	 * @param input
	 *            The node passed that needs to be added to the network
	 * @param parentNet
	 *            The network that the node will be added to.
	 */
	public void addSingleInputNode(Input input, NNetwork parentNet) {
		connectNodeToNode(input, NNLib.MAX_CONNECTION_WEIGHT / 2, parentNet); // TODO:
																				// Issue:
																				// WHERE
																				// DOES
																				// 'connectNodeToNode'
																				// come
																				// from!?!?
		datain.add(input);
	}

	/**
	 * Takes a list of input nodes, and a network, and then adds all the input
	 * nodes to the network
	 * 
	 * @param inputs
	 *            The list of nodes that need to be added to the network
	 * @param parentNet
	 *            The network that the nodes will be added to.
	 */
	public void addManyInputNodes(ArrayList<Input> inputs, NNetwork parentNet) {
		for (Input i : inputs) {
			addSingleInputNode(i, parentNet);
		}
	}

	/**
	 * Takes an output node, and a network, and then adds the output node to the
	 * network
	 * 
	 * @param output
	 *            The node passed that needs to be added to the network
	 * @param parentNet
	 *            The network that the node will be added to.
	 */
	public void addSingleOutputNode(Output output, NNetwork parentNet) {
		connectNodeToNode(output, NNLib.MAX_CONNECTION_WEIGHT / 2, parentNet); // TODO:
																				// Issue:
																				// WHERE
																				// DOES
																				// 'connectNodeToNode'
																				// come
																				// from!?!?
		dataout.add(output);
	}

	/**
	 * Takes a list of output nodes, and a network, and then adds all the output
	 * nodes to the network
	 * 
	 * @param outputs
	 *            The list of nodes that need to be added to the network
	 * @param parentNet
	 *            The network that the nodes will be added to.
	 */
	public void addManyOutputNodes(ArrayList<Output> outputs, NNetwork parentNet) {
		for (Output out : outputs) {
			addSingleOutputNode(out, parentNet);
		}
	}

	/**
	 * //TODO: How often does this run? Runs every ____ and tests for synapses.
	 * After finding a synapse it cheks tomake sure that
	 */
	public void doTick() {
		Collections.sort(getNodeConnections(), new Comparator<Synapse>() {

			@Override
			// TODO: Should I javadocs this?
			public int compare(Synapse synA, Synapse synB) {
				return (int) (synA.getSynapseWeight() - synB.getSynapseWeight());
			}

		});

		int i = 0;
		Synapse outLine = null;
		// TODO: Really similar to code below -- see 'todo' a few lines down
		while (i == 0 || outLine.hasPulsedInTick
				|| outLine.getConnectionDestination().equals(this)) {

			outLine = getNodeConnections().get(i);
			if (i >= getNodeConnections().size()) {
				break;
			}
			i++;
		}
		if (i == 0) {
			// TODO: Same as above
			while (i == 0 || outLine.getConnectionDestination().equals(this)) {
				outLine = getNodeConnections().get(i);
				if (i >= getNodeConnections().size()) {
					break;
				}
				i++;
			}
		}
		// TODO: What if its a INPUT node? Hidden and Output are checked below,
		// but not hidden
		if (outLine.getConnectionDestination().getNodeVariety() == NodeType.HIDDEN) {
			if (this.isActive()) {
				outLine.getConnectionDestination().spikeWithInput(outLine);
				System.out.println("Pulsed to Hidden "
						+ +outLine.getConnectionDestination().nodeID
						+ " from Hidden " + nodeID);
				// ((HiddenNode) outLine.getConnectionDestination())
				// .sendPulseToAppendData(this, outLine);
			}
		} else if (outLine.getConnectionDestination().getNodeVariety() == NodeType.OUTPUT) {
			if (this.isActive()) {
				System.out.println("Pulsed to Output "
						+ outLine.getConnectionDestination().getNodeInfo()
						+ " from " + getNodeInfo());
				((Output) outLine.getConnectionDestination())
						.putOrMove(outLine);
				// ((Output) synapse.getConnectionDestination())
				// .setNodeInfo(synapse.getConnectionDestination()
				// .getNodeInfo().getValue()
				// + getNodeInfo().getValue());
				// this.setActive(active);
			}
		}
	}

	/**
	 * Takes the active network that it is passed, and randomly connects all
	 * nodes to each other
	 * 
	 * @param parentNet
	 *            The network which does not currently have synapses connecting
	 *            the nodes
	 */
	// TODO: Shouldn't we should check to see if the network already has
	// connections so we do not accidently assign secondary connections?
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
		getNodeConnections().clear();
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

	/**
	 * Takes in the value that is to be assigned to the hidden node originally
	 * 
	 * @param value
	 * @param senderType
	 *            The node variety of the node that sent the value.
	 */
	// TODO: is this description correct? What is value and where does it come
	// from
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

	/**
	 * 
	 * @return
	 */
	// TODO: Not sure about the ? and : in the statement will document later
	public ArrayList<Node> getConnectedNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (Synapse synapse : getNodeConnections()) {
			Node companion = synapse.getConnectionDestination().equals(this) ? synapse
					.getConnectionDestination() : synapse.getConnectionOrigin();
			nodes.add(companion);
		}
		return nodes;
	}

	// @Deprecated
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
