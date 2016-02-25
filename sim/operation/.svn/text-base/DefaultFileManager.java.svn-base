/**
 * 
 */
package sim.operation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import sim.DefaultProcess;
import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.AbstractSimulationState;
import sim.datatype.DefaultSimulationState;
import sim.datatype.Element;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.NetworkVector;

/**
 * @author sikosek
 * @uml.stereotype uml_id="Standard::Utility"
 */
public class DefaultFileManager implements FileManager{
	
	private MyVector<String> stateIDs;
	private EvolutionaryProcess process;
	private Parameters p;
	
	/**
	 * Provides the Parameters and loads all existing states (if any) from file.
	 */
	public DefaultFileManager(EvolutionaryProcess process) {
		this.process = process;
		p = process.getParameters();
		stateIDs = new MyVector<String>();
		collectFiles();
		if (p.DELETE_OLD_FILES) {
			this.deleteAllStates();
		}
	}

	/**
	 * Reloads all states from file. <b>Automatically invoked by the constructor!</b>
	 */
	public void collectFiles(){
		
		ObjectInputStream is;
		boolean stop = false;
		stateIDs.removeAllElements();
		int stateID = 0;
		String filename;
		try {
			while(!stop){
				filename = "" + p.STATE_FILENAME_PREFIX + this.createFilenameNumber(stateID);
				System.out.print("Looking for " +filename);
				
				FileInputStream fis = new FileInputStream(filename);
				is = new ObjectInputStream(fis);
				
				System.out.println(" ... found !");
				stateIDs.add(filename);
				is.close();
				stateID++;
				
			}
		} catch (FileNotFoundException e) {
			System.out.println(" not found.\nFinished looking for states.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	private String createFilenameNumber(int stateID){
		String str = "";
		int zeros = 5 - (stateID+"").length();
		
		for(int i = 0; i < zeros; i++){
			str += '0';
		}
		str += stateID;
		return str;
	}
	
	/* (non-Javadoc)
	 * @see sim.FileManager#loadState(java.lang.String)
	 */
	public AbstractSimulationState loadState(String filename){
		
		AbstractSimulationState state;
		
		try {
			
			if(!stateIDs.contains(filename))
				throw new DefaultFileManagerException("state not found: " + filename);
				
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename));
			
			state = (AbstractSimulationState) is.readObject();
			
			if(true) System.out.println("state retrieved from file: " + filename);
			
			return state;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultFileManagerException e) {

		}
		
		return null;
	}
	

