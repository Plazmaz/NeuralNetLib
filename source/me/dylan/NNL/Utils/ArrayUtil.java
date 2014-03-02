package me.dylan.NNL.Utils;

import java.util.ArrayList;

import me.dylan.NNL.NNetwork;

public class ArrayUtil {
    public static ArrayList<NNetwork> shiftNetworkArray(
	    ArrayList<NNetwork> objects, int indexOffset) {
	ArrayList<NNetwork> shifted = new ArrayList<NNetwork>();
	for (int i = 0; i < indexOffset; i++) {
	    shifted.add(null);
	}
	shifted.addAll(objects);
	return shifted;
    }

    public static void swapItems(Object item1, Object item2, ArrayList list) {
	int item1index = list.indexOf(item1);
	int item2index = list.indexOf(item2);
	list.set(item1index, item2);
	list.set(item2index, item1);

    }
}
