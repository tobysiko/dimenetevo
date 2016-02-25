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
		processes.add(new DefaultProcess());
		gui = new GraphicalInterface(processes);
		gui.setVisible(true);
	}
}
