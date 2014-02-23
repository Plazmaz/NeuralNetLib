package me.dylan.NNL.Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import me.dylan.NNL.Input;
import me.dylan.NNL.NNLib;
import me.dylan.NNL.NNLib.NodeType;
import me.dylan.NNL.NNetwork;
import me.dylan.NNL.HiddenNode;
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
		ArrayList<HiddenNode> networkHiddens = new ArrayList<HiddenNode>();
		ArrayList<Input> inputHiddens = new ArrayList<Input>();
		ArrayList<Output> outputHiddens = new ArrayList<Output>();
		for (int i = 0; i < inputCount; i++) {
			inputHiddens.add(new Input());
		}
		for (int i = 0; i < outputCount; i++) {
			outputHiddens.add(new Output());
		}
		for (int i = 0; i < HiddenCount; i++) {
			HiddenNode Hidden = new HiddenNode();
			// Hidden.addManyInputNodes(inputHiddens);
			// Hidden.addManyOutputNodes(outputHiddens);
			networkHiddens.add(Hidden);
		}
		return new NNetwork(inputHiddens, networkHiddens, outputHiddens);
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
		for (NNetwork net : networks) {
			for (NNetwork net2 : networks) {

				ArrayList<Input> inputs = net.getInputNodesInNetwork();
				inputs.addAll(net2.getInputNodesInNetwork());
				ArrayList<Output> outputs = net.getOutputNodesInNetwork();
				outputs.addAll(net2.getOutputNodesInNetwork());
				ArrayList<HiddenNode> Hiddens = net.getHiddenNodesInNetwork();
				Hiddens.addAll(net2.getHiddenNodesInNetwork());
				childNet.addManyInputNodesToNetwork(inputs);
				childNet.addManyOutputNodesToNetwork(outputs);
				for (HiddenNode Hidden : net.getHiddenNodesInNetwork()) {
					if (rand.nextInt(101) <= mutationChance) { /*
																 * java randoms
																 * are
																 * non-inclusive
																 * to the last
																 * number
																 */
						Hidden.randomizeNodeConnections(childNet);
					} else {
						for (HiddenNode Hidden2 : net2.getHiddenNodesInNetwork()) {
							breedHiddens(Hidden, Hidden2, mutationChance,
									childNet);
						}
					}
				}
			}
		}
		return childNet;
	}

	/**
	 * 'Breed' the two Hiddens together, combining random parental features.
	 * This is more fusion than asexual or sexual reproduction
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
	public static HiddenNode breedHiddens(HiddenNode parent1, HiddenNode parent2,
			int mutationChance, NNetwork parentNet) {
		HiddenNode child = new HiddenNode();
		for (Synapse parentSynapse : parent1.getNodeConnections()) {
			if (NNLib.GLOBAL_RANDOM.nextInt(101) <= mutationChance) {
				Synapse childSynapse = parentSynapse.clone();
				childSynapse.setConnectionDestination(parent1
						.getConnectedNodes().get(
								NNLib.GLOBAL_RANDOM.nextInt(parentNet
										.getNodesInNetwork().size())));
				child.connectWithRandomWeight(childSynapse
						.getConnectionDestination());
			} else {

				for (Synapse parent2Synapse : parent2.getNodeConnections()) {
					Synapse childSynapse = null;
					if (parentSynapse.getConnectionDestination().equals(
							parent2Synapse)) {
						child.connectNodeToNode(
								parentSynapse.getConnectionDestination(),
								parentSynapse.getSynapseWeight()
										+ INCREASE_WEIGHT_ON_MATCH);
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
										+ INCREASE_WEIGHT_ON_CHOOSE);
					}
				}
			}
		}
		return child;
	}

	public static Color returnWeightColor(int colorVariable) {
		int oneThirdConnectionWeight = NNLib.MAX_CONNECTION_WEIGHT / 3;
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

	public static Color returnNodeWeightColor(int colorVariable, int MAX_CONNECTIONS) {
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
	
	public static HiddenNode createHidden(String incomingData, NodeType senderType) {
		HiddenNode hiddenOut = new HiddenNode();
		hiddenOut.setHiddenValueInNode(new Value(incomingData), senderType);
		return hiddenOut;
	}
}
