package me.dylan.NNL.test;

import java.util.Random;

import me.dylan.NNL.Input;
import me.dylan.NNL.MathUtil;
import me.dylan.NNL.NNetwork;
import me.dylan.NNL.Neuron;
import me.dylan.NNL.Output;
import me.dylan.NNL.Value;

public class NeuralTest1 {
	static char[] possibleCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890()-+={}".toCharArray();
	static Random rand = new Random();
	public static void main(String[] args) {
		NNetwork network = new NNetwork();
		Output out = new Output();
		for (int i = 0; i < 5; i++) {
			Input in = new Input();
			in.setOutput(new Value(""+possibleCharacters[i]));
			for (int j = 0; j < 10; j++) {
				Neuron neuron = new Neuron();
				neuron.addSingleInput(in);
				// neuron.sendPulse(in.getOutput(), NodeType.INPUT);
				neuron.addSingleOutput(out);
				network.addNeuron(neuron);
			}

		}
		int i = 0;
		while (i < 20) {
			i++;
			System.out.println("Tick: " + i);
			for (Neuron neuron : network.getConnections()) {
				neuron.doTick();
//				for (Input input : neuron.datain.keySet()) {
//					System.out.println("Input value: " + input.getOutput());
//				}
			}
			System.out.println("Output Value: " + out.getValue());
		}
	}
/*	private static void randomize(NNetwork net) {
		for(Neuron n : net.getConnections()) {
			for(Input i : net.getSensors()) {
				if(rand.nextInt(net.getSensors().size()) == net.getSensors().indexOf(i)) {
					n.a
				}
			}
		}
	}*/
}
