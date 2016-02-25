/**
 * 
 */
package sim.datatype;

import java.util.Collection;

/** 
 * @author sikosek
 * @uml.stereotype uml_id="Standard::Type" 
 */
public class MyVector<E> extends java.util.Vector<E> implements Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MyVector() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 */
	public MyVector(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param c
	 */
	public MyVector(Collection<E> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 * @param capacityIncrement
	 */
	public MyVector(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public synchronized Object clone(){
//		MyVector<E> clone = (MyVector<E>)super.clone();
//		
//		
//		return clone;
//	}
	
}
