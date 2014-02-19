package me.dylan.NNL;

public class Synapse {
	private Node origin, destination;
	private int weight = 0;
	public Synapse(Node origin, Node destination, int weight) {
		this.setOrigin(origin);
		this.setDestination(destination);
	}

	public Node getOrigin() {
		return origin;
	}

	public void setOrigin(Node origin) {
		this.origin = origin;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	public Synapse clone() {
		return new Synapse(getOrigin(), getDestination(), getWeight());
	}
}
