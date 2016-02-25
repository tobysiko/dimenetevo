package sim;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import sim.datatype.AbstractSimulationState;
import sim.datatype.DefaultSimulationState;
import sim.datatype.Element;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.datatype.PopulationHistoryEntry;
import sim.gui.PopStatsOutputter;
import sim.operation.DefaultNetworkCollector;
import sim.operation.DefaultProcessCollector;




public class DefaultProcess extends EvolutionaryProcess implements Runnable,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean generateNewPop;
	private PopStatsOutputter statsOut;
	private boolean terminated;
	
	
	
	public DefaultProcess() {
		super();
		simState = new DefaultSimulationState(population, generation);
		generateNewPop = true;
		paused = false;
		terminated = false;
		if (parameters.PRINT_PROCESS_STATS){
			statsOut = new PopStatsOutputter(this);
			statsOut.display();
		}
		
	}
	public DefaultProcess(NetworkVector pop){
		super();
		this.population = pop;
		simState = new DefaultSimulationState(population, generation);
		generateNewPop = false;
		paused = false;
		terminated = false;
		if (parameters.PRINT_PROCESS_STATS){
			statsOut = new PopStatsOutputter(this);
			statsOut.display();
		}
		
	}
	public DefaultProcess(Parameters p) {
		super(p);
		simState = new DefaultSimulationState(population, generation);
		generateNewPop = true;
		paused = false;
		terminated = false;
		if (parameters.PRINT_PROCESS_STATS){
			statsOut = new PopStatsOutputter(this);
			statsOut.display();
		}
		
	}
	public DefaultProcess(Parameters p, NetworkVector pop){
		super(p);
		this.population = pop;
		simState = new DefaultSimulationState(population, generation);
		generateNewPop = false;
		paused = false;
		terminated = false;
		if (parameters.PRINT_PROCESS_STATS){
			statsOut = new PopStatsOutputter(this);
			statsOut.display();
		}
		
	}
	
	
	public void updateState(){
		simState.setGeneration(generation);
		simState.setPopulation(population);
	}

	public void pauseProcess() {
		paused = true;

	}

	public void startFrom(AbstractSimulationState state) {
		System.out.println("starting from: "+state);
		simState.setPopulation(state.getPopulation());
		simState.setGeneration(state.getGeneration());
		generateNewPop = false;
		start();
	}

	public AbstractSimulationState terminateProcess() {
		
		paused = false;
		terminated = true;
		synchronized(this){
			notifyAll();
		}
		AbstractSimulationState temp = this.simState;
//		resetState();
		return temp;
	}

	public AbstractSimulationState getProcessState() {
		return simState;
	}

	public void resumeProcess() {
		generateNewPop = false;
		paused = false;
		synchronized(this){
			notifyAll();
		}

//		startFrom(simState);
	}

	public void startProcess() {
//		resetState();
		start();
	}
	public void resetState(){
		simState.getPopulation().removeAllElements();
		simState.setGeneration(0);
		generateNewPop = true;
	}

	
	public void run() {
		
//		this.setName("simulation-"+this.getId());

		Date startTime = new Date();
		System.out.println("DefaultProcess.run() of process "+processID+" called at " + startTime);
		
//		if (parameters.DELETE_OLD_FILES){
//			fileManager.deleteAllStates();
//		}
		
		paused = false;
		terminated = false;
		
		if (generateNewPop){
			System.out.println("generate new population...");
			for (int i = 0; i < parameters.POPULATION_SIZE; i++){
				
				population.add(parameters.getDefaultNetwork());
				
			}
			generateNewPop = false;
		}
		
		Date genTime = new Date();
		long preTime,postTime,preAllSim,postAllSim,preSel,postSel;
		
//		System.out.println("preloop-population: "+population.size());
//		if (generation == 0){
//			fileManager.writeStateToFileCompact(simState, this.folderPath+"/generation_");
//		}
		
		File logfile = null;
		FileWriter logWriter = null;
		BufferedWriter logBuffy = null;
		if (parameters.WRITE_FILES && parameters.KEEP_PERFORMANCE_LOG){
			try {
				String maxmem = "";
				maxmem = Math.round(Runtime.getRuntime().maxMemory() / 1000000)+ "M";
				logfile = new File(getFolderPath()+File.separator+"performance_"+maxmem+".log");
				System.out.println("setting up performance log: "+logfile.getAbsoluteFile());
				logWriter = new FileWriter(logfile);
				logBuffy = new BufferedWriter(logWriter);
				logBuffy.write("gen\t"+"time\t"+"elems\t"+"mem\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		File popFile = null;
		FileWriter popWriter = null;
		BufferedWriter popBuffy = null;
		
		if (parameters.WRITE_FILES) {
			try {
				popFile = new File(this.folderPath + File.separator
						+ "pop_stats");
				popWriter = new FileWriter(popFile);
				popBuffy = new BufferedWriter(popWriter);
				popBuffy.write(DefaultProcessCollector.popStatsColumnNames);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
		
//		START LOOP!
		while(!terminated && generation <= parameters.GENERATIONS){
//			System.out.println("generation* " + generation);
			
			synchronized(this){
				try {
					while (paused){
						System.out.println("wait!");
						this.wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (parameters.INCREASE_SINKS_ON){
				if (generation != 0 
						&& generation % parameters.INCREASE_SINKS_INTERVAL == 0){
					
//					for SinksFitnessFunction
					optimalSinksNumber += parameters.INCREASE_SINKS_INTERVAL;
					
					System.out.println("Adding " +parameters.SINKS_INCREMENT+ " sinks to each network!");
					
					for (Network n:population){
						
						for(int i = 0; i < parameters.SINKS_INCREMENT; i++){
							Element newSink = n.addElement();
							newSink.setSink(true);
						}
						
						n.generateInitialStates();
						
						n.getDataCollector().setAttractorsOld(true);
					}
				}
			}
			
			genTime = new Date();
			preTime = new Date().getTime();
			System.out.print("Process: " +this.getName()+"("+processID+")"+ " - Generation: " +generation+ " - time running(s): "+(float)((genTime.getTime()-startTime.getTime()))/1000);
			
//			System.out.print("Process: " +processID+ " - Generation: " +generation+ " - time running(s): "+(float)((genTime.getTime()-startTime.getTime()))/1000);
			long memUsed = (Runtime.getRuntime().totalMemory() -
			        Runtime.getRuntime().freeMemory());
			long memAvail = Runtime.getRuntime().maxMemory();
			float formattedMemUsed = (float) (memUsed  / 1000000);
			float formattedMemAvail = (float) (memAvail  / 1000000);
			int totalElements= 0;
			int totalGenes= 0;
			int totalDimers=0;
			for (Network nett:population){
				totalGenes+= nett.getGenes().size();
				totalDimers+= nett.getDimers().size();
				totalElements += nett.getElementVector().size();
			}
			System.out.println(" - used memory: " + formattedMemUsed + "M/"+formattedMemAvail+"M - G/D: "+totalGenes+"/"+totalDimers+" ("+totalElements+")");

			if (parameters.PRINT_PROCESS_STATS){
				String s = ("Process: " +processID+ " - Generation: " +generation+ " - PopSize: " +population.size()+" - time running(ms): "+(genTime.getTime()-startTime.getTime())+"\n")
					+ this.getCollector().getStatsString();
				
//				System.out.println(s);
				if(generation % 10 == 0)
					statsOut.update(s,this);
				
//				System.out.println("after Selection:");
//				this.getCollector().setPop(population);
//				this.getCollector().printStats();
			}
			
			preAllSim = new Date().getTime();
			
			for (Network net:population) {
				
				simulator.simulateAllStates(net);
				
				if (parameters.PRINT_NET_STATS) {
					System.out.println("after Simulator:");
					net.getDataCollector().printStats();
				}	
//				if (generation % 100 == 0){
//					parameters.VERBOSE = true;
////					System.out.println("\nAttractors for network["+population.indexOf(net)+"]: "+net);
////					net.getDataCollector().printAttractors();
//				}
//				else
//					parameters.VERBOSE = false;
//				if (paused) 
//					break generationloop;
			}
			
			if (parameters.PRINT_DURATIONS){
				postAllSim = new Date().getTime();
				System.out.println("SIM: "+(postAllSim-preAllSim)+"ms");
			}
			
			Runtime.getRuntime().gc();

			
			updateState();
			
			if (parameters.PRINT_DURATIONS){
				preSel = new Date().getTime();
				this.population = selector.transformPopulation(this.population);
				
				postSel = new Date().getTime();
				System.out.println("SELECT: "+(postSel-preSel)+"ms");
			}else
				this.population = selector.transformPopulation(this.population);

		
			Runtime.getRuntime().gc();


			if (parameters.USE_NETWORK_HISTORY){
	//			add entry to network-history
				double fitness;
				double term1;
				double term2;
				double term3;
				int attractors;
				int complex;
				int genes;
				int dimers;
				double meanIn;
				double meanOut;
				int acts;
				int reps;
				int mutation;
				
				for (Network n:population){
					n.getDataCollector().updateStats();
					fitness = n.getDataCollector().getCurrentFitness();
					term1 = n.getDataCollector().getCurrentTerm1();
					term2 = n.getDataCollector().getCurrentTerm2();
					term3 = n.getDataCollector().getCurrentTerm3();
					attractors = n.getDataCollector().getAttractors();
					complex = 0;
	//				complex attractors = attractors with cycle length >= 2
					for (int i = 2; i < n.getDataCollector().getCycleLengthHistogram().size(); i++){
						complex += n.getDataCollector().getCycleLengthHistogram().get(i);
					}
					genes = n.getGenes().size();
					dimers = n.getDimers().size();
					meanIn = n.getDataCollector().getAvgInDegree();
					meanOut = n.getDataCollector().getAvgOutDegree();
					acts =  n.getDataCollector().getPosInteractions();
					reps =  n.getDataCollector().getNegInteractions();
					mutation = n.getDataCollector().getCurrentMutation();
					
					
					n.getDataCollector().getHistory().addGenerationData(generation, 
																		fitness,
																		term1,
																		term2,
																		term3,
																		attractors,
																		complex,
																		genes,
																		dimers,
																		meanIn,
																		meanOut,
																		acts,
																		reps,
																		mutation);
					n.getDataCollector().setCurrentMutation(0);
				}
			}
			
			if (generation % parameters.WRITE_STATE_INTERVAL == 0){
				if (parameters.WRITE_FILES) 
					fileManager.writeStateToFileCompact(simState, this.folderPath+"/generation_");
			}
			
			this.getCollector().setPop(population);
			
//			write to population history
			if (generation % parameters.POPULATION_HISTORY_INTERVAL == 0){
				
				getCollector().newEntry();
				if (parameters.WRITE_FILES) {
					try {
						popBuffy.write(collector.getCurrentStats().toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			totalElements= 0;
			for (Network nett:population){
				
				totalElements += nett.getElementVector().size();
			}
			
			if (parameters.VERBOSE)
				System.out.println("elems:"+totalElements);
			memUsed = (Runtime.getRuntime().totalMemory() -
			        Runtime.getRuntime().freeMemory());
			if (parameters.VERBOSE)
				System.out.println("Java memory in use = " + memUsed + " - available:"+Runtime.getRuntime().maxMemory());
			
			postTime = new Date().getTime();
			
			if (parameters.PRINT_DURATIONS) 
				System.out.println("TOTAL: "+(long)(postTime-preTime)+"ms");
			
			if (parameters.WRITE_FILES && parameters.KEEP_PERFORMANCE_LOG && ( (generation % 10 == 0) || generation == 0) ){
				if (parameters.SHOW_FILE_OPERATIONS)
					System.out.println("writing to log!");
				try {
					formattedMemUsed = (float) (memUsed  / 1000000);
					String str = generation + "\t" 
								+ ((postTime-preTime)/1000) + "\t" 
								+ (totalElements/population.size()) + "\t" 
								+ formattedMemUsed + "\n";
					logBuffy.write(str);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			generation++;
			updateState();
			
			Runtime.getRuntime().gc();

		}
		
//		clean-up...
		
		if (parameters.WRITE_FILES && parameters.KEEP_PERFORMANCE_LOG){
			try {
				logBuffy.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (parameters.WRITE_FILES && parameters.USE_NETWORK_HISTORY){
			
	//		only write history for fittest proportion of final population
			long fittest = Math.round(parameters.FITTEST_PROPORTION * population.size());
			NetworkVector fitSubPop = new NetworkVector();
			for (int i = 0; i < fittest; i++){
				fitSubPop.add(population.get(i));
			}
	
	
			for (Network n:fitSubPop){
	//			System.out.println("index:"+population.indexOf(n));
	//			System.out.println(""+n);
				fileManager.writeNetworkHistory(n.getDataCollector().getHistory(), this.folderPath+File.separator+"history_network_", true);
				
			}
		}
//		fileManager.writePopulationHistory(getCollector().getHistory(), this.folderPath + File.separator + "pop_stats");
		
		if (parameters.WRITE_FILES) {
			try {
				popBuffy.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("DefaultProcess.run() finished at " + new Date());
		
		paused = false;
		
		terminated = false;
		
		if (parameters.PRINT_PROCESS_STATS) 
			statsOut.terminate();
		
	}
	/* (non-Javadoc)
	 * @see sim.EvolutionaryProcess#startFromAndPause(sim.datatype.AbstractSimulationState)
	 */
	@Override
	public void startFromAndPause(AbstractSimulationState state) {
		
		
	}

}
