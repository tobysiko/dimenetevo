package sim.operation;

import java.util.Date;
import java.util.Vector;

import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.Element;
import sim.datatype.ElementVector;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.datatype.OnewayInteraction;
import cern.jet.random.Poisson;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

/*
 * TODO make separate method for duplicating many genes instead of one at a time. 
 * also create corresponding add-methods in the vector and the matrix...
 * private void duplicateGene(Network net, int[] genes)
 * int[] genes = array of random gene indices
 * 
 * AIM: make adding of elements into the matrix faster. now still O(n^2) for each add operation!!!
 * */

/** 
 * Mutates networks in a population.
 * Mutations are divided into three categories: gene dynamics, link dynamics and dimer dynamics. 
 * Each of these has a separate probability and is split up further into different kinds of mutations within the category.
 * @see sim.Parameters
 * 
 * @uml.stereotype uml_id="Standard::Utility" 
 * @uml.stereotype uml_id="Standard::ImplementationClass" 
 */
public class DefaultMutator implements Mutator{
	
	static int linkDelCount = 0;
	static int linkAddCount = 0;
	
	protected RandomEngine generator;
	protected Uniform uniform;
	protected Poisson poisson_dup, poisson_del;
	protected Parameters p;
	protected EvolutionaryProcess process;
	
