/**
 * 
 */
package sim.operation;

import sim.EvolutionaryProcess;
import sim.datatype.ElementVector;
import sim.datatype.Network;
import sim.datatype.TimeStepSeries;

/**
 * @author sikosek
 *
 */
public class SourcesStatesGenerator extends AbstractStatesGenerator {

	/**
	 * @param net
	 * @param p
	 * @param sim
	 */
	public SourcesStatesGenerator(EvolutionaryProcess process) {
		super(process);
		
	}

	/* (non-Javadoc)
	 * @see sim.AbstractStatesGenerator#generateStates()
	 */
	public void generateStates(Network net) {
		this.net = net;
		allStates = new TimeStepSeries();
		ElementVector sources = net.getSources();
		
		int nSources = sources.size();
		
		int[] sourceIndices = new int[nSources];
		
		for (int i = 0; i < nSources; i++){
			sourceIndices[i] = net.getIndexOf(sources.get(i));
		}
		
		permutate(nSources);
		
		boolean[] state;
		
		int sIndex = 0;
		
		for (int i = 0; i < allStates.size(); i++){
			state = allStates.get(i);
			boolean[] b = new boolean[net.size()];
			sIndex = 0;
			for (int j = 0; j < net.size(); j++) {
				if (sIndex < sourceIndices.length && sourceIndices[sIndex] == j){
					b[j] = state[sIndex];
					sIndex++;
				}
				else{
					b[j] = false;
				}
			}
			allStates.set(i, b);
			
		}
	}
	
	/**
	 * Works by generating all 2^n integers and translating each into a boolean array.
	 * @param n
	 * @deprecated because it is actually slower than the recursive method
	 */
	private void getPermutations(int n){
			int total = (int) Math.pow(2, n);
			String binString = "";
			String zeroesString;
			for (int i = 0; i < total; i++){
				zeroesString = "";
				binString = Integer.toBinaryString(i);
//				System.out.println(binString);
				for (int j = 0; j < (n - binString.length()); j++) {
					zeroesString += '0';
				}			
				binString = zeroesString + binString;
				allStates.add(stringToBooleanArray(binString));
			}
			System.out.println(allStates.size());
	}
	
	/**for n=20 about 2secs - for n=25: java.lang.OutOfMemoryError
	 * @param n
	 */
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
	
	public static void main(String[] args){
//		Parameters p = new Parameters();
//		SourcesSimulator sim= new SourcesSimulator(p);
//		Network net = new MatrixNetwork(p);
//		SourcesStatesGenerator ssg = new SourcesStatesGenerator(net, true);
//		System.out.println("start " +new java.util.Date());
//		ssg.getPermutations(25);
////		ssg.permutate(25);
//		System.out.println("stop " +new java.util.Date());
	}

	/* (non-Javadoc)
	 * @see sim.operation.AbstractStatesGenerator#pickRandomStates(boolean[])
	 */
	@Override
	public void pickRandomStates(boolean[] notConsidered) {
		// TODO Auto-generated method stub
		
	}
}
