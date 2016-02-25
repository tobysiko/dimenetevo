/**
 * 
 */
package sim.operation;

import java.util.Date;

import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Attractor;
import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.OnewayInteraction;
import sim.datatype.TimeStepSeries;
import sim.operation.function.BooleanFunction;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

/**
 * @author sikosek
 * 
 */
public class DefaultSimulator implements Simulator {

	protected NetworkDataCollector dc;

	protected Parameters p;

	protected TimeStepSeries trajectory;

	protected RandomEngine generator = new MersenneTwister(new Date());

	protected Uniform uniform = new Uniform(generator);
	
	protected AbstractStatesGenerator stateGen;
	
	protected EvolutionaryProcess process;

	public DefaultSimulator(EvolutionaryProcess process) {
		this.process = process;
		p = process.getParm();
		stateGen = process.getStateGen();
		trajectory = new TimeStepSeries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Simulator#simulate(sim.Parameters, sim.Network)
	 */
	private boolean[] updateSynchronously(Network net, boolean[] previousStep) {

		// if no exception thrown, proceed ...
		try {

			// TODO for debugging. checks if 'previousStep' matches the network
			// size
			if (previousStep.length != net.size())
				throw new DefaultSimulatorException(
						"previousStep.length != net.size() !!!");

			// set genes in network ON/OFF according to 'previousStep'
			net.setToState(previousStep);

			// the new time step that is generated from 'previousStep'
			boolean[] currentstep = new boolean[previousStep.length];

			// the boolean function that integrates the inputs for each node.
			// different elements can have different boolean functions ...
			BooleanFunction function;

			
//			for (Integer i:net.getConsideredIndices()) {
//				function = net.getElementVector().get(i).getFunction();
//				currentstep[i] = function.integrate(net, i);
//			}
			
			// for each element ...
//			for (int i = 0; i < currentstep.length; i++) {
//
//				if (!net.getNotConsidered()[i]) {
//					// get its boolean function
//					function = net.getElementVector().get(i).getFunction();
//					// get its new state according to the function
//					currentstep[i] = function.integrate(net, i);
//				}	
//				else{
//					currentstep[i] = false;
//				}
//			}
			
			for (int i = 0; i < currentstep.length; i++) {
				// get its boolean function
				function = net.getElementVector().get(i).getFunction();
				// get its new state according to the function
				currentstep[i] = function.integrate(net, i);
			}				
			
//			 for each element ...
//			update genes first so that dimers will be on in the same timestep
//			MyVector<Integer> genes = net.getGeneIndices();
//			MyVector<Integer> dimers = net.getDimerIndices();
//			int gene;
//			int dimer;
//			for (int i = 0; i < genes.size(); i++) {
//					gene = genes.get(i);
//					
//					// get its boolean function
//					function = net.getElementVector().get(gene).getFunction();
//					// get its new state according to the function
//					currentstep[gene] = function.integrate(net, gene);
//				
//			}
//			for (int i = 0; i < dimers.size(); i++) {
//				dimer = dimers.get(i);
//				
//				// get its boolean function
//				function = net.getElementVector().get(dimer).getFunction();
//				// get its new state according to the function
//				currentstep[dimer] = function.integrate(net, dimer);
//
//			}
			// return the newly generated time step
			return currentstep;

			// if something went wrong ...
		} catch (DefaultSimulatorException e) {
			e.printStackTrace();
		}
		// ... return nothing
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Simulator#generateRandomInitialState(sim.Network)
	 */
	public boolean[] generateRandomInitialState(Network net) {

		// the new initial network state ...
		boolean[] initialState = new boolean[net.size()];

		// for each element ... get random value (true/false)
		for (int i = 0; i < initialState.length; i++)
			initialState[i] = uniform.nextBoolean();

		// update the actual network to this state
		net.setToState(initialState);

		// return the new initial state
		return initialState;
	}

	/**
	 * Removes all time steps from the trajectory, so it can be used for a new
	 * simulation.
	 */
	public void resetTrajectory() {
		trajectory.removeAllElements();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Simulator#findAttractor(boolean[])
	 */
	public Attractor findAttractor(boolean[] state) {

		// an attractor can only be found if the trajectory contains any time
		// steps
		if (! trajectory.isEmpty()) {

			// go through all time steps in the trajectory from the beginning
			// ...
			for (int t = 0; t < trajectory.size(); t++) {

				// if the currently observed time step t matches the input
				// state, then this state has been there before in a previous
				// time step
				if (dc.compareBooleanArrays(trajectory.get(t), state)) {

					// create a new attractor
					Attractor attractor = new Attractor(
							trajectory.size() - t);

					// from this time step t on (where the match occured) for
					// each time step ... add state to attractor
					for (int i = t; i < trajectory.size(); i++)
						attractor.add(trajectory.get(i));

					// return the attractor and thus terminate the for-loop
					// prematurely
					return attractor;
				}
			}
		}

		// if the trajectory is empty, add a copy of 'state' (--> the initial
		// state)
		trajectory.add(state.clone());

		// System.out.println("added new state!");
		return null;
	}

	/**
	 * Creates a <tt>String</tt>-representation of a <tt>boolean[]</tt>,
	 * where <tt>true</tt> and <tt>false</tt> are represented as
	 * <tt>'1'</tt> and <tt>'0'</tt>, respectively.
	 * 
	 * @param b
	 *            the Boolean array of which the <tt>String</tt>-representation
	 *            is to be made.
	 * @return the <tt>String</tt>-representing the Boolean array.
	 */
	public String booleanArrayToString(boolean[] b) {
		String str = "";
		for (int i = 0; i < b.length; i++) {
			if (b[i])
				str += '1';
			else
				str += '0';
		}
		return str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Simulator#simulate(sim.Network, boolean[])
	 */
	public Attractor simulateSingleState(Network net,
			boolean[] initialState) {

		// if no exception thrown, proceed ...
		try {

			// will terminate the while-loop as soon as an attractor is found
			boolean stop = false;

			// sets the current state to the initial state
			boolean[] currentState = initialState;

			// this will become the attractor
			Attractor attractor = null;

			// counter to make sure the whileloop terminates after a set number
			// of cycles
			int count = 0;

			// updates the time steps until an attractor is found or the counter
			// reaches its limit
			while (!stop && count < p.ATTRACTOR_MAX_LENGTH) {

				// TODO for debugging.
				if (p.VERBOSE)
					System.out.println(count + ":\t"
							+ booleanArrayToString(currentState));

				// update current state to get the state of the next time step
				currentState = updateSynchronously(net, currentState);

				// tries to find an attractor. if no attractor found, returns
				// 'null'
				attractor = findAttractor(currentState);
				
				// if attractor is not 'null' (i.e. one was found) ... terminate
				// while-loop
				if (attractor != null)
					stop = true;

				// increase counter in order to reach termination condition
				count++;
			}
			
			
			if (attractor != null) {
				
				
				if (p.VERBOSE)
					System.out.println("\nattractor: [" + attractor.size()
							+ "]" + attractor);
			}
			
			if(false)throw new DefaultSimulatorException("no attractor found!!");

			// returns the attractor
			return attractor;

			// something went wrong ...
		} catch (DefaultSimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO (for debugging) returns nothing, because no attractor was found
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Simulator#simulateAll(sim.Network)
	 */
	public void simulateAllStates(Network net) {
		if (p.MINIMAL_OUTPUT)
			System.out
					.print("CALLED: DefaultSimulator#simulateAllStates(Network net)");

		if (net.getDataCollector().needsNewAttractors()) {
			long preAllSim = new Date().getTime();
			
			if (p.MINIMAL_OUTPUT) System.out.println(" - simulate!");
			
			
			
			// gets NetworkDataCollector of network
			dc = net.getDataCollector();
			// erases all existing data from the data collector
			dc.reset();
			// holds the newly generated initial state for each single simulation
			boolean[] state;
			// holds the attractor that each single simulation produces
			Attractor attractor;
			// holds the total number of initial states for which the network will
			// be simulated
			long numPicks;
			// set 'numPicks' to number of those states
			numPicks = net.getNumberOfStates();
			// TODO (for debugging)
			if (p.VERBOSE)
				System.out.println("number of picks = " + numPicks);
		
			long preSingState,postSingState,preAddAttr,postAddAttr;
			long sumSingState = 0L;
			long sumAddAttr = 0L;
		
			// for all initial states ...
			for (int i = 0; i < numPicks; i++) {

				// TODO (for debugging)
				if (p.VERBOSE)
					System.out.println("\nState " + i);

				state = net.getState(i);

				// erase all time steps from the trajectory for the next simulation
				resetTrajectory();

				// TODO can I delete this?
				attractor = null;

//				Network dummy = pruneNetwork(net);
				
				// simulate given initial state ('state') and return the resulting
				// attractor
				if (p.PRINT_DURATIONS) {
					preSingState = new Date().getTime();
					attractor = simulateSingleState(net, state);
					postSingState = new Date().getTime();
					sumSingState += (postSingState-preSingState);
				}else{
					attractor = simulateSingleState(net, state);
				}
				
				if (attractor != null) {
					// add attractor to data collector - TODO method returns boolean as
					// a success statement - not utilised yet...
					if (p.PRINT_DURATIONS) {
						preAddAttr = new Date().getTime();
						dc.addAttractor(attractor);
						postAddAttr = new Date().getTime();
						sumAddAttr += (postAddAttr - preAddAttr);
					}else{
						dc.addAttractor(attractor);
					}
				}else{
					System.out.println("ATTRACTOR TOO LONG!!!!!!!!!!!!!!!");
				}
					
			}
			if (p.PRINT_DURATIONS){
				System.out.println("\t"+numPicks+" x simulateSingleState():"+sumSingState+"ms");
				System.out.println("\t"+numPicks+" x dc.addAttractor(attractor):"+sumAddAttr+"ms");
			}
			// TODO (for debugging/testing) print all attractors stored in the data
			// collector
			if (p.VERBOSE)
				dc.printAttractors();
			
			net.getDataCollector().setAttractorsOld(false);
			
			if (p.PRINT_DURATIONS){
			long postAllSim = new Date().getTime();
				System.out.println("\t[simulateAllStates(net): "+(postAllSim-preAllSim)+"ms]\n");
			}
		}		
		else{
			if (p.MINIMAL_OUTPUT) System.out.println("DON'T simulate!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Simulator#stringToBooleanArray(java.lang.String)
	 */
	public boolean[] stringToBooleanArray(String s) {

		// the Boolean array of the same size as the string
		boolean[] b = new boolean[s.length()];

		// proceed, if no exceptions thrown ...
		try {

			// go through entire array ...
			for (int i = 0; i < b.length; i++) {

				// translate a '1' into 'true'
				if (s.charAt(i) == '1')
					b[i] = true;

				// translate a '0' into 'false' - or throw an exception for any
				// other character
				else {
					if (s.charAt(i) == '0')
						b[i] = false;
					else
						throw new DefaultSimulatorException(
								"wrong character in string: " + s.charAt(i));
				}
			}

			// something went wrong ...
		} catch (DefaultSimulatorException e) {
			e.printStackTrace();
		}

		// return the Boolean array
		return b;
	}

	public Network pruneNetwork(Network net){
		Network dummy = p.getDefaultNetwork();
		
		ElementVector consideredElements = dummy.getElementVector();
		consideredElements.addAll(net.getElementVector());
//		ElementVector elements = net.getElementVector();
		ElementVector dimers = net.getDimers();
		ElementVector sinks = net.getSinks();
		ElementVector isolated = net.getIsolated();
		for (Element e:net.getElementVector()){
			if(isolated.contains(e)){
				consideredElements.remove(e);
			}else if (dimers.contains(e)){
				consideredElements.remove(e);
			}else if (sinks.contains(e)){
				int recursionDepth = 2;
				removeSinks(net,consideredElements, e, recursionDepth);
				
			}
		}

		
		return dummy;
	}
	private void removeSinks(Network net,ElementVector consideredElements, Element e, int count){
		if (! (count == 0)) {
			MyVector<OnewayInteraction> inputs = net.getRegulatoryInputsOf(e);
			consideredElements.remove(e);
			for (OnewayInteraction input : inputs) {
				if (net.getRegulatoryOutputsOf(input.getElement()).size() == 1
						&& net.getDimerisingOutputsOf(e).size() == 0) {
//					node has no other regulatory output than the sink already deleted, therefore, must be sink as well
					removeSinks(net, consideredElements, input.getElement(), (count-1));
					consideredElements.remove(e);
				}
			}
		}		
	}
	
	/**
	 * For testing and debugging of DefaultSimulator.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		Parameters p = new Parameters();
//		Simulator sim = new DefaultSimulator(p);
//		NetworkGenerator gen = new TestNetworkGenerator(p);
//		Network net = gen.generateNewNetwork();
//		Outputter out = new Outputter();
//		out.display(net);
//		// boolean[] initialState = sim.generateRandomInitialState(net);
//		// boolean[] initialState =
//		// sim.stringToBooleanArray("11000000110110011011101000010101101001001100111111");
//		// sim.simulate(net, initialState);
//		net.getDataCollector().updateStats();
//		net.getDataCollector().printStats();
//
//		for (int i = 0; i < 1; i++) {
//
//			sim.simulateAllStates(net);
//		}

		// /* combine with mutations*/

		// Mutator mut = new DefaultMutator(p);
		// NetworkVector population = new NetworkVector();
		// population.add(net);
		// for (int i = 0; i < 5000; i++){
		// mut.mutatePopulation(p, population);
		//			
		// if (i > 4000) {
		// sim.simulateAll(net);
		// out.update(net);
		// }
		// else{
		// if (i%100 == 0) {
		// out.update(net);
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }

		System.out.println("FINISHED!!!!");
	}

}
