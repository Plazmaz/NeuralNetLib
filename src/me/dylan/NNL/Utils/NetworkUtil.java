package me.dylan.NNL.Utils;

import java.util.ArrayList;
import java.util.Random;

import me.dylan.NNL.Input;
import me.dylan.NNL.NNLib;
import me.dylan.NNL.NNetwork;
import me.dylan.NNL.Neuron;
import me.dylan.NNL.Output;
import me.dylan.NNL.Synapse;

public class NetworkUtil {
	/**
	 * How much to increase the weight when a pathway is chosen over another
	 */
	private static int INCREASE_WEIGHT_ON_CHOOSE = 2;
	/**
	 * How much to increase the weight when two pathways are identical
	 */
	private static int INCREASE_WEIGHT_ON_MATCH = 4;
	public static NNetwork initializeNetwork(int neuronCount, int inputCount,
			int outputCount) {
		ArrayList<Neuron> networkNeurons = new ArrayList<Neuron>();
		ArrayList<Input> inputNeurons = new ArrayList<Input>();
		ArrayList<Output> outputNeurons = new ArrayList<Output>();
		for (int i = 0; i < inputCount; i++) {
			inputNeurons.add(new Input());
		}
		for (int i = 0; i < outputCount; i++) {
			outputNeurons.add(new Output());
		}
		for (int i = 0; i < neuronCount; i++) {
			Neuron neuron = new Neuron();
//			neuron.addManyInputNodes(inputNeurons);
//			neuron.addManyOutputNodes(outputNeurons);
			networkNeurons.add(neuron);
		}
		return new NNetwork(inputNeurons, networkNeurons, outputNeurons);
	}

	/**
	 * 'Breed' two or more networks together through fusion, combining neural
	 * pathways based on weight with a random chance for mutation. This will also
	 * combine all inputs and outputs into one network. 
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
				ArrayList<Neuron> neurons = net.getNeuronsInNetwork();
				neurons.addAll(net2.getNeuronsInNetwork());
				childNet.addManyInputNodesToNetwork(inputs);
				childNet.addManyOutputNodesToNetwork(outputs);
				for (Neuron neuron : net.getNeuronsInNetwork()) {
					if (rand.nextInt(101) <= mutationChance) { /*
																 * java randoms
																 * are
																 * non-inclusive
																 * to the last
																 * number
																 */
						neuron.randomizeNodeConnections(net);
					} else {
						for (Neuron neuron2 : net2.getNeuronsInNetwork()) {
							breedNeurons(neuron, neuron2, mutationChance, childNet);
						}
					}
				}
			}
		}
		return childNet;
	}

	/**
	 * 'Breed' the two neurons together, combining random parental features.
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
	public static Neuron breedNeurons(Neuron parent1, Neuron parent2,
			int mutationChance, NNetwork parentNet) {
		Neuron child = new Neuron();
		for (Synapse parentSynapse : parent1.getNodeConnections()) {
			if (NNLib.GLOBAL_RANDOM.nextInt(101) <= mutationChance) {
				Synapse childSynapse = parentSynapse.clone();
				childSynapse.setConnectionDestination(parent1.getConnectedNodes().get(
						NNLib.GLOBAL_RANDOM.nextInt(parentNet.getNodesInNetwork().size())));
				child.connectWithRandomWeight(childSynapse.getConnectionDestination());
			} else {

				for (Synapse parent2Synapse : parent2.getNodeConnections()) {
					Synapse childSynapse = null;
					if(parentSynapse.getConnectionDestination().equals(parent2Synapse)) {
						child.connectNeuronToNode(parentSynapse.getConnectionDestination(),
								parentSynapse.getSynapseWeight() + INCREASE_WEIGHT_ON_MATCH);
						continue;
						
					}
					if (parent2Synapse.getSynapseWeight() > parentSynapse.getSynapseWeight()) {
						childSynapse = parent2Synapse.clone();
					} else if (parentSynapse.getSynapseWeight() > parent2Synapse
							.getSynapseWeight()) {
						childSynapse = parentSynapse.clone();

					}
					if (childSynapse != null) {
						child.connectNeuronToNode(childSynapse.getConnectionDestination(),
								childSynapse.getSynapseWeight() + INCREASE_WEIGHT_ON_CHOOSE);
					}
				}
			}
		}
		return child;
	}
}
