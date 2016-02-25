/**
 * 
 */
package sim.datatype;

/**
 * @author sikosek
 *
 */
public class TwowayInteraction {
	private byte value;
	private int source, target;
	
	
	
	/**
	 * @param value
	 * @param source
	 * @param target
	 */
	public TwowayInteraction(byte value, int source, int target) {
		super();
		this.value = value;
		this.source = source;
		this.target = target;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public byte getValue() {
		return value;
	}
	public void setValue(byte value) {
		this.value = value;
	}
}
