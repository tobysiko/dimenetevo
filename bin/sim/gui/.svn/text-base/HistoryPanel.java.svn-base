package sim.gui;
//import info.clearthought.layout.TableLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import sim.EvolutionaryProcess;
import sim.datatype.NetworkHistory;
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
public class HistoryPanel extends javax.swing.JPanel implements TableModelListener, PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private JTable historyTable;
	private JScrollPane historyScrollPane;
	private DefaultTableModel historyTableModel;
	private Vector columnNames;
	private Vector data;

	public TableModel getHistoryTableModel() {
		return historyTableModel;
	}

	public void setHistoryTableModel(DefaultTableModel historyTableModel) {
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
		columnNames.add("attractors");
		columnNames.add("mutations");
		columnNames.add("composition");
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
				historyScrollPane.setMaximumSize(new java.awt.Dimension(800, this.getHeight()));

				historyScrollPane.setPreferredSize(new java.awt.Dimension(609, 800));
				{
					historyTableModel = new DefaultTableModel(data, columnNames);
					historyTableModel.addTableModelListener(this);
					historyScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
					historyScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

					historyTable = new JTable(data, columnNames);
					
					historyTable.setLayout(null);
					historyScrollPane.setViewportView(historyTable);
					historyTable.setModel(historyTableModel);
					historyTable.setPreferredSize(new java.awt.Dimension(
						875,
						1665));
					historyTable.getTableHeader().setBounds(0, 0, 171, 412);
					historyTable.addPropertyChangeListener(this);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateHistory(Network net){
		while(! (historyTableModel.getRowCount() == 0)) {
			historyTableModel.removeRow(0);
		}

		NetworkHistory networkHistory = net.getDataCollector().getHistory();
		net.getDataCollector().updateStats();
//		MyVector<String> mutations;
		String mutationString;
		for (int i = net.getProcess().getGeneration(); i >= 0; i--){
			Vector row = new Vector();
			row.add(i);
			if (i < networkHistory.getGenerations()){
				row.add(networkHistory.getFitnessHistory().get(i));
			}
			else{
				row.add(-1);
			}
			if (i < networkHistory.getGenerations()){
				row.add(networkHistory.getAttractorHistory().get(i));
			}
			else{
				row.add(-1);
			}
//			System.out.println("generation:"+i);
//			System.out.println("fithist:"+dc.getFitnessHistory().size());
//			row.add(dc.getFitnessHistory().get(i));
			
			if (i < networkHistory.getGenerations()){
//				mutations = dc.getMutationsStringAt(i);
				mutationString = net.getDataCollector().mutationEventToString(networkHistory.getMutationHistory().get(i));
//				for(String s:mutations){
//					mutationString += s + ", ";
//				}
				row.add(mutationString);

			}
			else{
				row.add("?");
			}
			if (i < networkHistory.getGenerations()){
				row.add("G:"+networkHistory.getGenesHistory().get(i)+"/D:"+networkHistory.getDimersHistory().get(i));
			}
			else{
				row.add(-1);
			}
			
//			mutations = dc.getMutationsStringAt(i);
//			mutationString = "";
//			for(String s:mutations){
//				mutationString += s + ", ";
//			}
//			row.add(mutationString);
			
			data.add(row);
		}
		
//		while(! (historyTableModel.getRowCount() == 0)) {
//			historyTableModel.removeRow(0);
//		}
		historyTableModel.setDataVector(data, columnNames);
		
//		historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//		historyTable.setSize(historyTable.getMinimumSize());
//		historyTable.doLayout();
//		historyScrollPane.getViewport().setSize(historyTable.getSize());
		
	}
	
	public JTable getHistoryTable() {
		return historyTable;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {
//		System.out.println("hello?");
//		System.out.println(e.getSource());
		
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent e) {
		
		
	}

}
