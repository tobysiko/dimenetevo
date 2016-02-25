/**
 * 
 */
package sim;

import sim.datatype.AbstractSimulationState;
import sim.datatype.NetworkVector;
import sim.operation.AbstractStatesGenerator;
import sim.operation.FileManager;
import sim.operation.FitnessFunction;
import sim.operation.NetworkGenerator;
import sim.operation.ProcessDataCollector;
import sim.operation.Selector;
import sim.operation.Simulator;

/**
 * @author sikosek
 * 
 */
public abstract class EvolutionaryProcess extends Thread{

	protected ProcessDataCollector collector;

	protected FileManager fileManager;

	protected int generation;

	protected NetworkGenerator generator;

	protected Parameters parameters;

	protected NetworkVector population;

	protected Selector selector;
	
	protected Simulator simulator;
	
	protected AbstractStatesGenerator stateGen;
	
	protected AbstractSimulationState simState;
	
	protected FitnessFunction fitnessFunction;

	protected boolean paused;
	
	protected long processID;

	public EvolutionaryProcess() {
		parameters = new Parameters(this);
		generation = 0;
		population = new NetworkVector();
		generator = parameters.getDefaultGenerator();
		collector = parameters.getDefaultProcessCollector(this);
		fileManager = parameters.getDefaultFileManager();
		fitnessFunction = parameters.getDefaultFitnessFunction();
		selector = parameters.getDefaultSelector();
		simulator = parameters.getDefaultSimulator();
		stateGen = parameters.getDefaultStatesGenerator();
		processID = this.getId();
	}

	public abstract void run();
	
	public ProcessDataCollector getCollector() {
		return collector;
	}

	public FileManager getFileManager() {
		return fileManager;
	}

	public synchronized int getGeneration() {
		return generation;
	}

	public NetworkGenerator getGenerator() {
		return generator;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public Parameters getParm() {
		return parameters;
	}

	public synchronized NetworkVector getPopulation() {
		return population;
	}

	

	public Selector getSelector() {
		return selector;
	}

	public Selector getSelector1() {
		return selector;
	}

	public Simulator getSimulator() {
		return simulator;
	}

	public AbstractSimulationState getSimState() {
		return simState;
	}

	public AbstractStatesGenerator getStateGen() {
		return stateGen;
	}

	public boolean isPaused() {
		return paused;
	}

	abstract public void pauseProcess();

	abstract public void resumeProcess();

	public void setCollector(ProcessDataCollector collector) {
		this.collector = collector;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public synchronized void setGeneration(int generation) {
		this.generation = generation;
	}

	public void setGenerator(NetworkGenerator generator) {
		this.generator = generator;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	public void setParm(Parameters parm) {
		this.parameters = parm;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public synchronized void setPopulation(NetworkVector population) {
		this.population = population;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	public void setSimState(AbstractSimulationState state) {
		this.simState = state;
	}

	public void setStateGen(AbstractStatesGenerator stateGen) {
		this.stateGen = stateGen;
	}

	abstract public void startProcess();

	abstract public void startFrom(AbstractSimulationState state);

	abstract public AbstractSimulationState terminateProcess();

	public FitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

	public void setFitnessFunction(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

//	public int getProcessID() {
//		return processID;
//	}
//
//	public void setProcessID(int processID) {
//		this.processID = processID;
//	}
	
	
}
