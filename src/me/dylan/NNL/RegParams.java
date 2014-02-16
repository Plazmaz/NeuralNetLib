package me.dylan.NNL;

public class RegParams {
	public static String regParamsDel = "teaspoons?, tablespoons?, tons?, gallons?, bowls?, (milli)?lit(er|re)?s?, (kili)?gram(me)?s? cups?, quarts?, chopp(ed)?, cook(ed)?, roast(ed)?, ";

	public static void addParam(String param) {
		regParamsDel += param + ", ";
	}
	
	public static void rmvParam(String param) {
		param.replace(param, "");
	}
}