/**
 * 
 */
package sim.datatype;

import java.io.Serializable;
import java.util.Collection;


/** 
 * @author sikosek
 */
public class ElementVector extends MyVector<Element> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ElementVector() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 */
	public ElementVector(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param c
	 */
	public ElementVector(Collection<Element> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 * @param capacityIncrement
	 */
	public ElementVector(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
		// TODO Auto-generated constructor stub
	}

	public synchronized Element get(int index){
		return (Element) super.get(index);
	}
	
	public Object clone(){
		ElementVector clone = new ElementVector();
		
		try {
//			clone.removeAllElements();
			for (Element e:this){
				clone.add((Element)e.clone());
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clone;
	}
}
