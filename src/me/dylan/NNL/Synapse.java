package me.dylan.NNL;

import me.dylan.NNL.Utils.StringUtil;

//TODO: CRAIG: Javadocs plux
@Deprecated
public class Synapse {
    private Node origin;
    private Node destination;
    private double weight = 0;
    private boolean hasPaintedInTick = false;
    public double percentMatchAtOrigin = 0;
    private boolean pulseBack = false;
    String desiredOutput = "";
    public boolean hasPulsedInTick = false;
    public Synapse(Node origin, Node destination, double weight,
	    String desiredOutput) {
	this.setConnectionOrigin(origin);
	this.setConnectionDestination(destination);
	setSynapseWeight(weight);
	this.desiredOutput = desiredOutput;
	percentMatchAtOrigin = StringUtil.calculateStringSimilarityPercentage(
		desiredOutput, origin.getNodeInfo().getData());
    }
    /**
     * Get the origin of the synapse's connection, works in both directions
     * @see doesPulseBack
     * @see setPulseBack
     * @return connection origin
     */
    public Node getConnectionOrigin() {
	return (doesPulseBack() ? destination : origin);
    }

    public void severConnections() {
//	if (origin != null)
//	    origin.disconnectNode(this);
//	if (destination != null)
//	    destination.disconnectNode(this);
	origin = null;
	destination = null;
    }

    public void setConnectionOrigin(Node origin) {
	this.origin = origin;
    }

    public Node getConnectionDestination() {
	return (doesPulseBack() ? origin : destination);
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
	return "A: " + (doesPulseBack() ? destination : origin) + " B:"
		+ (doesPulseBack() ? origin : destination) + " Weight:"
		+ weight;
    }

    public boolean doesPulseBack() {
	return pulseBack;
    }

    public void setPulseBack(boolean pulseBack) {
	this.pulseBack = pulseBack;
    }

//    public void sendDataPulse(Value pulseData) {
//	getConnectionDestination().spikeWithInput(pulseData, desiredOutput,
//		percentMatchAtOrigin);
//	getConnectionOrigin().setActive(false);
//    }
}
