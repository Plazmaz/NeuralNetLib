package me.dylan.NNL.Test;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import me.dylan.NNL.NNetwork;
import me.dylan.NNL.Node;

public class TestUtil {
    public static boolean AnyNodesExist(NNetwork networkToCheck) {
	if (networkToCheck.getNodesInNetwork().isEmpty()) {
	    System.out.println("No nodes found!");
	    return false;
	}
	System.out.println("Nodes found!");
	return true;
    }

    public static void WhatNodesExist(NNetwork networkToCheck) {
	int hiddenCount = 0, inputCount = 0, outputCount = 0;

	for (Node netNode : networkToCheck.getNodesInNetwork()) {
	    switch (netNode.getNodeVariety()) {
	    case HIDDEN:
		hiddenCount++;
		break;
	    case INPUT:
		inputCount++;
		break;
	    case OUTPUT:
		outputCount++;
		break;
	    default:
		JOptionPane.showMessageDialog(null, new JTextField(
			"WHAT HAPPENED?!?!?! In WhatNodesExist"));
	    }
	}
	System.out.println("Hidden: " + hiddenCount);
	System.out.println("Inputs: " + inputCount);
	System.out.println("Outputs: " + outputCount);

    }

    public static boolean StringSizeIsNotZero(String value) {
	return value.length() > 0;
    }

    public static boolean AHiddenNodeHasValue(NNetwork networkToCheck) {
	return !networkToCheck.getHiddenNodesInNetwork().isEmpty();
    }
}
