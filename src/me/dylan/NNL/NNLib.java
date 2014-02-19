package me.dylan.NNL;

import java.util.Random;

public class NNLib {
	public static Random rand = new Random();
	public static NNLib instance = new NNLib();
	public static int MAX_NODE_WEIGHT = 20;
	public enum NodeType {
		INPUT, OUTPUT, HIDDEN;
	}
}
