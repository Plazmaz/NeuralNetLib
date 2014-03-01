package me.dylan.NNL;

import me.dylan.NNL.Utils.StringUtil;

public class Synapse {
    private Node origin;
    private Node destination;
    private double weight = 0;
    private boolean hasPaintedInTick = false;
    public double percentMatchAtOrigin = 0;
    String desiredOutput = "";
    public boolean hasPulsedInTick = false;
    public Synapse(Node origin, Node destination, double weight, String desiredOutput) {
	this.setConnectionOrigin(origin);
	this.setConnectionDestination(destination);
	setSynapseWeight(weight);
	this.desiredOutput = desiredOutput;
	percentMatchAtOrigin = StringUtil.calculateStringSimilarityPercentage(desiredOutput, origin.getNodeInfo().getData());
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
		getSynapseWeight(), desiredOutput);
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
