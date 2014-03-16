package me.dylan.NNL;

/**
 * A simple class used for storing values of neural inputs and outputs, this is
 * meant for a generalized network (non-specific inputs and outputs, for
 * example, in a simulated animal, it would be an advantage to have as much
 * information as possible. such as all the pixels in the FOV instead of
 * something more traditional, like all other animals in FOV)
 * 
 */
// TODO: REVIEW AND POSSIBLY REMOVE VALUE CLASS - CONVERT ALL VALUE OBJECTS TO
// STRING IN OTHER CODE
public class Value {
	private String data = "";

	/**
	 * Initialize a blank-slate Value object.
	 */
	public Value() {
	}

	/**
	 * Initialize a Value object with the given string as the current value.
	 * 
	 * @param value
	 *            The string used to create the Value object
	 */
	public Value(String value) {
		this.setValue(value.intern());
	}

	/**
	 * Checks to see if there is currently data contained in Value and then
	 * returns the data or an error
	 * 
	 * @return the data variable of Value which contains the raw data for Value
	 */
	// TODO: Craig: Should check if null then return 'data' or an error message
	public String getData() {
		return data;
	}

	/**
	 * Sets the information in Value. Also checks to make sure that string
	 * doesnt already exist
	 * 
	 * @param value
	 *            the information that will be added to value
	 */
	public void setValue(String value) {

		this.data = value.intern();
	}

	/**
	 * Takes a Value and adds it to the existing data inside of Value. Does not
	 * delete the current Value data.
	 * 
	 * @param value
	 *            The information to be added onto data in Value
	 * @return the new Value containing the new information added into the old
	 *         information in Value
	 */
	public Value appendToValue(Value value) {
		Value out = new Value();
		// try {
		out.setValue(this.getData() + value.data);
		// } catch (OutOfMemoryError ex) {
		// ex.printStackTrace();
		// // System.out.println(this.getValue());
		// }
		return out;
	}

	@Override
	@Deprecated
	// Deprecated on 3/16/14
	public String toString() {
		return data;
	}
}
