/**
 * 
 */
package sim;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.graph.RandomGraphNetworkGenerator;
import sim.graph.TestGraphNetworkGenerator;
import sim.operation.AbstractStatesGenerator;
import sim.operation.AdvancedFitnessFunction;
import sim.operation.AllStatesGenerator;
import sim.operation.ClusteringNetworkCollector;
import sim.operation.DefaultFileManager;
import sim.operation.DefaultFitnessFunction;
import sim.operation.DefaultMutator;
import sim.operation.DefaultNetworkCollector;
import sim.operation.DefaultProcessCollector;
import sim.operation.DefaultSimulator;
import sim.operation.FileManager;
import sim.operation.FitnessFunction;
import sim.operation.Mutator;
import sim.operation.NetworkDataCollector;
import sim.operation.NetworkGenerator;
import sim.operation.NewFitnessFunction;
import sim.operation.ProcessDataCollector;
import sim.operation.RandomDimerGraphNetworkGenerator;
import sim.operation.RandomDimerNetworkGenerator;
import sim.operation.RandomNetworkGenerator;
import sim.operation.RandomSinkNetworkGenerator;
import sim.operation.Selector;
import sim.operation.SensibleStatesGenerator;
import sim.operation.SimpleSelector;
import sim.operation.Simulator;
import sim.operation.SinkMutator;
import sim.operation.SinksDataCollector;
import sim.operation.SinksFitnessFunction;
import sim.operation.SinksSimulator;
import sim.operation.SinksStatesGenerator;
import sim.operation.SourcesSimulator;
import sim.operation.SourcesStatesGenerator;
import sim.operation.TestNetworkGenerator;
import sim.operation.function.AndRule;
import sim.operation.function.BooleanFunction;
import sim.operation.function.DefaultOffRule;
import sim.operation.function.DefaultOnRule;
import sim.operation.function.LastingDimerRule;
import sim.operation.function.LenientRepressorWinsRule;
import sim.operation.function.PoissonLastingDimerRule;
import sim.operation.function.SimpleAndRule;
import sim.operation.function.SimpleDimerRule;
import sim.operation.function.StrengthInNumbersRule;
import sim.operation.function.StrictRepressorsWinRule;
import sim.operation.function.VaryingPoissonLastingDimerRule;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

/** 
 * Stores all parameters used in the program.
 * @author sikosek
 */
public class Parameters implements Serializable, Cloneable{
	
