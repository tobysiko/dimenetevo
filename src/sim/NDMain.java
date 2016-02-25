package sim;
import java.io.File;
import java.util.Date;
import java.util.Vector;

import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.MatrixElement;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.datatype.OnewayInteraction;
import sim.operation.DefaultFileManager;
import sim.operation.DefaultMutator;
import sim.operation.FileManager;

public class NDMain {
	public synchronized static void main( String[] args )
	{
		System.out.println("maximum memory available: "+Runtime.getRuntime().maxMemory());
		System.out.println("# processors available: " +Runtime.getRuntime().availableProcessors());
	
		Parameters parm;
		String baseFolderName;
		EvolutionaryProcess process = null;
		FileManager fm;
		String processfolder;

//		if one argument found, use it as filename for parameters-file and load it.
		if (args.length != 1){
			System.out.println("Parameters file must be provided!");
			System.exit(0);
			return;
		}
		
		String parametersFileName = args[0];
		parm = DefaultFileManager.loadParametersStatic(parametersFileName);
		parametersFileName = parametersFileName.replace(".parameters", "");
		baseFolderName = "sim_"+parametersFileName+"_"+(new Date()).getTime();
		
		process = new DefaultProcess(parm);
		process.getParameters().setProcess(process);
		
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

		process.setPopulation(startPop);
		process.getPopulation().setProcess(process);
		for (Network n:process.getPopulation()){
			n.setProcess(process);
			n.getDataCollector().setParameters(process.getParameters());
		}
		fm = process.getFileManager();
		
		//				simulation without dimers
		process.setName("ND");
		System.out.println("== simulation with no dimers ==");
		processfolder = fm.createProcessFolder(basefolder.getPath()+File.separator+"NO_DIMERS");
		
		process.getParameters().ONLY_DIMERS_REGULATE = false;
		process.getParameters().DIMER_DYNAMICS_RATE = 0.0;
		

		fm.saveParametersToFile(process.getParameters(), processfolder+File.separator+"parameters");
		
		System.out.println("STARTING PROCESS: " +process.getName());
		process.startProcess();
		
		try {
			
			while(process.isAlive()){
				Thread.sleep(500);
			}
				
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		zip output folder
		String zipFileName = DefaultFileManager.zipDirectory(basefolder.getPath());
		System.out.println("zipped..." + zipFileName);
		
		
//		delete original folder (recursively!) that was zipped
		DefaultFileManager.removeFolder(basefolder.getPath());
		
	}

}

