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
		ArrayList<Neuron> neurons = new ArrayList<Neuron>();
		ArrayList<Input> inputs = new ArrayList<Input>();
		ArrayList<Output> outputs = new ArrayList<Output>();
		for (int i = 0; i < inputCount; i++) {
			inputs.add(new Input());
		}
		for (int i = 0; i < outputCount; i++) {
			outputs.add(new Output());
		}
		for (int i = 0; i < neuronCount; i++) {
			Neuron neuron = new Neuron();
			neuron.addManyInputs(inputs);
			neuron.addManyOutputs(outputs);
			neurons.add(neuron);
		}
		return new NNetwork(inputs, neurons, outputs);
	}

	/**
	 * 'Breed' two or more networks together through fussion, combining neural
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
		Random rand = NNLib.rand;
		NNetwork childNet = new NNetwork();
		for (NNetwork net : networks) {
			for (NNetwork net2 : networks) {

				ArrayList<Input> inputs = net.getInputs();
				inputs.addAll(net2.getInputs());
				ArrayList<Output> outputs = net.getOutputs();
				outputs.addAll(net2.getOutputs());
				ArrayList<Neuron> neurons = net.getNeurons();
				neurons.addAll(net2.getNeurons());
				childNet.addManyInputs(inputs);
				childNet.addManyOutputs(outputs);
				for (Neuron neuron : net.getNeurons()) {
					if (rand.nextInt(101) <= mutationChance) { /*
																 * java randoms
																 * are
																 * non-inclusive
																 * to the last
																 * number
																 */
						neuron.randomize(net);
					} else {
						for (Neuron neuron2 : net2.getNeurons()) {
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
		for (Synapse parentSynapse : parent1.getConnections()) {
			if (NNLib.rand.nextInt(101) <= mutationChance) {
				Synapse childSynapse = parentSynapse.clone();
				childSynapse.setDestination(parent1.getNodes().get(
						NNLib.rand.nextInt(parentNet.getNodes().size())));
				child.connectRandom(childSynapse.getDestination());
			} else {

				for (Synapse parent2Synapse : parent2.getConnections()) {
					Synapse childSynapse = null;
					if(parentSynapse.getDestination().equals(parent2Synapse)) {
						child.connect(parentSynapse.getDestination(),
								parentSynapse.getWeight() + INCREASE_WEIGHT_ON_MATCH);
						continue;
						
					}
					if (parent2Synapse.getWeight() > parentSynapse.getWeight()) {
						childSynapse = parent2Synapse.clone();
					} else if (parentSynapse.getWeight() > parent2Synapse
							.getWeight()) {
						childSynapse = parentSynapse.clone();

					}
					if (childSynapse != null) {
						child.connect(childSynapse.getDestination(),
								childSynapse.getWeight() + INCREASE_WEIGHT_ON_CHOOSE);
					}
				}
			}
		}
		return child;
	}
}
