package me.dylan.NNL.test;

import java.util.ArrayList;

import me.dylan.NNL.Input;
import me.dylan.NNL.MathUtil;
import me.dylan.NNL.NNLib;
import me.dylan.NNL.NNetwork;
import me.dylan.NNL.Neuron;
import me.dylan.NNL.Output;
import me.dylan.NNL.Value;
import me.dylan.NNL.NNLib.NodeType;

public class NeuralTest1 {
	public static void main(String[] args) {
		NNetwork network = new NNetwork();
		Output out = new Output();
		for (int i = 0; i < 5; i++) {
			Input in = new Input();
			in.setOutput(new Value(MathUtil.generateRandFloat(25, 50), 50, 25));
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
				for (Input input : neuron.datain) {
					System.out.println("Input value: " + input.getOutput());
				}
			}
			System.out.println("Output Value: " + out.getValue());
		}
	}
}
