/**
 * 
 */
package sim.operation;

import java.io.Serializable;
import java.util.Date;

import sim.DefaultProcess;
import sim.EvolutionaryProcess;
import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.OnewayInteraction;
import sim.datatype.TimeStepSeries;



/**
 * @author sikosek
 *
 */
public class SensibleStatesGenerator extends AbstractStatesGenerator implements Serializable{

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	protected ElementVector consideredElements;
	/**
	 * 
	 */
	public SensibleStatesGenerator(EvolutionaryProcess process) {
		super(process);
		consideredElements = new ElementVector();
	}


	public void generateStates(Network net) {
		this.net = net;
		allStates = new TimeStepSeries();
//		Network dummy = pruneNetwork(net);
		
//		boolean[] notConsidered = getNotConsideredElements(dummy);
		boolean[] notConsidered = getNotConsideredElements();
		net.setNotConsidered(notConsidered);
		for (int i = 0; i < net.size(); i++){
			if(!notConsidered[i]){
				
				net.getConsideredIndices().add(i);
			}
		}
		
//		System.out.println("notC.:\t" + net.getDataCollector().booleanArrayToString(notConsidered));
//		System.out.println(net.printComposition());
//		System.out.println(net.printInputNumbers());
//		System.out.println(net.printRegOutputNumbers());
//		System.out.println(net.printDimOutputNumbers());
//		System.out.println("start: " +new Date());
//		System.out.println(estimateStates(notConsidered.length) +" - "+ Math.pow(2, p.MAX_PICKS_POWER));
		if (estimateStates(notConsidered.length) <= Math.pow(2, p.MAX_PICKS_POWER)){
			permutate(net.size(), notConsidered);
//			System.out.println("permutated states");
//			System.out.println("states generated: " +allStates.size());
//			System.out.println("permutate: " +new Date());
		}
		else{
			pickRandomStates(notConsidered);
//			System.out.println("picked random states");
//			System.out.println("states generated: " +allStates.size());
//			System.out.println("random: " +new Date());
		}
		
		
//		System.out.println("states [" +allStates.size()+ "/" +Math.pow(2, net.size())+ "]: " + allStates);
		
	}
	
	public Network pruneNetwork(Network net){
		
		Network dummy;
		try {
			dummy = (Network) net.clone();
			ElementVector dimers = net.getDimers();
			ElementVector sinks = net.getSinks();
			ElementVector isolated = net.getIsolated();
			MyVector<Integer> toBeRemoved = new MyVector<Integer>(net.size());
			for (int e = 0; e < net.size(); e++){
				if(isolated.contains(e)){
					if (dummy.getElementVector().contains(e)){
//						dummy.removeElement(e);
						toBeRemoved.add(e);
					}
				}else if (dimers.contains(e)){
					if (dummy.getElementVector().contains(e)){
//						dummy.removeElement(e);
						toBeRemoved.add(e);
					}
				}else if (sinks.contains(e)){
					
					removeSinks(net,toBeRemoved, net.get(e), p.SINK_SEARCH_REC_DEPTH);
				}
			}
			
			
			return dummy;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void removeSinks(Network net, MyVector<Integer> toBeRemoved, Element e, int count){
		if (! (count == 0)) {
			MyVector<OnewayInteraction> inputs = net.getRegulatoryInputsOf(e);
			if (net.getElementVector().contains(e))
//				dummy.removeElement(e);
				toBeRemoved.add(net.getIndexOf(e));
			for (OnewayInteraction input : inputs) {
				if (net.getRegulatoryOutputsOf(input.getElement()).size() == 1
						&& net.getDimerisingOutputsOf(e).size() == 0) {
//					node has no other regulatory output than the sink already deleted, therefore, must be sink as well
					removeSinks(net, toBeRemoved, input.getElement(), (count-1));
					if (net.getElementVector().contains(e)) 
//						dummy.removeElement(e);
						toBeRemoved.add(net.getIndexOf(e));
				}
			}
		}		
	}

	
	
	public boolean[] getNotConsideredElements(Network dummy){
		boolean[] notConsidered = new boolean[net.size()];
		ElementVector elements = net.getElementVector();
		for (int i = 0; i < notConsidered.length; i++) {
			Element e = elements.get(i);
			if (!dummy.getElementVector().contains(e)) {
				notConsidered[i] = true;
			}			
		}		
		return notConsidered;
	}
	
	public boolean[] getNotConsideredElements(){
		boolean[] notConsidered = new boolean[net.size()];
		ElementVector sources = net.getSources();
		ElementVector elements = net.getElementVector();
		ElementVector dimers = net.getDimers();
//		ElementVector sinks = net.getSinks();
//		ElementVector isolated = net.getIsolated();
		for (int i = 0; i < notConsidered.length; i++) {
			Element e = elements.get(i);

			if (dimers.contains(e)){
				notConsidered[i] = true;
			}
		
		}		
		return notConsidered;
	}
	
	public int estimateStates(int n){
		
		return (int) Math.pow(2, n);
	}
	
	public void pickRandomStates(boolean[] notConsidered){
		
		boolean[] state = null;
			for (int i = 0; i < Math.pow(2, p.MAX_PICKS_POWER); i++) {
				state = new boolean[net.size()];
				for (int j = 0; j < net.size(); j++) {
					if (notConsidered[j]) {
						state[j] = false;
					}
					else {
						if (uniform.nextBoolean())
							state[j] = true;
						else
							state[j] = false;
					}
				}
				allStates.add(state);
			}			
	}
	
	protected void permutate(int n, boolean[] notConsidered){
//		System.out.println("start rec");
		recPermutate(n, n-1, new boolean[n], notConsidered);
		
//		remove first state which contains only zeros
//		boolean allzeros = true;
//		
//		allzerosloop:
//		for (int i = 0; i < allStates.get(0).length; i++) {
//			if (allStates.get(0)[i]) {
//				allzeros = false;
//				break allzerosloop;
//			}
//		}	
//		if (allzeros)
//			allStates.remove(0);
	}
	
	protected void recPermutate(int n, int c, boolean[] bool, boolean[] notConsidered){
		
//		recursion continues as long as c >= 0 ...
		if (c >= 0){
			if (!notConsidered[c]) {
				boolean[] b1 = bool;
				recPermutate(n, c -1, b1, notConsidered);
			
				b1[c] = true;
				
			}
			boolean[] b2 = bool;
			recPermutate(n, c -1, b2, notConsidered);
			b2[c] = false;
			
		}
		
//		terminate recursion and add this initial state to 'allStates' (important: use a CLONE of the <tt>boolean[]</tt> object!)
		else
			allStates.add(bool.clone());
	}
	public static void main(String[] args){
		EvolutionaryProcess process = new DefaultProcess();
//		sim.Parameters p = process.getParm();
//		TestNetworkGenerator tng = new TestNetworkGenerator(process);
//		RandomNetworkGenerator rng = new RandomNetworkGenerator(process);
//		Network net = rng.generateNewNetwork();
		FileManager fm = process.getFileManager();
		sim.datatype.AbstractSimulationState state;
		fm.printStates();
		state = fm.loadLastState();
		process.setPopulation(state.getPopulation());
		Network net = state.getPopulation().get(0);
		Simulator sim = process.getSimulator();
//		SensibleStatesGenerator ssg = new SensibleStatesGenerator(net,p, sim);
		net.getDataCollector().printStats();
		sim.simulateAllStates(net);
		net.getDataCollector().printStats();
		
	}
}