	private EvolutionaryProcess process;
	private static final long serialVersionUID = 1L;
	public static HashSet<String> fieldNameSet;
	
//	general
	/**Number of networks in the population.*/					public 		 int 	POPULATION_SIZE 		= 100; // should match FITTEST_PROPORTION
	/**Number of generations in the evolutionary process.*/		public 		 int 	GENERATIONS 			= 20;
	/**Number of networks present at beginning of process.*/	public 		 int 	INITIAL_NETWORK_SIZE 	= 5;
	/**Number of networks may not decrease below this value.*/	public 		 int 	MIN_NETWORK_SIZE 		= 5;
	/**Value representing heterodimer in interaction matrix.*/	public final byte HETERODIMER_MATRIX_VALUE	= 3;
	/**Value representing homodimer in interaction matrix.*/	public final byte 	HOMODIMER_MATRIX_VALUE	= 4;
	/**# of timesteps a dimer will be present after expression*/public 	 	 int 	STABLE_DIMER_TIME 		= 2;
	/**Will allow only dimers to regulate.*/ 					public 		 boolean ONLY_DIMERS_REGULATE 	= false;
	/**Run processes in parallel threads, or in sequence?*/ 	public  	 boolean USE_PARALLEL_PROCESSES = false;
	/**Use attractor profiles that count transitions. */		public		 boolean ATTRACTOR_PROFILE_TRANS= false;
	/**Turn dimers on in the same timestep as their monomers.*/	public 		 boolean DIMERS_ON_IMMEDIATELY	= true;
	/**Default network implementation.
	 * <p>0: MatrixNetwork
	 * <p>1: GraphNetwork*/ 									public 	     int 	DEFAULT_NETWORK 		= 0;
	/**Default Boolean rule for non-dimers.
	 * <p>0: SimpleAndRule
	 * <p>1: AndRule
	 * <p>2: LenientRepressorWinsRule
	 * <p>3: StrictRepressorWinsRule
	 * <p>4: DefaultOnRule
	 * <p>5: DefaultOffRule
	 * <p>6: StrengthInNumbersRule
	 * <p>7: - random rule -*/									public 		 int 	DEFAULT_GENE_RULE		= 3;
	 /**Default Boolean rule for dimers.
	  * <p>0: SimpleDimerRule
	  * <p>1: LastingDimerRule
	  * <p>2: PoissonLastingDimerRule
	  * <p>3: VaryingPoissonLastingDimerRule*/					public 		 int 	DEFAULT_DIMER_RULE 		= 1;
	/**Default network generator.
	 * <p>0: RandomNetworkGenerator
	 * <p>1: TestNetworkGenerator
	 * <p>2: RandomGraphNetworkGenerator
	 * <p>3: TestGraphNetworkGenerator
	 * <p>4: RandomSinkNetworkGenerator
	 * <p>5: RandomDimerNetworkGenerator
	 * <p>6: RandomDimerGraphNetworkGenerator*/ 				public 		 int 	DEFAULT_GENERATOR 		= 0;
	/**Default simulator.
	 * <p>0: DefaultSimulator
	 * <p>1: SourcesSimulator
	 * <p>2: SinksSimulator*/ 									public final int 	DEFAULT_SIMULATOR 		= 0;
	 /**Default states generator.
	 * <p>0: AllStatesGenerator
	 * <p>1: SourcesStatesGenerator
	 * <p>2: SensibleStatesGenerator
	 * <p>3: SinksStatesGenerator*/ 							public final int 	DEFAULT_STATES_GEN 		= 2;
	/**Default mutator.
	 * <p>0: DefaultMutator
	 * <p>1: SinkMutator*/ 										public final int 	DEFAULT_MUTATOR 		= 0;
	/**Default selector.
	 * <p>0: SimpleSelector*/ 									public final int 	DEFAULT_SELECTOR 		= 0;
	 /**Default fitness function.
	  * <p>0: DefaultFitnessFunction
	  * <p>1: AdvancedFitnessFunction
	  * <p>2: SinksFitnessFunction
	  * <p>3: NewFitnessFunction*/								public 		 int 	DEFAULT_FITNESS_FUNCTION= 3;
	/**Default file manager.
	 * <p>0: DefaultFileManager*/ 								public final int 	DEFAULT_FILE_MANAGER 	= 0;
	/**Default process data collector.
	 * <p>0: DefaultProcessCollector*/ 							public final int DEFAULT_PROCESS_COLLECTOR 	= 0;
	/**Default network data collector.
	 * <p>0: DefaultNetworkCollector
	 * <p>1: ClusteringNetworkCollector
	 * <p>2: SinksDataCollector*/ 								public 		 int DEFAULT_NETWORK_COLLECTOR 	= 1;
	
//	for text output
	/**Maximum of text output to <tt>System.out</tt>.*/			public final boolean VERBOSE				= false;
	/**Minimum of text output to <tt>System.out</tt>.*/			public final boolean MINIMAL_OUTPUT			= false;	
	/**For printing stats for each network.*/					public final boolean PRINT_NET_STATS 		= false;
	/**For printing stats for each generation.*/				public final boolean PRINT_PROCESS_STATS	= false;
	/**Columns of networks in pop. stats output.*/				public final int	 POPSTAT_COLUMNS		= 5;
	/**For printing durations of diff. parts of simulation.*/	public final boolean PRINT_DURATIONS		= false;
																public final boolean SHOW_FILE_OPERATIONS	= false;
//	for DefaultMutator
	/**Probability of change in number of genes.*/				public 		 double GENE_DYNAMICS_RATE		= 0.01;
	/**Probability of change in interactions between genes.*/	public 		 double LINK_DYNAMICS_RATE		= 0.1;
	/**Probability of change in TF dimerisation.*/				public 		 double DIMER_DYNAMICS_RATE		= 0.1;
//	in case of gene dynamics ...
	/**Probability of genome duplication.*/						public 		 double GENOME_DUPLICATION_RATE	= 0.01;
	/**Probability of single gene deletion.*/					public 		 double GENE_DELETION_RATE 		= 0.3;
	/**Probability of single gene duplication.*/				public 		 double GENE_DUPLICATION_RATE 	= 1 - (GENOME_DUPLICATION_RATE+GENE_DELETION_RATE);
	/**Mean of Poission distribution (# duplicated genes).*/	public 		 double DUPL_POISSON_MEAN		= 1.0;
	/**Mean of Poission distribution (# deleted genes).*/		public 		 double DEL_POISSON_MEAN 		= 4.0;
	/**Probability of keeping an input after gene duplic.*/		public 		 double KEEP_INPUT_PROBABILITY	= 0.7;
	/**Probability of keeping an output after gene duplic.*/	public 		 double KEEP_OUTPUT_PROBABILITY	= 0.7;
	/**Probability of keeping output of dimer after gene dupl.*/public 		 double KEEP_DIMER_OUTPUT_PROBABILITY	= 0.7;
	/**Prob. of replacing in/output with random one.*/			public 		 double REPLACE_LINK_PROBABILITY= 0.2;
	/**Above this number, no gene duplications will occur*/		public 		 int 	MAX_REGULATORS 			= 20;
//	in case of link dynamics ...
	/**Probability of forming a new regulatory interaction.*/	public 		 double LINK_FORMATION_RATE		= 0.25;
	/**Probability of deleting a regulatory interaction.*/		public 		 double LINK_DELETION_RATE		= 0.3;
	/**Probability of changing target of reg. interaction.*/	public 		 double OUTPUT_CHANGE_RATE		= 0.15;
	/**Probability of changing source of reg. interaction.*/	public 		 double INPUT_CHANGE_RATE		= 0.15;
	/**Prob. of switching between activation and inhibition.*/	public 		 double SWAP_LINK_SIGN_RATE 	= 0.15;
//	in case of dimer dynamics ...
	/**Probability of new homodimer to arise.*/					public 		 double NEW_HOMODIMER_RATE 		= 0.0;
	/**Probability of new heterodimer btw. two existing TFs.*/	public 		 double NEW_HETERODIMER_RATE 	= 0.0;
	/**Probability of loosing dimerisation btw. two TFs.*/		public 		 double LOSS_OF_DIMER_RATE		= 1.0; 	// maybe important to have this higher than NEW_HOMODIMER_RATE in order to make the result clearer if selection favours dimers 
		
//	for DefaultSimulator 
	/**Maximum # of random picks= 2^X.*/						public 		 int 	MAX_PICKS_POWER 		= 11;
//	/**# of random picks dependent on this prop. of net. size*/	public final double RANDOM_PICKS_PROPORTION	= 0.2;
//	/**Always use random picks of initial states.*/				public final boolean ALWAYS_RANDOM_STATES	= false;
//	attractors
	/**Minimum prop. of expressed genes in valid attractor.*/	public 		 double ATTRACTOR_MIN_GENES 	= 0.1;
	/**Maximum prop. of expressed genes in valid attractor.*/	public 		 double ATTRACTOR_MAX_GENES 	= 0.9;
	/**Maximum attractor length.*/								public 		 int	ATTRACTOR_MAX_LENGTH	= 50;
	/**For clustering method. Distance btw. attr. profiles*/ 	public 		 double ATTR_DISTANCE_THRESHOLD = 0.8;
	/**For recursive sink-finding algorithm. NOT IN USE.*/		public final int 	SINK_SEARCH_REC_DEPTH 	= 2;
	
//	for SimpleSelector
	/**Proportion of fittest networks saved for next gen.*/		public 		 double	FITTEST_PROPORTION		= 0.15; 	// must NOT be greater than 0.5!!
	/**Minimum # of attractors for viable organism.*/			public 		 int 	VIABLE_MIN_ATTRACTORS	= 1;
	/**Minimum # of genes for viable organism.*/				public 		 int 	VIABLE_MIN_GENES		= 1;
//	for fitness function
	/**The optimum of #genes/#tissues.*/						public 		 double OPTIMAL_GT_RATIO		= 0.001;
	/**The optimal number of genes.*/							public 		 double OPTIMAL_GENE_NUMBER 	= 10;
	/**Contribution of G/T-term to fitness.*/					public 		 double RATIO_TERM_PROPORTION 	= 0.6;
	/**Contribution of opt. #(genes)-term to fitness.*/			public 		 double	GENES_TERM_PROPORTION	= 0.4;
	/**Contribution of genes/nonisolated-term to fitness.*/		public 		 double ISOLATED_TERM_PROPORTION= 0.0;


//	for SinksFitnessFunction
	/***/ public final double ATTRACTORS_TERM_PROP = 0.25;
	/***/ public final double SINKS_RATIO_TERM_PROP = 0.25;
	/***/ public final double NONREG_TERM_PROP = 0.25;
	/***/ public final double OPT_SINK_TERM_PROP = 0.25;
	
//	fitness noise
	/**Adds or subtracts random value to fitness.*/ 			public final boolean ADD_NOISE_TO_FITNESS 	= false;
	/**Maximum value for the noise.*/ 							public final double FITNESS_NOISE_MAXIMUM 	= 0.01;
	
//	for RandomNetworkGenerator
	/**Probability of adding one random input to element.*/		public  	 double RNG_P_FIRST_INPUT		= 1.0;
	/**Probability of adding a second random input to element.*/public 		 double RNG_P_SECOND_INPUT		= 0.5;
	
//	for DefaultFileManager
	/**Filename written for sim. state. Number added later.*/	public final String STATE_FILENAME_PREFIX 	= "SIM_STATE_";
	/**Write state to file every X generations.*/				public 		 int 	WRITE_STATE_INTERVAL	= 500;
	/**Write pop. history entry every X generations.*/			public 		 int POPULATION_HISTORY_INTERVAL= 10;
	/**Delete old files when starting new run?*/				public final boolean DELETE_OLD_FILES		= true;
	/**Whether or not to use network histories.*/ 				public 	 	 boolean USE_NETWORK_HISTORY 	= false;
	/**Should any files be written at all?*/					public  	 boolean WRITE_FILES 			= true;
	/**Should memory usage be recorded in a file?*/				public final boolean KEEP_PERFORMANCE_LOG 	= true;
	
//	for alternative sink-focussed model... not in use.
	public final boolean SINKS_MODEL_ON = false;
	/**Marks the simulation as running in the "sinks"-version.*/ public final boolean INCREASE_SINKS_ON 		= false;
	/**Increase #sinks every X generations.*/					public final int INCREASE_SINKS_INTERVAL 	= 100;	
	/**How many sinks to add at each increase.*/				public final int SINKS_INCREMENT 			= 0;
	//	for RandomSinkNetworkGenerator
	/***/ public final int INITIAL_SINK_NUMBER = 10;


	
	
