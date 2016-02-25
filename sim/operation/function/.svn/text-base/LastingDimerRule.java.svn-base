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
public class LastingDimerRule implements Serializable, Cloneable,
		BooleanFunction {
	private static final long serialVersionUID = 1L;
	private Parameters p;
	/* (non-Javadoc)
	 * @see sim.operation.BooleanFunction#integrate(sim.datatype.Network, int)
	 */
	public boolean integrate(Network net, int elementindex) {
		try {
			if (!net.get(elementindex).isDimer())
				throw new BooleanRuleException("this rule does only apply to dimers!");
			
			Element dimer = net.get(elementindex);
			MyVector<OnewayInteraction> dimIns = dimer.getDimInputs();
			boolean monomersPresent = true;
			
			if (dimIns.size() < 1 || dimIns.size() > 2)
				throw new BooleanRuleException("weird number of dimerising inputs: "+dimIns.size());
			
//			if dimer is still stable, it doesn't matter if monomers are expressed
			if (dimer.getDimerTimeRemaining() > 0){
				dimer.setDimerTimeRemaining(dimer.getDimerTimeRemaining() - 1);
				return true;
			}
			
			if (dimIns.size() == 1){
				if (!dimIns.get(0).getElement().isOn()){
					monomersPresent = false;
				}
			}else if (dimIns.size() == 2){
				if (!dimIns.get(0).getElement().isOn() || !dimIns.get(1).getElement().isOn()){
					monomersPresent = false;
				}
			}
			if (monomersPresent){
				dimer.setDimerTimeRemaining(p.STABLE_DIMER_TIME);
			}
			return monomersPresent;
		} catch (BooleanRuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	public void setParameters(Parameters p) {
		this.p = p;

	}
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
