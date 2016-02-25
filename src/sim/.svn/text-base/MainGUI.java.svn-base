/**
 * 
 */
package sim;

import sim.datatype.MyVector;
import sim.gui.GraphicalInterface;

/**
 * @author sikosek
 *
 */
public class MainGUI {


	
	private static GraphicalInterface gui;

	public static void main( String[] args )
	{
		MyVector<EvolutionaryProcess> processes = new MyVector<EvolutionaryProcess>();
		Parameters parms = new Parameters();
		parms.DEFAULT_NETWORK = 1;
		parms.DEFAULT_GENERATOR = 2;
		parms.WRITE_FILES = false;
		parms.GENERATIONS = 1000;
		processes.add(new DefaultProcess(parms));
		gui = new GraphicalInterface(processes);
		gui.setVisible(true);
	}
}
