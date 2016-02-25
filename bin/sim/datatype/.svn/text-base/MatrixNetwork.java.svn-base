package sim.datatype;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.SparseGraph;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;

import sim.DefaultProcess;
import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.operation.AbstractStatesGenerator;
import sim.operation.AdvancedFitnessFunction;
import sim.operation.DefaultNetworkCollector;
import sim.operation.NetworkDataCollector;

/**
 * This implementation of a <tt>Network</tt> stores elements in objects of
 * type <tt>Element</tt> in a vector (<tt>ElementVector</tt>) and the
 * interactions between the elements in a matrix of type
 * <tt>InteractionMatrix</tt>.
 * 
 * @uml.stereotype uml_id="Standard::ImplementationClass"
 */
public class MatrixNetwork implements Network, Cloneable, Serializable {

	/**
	 * For serialisation.
	 */
	private static final long serialVersionUID = 1L;

	private static int counter = 0;

	protected ElementVector elementVector;

	protected InteractionMatrix interactions;

	protected NetworkDataCollector networkDataCollector;

	protected AbstractStatesGenerator stateGen;
	
	protected TimeStepSeries initialStates;
	
	protected boolean[] notConsidered;
	
	protected MyVector<Integer> consideredIndices;

	protected Parameters p;
	
	protected EvolutionaryProcess process;

	protected int ID = 0;
	
	public Graph dummyGraph;

	
	public MatrixNetwork(){
		this(new DefaultProcess(), true);
		networkDataCollector = null;
	}
	/**
	 * Constructor that sets the network size according to <tt>Parameters</tt>.
	 * 
	 * @param p
	 *            the <tt>Parameters</tt>.
	 */
	public MatrixNetwork(EvolutionaryProcess process) {
		
//		call the other constructor, and let it set the initial size to the default value (see: sim.Parameters)
		this(process, true);
	}

	/**
	 * Either sets network to default size or leaves it empty.
	 * 
	 * @param p
	 *            the <tt>Parameters</tt>.
	 * @param setToDefaultInitialSize
	 *            true: default size; false: empty
	 */
	public MatrixNetwork(EvolutionaryProcess process, boolean setToDefaultInitialSize) {
		
		this.process = process;
		
//		sets the Parameters property of this instance
		this.p = process.getParm();
		
//		set this instance's ID to the current counter value
//		System.out.println("new instance ID: " +MatrixNetwork.counter);
		ID = MatrixNetwork.counter;
		
//		increment the counter, so that each instance has a distinct ID
		MatrixNetwork.counter++;
		
//		set this instance's NetworkDataCollector
		this.networkDataCollector = p.getDefaultNetworkCollector(this);

//		set new ElementVector with the default size
		this.setElementVector(new ElementVector());
		
//		set InteractionMatrix of dimensions NxN corresponding to ElementVector of length N
		this.setInteractions(new InteractionMatrix(this));

//		if this parameter is ' true' ...
		if (setToDefaultInitialSize) {
			
//			get default initial network size from p
			int size = p.INITIAL_NETWORK_SIZE;
			
			for (int i = 0; i < size; i++){
				this.addElement();
			}
		}		
		initialStates = new TimeStepSeries();
		stateGen = process.getStateGen();
		notConsidered = new boolean[0];
		consideredIndices = new MyVector<Integer>();
			
		
		dummyGraph = new SparseGraph();
		
		
	};
	
