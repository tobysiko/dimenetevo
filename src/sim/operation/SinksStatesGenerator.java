/**
 * 
 */
package sim.operation;

import java.util.Vector;

import sim.EvolutionaryProcess;
import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.Network;
import sim.datatype.TimeStepSeries;

/**
 * @author sikosek
 *
 */
public class SinksStatesGenerator extends SensibleStatesGenerator {

	
	private static final long serialVersionUID = 1L;
	/**
	 * @param process
	 */
	public SinksStatesGenerator(EvolutionaryProcess process) {
		super(process);
		// TODO Auto-generated constructor stub
	}
	public void generateStates(Network net) {
		this.net = net;
		allStates = new TimeStepSeries();
		boolean[] notConsidered = getNotConsideredElements();
//		Vector<Integer> indices = getNotConsideredIndices();
		net.setNotConsidered(notConsidered);
		int countOnes=0;
//		permutate(net.size(), notConsidered);
		for (int i = 0; i < notConsidered.length; i++){
			if (!notConsidered[i])
				countOnes++;
		}
		System.out.println("notC: "+net.getDataCollector().booleanArrayToString(notConsidered));
		System.out.println("sinks:"+net.getSinks().size());
		System.out.println("total elements: "+net.size());
		System.out.println("elements considered: "+countOnes);
		if (Math.pow(2, countOnes) <= Math.pow(2, p.MAX_PICKS_POWER)){
			permutate(net.size(), notConsidered);
//			System.out.println("permutated states");
//			System.out.println("states generated: " +allStates.size());
//			System.out.println("permutate: " +new Date());
		}
		else{
			pickRandomStates(notConsidered);
			System.out.println("picked random states");
//			System.out.println("states generated: " +allStates.size());
//			System.out.println("random: " +new Date());
		}
		allStates.remove(0);
		System.out.println("states [" +allStates.size()+ "/" +Math.pow(2, net.size())+ "]: " + allStates);
		
	}
	protected Vector<Integer> getNotConsideredIndices(){
		Vector<Integer> indices = new Vector<Integer>();
		ElementVector sources = net.getSources();
		ElementVector elements = net.getElementVector();
		for (int i = 0; i < elements.size(); i++) {
			Element e = elements.get(i);
			if (! sources.contains(e)){
				indices.add(i);
			}
		}		
		return indices;
	}
	
}
