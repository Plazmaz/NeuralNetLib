package me.dylan.NNL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.dylan.NNL.NNLib.NodeType;

public class HiddenNode extends Node {
	public HashMap<Output, Integer> dataout = new HashMap<Output, Integer>();
	public HashMap<Input, Integer> datain = new HashMap<Input, Integer>();
	ArrayList<HiddenNode> connectedHiddenNodes = new ArrayList<HiddenNode>();
	Value HiddenValue = new Value();

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

	public void sendPulse(Node sender) {
		// setHiddenValueInNode(, sender.getNodeVariety());
	}

	public void doTick() {
		HiddenValue.setValue("");
		for (Input in : datain.keySet()) {
			setHiddenValueInNode(in.getInputData(), NodeType.INPUT);
		}
		for (HiddenNode Hidden : connectedHiddenNodes) {
			Hidden.setHiddenValueInNode(Hidden.HiddenValue, NodeType.HIDDEN);
		}
		for (Output out : dataout.keySet()) {
			out.setValue(new Value(HiddenValue.getValue()));
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
			this.HiddenValue = value;
			break;
		case INPUT:
			this.HiddenValue = value;
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

}
