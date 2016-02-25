/**
 * 
 */
package sim.operation;

import java.io.Serializable;
import java.util.Date;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Network;
import sim.datatype.TimeStepSeries;

/**
 * @author sikosek
 *
 */
public abstract class AbstractStatesGenerator implements Serializable, Cloneable{
	
	//	stores all initial states
		protected TimeStepSeries allStates;
		
	//	helps reading out one initial step after the other
		protected int counter;
	
		protected Network net;
		
		protected Parameters p;
		
		protected EvolutionaryProcess process;
		
		protected RandomEngine generator = new MersenneTwister(new Date());

		protected Uniform uniform = new Uniform(generator);

		
		public AbstractStatesGenerator(EvolutionaryProcess process){
			this.process = process;
			
			this.p = process.getParm();
			
//			initialise 'allStates'
			allStates = new TimeStepSeries();
			
//			initialise 'counter'
			counter = 0;
			
			
			
		}
		public abstract void generateStates(Network net);
		
		/**
		 * Sets the internal counter back to zero, so it can be used for the simulation of a different network.
		 */
		public void resetCounter(){
			counter = 0;
		}
		
		
		/**
		 * The number of initial states generated for the given simulation.
		 * @return number of initial states.
		 */
		public int getNumberOfStates(){
			return allStates.size();
		}
		public int estimateStates(int n){
			return (int) Math.pow(2, n);
			
		}
		public abstract void pickRandomStates(boolean[] notConsidered);
		
		/**
		 * Get initial state at specified index.
		 * @param index index of vector storing all initial states.
		 * @return the initial state at that index.
		 */
		public boolean[] getState(int index){
			if (!allStates.isEmpty()) {
				return allStates.get(index);
			}	
			else{
				try {
					throw new Exception("no states available!!");
				} catch (Exception e) {
					
				}	
				return null;
			}
		}
		
		public boolean[] stringToBooleanArray(String s) {

			// the Boolean array of the same size as the string
			boolean[] b = new boolean[s.length()];

			// proceed, if no exceptions thrown ...
			try {

				// go through entire array ...
				for (int i = 0; i < b.length; i++) {

					// translate a '1' into 'true'
					if (s.charAt(i) == '1')
						b[i] = true;

					// translate a '0' into 'false' - or throw an exception for any
					// other character
					else {
						if (s.charAt(i) == '0')
							b[i] = false;
						else
							throw new DefaultSimulatorException(
									"wrong character in string: " + s.charAt(i));
					}
				}

				// something went wrong ...
			} catch (DefaultSimulatorException e) {
				e.printStackTrace();
			}

			// return the Boolean array
			return b;
		}

		public boolean[] getNext(){
			if (counter < allStates.size() - 1){
				boolean[] b = allStates.get(counter);
				counter++;
				return b;
			}
			else{
				try {
					throw new DefaultSimulatorException("AbstractStatesGenerator.getNext() exceeded size of 'allStates'!!");
				} catch (DefaultSimulatorException e) {
					e.printStackTrace();
				}
				return null;
			}
		}

		public TimeStepSeries getAllStates() {
			return allStates;
		}
		
		public Object clone() throws CloneNotSupportedException{
			AbstractStatesGenerator clone = (AbstractStatesGenerator) super.clone();
			clone.allStates = (TimeStepSeries) allStates.clone();
			clone.generator = (RandomEngine) generator.clone();
			clone.net = (Network) net.clone();
			clone.p = (Parameters) p.clone();
			clone.uniform = (Uniform) uniform.clone();
			
			return clone;
		}
}
