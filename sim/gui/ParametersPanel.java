package sim.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import sim.EvolutionaryProcess;


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
public class ParametersPanel extends javax.swing.JPanel implements ChangeListener{

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable parametersTable;
	private TableModel parmTableModel;
	private EvolutionaryProcess process;
	
	public ParametersPanel(EvolutionaryProcess process) {
		super();
		String[][] strings = process.getParameters().getStrings();
		String[] colTitles = {"Parms","Vals"};
		int cols = strings.length;
		int rows = strings[0].length;
		parmTableModel = new DefaultTableModel(rows, cols);
		for (int columnIndex = 0; columnIndex < cols; columnIndex++) {
			for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
				
				parmTableModel.setValueAt(strings[columnIndex][rowIndex], rowIndex, columnIndex);
			}			
		}
	
		initGUI();
		
	}
	
	private void initGUI() {
		try {
		{
//			this.setPreferredSize(new java.awt.Dimension(644, 501));
		}
//			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//			this.setEnabled(false);
			{
				
//					new DefaultTableModel(
//					new String[][] { { "One", "Two" }, { "Three", "Four" } },
//					new String[] { "Column 1", "Column 2" });
				parametersTable = new JTable();
				parametersTable.setModel(this.parmTableModel);
				add(parametersTable, BorderLayout.WEST);
				parametersTable.getTableHeader().setBounds(0, 0, -7, -7);
				FlowLayout tableHeaderLayout = new FlowLayout();
				parametersTable.setPreferredSize(new java.awt.Dimension(600, 800));
				parametersTable.getTableHeader().setLayout(tableHeaderLayout);
				
				parametersTable.setEditingColumn(1);
				parametersTable.setOpaque(false);
				parametersTable.setCellSelectionEnabled(true);
				parametersTable.setColumnSelectionAllowed(false);
			}
//			pack();
//			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JTable getParametersTable() {
		return parametersTable;
	}

	public void setParametersTable(JTable parametersTable) {
		this.parametersTable = parametersTable;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == parametersTable){
			System.out.println("action performed on table");
		}
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		
		Object source = e.getSource();
		if (source == parametersTable){
			System.out.println("change performed on table");
		}
	}

}
