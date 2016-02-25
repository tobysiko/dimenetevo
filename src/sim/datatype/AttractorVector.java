/**
 * 
 */
package sim.datatype;

import java.util.Collection;

/**
 * @author sikosek
 *
 */
public class AttractorVector extends MyVector<Attractor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public AttractorVector() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 */
	public AttractorVector(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param c
	 */
	public AttractorVector(Collection<Attractor> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 * @param capacityIncrement
	 */
	public AttractorVector(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
		// TODO Auto-generated constructor stub
	}
	public Object clone(){
		AttractorVector clone = new AttractorVector();
		
		for (Attractor a:this){
			clone.add((Attractor)a.clone());
		}
		return clone;
	}

}
