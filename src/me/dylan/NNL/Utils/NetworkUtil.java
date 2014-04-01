package me.dylan.NNL.Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import me.dylan.NNL.HiddenNode;
import me.dylan.NNL.Input;
import me.dylan.NNL.NNLib;
import me.dylan.NNL.NNLib.NodeType;
import me.dylan.NNL.NNetwork;
import me.dylan.NNL.Node;
import me.dylan.NNL.Output;
import me.dylan.NNL.Synapse;
import me.dylan.NNL.Value;
import me.dylan.NNL.Test.TestUtil;

public class NetworkUtil {

    /**
     * Creates the network based on the desired amount of starting nodes; input,
     * output, hidden
     * 
     * 
     * @param hiddenCount
     *            The amount of Hidden Nodes to be created at the start of the
     *            network
     * @param inputCount
     *            The amount of Input Nodes to be created at the start of the
     *            network
     * @param outputCount
     *            The amount of Output Nodes to be created at the start of the
     *            network
     * @param desiredOutput
     *            The output we are looking for from the network -- used to
     *            assist in training.
     * @param tempLines
     *            List of all data from the input file, broken up into lines of
     *            strings gets the current weight of the synapse being colored
     * @return Returns the created network
     */
    public static NNetwork initializeNetwork(int hiddenCount, int inputCount,
	    int outputCount, String desiredOutput, List<String> tmpLines) {
	ArrayList<HiddenNode> networkHiddenNodes = new ArrayList<HiddenNode>();
	ArrayList<Input> networkInputNodes = new ArrayList<Input>();
	ArrayList<Output> networkOutputNodes = new ArrayList<Output>();
	for (int i = 0; i < inputCount; i++) {
	    networkInputNodes.add(new Input(desiredOutput.split("\n")));
	}
	for (int i = 0; i < outputCount; i++) {
	    networkOutputNodes.add(new Output());
	}
	NNetwork net = new NNetwork(networkInputNodes, networkHiddenNodes,
		networkOutputNodes, desiredOutput);
	for (int i = 0; i < hiddenCount; i++) {
	    HiddenNode Hidden = new HiddenNode();
	    Hidden.addManyInputNodes(networkInputNodes, net);
	    Hidden.addManyOutputNodes(networkOutputNodes, net);
	    networkHiddenNodes.add(Hidden); // TODO: Possibly redundant -- do we
					    // add hidden nodes to network
					    // elsewhere based on a list?
	}
	for (String s : tmpLines) {
	    HiddenNode hiddenNode = NetworkUtil.createHidden(s, net);
	    net.addHiddenNodeToNetwork(hiddenNode);
	}
	net.connectAll();
	return net;
    }

    /**
     * Gives the color for the synapse line in the visualizer based on the
     * current weight of the line. Updated during every drawing tick
     * 
     * @param weightOfSynapse
     *            gets the current weight of the synapse being colored
     * @return Returns color to represent weight based on current weight
     */
    public static Color returnWeightColor(double weightOfSynapse) {
	int oneThirdConnectionWeight = NNLib.MAX_CONNECTION_WEIGHT / 3;
	if (weightOfSynapse <= oneThirdConnectionWeight) {
	    return Color.BLUE;
	}
	if (weightOfSynapse <= oneThirdConnectionWeight * 2) {
	    return Color.GREEN;
	}
	if (weightOfSynapse <= NNLib.MAX_CONNECTION_WEIGHT) {
	    return Color.RED;
	}
	return Color.WHITE;
    }

    /**
     * Gives the color for the synapse line in the visualizer based on the
     * current weight of the line. Updated during every drawing tick
     * 
     * @param weightOfSynapse
     *            gets the current weight of the synapse being colored
     * @return Returns color to represent weight based on current weight
     */
    public static Color returnNodeWeightColor(int colorVariable,
	    int MAX_CONNECTIONS) {
	int oneThirdConnectionWeight = MAX_CONNECTIONS / 3;
	if (colorVariable <= oneThirdConnectionWeight) {
	    return Color.BLUE;
	}
	if (colorVariable <= oneThirdConnectionWeight * 2) {
	    return Color.GREEN;
	}
	if (colorVariable <= NNLib.MAX_CONNECTION_WEIGHT) {
	    return Color.RED;
	}
	return Color.WHITE;
    }

    /**
     * Create a hidden node on each call. Test and connect it to input, and
     * output, nodes.
     * 
     * @param incomingData
     *            the data to be stored in the hidden node
     * @param parentNetwork
     *            Network to which the nodes belong
     * @return Returns color to represent weight based on current weight
     */
    // TODO: Dylan: ADD FUNCTIONALITY FOR HIDDEN NODES TO BE CONNECTED TO EACH
    // OTHER
    // UPON CREATION AS DONE WITH INPUT AND OUTPUT NODES
    public static HiddenNode createHidden(String incomingData,
	    NNetwork parentNetwork) {
	HiddenNode hiddenOut = new HiddenNode();

	if (TestUtil.InputNodesExist(parentNetwork))
	    hiddenOut.addManyInputNodes(parentNetwork.getInputNodesInNetwork(),
		    parentNetwork);
	if (TestUtil.OutputNodesExist(parentNetwork))
	    hiddenOut.addManyOutputNodes(
		    parentNetwork.getOutputNodesInNetwork(), parentNetwork);
	if (TestUtil.HiddenNodesExist(parentNetwork))
	    hiddenOut.setNodeData(incomingData);

	return hiddenOut;
    }

    @Deprecated
    // Deprecated on 3/16/14
    public static Synapse randomizeSynapse(NNetwork parentNetwork,
	    Synapse synapseToRandomize) {
	Synapse randSynapse = synapseToRandomize.clone();
	Node destination = null;
	Node origin = null;
	int i = 0;
	while (i == 0
		|| destination == synapseToRandomize.getConnectionDestination()
		|| origin == synapseToRandomize.getConnectionOrigin()) {
	    destination = parentNetwork.getNodesInNetwork().get(
		    NNLib.GLOBAL_RANDOM.nextInt(parentNetwork
			    .getNodesInNetwork().size()));

	    origin = parentNetwork.getNodesInNetwork().get(
		    NNLib.GLOBAL_RANDOM.nextInt(parentNetwork
			    .getNodesInNetwork().size()));
	    i++;

	}
	origin.connectNodeToNode(destination,
		NNLib.GLOBAL_RANDOM.nextInt(NNLib.MAX_CONNECTION_WEIGHT),
		parentNetwork);
	destination.connectNodeToNode(origin,
		NNLib.GLOBAL_RANDOM.nextInt(NNLib.MAX_CONNECTION_WEIGHT),
		parentNetwork);
	return randSynapse;
    }
}
