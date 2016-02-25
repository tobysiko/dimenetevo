/**
 * 
 */
package sim.datatype;

import java.util.Collection;


/**
 * @author sikosek
 *
 */
public class TimeStepSeries extends MyVector<boolean[]> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TimeStepSeries() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 */
	public TimeStepSeries(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param c
	 */
	public TimeStepSeries(Collection<boolean[]> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 * @param capacityIncrement
	 */
	public TimeStepSeries(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
		// TODO Auto-generated constructor stub
	}
	public String toString(){
		String str = "";
		for(int i = 0; i < this.size(); i++){
			str += this.toString(i);
		}
		
		return str;
	}

	public String toString(int index){
		String str = "\n";
		boolean[] b = this.get(index);
		str += "\t";
		for(int i = 0; i < b.length; i++){
			if (b[i]) str += '1';
			else str += '0';
		}
		str += " [" +index+ "]";
		
		
		return str;
	}
}
