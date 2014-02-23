package me.dylan.NNL.Utils;

import java.util.ArrayList;

import me.dylan.NNL.NNetwork;

public class ArrayUtil {
	public static ArrayList<NNetwork> shiftNetworkArray(ArrayList<NNetwork> objects, int indexOffset) {
		ArrayList<NNetwork> shifted = new ArrayList<NNetwork>();
		for(int i = 0; i < indexOffset; i++) {
			shifted.add(null);
		}
		shifted.addAll(objects);
		return shifted;
	}
}
