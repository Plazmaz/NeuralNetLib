package me.dylan.NNL.Utils;

import java.util.ArrayList;

import me.dylan.NNL.NNetwork;

@Deprecated
//Nothing in class is being used
//Deprecated on 3/16/14
public class ArrayUtil {
	
	@Deprecated
	//Deprecated on 3/16/14
	public static ArrayList<NNetwork> shiftNetworkArray(
			ArrayList<NNetwork> objects, int indexOffset) {
		ArrayList<NNetwork> shifted = new ArrayList<NNetwork>();
		for (int i = 0; i < indexOffset; i++) {
			shifted.add(null);
		}
		shifted.addAll(objects);
		return shifted;
	}

	/**
	 * Takes the passed objects, and list, and swaps the two objects within the
	 * list
	 * 
	 * @param item1
	 *            First object to be swaped with second
	 * @param item2
	 *            Second object to be swaped with first
	 * @param list
	 *            The list that contains the objects to be swaped
	 */
	@Deprecated
	//Deprecated on 3/16/14
	public static void swapItems(Object item1, Object item2, ArrayList list) {
		int item1index = list.indexOf(item1);
		int item2index = list.indexOf(item2);
		list.set(item1index, item2);
		list.set(item2index, item1);
	}
}
