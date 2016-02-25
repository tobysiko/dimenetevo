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

public class DimerNetworksMain {
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
			parm.getProcess().setName("temp");
			parametersFileName = parametersFileName.replace(".parameters", "");
			baseFolderName = "sim_"+parametersFileName+"_"+(new Date()).getTime();
		}
		else{
			
			parm = new Parameters(new DefaultProcess());
			parm.getProcess().setName("temp");
			
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
		
		
//		the number of processes that will run (((in parallel?)))
		int nProcesses = 3;
		
//		create processes
		EvolutionaryProcess process = null;
		FileManager fm;
		String processfolder;
		for (int i = nProcesses-1; i >= 0; i--){
			try {
				Parameters parmcopy = (Parameters)parm.clone();
				
				process = new DefaultProcess(parmcopy, new NetworkVector());
				process.getParameters().setProcess(process);
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
//			give each process a clone of startPop population
			process.setPopulation((NetworkVector)startPop.clone());
			process.getPopulation().setProcess(process);
			for (Network n:process.getPopulation()){
				n.setProcess(process);
				n.getDataCollector().setParameters(process.getParameters());
			}
			fm = process.getFileManager();
			switch (i){
			case 0:{//				simulation with dimers, unstable
				process.setName("UD");
				System.out.println("== simulation with unstable dimers ==");
//				create output folder for each process. they will be subfolders of "basefolder".
				
				processfolder = fm.createProcessFolder(basefolder.getPath()+File.separator+"UNSTABLE_DIMERS");
				process.getParameters().STABLE_DIMER_TIME = 1;
//				process.getParameters().DIMER_DYNAMICS_RATE = 0.0;
				process.getParameters().ONLY_DIMERS_REGULATE = true;

				insertDimers(process);
//				addHomodimersToProcess(process);
				fm.saveParametersToFile(process.getParameters(), processfolder+File.separator+"parameters");
				
				break;
			}
			case 1:{//				simulation with dimers, stable
				process.setName("SD");
				System.out.println("== simulation with stable dimers ==");
				processfolder = fm.createProcessFolder(basefolder.getPath()+File.separator+"STABLE_DIMERS");

				process.getParameters().STABLE_DIMER_TIME = 2;
//				process.getParameters().DIMER_DYNAMICS_RATE = 0.0;
				process.getParameters().ONLY_DIMERS_REGULATE = true;

				insertDimers(process);
//				addHomodimersToProcess(process);
				fm.saveParametersToFile(process.getParameters(), processfolder+File.separator+"parameters");
			
				break;
			}
			case 2:{//				simulation without dimers
				process.setName("ND");
				System.out.println("== simulation with no dimers ==");
				processfolder = fm.createProcessFolder(basefolder.getPath()+File.separator+"NO_DIMERS");
				
				process.getParameters().ONLY_DIMERS_REGULATE = false;
				process.getParameters().DIMER_DYNAMICS_RATE = 0.0;
				
//				addHomodimersToProcess(process);
				fm.saveParametersToFile(process.getParameters(), processfolder+File.separator+"parameters");
				
				break;
			}
			default:{
				process.setName("???");
				System.out.println("ERROR: process not defined!");
				break;
			}
			}
			
			System.out.println("STARTING PROCESS " +i);
//			System.out.println("pop:"+processes[i].getPopulation().size());
			process.startProcess();
//			processes[i].start();
			System.out.println(process.getName());
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
	
	
	/**
	 * Lets all monomers homodimerise. Their dimers take over all regulations.
	 * @param process
	 */
	public static void insertDimers(EvolutionaryProcess process){
//		System.out.println("insert dimers !!!!!");
		for (Network n:process.getPopulation()){
			
//			ElementVector dimers = new ElementVector();
			for (Element e:n.getGenes()){
				Element dimer = n.addElement();
				
//				Element dimer = new MatrixElement(n, process.parameters);
//				dimers.add(dimer);
				dimer.setDimer(true);
				n.addInteraction(e, process.getParameters().HOMODIMER_MATRIX_VALUE, dimer);
				

				
//				the regulatory outputs of the new dimer
				Vector<OnewayInteraction> targets = new Vector<OnewayInteraction>();
				Vector<OnewayInteraction> regOuts = e.getRegOutputs();
				for (OnewayInteraction out:regOuts){
					targets.add(out);
				}
				
//				remove original outputs of gene
				while(!regOuts.isEmpty()){
					n.removeRegulatoryInteraction(regOuts.get(0).getElement(), e);
					
					regOuts.remove(0);
				}
				
//				add new homodimer-interaction between gene and new dimer
				n.addInteraction(e, process.getParameters().HOMODIMER_MATRIX_VALUE, dimer);
				
				for(OnewayInteraction target:targets){
					boolean regvalue = false;
					try {
						if (target.getValue() > 0) regvalue = true;
						else if (target.getValue() < 0) regvalue = false;
						else throw new Exception("not a valid value!");
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
//					add regulatory interaction between dimer and target
					n.addRegulatoryInteraction(dimer, regvalue, target.getElement());
				}
				
				
			}
			
//			for (Element d:dimers){
//				n.addElement(d);
//			}
		}
	}

	public static void addHomodimersToProcess(EvolutionaryProcess process){
		for (Network n:process.getPopulation()){
//			DefaultMutator.formNewHomodimer(n, process);
			System.out.println(n.getInteractions().toString());
		}
	}
}
