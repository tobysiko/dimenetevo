/**
 * 
 */
package sim.operation;

import sim.EvolutionaryProcess;
import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.OnewayInteraction;

/**
 * @author sikosek
 *
 */
public class SinkMutator extends DefaultMutator {

	/**
	 * @param process
	 */
	public SinkMutator(EvolutionaryProcess process) {
		super(process);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Picks a random element and adds a random regulatory output to it.
	 * @param net The network.
	 * @return if successful, this method returns <tt>true</tt>, otherwise <tt>false</tt>.
	 */
	protected synchronized boolean formNewRandomRegulatoryInteraction(Network net){
		
//		proceed if no exception thrown
		try {
			
//			pick random element (all elements can potentially have regulatory output)
			Element source = this.pickRandomSource(net);
			
//			pick random gene to be the target of the new interaction
			Element target = this.pickRandomGene(net);
			
//			TODO for debugging. remove...
			if (source == null)
				return false;
//				throw new DefaultMutatorException("pickRandomRegulator(net) failed!");
			
//			TODO for debugging. remove...
			if (target == null)
				throw new DefaultMutatorException("pickRandomGene(net) failed!");
			
//			MyVector<OnewayInteraction> inputs = net.getRegulatoryInputsOf(target);
//			
//			for(OnewayInteraction input:inputs){
//				int count = 0;
//				while (!(count == net.size()) && input.getElement() == source){
//					source = this.pickRandomElement(net);
//					count ++;
//				}
//				if (input.getElement() == source)
//					return false;
//			}
			
//			add new interaction between the regulator and its new target
			net.addRegulatoryInteraction(source, uniform.nextBoolean(), target);
			
//			operation successful
			return true;
			
//		something went wrong ...
		} catch (DefaultMutatorException e) {
			e.printStackTrace();
		}
		
//		operation unsuccessful
		return false;
	}

	/**
	 * Picks a random gene that does not form a homodimer, yet, and makes it homodimerise, i.e. adds a new homodimer to the network.
	 * @param net The network.
	 * @return if successful, this method returns <tt>true</tt>, otherwise <tt>false</tt>.
	 */
	public synchronized boolean formNewHomodimer(Network net){
		
//		proceed if no exception thrown
		try {
	
//			only pick a gene that does not form a dimer already 			
			Element source = this.pickRandomNoHomodimerSource(net);
			
//			TODO for debugging ... replace throw-statement with 'return false;' (in case no suitable element exists.)
			if (source == null)
				throw new DefaultMutatorException("pickRandomNoHomodimerSource(net) failed!");
			
//			the regulatory output of the new dimer
			Element target = this.pickRandomGene(net);
			
//			TODO for debugging ...
			if (target == null)
				throw new DefaultMutatorException("pickRandomGene(net) returned -1!");
			
//			create new element
			Element newdimer = net.addElement();
			
//			mark element as 'dimer'
			newdimer.setDimer(true);
			
//			add new homodimer-interaction between gene and new dimer
			net.addInteraction(source, p.HOMODIMER_MATRIX_VALUE, newdimer);
			
//			add regulatory interaction between dimer and target
			net.addRegulatoryInteraction(newdimer, uniform.nextBoolean(), target);
			
//			operation successful
			return true;
			
//		something went wrong ...	
		} catch (DefaultMutatorException e) {
			e.printStackTrace();
		}
		
//		operation unsuccessful
		return false;
	}

	protected synchronized Element pickRandomNoHomodimerSource(Network net){
		
//		get indices of all genes
		ElementVector sources = net.getSources();
		
//		vector of outputs...
		MyVector<OnewayInteraction> dimeroutputs;
		
//		number of genes
		int gsize = sources.size();
		
//		vector that collects all indices of dimers that will be removed from 'genes'
		MyVector<Integer> toBeRemoved = new MyVector<Integer>();
		
//		go through 'genes' ...
		for (int i = 0; i < gsize; i++){
			
//			get dimerising outputs of gene i
			dimeroutputs = net.getDimerisingOutputsOf(sources.get(i));
			
//			to decide if gene i forms homodimer ...
			boolean formshomodimer = false;
			
//			... go through all dimerising outputs
			outputsLoop:
			for (OnewayInteraction output:dimeroutputs)
			{	
//				if output is a homodimer ... 'formshomodimer' is true
				if (net.isHomodimer(output.getElementIndex())){ 
					formshomodimer = true;
					break outputsLoop;
				}
			}
			
//			add those genes that do not form homodimer to 'remove'
			if (formshomodimer)
				toBeRemoved.add(i);
		}
		
//		finally remove those indices (the homodimer-forming genes) from 'genes'
		for (int i = 0; i < toBeRemoved.size(); i++)
			sources.remove(toBeRemoved.get(i));
		
//		if there are no genes (left) ... return nothing
		if (sources.size() == 0) 
			return null;
		
//		... otherwise return a random gene
		else {
			Element randomNonHomodimerSource = sources.get(uniform.nextIntFromTo(0, sources.size() - 1));
			return randomNonHomodimerSource;
		}
	}
	/**
	 * Picks a random dimer and a gene and forms a heterodimer between the gene and one of the dimer's genes.
	 * @param net The network.
	 * @return if successful, this method returns <tt>true</tt>, otherwise <tt>false</tt>.
	 */
	protected synchronized boolean formNewHeterodimer(Network net){
		
//		proceed if no exception thrown
		try {
			
//	 		pick old dimer that is supposed to be part of the new dimer
			Element dimer = this.pickRandomDimer(net);
			
//			if no dimer found ... abort.
			if (dimer == null) 
				return false;
			
//			this will allow for the new dimer partner to form another dimer already
			Element geneOfNewDimer = this.pickRandomSource(net);
			
//			TODO for debugging ...
			if (geneOfNewDimer == null || dimer == null)
				throw new DefaultMutatorException("pickRandomGene(net) or pickRandomDimer(net) returned -1!");
			
//			gene that forms the existing dimer ... not determined yet.
			Element geneOfOldDimer;
			
//			get the inputs that form this dimer ...
			MyVector<OnewayInteraction> dimerinputs = net.getDimerisingInputsOf(dimer);
			
//			TODO for debugging. checks for correct number of dimer-inputs
			if (dimerinputs.size() < 1 || dimerinputs.size() > 2) 
				throw new DefaultMutatorException("the number of dimerising inputs must be 1 or 2!!");
			
//			if dimer is a homodimer ... only one choice for 'geneOfOldDimer'
			if (dimerinputs.size() == 1)
				geneOfOldDimer = dimerinputs.get(0).getElement();
			
//			otherwise, dimer is a heterodimer ... choose randomly between the two possibilities for 'geneOfOldDimer'
			else
				geneOfOldDimer = dimerinputs.get(uniform.nextIntFromTo(0, dimerinputs.size()-1)).getElement();
			
//			add element that becomes the new dimer to network ...
			Element newdimer = net.addElement();
			
//			mark new element as 'dimer'
			newdimer.setDimer(true);

//			pick random regulatory target for new dimer
			Element target = this.pickRandomGene(net);
			
//			TODO debugging...
			if (target == null)
				throw new DefaultMutatorException("pickRandomGene(net) failed!");
			
//			add dimer-interaction between gene that forms old dimer and the new heterodimer
			net.addInteraction(geneOfOldDimer, p.HETERODIMER_MATRIX_VALUE, newdimer);
			
//			add dimer interaction between gene of new heterodimer and the dimer itself
			net.addInteraction(geneOfNewDimer, p.HETERODIMER_MATRIX_VALUE, newdimer);
			
//			add regulatory interaction (OF RANDOM VALUE) between new dimer and its target
			net.addRegulatoryInteraction(newdimer, uniform.nextBoolean(), target); 
			
//			operation successful
			return true;
		
//		something went wrong ...
		} catch (DefaultMutatorException e) {
			e.printStackTrace();
		}
		
//		operation unsuccessful
		return false;
	}
	public static synchronized boolean formNewHomodimer(Network net, EvolutionaryProcess process){
		return process.getSelector().getMutator().formNewHomodimer(net);
	}
}
