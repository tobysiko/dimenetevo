package sim.datatype;

import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.operation.AbstractStatesGenerator;
import sim.operation.NetworkDataCollector;

public interface Network {
	public abstract Element addElement();

	public abstract int addElement(Element elem);

	// public abstract void addInteraction(int source, boolean value, int
	// target);

	// public abstract void addInteraction(int source, byte value, int target);
	public void setInteractions(InteractionMatrix interactions) ;
	
	public abstract void addInteraction(Element source, byte value,
			Element target);
	public String printNotConsidered();
	public abstract void addRegulatoryInteraction(Element source,
			boolean value, Element target);
	public MyVector<Integer> getConsideredIndices() ;

	public void setConsideredIndices(MyVector<Integer> consideredIndices) ;
	public abstract Object clone() throws CloneNotSupportedException;

	public abstract boolean formsDimer(int gene);

	public void generateInitialStates();

	public abstract Element get(int index);

	public abstract NetworkDataCollector getDataCollector();
	public abstract void setDataCollector(NetworkDataCollector collector);
	public abstract MyVector<Integer> getDimerIndices();

	// /**
	// * @param dimer
	// * @return
	// * @deprecated
	// */
	// public abstract MyVector<OnewayInteraction> getDimerisingInputsOf(int
	// dimer);

	public abstract MyVector<OnewayInteraction> getDimerisingInputsOf(
			Element dimer);

	// /**
	// * @param gene
	// * @return
	// * @deprecated
	// */
	// public abstract MyVector<OnewayInteraction> getDimerisingOutputsOf(int
	// gene);
	public abstract MyVector<OnewayInteraction> getDimerisingOutputsOf(
			Element gene);

	public abstract ElementVector getDimers();

	public abstract MyVector<Integer> getElementIndices();

	public abstract ElementVector getElementVector();
	public abstract void setElementVector(ElementVector elementVector);
	public abstract MyVector<Integer> getGeneIndices();

	public abstract ElementVector getGenes();

	public abstract int getID();

	public abstract EvolutionaryProcess getProcess();
	
	public abstract void setProcess(EvolutionaryProcess process);
	
	public abstract int getIndexOf(Element e);

	public TimeStepSeries getInitialStates();

	public abstract InteractionMatrix getInteractions();

	public abstract ElementVector getIsolated();

	public abstract ElementVector getNonIsolatedGenes();

	public int getNumberOfStates();

	public abstract Parameters getParameters();

	public abstract MyVector<Integer> getRegulatorIndices();

	// public abstract ElementVector getGenes();
	// public abstract ElementVector getDimers();
	public abstract ElementVector getRegulators();

	// /**
	// * @param gene
	// * @return
	// * @deprecated
	// */
	// public abstract MyVector<OnewayInteraction> getRegulatoryInputsOf(int
	// gene);
	public abstract MyVector<OnewayInteraction> getRegulatoryInputsOf(
			Element gene);

	// /**
	// * @param regulator
	// * @return
	// * @deprecated
	// */
	// public abstract MyVector<OnewayInteraction> getRegulatoryOutputsOf(
	// int regulator);
	public abstract MyVector<OnewayInteraction> getRegulatoryOutputsOf(
			Element regulator);

	public abstract ElementVector getSinks();

	// public abstract ElementVector getElements();
	public abstract ElementVector getSources();

	public boolean[] getState(int index);

	public AbstractStatesGenerator getStateGen();

	public abstract boolean isDimer(int element);

	public abstract boolean isHeterodimer(int element);

	public abstract boolean isHomodimer(int element);

	public abstract String printComposition();

	// public abstract void removeElement(int index);

	public abstract String printDimOutputNumbers();

	public abstract String printInputNumbers();

	public abstract String printRegOutputNumbers();

	public abstract void removeElement(Element e);

	public abstract void removeRegulatoryInteraction(Element target, Element source);

	public void setInitialStates(TimeStepSeries initialStates);

	public abstract void setParameters(Parameters p);

	public void setStateGen(AbstractStatesGenerator stateGen);
	
	public abstract void setToState(boolean[] state);
	
	public abstract int size();
	
	public boolean[] getNotConsidered();

	public void setNotConsidered(boolean[] notConsidered);

}
