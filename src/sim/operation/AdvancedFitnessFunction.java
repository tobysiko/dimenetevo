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
public class AdvancedFitnessFunction implements FitnessFunction {


	protected Parameters p;
//	random number generator
	protected RandomEngine generator = new MersenneTwister(new Date());
	
//	uniform distribution
	protected Uniform uniform = new Uniform(generator);
	
	
	public AdvancedFitnessFunction(Parameters p){
		this.p = p;
	}
	public void assignFitness(NetworkVector pop, double[] popFitness) {
		
	//	number of attractors of a network
		double attractors = 0;
		
	//	number of genes in a network
		double genes = 0;
		double elements = 0;
		double nonisolated = 0;
		double isolated = 0;
		double gt = 0;
		double gt_term = 0;
		double genes_term  = 0;
		double isolated_term = 0;
		double subgraph_term = 0;
		double nSub = 0;
	//	a network
		Network net;
		
	//	for each network in the population ...
		for (int i = 0; i < popFitness.length; i++){
			
	//		get network i from population
			net = pop.get(i);
			
	//		get network's attractors TODO how to make sure attractors have already been collected?
			attractors = net.getDataCollector().getAttractors();
			
	//		get network's regulators
			nonisolated = net.getNonIsolatedGenes().size();
			
			genes = net.getGeneIndices().size();
			isolated = net.getIsolated().size();
			elements = net.getElementVector().size();
			nSub = net.getDataCollector().getSubGraphs();
	//		calculating fitness
			if (nSub == 0 || attractors <= p.VIABLE_MIN_ATTRACTORS || genes <= p.VIABLE_MIN_GENES){
	//			too few attractors and/or genes = lethal condition, zero fitness
				popFitness[i] = 0;
				net.getDataCollector().setCurrentTerm1(gt_term);
				net.getDataCollector().setCurrentTerm2(genes_term);
				net.getDataCollector().setCurrentTerm3(isolated_term);
			}	
			else{
	//			gt = Math.pow(genes, 2) / attractors;
				gt = genes / attractors;
	//			System.out.println("fitness0: " +popFitness[i]);
	//			System.out.println("attr: "+attractors+ " - genes: " +genes+ " - g/a: " +gt);
	//			System.out.println("opt.g/a: "+ p.OPTIMAL_GT_RATIO + " - (opt.g/a - g/a): " +(p.OPTIMAL_GT_RATIO - gt));
				
				gt_term = (1 / (1 + Math.abs(p.OPTIMAL_GT_RATIO - gt)));  
//				System.out.println("gt_term: " +gt_term);
				net.getDataCollector().setCurrentTerm1(gt_term);
				gt_term *= p.RATIO_TERM_PROPORTION;
				
				double exponent;
				if (genes <= p.OPTIMAL_GENE_NUMBER) {
					exponent = ((p.OPTIMAL_GENE_NUMBER / 2) - genes) / Math.round(p.OPTIMAL_GENE_NUMBER / 12);
//					System.out.println(genes+ " <= 50: exponent: "+exponent);
					genes_term = 1 / (1 + Math.pow(Math.E, exponent));
				}else{
					exponent = (1.5 * p.OPTIMAL_GENE_NUMBER - genes) / Math.round(p.OPTIMAL_GENE_NUMBER / 12);
//					System.out.println(genes+ " > 50: exponent: "+exponent);
					genes_term = 1 - (1 / (1 + Math.pow(Math.E, exponent)));
	//				genes_term =  (1 / (1 + Math.abs(p.OPTIMAL_GENE_NUMBER - genes)));
				}
				
//				System.out.println("genes_term: " +genes_term);
				net.getDataCollector().setCurrentTerm2(genes_term);
				genes_term *= p.GENES_TERM_PROPORTION;
				
				
				
				isolated_term = (1 / (1 + Math.pow(isolated, 2)));
//				System.out.println("isolated_term: " +isolated_term);
				net.getDataCollector().setCurrentTerm3(isolated_term);
				isolated_term *= p.ISOLATED_TERM_PROPORTION;
				
				
				
				subgraph_term = 1 / (Math.sqrt(nSub));
				
//				System.out.println("subgraph_term: "+subgraph_term);
				
				popFitness[i] = (gt_term + genes_term + isolated_term) * subgraph_term;
				
//				System.out.println("final term: " + popFitness[i]);
				
	//			adds current fitness value to fitness history of network
				
				
				
				
			}
			net.getDataCollector().setCurrentFitness(popFitness[i]);
		}
	}
}
