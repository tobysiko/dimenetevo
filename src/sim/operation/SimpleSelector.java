package sim.operation;

import java.util.Date;

import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;



/** 
 * SimpleSelector transforms a population from one generation to the next, whereas networks with a high fitness will be favourably selected for the next generation and unfit networks are likely to disappear from the population. 
 * <br>Networks are ranked according to their fitness. Then the fittest xx% (specified in the Parameters class) are saved to the next generation and copied (replicated). 
 * <br>So in total, these networks make up 2 * xx% of the next generation. 
 * <br>The remaining vacancies are filled randomly from the less fit networks (the remaining 100-xx% of the original population).
 * 
 * <p><br><b>The fitness function:</b>
 * 
 * <br>The fitness of a network x is determined as:
 * 
 * <p><br>F1(x) = 1 / (1 + |gt - G(x) / T(x)|)</p>
 * 
 * <p>
 * <br>gt  : optimal G(x)/T(x) ratio
 * <br>G(x): number of genes in the network
 * <br>T(x): number of tissues this network can generate
 * </p>
 * 
 * 
 * <p><br>This selects for an optimal ratio of genes to tissues (attractors).</p>
 * 
 * <p><br>Additionally, the following term is included:</p>
 * 
 * <p><br>F2(x) = 1 / 1 + |ng - G(x)|</p>
 * 
 * <p>
 * <br>ng: optimal number of genes
 * </p>
 * 
 * <p><br>This selects for an optimal number of genes.</p>
 * 
 * <p><br>The contribution of each of the two terms to total fitness is determined by a weight:</p>
 * 
 * <p><br>F(x) = w * F1(x) + (1 - w) * F2(x)</p>
 * 
 * <p><br>Values for gt, ng and w are taken from the Parameters class: OPTIMAL_GT_RATIO, OPTIMAL_GENE_NUMBER and RATIO_TERM_PROPORTION, respectively.</p>
 * </p>
 * @see sim.Parameters
 * @uml.stereotype uml_id="Standard::Utility" 
 * @uml.stereotype uml_id="Standard::ImplementationClass" 
 */
public class SimpleSelector implements Selector {

	protected Parameters p;
	
	protected EvolutionaryProcess process;
	
	protected FitnessFunction f;
	
	protected Mutator mutator;
	
//	random number generator
	protected RandomEngine generator = new MersenneTwister(new Date());
	
//	uniform distribution
	protected Uniform uniform = new Uniform(generator);
	
	public SimpleSelector(EvolutionaryProcess process){
		this.process = process;
		p = process.getParm();
//		create Mutator
		mutator = p.getDefaultMutator();

		f = process.getFitnessFunction();
	}
	
