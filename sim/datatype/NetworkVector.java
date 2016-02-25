/**
 * 
 */
package sim.datatype;

import java.io.Serializable;
import java.util.Collection;


/** 
 * @author sikosek
 */
public class NetworkVector extends MyVector<Network> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NetworkVector() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 */
	public NetworkVector(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param c
	 */
	public NetworkVector(Collection<Network> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initialCapacity
	 * @param capacityIncrement
	 */
	public NetworkVector(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property   name="network"
	 * @uml.associationEnd   multiplicity="(0 -1)" aggregation="composite" inverse="networkVector:sim.datatype.Network"
	 * @uml.association   name="has several"
	 */
	private Collection<Network> network;

	/** 
	 * Getter of the property <tt>network</tt>
	 * @return  Returns the network.
	 * @uml.property  name="network"
	 */
	public Collection<Network> getNetwork() {
		return network;
	}

	/** 
	 * Setter of the property <tt>network</tt>
	 * @param network  The network to set.
	 * @uml.property  name="network"
	 */
	public void setNetwork(Collection<Network> network) {
		this.network = network;
	}
//	public Network get(int index){
//		return this.get(index);
//	}
//	public boolean add(Network net){
//		this.add(net);
//		return true;
//	}

	/**
	 * @uml.property  name="evolutionaryProcess"
	 * @uml.associationEnd  multiplicity="(1 1)" aggregation="composite" inverse="networkVector:sim.EvolutionaryProcess"
	 * @uml.association  name="has a"
	 */
//	private EvolutionaryProcess evolutionaryProcess = new sim.DefaultProcess();

	/**
	 * Getter of the property <tt>evolutionaryProcess</tt>
	 * @return  Returns the evolutionaryProcess.
	 * @uml.property  name="evolutionaryProcess"
	 */
//	public EvolutionaryProcess getEvolutionaryProcess() {
//		return evolutionaryProcess;
//	}

//	/**
//	 * Setter of the property <tt>evolutionaryProcess</tt>
//	 * @param evolutionaryProcess  The evolutionaryProcess to set.
//	 * @uml.property  name="evolutionaryProcess"
//	 */
//	public void setEvolutionaryProcess(EvolutionaryProcess evolutionaryProcess) {
//		this.evolutionaryProcess = evolutionaryProcess;
//	}
}
