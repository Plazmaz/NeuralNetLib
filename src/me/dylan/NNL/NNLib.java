package me.dylan.NNL;

import java.util.Random;

//TODO: Dylan: Take a look, and possibly removed, all not used
public class NNLib {
	public static final int CHANCE_FOR_INPUT_ACTIVATION = 30; // not used
	public static double WEIGHT_DECREASE_ON_MISMATCH = 1;
	public static double WEIGHT_INCREASE_ON_MATCH = 1;
	public static int CHANCE_FOR_HIDDEN_CONNECTION = 40;
	public static Random GLOBAL_RANDOM = new Random();
	public static NNLib LIBRARY_INSTANCE = new NNLib(); // not used
	public static int MAX_CONNECTION_WEIGHT = 10;
	public static int CHANCE_FOR_IO_CONNECTION = 50;
	public static double SYNAPSE_MULTIPLIER_PER_GENERATION = 1; // not used
	public static int SYNAPSE_ADDITION_MUTATION_CHANCE = 0; // not used
	public static int SYNAPSE_WEIGHT_MUTATION_CHANCE = 10; // not used

	public enum NodeType {
		INPUT, OUTPUT, HIDDEN;
	}
}
