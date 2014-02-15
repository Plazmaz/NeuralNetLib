package me.dylan.NNL;


public class MathUtil {
	public static int clamp(int min, int max, int val) {
		int value = val;
		if (value < min)
			value = min;
		if (value > max)
			value = max;
		return value;
	}

	public static float clamp(float min, float max, float val) {
		float value = val;
		if (value < min)
			value = min;
		if (value > max)
			value = max;
		return value;
	}

	public static double clamp(double min, double max, double val) {
		double value = val;
		if (value < min)
			value = min;
		if (value > max)
			value = max;
		return value;
	}

	public static float generateRandFloat(float min, float max) {
		return (NNLib.rand.nextFloat() * (max - min)) + min;
	}
}
