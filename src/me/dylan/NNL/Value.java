package me.dylan.NNL;

/**
 * A simple class used for storing values of neural inputs and outputs, this is
 * meant for a generalized network (non-specific inputs and outputs, for
 * example, in a simulated animal, it would be an advantage to have as much
 * information as possible. such as all the pixels in the FOV instead of
 * something more traditional, like all other animals in FOV)
 * 
 * 
 */
public class Value {
    String data = "";

    // private float min = 0f;
    // private float max = 1f;
    // public Object extraInfo;

    /**
     * Initialize a blank-slate Value object.
     */
    public Value() {
    }

    /**
     * Initialize a Value object with the given float as the current value.
     * 
     * @param value
     */
    public Value(String value) {
	this.setValue(value);
    }

    public String getValue() {
	return data;
    }

    public void setValue(String value) {

	this.data = value;
    }

    // public float getMin() {
    // return min;
    // }
    //
    // public void setMin(float min) {
    // this.min = min;
    // }
    //
    // public float getMax() {
    // return max;
    // }
    //
    // public void setMax(float max) {
    // this.max = max;
    // }

    public Value appendToValue(Value value) {
	Value out = new Value();
	// try {
	out.setValue(this.getValue() + value.data);
	// } catch (OutOfMemoryError ex) {
	// ex.printStackTrace();
	// // System.out.println(this.getValue());
	// }
	return out;
    }

    @Override
    public String toString() {
	return data;
    }
}
