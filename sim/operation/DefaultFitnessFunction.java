/**
 * 
 */
package sim.operation;

import java.util.Date;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import sim.Parameters;
import sim.datatype.Network;
import sim.datatype.NetworkVector;

/**
 * @author sikosek
 *
 */
public class DefaultFitnessFunction implements FitnessFunction {

	protected Parameters p;
//	random number generator
	protected RandomEngine generator = new MersenneTwister(new Date());
	
//	uniform distribution
	protected Uniform uniform = new Uniform(generator);
	
	
	public DefaultFitnessFunction(Parameters p){
		this.p = p;
	}
	/* (non-Javadoc)
	 * @see sim.operation.FitnessFunction#assignFitness(sim.datatype.NetworkVector, double[])
	 */
	public void assignFitness(NetworkVector pop, double[] popFitness) {
		
//		number of attractors of a network
		double attractors = 0;
		
//		number of genes in a network
		double genes = 0;
		double elements = 0;
		double nonisolated = 0;
		double isolated = 0;
		double gt = 0;
		double gt_term = 0;
		double genes_term  = 0;
		double isolated_term = 0;
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
				gt = Math.pow(genes, 1.2) / attractors;
//				System.out.println("fitness0: " +popFitness[i]);
//				System.out.println("attr: "+attractors+ " - genes: " +genes+ " - g/a: " +gt);
//				System.out.println("opt.g/a: "+ p.OPTIMAL_GT_RATIO + " - (opt.g/a - g/a): " +(p.OPTIMAL_GT_RATIO - gt));
				
				gt_term = (1 / (1 + Math.abs(p.OPTIMAL_GT_RATIO - gt)));  
				gt_term *= p.RATIO_TERM_PROPORTION;
				
//				System.out.println("gt_term: " +gt_term);
				
				genes_term = (1 / (1 + Math.abs(p.OPTIMAL_GENE_NUMBER - genes)));
				genes_term *= p.GENES_TERM_PROPORTION;
				
//				System.out.println("genes_term: " +genes_term);
				
				isolated_term = (1 / (1 + Math.pow(isolated, 2)));
				isolated_term *= p.ISOLATED_TERM_PROPORTION;
				
//				System.out.println("isolated_term: " +isolated_term);
				
				popFitness[i] = gt_term + genes_term + isolated_term;
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
//			System.out.println("fitness assigned "+net.getProcess().getGeneration());
		}

		
	}

}
