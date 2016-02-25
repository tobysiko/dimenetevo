/**
 * 
 */
package sim;

import sim.datatype.Network;
import sim.datatype.NetworkVector;

/**
 * @author sikosek
 *
 */
public class MemoryTesterMain {

	public static void main(String... args){
		Parameters parm = new Parameters(new DefaultProcess());
		NetworkVector startPop = new NetworkVector();
		Network net;
		for (int j = 0; j < parm.POPULATION_SIZE; j++){
			net = parm.getDefaultGenerator().generateNewNetwork();
			startPop.add(net);
		}
		DefaultProcess process;
		process = new DefaultProcess(parm, new NetworkVector());
//		give each process a clone of startPop population
		process.setPopulation((NetworkVector)startPop.clone());
		process.getPopulation().setProcess(process);
		
		process.startProcess();
		
	}
}
