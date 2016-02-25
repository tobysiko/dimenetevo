/**
 * 
 */
package sim.datatype;

import java.util.Vector;

import sim.operation.NetworkDataCollector;

/**
 * @author sikosek
 *
 */
public class History {
	protected int generations;
	protected Vector<Double> fitnessHistory;
	protected Vector<Integer> attractorHistory;
	protected Vector<Integer> mutationHistory;
	protected Vector<String> compositionHistory;
	protected NetworkDataCollector dataCollector;
	
	public History(NetworkDataCollector dataCollector){
		this.dataCollector = dataCollector;
		generations = 0;
		fitnessHistory = new Vector<Double>();
		attractorHistory = new Vector<Integer>();
		mutationHistory = new Vector<Integer>();
		compositionHistory = new Vector<String>();
	}
	
//	the indices of the vectors are equal to the generation the respective value belongs to.
//	therefore, 'generations' must be = vector size, and the argument 'generation' must be = 'generations'.
	public boolean addGenerationData(int generation, double fitness, int attractors, int mutation, String composition){
		try {
//			System.out.println("process "+dataCollector.getNetwork().getProcess().getName());
//			System.out.println(dataCollector);
//			System.out.println(this);
//			System.out.println("network " +dataCollector.getNetwork());
//			System.out.println("generation " +generation);
//			System.out.println("generations = " +generations);
			if (generation != generations){
				System.out.println("process "+dataCollector.getNetwork().getProcess().getName());
				System.out.println(dataCollector);
				System.out.println(this);
				System.out.println("network " +dataCollector.getNetwork());
				System.out.println("generation " +generation);
				System.out.println("generations = " +generations);

				throw new HistoryException("skipped a generation!");
			} else {
				if (fitnessHistory.size() != generations
						|| attractorHistory.size() != generations
						|| mutationHistory.size() != generations
						|| compositionHistory.size() != generations){
					throw new HistoryException("mismatch between generation and vector length! g="+generations+",fh="+fitnessHistory.size()+",ah="+attractorHistory.size()+",mh="+mutationHistory.size()+",ch="+compositionHistory.size());
				}
				fitnessHistory.add(fitness);
				attractorHistory.add(attractors);
				mutationHistory.add(mutation);
				compositionHistory.add(composition);
				generations++;
				return true;
			}
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return false;
	}
	public String historyToString(){
		String s = "";
		for (int i = 0; i < generations; i++){
			s += i + "\t"
			+ fitnessHistory.get(i) + "\t"
			+ attractorHistory.get(i) + "\t"
			+ mutationHistory.get(i) + "\t"
			+ compositionHistory.get(i)+"\n";
		}
		return s;
	}

	public Vector<Vector> getEntireHistory(){
		Vector<Vector> history = new Vector<Vector>(4);
		history.add(fitnessHistory);
		history.add(attractorHistory);
		history.add(mutationHistory);
		history.add(compositionHistory);
		return history;
	}
	
	public Vector<String> getCompositionHistory() {
		return compositionHistory;
	}
	public void setCompositionHistory(Vector<String> compositionHistory) {
		this.compositionHistory = compositionHistory;
	}
	public Vector<Double> getFitnessHistory() {
		return fitnessHistory;
	}
	public void setFitnessHistory(Vector<Double> fitnessHistory) {
		this.fitnessHistory = fitnessHistory;
	}
	public int getGenerations() {
		return generations;
	}
	public void setGenerations(int generations) {
		this.generations = generations;
	}
	public Vector<Integer> getMutationHistory() {
		return mutationHistory;
	}
	public void setMutationHistory(Vector<Integer> mutationHistory) {
		this.mutationHistory = mutationHistory;
	}

	public Vector<Integer> getAttractorHistory() {
		return attractorHistory;
	}

	public void setAttractorHistory(Vector<Integer> attractorHistory) {
		this.attractorHistory = attractorHistory;
	}
	
	public Object clone(){
		History clone = new History(dataCollector);
		clone.generations = generations;
		clone.fitnessHistory = (Vector<Double>) fitnessHistory.clone();
		clone.attractorHistory = (Vector<Integer>) attractorHistory.clone();
		clone.mutationHistory = (Vector<Integer>) mutationHistory.clone();
		clone.compositionHistory = (Vector<String>) compositionHistory.clone();
		return clone;
	}

	public NetworkDataCollector getDataCollector() {
		return dataCollector;
	}

	public void setDataCollector(NetworkDataCollector dataCollector) {
		this.dataCollector = dataCollector;
	}
}
