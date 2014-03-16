package me.dylan.NNL.Utils;

import java.util.ArrayList;

import me.dylan.NNL.Test.TestUtil;

public class StringUtil {
	public static double compareStrings(String stringA, String stringB) {
		ArrayList<String> pairs1 = getLetterPairsFromWords(stringA);
		ArrayList<String> pairs2 = getLetterPairsFromWords(stringB);
		int intersection = 0;
		int union = pairs1.size() + pairs2.size();
		for (int i = 0; i < pairs1.size(); i++) {
			Object pair1 = pairs1.get(i);
			for (int j = 0; j < pairs2.size(); j++) {
				Object pair2 = pairs2.get(j);
				if (pair1.equals(pair2)) {
					intersection++;
					pairs2.remove(j);
					break;
				}
			}
		}
		return ((2.0 * intersection) / (union));
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
			for (int i = 0; i < (numPairs % 3); i++) {
				pairs[roundedPairCount + i] = ""
						+ str.charAt(roundedPairCount + i);
			}
		}
		ArrayList<String> formatTemp = new ArrayList<String>();
		for (String s : pairs) {
			if (s != null && s.length() > 0)
				formatTemp.add(s);
		}
		return formatTemp.toArray(new String[formatTemp.size()]);
	}

	/**
	 * Takes a string and breaks it up into groups of three letters
	 * 
	 * @param str
	 *            the string to be broken up into letters
	 * @return the list that contains the newly formed triplets of letters
	 */
	public static ArrayList<String> getLetterPairsFromWords(String str) {
		ArrayList<String> pairs = new ArrayList<String>();
		for (String s : str.split("\\s")) {
			if (!s.isEmpty())
				for (String pair : extractLetterTriplets(s)) {
					pairs.add(pair);
				}
		}
		return pairs;
	}

	/**
	 * Compares two strings and returns a percentage of how similar they are
	 * 
	 * @param a
	 *            first string to be compared to second
	 * @param b
	 *            second string to be compared to first
	 * @return The percentage of match
	 */
	public static double calculateStringSimilarityPercentage(String a, String b) {
		return compareStrings(a, b);
	}
}