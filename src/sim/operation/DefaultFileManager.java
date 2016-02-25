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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import sim.DefaultProcess;
import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.UnZip;
import sim.datatype.AbstractSimulationState;
import sim.datatype.DefaultSimulationState;
import sim.datatype.Element;
import sim.datatype.NetworkHistory;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.datatype.PopulationHistory;
import sim.datatype.PopulationHistoryEntry;

/**
 * @author sikosek
 * @uml.stereotype uml_id="Standard::Utility"
 */
public class DefaultFileManager implements FileManager{
	
	private MyVector<String> stateIDs;
	private EvolutionaryProcess process;
	private Parameters p;
	private String networkHistoryColumnNames = "gener.\t" +
												"fitn.\t" +
												"fTerm1\t" +
												"fTerm2\t" +
												"fTerm3\t" +
												"attrac.\t" +
												"cplxAt.\t" +
												"genes\t" +
												"dimers\t" +
												"meanIn\t" +
												"meanOut\t" +
												"act.\t" +
												"rep.\t" +
												"mutat.\n";
	

	/**
	 * Provides the Parameters and loads all existing states (if any) from file.
	 */
	public DefaultFileManager(EvolutionaryProcess process) {
		this.process = process;
		p = process.getParameters();
		stateIDs = new MyVector<String>();
//		collectFiles();
//		if (p.DELETE_OLD_FILES) {
//			this.deleteAllStates();
//		}
	}

	public String createProcessFolder(String baseFolderandName){
		File processFolder = new File(baseFolderandName);
		if (processFolder.mkdir()){
			if(p.SHOW_FILE_OPERATIONS)System.out.println("created folder: "+processFolder.getAbsolutePath());
		}
		else{
			if(p.SHOW_FILE_OPERATIONS)System.out.println("could not create folder: "+baseFolderandName);
		}
		process.setFolderPath(processFolder.getAbsolutePath());
		process.setFolderName(processFolder.getName());
		return processFolder.getAbsolutePath();
	}
	
