/**
 * 
 */
package sim.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import sim.DefaultProcess;
import sim.EvolutionaryProcess;
import sim.datatype.MyVector;
import sim.datatype.NetworkVector;
import sim.operation.DefaultNetworkCollector;
import sim.operation.NetworkDataCollector;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * @author sikosek
 *
 */
public class PopStatsOutputter extends JFrame  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected NetworkPanel popStatFrame;
	protected JTextArea popStatTextArea;
	
	protected NetworkPanel fitnessFrame;
	protected JTextArea fitnessTextArea;
	
	protected ParametersPanel parametersFrame;
//	protected JTextArea paramtersTextArea;
	protected JTable parametersTable;
	protected JTabbedPane tabPane;
	
	protected EvolutionaryProcess process;
	protected NetworkVector pop;
	
	public PopStatsOutputter(EvolutionaryProcess process) {
		Font f = new java.awt.Font("Monospaced",1,12);
		tabPane = new JTabbedPane();
		this.getContentPane().add(tabPane);
		
		
		popStatFrame = new NetworkPanel();
		popStatTextArea = popStatFrame.getJTextArea1();
		popStatTextArea.setFont(f);
		tabPane.addTab("Pop Stats["+process.getId()+"]", popStatFrame);
		popStatFrame.setVisible(true);

		

		fitnessFrame = new NetworkPanel();
		fitnessTextArea = fitnessFrame.getJTextArea1();
		fitnessTextArea.setFont(f);
		tabPane.addTab("Pop Fitness["+process.getId()+"]", fitnessFrame);
		fitnessFrame.setVisible(true);
	
		{
			

			parametersFrame = new ParametersPanel(process);
			tabPane.addTab("Parameters[]", null, parametersFrame, null);
			parametersFrame.setVisible(true);
		}
		
		

	}
	public void terminate(){
		this.dispose();
	}
	public void update(String popStatString, EvolutionaryProcess process){
		pop = process.getPopulation();
//		if (popStatFrame != null && popStatFrame.isVisible()){

			
		popStatTextArea.setText(popStatString);
//		}
//		if (fitnessFrame != null && fitnessFrame.isVisible()){
			NetworkDataCollector dc;
			String completeFitnessString = "["+process.getGeneration()+"]\t";
			String fitnessString;
			for (int i = 0; i < pop.size(); i++) {
				dc = pop.get(i).getDataCollector();
				fitnessString = ""+((int)(dc.getCurrentFitness()*100));
				if (fitnessString.length() > 5) 
					completeFitnessString += fitnessString.substring(0, 5);
				else
					completeFitnessString += fitnessString;
				
				completeFitnessString += "\t";
			}	
			completeFitnessString += "\n";
			fitnessTextArea.insert(completeFitnessString, 0);
//		}
	}
	public void display(){
		this.setLocation(0,0);
		this.setSize(new Dimension(1000, 700));
		this.setVisible(true);
		
	}
	public static void main(String[] args){
		
		EvolutionaryProcess process = new DefaultProcess();
		
		process.start();
		
	}
}
