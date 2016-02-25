/**
 * 
 */
package sim;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import sim.operation.DefaultFileManager;

/**
 * @author sikosek
 *
 */
public class DataAnalyser {

	public static void main(String[]args){
		DefaultFileManager.listZipFiles(".");
		
		DefaultFileManager.listZipContents("sim_testGeneRules_parameters.zip");
		System.out.println("finished");
	}
}
