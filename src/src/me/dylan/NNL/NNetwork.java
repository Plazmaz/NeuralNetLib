package src.me.dylan.NNL;

import java.util.ArrayList;

/**
 * A wrapper around an entire neural network.
 * 
 * @author Dylan
 * 
 */
public class NNetwork {
	private ArrayList<Neuron> connections = new ArrayList<Neuron>();

	public NNetwork(ArrayList<Input> sensors, ArrayList<Neuron> connections,
			ArrayList<Output> outputs) {
		this.connections = connections;
	}

	public NNetwork() {
	};

	public ArrayList<Neuron> getConnections() {
		return connections;
	}

	public void addNeuron(Neuron neuron) {
		connections.add(neuron);
	}
}
