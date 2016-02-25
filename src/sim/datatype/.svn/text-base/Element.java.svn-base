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
	public Object clone()throws CloneNotSupportedException;
	
	public int getDimerTimeRemaining();
	
	//	public MyVector<OnewayInteraction> getDimerisingInputs();
//	public MyVector<OnewayInteraction> getRegulatoryInputs();
//	public MyVector<OnewayInteraction> getDimerisingOutputs();
//	public MyVector<OnewayInteraction> getRegulatoryOutputs();
	public MyVector<OnewayInteraction> getDimInputs() ;
	
	public MyVector<OnewayInteraction> getDimOutputs() ;
	
	public BooleanFunction getFunction();
	
	public InteractionMatrix getInteractionMatrix();
	public Network getNet();
	public boolean getOn();

	public MyVector<OnewayInteraction> getRegInputs();
	
	public MyVector<OnewayInteraction> getRegOutputs();
	
	public ElementVector getVector();
	public boolean isDimer();

	public boolean isOn();

	public abstract boolean isSink();

	public void setDimer(boolean b);

	public void setDimerTimeRemaining(int dimerTimeRemaining);

	public void setDimInputs(MyVector<OnewayInteraction> dimInputs) ;

	public void setDimOutputs(MyVector<OnewayInteraction> dimOutputs) ;

	public void setFunction(BooleanFunction function);

	public void setInteractionMatrix(InteractionMatrix interactionMatrix);

	public void setNet(Network net);
	
	public void setOn(boolean on);
	
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
	
	public void setRegInputs(MyVector<OnewayInteraction> regInputs);
	
	public void setRegOutputs(MyVector<OnewayInteraction> regOutputs) ;
	
	public abstract void setSink(boolean b);

	public void setVector(ElementVector vector);

	public void updateConnections();
}
