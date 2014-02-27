package me.dylan.NNL;

public class Synapse {
    private Node origin;
    private Node destination;
    private double weight = 0;
    private boolean hasPaintedInTick = false;

    public Synapse(Node origin, Node destination, double weight) {
	this.setConnectionOrigin(origin);
	this.setConnectionDestination(destination);
	setSynapseWeight(weight);
    }

    public Node getConnectionOrigin() {
	return origin;
    }

    public void severConnections() {
	if (origin != null)
	    origin.disconnectNode(this);
	else if(destination != null)
	    destination.disconnectNode(this);
	origin = null;
	destination = null;
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

    public double getSynapseWeight() {
	return weight;
    }

    public void setSynapseWeight(double d) {
	this.weight = d;
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
    
    @Override
    public String toString() {
	return "A: "+origin+" B:"+destination+" Weight:"+weight;
    }
}