	public Parameters(EvolutionaryProcess process){
		this.process = process;
		Field[] fieldArray = getClass().getFields();
		fieldNameSet = new HashSet<String>();
		
		for (Field f:fieldArray)
			fieldNameSet.add(f.getName());
	}
	
	public Parameters(){
		this(null);
	}

	public NetworkGenerator getDefaultGenerator(){
		switch(this.DEFAULT_GENERATOR){
		case 0: return new RandomNetworkGenerator(process);
		case 1: return new TestNetworkGenerator(process);
		case 2: return new RandomGraphNetworkGenerator(process);
		case 3: return new TestGraphNetworkGenerator(process);
		case 4: return new RandomSinkNetworkGenerator(process);
		case 5: return new RandomDimerNetworkGenerator(process);
		case 6: return new RandomDimerGraphNetworkGenerator(process);
		default: return null;
		}
	}
	public Simulator getDefaultSimulator(){
		switch(this.DEFAULT_SIMULATOR){
		case 0: return new DefaultSimulator(process);
		case 1: return new SourcesSimulator(process);
		case 2: return new SinksSimulator(process);
		default: return null;
		}
	}
	public AbstractStatesGenerator getDefaultStatesGenerator(){
		switch(this.DEFAULT_STATES_GEN){
		case 0: return new AllStatesGenerator(process);
		case 1: return new SourcesStatesGenerator(process);
		case 2: return new SensibleStatesGenerator(process);
		case 3: return new SinksStatesGenerator(process);
		default: return null;
		}
	}
	public Mutator getDefaultMutator(){
		switch(this.DEFAULT_MUTATOR){
		case 0: return new DefaultMutator(process);
		case 1: return new SinkMutator(process);
		default: return null;
		}
	}
	public Selector getDefaultSelector(){
		switch(this.DEFAULT_SELECTOR){
		case 0: return new SimpleSelector(process);
		default: return null;
		}
	}
	
