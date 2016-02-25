package sim.operation;

import java.util.Date;
import java.util.Vector;

import sim.DimerNetworksMain;
import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.Network;
import sim.datatype.OnewayInteraction;
import sim.graph.GraphNetwork;
import sim.graph.VertexElement;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

public class RandomDimerGraphNetworkGenerator implements NetworkGenerator{
//	random number generator
	private RandomEngine generator = new MersenneTwister(new Date());
	
//	uniform distribution
	private Uniform uniform = new Uniform(generator);
	
//	program parameters
	private Parameters p;
	private EvolutionaryProcess process;
	public RandomDimerGraphNetworkGenerator(EvolutionaryProcess process){
		this.process = process;
		p = process.getParm();
	}
	public RandomDimerGraphNetworkGenerator() {
		// TODO Auto-generated constructor stub
	}

	public Network generateNewNetwork() {
//
////		if (p.MINIMAL_OUTPUT) System.out.println("CALLED: RandomDimerNetworkGenerator#generateNewNetwork(Parameters p)");
//		if (true) System.out.println("CALLED: RandomDimerGraphNetworkGenerator#generateNewNetwork(Parameters p)");
//		
////		generates the actual network of size specified in p
//		Network net = new GraphNetwork(process);
//		System.out.println("check 1");
////		for each gene in the network ...
//		for (int i = 0; i < net.size(); i++){
//			Element dimer = new VertexElement(net, process.getParameters());
//			
//			dimer.setDimer(true);
//			net.addElement(dimer);
//			
//			net.addInteraction(net.get(i), p.HOMODIMER_MATRIX_VALUE, dimer);
//			
//		}
//		System.out.println("check 2");
//		ElementVector dimers = net.getDimers();
//		for (Element gene:net.getGenes()){
////			add first random regulatory input
//			if(uniform.nextFloatFromTo(0.0f, 1.0f) <= p.RNG_P_FIRST_INPUT){ 
//				net.addRegulatoryInteraction(dimers.get(uniform.nextIntFromTo(0, dimers.size()-1)), uniform.nextBoolean(), gene);
//				
////				add second random regulatory input
//				if(uniform.nextFloatFromTo(0.0f, 1.0f) <= p.RNG_P_SECOND_INPUT){ 
//					net.addRegulatoryInteraction(dimers.get(uniform.nextIntFromTo(0, dimers.size()-1)), uniform.nextBoolean(), gene);
//				}
//			}
//		}
//		
//		System.out.println("new random net: \n" + net.getInteractions().toString());
		
		if (p.MINIMAL_OUTPUT) System.out.println("CALLED: RandomGraphNetworkGenerator#generateNewNetwork(Parameters p)");
		
//		generates the actual network of size specified in p
		Network net = new GraphNetwork(process);
		
//		for each gene in the network ...
		for (int i = 0; i < net.size(); i++){
			
//			add first random regulatory input
			if(uniform.nextFloatFromTo(0.0f, 1.0f) <= p.RNG_P_FIRST_INPUT){ 
				net.addRegulatoryInteraction(net.get(uniform.nextIntFromTo(0, net.size()-1)), uniform.nextBoolean(), net.get(i));
				
//				add second random regulatory input
				if(uniform.nextFloatFromTo(0.0f, 1.0f) <= p.RNG_P_SECOND_INPUT){ 
					net.addRegulatoryInteraction(net.get(uniform.nextIntFromTo(0, net.size()-1)), uniform.nextBoolean(), net.get(i));
				}
			}
		}
		
		
		
		for (Element e:net.getGenes()){
			Element dimer = net.addElement();
//			Element dimer = new MatrixElement(n, process.parameters);
//			dimers.add(dimer);
			dimer.setDimer(true);
			net.addInteraction(e, process.getParameters().HOMODIMER_MATRIX_VALUE, dimer);
			

			
//			the regulatory outputs of the new dimer
			Vector<OnewayInteraction> targets = new Vector<OnewayInteraction>();
			Vector<OnewayInteraction> regOuts = e.getRegOutputs();
			for (OnewayInteraction out:regOuts){
				targets.add(out);
			}
			
//			remove original outputs of gene
			while(!regOuts.isEmpty()){
				net.removeRegulatoryInteraction(regOuts.get(0).getElement(), e);
				regOuts.remove(0);
			}
			
//			add new homodimer-interaction between gene and new dimer
			net.addInteraction(e, process.getParameters().HOMODIMER_MATRIX_VALUE, dimer);
			
			for(OnewayInteraction target:targets){
				boolean regvalue = false;
				try {
					if (target.getValue() > 0) regvalue = true;
					else if (target.getValue() < 0) regvalue = false;
					else throw new Exception("not a valid value!");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				add regulatory interaction between dimer and target
				net.addRegulatoryInteraction(dimer, regvalue, target.getElement());
			}
			
			
		}
		
		
//		return the new network ...
		return net;
		
	}
}
