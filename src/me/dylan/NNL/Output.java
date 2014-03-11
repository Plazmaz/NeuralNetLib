package me.dylan.NNL;

import java.awt.Color;

import me.dylan.NNL.NNLib.NodeType;
import me.dylan.NNL.Utils.StringUtil;

public class Output extends Node {
	/**
	 * Initialize a blank output, with no initial value
	 */
	public Output() {
		this(new Value());
	}

	/**
	 * Initialize an output with the specified value
	 * 
	 * @param value
	 */
	public Output(Value value) {
		this.information = value;
		nodeVariety = NodeType.OUTPUT;
		this.setDisplayColor(Color.GREEN);
	}

	/**
	 * Get the value of this output
	 * 
	 * @return The power/value of the output. --may not be accurate currently
	 *         3/10/14
	 */
	// TODO: how can you be sure information contains only the output value that
	// we need?
	public Value getOutputValue() {
		return information;
	}

	/**
	 * Takes the synapse and determines if the information that came from origin
	 * fits into the output properly. Then it determines what to do with the
	 * data based on its percentage match with desired output
	 * 
	 * @param connector
	 *            The synapse that links the output node to the one or more
	 *            hidden nodes that are apart of the current output
	 */
	public void putOrMove(Synapse connector) {
		String infoData = information.getData();
		this.spikeWithInput(connector);
		String value = connector.getConnectionOrigin().getNodeInfo().getData();
		if (!value.isEmpty() && information.getData().contains(value)) {
			int valueIndex = infoData.indexOf(value);
			information.setValue(infoData.substring(valueIndex, valueIndex
					+ value.length()));
			information.appendToValue(new Value(value));
		} else {
			setNodeData(information.getData() + value);
		}
		if (StringUtil.calculateStringSimilarityPercentage(
				connector.desiredOutput, infoData) < 90) {
			System.out.println("Output invalid, backtracing.");
			connector.setPulseBack(true);
			connector.getConnectionOrigin().setActive(false);
			connector.getConnectionDestination().spikeWithInput(connector);
			cleanupDamage(connector);
			setNodeData("");

		}
	}
}
