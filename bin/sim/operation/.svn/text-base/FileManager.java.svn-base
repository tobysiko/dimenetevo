/**
 * 
 */
package sim.operation;

import java.io.File;

import sim.Parameters;
import sim.datatype.AbstractSimulationState;
import sim.datatype.NetworkHistory;
import sim.datatype.PopulationHistory;

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
	public abstract File writeStateToFileCompact(AbstractSimulationState state, String filename);
	public abstract NetworkHistory loadNetworkHistory(NetworkDataCollector dc, String filename);
	public abstract String writeNetworkHistory(NetworkHistory networkHistory, String filename, boolean createNewFile);
	public abstract boolean deleteLastState();
	public boolean writePopulationHistory(PopulationHistory pophist, String filename);
//	public abstract boolean deleteState(String state);
	
	public abstract boolean deleteAllStates();
	public abstract String createProcessFolder(String baseFolder);
//	public abstract static void createFolders(Vector<EvolutionaryProcess> ps, String baseFolder);
	public abstract void printStates();
}
