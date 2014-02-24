package me.dylan.NNL.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dylan.NNL.Test.TestUtil;

public class StringUtil {
    public static double calculateArraySimilarityPercentage(String[] array1,
	    String[] array2) {
	int conflict = 0;
	int shortestLength = Math.min(array1.length, array2.length), longestLength = Math
		.max(array1.length, array2.length);
	conflict = longestLength - shortestLength;
	for (int i = 0; i < shortestLength; i++) {
	    String s = array1[i];
	    String s2 = array2[i];

	    if (!s.equalsIgnoreCase(s2)) {
		conflict++;
		// break;
	    }
	}
	double similarity = 100d - (((double) conflict / (double) longestLength) * 100d);
	if (similarity > 0) {
	    return similarity;
	}

	return 0;
    }

    /*
     * The following methods are related to the StrikeAMatch method.
     * http://www.catalysoft.com/articles/StrikeAMatch.html
     */
    private static String[] extractLetterTriplets(String str) {
	if (!TestUtil.StringSizeIsNotZero(str)) {
	    System.out
		    .println("String explosion!!! Size is zero or less! POR QUE?!?!?!?");
	}

	int numPairs = str.length();
	String[] pairs = new String[numPairs];
	int roundedPairCount = (int) (Math.ceil(numPairs / 3) * 3);
	for (int i = 0; i < roundedPairCount; i += 3) {
	    pairs[Math.round(i / 3)] = str.substring(i, i + 3);
	}
	if (numPairs % 3 != 0) {
	    for (int i = 0; i < (numPairs % 3); i++) { // overflow
						       // letters(single
		pairs[roundedPairCount + i] = ""
			+ str.charAt(roundedPairCount + i); // and
		// double
		// pairs)
	    }
	}
	ArrayList<String> formatTemp = new ArrayList<String>();
	for (String s : pairs) {
	    if (s != null && s.length() > 0)
		formatTemp.add(s);
	}
	return formatTemp.toArray(new String[formatTemp.size()]);
    }

    @Deprecated
    public static ArrayList<String> getLetterPairsFromWords(String str) {
	ArrayList<String> pairs = new ArrayList<String>();
	for (String s : str.split("\\s")) {
	    for (String pair : extractLetterTriplets(s)) {
		pairs.add(pair);
	    }
	}
	return pairs;
    }

    public static double calculateStringSimilarityPercentage(String a, String b) {
	List<String> aArr = Arrays
		.asList(extractLetterTriplets(a.toUpperCase()));
	List<String> bArr = Arrays
		.asList(extractLetterTriplets(b.toUpperCase()));
	return calculateArraySimilarityPercentage(
		aArr.toArray(new String[aArr.size()]),
		bArr.toArray(new String[aArr.size()]));
    }
}
