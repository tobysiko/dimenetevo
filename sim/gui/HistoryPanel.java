package sim.gui;
//import info.clearthought.layout.TableLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import sim.EvolutionaryProcess;
import sim.datatype.MyVector;
import sim.datatype.Network;
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
public class HistoryPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;
	private JTable historyTable;
	private JScrollPane historyScrollPane;
	private TableModel historyTableModel;
	private Vector columnNames;
	private Vector data;

	public TableModel getHistoryTableModel() {
		return historyTableModel;
	}

	public void setHistoryTableModel(TableModel historyTableModel) {
		this.historyTableModel = historyTableModel;
	}

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new HistoryPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public HistoryPanel() {
		super();
		columnNames = new Vector(3);
		columnNames.add("generation");
		columnNames.add("fitness");
		columnNames.add("mutations");
		data = new MyVector();
		initGUI();
	}
	
	private void initGUI() {
		try {
//			this.setPreferredSize(new java.awt.Dimension(772, 455));
			this.setAutoscrolls(true);
			this.setPreferredSize(this.getMaximumSize());
			{
				historyScrollPane = new JScrollPane();
				this.add(historyScrollPane);
				historyScrollPane.setPreferredSize(new java.awt.Dimension(717, 485));
//				historyScrollPane.setPreferredSize(new java.awt.Dimension(609, 448));
				{
					historyTableModel = new DefaultTableModel(data, columnNames);
					historyTable = new JTable();
					historyTable.setLayout(null);
					historyScrollPane.setViewportView(historyTable);
					historyTable.setModel(historyTableModel);
					historyTable.setPreferredSize(new java.awt.Dimension(
						875,
						1665));
					historyTable.getTableHeader().setBounds(0, 0, 171, 412);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateHistory(Network net){
		
		NetworkDataCollector dc = net.getDataCollector();
		dc.updateStats();
		MyVector<String> mutations;
		String mutationString;
		for (int i = net.getProcess().getGeneration(); i >= 0; i--){
			Vector row = new Vector();
			row.add(i);
			if (i < dc.getFitnessHistory().size()){
				row.add(dc.getFitnessHistory().get(i));
			}
			else{
				row.add(-1);
			}
//			System.out.println("generation:"+i);
//			System.out.println("fithist:"+dc.getFitnessHistory().size());
//			row.add(dc.getFitnessHistory().get(i));
			
			if (i < dc.getMutationHistory().size()){
				mutations = dc.getMutationsStringAt(i);
				mutationString = "";
				for(String s:mutations){
					mutationString += s + ", ";
				}
				row.add(mutationString);

			}
			else{
				row.add("?");
			}
//			mutations = dc.getMutationsStringAt(i);
//			mutationString = "";
//			for(String s:mutations){
//				mutationString += s + ", ";
//			}
//			row.add(mutationString);
			
			data.add(row);
		}
		
		historyTableModel = new DefaultTableModel(data, columnNames);
	}
	
	public JTable getHistoryTable() {
		return historyTable;
	}

}
