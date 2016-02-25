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
public class SimpleDimerRule implements BooleanFunction, Serializable, Cloneable {

	private Parameters p;

	private static final long serialVersionUID = 1L;

	
	/* (non-Javadoc)
	 * @see sim.BooleanFunction#integrate(sim.InteractionMatrix, sim.ElementVector, int)
	 */
	public boolean integrate(Network net, int geneindex) {
		
		ElementVector ev = net.getElementVector();
		
		InteractionMatrix im = net.getInteractions();
		
//		for heterodimers: remembers whether one of the partners has already been found
		boolean partnerFound = false;
		
		try {
			
			if(!ev.get(geneindex).isDimer()) 
				throw new BooleanRuleException("DimerRule is only supposed to handle dimers!");
			
			byte value;
			
			for (int j = 0; j < im.length(); j++){
				
				value = im.get(geneindex, j);
				
				if(value != 0) {
					
					if (value == p.HOMODIMER_MATRIX_VALUE){ 
						
//						if this gene is not on, result is definitely 'false'
						if (!ev.get(j).isOn()) 
							return false; 
						else
							return true;
					}
					
					if (value == p.HETERODIMER_MATRIX_VALUE){
						if (!ev.get(j).isOn())
							return false;
						else {
							if (partnerFound)
								return true;
							else
								partnerFound = true;
							
						}
					}
				}
			}
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
