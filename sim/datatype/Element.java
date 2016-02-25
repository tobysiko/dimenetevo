/**
 * 
 */
package sim.datatype;

import sim.operation.function.BooleanFunction;


/**
 * @author sikosek
 *
 */
public interface Element {
	public boolean isOn();
	
	public void setOn(boolean on);
	
	public BooleanFunction getFunction();
	
	public InteractionMatrix getInteractionMatrix();
	
	public void setInteractionMatrix(InteractionMatrix interactionMatrix);
	
	public boolean isDimer();
	
	public void setDimer(boolean b);
	
	public boolean getOn();
	
	public void updateConnections();
	public int getDimerTimeRemaining();

	public void setDimerTimeRemaining(int dimerTimeRemaining);

//	public MyVector<OnewayInteraction> getDimerisingInputs();
//	public MyVector<OnewayInteraction> getRegulatoryInputs();
//	public MyVector<OnewayInteraction> getDimerisingOutputs();
//	public MyVector<OnewayInteraction> getRegulatoryOutputs();
	public MyVector<OnewayInteraction> getDimInputs() ;

	public void setDimInputs(MyVector<OnewayInteraction> dimInputs) ;

	public MyVector<OnewayInteraction> getDimOutputs() ;

	public void setDimOutputs(MyVector<OnewayInteraction> dimOutputs) ;

	public MyVector<OnewayInteraction> getRegInputs();

	public void setRegInputs(MyVector<OnewayInteraction> regInputs);

	public MyVector<OnewayInteraction> getRegOutputs();

	public Object clone()throws CloneNotSupportedException;
	
	public void setRegOutputs(MyVector<OnewayInteraction> regOutputs) ;
	
//	public void addInput(boolean value, int source);
//	
//	public void addInput(OnewayInteraction i);
//	
//	public void addOutput(boolean value, int target);
//	
//	public void addOutput(OnewayInteraction i);
	
//	public int getIndex();
	
//	public MyVector<OnewayInteraction> getRegulatoryInputs();
//	
//	public MyVector<OnewayInteraction> getRegulatoryOutputs();
	
	public ElementVector getVector();
	
	public void setVector(ElementVector vector);
	
	public Network getNet();

	public void setNet(Network net);

	public void setFunction(BooleanFunction function);
}
