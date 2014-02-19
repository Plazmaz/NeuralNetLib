package me.dylan.NNL.test;

import java.util.Random;

import me.dylan.NNL.Input;
import me.dylan.NNL.NNetwork;
import me.dylan.NNL.Neuron;
import me.dylan.NNL.Output;
import me.dylan.NNL.Value;
import me.dylan.NNL.Utils.MathUtil;
import me.dylan.NNL.Utils.NetworkUtil;

public class NeuralTest1 {
	static char[] possibleCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890()-+={}"
			.toCharArray();
	static Random rand = new Random();

	public static void main(String[] args) {
		NNetwork network = NetworkUtil.initializeNetwork(20, 5, 20);
		network.randomize();
		int i = 0;
		while (i < 20) {
			i++;
			System.out.println("Tick: " + i);
			for (Neuron neuron : network.getNeurons()) {
				neuron.doTick();
				// for (Input input : neuron.datain.keySet()) {
				// System.out.println("Input value: " + input.getOutput());
				// }
			}
		}
	}
	/*
	 * private static void randomize(NNetwork net) { for(Neuron n :
	 * net.getConnections()) { for(Input i : net.getSensors()) {
	 * if(rand.nextInt(net.getSensors().size()) == net.getSensors().indexOf(i))
	 * { n.a } } } }
	 */
}
