package src.me.dylan.NNL;

import java.util.Random;

public class NNLib {
	public static Random rand = new Random();
	public static NNLib instance = new NNLib();

	public enum NodeType {
		INPUT, OUTPUT, HIDDEN;
	}
}
