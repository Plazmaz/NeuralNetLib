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
		connectNodeToNode(input, NNLib.MAX_CONNECTION_WEIGHT / 2, parentNet);
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
		connectNodeToNode(output, NNLib.MAX_CONNECTION_WEIGHT / 2, parentNet);
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
	 * Runs every time the mainloop updates and tests for synapses.
	 * After finding a synapse it checks to make sure that
	 */
	public void doTick() {
		sortConnectionsByWeight();

//		getNodeConnections().get(0);
		// TODO: DYLAN: Really similar to code below -- see 'todo' a few lines down
		//TODO: DO NOTE DELETE CURRENTLY --- TESTING IF USED
		/*if (i == 0) {
			// TODO: Same as above
			while (i == 0 || outLine.getConnectionDestination().equals(this)) {
				outLine = getNodeConnections().get(i);
				if (i >= getNodeConnections().size()) {
					break;
				}
				i++;
			}
		}*/
		// TODO: Dylan: Add a check for input
//		if (outLine.getConnectionDestination().getNodeVariety() == NodeType.HIDDEN) {
//			if (this.isActive()) {
//				outLine.sendDataPulse(getNodeInfo());
//				System.out.println("Pulsed to Hidden "
//						+ +outLine.getConnectionDestination().nodeID
//						+ " from Hidden " + nodeID);
//			}
//		} else if (outLine.getConnectionDestination().getNodeVariety() == NodeType.OUTPUT) {
//			if (this.isActive()) {
//				System.out.println("Pulsed to Output "
//						+ outLine.getConnectionDestination().getNodeInfo()
//						+ " from " + getNodeInfo());
//				((Output) outLine.getConnectionDestination())
//						.putOrMove(outLine);
//			}
//		}
	}

}
