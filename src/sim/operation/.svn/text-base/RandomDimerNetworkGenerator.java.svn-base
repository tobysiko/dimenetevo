package sim.operation;

import java.util.Date;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.MatrixElement;
import sim.datatype.MatrixNetwork;
import sim.datatype.Network;
import sim.operation.NetworkGenerator;

public class RandomDimerNetworkGenerator implements NetworkGenerator {
	
//	random number generator
	private RandomEngine generator = new MersenneTwister(new Date());
	
//	uniform distribution
	private Uniform uniform = new Uniform(generator);
	
//	program parameters
	private Parameters p;
	private EvolutionaryProcess process;
	public RandomDimerNetworkGenerator(EvolutionaryProcess process){
		this.process = process;
		p = process.getParm();
	}
	public RandomDimerNetworkGenerator() {
		// TODO Auto-generated constructor stub
	}

	public Network generateNewNetwork() {

//		if (p.MINIMAL_OUTPUT) System.out.println("CALLED: RandomDimerNetworkGenerator#generateNewNetwork(Parameters p)");
		if (true) System.out.println("CALLED: RandomDimerNetworkGenerator#generateNewNetwork(Parameters p)");
		
//		generates the actual network of size specified in p
		MatrixNetwork net = new MatrixNetwork(process);
		
//		for each gene in the network ...
		for (int i = 0; i < net.size(); i++){
			Element dimer = new MatrixElement();
			dimer.setDimer(true);
			net.addElement(dimer);
			net.addInteraction(net.get(i), p.HOMODIMER_MATRIX_VALUE, dimer);
//			
		}
		ElementVector dimers = net.getDimers();
		for (Element gene:net.getGenes()){
//			add first random regulatory input
			if(uniform.nextFloatFromTo(0.0f, 1.0f) <= p.RNG_P_FIRST_INPUT){ 
				net.addRegulatoryInteraction(dimers.get(uniform.nextIntFromTo(0, dimers.size()-1)), uniform.nextBoolean(), gene);
				
//				add second random regulatory input
				if(uniform.nextFloatFromTo(0.0f, 1.0f) <= p.RNG_P_SECOND_INPUT){ 
					net.addRegulatoryInteraction(dimers.get(uniform.nextIntFromTo(0, dimers.size()-1)), uniform.nextBoolean(), gene);
				}
			}
		}
		
//		return the new network ...
		return net;
		
	}

}
