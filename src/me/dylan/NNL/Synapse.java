package me.dylan.NNL;

public class Synapse {
    private Node origin, destination;
    private int weight = 0;
    private boolean hasPaintedInTick = false;

    public Synapse(Node origin, Node destination, int weight) {
	this.setConnectionOrigin(origin);
	this.setConnectionDestination(destination);
	setSynapseWeight(weight);
    }

    public Node getConnectionOrigin() {
	return origin;
    }

    public void severConnections() {
	origin.getNodeConnections().remove(this);
	destination.getNodeConnections().remove(this);
    }

    public void setConnectionOrigin(Node origin) {
	this.origin = origin;
    }

    public Node getConnectionDestination() {
	return destination;
    }

    public void setConnectionDestination(Node destination) {
	this.destination = destination;
    }

    public int getSynapseWeight() {
	return weight;
    }

    public void setSynapseWeight(int weight) {
	this.weight = weight;
    }

    @Override
    public Synapse clone() {
	return new Synapse(getConnectionOrigin(), getConnectionDestination(),
		getSynapseWeight());
    }

    public boolean hasPaintedInTick() {
	return hasPaintedInTick;
    }

    public void setHasPaintedInTick(boolean hasPaintedInTick) {
	this.hasPaintedInTick = hasPaintedInTick;
    }
}
