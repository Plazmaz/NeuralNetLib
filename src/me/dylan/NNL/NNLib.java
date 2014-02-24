package me.dylan.NNL;

import java.util.Random;

public class NNLib {
    public static int CHANCE_FOR_HIDDEN_CONNECTION = 40;
    public static Random GLOBAL_RANDOM = new Random();
    public static NNLib LIBRARY_INSTANCE = new NNLib();
    public static int MAX_CONNECTION_WEIGHT = 40;
    public static int SYNAPSE_WEIGHT_PROGRESSION_THRESHOLD = 30;
    public static int CHANCE_FOR_IO_CONNECTION = 5;

    public enum NodeType {
	INPUT, OUTPUT, HIDDEN;
    }
}
