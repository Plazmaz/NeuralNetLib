package me.dylan.NNL;

import java.util.ArrayList;

/**
 * A wrapper around an entire neural network.
 * 
 * @author Dylan
 * 
 */
public class NNetwork {
	private ArrayList<Neuron> connections = new ArrayList<Neuron>();
	private ArrayList<Input> sensors = new ArrayList<Input>();
	private ArrayList<Output> outputs = new ArrayList<Output>();

	public NNetwork(ArrayList<Input> sensors, ArrayList<Neuron> connections,
			ArrayList<Output> outputs) {
		this.connections = connections;
		this.sensors = sensors;
		this.outputs = outputs;
	}

	public NNetwork() {
	};

	public ArrayList<Neuron> getConnections() {
		return connections;
	}

	public ArrayList<Input> getSensors() {
		return sensors;
	}

	public ArrayList<Output> getOutputs() {
		return outputs;
	}

	public void addNeuron(Neuron neuron) {
		connections.add(neuron);
		neuron.addManyInputs(getSensors());
		neuron.addManyOutputs(getOutputs());
		// neuron.addMa
	}
}
