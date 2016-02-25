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
public class NetworkHistory {
	protected int generations;
	protected Vector<Double> fitnessHistory;
	protected Vector<Double> fitnessTerm1History;
	protected Vector<Double> fitnessTerm2History;
	protected Vector<Double> fitnessTerm3History;
	protected Vector<Integer> attractorHistory;
	protected Vector<Integer> complexAttractorHistory;
	protected Vector<Double> meanInDegreeHistory;
	protected Vector<Double> meanOutDegreeHistory;
	protected Vector<Integer> activatorsHistory;
	protected Vector<Integer> repressorsHistory;
	protected Vector<Integer> mutationHistory;
	protected Vector<Integer> genesHistory;
	protected Vector<Integer> dimersHistory;
	
	protected NetworkDataCollector dataCollector;
	
	public NetworkHistory(NetworkDataCollector dataCollector){
		this.dataCollector = dataCollector;
		generations = 0;
		fitnessHistory = new Vector<Double>(dataCollector.getParameters().GENERATIONS);
		fitnessTerm1History = new Vector<Double>(dataCollector.getParameters().GENERATIONS);
		fitnessTerm2History = new Vector<Double>(dataCollector.getParameters().GENERATIONS);
		fitnessTerm3History = new Vector<Double>(dataCollector.getParameters().GENERATIONS);
		attractorHistory = new Vector<Integer>(dataCollector.getParameters().GENERATIONS);
		complexAttractorHistory = new Vector<Integer>(dataCollector.getParameters().GENERATIONS);
		meanInDegreeHistory = new Vector<Double>(dataCollector.getParameters().GENERATIONS);
		meanOutDegreeHistory = new Vector<Double>(dataCollector.getParameters().GENERATIONS);
		activatorsHistory = new Vector<Integer>(dataCollector.getParameters().GENERATIONS);
		repressorsHistory = new Vector<Integer>(dataCollector.getParameters().GENERATIONS);
		mutationHistory = new Vector<Integer>(dataCollector.getParameters().GENERATIONS);
		genesHistory = new Vector<Integer>(dataCollector.getParameters().GENERATIONS);
		dimersHistory = new Vector<Integer>(dataCollector.getParameters().GENERATIONS);
//		compositionHistory = new Vector<String>(dataCollector.getParameters().GENERATIONS);
	}
	
//	the indices of the vectors are equal to the generation the respective value belongs to.
//	therefore, 'generations' must be = vector size, and the argument 'generation' must be = 'generations'.
	public boolean addGenerationData(int generation, 
									double fitness, 
									double term1, 
									double term2, 
									double term3, 
									int attractors, 
									int complexAttractors, 
									int genes,
									int dimers,
									double meanIn,
									double meanOut,
									int activators,
									int repressors,
									int mutation){
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
						|| genesHistory.size() != generations){
					throw new HistoryException("mismatch between generation and vector length! g="+generations+",fh="+fitnessHistory.size()+",ah="+attractorHistory.size()+",mh="+mutationHistory.size());
				}
				fitnessHistory.add(fitness);
				fitnessTerm1History.add(term1);
				fitnessTerm2History.add(term2);
				fitnessTerm3History.add(term3);
				attractorHistory.add(attractors);
				complexAttractorHistory.add(complexAttractors);
				genesHistory.add(genes);
				dimersHistory.add(dimers);
				meanInDegreeHistory.add(meanIn);
				meanOutDegreeHistory.add(meanOut);
				activatorsHistory.add(activators);
				repressorsHistory.add(repressors);
				mutationHistory.add(mutation);
//				compositionHistory.add(composition);
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
		String fitString = "";
		int cut = 6;
		for (int i = 0; i < generations; i++){
			fitString = fitnessHistory.get(i).toString();
//			term1String = fitnessTerm1History.get(i).toString();
//			term2
				trimString(fitString, cut);
			s += i + "\t"
			+ fitString + "\t"
			+ attractorHistory.get(i) + "\t"
			+ mutationHistory.get(i) + "\t"
			+ compositionHistory.get(i)+"\n";
		}
		return s;
	}
	public void trimString(String string, int cut){
		if (string.length() > cut) string = string.substring(0, cut);
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
		NetworkHistory clone = new NetworkHistory(dataCollector);
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
