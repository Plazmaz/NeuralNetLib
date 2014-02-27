package me.dylan.NNL;

import java.util.Random;

public class NNLib {
    public static final int CHANCE_FOR_INPUT_ACTIVATION = 30;
    public static int CHANCE_FOR_HIDDEN_CONNECTION = 40;
    public static Random GLOBAL_RANDOM = new Random();
    public static NNLib LIBRARY_INSTANCE = new NNLib();
    public static int MAX_CONNECTION_WEIGHT = 40;
    public static double SYNAPSE_WEIGHT_PROGRESSION_THRESHOLD_MULTIPLIER = 1.25;
    public static int CHANCE_FOR_IO_CONNECTION = 50;
    /*
     * This is a multiplier of the original percentage
     */
    public static double SYNAPSE_MULTIPLIER_PER_GENERATION = 1.2;
    public static int SYNAPSE_MUTATION_CHANCE = 80;

    public enum NodeType {
	INPUT, OUTPUT, HIDDEN;
    }
}