	/* (non-Javadoc)
	 * @see sim.operation.FileManager#loadStateCompact(java.lang.String)
	 */
	public AbstractSimulationState loadStateCompact(String filename) {
		DefaultSimulationState state = new DefaultSimulationState();
		try {
			File f = new File(filename);
			FileReader reader = new FileReader(f);
			BufferedReader buffy = new BufferedReader(reader);
			String line = "";
			
//			get generation number
			line = buffy.readLine();
			state.setGeneration(Integer.parseInt(line));
			
//			get population size
			line = buffy.readLine();
			int popsize = Integer.parseInt(line);
			state.setPopulation(new NetworkVector());
			
//			get network sizes and compositions
			line = buffy.readLine();
			Network net = p.getDefaultNetwork();
			state.getPopulation().add(net);
			char c = ' ';
			for (int charindex = 0; charindex < line.length(); charindex++){
				c = line.charAt(charindex);
				if (c == '\t'){
					net = p.getDefaultNetwork();
					state.getPopulation().add(net);
					
				}
				else{
					Element e = net.addElement();
					if (line.charAt(charindex) == 'D')
						e.setDimer(true);
				}
			}
			System.out.println("got networks");
			
//			get interactions
			line = buffy.readLine();
			String valuestring = "";
			String inputstring = "";
			byte matrixvalue = 0;
			int inputindex = 0;
			int currentNetwork = 0;
			int currentElement = 0;
			for (int charindex = 0; charindex < line.length(); ){
				c = line.charAt(charindex);
				switch (c){
					case '(': {
						charindex++;
						c = line.charAt(charindex);
						while(c != ','){
							
							valuestring += c;
							charindex++;
							c = line.charAt(charindex);
						}
						
						matrixvalue = Byte.parseByte(valuestring);
						
						break;
					}
					case ',':{
						charindex++;
						c = line.charAt(charindex);
						while(c != ')'){
							
							inputstring += c;
							charindex++;
							c = line.charAt(charindex);
						}
						inputindex = Integer.parseInt(inputstring);
						
						break;
					}
					case '|': {
						
						currentElement++;
						charindex++;
						break;
					}
					case '\t':{
					
						currentNetwork++;
						currentElement = 0;
						charindex++;
						break;
					}
					default:{
						charindex++;
					}
					
				}
				if (valuestring != "" && inputstring != ""){
					state.getPopulation().get(currentNetwork).getInteractions().set(currentElement, inputindex, matrixvalue);
					inputindex = 0;
					matrixvalue = 0;
					valuestring = "";
					inputstring = "";
				}
				
			}
			System.out.println("got interactions");
			
			
			
			buffy.close();
			System.out.println("recovered compact simulation state from file: " +f);
			return state;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public AbstractSimulationState loadState(File file) {
		AbstractSimulationState state;
		
		try {
			
				
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
			
			state = (AbstractSimulationState) is.readObject();
			
			if(true) System.out.println("state retrieved from file: " + file.getAbsolutePath());
			
			return state;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see sim.FileManager#loadLastState()
	 */
	public AbstractSimulationState loadLastState() {
		
		AbstractSimulationState state;
		
		try {
			
			if(stateIDs.isEmpty())
				throw new DefaultFileManagerException("no states found!");
				
			
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(stateIDs.get(stateIDs.size() - 1)));
			
			state = (AbstractSimulationState) is.readObject();
			
			if(true) System.out.println("state retrieved from file: " + stateIDs.get(stateIDs.size() - 1));
			
			return state;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultFileManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see sim.FileManager#deleteLastState()
	 */
	public boolean deleteLastState(){
		if(!stateIDs.isEmpty()){
			
			return deleteState(stateIDs.get(stateIDs.size() - 1));
			
		}
		return false;
	}
	private boolean deleteState(String state){
		if(!stateIDs.isEmpty() && state != null && state != "" && stateIDs.contains(state)){
			File deletedFile = new File(state);
			deletedFile.delete();
			stateIDs.remove(stateIDs.indexOf(state));
			System.out.println("deleted state " + state);
			return true;
		}
		System.out.println("did NOT delete state " + state);
		return false;
	}
	/* (non-Javadoc)
	 * @see sim.FileManager#deleteAllStates()
	 */
	public boolean deleteAllStates(){
		boolean success = true;
		int n = stateIDs.size();
		while(!stateIDs.isEmpty()){
			
			if (!deleteState(stateIDs.get(0)))
				success = false;
		}		
		if (success) System.out.println(n+ " states deleted successfully!");
		return success;
	}
	
	public boolean writeStateToFile(AbstractSimulationState state, String filename) {
		
		try {
			
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename));
			
			os.writeObject(state);
			
			os.close();
			
			if(true) System.out.println("state written into file: " + filename);
			
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	/* (non-Javadoc)
	 * @see sim.FileManager#writeStateToFile(sim.AbstractSimulationState)
	 */
	public boolean writeStateToFile(AbstractSimulationState state) {
		int stateID = stateIDs.size();
		
		String filename = p.STATE_FILENAME_PREFIX;
		
		filename += this.createFilenameNumber(stateID);
		
		stateIDs.add(filename);
		
		try {
			
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename));
			
			os.writeObject(state);
			
			os.close();
			
			if(true) System.out.println("state written into file: " + filename);
			
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see sim.operation.FileManager#writeStateToFileCompact(sim.datatype.AbstractSimulationState)
	 */
	public File writeStateToFileCompact(AbstractSimulationState state) {
		try {
			String filename ="testfile.state";
			String content = "";
			File file = new File(filename);
			FileWriter writer = new FileWriter(file);
			BufferedWriter buffy = new BufferedWriter(writer);
			
			content += state.getGeneration()+"\n";
			
			content += state.getPopulation().size()+"\n";
			
			String composition = "";
			String interactions = "";
			for(Network net:state.getPopulation()){
				
				for (Element elem:net.getElementVector()){
					if(elem.isDimer())
						composition += 'D';
					else 
						composition += 'G';
					
					int index = net.getIndexOf(elem);
					for (int input = 0; input < net.size(); input++ ){
						byte value = net.getInteractions().get(index, input);
						if (! (value == 0)){
							interactions += "(" +value+ "," +input+ ")";
						}
					}
					interactions += '|';
				}
				
				
				
				composition += '\t';
				interactions += '\t';
			}
			composition += '\n';
			interactions += '\n';
			content += composition;
			content += interactions;
			
			buffy.write(content);
			buffy.close();
			System.out.println("compact simulation state written to file: " +file);
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public void printStates(){
		System.out.println("States available:");
		for (int i = 0; i < stateIDs.size(); i++) {
			System.out.println(stateIDs.get(i));
		}		
	}
	

	/* (non-Javadoc)
	 * @see sim.operation.FileManager#loadParameters(java.io.File)
	 */
	public Parameters loadParameters(String filename) {
		Parameters p = new Parameters(process);
		
		try {
			File f = new File(filename);
			FileReader reader = new FileReader(f);
			BufferedReader buffy = new BufferedReader(reader);
			String line = "";
			String fieldname;
			String value;
			char c;
			line = buffy.readLine();
			while(!(line == null)){
				
				fieldname = "";
				value = "";
				forloop:
				for(int i = 0; i < line.length(); i++){
					c = line.charAt(i);
					if (c != '=') 
						fieldname += c;
					else{
						value = line.substring(i+1);
						break forloop;
					}
						
				}
				
				Field field = p.getClass().getField(fieldname);
				Type type = field.getType();
				System.out.print("fieldname: "+fieldname+" - value: "+value+" - type: " +type.toString());
				if (type == int.class){
					int integervalue = Integer.parseInt(value);
					field.set(p, integervalue);
					System.out.println("... restored!");
				} else if(type == double.class){
					double doublevalue = Double.parseDouble(value);
					field.set(p, doublevalue);
					System.out.println("... restored!");
				}
				
					
				
				line = buffy.readLine();
				
			}
			buffy.close();
			System.out.println("parameters restored from file: " +f);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
	}

	/* (non-Javadoc)
	 * @see sim.operation.FileManager#saveParametersToFile(sim.Parameters, java.io.File)
	 */
	public File saveParametersToFile(Parameters p, String filename) {
		
		try {
			filename += ".parms";
			File file = new File(filename);
			FileWriter writer = new FileWriter(file);
			BufferedWriter buffy = new BufferedWriter(writer);
			System.out.println(""+p.toString());
			buffy.write(p.toString());
			buffy.close();
			System.out.println("wrote parameters-file: " +filename);
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * For debugging and testing.
	 * @param args
	 */
	public static void main(String[] args){
		DefaultProcess pro = new DefaultProcess();
//		String filename = "testparmfile";
//		pro.getFileManager().saveParametersToFile(pro.getParameters(), filename);
//		File f = pro.getFileManager().saveParametersToFile(pro.getParameters(), filename);
//		pro.setParameters(pro.getFileManager().loadParameters(f.getName()));
//		System.out.println(""+pro.getParameters().toString());
		pro.startProcess();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AbstractSimulationState state = pro.getSimState();
		System.out.println(state.getGeneration());
		for (Network n:state.getPopulation()){
			System.out.println(n.getInteractions()+"\n");
		}
		pro.getFileManager().writeStateToFileCompact(state);
		
		pro.pauseProcess();
		
		System.out.println("load state:");
		
		state = pro.getFileManager().loadStateCompact("testfile.state");
		
		System.out.println(state.getGeneration());
		for (Network n:state.getPopulation()){
			System.out.println(n.getInteractions()+"\n");
		}
	}



	
}
