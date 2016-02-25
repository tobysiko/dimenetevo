/**
 * 
 */
package sim;

import sim.datatype.MatrixNetwork;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.gui.Outputter;
import sim.operation.DefaultMutator;
import sim.operation.DefaultSimulator;
import sim.operation.RandomNetworkGenerator;
import sim.operation.SimpleSelector;
import sim.operation.SourcesSimulator;
import sim.operation.TestNetworkGenerator;

/**
 * @author sikosek
 *
 */
public class Tester {
	static EvolutionaryProcess process = new DefaultProcess();
	static Parameters p = new Parameters(process);
	static RandomNetworkGenerator rng = new RandomNetworkGenerator(process);
	static TestNetworkGenerator tng = new TestNetworkGenerator(process);
	static DefaultSimulator sim = new DefaultSimulator(process);
	static Outputter out = new Outputter();
	static DefaultMutator mut = new DefaultMutator(process);
	static SimpleSelector sel = new SimpleSelector(process);
	static NetworkVector pop = new NetworkVector();
	static SourcesSimulator sousim= new SourcesSimulator(process);
	static Network network = new MatrixNetwork(process);
	
	public static void main(String[] args) {
		network = tng.generateNewNetwork();
		pop.add(network);
		process = new DefaultProcess(pop);
		process.getParameters().GENERATIONS = 1;
		process.start();
		
		
	}

}
