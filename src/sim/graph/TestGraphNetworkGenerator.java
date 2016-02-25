/**
 * 
 */
package sim.graph;

import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Element;
import sim.datatype.MatrixNetwork;
import sim.datatype.Network;
import sim.operation.NetworkGenerator;

/**
 * @author sikosek
 *
 */
public class TestGraphNetworkGenerator implements NetworkGenerator {
	
//	program parameters
	private Parameters p;
	private EvolutionaryProcess process;
	
	/**
	 * Sets the paramters attribute.
	 * @param p parameters
	 * @see sim.Parameters
	 */
	public TestGraphNetworkGenerator(EvolutionaryProcess process){
		this.process = process;
		p = process.getParm();
	}
	/* (non-Javadoc)
	 * @see sim.NetworkGenerator#generateNewNetwork()
	 */
	public Network generateNewNetwork() {
		GraphNetwork net = new GraphNetwork(process, false);
		
		
//		A
		Element a = net.addElement();
//		B
		Element b = net.addElement();
//		AB
		Element ab = net.addElement();
		ab.setDimer(true);
		net.addInteraction(a, p.HETERODIMER_MATRIX_VALUE, ab);
		net.addInteraction(b, p.HETERODIMER_MATRIX_VALUE, ab);
//		C
		Element c = net.addElement();
		net.addRegulatoryInteraction(ab, true, c);
//		AC
		Element ac = net.addElement();
		ac.setDimer(true);
		net.addInteraction(a, p.HETERODIMER_MATRIX_VALUE, ac);
		net.addInteraction(c, p.HETERODIMER_MATRIX_VALUE, ac);
//		D
		Element d = net.addElement();
		net.addRegulatoryInteraction(ac, true, d);
//		DD
		Element dd = net.addElement();
		dd.setDimer(true);
		net.addInteraction(d, p.HOMODIMER_MATRIX_VALUE, dd);
		net.addRegulatoryInteraction(dd, false, c);
//		E
		Element e = net.addElement();
		net.addRegulatoryInteraction(d, true, e);
		net.addRegulatoryInteraction(e, false, d);
		
		
		return net;
	}


}
