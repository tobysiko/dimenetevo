/**
 * 
 */
package sim.operation;

import sim.EvolutionaryProcess;
import sim.datatype.Network;
import sim.operation.function.BooleanFunction;

/**
 * @author sikosek
 *
 */
public class SinksSimulator extends DefaultSimulator implements Simulator {

	/**
	 * @param process
	 */
	public SinksSimulator(EvolutionaryProcess process) {
		super(process);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.Simulator#simulate(sim.Parameters, sim.Network)
	 */
	protected boolean[] updateSynchronously(Network net, boolean[] previousStep) {

		// if no exception thrown, proceed ...
		try {

			// TODO for debugging. checks if 'previousStep' matches the network
			// size
			if (previousStep.length != net.size())
				throw new DefaultSimulatorException(
						"previousStep.length != net.size() !!!");

			// set genes in network ON/OFF according to 'previousStep'
			net.setToState(previousStep);

			// the new time step that is generated from 'previousStep'
			boolean[] currentstep = new boolean[previousStep.length];

			// the boolean function that integrates the inputs for each node.
			// different elements can have different boolean functions ...
			BooleanFunction function;
			
			for (int i = 0; i < currentstep.length; i++) {
				// get its boolean function
				function = net.getElementVector().get(i).getFunction();
				// get its new state according to the function
				currentstep[i] = function.integrate(net, i);
			}

			// return the newly generated time step
			return currentstep;

			// if something went wrong ...
		} catch (DefaultSimulatorException e) {
			e.printStackTrace();
		}
		// ... return nothing
		return null;
	}

}
