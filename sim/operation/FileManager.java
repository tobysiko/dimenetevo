/**
 * 
 */
package sim.operation;

import java.io.File;

import sim.Parameters;
import sim.datatype.AbstractSimulationState;

/** 
 * @author sikosek
 */
public interface FileManager {

	public abstract Parameters loadParameters(String filename);
	public abstract File saveParametersToFile(Parameters p, String filename);
	public abstract AbstractSimulationState loadLastState();
	
	public abstract AbstractSimulationState loadState(String filename);
	public abstract AbstractSimulationState loadStateCompact(String filename);
	public abstract AbstractSimulationState loadState(File file);
	public abstract boolean writeStateToFile(AbstractSimulationState state);
	public abstract boolean writeStateToFile(AbstractSimulationState state, String filename); 
	public abstract File writeStateToFileCompact(AbstractSimulationState state);
	
	public abstract boolean deleteLastState();
	
//	public abstract boolean deleteState(String state);
	
	public abstract boolean deleteAllStates();
	
	public abstract void printStates();
}
