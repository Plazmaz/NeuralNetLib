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
	for (String outVal : outInfoQueue)
	    setInformation(information.appendToValue(new Value(outVal)));
    }

    @Override
    public void sendPulseToAppendData(Synapse dataLine) {
	String originalInfo = getNodeInfo().getData();
	super.sendPulseToAppendData(dataLine);
	if (getNodeInfo().getData().isEmpty())
	    setNodeData(originalInfo);
	outInfoQueue = getNodeInfo().getData().split("\n");
//	if(dataLine.doesPulseBack())
//	    this.infoIndex = 0;
	activateInputNode();
    }

    public void activateInputNode() {
	if (infoIndex >= outInfoQueue.length)
	    return;
	System.out.println("Succesfully stimulated input node");
	int i = 0;
	Synapse outLine = null;
	while (outLine == null || outLine.hasPulsedInTick
		|| outLine.getConnectionDestination().equals(this)) {
	    if (i >= getNodeConnections().size())
		break;
	    outLine = getNodeConnections().get(i);
	    i++;
	}
	setActive(true);
	setNodeData(outInfoQueue[infoIndex]);
	Collections.sort(getNodeConnections(), new Comparator<Synapse>() {

	    @Override
	    public int compare(Synapse synA, Synapse synB) {
		return (int) (synA.getSynapseWeight() - synB.getSynapseWeight());
	    }

	});
	getNodeConnections().get(0).getConnectionDestination()
		.sendPulseToAppendData(getNodeConnections().get(0)); // Problem
								     // line
	// setActive(false);
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
