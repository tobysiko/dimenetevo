/**
 * 
 */
package sim.operation;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import sim.Parameters;
import sim.datatype.Attractor;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.TimeStepSeries;

/**
 * Uses algorithm from Skarja et al. 2004 for measuring similarity between attractors by looking at the frozen components.
 * @author sikosek
 *
 */
public class ClusteringNetworkCollector extends DefaultNetworkCollector
		implements Cloneable, Serializable {

//	public int attractorCounter;
	
	/**
	 * @param p
	 * @param net
	 */
	public ClusteringNetworkCollector(Parameters p, Network net) {
		super(p, net);
//		attractorCounter = 0;
//		System.out.println("constructor of ClusteringNetworkCollector");
		// TODO Auto-generated constructor stub
	}

	public boolean addAttractor(Attractor attractor) {
		
//		long startAdd = new Date().getTime();
		
		boolean debug = false;
		
		if (debug)System.out.println("add attractor...");

		
//		O(N*x) x= attractor cycle length (< 50)
		if (!p.ATTRACTOR_PROFILE_TRANS){
			attractor.generateAttractorProfile();
		}else{
			attractor.generateAttractorProfileWithTransitions();
		}
		
		if (!p.ATTRACTOR_PROFILE_TRANS){
			String profile = attractor.getAttractorProfile();
			if (debug)System.out.println(net+" profile: "+profile);
		}else{
			byte[] profile = attractor.getTransitionProfile();
			if (debug)System.out.println(net+" profile: "+profile);
		}

		
		
//		proceed until exception thrown ...
		try {
			if (attractor.isEmpty())
				return false;
			
//			double on = getRegulatorsOnInProfile(attractor.getAttractorProfile());
			
//			if(on < p.ATTRACTOR_MIN_GENES || on > p.ATTRACTOR_MAX_GENES)
//				return false;
			
//			will become 'true' if the attractor has already been encountered before (and therefore will NOT be added!)
			boolean found = false;
			
			if (!attractors.isEmpty()){
				if (debug)System.out.println("attractors not empty...");
//				long preLoop = new Date().getTime();
				
//				worst case: Omega(2^N) but not more than 2^max_power (e.g. 2^11 = 2048)
				for (int i = 0; i < attractors.size(); i++) {
					
//					if attractors have same cycle lengths: compare them...
					if (attractor.size() == attractors.get(i).size()) {
						
//						O(N) --- are the first states of the two identical: attractor has already been found before

						if (!p.ATTRACTOR_PROFILE_TRANS){
							if (calculateDistance(attractor, attractors.get(i)) >= p.ATTR_DISTANCE_THRESHOLD) {
								if (debug) System.out.println("attractor found! not added.");
								found = true;
								int c = attractorCount.get(i);
								c++;
								attractorCount.set(i, c);
								break;
							}
						}else{
							if (calculateDistanceWithTransitions(attractor, attractors.get(i)) >= p.ATTR_DISTANCE_THRESHOLD) {	
								if (debug) System.out.println("attractor found! not added.");
								found = true;
								int c = attractorCount.get(i);
								c++;
								attractorCount.set(i, c);
								break;
							}		
						}
					}
				}
//				long postLoop = new Date().getTime();
//				if ((postLoop-preLoop) > 10){
//					System.out.println("\t\t\tloop: "+(postLoop-preLoop));
//					System.out.println(attractor);
//				}
			}

//			if no matching attractor found, this one must be new ...
			if (!found) {
				if (attractor.size() > p.ATTRACTOR_MAX_LENGTH)
					throw new DefaultSimulatorException("attractor too long!!");
//				add attractor to 'attractors'
				attractors.add(attractor);
				attractorCount.add(1);
				attractorNumber=attractors.size();
//				System.out.println("attractor length: " +attractor.size());
				
				if (attractor.size() > 0){
					cycleLengthHistogram.set(attractor.size(), cycleLengthHistogram.get(attractor.size()) + 1);
				}
//				System.out.println("attractor added:"+attractor.toString());
//				System.out.println("profile:\t"+attractor.transitionProfileToString());
				return true;
			}
			
//		exception occured
		} catch (DefaultSimulatorException e) {
			e.printStackTrace();
		}
//		long postAdd = new Date().getTime();
//		if ((postAdd-startAdd) > 1)System.out.println("\t\t\t addAttractor(not added):"+(postAdd-startAdd));
//		attractor was NOT added. terminate.
		return false;
	}
	
	protected void removeNonDimers(Attractor attractor){
		
		try {
			boolean[] nondimers = new boolean[net.size()];
			for (int i = 0; i < net.size(); i++){
				
					nondimers[i] = !net.get(i).isDimer();
			}
			if (p.VERBOSE)System.out.println("remove dimers: "+booleanArrayToString(nondimers));
			for(boolean[] state:attractor){
				if (state.length != net.size())
					throw new DefaultSimulatorException("attractor state length does not match network size!!!");
				for (int i = 0; i < state.length; i++){
					if (nondimers[i])
						state[i] = false;
				}
			}
		} catch (DefaultSimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] removeNonDimersFromTransitionProfile(byte[] profile){
//		System.out.println("old:"+profile.length);
		Vector<Byte> newprofile = new Vector<Byte>();
		for (int i = 0; i < profile.length; i++){
			if (net.get(i).isDimer()){
				newprofile.add(profile[i]);
			}
		}
		byte[] newprofilearray = new byte[newprofile.size()];
		for (int i = 0; i < newprofile.size(); i++){
			profile[i] = newprofile.get(i);
		}
//		System.out.println("new:"+profile.length);
		return newprofilearray;
	}
	
	
	public double getRegulatorsOnInProfile(String profile){
		if (p.VERBOSE) System.out.println("getRegulatorsOnInProfile");
		double on = 0;
		for (int i = 0; i < profile.length(); i++){
			if (profile.charAt(i) != '0'){
				on++;
			}
		}	
		on /= profile.length();
		if (p.VERBOSE) System.out.println(" - prop. on: "+on);
		return on;
	}
	
	public String statsToString(){
		String s = "";
		updateStats();
		s += (" Stats for network: " /*+this.net + " - ID: " */+net.getID()+ "\n");
		s += (" comp:\t"+net.printComposition()+ "\n");
		s += (" R/Din:\t"+net.printInputNumbers()+ "\n");
		s += (" R.out:\t"+net.printRegOutputNumbers()+ "\n");
		s += (" D.out:\t"+net.printDimOutputNumbers()+ "\n");
		s += (" elements:\t" +elements+ "\n");
		s += (" genes:   \t" +genes+ "\n");
		s += (" dimers:  \t" +dimers+ "\n");
		s += (" sources: \t" +sources+ "\n");
		s += (" isolated:\t" +isolated+ "\n");
		s += (" sinks:   \t" +sinks+ "\n");
		s += (" indegree:\t" +avgInDegree+ "\n");
		s += (" outdegree:\t" +avgOutDegree+ "\n");
		s += (" activat.:\t" +posInteractions+ "\n");
		s += (" repress.:\t" +negInteractions+ "\n");
		s += (" attractors:\t" +attractorCount.size()+ "\n");
//		s += (" altern.att.\t" +attractorCounter+"\n");
//		if (!fitnessHistory.isEmpty())
//			s += (" fitness: \t" +fitnessHistory.get(fitnessHistory.size()-1)+ "[t=" +(fitnessHistory.size()-1)+ "]"+ "\n");
//		else
//			s += (" fitness: \tn/a"+"\n"); 
		s += (" fitness: \t" +getCurrentFitness()+"\n");
		s += "Cycle lengths histogram:\n";
		for (int i = 0; i < cycleLengthHistogram.size(); i++){
			if (cycleLengthHistogram.get(i) > 0)
				s += i +": " +cycleLengthHistogram.get(i)+ " times\n";
		}
		return s;
	}
	/**
	 * Only removes the attractors. Does therefore not behave like reset(). Should be used to reduce used memory.
	 */
	public void removeAttractors(){
		attractors.removeAllElements();
	}
	
	public void reset() {
//		System.out.println("network: "+getNetwork().getID());
//		System.out.println("before resetting data collector: of");
//		System.out.println(attractorCounter+" 'exact' method");
//		System.out.println(attractors.size()+" cluster method");
		attractors.removeAllElements();
		attractorCount = new MyVector<Integer>();
		cycleLengthHistogram = new MyVector<Integer>(p.ATTRACTOR_MAX_LENGTH+1);
		for (int i = 0; i < p.ATTRACTOR_MAX_LENGTH+1; i++){
			cycleLengthHistogram.add(0);
		}
//		attractorCounter = 0;
		attractorNumber=0;
	}
	protected double calculateDistance(Attractor a1, Attractor a2){
		double identical = 0.0;
		if (a1.getAttractorProfile().length() == a2.getAttractorProfile().length() 
				&& !a1.isEmpty() && !a2.isEmpty()){
			if (p.VERBOSE)System.out.println("a1:"+a1.getAttractorProfile()+"-a2: "+a2.getAttractorProfile());
			
			int genes = net.getGenes().size();
//			if (p.VERBOSE)System.out.println("genes:"+genes);
			int dimers = net.getDimers().size();
//			if (p.VERBOSE)System.out.println("dimers:"+dimers);
			int elements = net.size();
//			if (p.VERBOSE)System.out.println("elements:"+elements);
//			if (p.VERBOSE)System.out.println("a1:"+a1.getAttractorProfile());
//			if (p.VERBOSE)System.out.println("a2:"+a2.getAttractorProfile());
			if (p.ONLY_DIMERS_REGULATE){
				for (int i = 0; i < elements; i++){
					if (a1.getAttractorProfile().charAt(i) == a2.getAttractorProfile().charAt(i)){
						if (net.get(i).isDimer()){
							identical += 1;
						}
					}
				}
//				if (p.VERBOSE)System.out.println("(dimnet)identicals:"+identical);
				identical /= dimers;
			}else{
				for (int i = 0; i < elements; i++){
					if (a1.getAttractorProfile().charAt(i) == a2.getAttractorProfile().charAt(i)){
						if (!net.get(i).isDimer()){
							identical += 1;
						}
					}
				}
//				if (p.VERBOSE)System.out.println("(nondimnet)identicals:"+identical);
				identical /= genes;
			}
			
		}
		else 
			System.out.println("unequal attractor lengths!!!");
		if (p.VERBOSE)System.out.println("identical: "+identical);
		return identical;
	}
	protected double calculateDistanceWithTransitions(Attractor a1, Attractor a2){
		double identical = 0.0;
		if (a1.getAttractorProfile().length() == a2.getAttractorProfile().length() 
				&& !a1.isEmpty() && !a2.isEmpty()){
//			if (p.VERBOSE)System.out.println("a1:"+a1.getAttractorProfile()+"-a2: "+a2.getAttractorProfile());
			int genes = net.getGenes().size();
//			if (p.VERBOSE)System.out.println("genes:"+genes);
			int dimers = net.getDimers().size();
//			if (p.VERBOSE)System.out.println("dimers:"+dimers);
			int elements = net.size();
//			if (p.VERBOSE)System.out.println("elements:"+elements);
//			if (p.VERBOSE)System.out.println("a1:"+a1.transitionProfileToString());
//			if (p.VERBOSE)System.out.println("a2:"+a2.transitionProfileToString());
			if (p.ONLY_DIMERS_REGULATE){
		
				for (int i = 0; i < elements; i++){
					if (a1.getTransitionProfile()[i] == a2.getTransitionProfile()[i]){
						if (net.get(i).isDimer()){
							identical += 1;
						}
					}
				}
//				if (p.VERBOSE)System.out.println("(dimnet)identicals:"+identical);
				identical /= dimers;
			}else{
				
				for (int i = 0; i < elements; i++){
					if (a1.getTransitionProfile()[i] == a2.getTransitionProfile()[i]){
						if (!net.get(i).isDimer()){
							identical += 1;
						}
					}
				}
//				if (p.VERBOSE)System.out.println("(nondimnet)identicals:"+identical);
				identical /= genes;
			}
		}
		else 
			System.out.println("unequal attractor lengths!!!");
		if (p.VERBOSE)System.out.println("identical: "+identical);
		return identical;
	}
	public void printAttractors() {
		System.out.println(attractorsToString());
	}
	public String attractorsToString(){
		String s = "";
		s += ("\n=======================================================\n");
		s += ("| A T T R A C T O R S (different/total): ("
				+attractors.size()+ "/" +getTotalAttractorCount() +")\n");
		s += ("=====================================================\n");
		
		for (int i = 0; i < attractors.size(); i++) {
			s += ("{" +i+ "} -- " +attractorCount.get(i)+ " times --" +attractors.get(i)+ "\n");
			s += "profile: "+attractors.get(i).getAttractorProfile()+"\n";
		}
		s += "Cycle lengths histogram:\n";
		for (int i = 0; i < cycleLengthHistogram.size(); i++){
			if (cycleLengthHistogram.get(i) > 0)
				s += i +": " +cycleLengthHistogram.get(i)+ " times\n";
		}
		return s;
	}
}
