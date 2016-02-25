/**
 * 
 */
package sim.operation.function;

import java.io.Serializable;

import sim.Parameters;
import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.InteractionMatrix;
import sim.datatype.MatrixElement;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.OnewayInteraction;

/**
 * @author sikosek
 *
 */
public class AndRule implements BooleanFunction, Serializable, Cloneable {

	private Parameters p;
	
	
	private static final long serialVersionUID = 1L;

	
	
	public boolean integrate(Network net, int geneindex){
		ElementVector ev = net.getElementVector();
		InteractionMatrix im = net.getInteractions();
		Element elem = net.get(geneindex);
		MyVector<OnewayInteraction> inputs = elem.getRegInputs();
		
		
		boolean result = false;
		boolean dimerTerm = true;
		boolean monomerTerm = true;
		byte value;
		try {
			if (net.get(geneindex).isDimer()) 
				throw new BooleanRuleException("AND-rule is not supposed to handle dimers!");
			
			for(int j = 0; j < im.length(); j++){
				value = im.get(geneindex, j);
				if(value != 0 
						&& value != p.HETERODIMER_MATRIX_VALUE 
						&& value != p.HOMODIMER_MATRIX_VALUE){
					
					if(elem.isOn()){ // if input element is ON
						if(value == -1) monomerTerm = false; // if input element is monomeric repressor
						if(value == -2) dimerTerm = false;   // if input element is dimeric repressor
					}
					else{ // if input element is OFF
						if(value == 1) monomerTerm = false;  // if input element is monomeric activator
						if(value == 2) dimerTerm = false;    // if input element is dimeric activator
					}
				}
			}//end for-loop
			
			if (!dimerTerm) result = monomerTerm; // dimers always beat monomers...
			else result = dimerTerm;
			
		} catch (BooleanRuleException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see sim.BooleanFunction#setParameters(sim.Parameters)
	 */
	public void setParameters(Parameters p) {
		this.p = p;
	}
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
