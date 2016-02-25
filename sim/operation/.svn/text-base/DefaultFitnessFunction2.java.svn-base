/**
 * 
 */
package sim.operation;

import java.util.Date;

import sim.Parameters;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
/**
 * @author sikosek
 *
 */
public class DefaultFitnessFunction2 implements FitnessFunction {


	protected Parameters p;
//	random number generator
	protected RandomEngine generator = new MersenneTwister(new Date());
	
//	uniform distribution
	protected Uniform uniform = new Uniform(generator);
	
	
	public DefaultFitnessFunction2(Parameters p){
		this.p = p;
	}
public void assignFitness(NetworkVector pop, double[] popFitness) {
		
//		number of attractors of a network
		int attractors = 0;
		
//		number of genes in a network
		int genes = 0;
		int elements = 0;
		int nonisolated = 0;
		int isolated = 0;
//		a network
		Network net;
		
//		for each network in the population ...
		for (int i = 0; i < popFitness.length; i++){
			
//			get network i from population
			net = pop.get(i);
			
//			get network's attractors TODO how to make sure attractors have already been collected?
			attractors = net.getDataCollector().getAttractors();
			
//			get network's regulators
			nonisolated = net.getNonIsolatedGenes().size();
			
			genes = net.getGeneIndices().size();
			isolated = net.getIsolated().size();
			elements = net.getElementVector().size();
			
//			calculating fitness
			if (attractors < p.VIABLE_MIN_ATTRACTORS || genes < p.VIABLE_MIN_GENES)
				
//				too few attractors and/or genes = lethal condition, zero fitness
				popFitness[i] = 0;
			else{
				
				popFitness[i] = ( p.RATIO_TERM_PROPORTION 		* (1 / (1 + Math.abs(p.OPTIMAL_ATTR_NUM - attractors))) );  
//				System.out.println("attr: " +attractors+ "fitness1: " +popFitness[i]);
				popFitness[i]+= ( p.GENES_TERM_PROPORTION 		* (1 / (1 + Math.abs(p.OPTIMAL_GENE_NUMBER - genes))));
//				System.out.println("fitness2: " +popFitness[i]);
			}
			
//			if enabled, alters fitness value as specified in parameters. 
//			coin-toss decides, whether noise is added or subtracted.
			if (p.ADD_NOISE_TO_FITNESS) {
				double noise = uniform.nextDoubleFromTo(0.0, p.FITNESS_NOISE_MAXIMUM);
				if (uniform.nextBoolean())
					popFitness[i] += noise;
				else
					popFitness[i] -= noise;
			}
			
//			adds current fitness value to fitness history of network
			net.getDataCollector().addToFitnessHistory(popFitness[i]);
		}

		
	}
	
	

}
