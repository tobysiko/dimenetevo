package sim.operation.function;

import sim.Parameters;
import sim.datatype.Network;


/**
 * Classes implementing this interface will be used to determine the ON/OFF state of an element in the network depending on its inputs.
 * @author sikosek
 */
public interface BooleanFunction {

	/**
	 * Sets the ON/OFF state of the specified element in the network according to specific Boolean rules implemented in this method.
	 * @param net The network.
	 * @param elementindex The index of the element in the network.
	 * @return The new Boolean state of the element. (true = ON, false=OFF)
	 */
	public abstract boolean integrate(Network net, int elementindex);
	
	public Object clone() throws CloneNotSupportedException;
	
	/**
	 * Sets the property Parameters.
	 * @param p
	 */
	public abstract void setParameters(Parameters p);
}
