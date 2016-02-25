/**
 * 
 */
package sim.operation;

import java.util.Date;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Element;
import sim.datatype.Network;
import sim.graph.GraphNetwork;

/**
 * @author sikosek
 *
 */
public class RandomSinkNetworkGenerator implements NetworkGenerator {
	
//	random number generator
	private RandomEngine generator = new MersenneTwister(new Date());
	
//	uniform distribution
	private Uniform uniform = new Uniform(generator);
	
//	program parameters
	private Parameters p;
	private EvolutionaryProcess process;
	
	/**
	 * Sets the paramters attribute.
	 * @param p parameters
	 * @see sim.Parameters
	 */
	public RandomSinkNetworkGenerator(EvolutionaryProcess process){
		this.process = process;
		p = process.getParm();
	}

	/* (non-Javadoc)
	 * @see sim.operation.NetworkGenerator#generateNewNetwork()
	 */
	public Network generateNewNetwork() {
		Network net = new GraphNetwork(process, false);
		Element tf = net.addElement();
		tf.setSink(false);
		Element tf2 = net.addElement();
		tf2.setSink(false);
//		generate sinks
		for (int i = 0; i < p.INITIAL_SINK_NUMBER; i++){
			Element sink = net.addElement();
			sink.setSink(true);
//			Element tf = net.addElement();
//			tf.setSink(false);
//			net.addRegulatoryInteraction(tf, true, sink);
			net.addRegulatoryInteraction(tf, true, sink);
			net.addRegulatoryInteraction(tf2, false, sink);
		}
//		Element tf = net.addElement();
//		tf.setSink(false);
//		
//		net.addRegulatoryInteraction(tf, true, net.get(0));
		return net;
	}

}
