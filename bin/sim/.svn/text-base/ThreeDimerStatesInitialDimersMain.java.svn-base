package sim;

import java.io.File;
import java.util.Date;

import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.operation.DefaultFileManager;
import sim.operation.DefaultMutator;
import sim.operation.FileManager;


public class ThreeDimerStatesInitialDimersMain {
	public synchronized static void main( String[] args )
	{
		System.out.println("maximum memory available: "+Runtime.getRuntime().maxMemory());
		System.out.println("# processors available: " +Runtime.getRuntime().availableProcessors());
	
		Parameters parm;
		String baseFolderName;
//		if one argument found, use it as filename for parameters-file and load it.
		if (args.length == 1){
			String parametersFileName = args[0];
			parm = DefaultFileManager.loadParametersStatic(parametersFileName);
			parm.setProcess(new DefaultProcess());
			parametersFileName = parametersFileName.replace(".parameters", "");
			baseFolderName = "sim_"+parametersFileName;
		}
		else{
			parm = new Parameters(new DefaultProcess());
//			create folder for output from these processes
			baseFolderName = "sim_"+(new Date()).getTime();
		}
		
		File basefolder = new File(baseFolderName);
		if (basefolder.mkdir()){
			System.out.println("created base folder: "+basefolder.getPath());
		}else {
			System.out.println("could NOT create base folder: "+basefolder.getPath());
		}
		
		
//		create population
		NetworkVector startPop = new NetworkVector();
		Network net;
		for (int j = 0; j < parm.POPULATION_SIZE; j++){
			net = parm.getDefaultGenerator().generateNewNetwork();
			startPop.add(net);
		}
		
		
//		the number of processes that will run in parallel
		int nProcesses = 3;
		
//		create processes
		EvolutionaryProcess process = null;
		FileManager fm;
		String processfolder;
		for (int i = 0; i < nProcesses; i++){
			try {
				process = new DefaultProcess((Parameters)parm.clone(), new NetworkVector());
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
//			give each process a clone of startPop population
			process.setPopulation((NetworkVector)startPop.clone());
			process.getPopulation().setProcess(process);
			
			fm = process.getFileManager();
			switch (i){
			case 0:{
//				create output folder for each process. they will be subfolders of "basefolder".
				
				processfolder = fm.createProcessFolder(basefolder.getPath()+File.separator+"UNSTABLE_DIMERS");
//				simulation with dimers, unstable
				
				process.getParameters().STABLE_DIMER_TIME = 1;
				process.getParameters().DIMER_DYNAMICS_RATE = 0.0;
				addHomodimersToProcess(process);
				fm.saveParametersToFile(process.getParameters(), processfolder+File.separator+"parameters");
				
				break;
			}
			case 1:{
				processfolder = fm.createProcessFolder(basefolder.getPath()+File.separator+"STABLE_DIMERS");
//				simulation with dimers, stable
				process.getParameters().STABLE_DIMER_TIME = 2;
				process.getParameters().DIMER_DYNAMICS_RATE = 0.0;
				addHomodimersToProcess(process);
				fm.saveParametersToFile(process.getParameters(), processfolder+File.separator+"parameters");
			
				break;
			}
			case 2:{
				
				processfolder = fm.createProcessFolder(basefolder.getPath()+File.separator+"NO_DIMERS");
//				simulation without dimers
				process.getParameters().DIMER_DYNAMICS_RATE = 0.0;
				fm.saveParametersToFile(process.getParameters(), processfolder+File.separator+"parameters");
				
				break;
			}
			default:{
				System.out.println("ERROR: process not defined!");
				break;
			}
			}
			
			System.out.println("STARTING PROCESS " +i);
//			System.out.println("pop:"+processes[i].getPopulation().size());
			process.startProcess();
//			processes[i].start();
			if (parm.USE_PARALLEL_PROCESSES) {
				System.out.println("using parallel threads.");
			}
			else {
				System.out.println("NOT using parallel threads.");
				try {
					
					while(process.isAlive()){
						Thread.sleep(1000);
					}
						
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
//		zip output folder
		String zipFileName = DefaultFileManager.zipDirectory(basefolder.getPath());
		System.out.println("zipped..." + zipFileName);
		
		
//		delete original folder (recursively!) that was zipped
		DefaultFileManager.removeFolder(basefolder.getPath());
		
	}
	
	public static void addHomodimersToProcess(EvolutionaryProcess process){
		for (Network n:process.getPopulation()){
			DefaultMutator.formNewHomodimer(n, process);
			System.out.println(n.getInteractions().toString());
		}
	}

}