	/* (non-Javadoc)
	 * @see sim.Selector#transformPopulation(java.util.Vector)
	 */
	public synchronized NetworkVector transformPopulation(NetworkVector population) {
		
		if (p.MINIMAL_OUTPUT) System.out.println("CALLED: SimpleSelector#transformPopulation(NetworkVector population)");
		
//		array corresponding to elements (= Networks) in NetworkVector that contains the fitness values of the networks
		double[] popFitness = new double[population.size()];
		f.assignFitness(population, popFitness);
		
//		array containing the indices of the Networks, ordered by fitness and with low indices corresponding to high fitness
		int[] rankedIndices = new int[population.size()];
		this.rankNetworks(popFitness, rankedIndices);
		
//		the new population
		NetworkVector newPop = new NetworkVector();
		
//		the index of rankedIndices from which on networks are not considered to be 'fit' anymore
		int fit_unfit_boundary = (int)Math.round(rankedIndices.length * p.FITTEST_PROPORTION);
//		System.out.println("fit_unfit_boundary: " +fit_unfit_boundary);
		
//		get networks in order of fitness-ranking
		Network[] rankedNetworks = new Network[rankedIndices.length];
		for (int i = 0; i < rankedNetworks.length; i++) {
			rankedNetworks[i] = population.get(rankedIndices[i]);
		}		
		
		long preSelLoop = new Date().getTime();
		
//		repeat until either the original population is empty or the end of rankedNetworks has been reached
//		with every cycle 'population' loses one network and 'newPop' gains one.
		int i = 0;
		while (population.size() > 0 && i < rankedNetworks.length){
			
//			for the fittest xx% of networks in original population... (TWO networks added)
			if(i < fit_unfit_boundary){
				
//				get next network from rankedNetworks
				Network fitnet = rankedNetworks[i];
						
//				add this (fit) network to new population 
				newPop.add(fitnet);
				
//				... and remove it from the old one
				population.remove(fitnet);
				
//				add deep copy (= clone) of this network (and catch exception)
				try {
					Network fitclone = (Network) fitnet.clone();
					
					newPop.add(fitclone);
					
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
//				i++;
			}
			
//			for the remaining 100-2*xx %:
			else if(i >= fit_unfit_boundary*2){
				
//				pick random networks from remainder of old population, remove it there and add it to the new population
				Network randomPick = 
					population.get(uniform.nextIntFromTo(
							0, 
							population.size() - 1) );
				
				newPop.add(randomPick);
				
				population.remove(randomPick);
			}
			
			i++;
		}
		if (p.PRINT_DURATIONS){
			long postSelLoop = new Date().getTime();
			System.out.println("\tselection loop: " +(postSelLoop-preSelLoop) +"ms");
		}
		
//		mutate new population
		long preMute,postMute;
		preMute = new Date().getTime();
		mutator.mutatePopulation(p, newPop);
		postMute = new Date().getTime();
		if (p.PRINT_DURATIONS)System.out.println("\tmutatePopulation(p, newPop): " +(postMute-preMute) +"ms");
		
			
		
//		[minimal text output: print network statistics]
		if (false){//TODO p.MINIMAL_OUTPUT){
			System.out.println("PopSize(after selection) = " + population.size());
			for (Network net:population){
				net.getDataCollector().printStats();
			}
		}
		
//		return the new population
		return newPop;
	}
	
	
	/**
	 * Ranks the network-indices (of the population-vector) according to the networks' fitness values so that the indices of networks with high fitness appear first in the sorted array.
	 * @param popFitness array containing the fitness-values of all networks with the indices being the same as in the population-vector.
	 * @param indices array that will contain the indices of the networks, sorted by fitness.
	 */
	public synchronized void rankNetworks(double[] popFitness, int[] indices){
		
//		temporary vector used for sorting
		MyVector<Integer> temp = new MyVector<Integer>();
		
//		first index, can't be sorted ...
		temp.add(0);
		
//		sort indices of 'popFitness'. go through all indices and insert them at the proper position in 'temp'
		for (int i = 1; i < popFitness.length; i++){
			
//			counter used to wander along 'temp'
			int c = 0;
			
//			wander along 'temp' until query fitness value is higher than the ones found in 'temp'
			while((c < temp.size())
					&& (popFitness[i] <= popFitness[temp.get(c)])){
				c++;
			}
			
//			insert index i of popFitness in 'temp' after position c
			temp.insertElementAt(i, c); 
		}
		
//		copy 'temp' to array 'indices'
		for (int i = 0; i < indices.length; i++)
			indices[i] = temp.get(i);
	}
	
	/**
	 * For testing and debugging.
	 * @param args
	 */
	public static void main(String[] args){
//		generate random population
//		Parameters p = new Parameters();
//		NetworkVector pop = new NetworkVector(p.POPULATION_SIZE);
//		NetworkGenerator rng = new RandomNetworkGenerator(p);
//		Simulator sim = new DefaultSimulator(p);
//		for(int i = 0; i < p.POPULATION_SIZE; i++){
//			Network newnet = rng.generateNewNetwork();
//			pop.add(newnet);
//			sim.simulateAllStates(newnet);
////			newnet.getDataCollector().printStats();
//		}
//		Selector sel = new SimpleSelector(p);
//		
//		for(int i = 0; i < p.GENERATIONS; i++){
//			System.out.println("Generation " +i);
//			for(Network net:pop)
//				sim.simulateAllStates(net);
//			System.out.println("BEFORE:" +pop);
//			pop =  sel.transformPopulation(pop);
//			System.out.println("AFTER: " +pop);
//		}
		
	}

	public Mutator getMutator() {
		return mutator;
	}

	public void setMutator(Mutator mutator) {
		this.mutator = mutator;
	}

}
