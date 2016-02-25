package sim.operation;

import java.util.Set;

import edu.uci.ics.jung.graph.Graph;

import sim.Parameters;
import sim.datatype.Attractor;
import sim.datatype.NetworkHistory;
import sim.datatype.MyVector;
import sim.datatype.Network;

/**
 * Each <tt>Network</tt> has its own <tt>NetworkDataCollector</tt> that
 * stores its attractors and fitness values.
 * 
 * @author sikosek
 */
abstract public interface NetworkDataCollector {
	/**
	 * Adds the specified attractor to this <tt>NetworkDataCollector</tt>.
	 * 
	 * @param attractor
	 *            the <tt>TimeStepSeries</tt> that is the attractor.
	 * @return <tt>true</tt> if successful, <tt>false</tt> otherwise.
	 */
	public abstract boolean addAttractor(Attractor attractor);

	public abstract String attractorsToString();

	/**
	 * Adds the <tt>double</tt> value to the internal fitness-value history.
	 * 
	 * @param fitness
	 *            the fitness value.
	 */
	// public abstract void addToFitnessHistory(double fitness);
	// public abstract void addToMutationHistory(int mutationEvent);
	// public abstract MyVector<Integer> getMutationsAt(int generation);
	// public abstract MyVector<String> getMutationsStringAt(int generation);
	public abstract String booleanArrayToString(boolean[] b);

	// public MyVector<MyVector<Integer>> getMutationHistory();
	/**
	 * The overridden <tt>clone()</tt> method from the <tt>Object</tt> class
	 * 
	 * @return the clone (i.e. a copy by value, not by reference) of this
	 *         <tt>NetworkDataCollector</tt>.
	 * @throws CloneNotSupportedException
	 */
	public abstract Object clone() throws CloneNotSupportedException;

	/**
	 * Compares euality of two Boolean arrays by value.
	 * 
	 * @param a1
	 *            first Boolean array.
	 * @param a2
	 *            second Boolean array.
	 * @return <tt>true</tt> if the two arrays are equal, <tt>false</tt>
	 *         otherwise.
	 */
	public abstract boolean compareBooleanArrays(boolean[] a1, boolean[] a2);

	public Set findSubgraphs(Graph dummy);

	public MyVector<Integer> getAttractorCount();

	public abstract int getAttractors();

	public double getAvgInDegree();

	public double getAvgOutDegree();

	public double getCurrentFitness();

	public int getCurrentMutation();

	public int getDimers();

	public int getElements();

	public int getGenes();

	public abstract NetworkHistory getHistory();

	public int getIsolated();

	// public abstract MyVector<Double> getFitnessHistory();

	public int getNegInteractions();

	public Network getNetwork();

	public Parameters getParameters();

	public int getPosInteractions();

	public int getSinks();

	public int getSources();

	public int getSubGraphs();

	public boolean isAttractorsOld();

	public String mutationEventToString(int mutationEvent);

	public boolean needsNewAttractors();

	/**
	 * Sends a <tt>String</tt> representation of all the stored attractors to
	 * <tt>System.out</tt>.
	 */
	public abstract void printAttractors();

	public abstract void printStats();

	/**
	 * Erase all attractors.
	 */
	public abstract void reset();
	public abstract void removeAttractors();
	public void setAttractorCount(MyVector<Integer> attractorCount);

	public void setAttractorsOld(boolean attractorsOld);

	public void setAvgInDegree(double avgInDegree);

	public void setAvgOutDegree(double avgOutDegree);

	public void setCurrentFitness(double currentFitness);

	public void setCurrentMutation(int currentMutation);

	// public abstract void setFitnessHistory(MyVector<Double> fitnessHistory);

	public void setDimers(int dimers);

	public void setElements(int elements);

	public void setGenes(int genes);

	public abstract void setHistory(NetworkHistory networkHistory);

	public void setIsolated(int isolated);

	public void setNegInteractions(int negInteractions);

	public void setNetwork(Network net);

	public void setParameters(Parameters p);

	public void setPosInteractions(int posInteractions);

	public void setSinks(int sinks);

	public void setSources(int sources);

	public void setSubgraphs(int number);

	public void setSubGraphs(int subGraphs);

	public abstract String statsToString();

	public abstract void updateStats();
	public double getCurrentTerm1();
	public void setCurrentTerm1(double currentTerm1) ;
	public double getCurrentTerm2() ;
	public void setCurrentTerm2(double currentTerm2);
	public double getCurrentTerm3();
	public void setCurrentTerm3(double currentTerm3) ;
	public MyVector<Integer> getCycleLengthHistogram();
	public void setCycleLengthHistogram(MyVector<Integer> cycleLengthHistogram);
}
