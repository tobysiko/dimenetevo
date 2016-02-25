package sim;

import java.io.Serializable;
import java.util.Date;

import sim.datatype.AbstractSimulationState;
import sim.datatype.DefaultSimulationState;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.gui.PopStatsOutputter;




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
		resetState();
		start();
	}
	public void resetState(){
		simState.getPopulation().removeAllElements();
		simState.setGeneration(0);
		generateNewPop = true;
	}

	
	public void run() {
		this.setName("simulation-"+this.getId());

		Date startTime = new Date();
		System.out.println("DefaultProcess.run() of process "+processID+" called at " + startTime);
		
		if (parameters.DELETE_OLD_FILES){
			fileManager.deleteAllStates();
		}
		
		paused = false;
		terminated = false;
		
		if (generateNewPop){
			for (int i = 0; i < parameters.POPULATION_SIZE; i++){
				
				population.add(parameters.getDefaultNetwork());
				
			}
			generateNewPop = false;
		}
		
		Date genTime = new Date();
		long preTime,postTime,preAllSim,postAllSim,preSel,postSel;
		
		generationloop:
		while(!terminated && generation <= parameters.GENERATIONS){
			
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
			
			genTime = new Date();
			preTime = new Date().getTime();
			System.out.println("Process: " +processID+ " - Generation: " +generation+ " - time runnning(ms): "+(genTime.getTime()-startTime.getTime()));
			
			if (parameters.PRINT_PROCESS_STATS){
				String s = ("Process: " +processID+ " - Generation: " +generation+ " - PopSize: " +population.size()+" - time runnning(ms): "+(genTime.getTime()-startTime.getTime())+"\n")
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
			
			updateState();
			
			if (parameters.PRINT_DURATIONS){
				preSel = new Date().getTime();
				this.population = selector.transformPopulation(this.population);
				postSel = new Date().getTime();
				System.out.println("SELECT: "+(postSel-preSel)+"ms");
			}else
				this.population = selector.transformPopulation(this.population);
			
			this.getCollector().setPop(population);
			generation++;
			updateState();

			
			if (generation % parameters.WRITE_STATE_INTERVAL == 0){
				fileManager.writeStateToFile(simState);
			}
			postTime = new Date().getTime();
			
			if (parameters.PRINT_DURATIONS) 
				System.out.println("TOTAL: "+(long)(postTime-preTime)+"ms");
		}
//		clean-up...
		System.out.println("DefaultProcess.run() finished at " + new Date());
		
		paused = false;
		terminated = false;
		if (parameters.PRINT_PROCESS_STATS) 
			statsOut.terminate();

	}

}
