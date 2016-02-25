/**
 * 
 */
package sim.operation;

import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Network;
import sim.datatype.NetworkVector;

/**
 * @author sikosek
 *
 */
public abstract class ProcessDataCollector {

	protected double avgFitness;
	protected double avgAttractors;
	protected double avgElements;
	protected double avgGenes;
	protected double avgDimers;
	protected double avgIsolated;
	protected double avgSinks;
	protected double avgSources;
	protected double avgConnected;
	protected double avgInDegree;
	protected double avgOutDegree;
	protected double avgGTRatio;
	protected double minFit;
	protected double maxFit;
	protected int minAttr;
	protected int maxAttr;
	protected double avgPosInteractions;
	protected double avgNegInteractions;
	protected EvolutionaryProcess process;
	protected NetworkVector pop;
	protected Parameters p;
	
	public ProcessDataCollector(Parameters p, EvolutionaryProcess process){
		this.p = p;
		this.pop = process.getPopulation();
		avgFitness = 0;
		avgAttractors = 0;
		avgElements = 0;
		avgGenes = 0;
		avgDimers = 0;
		avgIsolated = 0;
		avgSinks = 0;
		avgSources = 0;
		avgConnected = 0;
		avgInDegree = 0;
		avgOutDegree = 0;
		avgGTRatio = 0;
		avgPosInteractions = 0;
		avgNegInteractions = 0;
		minFit = 0;
		maxFit = 0;
		minAttr = 0;
		maxAttr = 0;
		
	}
	public void printStats(){
		System.out.println(getStatsString());

	}
	public String getStatsString(){
		String stats = "";
		
		if (!pop.isEmpty()) {
			this.updateStats();
			String F = Double.toString(avgFitness);
			String A = Double.toString(avgAttractors);
			String E = Double.toString(avgElements);
			String G = Double.toString(avgGenes);
			String D = Double.toString(avgDimers);
			String I = Double.toString(avgIsolated);
			String K = Double.toString(avgSinks);
			String O = Double.toString(avgSources);
			String C = Double.toString(avgConnected);
			String ID = Double.toString(avgInDegree);
			String OD = Double.toString(avgOutDegree);
			String GT = Double.toString(avgGTRatio);
			String PI = Double.toString(avgPosInteractions);
			String NI = Double.toString(avgNegInteractions);
			String IF = Double.toString(minFit);
			String AF = Double.toString(maxFit);
			String IA = Double.toString(minAttr);
			String AA = Double.toString(maxAttr);
			
			int cut = 5;
			if (F.length() > cut)
				F = F.substring(0, cut);
			if (A.length() > cut)
				A = A.substring(0, cut);
			if (E.length() > cut)
				E = E.substring(0, cut);
			if (D.length() > cut)
				D = D.substring(0, cut);
			if (I.length() > cut)
				I = I.substring(0, cut);
			if (ID.length() > cut)
				ID = ID.substring(0, cut);
			if (GT.length() > cut)
				GT = GT.substring(0, cut);
			if (IF.length() > cut)
				IF = IF.substring(0, cut);
			if (AF.length() > cut)
				AF = AF.substring(0, cut);
			if (IA.length() > cut)
				IA = IA.substring(0, cut);
			if (AA.length() > cut)
				AA = AA.substring(0, cut);
			if (G.length() > cut)
				G = G.substring(0, cut);
			if (K.length() > cut)
				K = K.substring(0, cut);
			if (O.length() > cut)
				O = O.substring(0, cut);
			if (ID.length() > cut)
				ID = ID.substring(0, cut);
			if (OD.length() > cut)
				OD = OD.substring(0, cut);
			if (C.length() > cut)
				C = C.substring(0, cut);
			if (PI.length() > cut)
				PI = PI.substring(0, cut);
			if (NI.length() > cut)
				NI = NI.substring(0, cut);
			
			stats += ("\n==========================================================================================\n");
			stats += ("| \t\t--- POPULATION STATISTICS --- \n");
			stats += ("|\tElem.\tGenes\tDim.\tIsol.\tSources\tSinks\tConn.\n");
			stats += ("|\t" + E + "\t" + G +"\t" + D + "\t" + I + "\t" + O + "\t" + K + "\t" + C + "\n");
			stats += ("|\tFitn.\tminFit\tmaxFit\t\tAttr.\tminAtt\tmaxAtt\t\tG/T\n");
			stats += ("|\t" + F + "\t" + IF	+ "\t" + AF + "\t\t" + A + "\t" + IA + "\t" + AA + "\t\t" + GT + "\n");
			stats += ("|\tInD.\tOutD.\t\tPosI.\tNegI.\n");
			stats += ("|\t" + ID + "\t" + OD + "\t\t" + PI + "\t" + NI + "\n");
			stats += ("===========================================================================================\n");
			stats += "key: o=source - k=sink - i=isolated gene - c=connected gene - D=dimer - I = isolated dimer\n\n";
			Network net;
			//		String idString = "";
			String notCString = "notC.:\t";
			String compString = "comp:\t";
			String inputString = "R/D.in:\t";
			String RegOutString = "R.out:\t";
			String DimOutString = "D.out:\t";
			String fitString = "fitn.:\t";
			String space = "";
			for (int i = 0; i < pop.size(); i++) {
				net = pop.get(i);
				space = "";
				//idString += ("net:"+net.getID()+"\t");
				notCString += " /" + (net.printNotConsidered() + "\\ \t");
				compString += "| " + (net.printComposition() + " |\t");
				inputString += "| " + (net.printInputNumbers() + " |\t");
				RegOutString += "| " + (net.printRegOutputNumbers() + " |\t");
				DimOutString += " \\" + (net.printDimOutputNumbers() + "/ \t");
				for(int st = 0; st < (net.size() -F.length()); st++)
					space += "-";
				fitString += " <" +F+space+ "> \t";
				if ((i + 1) % p.POPSTAT_COLUMNS == 0) {
					//				stats += idString + "\n";
					stats += notCString + "\n";
					stats += compString + "\n";
					stats += inputString + "\n";
					stats += RegOutString + "\n";
					stats += DimOutString + "\n";
					stats += fitString + "\n";
					stats += "\n";
					notCString = "notC.:\t";
					compString = "comp:\t";
					inputString = "R/D.in:\t";
					RegOutString = "R.out:\t";
					DimOutString = "D.out:\t";
					fitString = "fitn.:\t";
				}
			}
			stats += p.toString();
		}		
		return stats;
	}
	
