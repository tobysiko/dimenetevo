package sim.operation;

import java.util.Date;

import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.MatrixNetwork;
import sim.datatype.Network;

import cern.jet.random.*;
import cern.jet.random.engine.*;

/** 
 * Creates a new "blank" network and adds up to two random regulatory inputs per node (see parameters). No dimers are added.
 * @see sim.Parameters
 * @uml.stereotype uml_id="Standard::Utility" 
 * @uml.stereotype uml_id="Standard::ImplementationClass" 
 */
public class RandomNetworkGenerator implements NetworkGenerator {
	
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
	public RandomNetworkGenerator(EvolutionaryProcess process){
		this.process = process;
		p = process.getParm();
	}
	
	
	/* (non-Javadoc)
	 * @see sim.NetworkGenerator#generateNewNetwork()
	 */
	public MatrixNetwork generateNewNetwork(){
		
		if (p.MINIMAL_OUTPUT) System.out.println("CALLED: RandomNetworkGenerator#generateNewNetwork(Parameters p)");
		
//		generates the actual network of size specified in p
		MatrixNetwork net = new MatrixNetwork(process);
		
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
