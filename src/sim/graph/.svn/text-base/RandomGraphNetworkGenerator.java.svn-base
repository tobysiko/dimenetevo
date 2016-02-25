/**
 * 
 */
package sim.graph;

import java.util.Date;

import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Network;
import sim.operation.NetworkGenerator;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

/**
 * @author sikosek
 *
 */
public class RandomGraphNetworkGenerator implements NetworkGenerator {
	
//	random number generator
	private RandomEngine generator = new MersenneTwister(new Date());
	
//	uniform distribution
	private Uniform uniform = new Uniform(generator);
	
//	program parameters
	private Parameters p;
	private EvolutionaryProcess process;
	
	/**
	 * Sets the paramters attribute.
	 * @param p parameters
	 * @see sim.Parameters
	 */
	public RandomGraphNetworkGenerator(EvolutionaryProcess process){
		this.process = process;
		p = process.getParm();
	}
	public RandomGraphNetworkGenerator(Parameters p){
		this.p = p;
	}

	
	/* (non-Javadoc)
	 * @see sim.NetworkGenerator#generateNewNetwork()
	 */
	public Network generateNewNetwork(){
		
		if (p.MINIMAL_OUTPUT) System.out.println("CALLED: RandomGraphNetworkGenerator#generateNewNetwork(Parameters p)");
		
//		generates the actual network of size specified in p
		Network net = new GraphNetwork(process);
		
//		for each gene in the network ...
		for (int i = 0; i < net.size(); i++){
			
//			add first random regulatory input
			if(uniform.nextFloatFromTo(0.0f, 1.0f) <= p.RNG_P_FIRST_INPUT){ 
				net.addRegulatoryInteraction(net.get(uniform.nextIntFromTo(0, net.size()-1)), uniform.nextBoolean(), net.get(i));
				
//				add second random regulatory input
				if(uniform.nextFloatFromTo(0.0f, 1.0f) <= p.RNG_P_SECOND_INPUT){ 
					net.addRegulatoryInteraction(net.get(uniform.nextIntFromTo(0, net.size()-1)), uniform.nextBoolean(), net.get(i));
				}
			}
		}
		
//		return the new network ...
		return net;
	}

}
