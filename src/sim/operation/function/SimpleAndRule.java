/**
 * 
 */
package sim.operation.function;

import java.io.Serializable;

import sim.Parameters;
import sim.datatype.ElementVector;
import sim.datatype.InteractionMatrix;
import sim.datatype.Network;

/**
 * @author sikosek
 *
 */
public class SimpleAndRule implements BooleanFunction, Serializable, Cloneable {

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Parameters p;
	
	

	/** (non-Javadoc)
	 * does not take dimers into account. every activator/repressor treated in the same way.
	 * @see sim.operation.function.BooleanFunction#integrate(sim.datatype.Network, int)
	 */
	public boolean integrate(Network net, int geneindex) {
		
		ElementVector ev = net.getElementVector();
		
		InteractionMatrix im = net.getInteractions();
		
		try {
			
			if (ev.get(geneindex).isDimer()) 
				throw new BooleanRuleException("AND-rule is not supposed to handle dimers!");
			
			boolean activatorsON = false;
			
			byte value;
			
			for (int j = 0; j < im.length(); j++){
				
				value = im.get(geneindex, j);
				
//				only let regulatory inputs through ...
				if (value != 0 && value != p.HETERODIMER_MATRIX_VALUE && value != p.HOMODIMER_MATRIX_VALUE){

//					if input element is ON
					if (ev.get(j).isOn()){ 
						
//						if input is repressor
						if (im.get(geneindex, j) < 0) {
							return false; 
						}
						else{
							activatorsON = true;
						}
						
					}
					
//					if input element is OFF
					else{ 
						
//						if input is activator
						if(im.get(geneindex, j) > 0) {
//							System.out.println("OFF!");
							return false;  // if input element is monomeric activator
						}
						
					}
				}
			}//end for-loop
			
			
			return (activatorsON || ev.get(geneindex).isOn());

			
		} catch (BooleanRuleException e) {
			e.printStackTrace();
		}
		
		return false;
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