	public FitnessFunction getDefaultFitnessFunction(){
		switch(this.DEFAULT_FITNESS_FUNCTION){
		case 0: return new DefaultFitnessFunction(this);
		case 1: return new AdvancedFitnessFunction(this);
		case 2: return new SinksFitnessFunction(process);
		case 3: return new NewFitnessFunction(this);
		default: return null;
		}
	}
	
	public FileManager getDefaultFileManager(){
		switch(this.DEFAULT_FILE_MANAGER){
		case 0: return new DefaultFileManager(process);
		default: return null;
		}
	}
	public NetworkDataCollector getDefaultNetworkCollector(Network net){
		switch(this.DEFAULT_NETWORK_COLLECTOR){
		case 0: return new DefaultNetworkCollector(this, net);
		case 1: return new ClusteringNetworkCollector(this, net);
		case 2: return new SinksDataCollector(this, net);
		default: return null;
		}
	}
	public ProcessDataCollector getDefaultProcessCollector(EvolutionaryProcess process){
		switch(this.DEFAULT_PROCESS_COLLECTOR){
		case 0: return new DefaultProcessCollector(this, process);
		default: return null;
		}
	}
	public BooleanFunction getDefaultGeneRule(){
		if (DEFAULT_GENE_RULE == 7){
			RandomEngine generator = new MersenneTwister(new Date());
			Uniform uniform = new Uniform(generator);
			int rule = uniform.nextIntFromTo(2, 6);
			switch(rule){
			case 0: return new SimpleAndRule();
			case 1: return new AndRule();
			case 2: return new LenientRepressorWinsRule();
			case 3: return new StrictRepressorsWinRule();
			case 4: return new DefaultOnRule();
			case 5: return new DefaultOffRule();
			case 6: return new StrengthInNumbersRule();
			
			default: return null;
			}
		}
		switch(this.DEFAULT_GENE_RULE){
		case 0: return new SimpleAndRule();
		case 1: return new AndRule();
		case 2: return new LenientRepressorWinsRule();
		case 3: return new StrictRepressorsWinRule();
		case 4: return new DefaultOnRule();
		case 5: return new DefaultOffRule();
		case 6: return new StrengthInNumbersRule();
		
		default: return null;
		}
	}
	public BooleanFunction getDefaultDimerRule(){
		switch(this.DEFAULT_DIMER_RULE){
		case 0: return new SimpleDimerRule();
		case 1: return new LastingDimerRule();
		case 2: return new PoissonLastingDimerRule();
		case 3: return new VaryingPoissonLastingDimerRule();
		default: return null;
		}
	}
	public Network getDefaultNetwork(){
		switch(this.DEFAULT_NETWORK){
		case 0: return this.getDefaultGenerator().generateNewNetwork();
		case 1: /*{
			if (this.DEFAULT_GENERATOR < 2)
				System.out.println("WRONG COMBINATION OF GENERATOR AND NETWORK IN PARAMETERS!!");
			*/return this.getDefaultGenerator().generateNewNetwork();
		/*}*/
		default: return null;
		}
	}
	
//	non-final attributes for import into a JTable
	public String[][] getStrings(){
		MyVector<String> names = new MyVector<String>();
		MyVector<String> values = new MyVector<String>();
		MyVector<MyVector<String>> all = new MyVector<MyVector<String>>();
		all.add(names);
		all.add(values);
		Field[] fields = this.getClass().getFields();
		String emptyRow = "";
		Field f;
		int mods;
		Object value;
		try {
			for (int i = 0; i < fields.length; i++) {
				f = fields[i];
				mods = f.getModifiers();
				value = f.get(this);
				if(!Modifier.isFinal(mods)){
					names.add(f.getName());
					if(f.getName() == "DEFAULT_FITNESS_FUNCTION"){
						values.add(this.getDefaultFitnessFunction().getClass().getSimpleName().toString());
					}else
						values.add(value.toString());
				}
				if(f.getName() == "HOMODIMER_MATRIX_VALUE"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
				if(f.getName() == "DEFAULT_NETWORK_COLLECTOR"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
				if(f.getName() == "PRINT_DURATIONS"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
				if(f.getName() == "DIMER_DYNAMICS_RATE"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
				if(f.getName() == "KEEP_DIMER_OUTPUT_PROBABILITY"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
				if(f.getName() == "INPUT_CHANGE_RATE"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
				if(f.getName() == "LOSS_OF_DIMER_RATE"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
				if(f.getName() == "ALWAYS_RANDOM_STATES"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
				if(f.getName() == "ATTRACTOR_MAX_LENGTH"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
				if(f.getName() == "VIABLE_MIN_GENES"){
					names.add(emptyRow);
					values.add(emptyRow);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		String[] nameStrings = new String[names.size()];
		String[] valueStrings = new String[values.size()];
		
		String[][] allStrings = {names.toArray(nameStrings), values.toArray(valueStrings)};
		
		return allStrings;
	}
	
//	returns absolutely all attributes 'Parameters' has
	public String toStringAll(){
		String str = "";
		Field[] fields = this.getClass().getFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				Object obj = f.get(this);
				str += f.getName() + "="
						+ (obj).toString() + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return str;
	}
	
//	returns only non-final attributes
	public String toString(){
		String str = "";
		Field[] fields = this.getClass().getFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				int mods = f.getModifiers();
				
				Object obj = f.get(this);
				if(!Modifier.isFinal(mods)){
					str += f.getName() + "="
							+ (obj).toString() + "\n";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return str;
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	public EvolutionaryProcess getProcess() {
		return process;
	}
	public void setProcess(EvolutionaryProcess process) {
		this.process = process;
	}

}
