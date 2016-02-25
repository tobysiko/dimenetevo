package sim.operation;

import java.util.Date;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Element;
import sim.datatype.Network;
import sim.datatype.NetworkVector;

/**
 * @author sikosek
 *
 */
public class SinksFitnessFunction implements FitnessFunction {

	protected Parameters p;
//	random number generator
	protected RandomEngine generator = new MersenneTwister(new Date());
	
//	uniform distribution
	protected Uniform uniform = new Uniform(generator);
	protected EvolutionaryProcess process;
	
	public SinksFitnessFunction(EvolutionaryProcess process){
		p = process.getParameters();
		this.process = process;
	}
	/* (non-Javadoc)
	 * @see sim.operation.FitnessFunction#assignFitness(sim.datatype.NetworkVector, double[])
	 */
	public void assignFitness(NetworkVector pop, double[] popFitness) {
		
//		number of attractors of a network
		double attractors = 0;
		
//		number of genes in a network
		double nSinks, nSources, optA, nNonRegSinks, optO;
		
//		a network
		Network net;
		
//		for each network in the population ...
		for (int i = 0; i < popFitness.length; i++){
			
//			get network i from population
			net = pop.get(i);
			
//			get network's attractors 
			attractors = net.getDataCollector().getAttractors();
			
			
				
			nSinks = net.getSinks().size();
			
			optA = Math.pow(2, nSinks);
			
			optO = nSinks * 0.05;
			
			nSources = net.getSources().size();
			
			nNonRegSinks = 0;
			for (Element sink:net.getSinks()){
				if (sink.getRegInputs().size() == 0){
					nNonRegSinks++;
				}
			}
			
			
			if (attractors < p.VIABLE_MIN_ATTRACTORS || nSinks < p.VIABLE_MIN_GENES)
				
//				too few attractors and/or genes = lethal condition, zero fitness
				popFitness[i] = 0;
			else{
	
				double firstTerm  = p.ATTRACTORS_TERM_PROP  * (1/(1+Math.abs(optA - attractors)));
//				System.out.println("first term: "+firstTerm+ "(of " +p.ATTRACTORS_TERM_PROP+ ")");

				double secondTerm = p.SINKS_RATIO_TERM_PROP * (1/(1+Math.abs(optO - nSources)));
//				double secondTerm = p.SINKS_RATIO_TERM_PROP * (1/(1+Math.abs(nSinks - nSources)));
//				System.out.println("second term: "+secondTerm+ "(of " +p.SINKS_RATIO_TERM_PROP+ ")");

				double thirdTerm;
				if (nNonRegSinks != 0)
					thirdTerm  	  = p.NONREG_TERM_PROP      * (nSinks / nNonRegSinks);
				else
					thirdTerm = 0;
//				System.out.println("third term: "+thirdTerm+ "(of " +p.NONREG_TERM_PROP+ ")");
				
				double fourthTerm = p.OPT_SINK_TERM_PROP	* (1 / (1 + Math.abs(process.getOptimalSinksNumber() - nSinks)));
//				System.out.println("fourth term: "+fourthTerm+ "(of " +p.OPT_SINK_TERM_PROP+ ")");
				
				popFitness[i] = firstTerm + secondTerm;
//				System.out.println("final term: "+popFitness[i]);
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
			net.getDataCollector().setCurrentFitness(popFitness[i]);
		}

		
	}

}

