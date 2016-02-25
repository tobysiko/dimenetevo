package sim;

import sim.datatype.Network;
import sim.datatype.NetworkVector;



public class Main {

	
	public synchronized static void main( String[] args )
	{
		int nProcesses = 2;
		EvolutionaryProcess[] processes = new EvolutionaryProcess[nProcesses];
		NetworkVector startPop = new NetworkVector();
		
		for (int i = 0; i < processes.length; i++){
			
			
			
			processes[i] = new DefaultProcess();
			
			
		
		}
		for (int j = 0; j < processes[0].getParameters().INITIAL_NETWORK_SIZE; j++){
			Network net = processes[0].getParameters().getDefaultGenerator().generateNewNetwork();
			startPop.add(net);
		}
		for (int i = 0; i < processes.length; i++){
			processes[i].setPopulation((NetworkVector)startPop.clone());
		}
		processes[0].getParameters().GENE_DYNAMICS_RATE = 0.001;
		processes[0].getParameters().LINK_DYNAMICS_RATE = 0.1;
		processes[0].getParameters().DIMER_DYNAMICS_RATE = 0.1;
		
		processes[1].getParameters().GENE_DYNAMICS_RATE = 0.001;
		processes[1].getParameters().LINK_DYNAMICS_RATE = 0.01;
		processes[1].getParameters().DIMER_DYNAMICS_RATE = 0.0;
		
//		processes[0].getParameters().GENE_DYNAMICS_RATE = 0.001;
//		processes[0].getParameters().LINK_DYNAMICS_RATE = 0.1;
//		processes[0].getParameters().DIMER_DYNAMICS_RATE = 0.01;
//		
//		processes[1].getParameters().GENE_DYNAMICS_RATE = 0.001;
//		processes[1].getParameters().LINK_DYNAMICS_RATE = 0.01;
//		processes[1].getParameters().DIMER_DYNAMICS_RATE = 0.01;
//		
//		processes[2].getParameters().GENE_DYNAMICS_RATE = 0.01;
//		processes[2].getParameters().LINK_DYNAMICS_RATE = 0.01;
//		processes[2].getParameters().DIMER_DYNAMICS_RATE = 0.01;
//		
//		processes[3].getParameters().GENE_DYNAMICS_RATE = 0.001;
//		processes[3].getParameters().LINK_DYNAMICS_RATE = 0.1;
//		processes[3].getParameters().DIMER_DYNAMICS_RATE = 0.01;
//		
//		processes[4].getParameters().GENE_DYNAMICS_RATE = 0.001;
//		processes[4].getParameters().LINK_DYNAMICS_RATE = 0.01;
//		processes[4].getParameters().DIMER_DYNAMICS_RATE = 0.01;
//		
//		processes[5].getParameters().GENE_DYNAMICS_RATE = 0.01;
//		processes[5].getParameters().LINK_DYNAMICS_RATE = 0.01;
//		processes[5].getParameters().DIMER_DYNAMICS_RATE = 0.01;
		
		for (int i = 0; i < processes.length; i++){
			System.out.println("STARTING PROCESS " +i);
			processes[i].startProcess();
		
		}
	}


}