	/**
	 * Hands over the parameters and initialises attributes.
	 * @param p <tt>Parameters</tt> object.
	 */
	public DefaultMutator(EvolutionaryProcess process) {
		this.process = process;
		p = process.getParm();
		generator 		= new MersenneTwister(new Date());
		uniform 		= new Uniform(generator);
		poisson_dup 	= new Poisson(p.DUPL_POISSON_MEAN, generator);
		poisson_del 	= new Poisson(p.DEL_POISSON_MEAN, generator);
		
	}
	
	
	/**
	 * Duplicates a single gene in the network. 
	 * Regulatory in- and outputs are kept for the new gene are kept with certain probabilities.
	 * @see sim.Parameters
	 * If the duplicated gene forms a dimer, the new gene will form a dimer, too.
	 * @param net The Network.
	 * @param gene The gene to be duplicated.
	 * @return if successful, this method returns <tt>true</tt>, otherwise <tt>false</tt>.
	 */
	protected synchronized boolean duplicateGene(Network net, Element gene){
		
//		proceed if no exception is thrown...
		try {
			
//			add new (duplicate) gene to network
			Element newgene = net.addElement();
			
//			get regulatory inputs and outputs of old gene
			MyVector<OnewayInteraction> inputs, outputs;
			inputs = net.getRegulatoryInputsOf(gene);
			outputs = net.getRegulatoryOutputsOf(gene);

//			copy regulatory inputs from old gene to new gene
			for (OnewayInteraction input : inputs){
//				 for each input...copy it with certain probability
				double random = uniform.nextDouble();
				if (random <= p.KEEP_INPUT_PROBABILITY){
					boolean regvalue = false;
					try {
						if (input.getValue() > 0) regvalue = true;
						else if (input.getValue() < 0) regvalue = false;
						else throw new Exception("not a valid value!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					net.addRegulatoryInteraction(input.getElement(), regvalue, newgene);
				}else{
					if (random <=  p.KEEP_INPUT_PROBABILITY + p.REPLACE_LINK_PROBABILITY){
						Element randominput = pickRandomElement(net);
						net.addRegulatoryInteraction(randominput, uniform.nextBoolean(), newgene);
					}
				}
			}
			
//			copy regulatory outputs from old gene to new gene
			for (OnewayInteraction output : outputs){
//				 for each regulatory output...copy it with certain probability
				double random = uniform.nextDouble();
				if (random <= p.KEEP_OUTPUT_PROBABILITY){
					boolean regvalue = false;
					try {
						if (output.getValue() > 0) regvalue = true;
						else if (output.getValue() < 0) regvalue = false;
						else throw new Exception("not a valid value!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					net.addRegulatoryInteraction(newgene, regvalue, output.getElement());
				}else{
					if (random <=  p.KEEP_OUTPUT_PROBABILITY + p.REPLACE_LINK_PROBABILITY){
						Element randomoutput = pickRandomGene(net);
						if(randomoutput == null)
							throw new DefaultMutatorException("pickRandomGene failed!");
						net.addRegulatoryInteraction(newgene, uniform.nextBoolean(), randomoutput);
					}
				}
			}
			
//			if gene does NOT form dimer...FINISHED!
			if (net.getDimerisingOutputsOf(gene).size() == 0) 
				return true;
			
//			if gene DOES form dimer...
			if (net.getDimerisingOutputsOf(gene).size() != 0) { 
				
//				get all dimers of old gene
				MyVector<OnewayInteraction> dimeroutputs = net.getDimerisingOutputsOf(gene);
				
//				for each dimer...
				for (OnewayInteraction dimeroutput : dimeroutputs){
					
//					if it's a homodimer...
					if (net.getDimerisingInputsOf(dimeroutput.getElement()).size() == 1) {
						
//						get old homodimer
						Element oldhomodimer = dimeroutput.getElement();
						
//						create new homodimer
						Element newhomodimer = net.addElement();
						
//						mark this element as 'dimer'
						newhomodimer.setDimer(true);
						
//						set new homodimer-interaction between new gene and new homodimer
						net.addInteraction(newgene, p.HOMODIMER_MATRIX_VALUE, newhomodimer);
						
//						get regulatory outputs of old homodimer
						MyVector<OnewayInteraction> oldhomodimeroutputs = net.getRegulatoryOutputsOf(oldhomodimer);
						
//						copy regulatory outputs to new dimer
						for (OnewayInteraction output : oldhomodimeroutputs){
//							for each regulatory output...copy it with certain probability
							double random = uniform.nextDouble();
							if (random <= p.KEEP_DIMER_OUTPUT_PROBABILITY){
								boolean regvalue = false;
								try {
									if (output.getValue() > 0) regvalue = true;
									else if (output.getValue() < 0) regvalue = false;
									else throw new Exception("not a valid value!");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								net.addRegulatoryInteraction(newhomodimer, regvalue, output.getElement());
							}else{
								if (random <= p.KEEP_DIMER_OUTPUT_PROBABILITY + p.REPLACE_LINK_PROBABILITY){
									Element randomoutput = pickRandomGene(net);
									if(randomoutput == null)
										throw new DefaultMutatorException("pickRandomGene failed!");
									net.addRegulatoryInteraction(newhomodimer, uniform.nextBoolean(), randomoutput);
								}
							}
						}
//						get dimerising partners of old monomer
						
						
//						create element for new heterodimer
						Element newheterodimer = net.addElement();
						
//						mark element as 'dimer'
						newheterodimer.setDimer(true);
						
//						pick regulatory target for new heterodimer
						Element target = this.pickRandomGene(net);
						
//						TODO debugging..
						if (target == null)
							throw new DefaultMutatorException("pickRandomGene(net) failed!");
						
//						add heterodimer-interaction between old gene and new heterodimer
						net.addInteraction(gene, p.HETERODIMER_MATRIX_VALUE, newheterodimer);
						
//						add heterodimer-interaction between new gene and new heterodimer
						net.addInteraction(newgene, p.HETERODIMER_MATRIX_VALUE, newheterodimer);
						
//						copy regulatory outputs to new dimer
						for (OnewayInteraction output : oldhomodimeroutputs){
//							for each regulatory output...copy it with certain probability
							double random = uniform.nextDouble();
							if (random <= p.KEEP_DIMER_OUTPUT_PROBABILITY){
								boolean regvalue = false;
								try {
									if (output.getValue() > 0) regvalue = true;
									else if (output.getValue() < 0) regvalue = false;
									else throw new Exception("not a valid value!");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								net.addRegulatoryInteraction(newheterodimer, regvalue, output.getElement());
							}else{
								if (random <= p.KEEP_DIMER_OUTPUT_PROBABILITY + p.REPLACE_LINK_PROBABILITY){
									Element randomoutput = pickRandomGene(net);
									if(randomoutput == null)
										throw new DefaultMutatorException("pickRandomGene failed!");
									net.addRegulatoryInteraction(newheterodimer, uniform.nextBoolean(), randomoutput);
								}
							}
						}

					}
					
//					if it's a heterodimer...
					else if (net.getDimerisingInputsOf(dimeroutput.getElement()).size() == 2) {
						
//						this heterodimer...
						Element oldheterodimer = dimeroutput.getElement();
						
//						the other gene that forms this dimer...(not known yet)
						Element dimerpartner = null;
						
//						for this heterodimer, get the two monomers it is made of
						MyVector<OnewayInteraction> dimerinputs = net.getDimerisingInputsOf(oldheterodimer);
						
//						for each monomer...
						for (OnewayInteraction input:dimerinputs){							
//							if this monomer is not formed by the old gene then we have found the dimer-partner
							if (input.getElement() != gene) 
								dimerpartner = input.getElement();
						}
						
//						if the dimer-partner was not found...something must be wrong! jump to catch-block...
						if (dimerpartner == null)
							throw new DefaultMutatorException("dimerpartner is '-1'!! are you kidding me??");
						
//						form new heterodimer...
						Element newheterodimer = net.addElement();
						
//						mark this element as 'dimer'
						newheterodimer.setDimer(true);
						
//						get regulatory outputs of old heterodimer
						MyVector<OnewayInteraction> oldheterodimeroutputs = net.getRegulatoryOutputsOf(oldheterodimer);

//						copy regulatory outputs to new dimer
						for (OnewayInteraction output : oldheterodimeroutputs){							
//							for each regulatory output...copy it with certain probability
							double random = uniform.nextDouble();
							if (random <= p.KEEP_DIMER_OUTPUT_PROBABILITY){
								boolean regvalue = false;
								try {
									if (output.getValue() > 0) regvalue = true;
									else if (output.getValue() < 0) regvalue = false;
									else throw new Exception("not a valid value!");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								net.addRegulatoryInteraction(newheterodimer, regvalue, output.getElement());
							}else{
								if (random <= p.KEEP_DIMER_OUTPUT_PROBABILITY + p.REPLACE_LINK_PROBABILITY){
									Element randomoutput = pickRandomGene(net);
									if(randomoutput == null)
										throw new DefaultMutatorException("pickRandomGene failed!");
									net.addRegulatoryInteraction(newheterodimer, uniform.nextBoolean(), randomoutput);
								}
							}
						}
						
//						form heterodimer-interaction between the new gene and the new heterodimer
						net.addInteraction(newgene, p.HETERODIMER_MATRIX_VALUE, newheterodimer);
						
//						form heterodimer-interaction between the old dimer-partner and the new heterodimer
						net.addInteraction(dimerpartner, p.HETERODIMER_MATRIX_VALUE, newheterodimer);
					}
				}
//				everything worked out fine...
				return true;
			}
//		an exception was thrown!
		} catch (DefaultMutatorException e) {
			e.printStackTrace();
		}
//		something must have gone wrong...
		return false;
	}

	
	/**
	 * Deletes a gene from the network.
	 * If the gene forms a dimer, the dimer is removed as well.
	 * @param net The network.
	 * @param gene The gene to be deleted.
	 * @return if successful, this method returns <tt>true</tt>, otherwise <tt>false</tt>.
	 */
	protected synchronized boolean deleteGene(Network net, Element gene){
		
//		proceed if no exception is thrown...
		try {
			
//			if the number of genes is higher then the minimum number allowed... 
			if (net.getGeneIndices().size() > p.MIN_NETWORK_SIZE){
				
//				if the gene is not actually a dimer... (for debugging)
				if (!gene.isDimer()) {
					
//					get all the dimers that this gene forms...
					MyVector<OnewayInteraction> dimerisingoutputs = net.getDimerisingOutputsOf(gene);

//					go through dimers backwards (to avoid hitting index of removed dimer)
					for (int i = dimerisingoutputs.size()-1; i >= 0; i--){
//						remove dimer
						net.removeElement(dimerisingoutputs.get(i).getElement());
					}
////					as long as there are still dimerising outputs left ...
//					while(! dimerisingoutputs.isEmpty()){
////						... remove first dimer in 'dimerisingoutputs'
//						net.removeElement(dimerisingoutputs.get(0).getElement());
////						... remove first dimerising output in 'dimerisingoutputs' (to make sure the loop terminates)
//						dimerisingoutputs.remove(0);
//					}
					
//					remove gene...
					net.removeElement(gene); 
										
//					everything worked out fine...
					return true;
				}
				
//				if the gene is actually a dimer, something went wrong...
				else 
					throw new DefaultMutatorException("Hey! Who sent a dimer in here?!?");
			}
			
//		an exception was thrown!			
		} catch (DefaultMutatorException e) {
			e.printStackTrace();
		}
		
//		something must have gone wrong...
		return false;
	}
	
	
	/**
	 * Tries to find a random gene.
	 * @param net The network.
	 * @return The gene, if it exists, otherwise <tt>null</tt>.
	 */
	protected synchronized Element pickRandomGene(Network net){
		
//		get indices of all genes
		ElementVector genes = net.getGenes();
		
//		if there are no genes ... return nothing
		if (genes.size() == 0) 
			return null;
		
//		... otherwise return a random gene
		else {
			Element randomGene = genes.get(uniform.nextIntFromTo(0, genes.size() - 1));
			return randomGene;
		}
	}

	
	/**
	 * Tries to find a gene that does not form a homodimer. The gene is picked at random. Called by 'formNewHomodimer'.
	 * @param net The network.
	 * @return The gene, if it exists, otherwise <tt>null</tt>.
	 */
	protected synchronized Element pickRandomNoHomodimerGene(Network net){
		
//		get indices of all genes
		ElementVector genes = net.getGenes();
		
//		vector of outputs...
		MyVector<OnewayInteraction> dimeroutputs;
		
//		number of genes
		int gsize = genes.size();
		
//		vector that collects all indices of dimers that will be removed from 'genes'
		MyVector<Integer> toBeRemoved = new MyVector<Integer>();
		
//		go through 'genes' ...
//		TODO why did it say >= before???
		for (int i = 0; i < gsize; i++){
			
//			get dimerising outputs of gene i
			dimeroutputs = net.getDimerisingOutputsOf(genes.get(i));
			
//			to decide if gene i forms homodimer ...
			boolean formshomodimer = false;
			
//			... go through all dimerising outputs
			for (OnewayInteraction output:dimeroutputs)
				
//				if output is a homodimer ... 'formshomodimer' is true
				if (net.isHomodimer(output.getElementIndex())) 
					formshomodimer = true;
			
//			add those genes that do not form homodimer to 'remove'
			if (formshomodimer)
				toBeRemoved.add(i);
		}
		
//		finally remove those indices (the homodimer-forming genes) from 'genes'
		for (int i = 0; i < toBeRemoved.size(); i++)
			genes.remove(toBeRemoved.get(i));
		
//		if there are no genes (left) ... return nothing
		if (genes.size() == 0) 
			return null;
		
//		... otherwise return a random gene
		else {
			Element randomNonHomodimerGene = genes.get(uniform.nextIntFromTo(0, genes.size() - 1));
			return randomNonHomodimerGene;
		}
	}
	/**
	 * Tries to find a gene that does not form a homodimer. The gene is picked at random. Called by 'formNewHomodimer'.
	 * @param net The network.
	 * @return The gene, if it exists, otherwise <tt>null</tt>.
	 */
	protected synchronized Element pickRandomNonDimerisingGene(Network net){
		
//		get indices of all genes
		ElementVector genes = net.getGenes();
		
//		vector of outputs...
		MyVector<OnewayInteraction> dimeroutputs;
		
//		vector that collects all indices of dimers that will be removed from 'genes'
		ElementVector toBeRemoved = new ElementVector();
		
//		go through 'genes' ...
//		TODO why did it say >= before???
		for (Element e:genes){
			
//			get dimerising outputs of gene i
			dimeroutputs = net.getDimerisingOutputsOf(e);

//			add those genes that do not form homodimer to 'remove'
			if (dimeroutputs.size() > 0)
				toBeRemoved.add(e);
		}
		
//		finally remove those indices (the homodimer-forming genes) from 'genes'
		
		while(!toBeRemoved.isEmpty()){
			genes.remove(toBeRemoved.get(0));
			toBeRemoved.remove(0);
			
		}
//		for (int i = toBeRemoved.size(); i > 0; i++){
//			genes.remove(toBeRemoved.get(i));
//		}
		
//		if there are no genes (left) ... return nothing
		if (genes.size() == 0) 
			return null;
		
//		... otherwise return a random gene
		else {
			Element randomNonHomodimerGene = genes.get(uniform.nextIntFromTo(0, genes.size() - 1));
			return randomNonHomodimerGene;
		}
	}
	
	/**
	 * Tries to find a random regulator (TF monomer or dimer). 
	 * @param net The network.
	 * @return The regulator, if it exists, otherwise <tt>null</tt>.
	 */
	protected synchronized Element pickRandomRegulator(Network net){
		
//		get indices of all regulators (genes or dimers that have regulatory outputs)
		ElementVector regulators;
		if (p.ONLY_DIMERS_REGULATE){
			regulators = net.getRegulators();
			ElementVector tmp = new ElementVector();
			for (Element e:regulators){
				if (e.isDimer())
					tmp.add(e);
			}
			regulators = tmp;
		}
		else regulators = net.getRegulators();
		
//		if there are no regulators ... return nothing
		if (regulators.size() == 0) 
			return null;
		
//		... otherwise return a random regulator
		else {
			Element randomRegulator = regulators.get(uniform.nextIntFromTo(0, regulators.size() - 1));
			return randomRegulator;
		}
	}
	
	
	/**
	 * Tries to find a random dimer. 
	 * @param net The network.
	 * @return The dimer, if it exists, otherwise <tt>null</tt>.
	 */
	protected synchronized Element pickRandomDimer(Network net){
		
//		get indices of all dimers
		ElementVector dimers = net.getDimers();
		
//		if there are no dimers ... return nothing
		if (dimers.size() == 0) 
			return null;
		
//		... otherwise return a random dimer
		else {
			Element randomDimer = dimers.get(uniform.nextIntFromTo(0, dimers.size() - 1));
			return randomDimer;
		}
	}
	
	
	/**
	 * Tries to find any random element (gene, regulator or dimer).
	 * @param net The network.
	 * @return The element, if it exists, otherwise <tt>null</tt>.
	 */
	protected synchronized Element pickRandomElement(Network net){
//		get indices of all dimers
		ElementVector elements = net.getElementVector();
		
//		if there are no elements ... return nothing
		if (elements.size() == 0) 
			return null;
		
//		... otherwise return a random element
		else {
			Element randomElement = elements.get(uniform.nextIntFromTo(0, elements.size() - 1));
			return randomElement;
		}
	}
	
	
	protected synchronized Element pickRandomSource(Network net){
//		get indices of all dimers
		ElementVector sources = net.getSources();
		
//		if there are no elements ... return nothing
		if (sources.size() == 0) 
			return null;
		
//		... otherwise return a random element
		else {
			Element randomSource = sources.get(uniform.nextIntFromTo(0, sources.size() - 1));
			return randomSource;
		}

	}

	/**
	 * Duplicates the number of genes picked from a Poisson distribution with the mean value specified in the parameters.
	 * @see sim.Parameters
	 * @param net The network.
	 * @return '0' if not successful. otherwise the number of genes that were successfully duplicated.
	 */
	protected synchronized int duplicateGenesPoisson(Network net){
		
//		the number of genes to be duplicated
		int numGenes = 0;
		
//		keeps track of unsuccessful deletions
		int fails = 0;

//		proceed if no exception is thrown...
		try {
			
//			counter to ensure termination of while-loop after specified number of repeats
			int count = 5;
			
//			repeat loop until 'numGenes' lies between 0 and the total number of genes present  - or until count = 0
			while (count > 0 
					&& ((numGenes <= 0) || (numGenes > net.getGeneIndices().size())))
			{
				if (net.getRegulators().size() > p.MAX_REGULATORS) 
					return 0;
//				pick number of duplicated genes from poisson distribution
				numGenes = poisson_dup.nextInt();
				if (numGenes == 0) numGenes = 1;
//				TODO for debugging..
				if (p.VERBOSE)
					System.out.println("duplicated genes: "+numGenes+ " (#Genes: "+net.getGeneIndices().size()+")");
				
//				to make sure the loop terminates
				count--;
			}
			
//			TODO for debugging. should be removed... 
//			if (numGenes == 0) 
//				throw new DefaultMutatorException("number of genes duplicated = 0!");
			
//			repeat for number of genes that is supposed to be duplicated ...
			for (int j = 0; j < numGenes; j++){
				
//				... pick random gene to be duplicated
				Element gene = this.pickRandomGene(net);
				
//				TODO for debugging
				if (gene == null)
					throw new DefaultMutatorException("pickRandomGene(net) failed!!");
				
//				... try to duplicate gene - if not successful, increment 'fails'
				if (! duplicateGene(net, gene)) 
						fails++;
			}
			
//		something went wrong...
		} catch (DefaultMutatorException e) {
			
			e.printStackTrace();
		}
		
//		number of genes that were succesfully duplicated
		return numGenes - fails;
	}
	
	
	/**
	 * Duplicates every gene in the network one by one.
	 * @param net The network.
	 * @return <tt>true</tt> if all single duplications were successful. <tt>false</tt> if at least one single duplication fails.
	 */
	protected synchronized boolean duplicateGenome(Network net){
		
//		a variable for determining the success of this operation
		boolean success = true;
		
//		a vector that contains all the elements of the UNDUPLICATED network. 
//		we can't take the ElementVector of the network, 
//		because its size will be changing all the time during this operation.
		ElementVector tempVector = new ElementVector(net.getElementVector().size());
		
//		copy all elements that are not dimers(!) from net.elementVector to tempVector
		for (int i = 0; i < net.getElementVector().size(); i++)
			if(!net.get(i).isDimer()) 
				tempVector.add(net.get(i));
		
//		for each element ... try to duplicate it and report if not successful
		for (Element gene:tempVector)
			if (! duplicateGene(net, gene)) success = false;
		
//		if successful for all duplications
		if (success) 
			return true;
		
//		otherwise
		else 
			return false;
	}
	
	
	/**
	 * Deletes the number of genes picked from a Poisson distribution with the mean value specified in the parameters.
	 * @see sim.Parameters
	 * @param net
	 * @return '0' if not successful. otherwise the number of genes that were successfully deleted.
	 */
	protected synchronized int deleteGenesPoisson(Network net){
		
//		the number of genes to be deleted
		int numGenes = 0;
		
//		keeps track of unsuccessful deletions
		int fails = 0;
		
//		proceed if no exception is thrown...
		try {
			
//			counter to ensure termination of while-loop after specified number of repeats
			int count = 5;
			
//			repeat loop until 'numGenes' lies between 0 and the total number of genes present  - or until count = 0
			while (count > 0 && ((numGenes <= 0) || (numGenes > net.getGeneIndices().size()))) {
				
//				pick number of deleted genes from poisson distribution
				numGenes = poisson_del.nextInt();
				if (numGenes == 0) numGenes = 1;
//				TODO for debugging
				if(p.VERBOSE)
					System.out.println("deleted genes: "+numGenes+ " (#Genes: "+net.getGeneIndices().size()+")");
				
//				to make sure the loop terminates
				count--;
			}
			
//			TODO for debugging. should be removed...
			if (numGenes == 0)
				throw new DefaultMutatorException("number of genes deleted = 0!");
				
//			repeat for number of genes that is supposed to be deleted ...
			for (int j = 0; j < numGenes; j++){
				
//				... pick random gene to be deleted
				Element gene = this.pickRandomGene(net);
				
//				TODO for debugging. should be removed...
				if (gene == null)
					throw new DefaultMutatorException("pickRandomGene(net) failed!!");
				
//				... try to delete gene - if not successful, increment 'fails'
				if (! deleteGene(net, gene)) 
						fails++;
			}
			
//		something went wrong ...
		} catch (DefaultMutatorException e) {
			e.printStackTrace();
		}
		
//		number of genes that were successfully duplicated
		return numGenes - fails;
	}
	
	
	/**
	 * Picks a random gene and randomly changes one of its inputs.
	 * @param net The network.
	 * @return if successful, this method returns <tt>true</tt>, otherwise <tt>false</tt>.
	 */
	protected synchronized boolean changeInputOfRandomGene(Network net){
		
//		proceed if no exception is thrown...
		try {
			
//			the gene that has one of its regulatory inputs changed
			Element gene = this.pickRandomGene(net);
			
//			TODO for debugging. should be removed ...
			if (gene == null)
				throw new DefaultMutatorException("pickRandomGene(net) failed!");
									
//			get regulatory inputs for this gene
			MyVector<OnewayInteraction> inputs = net.getRegulatoryInputsOf(gene);

//			if there are no inputs, the whole operation fails
			if (inputs.isEmpty())
				return false;
			
//			pick random regulator as new input
			Element randomregulator = this.pickRandomRegulator(net);
			
//			TODO for debugging. should be removed...
			if (randomregulator == null)
				return false;
//				throw new DefaultMutatorException("pickRandomRegulator(net) returned -1!");
			
			for(OnewayInteraction input:inputs){
				int count = 0;
				while (!(count == net.size()) && input.getElement() == randomregulator){
					randomregulator = this.pickRandomRegulator(net);
					count ++;
				}
				if (input.getElement() == randomregulator)
					return false;
			}
//			select random input ...
			Element oldsource = inputs.get(uniform.nextIntFromTo(0, inputs.size()-1)).getElement();
			
//			... and remove this input
			net.removeRegulatoryInteraction(gene, oldsource);

			
//			add new input to network
			net.addRegulatoryInteraction(randomregulator, uniform.nextBoolean(), gene);
			
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
	 * Picks a gene and randomly changes one of its outputs.
	 * @param net The network.
	 * @return if successful, this method returns <tt>true</tt>, otherwise <tt>false</tt>.
	 */
	protected synchronized boolean changeOutputOfRandomRegulator(Network net){
		
//		proceed if no exception thrown
//		try {
			
//			pick random regulator (gene or dimer) 
			Element regulator = this.pickRandomRegulator(net);
			
//			TODO for debugging. remove...
			if (regulator == null)
				return false;
//				throw new DefaultMutatorException("pickRandomRegulator(net) failed!");
			
//			get regulatory outputs of regulator
			MyVector<OnewayInteraction> outputs = net.getRegulatoryOutputsOf(regulator);
			
//			if there are no outputs, the whole operation fails
			if (outputs.isEmpty()) 
				return false;
			
//			randomly pick an output to be removed
			Element target = outputs.get(uniform.nextIntFromTo(0, outputs.size()-1)).getElement();
			
//			pick random gene as the new target of the output
			Element newtarget = this.pickRandomGene(net);
			
			for(OnewayInteraction output:outputs){
				int count = 0;
				while (!(count == net.size()) && output.getElement() == newtarget){
					newtarget = this.pickRandomGene(net);
					count ++;
				}
				if (output.getElement() == newtarget)
					return false;
			}
			
//			remove the interaction between the regulator and the old target
			net.removeRegulatoryInteraction(target, regulator);

//			add interaction between the regulator and the new target
//			activation/repression chosen randomly
			net.addRegulatoryInteraction(regulator, uniform.nextBoolean(), newtarget);
			
//			operation successful
			return true;
			
//		something went wrong ..
//		} catch (DefaultMutatorException e) {
//			e.printStackTrace();
//		}
		
//		operation unsuccessful
//		return false;
	}
	

/*
 * TODO
 * DISCUSSION: should we distinguish between 'regulator' and 'gene' as the source of a regulatory input? 
 * This method models point mutations in either the promotor of a gene OR the DBD-domain of the regulator. 
 * So we could have a dimer with no reg. outputs.  - Yes,this is called isolated dimer.
 */ 	
	
	/**
	 * Picks one regulator at random and deletes one of its outputs. Point mutation in DBD in TF or promotor of target gene.
	 * @param net The network.
	 * @return if successful, this method returns <tt>true</tt>, otherwise <tt>false</tt>.
	 */
	protected synchronized boolean deleteRandomRegulatoryInteraction(Network net){
		
//		preceed if no exception thrown
		try {
			
//			pick random regulator
			Element regulator = this.pickRandomRegulator(net);
			
//			TODO for debugging. remove...
			if (regulator == null)
				return false;
//				throw new DefaultMutatorException("pickRandomRegulator(net) failed!");
			
//			get its outputs
			MyVector<OnewayInteraction> outputs = net.getRegulatoryOutputsOf(regulator);
			
			if (outputs.isEmpty())
			{
				return false;
			}
			
//			randomly pick one output
			Element target = outputs.get(uniform.nextIntFromTo(0, outputs.size() - 1)).getElement();
//			System.out.println(target.getInteractionMatrix());
//			System.out.println(target.getInteractionMatrix().getElements());
//			System.out.println(net.getElementVector());
//			TODO for debugging. remove...				
			if (target == null)
				throw new DefaultMutatorException("pickRandomGene(net) failed!");
			
//			remove interaction
			net.removeRegulatoryInteraction(target, regulator);
			
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
	 * Picks a random element and adds a random regulatory output to it.
	 * @param net The network.
	 * @return if successful, this method returns <tt>true</tt>, otherwise <tt>false</tt>.
	 */
	protected synchronized boolean formNewRandomRegulatoryInteraction(Network net){
		
//		proceed if no exception thrown
		try {
			
//			pick random element (all elements can potentially have regulatory output)
			Element element;
			if (p.ONLY_DIMERS_REGULATE)
				element = pickRandomDimer(net);
			else
				element = pickRandomElement(net);
			
//			pick random gene to be the target of the new interaction
			Element target = this.pickRandomGene(net);
			
//			TODO for debugging. remove...
			if (element == null)
				return false;
//				throw new DefaultMutatorException("pickRandomRegulator(net) failed!");
			
//			TODO for debugging. remove...
			if (target == null)
				throw new DefaultMutatorException("pickRandomGene(net) failed!");
			
//			remember why you did this??
//			MyVector<OnewayInteraction> inputs = net.getRegulatoryInputsOf(target);
//			
//			for(OnewayInteraction input:inputs){
//				int count = 0;
//				while (!(count == net.size()) && input.getElement() == element){
//					element = this.pickRandomElement(net);
//					count ++;
//				}
//				if (input.getElement() == element)
//					return false;
//			}
			
//			add new interaction between the regulator and its new target
			net.addRegulatoryInteraction(element, uniform.nextBoolean(), target);
			
//			operation successful
			return true;
			
//		something went wrong ...
		} catch (DefaultMutatorException e) {
			e.printStackTrace();
		}
		
//		operation unsuccessful
		return false;
	}
	
	protected synchronized boolean swapRegulatorySign(Network net){
		Element regulator = pickRandomRegulator(net);
		
		if (regulator == null)
			return false;
		MyVector<OnewayInteraction> regOuts = regulator.getRegOutputs();
		int randomIndex = uniform.nextIntFromTo(0, regOuts.size()-1);
		byte value = regOuts.get(randomIndex).getValue();
		Element target = regOuts.get(randomIndex).getElement();
		byte newValue = (byte) (value * (-1));
		
		net.getInteractions().set(net.getIndexOf(target), net.getIndexOf(regulator), newValue);
		
		return true;
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
//			Element gene = this.pickRandomNoHomodimerGene(net);
			Element gene = pickRandomNonDimerisingGene(net);
			
//			TODO for debugging ... replace throw-statement with 'return false;' (in case no suitable element exists.)
			if (gene == null){
				return false;
//				net.getDataCollector().printStats();
//				throw new DefaultMutatorException("pickRandomNonDimerisingGene(net) failed!");
			}
			if (!gene.getDimOutputs().isEmpty()){
				return false;
//				for (OnewayInteraction dimer:gene.getDimOutputs()){
//					System.out.println("dimer inputs: "+dimer.getElement().getDimInputs().size());
//				}
//				net.getDataCollector().printStats();
//				throw new DefaultMutatorException("gene forms ("+gene.getDimOutputs().size()+") dimer already !");
				
			}

			
//			the regulatory outputs of the new dimer
			
			Vector<OnewayInteraction> targets = new Vector<OnewayInteraction>();
			Vector<OnewayInteraction> regOuts = gene.getRegOutputs();
			for (OnewayInteraction out:regOuts){
				if (uniform.nextDouble() <= p.KEEP_DIMER_OUTPUT_PROBABILITY){
					targets.add(out);
				}
				else{
					Element randomtarget = this.pickRandomGene(net);
					byte randomvalue = 0;
					if (uniform.nextBoolean())randomvalue= 1;
					else randomvalue = -1;
					targets.add(new OnewayInteraction(randomvalue ,randomtarget));
//					TODO for debugging ...
					if (randomtarget == null)
						throw new DefaultMutatorException("pickRandomGene(net) returned -1!");
//					TODO for debugging ...
					if (randomtarget == null)
						throw new DefaultMutatorException("pickRandomGene(net) returned -1!");

				}
				
			}
			
//			remove original outputs of gene
			while(!regOuts.isEmpty()){
				net.removeRegulatoryInteraction(regOuts.get(0).getElement(), gene);
				regOuts.remove(0);
			}
			
//			create new element
			Element newdimer = net.addElement();
			
//			mark element as 'dimer'
			newdimer.setDimer(true);
			
//			add new homodimer-interaction between gene and new dimer
			net.addInteraction(gene, p.HOMODIMER_MATRIX_VALUE, newdimer);
			
			for(OnewayInteraction target:targets){
//				add regulatory interaction between dimer and target
				net.addInteraction(newdimer, target.getValue(), target.getElement());
			}
			
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
			Element geneOfNewDimer = this.pickRandomNonDimerisingGene(net);
			
//			TODO for debugging ...
			if (geneOfNewDimer == null || dimer == null){
				return false;
//				net.getDataCollector().printStats();
//				throw new DefaultMutatorException("pickRandomGene(net) or pickRandomDimer(net) returned -1!");
			}
			
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
			
			ElementVector targets = new ElementVector();
			Vector<OnewayInteraction> regOuts = dimer.getRegOutputs();
			for (OnewayInteraction out:regOuts){
				if (uniform.nextDouble() <= p.KEEP_DIMER_OUTPUT_PROBABILITY){
					targets.add(out.getElement());
				}
				else{
					Element randomtarget = this.pickRandomGene(net);
					targets.add(randomtarget);
//					TODO for debugging ...
					if (randomtarget == null)
						throw new DefaultMutatorException("pickRandomGene(net) returned -1!");
//					TODO for debugging ...
					if (randomtarget == null)
						throw new DefaultMutatorException("pickRandomGene(net) returned -1!");

				}
			}
////			TODO debugging...
//			if (target == null)
//				throw new DefaultMutatorException("pickRandomGene(net) failed!");
			
//			add dimer-interaction between gene that forms old dimer and the new heterodimer
			net.addInteraction(geneOfOldDimer, p.HETERODIMER_MATRIX_VALUE, newdimer);
			
//			add dimer interaction between gene of new heterodimer and the dimer itself
			net.addInteraction(geneOfNewDimer, p.HETERODIMER_MATRIX_VALUE, newdimer);
			
			for(Element target:targets){
//				add regulatory interaction (OF RANDOM VALUE) between new dimer and its target
				net.addRegulatoryInteraction(newdimer, uniform.nextBoolean(), target); 
			}
			
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
	 * Tries to delete a random dimer, i.e. it models a mutation in the dimerising domain.
	 * @param net The network.
	 * @return <tt>true</tt> if successful, <tt>false</tt> if no dimer found.
	 */
	protected synchronized boolean removeRandomDimer(Network net){
		
//		pick dimer to be removed
		Element dimer = this.pickRandomDimer(net);
		
//		if no dimer present ... abort.
		if (dimer == null) 
			return false;
		
//		otherwise, remove dimer from network
		net.removeElement(dimer);
		
//		operation successful
		return true;
	}
	
	
	/** 
	 * Main method from which several protected methods are called (with the probabilities specified in the <tt>Parameters</tt> object) which actually do the mutations.
	 * @see sim.Parameters
	 * @see sim.operation.Mutator#mutatePopulation(sim.datatype.MyVector)
	 */
	public synchronized void mutatePopulation(Parameters p, NetworkVector pop) {
		
		if (p.MINIMAL_OUTPUT) System.out.println("CALLED: DefaultMutator#mutatePopulation(Parameters p, NetworkVector pop)");
//		int updateCount = 0;
		
		Network net;
		boolean doGeneDynamics;
		boolean doLinkDynamics;
		boolean doDimerDynamics;
		
		try {
			boolean success = false;
			
//			iterate through all networks in population...
			for (int i = 0; i < pop.size(); i++){
				int mutCount = 0;
				doGeneDynamics = false;
				doLinkDynamics = false;
				doDimerDynamics = false;
				
				
//				current network
				net = pop.get(i); 
//				net.getDataCollector().getMutationHistory().add(process.getGeneration(), new MyVector<Integer>());
				
//				allow only one type of mutation per network and generation. if two mutations would occur, randomly pick between them.
				double dynamicsrate;
//				double dynamicsrate = uniform.nextDouble();
				Vector<Integer> mutations = new Vector<Integer>(3);
				mutations.add(0);
				mutations.add(1);
				mutations.add(2);
				Vector<Integer> mutationOrder = new Vector<Integer>(3);
				for (int m = 0; m < 3; m++){
					int r = uniform.nextIntFromTo(0, mutations.size() -1);
					mutationOrder.add(mutations.get(r));
//					System.out.println("-->"+mutations.get(r));
					mutations.remove(r);
				}
//				System.out.println("mutation order " +mutationOrder);
				for (int x = 0; x < mutationOrder.size(); x++){
					dynamicsrate = uniform.nextDouble();
					switch(mutationOrder.get(x)){
					case 0: {
						if (dynamicsrate <= p.GENE_DYNAMICS_RATE){
							doGeneDynamics = true;
							doLinkDynamics = false;
							doDimerDynamics = false;
						}
						break;
					}
					case 1: {
						if (dynamicsrate <= p.LINK_DYNAMICS_RATE){
							doLinkDynamics = true;
							doGeneDynamics = false;
							doDimerDynamics = false;
						}
						break;
					}
					case 2: {
						if (dynamicsrate <= p.DIMER_DYNAMICS_RATE){
							doDimerDynamics = true;
							doGeneDynamics = false;
							doLinkDynamics = false;
						}
						break;
					}
					default:{}
					}
				}
//				if (dynamicsrate <= p.GENE_DYNAMICS_RATE)
//					doGeneDynamics = true;
//				dynamicsrate = uniform.nextDouble();
//				if (dynamicsrate <= p.LINK_DYNAMICS_RATE)
//					doLinkDynamics = true;
//				dynamicsrate = uniform.nextDouble();
//				if (dynamicsrate <= p.DIMER_DYNAMICS_RATE)
//					doDimerDynamics = true;
			
				
				
//				GENE DYNAMICS
				if (doGeneDynamics){
					double genedynamicsrate = uniform.nextDouble();
					if (!((p.GENE_DUPLICATION_RATE
							+ p.GENOME_DUPLICATION_RATE
							+ p.GENE_DELETION_RATE) 
							== 1))
						throw new DefaultMutatorException("p.GENE_DUPLICATION_RATE+p.GENOME_DUPLICATION_RATE+p.GENE_DELETION_RATE must be = 1!!");
//					perform gene duplications
					if (genedynamicsrate <= p.GENE_DUPLICATION_RATE){ 
						int n = duplicateGenesPoisson(net);
						if(p.VERBOSE) System.out.println("duplicateGenesPoisson(net)"+n +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(10+n);
						if (success) mutCount++;
					}
//					perform whole genome duplication
					if (genedynamicsrate > p.GENE_DUPLICATION_RATE 
							&& genedynamicsrate <= p.GENOME_DUPLICATION_RATE + p.GENE_DUPLICATION_RATE){
						duplicateGenome(net);
						if(p.VERBOSE) System.out.println("duplicateGenome(net)!!" +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(10+net.size());
						if (success) mutCount++;
					}
//					perform gene deletions
					if (genedynamicsrate > p.GENE_DUPLICATION_RATE + p.GENOME_DUPLICATION_RATE 
							&& genedynamicsrate <= (p.GENE_DELETION_RATE + p.GENOME_DUPLICATION_RATE + p.GENE_DUPLICATION_RATE)
							&& net.size() > p.MIN_NETWORK_SIZE){
						int n = deleteGenesPoisson(net);
						if(p.VERBOSE) System.out.println("deleteGenesPoisson(net)"+n +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(-(10+n));
						if (success) mutCount++;
					}
					
				}
				
//				LINK DYNAMICS
				if (doLinkDynamics){
					double linkdynamicsrate = uniform.nextDouble();
//					if (! ((p.INPUT_CHANGE_RATE 
//							+ p.OUTPUT_CHANGE_RATE*100 
//							+ p.LINK_DELETION_RATE*100 
//							+ p.LINK_FORMATION_RATE*100) 
//							== 100)){
//						System.out.println((p.INPUT_CHANGE_RATE 
//							+ p.OUTPUT_CHANGE_RATE 
//							+ p.LINK_DELETION_RATE 
//							+ p.LINK_FORMATION_RATE));
//						throw new DefaultMutatorException("p.INPUT_CHANGE_RATE + p.OUTPUT_CHANGE_RATE + p.LINK_DELETION_RATE + p.LINK_FORMATION_RATE must be = 1!!");
//					}
						
						
					
//					substitute one input with another
					if (linkdynamicsrate <= p.INPUT_CHANGE_RATE){ 
						success = changeInputOfRandomGene(net);
						if(p.VERBOSE) System.out.println("changeInputOfRandomGene(net)"+success +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(1);
						if (success) mutCount++;
					}
//					substitute one output with another
					if (linkdynamicsrate > p.INPUT_CHANGE_RATE 
							&& linkdynamicsrate <= p.INPUT_CHANGE_RATE + p.OUTPUT_CHANGE_RATE){
						success = changeOutputOfRandomRegulator(net);
						if(p.VERBOSE) System.out.println("changeOutputOfRandomRegulator(net)"+success +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(2);
						if (success) mutCount++;
					}
//					remove one regulatory interaction
					if (linkdynamicsrate > p.INPUT_CHANGE_RATE + p.OUTPUT_CHANGE_RATE
							&& linkdynamicsrate <= p.INPUT_CHANGE_RATE + p.OUTPUT_CHANGE_RATE + p.LINK_DELETION_RATE){
						success = deleteRandomRegulatoryInteraction(net);
						if (success) linkDelCount++;
						if(p.VERBOSE) System.out.println("deleteRandomRegulatoryInteraction(net)"+success +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(3);
						if (success) mutCount++;
					}
//					form one new regulatory interaction
					if (linkdynamicsrate > p.INPUT_CHANGE_RATE + p.OUTPUT_CHANGE_RATE + p.LINK_DELETION_RATE
							&& linkdynamicsrate <= p.INPUT_CHANGE_RATE + p.OUTPUT_CHANGE_RATE + p.LINK_DELETION_RATE + p.LINK_FORMATION_RATE){
						success = formNewRandomRegulatoryInteraction(net);
						if (success) linkAddCount++;
						if(p.VERBOSE) System.out.println("formNewRandomRegulatoryInteraction(net)"+success +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(4);
						if (success) mutCount++;
					}
//					switch link between activation and inhibition
					if (linkdynamicsrate > p.INPUT_CHANGE_RATE + p.OUTPUT_CHANGE_RATE + p.LINK_DELETION_RATE + p.LINK_FORMATION_RATE
							&& linkdynamicsrate <= p.INPUT_CHANGE_RATE + p.OUTPUT_CHANGE_RATE + p.LINK_DELETION_RATE + p.LINK_FORMATION_RATE + p.SWAP_LINK_SIGN_RATE){
						success = swapRegulatorySign(net);
						if(p.VERBOSE) System.out.println("swapRegulatorySign(net)"+success +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(5);
						if (success) mutCount++;
					}
				}
				
				if(p.VERBOSE) System.out.println("LINK -DEL:"+linkDelCount+" -ADD:"+linkAddCount);
				
//				DIMER DYNAMICS
				if (doDimerDynamics){
					double dimerdynamicsrate = uniform.nextDouble();
					if (! (p.NEW_HOMODIMER_RATE
							+ p.NEW_HETERODIMER_RATE
							+ p.LOSS_OF_DIMER_RATE
							==1))
						throw new DefaultMutatorException("p.NEW_HOMODIMER_RATE + p.NEW_HETERODIMER_RATE + p.NEW_HETERODIMER_RATE + p.LOSS_OF_DIMER_RATE must be = 1!!");
						
//					form completely new homodimer (= new dimerising domain)
					if (dimerdynamicsrate <= p.NEW_HOMODIMER_RATE){
						success = formNewHomodimer(net);
						if(p.VERBOSE) System.out.println("formNewHomodimer(net)"+success +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(6);
						if (success) mutCount++;
					}
//					form new dimer from existing dimer (= spread of dimerising domain)
					if (dimerdynamicsrate > p.NEW_HOMODIMER_RATE
							&& dimerdynamicsrate <= p.NEW_HOMODIMER_RATE + p.NEW_HETERODIMER_RATE){
						success = formNewHeterodimer(net);
						if(p.VERBOSE) System.out.println("formNewHeterodimer(net)"+success +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(7);
						if (success) mutCount++;
					}
//					remove one dimer (loss of dimerising domain in transcription factor gene)
					if (dimerdynamicsrate > p.NEW_HOMODIMER_RATE + p.NEW_HETERODIMER_RATE
							&& dimerdynamicsrate <= p.NEW_HOMODIMER_RATE + p.NEW_HETERODIMER_RATE + p.LOSS_OF_DIMER_RATE){
						success = removeRandomDimer(net);
						if(p.VERBOSE) System.out.println("removeRandomDimer(net)"+success +" - net:"+net.getID()+" ("+i+") - generation: "+process.getGeneration());
						net.getDataCollector().setCurrentMutation(8);
						if (success) mutCount++;
					}

				}
				
//				System.out.println("mutations for network "+net+" in generation "+process.getGeneration()+": "+mutCount);
//				System.out.println(net.getDataCollector().getMutationsStringAt(process.getGeneration()));
//				System.out.println(net.getDataCollector().getNetwork());
//				System.out.println();
				
				if (doGeneDynamics || doLinkDynamics || doDimerDynamics) {
					//				properties of network were changed, therefore need to generate initial states again
//					updateCount++;
					if (p.PRINT_DURATIONS){
						long preInitStates = new Date().getTime();
						net.generateInitialStates();
						net.getDataCollector().setAttractorsOld(true);
						long postInitStates = new Date().getTime();
						long sumInitStates = (postInitStates-preInitStates);
						System.out.println("\tnet.generateInitialStates(): "+sumInitStates+"ms");
					}else{
						net.generateInitialStates();
						net.getDataCollector().setAttractorsOld(true);
						
					}
						
				}
				
				
			}
			
		} catch (DefaultMutatorException e) {
			e.printStackTrace();
		}
//		System.out.println(updateCount);
	}
	
	
	/**
	 * For testing and debugging.
	 * @param args
	 */
	public static void main(String[] args){
		
//		Parameters p = new Parameters();
//		NetworkGenerator gen = new RandomNetworkGenerator(p);
//		Network net = gen.generateNewNetwork();
//		NetworkVector v = new NetworkVector(1);
//		v.add(net);
//		Outputter out = new Outputter();
//		DefaultMutator mut = new DefaultMutator(p);
//		
//		out.display(net);
//		
//		int n = 5001;
//		for (int i = 0; i <  n; i++){
//			System.out.println("loop = "+i+" net size: " + net.size());
//			System.out.println("genes("+net.getGeneIndices().size()+"):dimers("+net.getDimerIndices().size()+")");
//			
//			mut.mutatePopulation(p, v);
//			if (i%100 == 0) {
//				out.update(net);
//				try {
//					
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					
//					e.printStackTrace();
//				}
//			}
//		}
		System.out.println("FINISHED!");

	}
}
