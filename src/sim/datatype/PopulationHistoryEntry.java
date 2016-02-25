/**
 * 
 */
package sim.datatype;



/**
 * @author sikosek
 *
 */
public class PopulationHistoryEntry {
	short generation;
	float avgFitness;
	float maxFitness;
	float avgFTerm1;
	float avgFTerm2;
	float avgFTerm3;
	float avgAttractors;
	float avgComplexAttractors;
	float avgGenes;
	float avgDimers;
	float avgInDegree;
	float avgOutDegree;
	float avgActivations;
	float avgRepressions;
	
	public PopulationHistoryEntry(){
		generation = 0;
		avgFitness = 0;
		maxFitness = 0;
		avgFTerm1 = 0;
		avgFTerm2 = 0;
		avgFTerm3 = 0;
		avgAttractors = 0;
		avgComplexAttractors = 0;
		avgGenes = 0;
		avgDimers = 0;
		avgInDegree = 0;
		avgOutDegree = 0;
		avgActivations = 0;
		avgRepressions = 0;
	}
	public PopulationHistoryEntry(short generation,
			float avgFitness,
			float maxFitness,
			float avgFTerm1,
			float avgFTerm2,
			float avgFTerm3,
			float avgAttractors,
			float avgComplexAttractors,
			float avgGenes,
			float avgDimers,
			float avgInDegree,
			float avgOutDegree,
			float avgActivations,
			float avgRepressions){
		setEntry(generation, avgFitness, maxFitness, avgFTerm1, avgFTerm2, avgFTerm3, avgAttractors, avgComplexAttractors, avgGenes, avgDimers, avgInDegree, avgOutDegree, avgActivations, avgRepressions);
	}
	public float getAvgActivations() {
		return avgActivations;
	}

	public void setAvgActivations(float avgActivations) {
		this.avgActivations = avgActivations;
	}

	public float getAvgAttractors() {
		return avgAttractors;
	}

	public void setAvgAttractors(float avgAttractors) {
		this.avgAttractors = avgAttractors;
	}

	public float getAvgComplexAttractors() {
		return avgComplexAttractors;
	}

	public void setAvgComplexAttractors(float avgComplexAttractors) {
		this.avgComplexAttractors = avgComplexAttractors;
	}

	public float getAvgDimers() {
		return avgDimers;
	}

	public void setAvgDimers(float avgDimers) {
		this.avgDimers = avgDimers;
	}

	public float getAvgFitness() {
		return avgFitness;
	}

	public void setAvgFitness(float avgFitness) {
		this.avgFitness = avgFitness;
	}

	public float getAvgFTerm1() {
		return avgFTerm1;
	}

	public void setAvgFTerm1(float avgFTerm1) {
		this.avgFTerm1 = avgFTerm1;
	}

	public float getAvgFTerm2() {
		return avgFTerm2;
	}

	public void setAvgFTerm2(float avgFTerm2) {
		this.avgFTerm2 = avgFTerm2;
	}

	public float getAvgFTerm3() {
		return avgFTerm3;
	}

	public void setAvgFTerm3(float avgFTerm3) {
		this.avgFTerm3 = avgFTerm3;
	}

	public float getAvgGenes() {
		return avgGenes;
	}

	public void setAvgGenes(float avgGenes) {
		this.avgGenes = avgGenes;
	}

	public float getAvgInDegree() {
		return avgInDegree;
	}

	public void setAvgInDegree(float avgInDegree) {
		this.avgInDegree = avgInDegree;
	}

	public float getAvgOutDegree() {
		return avgOutDegree;
	}

	public void setAvgOutDegree(float avgOutDegree) {
		this.avgOutDegree = avgOutDegree;
	}

	public float getAvgRepressions() {
		return avgRepressions;
	}

	public void setAvgRepressions(float avgRepressions) {
		this.avgRepressions = avgRepressions;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(short generation) {
		this.generation = generation;
	}

	public float getMaxFitness() {
		return maxFitness;
	}

	public void setMaxFitness(float maxFitness) {
		this.maxFitness = maxFitness;
	}
	
	public void setEntry(short generation,
							float avgFitness,
							float maxFitness,
							float avgFTerm1,
							float avgFTerm2,
							float avgFTerm3,
							float avgAttractors,
							float avgComplexAttractors,
							float avgGenes,
							float avgDimers,
							float avgInDegree,
							float avgOutDegree,
							float avgActivations,
							float avgRepressions){
		this.generation = generation;
		this.avgFitness = avgFitness;
		this.maxFitness = maxFitness;
		this.avgFTerm1 = avgFTerm1;
		this.avgFTerm2 = avgFTerm2;
		this.avgFTerm3 = avgFTerm3;
		this.avgAttractors = avgAttractors;
		this.avgComplexAttractors = avgComplexAttractors;
		this.avgGenes = avgGenes;
		this.avgDimers = avgDimers;
		this.avgInDegree = avgInDegree;
		this.avgOutDegree = avgOutDegree;
		this.avgActivations = avgActivations;
		this.avgRepressions = avgRepressions;

	}
	
	public String toString(){
		String s = "";
		int cut = 6;
		s += generation + "\t" +
			trimfloatString(avgFitness, cut) + "\t" +
			trimfloatString(maxFitness, cut) + "\t" +
			trimfloatString(avgFTerm1, cut) + "\t" +
			trimfloatString(avgFTerm2, cut) + "\t" +
			trimfloatString(avgFTerm3, cut) + "\t" +
			trimfloatString(avgAttractors, cut) + "\t" +
			trimfloatString(avgComplexAttractors, cut) + "\t" +
			trimfloatString(avgGenes, cut) + "\t" +
			trimfloatString(avgDimers, cut) + "\t" +
			trimfloatString(avgInDegree, cut) + "\t" +
			trimfloatString(avgOutDegree, cut) + "\t" +
			trimfloatString(avgActivations, cut) + "\t" +
			trimfloatString(avgRepressions, cut) + "\n";
		
		
		return s;
	}
	public String trimfloatString(float d, int cut){
		String string = d+"";
		if (string.length() > cut) string = string.substring(0, cut);
		return string;
	}
//	avgFitness = 0;
//	maxFitness
//	avgFTerm1
//	avgFTerm2
//	avgFTerm3
//	avgAttractors
//	avgComplexAttractors
//	avgGenes
//	avgDimers
//	avgInDegree
//	avgOutDegree
//	avgActivations
//	avgRepressions
}
