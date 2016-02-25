/**
 * 
 */
package sim.operation;

import sim.EvolutionaryProcess;
import sim.datatype.Attractor;
import sim.datatype.Network;
import sim.datatype.TimeStepSeries;

/**
 * @author sikosek
 *
 */
public class SourcesSimulator extends DefaultSimulator {

	
	public SourcesSimulator(EvolutionaryProcess process) {
		super(process);
		
	}

	public void simulateAllStates(Network net) {

		if (p.MINIMAL_OUTPUT)
			System.out.println("CALLED: SourcesSimulator#simulateAllStates(Network net)");

		// gets NetworkDataCollector of network
		this.dc = net.getDataCollector();

		// erases all existing attractors from the data collector
		dc.reset();

		// holds the newly generated initial state for each single simulation
		boolean[] state;

		// holds the attractor that each single simulation produces
		Attractor attractor;
		
		
		SourcesStatesGenerator soustagen = new SourcesStatesGenerator(process);
		soustagen.generateStates(net);

		// holds the total number of initial states for which the network will
		// be simulated
		long numPicks;
		
		// set 'numPicks' to number of those states
		numPicks = soustagen.getNumberOfStates();

		// TODO (for debugging)
		if (p.VERBOSE)
			System.out.println("number of picks = " + numPicks);

		// for all initial states ...
		for (int i = 0; i < numPicks; i++) {

			// TODO (for debugging)
			if (p.VERBOSE)
				System.out.println("\nState " + i);

			state = soustagen.getState(i);

			// erase all time steps from the trajectory for the next simulation
			resetTrajectory();

			// TODO can I delete this?
			attractor = null;

			// simulate given initial state ('state') and return the resulting
			// attractor
			attractor = simulateSingleState(net, state);

			// add attractor to data collector - TODO method returns boolean as
			// a success statement - not utilised yet...
			dc.addAttractor(attractor);
		}

		// TODO (for debugging/testing) print all attractors stored in the data
		// collector
		if (p.VERBOSE)
			dc.printAttractors();
	}
	/**
	 * For testing and debugging of SourcesSimulator.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
////		generate random population
//		Parameters p = new Parameters();
//		NetworkVector pop = new NetworkVector(p.POPULATION_SIZE);
//		NetworkGenerator rng = new RandomNetworkGenerator(p);
//		Simulator sim = new SourcesSimulator(p);
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
//			sel.transformPopulation(pop);
//			System.out.println("AFTER: " +pop);
//		}

		System.out.println("FINISHED!!!!");
	}
	
}
