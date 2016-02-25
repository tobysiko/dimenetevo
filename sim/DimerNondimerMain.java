package sim;

import java.io.File;
import java.util.Date;

import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.operation.DefaultFileManager;
import sim.operation.FileManager;

public class DimerNondimerMain {
	
	public synchronized static void main( String[] args )
	{
		Parameters parm;
		String baseFolderName;
//		if one argument found, use it as filename for parameters-file and load it.
		if (args.length == 1){
			String parametersFileName = args[0];
			parm = DefaultFileManager.loadParametersStatic(parametersFileName);
			parametersFileName = parametersFileName.replace(".parameters", "");
			baseFolderName = "sim_"+parametersFileName;
		}
		else{
			parm = new Parameters();
//			create folder for output from these processes
			baseFolderName = "sim_"+(new Date()).getTime();
		}
		
		File basefolder = new File(baseFolderName);
		if (basefolder.mkdir()){
			System.out.println("created base folder: "+basefolder.getAbsolutePath());
		}else {
			System.out.println("could NOT create base folder: "+basefolder.getAbsolutePath());
		}
		
//		the number of processes that will run in parallel
		int nProcesses = 2;
		
//		create processes
		EvolutionaryProcess[] processes = new EvolutionaryProcess[nProcesses];
		for (int i = 0; i < processes.length; i++){
			processes[i] = new DefaultProcess(parm, new NetworkVector());
//			parm.setProcess(processes[i]);
		}
		
//		create population
		NetworkVector startPop = new NetworkVector();
		for (int j = 0; j < processes[0].getParameters().POPULATION_SIZE; j++){
			Network net = processes[0].getParameters().getDefaultGenerator().generateNewNetwork();
			startPop.add(net);
		}
		
//		give each process a clone of that population
		for (int i = 0; i < processes.length; i++){
//			processes[i].getPopulation().removeAllElements();
			processes[i].setPopulation((NetworkVector)startPop.clone());
			processes[i].getPopulation().setProcess(processes[i]);
//			System.out.println(""+processes[i].getPopulation());
		}
		
//		create output folder for each process. they will be subfolders of "basefolder".
		FileManager fm0;
		fm0 = processes[0].getFileManager();
		String processfolder0 = fm0.createProcessFolder(basefolder.getPath()+File.separator+"DIMERS_process_"+processes[0].getId());
//		simulation with dimers
		processes[0].getParameters().DIMER_DYNAMICS_RATE = 0.1;
		fm0.saveParametersToFile(processes[0].getParameters(), processfolder0+File.separator+"parameters");
		
		FileManager fm1;
		fm1 = processes[1].getFileManager();
		String processfolder1 = fm1.createProcessFolder(basefolder.getPath()+File.separator+"NO_DIMERS_process_"+processes[1].getId());
//		simulation without dimers
		processes[1].getParameters().DIMER_DYNAMICS_RATE = 0.0;
		fm1.saveParametersToFile(processes[1].getParameters(), processfolder1+File.separator+"parameters");
		
//		start processes as individual threads
		for (int i = 0; i < processes.length; i++){
			System.out.println("STARTING PROCESS " +i);
//			System.out.println("pop:"+processes[i].getPopulation().size());
			processes[i].startProcess();
//			processes[i].start();
			if (!parm.USE_PARALLEL_PROCESSES){
				System.out.println("NOT using parallel threads.");
				while(processes[i].isAlive()){
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else{
				System.out.println("using parallel threads.");
			}
		}
		
//		loop that will run until both proceses have terminated
		while (processes[0].isAlive() || processes[1].isAlive()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		zip output folder
		String zipFileName = DefaultFileManager.zipDirectory(basefolder.getPath());
		if(parm.SHOW_FILE_OPERATIONS)System.out.println("zipped..." + zipFileName);
//		DefaultFileManager.printZipFileContents(zipFileName);
		
//		delete original folder (recursively!) that was zipped
		DefaultFileManager.removeFolder(basefolder.getPath());
		
	}
}
