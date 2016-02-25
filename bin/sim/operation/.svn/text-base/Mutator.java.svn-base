package sim.operation;

import sim.Parameters;
import sim.datatype.Network;
import sim.datatype.NetworkVector;


public interface Mutator {

	/**
	 * Introduces mutations to every network with the probabilities given in the <tt>Parameters</tt> object.
	 * @see sim.Parameters
	 * @param p the program parameters.
	 * @param population vector of all networks.
	 */
	public abstract void mutatePopulation(Parameters p, NetworkVector population);
	public abstract boolean formNewHomodimer(Network net);
}
