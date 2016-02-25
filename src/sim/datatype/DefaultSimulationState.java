package sim.datatype;

import java.io.Serializable;


/** 
 * Used for transporting and storing the states of running simulations. 
 * Contains the generation number the simulation is (was) in and the entire population of networks at that time.
 * @uml.stereotype uml_id="Standard::Type" 
 * @uml.stereotype uml_id="Standard::Realization" 
 */
public class DefaultSimulationState extends AbstractSimulationState implements Serializable{

	private static final long serialVersionUID = 1L;
	public DefaultSimulationState(){
		super(new NetworkVector(), 0);
	}
	public DefaultSimulationState(NetworkVector pop, int gen){
		super(pop,gen);
	}
	
}
