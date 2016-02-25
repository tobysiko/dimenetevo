package sim.operation;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;

import sim.Parameters;
import sim.datatype.Attractor;
import sim.datatype.AttractorVector;
import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.NetworkHistory;
import sim.datatype.MatrixNetwork;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.OnewayInteraction;
import sim.datatype.TimeStepSeries;

/**
 * Implements the NetworkDataCollector interface.  The DefaultNetworkCollector
 * @uml.stereotype   uml_id="Standard::Utility" 
 * @uml.stereotype   uml_id="Standard::ImplementationClass" 
 */
public class DefaultNetworkCollector implements NetworkDataCollector, Cloneable, Serializable {

	private static final long serialVersionUID = 0L;
		public static void main(String[] args){
////		test boolean array to int
////		boolean[] boo = {false, true, false, false, false};
//		Parameters p = new Parameters();
//		MatrixNetwork net = new MatrixNetwork(p);
//		DefaultNetworkCollector dc = new DefaultNetworkCollector(p, net);
////		System.out.println(dc.booleanArrayToInt(boo));
//		
//		Simulator sim = new DefaultSimulator(p);
//		
////		dummy attractor
//		TimeStepSeries a = new TimeStepSeries();
//		a.add(sim.stringToBooleanArray("11111100"));
//		a.add(sim.stringToBooleanArray("11111111"));
//		a.add(sim.stringToBooleanArray("11101011"));
//		a.add(sim.stringToBooleanArray("11100000"));
//		a.add(sim.stringToBooleanArray("11110000"));
//		a.add(sim.stringToBooleanArray("11111000"));
//		System.out.println(a);
//		System.out.println(dc.findSmallestState(a));
//		a = dc.formatAttractor(a);
//		System.out.println(a);
	}
	
	/**
	 * @uml.property   name="attractors"
	 * @uml.associationEnd   multiplicity="(0 -1)" elementType="sim.datatype.TimeStepSeries"
	 */
	protected AttractorVector attractors;

	protected int attractorNumber;
	
	protected MyVector<Integer> cycleLengthHistogram;
	
	protected MyVector<Integer> attractorCount;
	
//	protected MyVector<Double> fitnessHistory;
//	
//	protected MyVector<MyVector<Integer>> mutationHistory;
	
	protected Parameters p;
	
	protected Network net;
	
	protected int sinks;
	
	protected int isolated;
	
	protected int sources;
	
	protected int dimers;
	
	protected int genes;
	
	protected int elements;
	
	protected int posInteractions;
	
	protected int negInteractions;
	
	protected double avgInDegree;
	
	protected double avgOutDegree;

	protected boolean attractorsOld;
	
	protected NetworkHistory networkHistory;
	
	protected double currentFitness;
	
	protected double currentTerm1;
	
	protected double currentTerm2;
	
	protected double currentTerm3;
		
	protected int currentMutation;
	
	protected int subGraphs;
	