	public synchronized Graph createDummyGraph(){
//		System.out.println("create dummy!");
		if (dummyGraph.numVertices() > 0)
			dummyGraph.removeAllVertices();
		Vector<Vertex> vertices= new Vector<Vertex>(elementVector.size());
		for(int i = 0; i < interactions.length(); i++){
			Vertex v = new SparseVertex();
			vertices.add(v);
			dummyGraph.addVertex(v);
		}
		for (int col = 0; col < interactions.length(); col++){
			for (int row = 0; row < interactions.length(); row++){
				if (interactions.get(col, row) != 0){
					dummyGraph.addEdge(new UndirectedSparseEdge(vertices.get(col), vertices.get(row)));
				}
			}
		}
		
		getDataCollector().setSubgraphs(getDataCollector().findSubgraphs(dummyGraph).size());
		return dummyGraph;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#addElement()
	 */
	public Element addElement() {
		
//		the new element
		Element elem = new MatrixElement(this, p);

//		call addElement(Element e) (which returns the element's index...)
		addElement(elem);
		
//		this.elementVector.add(elem);
//		int index_e = this.elementVector.indexOf(elem);
//		try {
//			int index_i = this.interactions.add();
//			if (index_e != index_i)
//				throw new MatrixNetworkException(
//						"sim.MatrixNetwork.addElement(Element elem): a mismatch occured between the indces of the new elements in the ElementVector(-->returned) and in the InteractionMatrix!");
//		} catch (MatrixNetworkException e) {
//			e.printStackTrace();
//		}
		
		
		return elem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#addElement(sim.Element)
	 */
	public int addElement(Element elem) {
		
//		add element in ElementVector
		this.elementVector.add(elem);
		
//		get the index of the new element
		int index_e = this.elementVector.indexOf(elem);
		
		try {
			
//			add new element to InteractionMatrix
			int index_i = this.interactions.add();
			for(Element element:elementVector){
				element.updateConnections();
			}
//			TODO (for debgging) throw exception if the indices of vector and  matrix don't match
			if (index_e != index_i){
				throw new MatrixNetworkException(
						"sim.MatrixNetwork.addElement(Element elem): a mismatch occured between the indces of the new elements in the ElementVector(-->returned) and in the InteractionMatrix!");
				
			}
		} catch (MatrixNetworkException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
//		return the index of the new element
		return index_e;
	}

	/*
	 * (non-Javadoc)
	 * TODO is there an error in here? if target is dimer, bytevalue = 2!!! source should be dimer!!
	 * @see sim.Network#addInteraction(int, boolean, int)
	 */
//	public void addInteraction(int source, boolean value, int target) {
//		
////		will hold the matrix cell value for the interaction
//		byte bytevalue;
//		
////		if the source is a dimer ...
//		if (this.get(source).isDimer())
//			
////			dimers have value '2' in the interaction matrix
//			bytevalue = 2; 
//		
////		monomers have value '1'
//		else	
//			bytevalue = 1;
//		
////		repressors have negative values
//		if (!value)
//			bytevalue *= -1;
//		
////		set interaction in the matrix
//		this.getInteractions().set(target, source, bytevalue);
//	}
	public void addRegulatoryInteraction(Element source, boolean value, Element target) {
		
		try {
			if(target.isDimer())
				throw new MatrixNetworkException("tried to add regulatory interaction to a dimer!!!");
//		will hold the matrix cell value for the interaction
			byte bytevalue;
			
//		if the source is a dimer ...
			if (source.isDimer())
				
//			dimers have value '2' in the interaction matrix
				bytevalue = 2; 
			
//		monomers have value '1'
			else	
				bytevalue = 1;
			
//		repressors have negative values
			if (!value)
				bytevalue *= -1;
			
//			if (source.getDimOutputs().size() != 0){
//				throw new MatrixNetworkException("element already forms a dimer!");
//			}
			
//		set interaction in the matrix
			this.getInteractions().set(this.getIndexOf(target), this.getIndexOf(source), bytevalue);
			source.updateConnections();
			target.updateConnections();
		} catch (MatrixNetworkException e) {

			e.printStackTrace();
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#addInteraction(int, byte, int)
	 */
//	public void addInteraction(int source, byte value, int target) {
//		
////		set interaction matrix value directly 
//		this.getInteractions().set(target, source, value);
//	}
	
public void addInteraction(Element source, byte value, Element target) {
//		if (value == p.HETERODIMER_MATRIX_VALUE || value == p.HOMODIMER_MATRIX_VALUE){
//			if (source.getRegOutputs().size() != 0){
//				throw new MatrixNetworkException("")
//			}
//		}
//		set interaction matrix value directly 
		this.getInteractions().set(this.getIndexOf(target), this.getIndexOf(source), value);
		source.updateConnections();
		target.updateConnections();
	}
	/*
	 * (non-Javadoc)
	 * Creates an exact (deep) copy of this network. All attributes of non-basic types must be cloned as well.
	 * Overrides clone() of Object class.
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException {
		
//		the 'shallow' clone (all attributes uncloned)
		MatrixNetwork clone = (MatrixNetwork) super.clone();
		
//		clone of NetworkDataCollector
		clone.networkDataCollector = (NetworkDataCollector) networkDataCollector.clone();
		
//		set the network the cloned NetworkDataCollector belongs to the cloned network
		clone.networkDataCollector.setNetwork(clone);
//		clone ElementVector
//		clone.elementVector = (ElementVector) elementVector.clone();
		
		clone.elementVector = new ElementVector();
		
//		clone InteractionMatrix
		clone.interactions = (InteractionMatrix) interactions.clone();
//		set cloned InteractionMatrix's ElementVector to cloned ElementVector
		clone.interactions.setElements(clone.elementVector);

		for(int i = 0; i < this.elementVector.size(); i++){
			MatrixElement elem = (MatrixElement)elementVector.get(i).clone();
			elem.setNet(clone);
			elem.setInteractionMatrix(clone.interactions);
			elem.setVector(clone.elementVector);
			clone.elementVector.addElement(elem);
			
			
			
//			System.out.println("\tELEM");
//			System.out.println("\tIM:" +clone.interactions+ " - " +clone.interactions.length());
//			System.out.println("\tEV:" +clone.elementVector+ " - " +clone.elementVector.size());
		}
		
		for(int i = 0; i < clone.elementVector.size(); i++){
			clone.elementVector.get(i).updateConnections();
		}
		clone.dummyGraph = new SparseGraph();
		
//		make sure it gets the latest stats
		clone.networkDataCollector.updateStats();

//		System.out.println("ORIGINAL");
//		System.out.println("IM:" +this.interactions+ " - " +this.interactions.length());
//		System.out.println("EV:" +this.elementVector+ " - " +this.elementVector.size());
//		
//		System.out.println("CLONE");
//		System.out.println("IM:" +clone.interactions+ " - " +clone.interactions.length());
//		System.out.println("EV:" +clone.elementVector+ " - " +clone.elementVector.size());
		
//		give cloned network an ID of its own
//		System.out.println("new clone ID: " +MatrixNetwork.counter);
		clone.ID = MatrixNetwork.counter;
		MatrixNetwork.counter++;
		
		
		clone.p = (Parameters) p.clone();
//		clone.stateGen = clone.p.getDefaultStatesGenerator();
		clone.notConsidered = (boolean[]) notConsidered.clone();
//		System.out.println("\n! "+this.printNotConsidered() +"");
//		System.out.println("! "+clone.printNotConsidered() +"\n");
		clone.consideredIndices = (MyVector<Integer>) consideredIndices.clone();
		clone.initialStates = (TimeStepSeries) initialStates.clone();
		

		
//		return the entirely cloned network
		return clone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#formsDimer(int)
	 */
	public boolean formsDimer(int gene) {
		
//		gene does not form dimer if there are no dimerising outputs
		if (this.getDimerisingOutputsOf(this.get(gene)).size() == 0)
			return false;
		else
			return true;
	}

	/**
	 * @param index
	 * @return
	 */
	public Element get(int index) {
		return this.getElementVector().get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getDataCollector()
	 */
	public NetworkDataCollector getDataCollector() {
		return networkDataCollector;
	}

	/**
	 * Gets monomer(s) for the specified (homo- or hetero-)dimeric regulator
	 * (transcription factor) in form of the <tt>byte</tt> value and the
	 * <tt>int</tt> index of the monomer of the dimerising interaction.
	 * 
	 * @param dimer
	 *            the index of the dimer for which the dimerising inputs are
	 *            returned.
	 * @return a <tt>{@link #sim.Vector}</tt> of <tt>OnewayInteraction</tt>
	 *         objects.
	 */
//	public MyVector<OnewayInteraction> getDimerisingInputsOf(int dimer) {
//		
////		vector that will hold the dimerising inputs
//		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(2, 1);
//		
//		try {
//			
////			TODO (debugging) if this element is not a dimer, throw exception
//			if (!this.get(dimer).isDimer())
//				throw new MatrixNetworkException(
//						"sim.MatrixNetwork.getDimerisingInputsOf(int dimer) does not work for genes (transcription factor monomers), only for dimers");
//			
////			go through all inputs for this element ...
//			for (int input = 0; input < this.getInteractions().length(); input++) {
//				
////				get input from InteractionMatrix
//				byte value = this.getInteractions().get(dimer, input);
//				
////				if it is a homo- or heterodimer ... add it to v
//				if (value == p.HETERODIMER_MATRIX_VALUE
//						|| value == p.HOMODIMER_MATRIX_VALUE)
//					v.add(new OnewayInteraction(value, input, this.get(input)));
//			}
//			
////			TODO (for debugging) number of dimerising inputs not supposed to exceed two
//			if (v.size() > 2)
//				throw new MatrixNetworkException(
//						"More than two dimerising inputs found!! There should be one input for a homodimer and two inputs for a heterodimer.");
//			
//		} catch (MatrixNetworkException e) {
////			TODO if this is uncommented, lots of errors will be produced, because method is often called for dimers.
////			e.printStackTrace();
//		}
//		
////		return dimerising inputs
//		return v;
//	}
	
	public MyVector<OnewayInteraction> getDimerisingInputsOf(Element dimer) {
		return dimer.getDimInputs();
//		
////		vector that will hold the dimerising inputs
//		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(2, 1);
//		
//		try {
//			
////			TODO (debugging) if this element is not a dimer, throw exception
//			if (!dimer.isDimer())
//				throw new MatrixNetworkException(
//						"sim.MatrixNetwork.getDimerisingInputsOf(int dimer) does not work for genes (transcription factor monomers), only for dimers");
//			
////			go through all inputs for this element ...
//			for (int input = 0; input < this.getInteractions().length(); input++) {
//				
////				get input from InteractionMatrix
//				byte value = this.getInteractions().get(this.getIndexOf(dimer), input);
//				
////				if it is a homo- or heterodimer ... add it to v
//				if (value == p.HETERODIMER_MATRIX_VALUE
//						|| value == p.HOMODIMER_MATRIX_VALUE)
//					v.add(new OnewayInteraction(value, input, this.get(input)));
//			}
//			
////			TODO (for debugging) number of dimerising inputs not supposed to exceed two
//			if (v.size() > 2)
//				throw new MatrixNetworkException(
//						"More than two dimerising inputs found!! There should be one input for a homodimer and two inputs for a heterodimer.");
//			
//		} catch (MatrixNetworkException e) {
////			TODO if this is uncommented, lots of errors will be produced, because method is often called for dimers.
////			e.printStackTrace();
//		}
//		
////		return dimerising inputs
//		return v;
	}

	/**
	 * Gets all the dimerising outputs of the specified gene (i.e. all the
	 * dimers it forms) in form of the <tt>byte</tt> value and the
	 * <tt>int</tt> index of the dimer of the dimerising interaction.
	 * 
	 * @param gene
	 *            the index of the gene for which the dimerising outputs are
	 *            returned.
	 * @return a <tt>{@link #sim.Vector}</tt> of <tt>OnewayInteraction</tt>
	 *         objects.
	 */
//	public MyVector<OnewayInteraction> getDimerisingOutputsOf(int gene) {
//		
////		vector that will hold the dimerising outputs
//		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(2, 1);
//		
////		go through all outputs ...
//		for (int output = 0; output < this.getInteractions().length(); output++) {
//			
////			get output from matrix
//			byte value = this.getInteractions().get(output, gene);
//			
////			add output to v if hetero- or homodimer
//			if (value == p.HETERODIMER_MATRIX_VALUE
//					|| value == p.HOMODIMER_MATRIX_VALUE)
//				v.add(new OnewayInteraction(value, output, this.get(output)));
//		}
//		
////		return dimerising outputs
//		return v;
//	}
	public MyVector<OnewayInteraction> getDimerisingOutputsOf(Element gene) {
		return gene.getDimOutputs();
//		
////		vector that will hold the dimerising outputs
//		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(2, 1);
//		
////		go through all outputs ...
//		for (int output = 0; output < this.getInteractions().length(); output++) {
//			
////			get output from matrix
//			byte value = this.getInteractions().get(output, this.getIndexOf(gene));
//			
////			add output to v if hetero- or homodimer
//			if (value == p.HETERODIMER_MATRIX_VALUE
//					|| value == p.HOMODIMER_MATRIX_VALUE)
//				v.add(new OnewayInteraction(value, output, this.get(output)));
//		}
//		
////		return dimerising outputs
//		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getDimers()
	 */
	public MyVector<Integer> getDimerIndices() {
		MyVector<Integer> dimers = new MyVector<Integer>();
		for (int i = 0; i < getElementVector().size(); i++) {
			if (this.get(i).isDimer())
				dimers.add(new Integer(i));
		}
		return dimers;
	}

	/**
	 * Gets all dimers.
	 * @return ElementVector of dimers.
	 */
	public ElementVector getDimers() {
		ElementVector dimers = new ElementVector();
		for (int i = 0; i < getElementVector().size(); i++) {
			if (this.get(i).isDimer())
				dimers.add(this.get(i));
		}
		return dimers;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getElements()
	 */
	public MyVector<Integer> getElementIndices() {
		MyVector<Integer> elements = new MyVector<Integer>();
		for (int i = 0; i < getElementVector().size(); i++) {
			elements.add(new Integer(i));
		}
		return elements;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getElementVector()
	 */
	public ElementVector getElementVector() {
		return elementVector;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getGenes()
	 */
	public MyVector<Integer> getGeneIndices() {
		MyVector<Integer> genes = new MyVector<Integer>();
		for (int i = 0; i < getElementVector().size(); i++) {
			if (!this.get(i).isDimer())
				genes.add(new Integer(i));
		}
		return genes;
	}
	
	/**
	 * Gets all genes.
	 * @return ElementVector of genes.
	 */
	public ElementVector getGenes() {
		ElementVector genes = new ElementVector();
		for (int i = 0; i < getElementVector().size(); i++) {
			if (!this.get(i).isDimer())
				genes.add(this.get(i));
		}
		return genes;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getID()
	 */
	public int getID() {
		return ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getIndexOf(sim.Element)
	 */
	public int getIndexOf(Element e) {
		return this.getElementVector().indexOf(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getInteractions()
	 */
	public InteractionMatrix getInteractions() {
		return interactions;
	}

	/*
	 * TODO Needs to be improved: not all isolated nodes can be found, e.g. if
	 * the isolated gene forms two (or more) isolated dimers.
	 * 
	 * @see sim.Network#getIsolated()
	 */
	public ElementVector getIsolated() {
		
//		will hold isolated elements
		ElementVector isolated = new ElementVector();

//		for all elements in the network ...
		for (Element e : this.getElementVector()) {
			
//			if it is not a dimer ...
			if (!e.isDimer()) {
				
//				if it has no regulatory in- or outputs, and  no dimerising outputs ... add to 'isolated'
				if (this.getRegulatoryInputsOf(e).size() == 0
						&& this.getRegulatoryOutputsOf(e)
								.size() == 0
						&& this.getDimerisingOutputsOf(e)
								.size() == 0) {
					isolated.add(e);
				}
				
//				self-regulating but otherwise isolated nodes are isolated
				if (this.getRegulatoryInputsOf(e).size() == 1
						&& this.getRegulatoryOutputsOf(e).size() == 1
						&& this.getDimerisingOutputsOf(e).size() == 0
						&& this.getRegulatoryInputsOf(e).get(0).getElement() == e) {
					isolated.add(e);
				}
				
//				if it is a dimer ...
			} /*else if (e.isDimer()) {
				
//				if dimer has no regulatory outputs ...
				if (this.getRegulatoryOutputsOf(e).size() == 0) {
					
//					add to 'isolated'
					isolated.add(e);
					
//					get dimerising inputs of dimer
					MyVector<OnewayInteraction> monomers = this
							.getDimerisingInputsOf(e);
					
//					for each input...
					for (OnewayInteraction input : monomers) {
//						a gene forming the isolated dimer that has no other interactions is isolated as well.
						if (this.getRegulatoryInputsOf(input.getElement())
								.size() == 0
								&& this.getRegulatoryOutputsOf(
										input.getElement()).size() == 0
								&& this.getDimerisingOutputsOf(
										input.getElement()).size() == 1) {
							isolated.add(e);
						}
					}
				}
			}*/
		}
		return isolated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getRegulators()
	 */
	public MyVector<Integer> getRegulatorIndices() {
		MyVector<Integer> regulators = new MyVector<Integer>();
		for (int i = 0; i < getElementVector().size(); i++) {
			if (this.getRegulatoryOutputsOf(this.get(i)).size() != 0)
				regulators.add(new Integer(i));
		}
		return regulators;
	}
	public ElementVector getRegulators() {
		ElementVector regulators = new ElementVector();
		for (int i = 0; i < this.size(); i++) {
			if (this.getRegulatoryOutputsOf(this.get(i)).size() != 0){
				if (p.ONLY_DIMERS_REGULATE){
					if (get(i).isDimer())
						regulators.add(this.get(i));
				}
				else{
					regulators.add(this.get(i));
				}
			}
		}
		return regulators;
	}
	
	public ElementVector getNonIsolatedGenes(){
		ElementVector genes = new ElementVector();
		ElementVector isolated = new ElementVector();
		isolated = getIsolated();
		genes.removeAll(isolated);
		return genes;
	}

	/**
	 * Gets all regulatory inputs for the specified gene (transcription factor
	 * monomer) in form of the <tt>byte</tt> value and the <tt>int</tt>
	 * index of the source of the interaction.
	 * 
	 * @param gene
	 *            the index of the gene that receives the inputs returned.
	 * @return a <tt>{@link #sim.Vector}</tt> of <tt>OnewayInteraction</tt>
	 *         objects.
	 */
//	public MyVector<OnewayInteraction> getRegulatoryInputsOf(int gene) {
//		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(5, 5);
//		try {
//			if (this.get(gene).isDimer())
//				throw new MatrixNetworkException(
//						"sim.MatrixNetwork.getRegulatoryInputsOf(int gene) does not work for dimers since they don't have regulatory inputs!");
//
//			for (int input = 0; input < this.getInteractions().length(); input++) {
//				byte value = this.getInteractions().get(gene, input);
//				if (value != 0 && value != p.HETERODIMER_MATRIX_VALUE
//						&& value != p.HOMODIMER_MATRIX_VALUE)
//					v.add(new OnewayInteraction(value, input, this.get(input)));
//			}
//		} catch (MatrixNetworkException e) {
//			e.printStackTrace();
//		}
//		return v;
//	}
	public MyVector<OnewayInteraction> getRegulatoryInputsOf(Element gene){
		return gene.getRegInputs();
//		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(5, 5);
//		try {
//			if (gene.isDimer())
//				throw new MatrixNetworkException(
//						"sim.MatrixNetwork.getRegulatoryInputsOf(int gene) does not work for dimers since they don't have regulatory inputs!");
//
//			for (int input = 0; input < this.getInteractions().length(); input++) {
//				byte value = this.getInteractions().get(getIndexOf(gene), input);
//				if (value != 0 && value != p.HETERODIMER_MATRIX_VALUE
//						&& value != p.HOMODIMER_MATRIX_VALUE)
//					v.add(new OnewayInteraction(value, input, this.get(input)));
//			}
//		} catch (MatrixNetworkException e) {
//			e.printStackTrace();
//		}
//		return v;
	}
	/**
	 * Gets all regulatory outputs for the specified regulator (mono- or dimeric
	 * transcription factor) in form of the <tt>byte</tt> value and the
	 * <tt>int</tt> index of the target of the interaction.
	 * 
	 * @param regulator
	 *            the index of the regulator for which the outputs are returned.
	 * @return a <tt>{@link #sim.Vector}</tt> of <tt>OnewayInteraction</tt>
	 *         objects.
	 */
//	public MyVector<OnewayInteraction> getRegulatoryOutputsOf(int regulator) {
//
//		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(5, 5);
//
//		for (int output = 0; output < this.getInteractions().length(); output++) {
//
//			byte value = this.getInteractions().get(output, regulator);
//
//			// only get those interactions that are not '0' (= no interaction)
//			// and do not form a dimer.
//			if (value != 0 && value != p.HETERODIMER_MATRIX_VALUE
//					&& value != p.HOMODIMER_MATRIX_VALUE)
//				v.add(new OnewayInteraction(value, output, this.get(output)));
//		}
//		return v;
//	}
	public MyVector<OnewayInteraction> getRegulatoryOutputsOf(Element regulator) {
		return regulator.getRegOutputs();
//		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(5, 5);
//
//		for (int output = 0; output < this.getInteractions().length(); output++) {
//
//			byte value = this.getInteractions().get(output, this.getIndexOf(regulator));
//
//			// only get those interactions that are not '0' (= no interaction)
//			// and do not form a dimer.
//			if (value != 0 && value != p.HETERODIMER_MATRIX_VALUE
//					&& value != p.HOMODIMER_MATRIX_VALUE)
//				v.add(new OnewayInteraction(value, output, this.get(output)));
//		}
//		return v;
	}

	public ElementVector getSinks() {
		ElementVector ev = new ElementVector();
		for (Element e : this.getElementVector()) {
//			if (p.SINKS_MODEL_ON){
//				if (e.isSink())
//					ev.add(e);
//			}else{
			if (p.ONLY_DIMERS_REGULATE){
				if (e.isDimer()) {
			
					if (this.getRegulatoryOutputsOf(e)
							.size() == 0){
						ev.add(e);
					}
				}
			}else{
				if (!e.isDimer()) {
					if ((this.getRegulatoryInputsOf(e).size() > 0 || this
						.getDimerisingInputsOf(e).size() > 0)
						&& this.getRegulatoryOutputsOf(e)
								.size() == 0
						&& this.getDimerisingOutputsOf(e)
								.size() == 0) {
						ev.add(e);
					}
				}else{
					if (this.getRegulatoryOutputsOf(e)
							.size() == 0){
						ev.add(e);
					}
				}
			}
	
//			}
		}
		return ev;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#getSources()
	 */
	public ElementVector getSources() {
		ElementVector ev = new ElementVector();

		for (Element e : this.getElementVector()) {
			if (!e.isDimer() && !e.isSink()) {
				ev.add(e);
//				if (getDimerisingOutputsOf(e).size() > 0
//						|| getRegulatoryOutputsOf(e).size() > 0) {
//					ev.add(e);
//				}
			}
		}
		return ev;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#isDimer(int)
	 */
	public boolean isDimer(int element) {
		if (this.getElementVector().get(element).isDimer())
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#isHeterodimer(int)
	 */
	public boolean isHeterodimer(int element) {
		if (this.getElementVector().get(element).isDimer()
				&& this.getDimerisingInputsOf(this.get(element)).size() == 2)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#isHomodimer(int)
	 */
	public boolean isHomodimer(int element) {
		if (this.getElementVector().get(element).isDimer()
				&& this.getDimerisingInputsOf(this.get(element)).size() == 1)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#removeElement(sim.Element)
	 */
	public void removeElement(Element e) {
		interactions.remove(this.getIndexOf(e));
		elementVector.removeElement(e);
		for(Element elem:elementVector){
			elem.updateConnections();
		}
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see sim.Network#removeElement(int)
//	 */
//	public void removeElement(int index) {
//		interactions.remove(index);
//		elementVector.removeElementAt(index);
//		for(Element elem:elementVector){
//			elem.updateConnections();
//		}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#removeInteraction(int, int)
	 */
	public void removeRegulatoryInteraction(Element target, Element source) {
		byte zero = 0;
//		System.out.println("remove:"+getIndexOf(target)+","+getIndexOf(source));
		
		this.interactions.set(getIndexOf(target), getIndexOf(source), zero);
		source.updateConnections();
		target.updateConnections();
	}

	/**
	 * @param networkDataCollector
	 */
	public void setDataCollector(NetworkDataCollector networkDataCollector) {
		this.networkDataCollector = networkDataCollector;
	}

	/**
	 * @param elements
	 */
	public void setElementVector(ElementVector elements) {
		this.elementVector = elements;
	}

	/**
	 * @param interactions
	 */
	public void setInteractions(InteractionMatrix interactions) {
		this.interactions = interactions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#setToState(boolean[])
	 */
	public void setToState(boolean[] state) {
		for (int i = 0; i < this.size(); i++)
			this.getElementVector().get(i).setOn(state[i]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Network#size()
	 */
	public int size() {
		return this.getElementVector().size();
	}
	
	public String printNotConsidered(){
		String str = "";
		for (boolean b:this.notConsidered) {
			if (b)
				str+='X';
			else
				str+='-';
		}
		return str;
	}
	
	public String printComposition(){
		String str = "";
		ElementVector sources = this.getSources();
		ElementVector sinks = this.getSinks();
		ElementVector isolated = this.getIsolated();
		ElementVector dimers = this.getDimers();
		
		for (Element e:this.elementVector) {
			if (sources.contains(e)) str += 'o';
			else if (sinks.contains(e)) str += 'k';
			else if (isolated.contains(e)){
				if (dimers.contains(e))
					str += 'I';
				else
					str += 'i';
			}else if (dimers.contains(e)) str += 'D';
			else str += 'c';
		}		
		return str;
	}

	/* (non-Javadoc)
	 * @see sim.datatype.Network#printInputNumbers()
	 */
	public String printInputNumbers() {
		String str = "";
		
		for (Element e:this.elementVector) {
			int n;
			if(e.isDimer()){
				n = getDimerisingInputsOf(e).size();
				switch (n){
					case 1: str += '\''; break;
					case 2: str += '\"'; break;
					default: str += '!';
				}
			}
			else{
				n = getRegulatoryInputsOf(e).size();
				if (n < 10) 
					if (n == 0)
						str += '-';
					else
						str += n;

				else 
					str += '^';
			}
		}
		return str;
	}
	public String printRegOutputNumbers() {
		String str = "";
		
		for (Element e:this.elementVector) {
			int n;
			n = getRegulatoryOutputsOf(e).size();
			if (n < 10)
				if (n == 0)
					str += '-';
				else
					str += n;

			else 
				str += '^';
		}
		
		return str;
	}
	public String printDimOutputNumbers() {
		String str = "";
		
		for (Element e:this.elementVector) {
			int n;
			n = getDimerisingOutputsOf(e).size();
			if (n < 10) {
				if (n == 0)
					str += '-';
				else
					str += n;
			}
			else 
				str += '^';
		}
		
		return str;
	}

	public Parameters getParameters() {
		return p;
	}

	public void setParameters(Parameters p) {
		this.p = p;
	}

	public int getNumberOfStates(){
		return initialStates.size();
	}
	
	public boolean[] getState(int index){
		return initialStates.get(index);
	}
	
	public void generateInitialStates() {
		stateGen.generateStates(this);
		initialStates = stateGen.getAllStates();
	}
	public TimeStepSeries getInitialStates() {
		return initialStates;
	}
	public void setInitialStates(TimeStepSeries initialStates) {
		this.initialStates = initialStates;
	}

	public AbstractStatesGenerator getStateGen() {
		return stateGen;
	}

	public void setStateGen(AbstractStatesGenerator stateGen) {
		this.stateGen = stateGen;
	}

	public boolean[] getNotConsidered() {
		return notConsidered;
	}

	public void setNotConsidered(boolean[] notConsidered) {
		this.notConsidered = notConsidered;
	}

	public MyVector<Integer> getConsideredIndices() {
		return consideredIndices;
	}

	public void setConsideredIndices(MyVector<Integer> consideredIndices) {
		this.consideredIndices = consideredIndices;
	}

	public EvolutionaryProcess getProcess() {
		return process;
	}

	public void setProcess(EvolutionaryProcess process) {
		this.process = process;
	}

	
}
