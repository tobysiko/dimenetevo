/**
 * 
 */
package sim.datatype;

import java.io.Serializable;

import sim.Parameters;
import sim.operation.function.BooleanFunction;
import sim.operation.function.SimpleDimerRule;

/**
 * @author sikosek
 * @uml.stereotype uml_id="Standard::Type"
 */
public abstract class AbstractElement implements Element, Serializable, Cloneable {

	protected boolean on;

	protected boolean dimer;
	
	protected boolean sink;

	protected int dimerTimeRemaining;
	
	protected ElementVector vector;
	
	protected Network net;

	protected BooleanFunction function;

	protected InteractionMatrix interactionMatrix;

	protected Parameters p;
	
	protected MyVector<OnewayInteraction> regInputs;
	
	protected MyVector<OnewayInteraction> regOutputs;
	
	protected MyVector<OnewayInteraction> dimInputs;
	
	protected MyVector<OnewayInteraction> dimOutputs;
	
	/**
	 * Initialise properties.
	 */
	public AbstractElement(Network net, Parameters p) {
		this.net = net;
		this.vector = net.getElementVector();
		this.interactionMatrix = net.getInteractions();
		this.dimer = false;
		dimerTimeRemaining = 0;
		this.on = false;
		this.p = net.getParameters();
		this.function = p.getDefaultGeneRule();
		this.function.setParameters(p);
		regInputs = new MyVector<OnewayInteraction>();
		regOutputs = new MyVector<OnewayInteraction>();
		dimInputs = new MyVector<OnewayInteraction>();
		dimOutputs = new MyVector<OnewayInteraction>();
	}
//	for testing only!!!
	public AbstractElement(){
		this.net = null;
		this.vector = null;
		this.interactionMatrix = null;
		this.dimer = false;
		dimerTimeRemaining = 0;
		this.on = false;
		this.p = null;
		this.function = new Parameters().getDefaultGeneRule();
//		this.function.setParameters(p);
		regInputs = new MyVector<OnewayInteraction>();
		regOutputs = new MyVector<OnewayInteraction>();
		dimInputs = new MyVector<OnewayInteraction>();
		dimOutputs = new MyVector<OnewayInteraction>();
	}
	public void updateConnections(){
		dimInputs = getDimerisingInputs();
		regInputs = getRegulatoryInputs();
		dimOutputs = getDimerisingOutputs();
		regOutputs = getRegulatoryOutputs();
	}
	
	protected MyVector<OnewayInteraction> getDimerisingInputs() {
		//		vector that will hold the dimerising inputs
		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(2, 1);

		if (this.isDimer()){
			
			try {
				
					
	//			go through all inputs for this element ...
				for (int input = 0; input < this.getInteractionMatrix().length(); input++) {
					
	//				get input from InteractionMatrix
					byte value = this.getInteractionMatrix().get(net.getIndexOf(this), input);
					
	//				if it is a homo- or heterodimer ... add it to v
					if (value == p.HETERODIMER_MATRIX_VALUE
							|| value == p.HOMODIMER_MATRIX_VALUE){
						v.add(new OnewayInteraction(value, input, net.get(input)));
//						this.dimInputsFrom.add(net.get(input));
					}
				}
				
	//			TODO (for debugging) number of dimerising inputs not supposed to exceed two
				if (v.size() > 2)
					throw new MatrixNetworkException(
							"More than two dimerising inputs found!! There should be one input for a homodimer and two inputs for a heterodimer.");
				
			} catch (MatrixNetworkException e) {
	//			TODO if this is uncommented, lots of errors will be produced, because method is often called for dimers.
	//			e.printStackTrace();
			}
			
	//		return dimerising inputs
			
			}
		return v;
	}
	
	protected MyVector<OnewayInteraction> getRegulatoryInputs(){
		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(5,
				5);

		if (!this.isDimer()) {
			
			for (int input = 0; input < this.getInteractionMatrix().length(); input++) {
				byte value = this.getInteractionMatrix().get(
						net.getIndexOf(this), input);
				if (value != 0 && value != p.HETERODIMER_MATRIX_VALUE
						&& value != p.HOMODIMER_MATRIX_VALUE) {
					v.add(new OnewayInteraction(value, input, net
							.get(input)));
//					this.regInputsFrom.add(net.get(input));
				}
			}
			
			
		}		
		return v;
	}
	
