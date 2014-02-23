package me.dylan.NNL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.dylan.NNL.NNLib.NodeType;

public class HiddenNode extends Node {
	public HashMap<Output, Integer> dataout = new HashMap<Output, Integer>();
	public HashMap<Input, Integer> datain = new HashMap<Input, Integer>();
	ArrayList<HiddenNode> connectedHiddenNodes = new ArrayList<HiddenNode>();
	private boolean active = false;

	public HiddenNode() {
		nodeVariety = NodeType.HIDDEN;
	}

	public void addSingleInputNode(Input input) {
		connectWithRandomWeight(input);
		datain.put(input, 0);
	}

	public void addManyInputNodes(ArrayList<Input> inputs) {
		for (Input i : inputs) {
			addSingleInputNode(i);
		}
	}

	public void addSingleOutputNode(Output output) {
		connectWithRandomWeight(output);
		dataout.put(output, 0);
	}

	public void addManyOutputNodes(ArrayList<Output> outputs) {
		for (Output out : outputs) {
			addSingleOutputNode(out);
		}
	}

	public void sendPulseToAppendData(Node sender) {
		try {
			Value vInNode = getNodeInfo().appendToValue(sender.getNodeInfo());
		setHiddenValueInNode(vInNode, sender.getNodeVariety());
		} catch(Exception ex) {
			System.out.println();
			ex.printStackTrace();
		}
		this.setActive(true);
		if (sender instanceof HiddenNode)
			((HiddenNode) sender).setActive(false);
		// setHiddenValueInNode(, sender.getNodeVariety());
	}

	public void doTick() {
		for (Input in : datain.keySet()) {
			if (in.active) {
				this.setActive(true);
			}
		}
		for (HiddenNode Hidden : connectedHiddenNodes) {
			if (this.active) {
				Hidden.sendPulseToAppendData(Hidden);
//				getNodeInfo().setValue("");
			}
		}
		for (Output out : dataout.keySet()) {
			if (this.active) {
				Value concattedValue = out.getOutputValue();
				concattedValue = concattedValue.appendToValue(getNodeInfo());
				out.setValue(concattedValue);
				System.out.println(out.getOutputValue());
//				getNodeInfo().setValue("");
			}
		}

		// Old regex implementation:
		// String[] regexes = RegParams.regParamsDel.split(", ");
		// for (Output out : dataout.keySet()) {
		// // value.avg(out.getValue());
		// HiddenValue = new Value(HiddenValue.data.replaceAll(
		// RegParams.regParamsDel, ""));
		// out.setValue(HiddenValue);
		// }
	}

	public void randomizeNodeConnections(NNetwork parentNet) {
		Random rand = NNLib.GLOBAL_RANDOM;
		datain.clear();
		dataout.clear();
		this.connectedHiddenNodes.clear();
		if (!parentNet.getInputNodesInNetwork().isEmpty())
			for (int i = 0; i <= rand.nextInt(parentNet
					.getInputNodesInNetwork().size()); i++) {
				addSingleInputNode(parentNet.getInputNodesInNetwork().get(i));
			}
		if (!parentNet.getOutputNodesInNetwork().isEmpty())
			for (int i = 0; i <= rand.nextInt(parentNet
					.getOutputNodesInNetwork().size()); i++) {
				addSingleOutputNode(parentNet.getOutputNodesInNetwork().get(i));
			}
		if (!parentNet.getHiddenNodesInNetwork().isEmpty())
			for (int i = 0; i <= rand.nextInt(parentNet
					.getHiddenNodesInNetwork().size()); i++) {
				// if(!parentNet.)
				this.connectedHiddenNodes.add(parentNet
						.getHiddenNodesInNetwork().get(i));
				connectWithRandomWeight(parentNet.getHiddenNodesInNetwork()
						.get(i));
			}

	}

	public void setHiddenValueInNode(Value value, NodeType senderType) {
		switch (senderType) {
		case HIDDEN:
			this.information = value;
			break;
		case INPUT:
			this.information = value;
			break;
		case OUTPUT:
			break;
		default:
			break;
		}
	}

	public ArrayList<Node> getConnectedNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.addAll(connectedHiddenNodes);
		nodes.addAll(datain.keySet());
		nodes.addAll(dataout.keySet());
		return nodes;
	}

	public void connectNodeToNode(Node destination, int weight) {
		if (destination instanceof Input)
			addSingleInputNode((Input) destination);
		if (destination instanceof Output)
			addSingleOutputNode((Output) destination);
		if (destination instanceof HiddenNode)
			connectedHiddenNodes.add((HiddenNode) destination);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
