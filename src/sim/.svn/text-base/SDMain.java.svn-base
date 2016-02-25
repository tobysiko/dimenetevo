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

public class SDMain {
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
		

//		simulation with dimers, stable
		process.setName("SD");
		System.out.println("== simulation with stable dimers ==");
		processfolder = fm.createProcessFolder(basefolder.getPath()+File.separator+"STABLE_DIMERS");

		process.getParameters().STABLE_DIMER_TIME = 2;
		process.getParameters().ONLY_DIMERS_REGULATE = true;
		
		insertDimers(process);
		
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