	public void updateStats(){
		if (!pop.isEmpty()) {
			//		this.pop = process.getPopulation(); //TODO why doesn't this work??? have to use setPopulation instead...
			Network net = pop.get(0);
			NetworkDataCollector dc;
			double gt;
			double minf = Double.POSITIVE_INFINITY;
			double maxf = 0;
			int mina = (int) Double.POSITIVE_INFINITY;
			int maxa = 0;
			for (int i = 0; i < pop.size(); i++) {
				net = pop.get(i);
				dc = net.getDataCollector();
				dc.updateStats();
				if (dc.getCurrentFitness() < 0.001)
					avgFitness += 0.0;
				else
					avgFitness += dc.getCurrentFitness();
				avgAttractors += dc.getAttractors();
				avgElements += dc.getElements();
				avgGenes += dc.getGenes();
				avgDimers += dc.getDimers();//(dc.getDimers()/dc.getElements());
				avgIsolated += dc.getIsolated();
				avgSinks += dc.getSinks();
				avgSources += dc.getSources();
				
				avgInDegree += dc.getAvgInDegree();
				avgOutDegree += dc.getAvgOutDegree();
				avgPosInteractions += dc.getPosInteractions();
				avgNegInteractions += dc.getNegInteractions();
				if (!(dc.getAttractors() == 0)) {
					gt = dc.getGenes();
					gt /= dc.getAttractors();
				}
				else
					gt = 0;
		
				avgGTRatio += gt;
		
				if (dc.getCurrentFitness() < minf)
					minf = dc.getCurrentFitness();
				if (dc.getCurrentFitness() > maxf)
					maxf = dc.getCurrentFitness();
				if (dc.getAttractors() < mina)
					mina = dc.getAttractors();
				if (dc.getAttractors() > maxa)
					maxa = dc.getAttractors();
		
			}
			if (minf < 0.001)
				minf = 0.0;
			if (maxf < 0.001)
				maxf = 0.0;
			avgFitness /= pop.size();
			avgAttractors /= pop.size();
			avgElements /= pop.size();
			avgGenes /= pop.size();
			avgDimers /= pop.size();
			avgIsolated /= pop.size();
			avgSources /= pop.size();
			avgSinks /= pop.size();
			avgConnected = avgGenes - (avgIsolated + avgSources + avgSinks);
			avgInDegree /= pop.size();
			avgOutDegree /= pop.size();
			avgPosInteractions /= pop.size();
			avgNegInteractions /= pop.size();
			avgGTRatio /= pop.size();
			minFit = minf;
			maxFit = maxf;
			minAttr = mina;
			maxAttr = maxa;
		}
	}

	public double getAvgAtractors() {
		return avgAttractors;
	}


	public void setAvgAtractors(double avgAtractors) {
		this.avgAttractors = avgAtractors;
	}


	public double getAvgDimers() {
		return avgDimers;
	}


	public void setAvgDimers(double avgDimerProp) {
		this.avgDimers = avgDimerProp;
	}


	public double getAvgElements() {
		return avgElements;
	}


	public void setAvgElements(double avgElements) {
		this.avgElements = avgElements;
	}


	public double getAvgFitness() {
		return avgFitness;
	}


	public void setAvgFitness(double avgFitness) {
		this.avgFitness = avgFitness;
	}


	public double getAvgIsolated() {
		return avgIsolated;
	}


	public void setAvgIsolated(double avgIsolated) {
		this.avgIsolated = avgIsolated;
	}

	public NetworkVector getPop() {
		return pop;
	}

	public void setPop(NetworkVector pop) {
		this.pop = pop;
	}
	
}