	public static String zipDirectory(String dirName){
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dirName+".zip"));
			DefaultFileManager.zipDir(dirName, zos);
			zos.close();
			return dirName+".zip";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static void printZipFileContents(String zipFileName){
		try {
			ZipFile zippy = new ZipFile(zipFileName);
			Enumeration entries = zippy.entries();
			for ( ; entries.hasMoreElements() ;) {
				System.out.println(entries.nextElement());

		     }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void zipDir(String dirName, ZipOutputStream zos){
		try {
//			create a new File object based on the directory we 
//		    have to zip File    
			File zipDir = new File(dirName); 
//		    get a listing of the directory content 
		    String[] dirList = zipDir.list(); 
//		    System.out.println("-------zipDir.list():");
//		    for (String s:dirList)System.out.println(s);
		    byte[] readBuffer = new byte[2156]; 
		    int bytesIn = 0; 
//		      loop through dirList, and zip the files 
		    for(int i=0; i<dirList.length; i++) 
		    { 
		    	File f = new File(zipDir, dirList[i]); 
		    	if(f.isDirectory()) 
		    	{ 
//	             	if the File object is a directory, call this 
//	              	function again to add its content recursively 
		    		String filePath = f.getPath(); 
		    		zipDir(filePath, zos); 
//	              	loop again 
		    		continue; 
		    	} 
//		        if we reached here, the File object f was not 
//		        a directory 
//		        create a FileInputStream on top of f 
		        FileInputStream fis = new FileInputStream(f); 
//		        create a new zip entry 
		        ZipEntry anEntry = new ZipEntry(f.getPath()); 
//		      	place the zip entry in the ZipOutputStream object 
		        zos.putNextEntry(anEntry); 
//	          	now write the content of the file to the ZipOutputStream 
		        while((bytesIn = fis.read(readBuffer)) > 0) 
		        { 
		        	zos.write(readBuffer, 0, bytesIn); 
		        } 
		        zos.closeEntry();
//	         	close the Stream 
		        fis.close(); 
	      	}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void zipFile(String filename){
//		 These are the files to include in the ZIP file
//	    String[] filenames = new String[]{"filename1", "filename2"};
	    
	    // Create a buffer for reading the files
	    byte[] buf = new byte[1024];
	    
	    try {
	        // Create the ZIP file
	        String outFilename = filename+".zip";
	        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outFilename));
	    
	        // Compress the files
//	        for (int i=0; i<filenames.length; i++) {
//	        	File f = new File(filename);
	            FileInputStream fis = new FileInputStream(filename);
	    
	            // Add ZIP entry to output stream.
	            zos.putNextEntry(new ZipEntry(filename));
//	            ZipEntry anEntry = new ZipEntry(f.getPath()); 
//		      	place the zip entry in the ZipOutputStream object 
//		        zos.putNextEntry(anEntry); 
	            // Transfer bytes from the file to the ZIP file
	            int len;
	            while ((len = fis.read(buf)) > 0) {
	                zos.write(buf, 0, len);
	            }
	    
	            // Complete the entry
	            zos.closeEntry();
	            fis.close();
//	        }
	    
	        // Complete the ZIP file
	        zos.close();
	    } catch (IOException e) {
	    }
	}
	
	public static void listZipFiles(String path){
		File location = new File(path);
		File[] files = location.listFiles();
		for (File f:files){
			if (f.isDirectory()){
				listZipFiles(f.getPath());
				continue;
			}
			if (f.getName().endsWith(".zip")){
				System.out.println(f.getPath());
			}
		}
		
	}
	
	public static void listZipContents(String filename){
		try {
			ZipFile zippy = new ZipFile(filename);
			Enumeration all = zippy.entries();
			for (;all.hasMoreElements();){
				System.out.println(all.nextElement());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void unzip(String filename){
		
			UnZip unzip = new UnZip();
			unzip.unZip(filename);
			
			
			
		
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
			
			if(p.SHOW_FILE_OPERATIONS) System.out.println("state retrieved from file: " + filename);
			
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
//			int popsize = Integer.parseInt(line);
			state.setPopulation(new NetworkVector());
			
//			get network sizes and compositions
			line = buffy.readLine();
			Network net = process.getGenerator().generateNewNetwork();
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
			if(p.SHOW_FILE_OPERATIONS)System.out.println("got networks");
			
//			get interactions
			line = buffy.readLine();
			String valuestring = "";
			String inputstring = "";
			byte matrixvalue = 0;
			int inputindex = 0;
			int currentNetwork = 0;
			int currentElement = 0;
			
//			run along the entire string, character by character
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
					state.getPopulation().get(currentNetwork).addInteraction(state.getPopulation().get(currentNetwork).get(currentElement), matrixvalue, state.getPopulation().get(currentNetwork).get(inputindex));
					inputindex = 0;
					matrixvalue = 0;
					valuestring = "";
					inputstring = "";
				}
				
			}
			if(p.SHOW_FILE_OPERATIONS)System.out.println("got interactions");
			
			
			
			buffy.close();
			if(p.SHOW_FILE_OPERATIONS)System.out.println("recovered compact simulation state from file: " +f);
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
		if(p.SHOW_FILE_OPERATIONS)if (success) System.out.println(n+ " states deleted successfully!");
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
	public File writeStateToFileCompact(AbstractSimulationState state, String filename) {
		try {
//			String filename ="testfile.state";
			String content = "";
			File file = new File(filename+createFilenameNumber(state.getGeneration()));
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
	
	public static Parameters loadParametersStatic(String filename) {
		Parameters p = new Parameters();
//		Vector<String> ignore = new Vector<String>(7);
//		ignore.add("ParameterToChange");
//		ignore.add("ParameterToChangeBegin");
//		ignore.add("ParameterToChangeEnd");
//		ignore.add("ParameterToChangeStep");
//		ignore.add("SimulationRepeats");
//		ignore.add("Email");
//		ignore.add("MainClass");
//		ignore.add("MFinderAnalyser");
//		ignore.add("OutputFile");
//		ignore.add("SimulationRepeatNumber");
//		ignore.add("SIMULATION_NAME");
			
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
				if (!Parameters.fieldNameSet.contains(fieldname)){
					System.out.print("fieldname: "+fieldname+" - value: "+value);
					System.out.println("... ignored!");
				}else{
					
					Field field = p.getClass().getField(fieldname);
					
					Type type = field.getType();
					System.out.print("fieldname: "+fieldname+" - value: "+value+" - type: " +type.toString());
					if (type == int.class){
						int integervalue = (int) Double.parseDouble(value);
						field.set(p, integervalue);
						System.out.println("... restored!");
					} else if(type == double.class){
						double doublevalue = Double.parseDouble(value);
						field.set(p, doublevalue);
						System.out.println("... restored!");
					}else if(type == byte.class){
						byte bytevalue = (byte) Double.parseDouble(value);
						field.set(p, bytevalue);
						System.out.println("... restored!");
					}else if(type == boolean.class){
						boolean booleanvalue = Boolean.parseBoolean(value);
						field.set(p, booleanvalue);
						System.out.println("... restored!");
					}
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
	public Parameters loadParameters(String filename) {
		Parameters p = new Parameters(process);
//		Vector<String> ignore = new Vector<String>(7);
//		ignore.add("ParameterToChange");
//		ignore.add("ParameterToChangeBegin");
//		ignore.add("ParameterToChangeEnd");
//		ignore.add("ParameterToChangeStep");
//		ignore.add("SimulationRepeats");
//		ignore.add("Email");
//		ignore.add("MainClass");
//		ignore.add("MFinderAnalyser");
//		ignore.add("OutputFile");
//		ignore.add("SimulationRepeatNumber");
//		ignore.add("SIMULATION_NAME");
		
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
				
				if (!Parameters.fieldNameSet.contains(fieldname)){
					System.out.print("fieldname: "+fieldname+" - value: "+value);
					System.out.println("... ignored!");
				}else{
					Field field = p.getClass().getField(fieldname);
					Type type = field.getType();
					System.out.print("fieldname: "+fieldname+" - value: "+value+" - type: " +type.toString());
					
					if (type == int.class){
						int integervalue = (int) Double.parseDouble(value);
						field.set(p, integervalue);
						System.out.println("... restored!");
					} else if(type == double.class){
						double doublevalue = Double.parseDouble(value);
						field.set(p, doublevalue);
						System.out.println("... restored!");
					}else if(type == byte.class){
						byte bytevalue = (byte) Double.parseDouble(value);
						field.set(p, bytevalue);
						System.out.println("... restored!");
					}else if(type == boolean.class){
						boolean booleanvalue = Boolean.parseBoolean(value);
						field.set(p, booleanvalue);
						System.out.println("... restored!");
					}
				
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
//			filename += ".parms";
			File file = new File(filename);
			FileWriter writer = new FileWriter(file);
			BufferedWriter buffy = new BufferedWriter(writer);
//			System.out.println(""+p.toString());
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
	public boolean writePopulationHistory(PopulationHistory popHistory, String filename){
		try {
			
			File f = new File(filename);
			FileWriter writer = new FileWriter(f);
			BufferedWriter buffy = new BufferedWriter(writer);
			buffy.write(DefaultProcessCollector.popStatsColumnNames + popHistory.toString());
			if(p.SHOW_FILE_OPERATIONS)System.out.println("wrote population history to file: "+filename);
			buffy.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public String writeNetworkHistory(NetworkHistory networkHistory){
		return writeNetworkHistory(networkHistory, networkHistory.getFilename(), false);
	}
	public String writeNetworkHistory(NetworkHistory networkHistory, String filename, boolean createNewFile){
		try {
			
//			append populatoin index of network as a fixed-size string (add zeros...)
			Network net = networkHistory.getDataCollector().getNetwork();
//			System.out.println("-"+net);
			NetworkVector pop = networkHistory.getDataCollector().getNetwork().getProcess().getPopulation();
			int index = pop.indexOf(net);
//			System.out.println("netindex:"+index);
			
			if (createNewFile){
				filename += createFilenameNumber(index);
				File f = new File(filename);
				networkHistory.setFilename(filename);
				FileWriter writer = new FileWriter(f);
				BufferedWriter buffy = new BufferedWriter(writer);

				buffy.write(networkHistoryColumnNames + networkHistory.historyToString());
				if(p.SHOW_FILE_OPERATIONS)System.out.println("wrote history to file: "+filename);
				buffy.close();
				return filename;
			}else{
				String contents = "";
				FileReader reader = new FileReader(filename);
				BufferedReader buffet = new BufferedReader(reader);
				String line = buffet.readLine();
				contents += line;
				while(line != null){
					line = buffet.readLine();
					contents += line;
				}
				File f = new File(filename);
				FileWriter writer = new FileWriter(f);
				BufferedWriter buffy = new BufferedWriter(writer);

				buffy.write(contents);
				if(p.SHOW_FILE_OPERATIONS)System.out.println("wrote history to file: "+filename);
				buffy.close();
				return filename;
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename;
	}
	public static void getAveragesOfSimulationRuns(String simfolder, String outputfolder){
		String[] simnames = {"NO_DIMERS", "STABLE_DIMERS", "UNSTABLE_DIMERS"};
		File folder = new File(simfolder);
		System.out.println(""+folder.getAbsolutePath());
		File[] repeatFolders = folder.listFiles();
		if (repeatFolders == null){
			System.out.println("no folders found!");
			System.exit(0);
		}
		
		
		PopulationHistoryEntry[] stats = new PopulationHistoryEntry[simnames.length];
		for (int j = 0; j < stats.length; j++)
			stats[j] = new PopulationHistoryEntry();
		
		for (File sub:repeatFolders){
			
			try {
				for (int i = 0; i < simnames.length; i++){
					File file = new File(sub.getPath()+File.separator+simnames[i] +File.separator+ "pop_stats");
					System.out.println("get file: "+file);
					FileReader reader = new FileReader(file);
					BufferedReader  buff = new BufferedReader(reader);
//					String newfile=buff.readLine();
					String line = buff.readLine();
					String newline = line;
					System.out.println("header:"+line);
					
					while(newline != null){
						
						newline = buff.readLine();
						if (newline != null){
							line = newline;
						}
					}
					System.out.println("last line: "+line);
					Vector<Float> values = readValuesFromTabbedLine(line);
					System.out.println("vals:"+values.size()+"   ");
					System.out.println("values from line: "+values);
					System.out.println(""+stats[i].toString());
					stats[i].setGeneration(values.get(0).shortValue());
					stats[i].setAvgFitness(stats[i].getAvgFitness() + values.get(1));
					stats[i].setMaxFitness(stats[i].getMaxFitness() + values.get(2));
					stats[i].setAvgFTerm1(stats[i].getAvgFTerm1() + values.get(3));
					stats[i].setAvgFTerm2(stats[i].getAvgFTerm2() + values.get(4));
					stats[i].setAvgFTerm3(stats[i].getAvgFTerm3() + values.get(5));
					stats[i].setAvgAttractors(stats[i].getAvgAttractors() + values.get(6));
					stats[i].setAvgComplexAttractors(stats[i].getAvgComplexAttractors() + values.get(7));
					stats[i].setAvgGenes(stats[i].getAvgGenes() + values.get(8));
					stats[i].setAvgDimers(stats[i].getAvgDimers() + values.get(9));
					stats[i].setAvgInDegree(stats[i].getAvgInDegree() + values.get(10));
					stats[i].setAvgOutDegree(stats[i].getAvgOutDegree() + values.get(11));
					stats[i].setAvgActivations(stats[i].getAvgActivations() + values.get(12));
					stats[i].setAvgRepressions(stats[i].getAvgRepressions() + values.get(13));
					
					buff.close();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		for (int i = 0; i < simnames.length; i++){
			try {
				File newfile = new File(outputfolder+File.separator+simnames[i]+".summary");
				FileWriter writer = new FileWriter(newfile);
				BufferedWriter buffy = new BufferedWriter(writer);
				
				stats[i].setAvgFitness(stats[i].getAvgFitness() / repeatFolders.length);
				stats[i].setMaxFitness(stats[i].getMaxFitness() / repeatFolders.length);
				stats[i].setAvgFTerm1(stats[i].getAvgFTerm1() / repeatFolders.length);
				stats[i].setAvgFTerm2(stats[i].getAvgFTerm2() / repeatFolders.length);
				stats[i].setAvgFTerm3(stats[i].getAvgFTerm3() / repeatFolders.length);
				stats[i].setAvgAttractors(stats[i].getAvgAttractors() / repeatFolders.length);
				stats[i].setAvgComplexAttractors(stats[i].getAvgComplexAttractors() / repeatFolders.length);
				stats[i].setAvgGenes(stats[i].getAvgGenes() / repeatFolders.length);
				stats[i].setAvgDimers(stats[i].getAvgDimers() / repeatFolders.length);
				stats[i].setAvgInDegree(stats[i].getAvgInDegree() / repeatFolders.length);
				stats[i].setAvgOutDegree(stats[i].getAvgOutDegree() / repeatFolders.length);
				stats[i].setAvgActivations(stats[i].getAvgActivations() / repeatFolders.length);
				stats[i].setAvgRepressions(stats[i].getAvgRepressions() / repeatFolders.length);
				buffy.write(DefaultProcessCollector.popStatsColumnNames + stats[i].toString());
				buffy.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		FileReader reader = new FileReader(filename);
//		BufferedReader buffet = new BufferedReader(reader);
	}
	public static Vector<Float> readValuesFromTabbedLine(String line){
		Vector<Float> data = new Vector<Float>();
		char c;
		String str="";
		for (int i = 0; i < line.length(); i++){
			c = line.charAt(i);
			if(c == '\n') break;
			if (c == '\t'){
				
				data.add(Float.parseFloat(str));
				str = "";
			}else{
				str += c;
			}
		}
		data.add(Float.parseFloat(str));
		return data;
	}
//	public static PopulationHistoryEntry readPopulationHistoryEntryFromTabbedLine(String line){
//		
//	}
	
	public NetworkHistory loadNetworkHistory(NetworkDataCollector dc, String filename){
		NetworkHistory his = null;
//		Vector<Double> fitHist = new Vector<Double>();
//		Vector<Integer> attHist = new Vector<Integer>();
//		Vector<Integer> mutHist = new Vector<Integer>();
//		Vector<String> comHist = new Vector<String>();
		Vector<Float> fitnessHistory = new Vector<Float>(dc.getParameters().GENERATIONS);
		Vector<Float> fitnessTerm1History = new Vector<Float>(dc.getParameters().GENERATIONS);
		Vector<Float> fitnessTerm2History = new Vector<Float>(dc.getParameters().GENERATIONS);
		Vector<Float> fitnessTerm3History = new Vector<Float>(dc.getParameters().GENERATIONS);
		Vector<Short> attractorHistory = new Vector<Short>(dc.getParameters().GENERATIONS);
		Vector<Short> complexAttractorHistory = new Vector<Short>(dc.getParameters().GENERATIONS);
		Vector<Float> meanInDegreeHistory = new Vector<Float>(dc.getParameters().GENERATIONS);
		Vector<Float> meanOutDegreeHistory = new Vector<Float>(dc.getParameters().GENERATIONS);
		Vector<Short> activatorsHistory = new Vector<Short>(dc.getParameters().GENERATIONS);
		Vector<Short> repressorsHistory = new Vector<Short>(dc.getParameters().GENERATIONS);
		Vector<Short> mutationHistory = new Vector<Short>(dc.getParameters().GENERATIONS);
		Vector<Short> genesHistory = new Vector<Short>(dc.getParameters().GENERATIONS);
		Vector<Short> dimersHistory = new Vector<Short>(dc.getParameters().GENERATIONS);
		try {
			File f = new File(filename);
			FileReader reader = new FileReader(f);
			BufferedReader buffy = new BufferedReader(reader);
			String line = buffy.readLine();
			while(line != null){
				line = buffy.readLine();
				char c;
				Vector<String> lineData = new Vector<String>();
				String value = "";
				if(line == networkHistoryColumnNames){
					line  = buffy.readLine();
				}
				
//				extract value strings from this line and store them in vector
				for (int i = 0; i < line.length(); i++){
					c = line.charAt(i);
			
					if (c == '\t'){
						lineData.add(value);
						value = "";
//						careful: last value might be empty! (after last tab)
					}else{
						value += c;
					}
					fitnessHistory.add(Float.parseFloat(lineData.get(0)));
					fitnessTerm1History.add(Float.parseFloat(lineData.get(1)));
					fitnessTerm2History.add(Float.parseFloat(lineData.get(2)));
					fitnessTerm3History.add(Float.parseFloat(lineData.get(3)));
					attractorHistory.add(Short.parseShort(lineData.get(4)));
					complexAttractorHistory.add(Short.parseShort(lineData.get(5)));
					meanInDegreeHistory.add(Float.parseFloat(lineData.get(6)));
					meanOutDegreeHistory.add(Float.parseFloat(lineData.get(7)));
					activatorsHistory.add(Short.parseShort(lineData.get(8)));
					repressorsHistory.add(Short.parseShort(lineData.get(9)));
					mutationHistory.add(Short.parseShort(lineData.get(10)));
					genesHistory.add(Short.parseShort(lineData.get(11)));
					dimersHistory.add(Short.parseShort(lineData.get(12)));
					
				}
				his = new NetworkHistory(dc);
				
				his.setFitnessHistory(fitnessHistory);
				his.setFitnessTerm1History(fitnessTerm1History);
				his.setFitnessTerm2History(fitnessTerm2History);
				his.setFitnessTerm3History(fitnessTerm3History);
				his.setAttractorHistory(attractorHistory);
				his.setComplexAttractorHistory(complexAttractorHistory);
				his.setGenesHistory(genesHistory);
				his.setDimersHistory(dimersHistory);
				his.setMeanInDegreeHistory(meanInDegreeHistory);
				his.setMeanOutDegreeHistory(meanOutDegreeHistory);
				his.setActivatorsHistory(activatorsHistory);
				his.setRepressorsHistory(repressorsHistory);
				his.setMutationHistory(mutationHistory);
				
				his.setGenerations(fitnessHistory.size());
			}
			buffy.close();
			if(p.SHOW_FILE_OPERATIONS)System.out.println("retrieved history from file: " +filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return his;
	}
	
//	public void writePopulationStats(String filename){
//		try {
//			File f = new File(filename);
//			FileWriter writer = new FileWriter(f);
//			BufferedWriter buffy = new BufferedWriter(writer);
//			process.getCollector().updateStats();
//			String stats = process.getGeneration() + "\t"
//				+ process.getCollector().avgFitness + "\t"
//				+ process.getCollector().avgAttractors + "\t"
//				+ process.getCollector().avgGenes + "\t"
//				+ process.getCollector().avgDimers + "\t"
//				+ process.getCollector().avgIsolated + "\t"
//				+ process.getCollector().avgInDegree + "\t"
//				+ process.getCollector().avgOutDegree + "\t"
//				+ process.getCollector().avgPosInteractions + "\t"
//				+ process.getCollector().avgNegInteractions + "\n";
//			buffy.write(popStatsColumnNames + stats);
//			buffy.close();
//			if(p.SHOW_FILE_OPERATIONS)System.out.println("wrote population stats to file: " + filename);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	
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
		pro.getFileManager().writeStateToFileCompact(state, "name");
		
		pro.pauseProcess();
		
		System.out.println("load state:");
		
		state = pro.getFileManager().loadStateCompact("testfile.state");
		
		System.out.println(state.getGeneration());
		for (Network n:state.getPopulation()){
			System.out.println(n.getInteractions()+"\n");
		}
	}
	
	
	public static void removeFolder(String folderPath){
		removeFolderRecursively(folderPath);
		File f = new File(folderPath);
		
//		remove empty base folder
		if (f.delete()){
//			System.out.println("deleted file: "+f.getPath());
		}
		else{
			System.out.println("could not delete: "+f.getPath());
		}
	}

	private static void removeFolderRecursively(String folderPath){
		File f = new File(folderPath);
		File[] files = f.listFiles();
		for (File file:files){
			if (file.isDirectory()){
				removeFolderRecursively(file.getPath());
			}
			if (file.delete()){
//				System.out.println("deleted file: "+file.getPath());
			}
			else{
				System.out.println("could not delete: "+file.getPath());
			}
		}
		
	}
	
}
