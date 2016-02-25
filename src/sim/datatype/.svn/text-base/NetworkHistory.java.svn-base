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
	protected Vector<Float> fitnessHistory;
	protected Vector<Float> fitnessTerm1History;
	protected Vector<Float> fitnessTerm2History;
	protected Vector<Float> fitnessTerm3History;
	protected Vector<Short> attractorHistory;
	protected Vector<Short> complexAttractorHistory;
	protected Vector<Float> meanInDegreeHistory;
	protected Vector<Float> meanOutDegreeHistory;
	protected Vector<Short> activatorsHistory;
	protected Vector<Short> repressorsHistory;
	protected Vector<Short> mutationHistory;
	protected Vector<Short> genesHistory;
	protected Vector<Short> dimersHistory;
	
	protected String filename;
	
	protected NetworkDataCollector dataCollector;
	
	public NetworkHistory(NetworkDataCollector dataCollector){
		this.dataCollector = dataCollector;
		generations = 0;
		fitnessHistory = new Vector<Float>(dataCollector.getParameters().GENERATIONS);
		fitnessTerm1History = new Vector<Float>(dataCollector.getParameters().GENERATIONS);
		fitnessTerm2History = new Vector<Float>(dataCollector.getParameters().GENERATIONS);
		fitnessTerm3History = new Vector<Float>(dataCollector.getParameters().GENERATIONS);
		attractorHistory = new Vector<Short>(dataCollector.getParameters().GENERATIONS);
		complexAttractorHistory = new Vector<Short>(dataCollector.getParameters().GENERATIONS);
		meanInDegreeHistory = new Vector<Float>(dataCollector.getParameters().GENERATIONS);
		meanOutDegreeHistory = new Vector<Float>(dataCollector.getParameters().GENERATIONS);
		activatorsHistory = new Vector<Short>(dataCollector.getParameters().GENERATIONS);
		repressorsHistory = new Vector<Short>(dataCollector.getParameters().GENERATIONS);
		mutationHistory = new Vector<Short>(dataCollector.getParameters().GENERATIONS);
		genesHistory = new Vector<Short>(dataCollector.getParameters().GENERATIONS);
		dimersHistory = new Vector<Short>(dataCollector.getParameters().GENERATIONS);
		filename = "";
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
//				System.out.println("process "+dataCollector.getNetwork().getProcess().getName());
//				System.out.println(dataCollector);
//				System.out.println(this);
//				System.out.println("network " +dataCollector.getNetwork());
				System.out.println("generation from process: " +generation);
				System.out.println("generations in history = " +generations);

				throw new HistoryException("skipped a generation!");
			} else {
				if (fitnessHistory.size() != generations
						|| attractorHistory.size() != generations
						|| mutationHistory.size() != generations
						|| genesHistory.size() != generations){
					throw new HistoryException("mismatch between generation and vector length! g="+generations+",fh="+fitnessHistory.size()+",ah="+attractorHistory.size()+",mh="+mutationHistory.size());
				}
				fitnessHistory.add((float)fitness);
				fitnessTerm1History.add((float)term1);
				fitnessTerm2History.add((float)term2);
				fitnessTerm3History.add((float)term3);
				attractorHistory.add((short)attractors);
//				complex attractors = attractors with cycle length >= 2
				complexAttractorHistory.add((short)complexAttractors);
				genesHistory.add((short)genes);
				dimersHistory.add((short)dimers);
				meanInDegreeHistory.add((float)meanIn);
				meanOutDegreeHistory.add((float)meanOut);
				activatorsHistory.add((short)activators);
				repressorsHistory.add((short)repressors);
				mutationHistory.add((short)mutation);
				generations++;
			
				return true;
			}
		} catch (HistoryException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return false;
	}
	
	public String historyToString(){
		String s = "";
		String fitString = "";
		String term1String;
		String term2String;
		String term3String;
		
		String meanInString;
		String meanOutString;
		
		int cut = 6;
		for (int i = 0; i < generations; i++){
			fitString = fitnessHistory.get(i).toString();
			term1String = fitnessTerm1History.get(i).toString();
			term2String = fitnessTerm2History.get(i).toString();
			term3String = fitnessTerm3History.get(i).toString();
			meanInString = meanInDegreeHistory.get(i).toString();
			meanOutString = meanOutDegreeHistory.get(i).toString();
			
			fitString = trimString(fitString, cut);
			term1String = trimString(term1String, cut);
			term2String = trimString(term2String, cut);
			term3String = trimString(term3String, cut);
			meanInString = trimString(meanInString, cut);
			meanOutString = trimString(meanOutString, cut);
			
			s += i + "\t"
			+ fitString + "\t"
			+ term1String + "\t"
			+ term2String + "\t"
			+ term3String + "\t"
			+ attractorHistory.get(i) + "\t"
			+ complexAttractorHistory.get(i) + "\t"
			+ genesHistory.get(i) + "\t"
			+ dimersHistory.get(i) + "\t"
			+ meanInString + "\t"
			+ meanOutString + "\t"
			+ activatorsHistory.get(i) + "\t"
			+ repressorsHistory.get(i) + "\t"
			+ mutationHistory.get(i) + "\n";
		}
		return s;
	}
	
	public String trimString(String string, int cut){
		if (string.length() > cut) string = string.substring(0, cut);
		return string;
	}
	
	public Vector<Short> getActivatorsHistory() {
		return activatorsHistory;
	}

	public void setActivatorsHistory(Vector<Short> activatorsHistory) {
		this.activatorsHistory = activatorsHistory;
	}

	public Vector<Short> getComplexAttractorHistory() {
		return complexAttractorHistory;
	}

	public void setComplexAttractorHistory(Vector<Short> complexAttractorHistory) {
		this.complexAttractorHistory = complexAttractorHistory;
	}

	public Vector<Short> getDimersHistory() {
		return dimersHistory;
	}

	public void setDimersHistory(Vector<Short> dimersHistory) {
		this.dimersHistory = dimersHistory;
	}

	public Vector<Float> getFitnessTerm1History() {
		return fitnessTerm1History;
	}

	public void setFitnessTerm1History(Vector<Float> fitnessTerm1History) {
		this.fitnessTerm1History = fitnessTerm1History;
	}

	public Vector<Float> getFitnessTerm2History() {
		return fitnessTerm2History;
	}

	public void setFitnessTerm2History(Vector<Float> fitnessTerm2History) {
		this.fitnessTerm2History = fitnessTerm2History;
	}

	public Vector<Float> getFitnessTerm3History() {
		return fitnessTerm3History;
	}

	public void setFitnessTerm3History(Vector<Float> fitnessTerm3History) {
		this.fitnessTerm3History = fitnessTerm3History;
	}

	public Vector<Short> getGenesHistory() {
		return genesHistory;
	}

	public void setGenesHistory(Vector<Short> genesHistory) {
		this.genesHistory = genesHistory;
	}

	public Vector<Float> getMeanInDegreeHistory() {
		return meanInDegreeHistory;
	}

	public void setMeanInDegreeHistory(Vector<Float> meanInDegreeHistory) {
		this.meanInDegreeHistory = meanInDegreeHistory;
	}

	public Vector<Float> getMeanOutDegreeHistory() {
		return meanOutDegreeHistory;
	}

	public void setMeanOutDegreeHistory(Vector<Float> meanOutDegreeHistory) {
		this.meanOutDegreeHistory = meanOutDegreeHistory;
	}

	public Vector<Short> getRepressorsHistory() {
		return repressorsHistory;
	}

	public void setRepressorsHistory(Vector<Short> repressorsHistory) {
		this.repressorsHistory = repressorsHistory;
	}

	public Vector<Float> getFitnessHistory() {
		return fitnessHistory;
	}
	
	public void setFitnessHistory(Vector<Float> fitnessHistory) {
		this.fitnessHistory = fitnessHistory;
	}
	
	public int getGenerations() {
		return generations;
	}
	
	public void setGenerations(int generations) {
		this.generations = generations;
	}
	
	public Vector<Short> getMutationHistory() {
		return mutationHistory;
	}
	
	public void setMutationHistory(Vector<Short> mutationHistory) {
		this.mutationHistory = mutationHistory;
	}

	public Vector<Short> getAttractorHistory() {
		return attractorHistory;
	}

	public void setAttractorHistory(Vector<Short> attractorHistory) {
		this.attractorHistory = attractorHistory;
	}
	
	public Object clone(){
		NetworkHistory clone = new NetworkHistory(dataCollector);
		clone.generations = generations;
		clone.fitnessHistory = (Vector<Float>) fitnessHistory.clone();
		clone.fitnessTerm1History= (Vector<Float>) fitnessTerm1History.clone();
		clone.fitnessTerm2History= (Vector<Float>) fitnessTerm2History.clone();
		clone.fitnessTerm3History= (Vector<Float>) fitnessTerm3History.clone();
		clone.attractorHistory = (Vector<Short>) attractorHistory.clone();
		clone.complexAttractorHistory = (Vector<Short>) complexAttractorHistory.clone();
		clone.genesHistory = (Vector<Short>) genesHistory.clone();
		clone.dimersHistory = (Vector<Short>) dimersHistory.clone();
		clone.meanInDegreeHistory = (Vector<Float>) meanInDegreeHistory.clone();
		clone.meanOutDegreeHistory = (Vector<Float>) meanOutDegreeHistory.clone();
		clone.activatorsHistory = (Vector<Short>) activatorsHistory.clone();
		clone.repressorsHistory = (Vector<Short>) repressorsHistory.clone();
		clone.mutationHistory = (Vector<Short>) mutationHistory.clone();
		
//		clone.compositionHistory = (Vector<String>) compositionHistory.clone();
		return clone;
	}

	public NetworkDataCollector getDataCollector() {
		return dataCollector;
	}

	public void setDataCollector(NetworkDataCollector dataCollector) {
		this.dataCollector = dataCollector;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
