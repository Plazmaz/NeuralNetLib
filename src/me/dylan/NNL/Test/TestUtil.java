package me.dylan.NNL.Test;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import me.dylan.NNL.NNetwork;
import me.dylan.NNL.Node;

/**
 * Neural Network Testing Utility for Neuroticz
 * 
 */
public class TestUtil {
	/**
	 * Checks to see if there are any nodes in existence in the network
	 * 
	 * @return True or False
	 */
    public static boolean AnyNodesExist(NNetwork networkToCheck) {
    	if (networkToCheck.getNodesInNetwork().isEmpty()) {
    		System.out.println("No nodes found!");
    		return false;
    	}
    	System.out.println("Nodes found!");
    	return true;
    }
    
    public static boolean InputNodesExist(NNetwork networkToCheck) {
    	if (networkToCheck.getInputNodesInNetwork().isEmpty()) {
    		System.out.println("No Input Nodes Exist In Network!");
    		return false;
    	}
    	return true;
    }
    
    public static boolean OutputNodesExist(NNetwork networkToCheck) {
    	if (networkToCheck.getOutputNodesInNetwork().isEmpty()) {
    		System.out.println("No Output Nodes Exist In Network!");
    		return false;
    	}
    	return true;
    }
    
    public static boolean HiddenNodesExist(NNetwork networkToCheck) {
    	if (networkToCheck.getHiddenNodesInNetwork().isEmpty()) {
    		System.out.println("No Hidden Nodes Exist In Network!");
    		return false;
    	}
    	return true;
    }

    /**
     * Verifies that the proper amount of Synapses exist inside of the network
     * 
     * @return True or False
     */
    public static boolean IsSynapseCountProper(NNetwork networkToCheck) {
    	boolean isSynapseCountCorrect = networkToCheck.getNetworkSynapses()
    			.size() == Math.pow((networkToCheck.getHiddenNodesInNetwork().size() + 1), 2) - 1;	// the -1 is needed
    																								//due to the missing
    																								//connection(input to output)
	return isSynapseCountCorrect;
    }
    
    /**
     * Checks to see what kinds (Input, Hidden, Output) nodes exist within the network
     * 		Outputs the findings to console
     * 
     */
  
    // ******		To Upgrade:			******
    // Update to return the node count to the visualizer
    // Print the results in the visualizer window
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
		System.out.println("Synapses: " + networkToCheck.getNetworkSynapses().size());
    }
    
    /**
     * Verify that the data in the string is greater then zero. 
     * This Helps to verify that we are not passing empty lines to a node; saving resources
     * 
     * @return True or False
     */
    public static boolean StringSizeIsNotZero(String value) {
    	return value.length() > 0;
    }
    
    /**
     * Verifies that the Hidden Node has a value stored in it.
     * 
     * @return True or False
     */
    public static boolean AHiddenNodeHasValue(NNetwork networkToCheck) {
    	return !networkToCheck.getHiddenNodesInNetwork().isEmpty();
    }
    
    /* Warning: Checks from here below are ideas by Craig and may not function properly yet
     * 
     * Notes: No current tests need to be implemented. Will update in future
    */
    
    
}