	/**
	 * Initialises all attributes.
	 */
	public DefaultNetworkCollector(Parameters p, Network net) {
		this.p = p;
		this.net = net;
		
		attractors = new AttractorVector();
		attractorNumber = 0;
		cycleLengthHistogram = new MyVector<Integer>(p.ATTRACTOR_MAX_LENGTH+1);
		for (int i = 0; i < p.ATTRACTOR_MAX_LENGTH+1; i++){
			cycleLengthHistogram.add(0);
		}
		attractorCount = new MyVector<Integer>();
		if (p.USE_NETWORK_HISTORY)
			networkHistory = new NetworkHistory(this);
		currentFitness = 0.0;
		currentTerm1 = 0.0;
		currentTerm2 = 0.0;
		currentTerm3 = 0.0;
		currentMutation = 0;
//		fitnessHistory = new MyVector<Double>();
//		mutationHistory = new MyVector<MyVector<Integer>>();		
		sinks = 0;
		isolated = 0;
		sources = 0;
		dimers = 0;
		genes = 0;
		elements = 0;
		avgInDegree = 0;
		avgOutDegree = 0;
		posInteractions = 0;
		negInteractions = 0;
		subGraphs = 0;
		attractorsOld = true;
	}
	/**
	 * Only removes the attractors. Does therefore not behave like reset(). Should be used to reduce used memory.
	 */
	public void removeAttractors(){
		attractors.removeAllElements();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.NetworkDataCollector#addAttractor(sim.TimeStepSeries)
	 */
	public boolean addAttractor(Attractor attractor) {
		
//		proceed until exception thrown ...
		try {
			if (attractor.size() <= 0)
				return false;
			
//			attractors are about gene expression patterns, so dimers must be removed
			removeDimers(attractor);
			
			double on = getGenesOn(attractor);
			
			if(on < p.ATTRACTOR_MIN_GENES || on > p.ATTRACTOR_MAX_GENES)
				return false;
			
//			reorder the states in the attractor so that the "smallest one" comes first in the vector
			attractor = formatAttractor(attractor);
			
//			will become 'true' if the attractor has already been encountered before (and therefore will NOT be added!)
			boolean found = false;
			
//			get the first state of the attractor
			boolean[] first = attractor.get(0);
			
//			(for testing/debugging)
			if (attractor.isEmpty())
				throw new DefaultSimulatorException("attractor is empty!!");
			
//			the first attractor is always added ...
			if (attractors.isEmpty()) {
				
//				add attractor to 'attractors'
				attractors.add(attractor);
				attractorCount.add(1);
				attractorNumber = attractors.size();
				if (attractor.size() > p.ATTRACTOR_MAX_LENGTH)
					throw new DefaultSimulatorException("attractor too long!!");
				if (attractor.size() > 0){
					cycleLengthHistogram.set(attractor.size(), cycleLengthHistogram.get(attractor.size()) + 1);
				}
				
//				[verbose text output]
				if (p.VERBOSE)
					System.out.println("ADDED attractor with first state: "
							+ booleanArrayToString(first));
				
//				attractor was added. terminate here.
				return true;
			}
			
//			all subsequent attractors are compared (by first state) to each of the stored attractors ...
			for (int i = 0; i < attractors.size(); i++) {
				
//				are the first states of the two identical: attractor has already been found before
				if (this.compareBooleanArrays(attractors.get(i).get(0), first)) {
					found = true;
					int c = attractorCount.get(i);
					c++;
					attractorCount.set(i, c);
				}
			}
			
//			if no matching attractor found, this one must be new ...
			if (!found) {
				
//				add attractor to 'attractors'
				attractors.add(attractor);
				attractorCount.add(1);
				attractorNumber = attractors.size();
//				System.out.println("attractor length: " +attractor.size());
				if (attractor.size() > p.ATTRACTOR_MAX_LENGTH)
					throw new DefaultSimulatorException("attractor too long!!");
				if (attractor.size() > 0){
					cycleLengthHistogram.set(attractor.size(), cycleLengthHistogram.get(attractor.size()) + 1);
				}
				
//				[verbose text output]
				if (p.VERBOSE)
					System.out.println("ADDED: "
							+ booleanArrayToString(first));
				
//				attractor was added. terminate here.
				return true;
			}
			
//		exception occured
		} catch (DefaultSimulatorException e) {
			e.printStackTrace();
		}
		
//		attractor was NOT added. terminate.
		return false;
	}
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see sim.NetworkDataCollector#addToFitnessHistory(double)
//	 */
//	public void addToFitnessHistory(double fitness) {
////		System.out.println("add to fithist " + fitnessHistory.size());
//		fitnessHistory.add(fitness);
////		System.out.println("fithist.size:"+fitnessHistory.size());
//	}
//	
//	public void addToMutationHistory(int mutationType){
//		int generation = net.getProcess().getGeneration();
//		while (! (mutationHistory.size() > generation+1)){
//			mutationHistory.add(new MyVector<Integer>());
//		}
//		
//			
////		System.out.println("mutationHistory.size():" +mutationHistory.size()+" generation:"+generation);
////		System.out.println(mutationHistory);
//		mutationHistory.get(generation).add(mutationType);
////		System.out.println("added mutation ("+mutationType+")to history of network "+this.net+" at generation "+ this.net.getProcess().getGeneration());
//	}
//	public void addToMutationHistory(int generation, int mutationType){
////		int generation = net.getProcess().getGeneration();
//		while (! (mutationHistory.size() > generation+1)){
//			mutationHistory.add(new MyVector<Integer>());
//		}
//		
//			
////		System.out.println("mutationHistory.size():" +mutationHistory.size()+" generation:"+generation);
////		System.out.println(mutationHistory);
//		mutationHistory.get(generation).add(mutationType);
//		if (false)System.out.println("added mutation ("+mutationType+")to history of network "+this.net+" at generation "+ this.net.getProcess().getGeneration());
//	}
//	public MyVector<Integer> getMutationsAt(int generation){
//		if (mutationHistory.size() >= generation)
//			return mutationHistory.get(generation);
//		else
//			return null;
//	}
//	public MyVector<String> getMutationsStringAt(int generation){
//		if (mutationHistory.size() > generation){
//			MyVector<String> string = new MyVector<String>();
//			for (int i:mutationHistory.get(generation)){
//				string.add(mutationEventToString(i));
//			}
//			return string;
//		}else
//			return null;
//	}
	public String mutationEventToString(int mutationEvent){
		String s = "";
			if (mutationEvent >=  10) s += "G++("+(mutationEvent-10)+")";
			if (mutationEvent <= -10) s += "G--("+(mutationEvent+10)+")";
			if (mutationEvent ==   1) s += "I->I*";
			if (mutationEvent ==   2) s += "O->O*";
			if (mutationEvent ==   3) s += "L--";
			if (mutationEvent ==   4) s += "L++";
			if (mutationEvent ==   5) s += "+/-";
			if (mutationEvent ==   6) s += "*HomD";
			if (mutationEvent ==   7) s += "*HetD";
			if (mutationEvent ==   8) s += "D--";
		return s;
	}
	/**
	 * Sets all values for dimers to zero. Attractors that only differ dues to dimers will be the same.
	 * @param attractor
	 */
	protected void removeDimers(Attractor attractor){
		
		try {
			boolean[] dimers = new boolean[net.size()];
			for (int i = 0; i < net.size(); i++){
				
					dimers[i] = net.get(i).isDimer();
			}
			if (p.VERBOSE)System.out.println("remove dimers: "+booleanArrayToString(dimers));
			for(boolean[] state:attractor){
				if (state.length != net.size())
					throw new DefaultSimulatorException("attractor state length does not match network size!!!");
				for (int i = 0; i < state.length; i++){
					if (dimers[i])
						state[i] = false;
				}
			}
		} catch (DefaultSimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int booleanArrayToInt(boolean[] b){
		int number = 0;
		for (int i = b.length-1; i >= 0; i--){
			if (b[i])
				number += Math.pow(2, i);
		}
		return number;
	}


	/**
	 * Converts a Boolean array into a <tt>String</tt>-representation, where
	 * '1' stands for <tt>true</tt> and '0' stands for <tt>false</tt>.
	 * 
	 * @param b
	 *            the Boolean array,
	 * @return the <tt>String</tt> representing the array.
	 */
	public String booleanArrayToString(boolean[] b) {
		if (b.length == 0) System.out.println("!!!!");
		String str = "";
		for (int i = 0; i < b.length; i++) {
			if (b[i])
				str += '1';
			else
				str += '0';
		}
		return str;
	}

	// TODO how to get rid of the warnings??
	@SuppressWarnings("unchecked")
	public Object clone() throws CloneNotSupportedException {
		DefaultNetworkCollector clone = (DefaultNetworkCollector) super.clone();
		clone.attractors = (AttractorVector) attractors.clone();
//		clone.fitnessHistory = (MyVector<Double>) fitnessHistory.clone();
		clone.attractorCount = (MyVector<Integer>) attractorCount.clone();
//		clone.mutationHistory= (MyVector<MyVector<Integer>>) mutationHistory.clone();
//		for (int i = 0; i < clone.mutationHistory.size(); i++){
//			clone.mutationHistory.set(i,(MyVector<Integer>)mutationHistory.get(i).clone());
//			
//		}
		if (p.USE_NETWORK_HISTORY){
			clone.networkHistory = (NetworkHistory) networkHistory.clone();
			clone.networkHistory.setDataCollector(clone);
		}
		clone.sinks = 0;
		clone.isolated = 0;
		clone.sources = 0;
		clone.dimers = 0;
		clone.genes = 0;
		clone.elements = 0;
		clone.avgInDegree = 0;
		clone.subGraphs = 0;
//		clone.currentFitness = 0;
//		clone.currentMutation = 0;
//		clone.currentTerm1 = 0;
//		clone.currentTerm2 = 0;
//		clone.currentTerm3 = 0;
		
		return clone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.NetworkDataCollector#compareBooleanArrays(boolean[], boolean[])
	 */
	public boolean compareBooleanArrays(boolean[] a1, boolean[] a2) {
		if (!(a1.length == a2.length))
			return false;
		for (int i = 0; i < a1.length; i++) {
			if (!(a1[i] == a2[i]))
				return false;
		}
		return true;
	}

	protected void determineInDegree(){
		ElementVector genes = net.getGenes();
		avgInDegree=0;
		for (Element e:genes) {
			
			avgInDegree += net.getRegulatoryInputsOf(e).size();
		}		
		avgInDegree /= genes.size();
	}
	protected void determineOutDegree(){
		ElementVector regulators;
		if (p.ONLY_DIMERS_REGULATE) 
			regulators = net.getDimers();
		else
			regulators = net.getGenes();
		avgOutDegree=0;
		for (Element e:regulators) {
			
			avgOutDegree += net.getRegulatoryOutputsOf(e).size();
		}		
		avgOutDegree /= regulators.size();
	}
	
	private int findSmallestState(TimeStepSeries attractor){
		int index = 0;
		int smallest = this.booleanArrayToInt(attractor.get(0));
		for (int i = 0; i < attractor.size(); i++){
			if (this.booleanArrayToInt(attractor.get(i)) < smallest){
				smallest = this.booleanArrayToInt(attractor.get(i));
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * Order the states of the attractor, so that if its states are interpreted as binary numbers, the smallest one comes first.
	 *
	 * @param attractor
	 */
	protected Attractor formatAttractor(Attractor attractor){
		Attractor formattedAttractor = new Attractor();
		formattedAttractor.setAttractorProfile(attractor.getAttractorProfile());
		int smallestState = findSmallestState(attractor);
		for (int i = smallestState; i < attractor.size(); i++){
			formattedAttractor.add(attractor.get(i));
		}
		if (smallestState != 0)
			for (int i = 0; i < smallestState; i++){
				formattedAttractor.add(attractor.get(i));
			}
		return formattedAttractor;
	}
	
	public MyVector<Integer> getAttractorCount() {
		return attractorCount;
	}

	/* (non-Javadoc)
	 * @see sim.NetworkDataCollector#getAttractors()
	 */
	public int getAttractors() {
		return attractorNumber;
//		return this.attractors.size();
	}
	
	
	public double getCurrentFitness(){
		return currentFitness;
	}

	public int getDimers() {
		return dimers;
	}
	
	
	public int getElements() {
		return elements;
	}

//	public MyVector<Double> getFitnessHistory() {
//		return fitnessHistory;
//	}



	public int getGenes() {
		return genes;
	}



	public double getGenesOn(TimeStepSeries a){
		if (p.VERBOSE) System.out.println("getGenesOn");
		double on = 0;
		
		double[] averages = new double[a.get(0).length];
		
		for (int i = 0; i < a.get(0).length; i++) {
			for (int j = 0; j < a.size(); j++) {
				boolean[] b = a.get(j);
				if (b[i]) averages[i] += 1;
				else averages[i] += 0;
			}	
			averages[i] /= a.size();
			if (p.VERBOSE) System.out.print(averages[i]+ " ");
//			if (averages[i] == 1.0) on += 1;
			if (averages[i] > 0.0) on += 1;
		}	
		on /= a.get(0).length;
		if (p.VERBOSE) System.out.println(" - prop. on: "+on);
		return on;
	}



	public int getIsolated() {
		return isolated;
	}



	public Network getNetwork() {
		return net;
	}



	public Parameters getParameters() {
		return p;
	}



	public int getSinks() {
		return sinks;
	}
	public int getSources() {
		return sources;
	}



	protected int getTotalAttractorCount(){
		 int c = 0;
		 for (int i = 0; i < this.attractorCount.size(); i++)
			 c += this.attractorCount.get(i);
		 return c;
	}



	public boolean isAttractorsOld() {
		return attractorsOld;
	}



	public boolean needsNewAttractors(){
		return (attractorsOld)? true:false;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.NetworkDataCollector#printAttractors()
	 */
	public void printAttractors() {
		System.out.println(attractorsToString());
	}
	public String attractorsToString(){
		String s = "";
		s += ("\n=======================================================\n");
		s += ("| A T T R A C T O R S (different/total): ("
				+attractors.size()+ "/" +getTotalAttractorCount() +")\n");
		s += ("=====================================================\n");
		
		for (int i = 0; i < attractors.size(); i++) {
			s += ("{" +i+ "} -- " +attractorCount.get(i)+ " times --" +attractors.get(i)+ "\n");
		}
		s += "Cycle lengths histogram:\n";
		for (int i = 0; i < cycleLengthHistogram.size(); i++){
			if (cycleLengthHistogram.get(i) > 0)
				s += i +": " +cycleLengthHistogram.get(i)+ " times\n";
		}
		return s;
	}


	public void printStats(){
	
		System.out.println(statsToString());
	}
	public String statsToString(){
		String s = "";
		updateStats();
		s += (" Stats for network: " /*+this.net + " - ID: " */+net.getID()+ "\n");
		s += (" comp:\t"+net.printComposition()+ "\n");
		s += (" R/Din:\t"+net.printInputNumbers()+ "\n");
		s += (" R.out:\t"+net.printRegOutputNumbers()+ "\n");
		s += (" D.out:\t"+net.printDimOutputNumbers()+ "\n");
		s += (" elements:\t" +elements+ "\n");
		s += (" genes:   \t" +genes+ "\n");
		s += (" dimers:  \t" +dimers+ "\n");
		s += (" sources: \t" +sources+ "\n");
		s += (" isolated:\t" +isolated+ "\n");
		s += (" sinks:   \t" +sinks+ "\n");
		s += (" indegree:\t" +avgInDegree+ "\n");
		s += (" outdegree:\t" +avgOutDegree+ "\n");
		s += (" activat.:\t" +posInteractions+ "\n");
		s += (" repress.:\t" +negInteractions+ "\n");
		s += (" attractors:\t" +attractorCount.size()+ "\n");
		
//		if (!fitnessHistory.isEmpty())
//			s += (" fitness: \t" +fitnessHistory.get(fitnessHistory.size()-1)+ "[t=" +(fitnessHistory.size()-1)+ "]"+ "\n");
//		else
//			s += (" fitness: \tn/a"+"\n"); 
		s += (" fitness: \t" +getCurrentFitness()+"\n");
		s += "Cycle lengths histogram:\n";
		for (int i = 0; i < cycleLengthHistogram.size(); i++){
			if (cycleLengthHistogram.get(i) > 0)
				s += i +": " +cycleLengthHistogram.get(i)+ " times\n";
		}
		return s;
	}
	public void setSubgraphs(int number){
		subGraphs = number;
	}
	public synchronized Set findSubgraphs(Graph dummy){
		
		Set<Set> subgraphs = new HashSet<Set>();
		Set verts = dummy.getVertices();
		UnweightedShortestPath uwsp = new UnweightedShortestPath(dummy);
		for (Iterator iter = verts.iterator(); iter.hasNext(); ){
			Vertex v = (Vertex) iter.next();
			Map distances = uwsp.getDistanceMap(v);
			Set distKeys = distances.keySet();
			Set<Vertex> subgraph = new HashSet<Vertex>();
			subgraph.add(v);
			for (Iterator iter2 = distKeys.iterator(); iter2.hasNext(); ){
				Vertex v2 = (Vertex) iter2.next();
				if (distances.get(v2) != null){
					subgraph.add(v2);
				}
			}
			subgraphs.add(subgraph);
		}
//		System.out.println("subs:"+subgraphs.size());
		return subgraphs;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.NetworkDataCollector#reset()
	 */
	public void reset() {
		attractors.removeAllElements();
		attractorCount = new MyVector<Integer>();
		attractorNumber=0;
		cycleLengthHistogram = new MyVector<Integer>(p.ATTRACTOR_MAX_LENGTH+1);
		for (int i = 0; i < p.ATTRACTOR_MAX_LENGTH+1; i++){
			cycleLengthHistogram.add(0);
		}
		
	}



	public void setAttractorCount(MyVector<Integer> attractorCount) {
		this.attractorCount = attractorCount;
	}



	public void setAttractorsOld(boolean attractorsOld) {
		this.attractorsOld = attractorsOld;
	}






	public void setDimers(int dimers) {
		this.dimers = dimers;
	}



	public void setElements(int elements) {
		this.elements = elements;
	}



//	public void setFitnessHistory(MyVector<Double> fitnessHistory) {
//		this.fitnessHistory = fitnessHistory;
//	}



	public void setGenes(int genes) {
		this.genes = genes;
	}



	public void setIsolated(int isolated) {
		this.isolated = isolated;
	}



	public void setNetwork(Network net) {
		this.net = net;
	}



	public void setParameters(Parameters p) {
		this.p = p;
	}
	
	public void setSinks(int sinks) {
		this.sinks = sinks;
	}



	public void setSources(int sources) {
		this.sources = sources;
	}



	public synchronized void updateStats(){
		
//		elements
		elements = net.size();
		
//		genes
		genes = net.getGenes().size();
		
//		dimers
		dimers = net.getDimers().size();
		
//		sources, isolated, sinks
		sources = net.getSources().size();
		isolated = net.getIsolated().size();
		sinks = net.getSinks().size();
		
//		represssors/activators
		negInteractions = 0;
		posInteractions = 0;
		for(Element e:net.getElementVector()){
			MyVector<OnewayInteraction> ins = e.getRegInputs();
			for (OnewayInteraction in:ins){
				if(in.getValue() < 0)
					negInteractions++;
				if(in.getValue() > 0)
					posInteractions++;
			}
		}
		
//		in-/out-degrees
		determineInDegree();
		determineOutDegree();
		
//		subgraphs
		getNetwork().createDummyGraph();
		
	}
	public double getAvgOutDegree() {
		return avgOutDegree;
	}
	public void setAvgOutDegree(double avgOutDegree) {
		this.avgOutDegree = avgOutDegree;
	}
	public double getAvgInDegree() {
		return avgInDegree;
	}
	public void setAvgInDegree(double avgInDegree) {
		this.avgInDegree = avgInDegree;
	}
	public int getNegInteractions() {
		return negInteractions;
	}
	public void setNegInteractions(int negInteractions) {
		this.negInteractions = negInteractions;
	}
	public int getPosInteractions() {
		return posInteractions;
	}
	public void setPosInteractions(int posInteractions) {
		this.posInteractions = posInteractions;
	}
//	public MyVector<MyVector<Integer>> getMutationHistory() {
//		return mutationHistory;
//	}
//	public void setMutationHistory(MyVector<MyVector<Integer>> mutationHistory) {
//		this.mutationHistory = mutationHistory;
//	}
	/* (non-Javadoc)
	 * @see sim.operation.NetworkDataCollector#getHistory()
	 */
	public NetworkHistory getHistory() {
		return networkHistory;
	}
	/* (non-Javadoc)
	 * @see sim.operation.NetworkDataCollector#setHistory(sim.datatype.History)
	 */
	public void setHistory(NetworkHistory networkHistory) {
		this.networkHistory = networkHistory;
	}
	public int getCurrentMutation() {
		return currentMutation;
	}
	public void setCurrentMutation(int currentMutation) {
		this.currentMutation = currentMutation;
	}
	public void setCurrentFitness(double currentFitness) {
		this.currentFitness = currentFitness;
	}
	public int getSubGraphs() {
		return subGraphs;
	}
	public void setSubGraphs(int subGraphs) {
		this.subGraphs = subGraphs;
	}
	public double getCurrentTerm1() {
		return currentTerm1;
	}
	public void setCurrentTerm1(double currentTerm1) {
		this.currentTerm1 = currentTerm1;
	}
	public double getCurrentTerm2() {
		return currentTerm2;
	}
	public void setCurrentTerm2(double currentTerm2) {
		this.currentTerm2 = currentTerm2;
	}
	public double getCurrentTerm3() {
		return currentTerm3;
	}
	public void setCurrentTerm3(double currentTerm3) {
		this.currentTerm3 = currentTerm3;
	}
	public MyVector<Integer> getCycleLengthHistogram() {
		return cycleLengthHistogram;
	}
	public void setCycleLengthHistogram(MyVector<Integer> cycleLengthHistogram) {
		this.cycleLengthHistogram = cycleLengthHistogram;
	}
	public int getAttractorNumber() {
		return attractorNumber;
	}
	public void setAttractorNumber(int attractorNumber) {
		this.attractorNumber = attractorNumber;
	}
	
}
