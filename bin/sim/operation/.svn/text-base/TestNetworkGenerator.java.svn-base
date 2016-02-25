/**
 * 
 */
package sim.operation;

import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Element;
import sim.datatype.MatrixElement;
import sim.datatype.MatrixNetwork;
import sim.datatype.Network;


/**
 * Generates ONE particular network. E.g. as specified in 2nd project report.
 * @author sikosek
 *
 */
public class TestNetworkGenerator implements NetworkGenerator {
	
//	program parameters
	private Parameters p;
	private EvolutionaryProcess process;
	
	/**
	 * Sets the paramters attribute.
	 * @param p parameters
	 * @see sim.Parameters
	 */
	public TestNetworkGenerator(EvolutionaryProcess process){
		this.process = process;
		p = process.getParm();
	}
	/* (non-Javadoc)
	 * @see sim.NetworkGenerator#generateNewNetwork()
	 */
	public Network generateNewNetwork() {
		MatrixNetwork net = new MatrixNetwork(process, false);
		
		
//		A
		Element a = new MatrixElement(net, p);
		net.addElement(a);
//		B
		Element b = new MatrixElement(net, p);
		net.addElement(b);
//		AB
		Element ab = new MatrixElement(net, p);
		net.addElement(ab);
		ab.setDimer(true);
		net.addInteraction(a, p.HETERODIMER_MATRIX_VALUE, ab);
		net.addInteraction(b, p.HETERODIMER_MATRIX_VALUE, ab);
//		C
		Element c = new MatrixElement(net, p);
		net.addElement(c);
		net.addRegulatoryInteraction(ab, true, c);
//		AC
		Element ac = new MatrixElement(net, p);
		net.addElement(ac);
		ac.setDimer(true);
		net.addInteraction(a, p.HETERODIMER_MATRIX_VALUE, ac);
		net.addInteraction(c, p.HETERODIMER_MATRIX_VALUE, ac);
//		D
		Element d = new MatrixElement(net, p);
		net.addElement(d);
		net.addRegulatoryInteraction(ac, true, d);
//		DD
		Element dd = new MatrixElement(net, p);
		net.addElement(dd);
		dd.setDimer(true);
		net.addInteraction(d, p.HOMODIMER_MATRIX_VALUE, dd);
		net.addRegulatoryInteraction(dd, false, c);
//		E
		Element e = new MatrixElement(net, p);
		net.addElement(e);
		net.addRegulatoryInteraction(d, true, e);
		net.addRegulatoryInteraction(e, false, d);
		
		
		return net;
	}

}
