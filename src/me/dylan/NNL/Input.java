package me.dylan.NNL;

import java.util.Collections;
import java.util.Comparator;

import me.dylan.NNL.NNLib.NodeType;

/**
 * Input nodes, essentially
 * 
 * @author Dylan
 * 
 */
public class Input extends Node {
    int infoIndex = 0;
    private String[] outInfoQueue;
    public Input(String[] outInfoQueue) {
	nodeVariety = NodeType.INPUT;
	this.outInfoQueue = outInfoQueue;
    }

    public void activateInputNode() {
	if(infoIndex >= outInfoQueue.length)
	    return;
	Collections.sort(getNodeConnections(), new Comparator<Synapse>() {

	    @Override
	    public int compare(Synapse synA, Synapse synB) {
		return (int) (synA.getSynapseWeight() - synB.getSynapseWeight());
	    }

	});
	int i = 0;
	Synapse outLine = null;
	while (outLine == null || outLine.hasPulsedInTick || outLine.getConnectionDestination().equals(this)) {
	    outLine = getNodeConnections().get(i);
	    if (i >= getNodeConnections().size())
		break;
	    i++;
	}
	setActive(true);
	setNodeData(outInfoQueue[infoIndex]);
	getNodeConnections().get(0).getConnectionDestination().spikeWithInput(getNodeConnections().get(0));
//	setActive(false);
	infoIndex++;
    }

    /**
     * Retrieve the raw data collected by the Hidden
     * 
     * @return data A dump of all data collected by Hidden this tick
     */
    public Value getInputData() {
	return information;
    }

    public void setInformation(Value info) {
	this.information = info;
    }

    public void appendInfo(String info) {
	this.information.setValue(info);
    }

}
