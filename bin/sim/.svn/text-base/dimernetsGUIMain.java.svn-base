package sim;

import sim.datatype.MyVector;
import sim.gui.GraphicalInterface;

public class dimernetsGUIMain {
	private static GraphicalInterface gui;
	
	public static void main( String[] args ){
		MyVector<EvolutionaryProcess> processes = new MyVector<EvolutionaryProcess>();
		Parameters parms = new Parameters();
		parms.DEFAULT_NETWORK = 1;
		parms.DEFAULT_GENERATOR = 6;
		parms.WRITE_FILES = false;
		parms.GENERATIONS = 1000;
		
		parms.ONLY_DIMERS_REGULATE = true;
		
		parms.DIMER_DYNAMICS_RATE = 0.1;
//		parms.LOSS_OF_DIMER_RATE = 0.8;
//		parms.NEW_HETERODIMER_RATE = 0.2;
//		parms.NEW_HOMODIMER_RATE = 0.0;
		
//		parms.ATTR_DISTANCE_THRESHOLD = 0.8;
		
//		parms.ATTRACTOR_PROFILE_TRANS = false;
		
		
		parms.USE_NETWORK_HISTORY = true;
//		parms.RATIO_TERM_PROPORTION = 0.6;
//		parms.GENES_TERM_PROPORTION = 0.4;
//		parms.ISOLATED_TERM_PROPORTION = 0;
		
//		parms.KEEP_INPUT_PROBABILITY = 1.0;
//		parms.KEEP_OUTPUT_PROBABILITY = 1.0;
//		parms.KEEP_DIMER_OUTPUT_PROBABILITY = 1.0;
		
//		parms.REPLACE_LINK_PROBABILITY = 0.0;
		
		parms.STABLE_DIMER_TIME = 2;
		
		
		processes.add(new DefaultProcess(parms));
		gui = new GraphicalInterface(processes);
		gui.setVisible(true);
//		processes.get(0).fileManager.saveParametersToFile(processes.get(0).getParameters(), "parmfile");
	}
}
