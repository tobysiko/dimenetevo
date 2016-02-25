/**
 * 
 */
package sim.operation;

import sim.datatype.Network;
import sim.datatype.TimeStepSeries;

/** 
 * @author sikosek
 */
public interface Simulator {

	public abstract TimeStepSeries simulateSingleState (Network net, boolean[] initialState);
	public abstract void simulateAllStates(Network net);
	public abstract boolean[] generateRandomInitialState(Network net);
	public abstract boolean[] stringToBooleanArray(String s);
	public abstract TimeStepSeries findAttractor(boolean[] state);
	
}