	protected MyVector<OnewayInteraction> getDimerisingOutputs() {
		//		vector that will hold the dimerising outputs
		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(2, 1);

		if(!this.isDimer()){
			
	//		go through all outputs ...
			for (int output = 0; output < this.getInteractionMatrix().length(); output++) {
				
	//			get output from matrix
				byte value = this.getInteractionMatrix().get(output, net.getIndexOf(this));
				
	//			add output to v if hetero- or homodimer
				if (value == p.HETERODIMER_MATRIX_VALUE
						|| value == p.HOMODIMER_MATRIX_VALUE){
					v.add(new OnewayInteraction(value, output, net.get(output)));
	//				this.dimOutputsTo.add(net.get(output));
				}
			}
			
		}
		//		return dimerising outputs
		return v;

	}
	protected MyVector<OnewayInteraction> getRegulatoryOutputs() {
		MyVector<OnewayInteraction> v = new MyVector<OnewayInteraction>(5, 5);

		for (int output = 0; output < this.getInteractionMatrix().length(); output++) {

			byte value = this.getInteractionMatrix().get(output, net.getIndexOf(this));

			// only get those interactions that are not '0' (= no interaction)
			// and do not form a dimer.
			if (value != 0 && value != p.HETERODIMER_MATRIX_VALUE
					&& value != p.HOMODIMER_MATRIX_VALUE){
				v.add(new OnewayInteraction(value, output, net.get(output)));
//				this.regOutputsTo.add(net.get(output));
			}
		}
		return v;
	}

	
	public Object clone() throws CloneNotSupportedException {
		AbstractElement clone = (AbstractElement) super.clone();
		clone.function = (BooleanFunction) function.clone();
		
		return clone;
	}
	/**
	 * Getter of the property <tt>on</tt>
	 * 
	 * @return true: element is ON (active, expressed) - false: element is OFF
	 *         (inactive, not expressed)
	 */
	public boolean isOn() {
		return on;
	}

	/**
	 * Setter of the property <tt>on</tt>
	 * 
	 * @param on
	 *            true: element is ON (active, expressed) - false: element is
	 *            OFF (inactive, not expressed)
	 */
	public void setOn(boolean on) {
		this.on = on;
	}

	/**
	 * Getter of the property <tt>function</tt>
	 * 
	 * @return Returns the function.
	 * @uml.property name="function"
	 */
	public sim.operation.function.BooleanFunction getFunction() {
		return function;
	}

	/**
	 * Getter of the property <tt>interactionMatrix</tt>
	 * 
	 * @return Returns the interactionMatrix.
	 * @uml.property name="interactionMatrix"
	 */
	public InteractionMatrix getInteractionMatrix() {
		return interactionMatrix;
	}

	/**
	 * Setter of the property <tt>interactionMatrix</tt>
	 * 
	 * @param interactionMatrix
	 *            The interactionMatrix to set.
	 * @uml.property name="interactionMatrix"
	 */
	public void setInteractionMatrix(InteractionMatrix interactionMatrix) {
		this.interactionMatrix = interactionMatrix;
	}

	/**
	 * @return
	 */
	public boolean isDimer() {
		return this.dimer;
	}

	public void setDimer(boolean b) {
		this.dimer = b;
		if (b){
			this.function = p.getDefaultDimerRule();
			this.function.setParameters(p);
		}
	}

	/**
	 * Getter of the property <tt>on</tt>
	 * 
	 * @return Returns the on.
	 * @uml.property name="on"
	 */
	public boolean getOn() {
		return on;
	}
//
//	public void addInput(boolean value, int source) {
//		this.getInteractionMatrix().set(this.getIndex(), source, value);
//	}
//
//	public void addInput(OnewayInteraction i) {
//		this.getInteractionMatrix().set(this.getIndex(), i.getElementIndex(),
//				i.getValue());
//	}
//
//	public void addOutput(boolean value, int target) {
//		this.getInteractionMatrix().set(target, this.getIndex(), value);
//	}
//
//	public void addOutput(OnewayInteraction i) {
//		this.getInteractionMatrix().set(i.getElementIndex(), this.getIndex(),
//				i.getValue());
//	}

//	/**
//	 * @return
//	 * @deprecated use method <tt>indexOf(Element this_element)</tt> of class
//	 *             <tt>ElementVector</tt> instead.
//	 */
//	public int getIndex() {
//		return this.getVector().indexOf(this);
//	}

	public ElementVector getVector() {
		return vector;
	}

	public void setVector(ElementVector vector) {
		this.vector = vector;
	}

	public MyVector<OnewayInteraction> getDimInputs() {
		return dimInputs;
	}

	public void setDimInputs(MyVector<OnewayInteraction> dimInputs) {
		this.dimInputs = dimInputs;
	}

	public MyVector<OnewayInteraction> getDimOutputs() {
		return dimOutputs;
	}

	public void setDimOutputs(MyVector<OnewayInteraction> dimOutputs) {
		this.dimOutputs = dimOutputs;
	}

	public MyVector<OnewayInteraction> getRegInputs() {
		return regInputs;
	}

	public void setRegInputs(MyVector<OnewayInteraction> regInputs) {
		this.regInputs = regInputs;
	}

	public MyVector<OnewayInteraction> getRegOutputs() {
		return regOutputs;
	}

	public void setRegOutputs(MyVector<OnewayInteraction> regOutputs) {
		this.regOutputs = regOutputs;
	}

	public Network getNet() {
		return net;
	}

	public void setNet(Network net) {
		this.net = net;
	}

	public void setFunction(BooleanFunction function) {
		this.function = function;
	}

	public int getDimerTimeRemaining() {
		return dimerTimeRemaining;
	}

	public void setDimerTimeRemaining(int dimerTimeRemaining) {
		this.dimerTimeRemaining = dimerTimeRemaining;
	}

	public boolean isSink() {
		return sink;
	}

	public void setSink(boolean sink) {
		this.sink = sink;
	}



}
