package sim.datatype;

import java.io.Serializable;




/**
 * @uml.stereotype uml_id="Standard::Type"
 */
public abstract class AbstractSimulationState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @uml.property name="population"
	 */
	private NetworkVector population;

	/**
	 * @uml.property name="generation"
	 */
	private int generation;

	public AbstractSimulationState(NetworkVector pop, int gen) {
		this.population = pop;
		this.generation = gen;
	}

	/**
	 * Getter of the property <tt>generation</tt>
	 * 
	 * @return Returns the generation.
	 * @uml.property name="generation"
	 */
	public int getGeneration() {
		return generation;
	}

	/**
	 * Getter of the property <tt>population</tt>
	 * 
	 * @return Returns the population.
	 * @uml.property name="population"
	 */
	public NetworkVector getPopulation() {
		return population;
	}

	/**
	 * Setter of the property <tt>generation</tt>
	 * 
	 * @param generation
	 *            The generation to set.
	 * @uml.property name="generation"
	 */
	public void setGeneration(int generation) {
		this.generation = generation;
	}

	/**
	 * Setter of the property <tt>population</tt>
	 * 
	 * @param population
	 *            The population to set.
	 * @uml.property name="population"
	 */
	public void setPopulation(NetworkVector population) {
		this.population = population;
	}

}
