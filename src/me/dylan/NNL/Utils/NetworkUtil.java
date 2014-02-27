package me.dylan.NNL.Utils;

import java.awt.Color;
import java.util.ArrayList;
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

public class NetworkUtil {
    /**
     * How much to increase the weight when a pathway is chosen over another
     */
    private static int INCREASE_WEIGHT_ON_CHOOSE = 2;
    /**
     * How much to increase the weight when two pathways are identical
     */
    private static int INCREASE_WEIGHT_ON_MATCH = 4;

    public static NNetwork initializeNetwork(int HiddenCount, int inputCount,
	    int outputCount) {
	ArrayList<HiddenNode> networkHiddenNodes = new ArrayList<HiddenNode>();
	ArrayList<Input> inputHiddenNodes = new ArrayList<Input>();
	ArrayList<Output> outputHiddenNodes = new ArrayList<Output>();
	for (int i = 0; i < inputCount; i++) {
	    inputHiddenNodes.add(new Input());
	}
	for (int i = 0; i < outputCount; i++) {
	    outputHiddenNodes.add(new Output());
	}
	NNetwork net = new NNetwork(inputHiddenNodes, networkHiddenNodes,
		outputHiddenNodes);
	for (int i = 0; i < HiddenCount; i++) {
	    HiddenNode Hidden = new HiddenNode();
	    Hidden.addManyInputNodes(inputHiddenNodes, net);
	    Hidden.addManyOutputNodes(outputHiddenNodes, net);
	    networkHiddenNodes.add(Hidden);
	}
	return net;
    }

    private static void addRandomSynapse(NNetwork parentNetwork,
	    Synapse seedSynapse) {

	parentNetwork.addSynapse(randomizeSynapse(parentNetwork, seedSynapse));
    }

    public static void updateSynapseGenerations(NNetwork parentNetwork) {
	ArrayList<Synapse> synapsesClone = (ArrayList<Synapse>) parentNetwork
		.getNetworkSynapses().clone();
	double weightToDistribute = 0;
	double avgWeight = 0;
	for (Synapse netSynapse : synapsesClone) {
	    if (parentNetwork.getNetworkSynapses().isEmpty())
		break;
	    if (NNLib.GLOBAL_RANDOM.nextInt(101) <= NNLib.SYNAPSE_MUTATION_CHANCE) {
		addRandomSynapse(parentNetwork, netSynapse);
	    }
		avgWeight += netSynapse.getSynapseWeight();
		weightToDistribute += netSynapse.getSynapseWeight();
	}
	avgWeight /= synapsesClone.size();
	// parentNetwork.removeUnusedSynapses();
	synapsesClone = (ArrayList<Synapse>) parentNetwork.getNetworkSynapses()
		.clone();
	for (Synapse netSynapse : synapsesClone) {
	    if (parentNetwork.getNetworkSynapses().isEmpty())
		break;
	    if (netSynapse.getSynapseWeight() < avgWeight
		    + NNLib.SYNAPSE_WEIGHT_PROGRESSION_THRESHOLD) {
		// randomizeSynapse(parentNetwork, netSynapse);
		if (parentNetwork.getNetworkSynapses().isEmpty()) {
		    // if
		    // (parentNetwork.getNetworkSynapses().contains(netSynapse))
		    // {
		    Synapse netSynapseClone = netSynapse.clone();

		    parentNetwork.getNetworkSynapses().add(
			    randomizeSynapse(parentNetwork, netSynapseClone));


		    // }

		} else {
		    if (netSynapse.getSynapseWeight() <=NNLib.SYNAPSE_WEIGHT_PROGRESSION_THRESHOLD)
			netSynapse.severConnections();
		}
	    } else {
		netSynapse.setSynapseWeight(netSynapse.getSynapseWeight()
			+ weightToDistribute / synapsesClone.size());
		weightToDistribute -= weightToDistribute / synapsesClone.size();
	    }
	}
    }

