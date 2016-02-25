/**
 * 
 */
package sim.operation.function;

import java.io.Serializable;

import sim.Parameters;
import sim.datatype.Element;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.OnewayInteraction;

/**
 * @author sikosek
 *
 */
public class DefaultOffRule implements BooleanFunction, Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private Parameters p;
	/* (non-Javadoc)
	 * @see sim.operation.BooleanFunction#integrate(sim.datatype.Network, int)
	 */
	public boolean integrate(Network net, int elementindex) {
		try {
			if (net.get(elementindex).isDimer())
				throw new BooleanRuleException("this rule does not apply to dimers!");
			MyVector<OnewayInteraction> regIns = net.get(elementindex).getRegInputs();
			byte value;
			Element element;
			boolean activatorPresent = false;
			
			floop:
			for (OnewayInteraction input:regIns){
				value = input.getValue();
				element = input.getElement();
				
				if (value != 0 && value != p.HETERODIMER_MATRIX_VALUE && value != p.HOMODIMER_MATRIX_VALUE){
					if (value > 0){
						if (element.isOn()){
							activatorPresent = true;
							break floop;
						}
					}
				}
			}
			return activatorPresent;
		} catch (BooleanRuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see sim.operation.BooleanFunction#setParameters(sim.Parameters)
	 */
	
	public void setParameters(Parameters p) {
		this.p = p;

	}
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
