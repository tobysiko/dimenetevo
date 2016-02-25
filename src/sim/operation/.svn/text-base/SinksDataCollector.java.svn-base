/**
 * 
 */
package sim.operation;

import java.util.Vector;

import sim.Parameters;
import sim.datatype.Attractor;
import sim.datatype.Network;

/**
 * @author sikosek
 *
 */
public class SinksDataCollector extends ClusteringNetworkCollector {

	/**
	 * @param p
	 * @param net
	 */
	public SinksDataCollector(Parameters p, Network net) {
		super(p, net);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.NetworkDataCollector#addAttractor(sim.TimeStepSeries)
	 */
	public boolean addAttractor(Attractor attractor) {
		
//		proceed until exception thrown ...
		try {
			if (attractor.size() <= 0)
				return false;
			
//			only interested in expression of sinks (non-TF genes)
			extractSinks(attractor);
//			removeDimers(attractor);
			
//			attractor.generateAttractorProfile();
//			String profile = attractor.getAttractorProfile();
//			if (true)System.out.println(net+" profile: "+profile);
			
//			double on = getGenesOn(attractor);
			
//			if(on < p.ATTRACTOR_MIN_GENES || on > p.ATTRACTOR_MAX_GENES)
//				return false;
			
//			reorder the states in the attractor so that the "smallest one" comes first in the vector
			attractor = formatAttractor(attractor);
			
//			will become 'true' if the attractor has already been encountered before (and therefore will NOT be added!)
			boolean found = false;
			
//			get the first state of the attractor
			boolean[] first = attractor.get(0);
			
//			(for testing/debugging)
			if (attractor.isEmpty())
				throw new DefaultSimulatorException("attractor is empty!!");
			
//			the first attractor is always added ...
			if (attractors.isEmpty()) {
				
//				add attractor to 'attractors'
				attractors.add(attractor);
				attractorCount.add(1);
				
				if (attractor.size() > p.ATTRACTOR_MAX_LENGTH)
					throw new DefaultSimulatorException("attractor too long!!");
				if (attractor.size() > 0){
					cycleLengthHistogram.set(attractor.size(), cycleLengthHistogram.get(attractor.size()) + 1);
				}
				
//				[verbose text output]
				if (p.VERBOSE)
					System.out.println("ADDED attractor with first state: "
							+ booleanArrayToString(first));
				
//				attractor was added. terminate here.
				return true;
			}
			
//			all subsequent attractors are compared (by first state) to each of the stored attractors ...
			for (int i = 0; i < attractors.size(); i++) {
				
//				are the first states of the two identical: attractor has already been found before
				if (this.compareBooleanArrays(attractors.get(i).get(0), first)) {
//				if (calculateDistance(attractor, attractors.get(i)) >= p.ATTR_DISTANCE_THRESHOLD) {
					found = true;
					int c = attractorCount.get(i);
					c++;
					attractorCount.set(i, c);
				}
			}
			
//			if no matching attractor found, this one must be new ...
			if (!found) {
				
//				add attractor to 'attractors'
				attractors.add(attractor);
				attractorCount.add(1);
//				System.out.println("attractor length: " +attractor.size());
				if (attractor.size() > p.ATTRACTOR_MAX_LENGTH)
					throw new DefaultSimulatorException("attractor too long!!");
				if (attractor.size() > 0){
					cycleLengthHistogram.set(attractor.size(), cycleLengthHistogram.get(attractor.size()) + 1);
				}
				
//				[verbose text output]
				if (p.VERBOSE)
					System.out.println("ADDED: "
							+ booleanArrayToString(first));
				
//				attractor was added. terminate here.
				return true;
			}
			
//		exception occured
		} catch (DefaultSimulatorException e) {
			e.printStackTrace();
		}
		
//		attractor was NOT added. terminate.
		return false;
	}

	protected void extractSinks(Attractor attractor){
		try {
			
			boolean[] sinks = new boolean[net.size()];
			
			if (! attractor.isEmpty()){
				
				if (attractor.get(0).length != net.size())
					throw new DefaultSimulatorException("mismatch of elements between attractor and network!");
				
				int nSinks = 0;
				
				for (int i = 0; i < net.size(); i++){
					if (net.get(i).isSink()){
						sinks[i] = true;
						nSinks++;
					}
					else
						sinks[i] = false;
				}
				
				if (nSinks == 0)
					return;
				
				for (boolean[] state:attractor){
					boolean[] newState = new boolean[nSinks];
					int count = 0;
					for (int i = 0; i < state.length; i++){
						if (sinks[i]){
							newState[count] = state[i];
							count++;
						}
					}
					state = newState;
				}
				
				
			}
		} catch (DefaultSimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
