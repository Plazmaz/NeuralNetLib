package src.me.dylan.NNL;

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
	private float value = 0f;
	private float min = 0f;
	private float max = 1f;
	public Object extraInfo;

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
	public Value(float value) {
		this.setValue(value);
	}

	/**
	 * Initialize a Value object with value and maximum parameters.
	 * 
	 * @param value
	 *            The current value
	 * @param max
	 *            The maximum possible value
	 */
	public Value(float value, float max) {
		this.setValue(value);
		this.setMax(max);
	}

	/**
	 * Initialize a Value object with value, minimum, and maximum parameters.
	 * 
	 * @param value
	 * @param max
	 * @param min
	 */
	public Value(float value, float max, float min) {
		this.setMax(max);
		this.setMin(min);
		this.setValue(value);
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {

		this.value = MathUtil.clamp(getMin(), getMax(), value);
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public void avg(Value value) {
		float val1 = getValue();
		float val2 = value.getValue();
		this.value = (val1 + val2) / 2;
	}

	@Override
	public String toString() {
		return value + "";
	}
}
