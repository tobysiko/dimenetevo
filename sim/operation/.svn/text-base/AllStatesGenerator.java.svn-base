/**
 * 
 */
package sim.operation;

import sim.EvolutionaryProcess;
import sim.datatype.Network;
import sim.datatype.TimeStepSeries;


/**
 * Provides methods for generating and storing all permutations of a Boolean array which represents the initial state of a network. The permutations therefore represent all possible initial states. 
 * @author sikosek
 */
public class AllStatesGenerator extends AbstractStatesGenerator{

	
	/**
	 * Generates all permutations of initial states of given network. 
	 * @param net The Network for which the initial states are generated.
	 * @param p Parameters.
	 */
	public AllStatesGenerator(EvolutionaryProcess process){
		
		super(process);
		
	}
	
	
	public void generateStates(Network net){
		this.net = net;
		allStates = new TimeStepSeries();
//		proceed, if no exception thrown ...
		try {
			
//			TODO (for debugging/testing) throw exception if network size exceeds the limit for generating all states instead of random picks
			if (net.size() > p.RANDOM_PICKS_MIN_SIZE)
				throw new DefaultStatesGeneratorException("network size exceeds the allowable maximum for AllStatesGenerator!");
			
//			call the recursive method that generates all the permutations and stores them in 'allStates'
			permutate(net.size());
			
//			TODO (for debugging/testing)
			if (p.VERBOSE) System.out.println("Generating all " +Math.pow(2, net.size())+ " initial states for n=" +net.size()+ "!" /*+allStates*/);
		
//		something went wrong ...
		} catch (DefaultStatesGeneratorException e) {
			e.printStackTrace();
		}

	}
	
	private void permutate(int n){
		recPermutate(n, n-1, new boolean[n]);
	}
	
	/**
	 * Determines all permutations of <tt>n</tt> elements. Works with binary recursion. Exactly <tt>2^n</tt> Boolean arrays will be generated.
	 * @param n fixed size of the Boolean array containing one state.
	 * @param c counter that is used to reach the termination condition. Must be equal to <tt>n</tt> in the beginning.
	 * @param bool the Boolean array that is passed through the recursions to finally contain one of the permutations.
	 */
	private void recPermutate(int n, int c, boolean[] bool){
		
//		recursion continues as long as c >= 0 ...
		if (c >= 0){
			boolean[] b1 = bool;
			recPermutate(n, c -1, b1);
			b1[c] = true;
			
			boolean[] b2 = bool;
			recPermutate(n, c -1, b2);
			b2[c] = false;
		}
		
//		terminate recursion and add this initial state to 'allStates' (important: use a CLONE of the <tt>boolean[]</tt> object!)
		else
			allStates.add(bool.clone());
	}


	/* (non-Javadoc)
	 * @see sim.operation.AbstractStatesGenerator#pickRandomStates(boolean[])
	 */
	@Override
	public void pickRandomStates(boolean[] notConsidered) {
		// TODO Auto-generated method stub
		
	}
	

}
