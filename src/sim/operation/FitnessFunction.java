/**
 * 
 */
package sim.operation;

import sim.datatype.NetworkVector;

/**
 * @author sikosek
 *
 */
public interface FitnessFunction {
	/**
	 * Determines the fitness of all networks in a population according to the fitness function (see class discription above).
	 * @param pop The NetworkVector that is the population of networks.
	 * @param popFitness A <tt>double</tt> array that will contain the fitness values of all networks. Its indices correspond to the indices of the NetworkVector that is the population.
	 */
	public abstract void assignFitness(NetworkVector pop, double[] popFitness);

}
