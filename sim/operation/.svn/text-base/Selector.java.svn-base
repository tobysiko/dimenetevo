package sim.operation;

import sim.datatype.NetworkVector;


/**
 * The Selector forms the transition between two generations of the evolutionary process.
 * <p><br>It selects the networks for the next generation according to their fitness.</p>
 * @author sikosek
 *
 */
public interface Selector {
	
	/**
	 * This method has to implemented by all Selectors. It specifies how exactly the selection process works and is accessed from outside classes.
	 * @param population The population to be transformed from one generation to the next.
	 */
	public abstract NetworkVector transformPopulation(NetworkVector population);
}