    /**
     * 'Breed' two or more networks together through fusion, combining neural
     * pathways based on weight with a random chance for mutation. This will
     * also combine all inputs and outputs into one network.
     * 
     * @param networks
     *            The list of parents to combine into a child
     * @param mutation
     *            The chance to discard a parental feature and mutate a random
     *            feature as expressed as a percentage(suggested: 30)
     */
    public static NNetwork breedNetworks(ArrayList<NNetwork> networks,
	    int mutationChance) {
	Random rand = NNLib.GLOBAL_RANDOM;
	NNetwork childNet = new NNetwork();
	childNet.addOutputNodeToNetwork(new Output());
	childNet.addInputNodeToNetwork(new Input());
	for (NNetwork net : networks) {
	    for (NNetwork net2 : networks) {
		ArrayList<HiddenNode> HiddenNodes = (ArrayList<HiddenNode>) net
			.getHiddenNodesInNetwork().clone();
		HiddenNodes.addAll(net2.getHiddenNodesInNetwork());
		for (HiddenNode Hidden : HiddenNodes) {
		    if (rand.nextInt(101) <= mutationChance) { /*
							        * java randoms
							        * are
							        * non-inclusive
							        * to the last
							        * number
							        */
			Hidden.randomizeNodeConnections(childNet);
		    } else {
			breedHiddenNodes(Hidden,
				HiddenNodes.get(HiddenNodes.indexOf(Hidden)),
				mutationChance, childNet);
		    }
		}
	    }
	}
	return childNet;
    }

    /**
     * 'Breed' the two Hidden Nodes together, combining random parental
     * features. This is more fusion than asexual or sexual reproduction
     * 
     * @param parent1
     *            The first parent
     * @param parent2
     *            The second parent
     * @param mutationChance
     *            The chance to randomize a feature expressed in a
     *            percentage(suggested: 30%)
     * @return
     */
    public static HiddenNode breedHiddenNodes(HiddenNode parent1,
	    HiddenNode parent2, int mutationChance, NNetwork parentNet) {
	HiddenNode child = new HiddenNode();
	ArrayList<Synapse> parent1Synapses = (ArrayList<Synapse>) parent1
		.getNodeConnections().clone();
	for (Synapse parentSynapse : parent1Synapses) {

	    if (NNLib.GLOBAL_RANDOM.nextInt(101) <= mutationChance) {
		randomizeSynapse(parentNet, parentSynapse);
	    } else {
		Synapse childSynapse = null;
		ArrayList<Synapse> parent2Synapses = (ArrayList<Synapse>) parent2
			.getNodeConnections().clone();
		for (Synapse parent2Synapse : parent2Synapses) {
		    if (parentSynapse.getConnectionDestination().equals(
			    parent2Synapse)) {
			child.connectNodeToNode(
				parentSynapse.getConnectionDestination(),
				parentSynapse.getSynapseWeight()
					+ INCREASE_WEIGHT_ON_MATCH, parentNet);
			continue;

		    }
		    if (parent2Synapse.getSynapseWeight() > parentSynapse
			    .getSynapseWeight()) {
			childSynapse = parent2Synapse.clone();
		    } else if (parentSynapse.getSynapseWeight() > parent2Synapse
			    .getSynapseWeight()) {
			childSynapse = parentSynapse.clone();

		    }
		    if (childSynapse != null) {
			child.connectNodeToNode(
				childSynapse.getConnectionDestination(),
				childSynapse.getSynapseWeight()
					+ INCREASE_WEIGHT_ON_CHOOSE, parentNet);
		    }
		    if (parentSynapse.getSynapseWeight() < NNLib.SYNAPSE_WEIGHT_PROGRESSION_THRESHOLD) {
			parentSynapse.severConnections();
		    }
		}

	    }
	}
	return child;
    }

    public static Color returnWeightColor(double d) {
	int oneThirdConnectionWeight = NNLib.MAX_CONNECTION_WEIGHT / 3;
	if (d <= oneThirdConnectionWeight) {
	    return Color.BLUE;
	}
	if (d <= oneThirdConnectionWeight * 2) {
	    return Color.GREEN;
	}
	if (d <= NNLib.MAX_CONNECTION_WEIGHT) {
	    return Color.RED;
	}
	return Color.WHITE;
    }

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

    public static HiddenNode createHidden(String incomingData,
	    NodeType senderType) {
	HiddenNode hiddenOut = new HiddenNode();
	hiddenOut.setHiddenValueInNode(new Value(incomingData), senderType);
	return hiddenOut;
    }

    public static Synapse randomizeSynapse(NNetwork parentNetwork,
	    Synapse synapseToRandomize) {
	Synapse randSynapse = synapseToRandomize.clone();
	Node destination = null;
	Node origin = null;
	parentNetwork.removeUnusedSynapses();
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
	destination.getNodeConnections().add(randSynapse);
	origin.getNodeConnections().add(randSynapse);
	randSynapse.setConnectionDestination(destination);
	randSynapse.setConnectionOrigin(origin);
	randSynapse.setSynapseWeight(NNLib.GLOBAL_RANDOM
		.nextInt(NNLib.MAX_CONNECTION_WEIGHT));
	return randSynapse;
    }
}
