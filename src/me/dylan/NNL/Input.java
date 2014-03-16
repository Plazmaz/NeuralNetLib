package me.dylan.NNL;

import java.util.Collections;
import java.util.Comparator;

import me.dylan.NNL.NNLib.NodeType;

public class Input extends Node {
	int infoIndex = 0;
	private String[] outInfoQueue;

	public Input(String[] outInfoQueue) {
		nodeVariety = NodeType.INPUT;
		this.outInfoQueue = outInfoQueue;
		for (String outVal : outInfoQueue)
			setInformation(information.appendToValue(new Value(outVal)));
	}

	/**
	 * @see me.dylan.NNL.Node#spikeWithInput(me.dylan.NNL.Synapse)
	 */
	@Override
	public void spikeWithInput(Synapse dataLine) {
		String originalInfo = getNodeInfo().getData();
		super.spikeWithInput(dataLine);
		if (getNodeInfo().getData().isEmpty())
			setNodeData(originalInfo);
		outInfoQueue = getNodeInfo().getData().split("\n");
		activateInputNode();
	}

	/**
	 * Activates the input node after running a check to make sure that it needs
	 * to activate it. Then sorts the list.
	 * 
	 */
	public void activateInputNode() {
		if (infoIndex >= outInfoQueue.length) {
			return;
		}
		System.out.println("Succesfully stimulated input node");
		int i = 0;
		Synapse outLine = null;
		while (outLine == null || outLine.hasPulsedInTick
				|| outLine.getConnectionDestination().equals(this)) {
			if (i >= getNodeConnections().size())
				break;

			outLine = getNodeConnections().get(i);
			i++;
		}
		setActive(true);
		setNodeData(outInfoQueue[infoIndex]);
		Collections.sort(getNodeConnections(), new Comparator<Synapse>() {

			@Override
			// TODO: DYLAN: Compare Function Below -- Merge with rest
			public int compare(Synapse synA, Synapse synB) {
				return (int) (synA.getSynapseWeight() - synB.getSynapseWeight());
			}

		});
		getNodeConnections().get(0).getConnectionDestination()
				.spikeWithInput(getNodeConnections().get(0)); 
		infoIndex++;
	}

	/**
	 * Retrieve the raw data collected by the Hidden
	 * 
	 * @return information The Value variable that holds all the raw data
	 */
	public Value getInputData() {
		return information;
	}

	/**
	 * Add additional raw data to information
	 * 
	 * @param info
	 *            The data that needs to be added to the nodes raw data storage
	 *            variable
	 */
	public void setInformation(Value info) {
		this.information = info;
	}

	/**
	 * Turn the input node to it's off state, this entails deactivating synapses
	 * and node, as well as restoring original synapse direction
	 */
	public void deactivateInputNode() {
		cleanupDamage(null);
		setActive(false);
	}
}
