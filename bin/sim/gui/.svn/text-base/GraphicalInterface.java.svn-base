package sim.gui;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Iterator;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.collections.Predicate;

import sim.DefaultProcess;
import sim.EvolutionaryProcess;
import sim.Parameters;
import sim.datatype.AbstractSimulationState;
import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.datatype.OnewayInteraction;
import sim.graph.DimerisingEdge;
import sim.graph.GraphNetwork;
import sim.graph.RegulatoryEdge;
import sim.graph.VertexElement;
import sim.operation.function.PoissonLastingDimerRule;
import sim.operation.function.VaryingPoissonLastingDimerRule;
import edu.uci.ics.jung.graph.DirectedEdge;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.UndirectedEdge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.AbstractVertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.ConstantEdgeStringer;
import edu.uci.ics.jung.graph.decorators.ConstantVertexStringer;
import edu.uci.ics.jung.graph.decorators.DefaultToolTipFunction;
import edu.uci.ics.jung.graph.decorators.EdgeFontFunction;
import edu.uci.ics.jung.graph.decorators.EdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.EdgeStringer;
import edu.uci.ics.jung.graph.decorators.EdgeStrokeFunction;
import edu.uci.ics.jung.graph.decorators.GradientEdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValueStringer;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;
import edu.uci.ics.jung.graph.decorators.PickableEdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.UserDatumNumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.UserDatumNumberVertexValue;
import edu.uci.ics.jung.graph.decorators.VertexAspectRatioFunction;
import edu.uci.ics.jung.graph.decorators.VertexFontFunction;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexSizeFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.decorators.VertexStrokeFunction;
import edu.uci.ics.jung.graph.predicates.SelfLoopEdgePredicate;
import edu.uci.ics.jung.utils.MutableDouble;
import edu.uci.ics.jung.visualization.DefaultVisualizationModel;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.HasGraphLayout;
import edu.uci.ics.jung.visualization.PickSupport;
import edu.uci.ics.jung.visualization.PickedInfo;
import edu.uci.ics.jung.visualization.PickedState;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.ShapePickSupport;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.transform.LayoutTransformer;
import edu.uci.ics.jung.visualization.transform.Transformer;


public class GraphicalInterface extends javax.swing.JFrame implements ItemListener, ChangeListener, ActionListener  {
	
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenu processMenu;
	private JButton pauseButton;
	private JMenuItem quitMenuItem;
	private JSeparator jSeparator1;
	private JMenuItem saveStateMenuItem;
	private JMenuItem loadStateMenuItem;
	private JSlider populationSlider;
	private JScrollPane networkScrollPane;
	private JPanel populationPanel;
	private ParametersPanel parametersPanel;
	private HistoryPanel historyPanel;
	private JButton stopButton;
	private JButton startButton;
	private JToolBar jToolBar1;
	private JTabbedPane tabbedPane;
	private JMenu viewMenu;
	private JButton updateGraphButton;
	private JTextField statusTextField;
	private JMenu fileMenu;
	protected JCheckBox v_color;
    protected JCheckBox e_color;
    protected JCheckBox v_stroke;
    protected JCheckBox e_uarrow_pred;
    protected JCheckBox e_darrow_pred;
    protected JCheckBox v_shape;
    protected JCheckBox v_size;
    protected JCheckBox v_aspect;
    protected JCheckBox v_labels;
    protected JRadioButton e_line;
    protected JRadioButton e_bent;
    protected JRadioButton e_wedge;
    protected JRadioButton e_quad;
    protected JRadioButton e_cubic;
    protected JCheckBox e_labels;
    protected JCheckBox font;
    protected JCheckBox e_show_d;
    protected JCheckBox e_show_u;
    protected JCheckBox v_small;
    protected JCheckBox zoom_at_mouse;
    protected JCheckBox fill_edges;
 	protected JRadioButton no_gradient;
	protected JRadioButton gradient_absolute;
	protected JRadioButton gradient_relative;
	protected JPanel mainPanel;
	protected JPanel control_panel;
	protected JLabel sliderLabel;
	protected JFileChooser stateFileChooser;
	protected FileChooserDialog chooseFilePopup;
	protected FileChooserDialog saveFilePopup;
	protected NetStatsPanel netStatsPanel;
	protected JTextField currentNetTextField;
	
	protected DefaultModalGraphMouse gm;
	protected Transformer affineTransformer;
	protected SeedColor vcf;
    protected EdgeWeightStrokeFunction ewcs;
    protected VertexStrokeHighlight vsh;
    protected VertexStringer vs;
    protected VertexStringer vs_none;
    protected EdgeStringer es;
    protected EdgeStringer es_none;
    protected FontHandler ff;
//    protected VertexShapeSizeAspect vssa;
    protected DirectionDisplayPredicate show_edge;
    protected DirectionDisplayPredicate show_arrow;
    protected VertexDisplayPredicate show_vertex;
    protected GradientPickedEdgePaintFunction edgePaint;
//    protected final static Object VOLTAGE_KEY = "voltages";
    protected final static Object TRANSPARENCY = "transparency";    
    protected NumberEdgeValue edge_weight = new UserDatumNumberEdgeValue("edge_weight");
//    protected NumberVertexValue voltages = new UserDatumNumberVertexValue(VOLTAGE_KEY);
    protected NumberVertexValue transparency = new UserDatumNumberVertexValue(TRANSPARENCY);
    protected static final int GRADIENT_NONE = 0;
	protected static final int GRADIENT_RELATIVE = 1;
    protected static int gradient_level = GRADIENT_NONE;
    protected FRLayout lay;
	protected DefaultVisualizationModel model;
	protected PluggableRenderer rend;
	protected VisualizationViewer vv;
	
    protected MyVector<EvolutionaryProcess> processes;
    protected EvolutionaryProcess process;
    protected NetworkVector population;
    protected Parameters p;
    protected GraphNetwork displayedGraph;
	
	public GraphicalInterface(MyVector<EvolutionaryProcess> processes){
		super("Network Viewer");
		this.processes = processes;
		this.process = processes.get(0);
		this.population = process.getPopulation();
		this.p = process.getParameters();
		displayedGraph = new GraphNetwork(process);
		control_panel = new JPanel();
		mainPanel = startFunction();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setPreferredSize(new Dimension(1600, 800));
//			this.setMaximumSize(new Dimension(1024,769));
			{
				tabbedPane = new JTabbedPane();
				getContentPane().add(tabbedPane, BorderLayout.CENTER);
				tabbedPane.setPreferredSize(new java.awt.Dimension(759, 430));
				{
					populationPanel = new JPanel();
					networkScrollPane = new JScrollPane();
//					networkScrollPane.setPreferredSize(new java.awt.Dimension(609, 448));
					networkScrollPane.setViewportView(populationPanel);
					parametersPanel = new ParametersPanel(process);
					historyPanel = new HistoryPanel();
					tabbedPane.addTab("Networks", null, networkScrollPane, null);
					tabbedPane.addTab("Parameters", null, parametersPanel, null);
					tabbedPane.addTab("History", null, historyPanel, null);
					historyPanel.setVisible(false);
//					populationPanel.setPreferredSize(new java.awt.Dimension(1024, 629));
					parametersPanel.setPreferredSize(new java.awt.Dimension(1024, 629));
					{
						populationSlider = new JSlider();
						populationSlider.setName("population");
						sliderLabel = new JLabel("population");
						
						populationSlider.setBorder(BorderFactory
							.createEtchedBorder(BevelBorder.LOWERED));
						populationSlider.setMajorTickSpacing(10);
						populationSlider.setMinorTickSpacing(1);
						populationSlider.setPaintTicks(true);
						populationSlider.setPaintLabels(true);
						populationSlider.setSnapToTicks(true);
						populationSlider.setMaximum(this.process.getPopulation().size());
						populationSlider.setValue(0);
						populationSlider.setBounds(-13, 425, 776, 21);
						populationSlider.setPreferredSize(new java.awt.Dimension(776, 47));
						populationSlider.addChangeListener(this);
						populationSlider.setToolTipText("use slide to browse through networks in the population");
						
					}
					{
						JPanel sliderPanel = new JPanel();
						currentNetTextField = new JTextField("      "+populationSlider.getValue());
						currentNetTextField.setEditable(true);
						currentNetTextField.addActionListener(this);
//						currentNetTextField.setSize(100, 120);
//						currentNetTextField.setMinimumSize(new Dimension(100, 120));
						currentNetTextField.setPreferredSize(new Dimension(60, 25));
						sliderPanel.add(sliderLabel, BorderLayout.WEST);
						sliderPanel.add(populationSlider, BorderLayout.CENTER);
						sliderPanel.add(currentNetTextField, BorderLayout.EAST);
						mainPanel.add(sliderPanel, BorderLayout.NORTH);
						populationPanel.add(mainPanel, BorderLayout.CENTER);
						populationPanel.add(control_panel, BorderLayout.EAST);
						
					}
				}
			}
			{
				jToolBar1 = new JToolBar();
				getContentPane().add(jToolBar1, BorderLayout.NORTH);
				{
					startButton = new JButton();
					jToolBar1.add(startButton);
					startButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/play_tiny.png")));
					startButton.setPreferredSize(new java.awt.Dimension(28, 18));
					startButton.addActionListener(this);
					startButton.setEnabled(true);
					startButton.setToolTipText("start evolutionary process");
				}
				{
					pauseButton = new JButton();
					jToolBar1.add(pauseButton);
					pauseButton.setIcon(new ImageIcon(getClass()
						.getClassLoader().getResource("img/pause_tiny.png")));
					pauseButton.addActionListener(this);
					pauseButton.setEnabled(false);
					pauseButton.setToolTipText("pause evolutionary process");
				}
				{
					stopButton = new JButton();
					jToolBar1.add(stopButton);
					stopButton.setIcon(new ImageIcon(getClass()
						.getClassLoader().getResource("img/stop_tiny.png")));
					stopButton.addActionListener(this);
					stopButton.setEnabled(false);
					stopButton.setToolTipText("terminate evolutionary process");
				}
				{
					statusTextField = new JTextField();
					jToolBar1.add(statusTextField);
					statusTextField.setText("not running");
					statusTextField.setBackground(new java.awt.Color(0,0,0));
					statusTextField.setForeground(new java.awt.Color(0,255,0));
					statusTextField.setFont(new java.awt.Font("Dialog",1,11));
					statusTextField.setEditable(false);
				}
				{
					updateGraphButton = new JButton();
					jToolBar1.add(updateGraphButton);
					updateGraphButton.setText("update");
					updateGraphButton.addActionListener(this);
					updateGraphButton.setToolTipText("update displayed network to latest state");
					updateGraphButton.setEnabled(false);
				}
			}
			{
				menuBar = new JMenuBar();
				setJMenuBar(menuBar);
				menuBar.setPreferredSize(new java.awt.Dimension(756, 25));
				{
					fileMenu = new JMenu();
					menuBar.add(fileMenu);
					fileMenu.setText("File");
					fileMenu.setPreferredSize(new java.awt.Dimension(28, 21));
					{
						loadStateMenuItem = new JMenuItem();
						fileMenu.add(loadStateMenuItem);
						loadStateMenuItem.setText("Load state ...");
						loadStateMenuItem.addActionListener(this);
					}
					{
						saveStateMenuItem = new JMenuItem();
						fileMenu.add(saveStateMenuItem);
						saveStateMenuItem.setText("Save state ...");
						saveStateMenuItem.setBounds(0, 0, 28, 21);
						saveStateMenuItem.addActionListener(this);
					}
					{
						jSeparator1 = new JSeparator();
						fileMenu.add(jSeparator1);
						jSeparator1.setBounds(0, 0, 28, 14);
					}
					{
						quitMenuItem = new JMenuItem();
						fileMenu.add(quitMenuItem);
						quitMenuItem.setText("Quit");
						quitMenuItem.setBounds(0, 0, 28, 14);
					}
				}
				chooseFilePopup = new FileChooserDialog(this);
				saveFilePopup = new FileChooserDialog(this);
				
				chooseFilePopup.getStateFileChooser().addActionListener(this);
				saveFilePopup.getStateFileChooser().addActionListener(this);
				{
					processMenu = new JMenu();
					menuBar.add(processMenu);
					processMenu.setText("Process");
				}
				{
					viewMenu = new JMenu();
					menuBar.add(viewMenu);
					viewMenu.setText("View");
				}
			}
			pack();
			this.setSize(800, 692);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public JPanel startFunction() {
//      Graph g = getGraph();
		setupGraph();
      rend = new PluggableRenderer();
      lay = new FRLayout(displayedGraph);

      model = new DefaultVisualizationModel(lay);
      model.addChangeListener(this);
      vv = new VisualizationViewer(model, rend);
      
      
      // add Shape based pick support
      vv.setPickSupport(new ShapePickSupport());
      PickedState picked_state = vv.getPickedState();
      
      affineTransformer = vv.getLayoutTransformer();
      
      // create decorators
      vcf = new SeedColor(picked_state);
      ewcs = 
          new EdgeWeightStrokeFunction(edge_weight);
      vsh = new VertexStrokeHighlight(picked_state);
      ff = new FontHandler();
      vs_none = new ConstantVertexStringer(null);
      es_none = new ConstantEdgeStringer(null);
//      vssa = new VertexShapeSizeAspect(voltages);
      show_edge = new DirectionDisplayPredicate(true, true);
      show_arrow = new DirectionDisplayPredicate(true, false);
      show_vertex = new VertexDisplayPredicate(false);

      // uses a gradient edge if unpicked, otherwise uses picked selection
      edgePaint = new GradientPickedEdgePaintFunction( new PickableEdgePaintFunction(picked_state,Color.black,Color.cyan), 
              vv, vv, picked_state);
      
      rend.setVertexPaintFunction(vcf);
      rend.setVertexStrokeFunction(vsh);
      rend.setVertexStringer(vs_none);
      rend.setVertexFontFunction(ff);
//      rend.setVertexShapeFunction(vssa);
      rend.setVertexIncludePredicate(show_vertex);
      
      rend.setEdgePaintFunction( edgePaint );
      rend.setEdgeStringer(es_none);
      rend.setEdgeFontFunction(ff);
      rend.setEdgeStrokeFunction(ewcs);
      rend.setEdgeIncludePredicate(show_edge);
      rend.setEdgeShapeFunction(new EdgeShape.Line());
      rend.setEdgeArrowPredicate(show_arrow);
      JPanel jp = new JPanel();
      jp.setLayout(new BorderLayout());
      
      vv.setBackground(Color.white);
      GraphZoomScrollPane scrollPane = new GraphZoomScrollPane(vv);
      jp.add(scrollPane, BorderLayout.CENTER);
      gm = new DefaultModalGraphMouse();
      vv.setGraphMouse(gm);
      gm.add(new PopupGraphMousePlugin());
      netStatsPanel = new NetStatsPanel();
      netStatsPanel.getNetStatsTextArea().setText("-N/A-");
      jp.add(netStatsPanel, BorderLayout.WEST);
      jp.add(addBottomControls(), BorderLayout.EAST);
      
      
      
 //      jp.add(control_panel, BorderLayout.SOUTH);
   
//      vssa.setScaling(true);

//      vv.setToolTipFunction(new VoltageTips());
//      sets tooltips texts
      vv.setToolTipFunction(new ToolTips());
      vv.setToolTipText("<html><center>Use the mouse wheel to zoom<p>Click and Drag the mouse to pan<p>Shift-click and Drag to Rotate</center></html>");
      

      
      return jp;
  }
	public void setupGraph()
    {

    	
//    	TestGraphNetworkGenerator tng = new TestGraphNetworkGenerator(p);
//    	RandomGraphNetworkGenerator rng = new RandomGraphNetworkGenerator(process);
//    	net = (GraphNetwork) rng.generateNewNetwork();
//    	net = (GraphNetwork) this.process.getPopulation().get(0);

//      Graph g = TestGraphs.generateMixedRandomGraph(edge_weight, 20, false);
//        vs = new NumberVertexValueStringer(voltages);
        es = new NumberEdgeValueStringer(edge_weight);
        
        
        for (Iterator iter = displayedGraph.getEdges().iterator(); iter.hasNext(); )
        {
        	Edge e = (Edge)iter.next();
        	if(e instanceof DimerisingEdge){
        		DimerisingEdge de = (DimerisingEdge) e;
        		edge_weight.setNumber(de, 0);
        	}
        	else if(e instanceof RegulatoryEdge){
        		RegulatoryEdge re = (RegulatoryEdge) e;
        		edge_weight.setNumber(re, re.getValue());
        	}
//        	Edge e = (Edge)iter.next();
//            
//            edge_weight.setNumber(e, new Double(Math.random()));
        }
        
        
        // collect the seeds used to define the random graph
//        Collection seeds = displayedGraph.getVertices();
//        	PredicateUtils.getVertices(g, 
//                new ContainsUserDataKeyVertexPredicate(BarabasiAlbertGenerator.SEED));
//        if (seeds.size() < 2)
//            System.out.println("need at least 2 seeds (one source, one sink)");
        
        // use these seeds as source and sink vertices, run VoltageRanker
//        boolean source = true;
//        Set sources = new HashSet();
//        Set sinks = new HashSet();
//        for (Iterator iter = seeds.iterator(); iter.hasNext(); )
//        {
//            if (source)
//                sources.add(iter.next());
//            else
//                sinks.add(iter.next());
//            source = !source;
//        }
//        VoltageRanker vr = new VoltageRanker(edge_weight, voltages, 100, 0.01);
//        vr.calculateVoltages(displayedGraph, sources, sinks);

        Set verts = displayedGraph.getVertices();
        
        // assign a transparency value of 0.9 to all vertices
        for (Iterator iter = verts.iterator(); iter.hasNext(); )
        {
            Vertex v = (Vertex)iter.next();
            transparency.setNumber(v, new MutableDouble(0.9));
        }

        // add a couple of self-loops (sanity check on rendering)
//        Vertex v = (Vertex)verts.iterator().next(); 
//        Edge e = new DirectedSparseEdge(v,v);
//        edge_weight.setNumber(e, new Double(Math.random()));
//        g.addEdge(e);
//        e = new UndirectedSparseEdge(v,v);
//        edge_weight.setNumber(e, new Double(Math.random()));
//        g.addEdge(e);
        
        
    }
	
//	private VisualizationViewer setUpViewer(){
//		displayedGraph = (GraphNetwork)population.get(0);
//		lay = new FRLayout(displayedGraph);
//		rend = new PluggableRenderer();
//		vv = new VisualizationViewer(lay,rend);
//		
//		return vis;
//	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
//		System.out.println("state changed: "+e.getSource());
		if (e.getSource() == populationSlider){
			if (process.getState() == Thread.State.RUNNABLE)
				statusTextField.setText("process running ... generation: "+process.getGeneration());
			
			population = process.getPopulation();
			
			if (population.isEmpty()){
				populationSlider.setMaximum(0);
				populationSlider.setValue(0);
				
			}
			
			if (population.size() > 0){
				populationSlider.setMaximum(population.size()-1);
				
			}
			
			if (! population.isEmpty() && !populationSlider.getValueIsAdjusting()){
//				System.out.println("graph: " +populationSlider.getValue());
				displayedGraph = (GraphNetwork)population.get(populationSlider.getValue());
				setupGraph();
				
				lay = new FRLayout(displayedGraph);
				
				model = new DefaultVisualizationModel(lay);
				
				vv.setModel(model);

				lay.update();
	            if (!vv.isVisRunnerRunning())
	                vv.init();
	            vv.repaint();
	            String s = displayedGraph.getDataCollector().statsToString();
				netStatsPanel.getNetStatsTextArea().setText(s);
				populationSlider.setToolTipText("use slide to browse through networks in the population (current="+populationSlider.getValue()+")");
//				set textfield to ID of currently displayed network
				currentNetTextField.setText(""+population.get(populationSlider.getValue()).getID());
				if (process.isPaused()){
					historyPanel.setVisible(false);
					historyPanel.updateHistory(displayedGraph);
				}
			}
		}	
		if (e.getSource() == vv){
			System.out.println("GRAPH CHANGED!!!!");
			
		}
		
		
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == startButton){
			statusTextField.setText("process running ... generation: "+process.getGeneration());
			
			population = process.getPopulation();
			
			if (population.isEmpty()){
				populationSlider.setMaximum(0);
				populationSlider.setValue(0);
			}
			
			if (population.size() > 0){
				populationSlider.setMaximum(population.size()-1);
				
			}
			
			if(!process.isPaused()){
				if (process.getState() == Thread.State.NEW 
						|| process.getState() == Thread.State.TERMINATED){
					if(process.getState() == Thread.State.TERMINATED){
						process = new DefaultProcess();
						population = process.getPopulation();
						p = process.getParameters();
					}
//					process.terminateProcess();
					process.startProcess();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					displayedGraph = new GraphNetwork(process);
					populationSlider.setMaximum(population.size()-1);
					displayedGraph = (GraphNetwork) population.get(populationSlider.getValue());
					setupGraph();
					lay = new FRLayout(displayedGraph);
					model = new DefaultVisualizationModel(lay);
					vv.setModel(model);
					lay.update();
					if (!vv.isVisRunnerRunning())
						vv.init();
					vv.repaint();
					startButton.setEnabled(false);
					pauseButton.setEnabled(true);
					stopButton.setEnabled(true);
					updateGraphButton.setEnabled(true);
					historyPanel.setVisible(false);
					String s = displayedGraph.getDataCollector().statsToString();
					netStatsPanel.getNetStatsTextArea().setText(s);
				}
			}
			else{
				if (process.getState() == Thread.State.WAITING){
					process.resumeProcess();
					startButton.setEnabled(false);
					pauseButton.setEnabled(true);
					stopButton.setEnabled(true);
					historyPanel.setVisible(false);
					if (!population.isEmpty()) {
						displayedGraph = (GraphNetwork) this.population
								.get(populationSlider.getValue());
						setupGraph();
						lay = new FRLayout(displayedGraph);
						model = new DefaultVisualizationModel(lay);
						vv.setModel(model);
						lay.update();
						if (!vv.isVisRunnerRunning())
							vv.init();
						vv.repaint();
					}	
				}
			}
		}
		
		if (e.getSource() == stopButton){
			
			process.terminateProcess();
			startButton.setEnabled(true);
			pauseButton.setEnabled(false);
			stopButton.setEnabled(false);
			historyPanel.setVisible(false);
			statusTextField.setText("not running");
		}
		
		if (e.getSource() == pauseButton){
			if(!process.isPaused()){
				process.pauseProcess();
				startButton.setEnabled(true);
				pauseButton.setEnabled(false);
				stopButton.setEnabled(true);
				historyPanel.setVisible(true);
				historyPanel.updateHistory(displayedGraph);
				statusTextField.setText("--paused at generation " +process.getGeneration()+ "--");
			}
		}
		
		if (e.getSource() == updateGraphButton){
			displayedGraph.checkGraphCorrectness();
			if (process.getState() == Thread.State.RUNNABLE)
				statusTextField.setText("process running ... generation: "+process.getGeneration());
			
//			if(historyPanel.isVisible() == true)
			if (process.isPaused()){
				historyPanel.setVisible(false);
				historyPanel.updateHistory(displayedGraph);
			}
				
			
			population = process.getPopulation();
		
			if (population.isEmpty()){
				
				populationSlider.setMaximum(0);
				populationSlider.setValue(0);
				
			}
			
			if (population.size() > 0){
				populationSlider.setMaximum(population.size()-1);
				
			}
			

			
			if (!population.isEmpty()) {
				
				displayedGraph = (GraphNetwork) population.get(populationSlider.getValue());
				
				setupGraph();
				lay = new FRLayout(displayedGraph);
				model = new DefaultVisualizationModel(lay);
				vv.setModel(model);
				lay.update();
				if (!vv.isVisRunnerRunning())
					vv.init();
				vv.repaint();
				String s = displayedGraph.getDataCollector().statsToString();
				if (process.isPaused())
					s += displayedGraph.printGraphStats();
				netStatsPanel.getNetStatsTextArea().setText(s);
				
			}			
		}
		
		if (e.getSource() == loadStateMenuItem){
			
			chooseFilePopup.setVisible(true);
		}
		
		if (e.getSource() == saveStateMenuItem){
			System.out.println("Yes...");
			saveFilePopup.setVisible(true);
		}
		
		if (e.getSource() == saveFilePopup.getStateFileChooser()){
			JFileChooser chooser = saveFilePopup.getStateFileChooser();
			System.out.println("Hossa! And now?");
			File f;
			if (chooser.getSelectedFile() != null){
				System.out.println("It's a ... File!"+chooser.getSelectedFile());
				f = (File)chooser.getSelectedFile();
				System.out.println("writing: "+f.getAbsolutePath());
				process.getFileManager().writeStateToFileCompact(process.getSimState(), f.getAbsolutePath());
			}
			System.out.println("close!");
			saveFilePopup.setVisible(false);
		}
		
		if (e.getSource() == chooseFilePopup.getStateFileChooser()){
			JFileChooser chooser = chooseFilePopup.getStateFileChooser();
			System.out.println("Hooray! And now?");
			File f;
			if (chooser.getSelectedFile() != null){
				System.out.println("It's a ... File!"+chooser.getSelectedFile());
				f = (File)chooser.getSelectedFile();
				process.terminateProcess();
				displayedGraph.removeAllVertices();
				process = new DefaultProcess(process.getParameters());
				AbstractSimulationState loadedState = process.getFileManager().loadStateCompact(f.getPath());
				if (loadedState != null){
					process.setSimState(loadedState);
					population = process.getPopulation();
					System.out.println("population = "+population.size());
					populationSlider.setMaximum(population.size()-1);
					p = process.getParameters();
//					process.startFrom(process.getSimState());
					process.pauseProcess();
					
					displayedGraph = (GraphNetwork) process.getSimState().getPopulation().get(0);
					setupGraph();
					lay = new FRLayout(displayedGraph);
					model = new DefaultVisualizationModel(lay);
					vv.setModel(model);
					lay.update();
					if (!vv.isVisRunnerRunning())
						vv.init();
					vv.repaint();
					startButton.setEnabled(true);
					pauseButton.setEnabled(false);
					stopButton.setEnabled(false);
					updateGraphButton.setEnabled(true);
				}
				else{
					System.out.println("Couldn't load state!!!");
				}
				
			}
			chooseFilePopup.setVisible(false);
		}
		
		if (e.getSource() == currentNetTextField){
			JTextField tf = (JTextField)e.getSource();
			String text = tf.getText();
//			parse integer
			char c;
			for (int i = 0; i < text.length(); i++){
				c = text.charAt(i);
				if (c < '0' || c > '9'){
					return;
				}
			}
			int number = Integer.parseInt(text);
//			search for network ID
			int index = -1;
			for (int i = 0; i < population.size(); i++){
				Network n = population.get(i);
				if (n.getID() == number){
					index = i;
					break;
				}
			}
			if (index == -1)
				return;
			populationSlider.setValue(index);
			
		}
		
		
		if (e.getSource() instanceof AbstractButton){
			AbstractButton source = (AbstractButton)e.getSource();
	        if (source == v_color)
	        {
	            vcf.setSeedColoring(source.isSelected());
	        }
	        else if (source == e_color)
	        {
	            ewcs.setWeighted(source.isSelected());
	        }
	        else if (source == v_stroke) 
	        {
	            vsh.setHighlight(source.isSelected());
	        }
	        else if (source == v_labels)
	        {
	            if (source.isSelected())
	                rend.setVertexStringer(vs);
	            else
	            	rend.setVertexStringer(vs_none);
	        }
	        else if (source == e_labels)
	        {
	            if (source.isSelected())
	            	rend.setEdgeStringer(es);
	            else
	            	rend.setEdgeStringer(es_none);
	        }
	        else if (source == e_uarrow_pred)
	        {
	            show_arrow.showUndirected(source.isSelected());
	        }
	        else if (source == e_darrow_pred)
	        {
	            show_arrow.showDirected(source.isSelected());
	        }
	        else if (source == font)
	        {
	            ff.setBold(source.isSelected());
	        }
	        else if (source == v_shape)
	        {
	//            vssa.useFunnyShapes(source.isSelected());
	        }
	        else if (source == v_size)
	        {
	//            vssa.setScaling(source.isSelected());
	        }
	        else if (source == v_aspect)
	        {
	//            vssa.setStretching(source.isSelected());
	        }
	        else if (source == e_line) 
	        {
	            if(source.isSelected())
	            {
	            	rend.setEdgeShapeFunction(new EdgeShape.Line());
	            }
	        }
	        else if (source == e_wedge)
	        {
	            if (source.isSelected())
	            	rend.setEdgeShapeFunction(new EdgeShape.Wedge(10));
	        }
	//        else if (source == e_bent) 
	//        {
	//            if(source.isSelected())
	//            {
	//                pr.setEdgeShapeFunction(new EdgeShape.BentLine());
	//            }
	//        }
	        else if (source == e_quad) 
	        {
	            if(source.isSelected())
	            {
	            	rend.setEdgeShapeFunction(new EdgeShape.QuadCurve());
	            }
	        }
	        else if (source == e_cubic) 
	        {
	            if(source.isSelected())
	            {
	            	rend.setEdgeShapeFunction(new EdgeShape.CubicCurve());
	            }
	        }
	       else if (source == e_show_d)
	        {
	            show_edge.showDirected(source.isSelected());
	        }
	        else if (source == e_show_u)
	        {
	            show_edge.showUndirected(source.isSelected());
	        }
	        else if (source == v_small)
	        {
	            show_vertex.filterSmall(source.isSelected());
	        }
	        else if(source == zoom_at_mouse)
	        {
	            gm.setZoomAtMouse(source.isSelected());
	        } 
	        else if (source == no_gradient) {
				if (source.isSelected()) {
					gradient_level = GRADIENT_NONE;
				}
	//		} else if (source == gradient_absolute) {
	//			if (source.isSelected()) {
	//				gradient_level = GRADIENT_ABSOLUTE;
	//			}
			} 
	        else if (source == gradient_relative) {
				if (source.isSelected()) {
					gradient_level = GRADIENT_RELATIVE;
				}
			}
	        else if (source == fill_edges)
	        {
	            edgePaint.useFill(source.isSelected());
	        }
	        vv.repaint();
		}
	}

	protected JPanel addBottomControls(/*final JPanel jp*/) 
    {
		
    	/*final JPanel*/ control_panel = new JPanel();
//        jp.add(control_panel, BorderLayout.SOUTH);
        control_panel.setLayout(new BorderLayout());
//        control_panel.setLayout(new FlowLayout());
        final Box vertex_panel = Box.createVerticalBox();
        vertex_panel.setBorder(BorderFactory.createTitledBorder("Vertices"));
        final Box edge_panel = Box.createVerticalBox();
        edge_panel.setBorder(BorderFactory.createTitledBorder("Edges"));
        final Box both_panel = Box.createVerticalBox();

        control_panel.add(vertex_panel, BorderLayout.NORTH);
        control_panel.add(edge_panel, BorderLayout.SOUTH);
        control_panel.add(both_panel, BorderLayout.CENTER);
        
        // set up vertex controls
        v_color = new JCheckBox("dimer coloring");
        v_color.addActionListener(this);
       
        v_stroke = new JCheckBox("<html>vertex selection<p>stroke highlighting</html>");
        v_stroke.addActionListener(this);
        v_labels = new JCheckBox("show vertex ranks (voltages)");
        v_labels.addActionListener(this);
        v_shape = new JCheckBox("vertex degree shapes");
        v_shape.addActionListener(this);
        v_size = new JCheckBox("vertex voltage size");
        v_size.addActionListener(this);
        v_size.setSelected(false);
        v_aspect = new JCheckBox("vertex degree ratio stretch");
        v_aspect.addActionListener(this);
        v_small = new JCheckBox("filter vertices of degree < " + VertexDisplayPredicate.MIN_DEGREE);
        v_small.addActionListener(this);

        vertex_panel.add(v_color);
        vertex_panel.add(v_stroke);
//        vertex_panel.add(v_labels);
//        vertex_panel.add(v_shape);
//        vertex_panel.add(v_size);
//        vertex_panel.add(v_aspect);
        vertex_panel.add(v_small);
        
        // set up edge controls
		JPanel gradient_panel = new JPanel(new GridLayout(1, 0));
        gradient_panel.setBorder(BorderFactory.createTitledBorder("Edge paint"));
		no_gradient = new JRadioButton("Solid color");
		no_gradient.addActionListener(this);
		no_gradient.setSelected(true);
//		gradient_absolute = new JRadioButton("Absolute gradient");
//		gradient_absolute.addActionListener(this);
		gradient_relative = new JRadioButton("Gradient");
		gradient_relative.addActionListener(this);
		ButtonGroup bg_grad = new ButtonGroup();
		bg_grad.add(no_gradient);
		bg_grad.add(gradient_relative);
		//bg_grad.add(gradient_absolute);
		gradient_panel.add(no_gradient);
		//gradientGrid.add(gradient_absolute);
		gradient_panel.add(gradient_relative);
        
        JPanel shape_panel = new JPanel(new GridLayout(3,2));
        shape_panel.setBorder(BorderFactory.createTitledBorder("Edge shape"));
        e_line = new JRadioButton("line");
        e_line.addActionListener(this);
        e_line.setSelected(true);
//        e_bent = new JRadioButton("bent line");
//        e_bent.addActionListener(this);
        e_wedge = new JRadioButton("wedge");
        e_wedge.addActionListener(this);
        e_quad = new JRadioButton("quad curve");
        e_quad.addActionListener(this);
        e_cubic = new JRadioButton("cubic curve");
        e_cubic.addActionListener(this);
        ButtonGroup bg_shape = new ButtonGroup();
        bg_shape.add(e_line);
//        bg.add(e_bent);
        bg_shape.add(e_wedge);
        bg_shape.add(e_quad);
        bg_shape.add(e_cubic);
        shape_panel.add(e_line);
//        shape_panel.add(e_bent);
        shape_panel.add(e_wedge);
        shape_panel.add(e_quad);
        shape_panel.add(e_cubic);
        fill_edges = new JCheckBox("fill edge shapes");
        fill_edges.setSelected(false);
        fill_edges.addActionListener(this);
        shape_panel.add(fill_edges);
        shape_panel.setOpaque(true);
        e_color = new JCheckBox("edge weight highlighting");
        e_color.addActionListener(this);
        e_labels = new JCheckBox("show edge weights");
        e_labels.addActionListener(this);
        e_uarrow_pred = new JCheckBox("undirected");
        e_uarrow_pred.addActionListener(this);
        e_darrow_pred = new JCheckBox("directed");
        e_darrow_pred.addActionListener(this);
        e_darrow_pred.setSelected(true);
        JPanel arrow_panel = new JPanel(new GridLayout(1,0));
        arrow_panel.setBorder(BorderFactory.createTitledBorder("Show arrows"));
        arrow_panel.add(e_uarrow_pred);
        arrow_panel.add(e_darrow_pred);
        
        e_show_d = new JCheckBox("directed");
        e_show_d.addActionListener(this);
        e_show_d.setSelected(true);
        e_show_u = new JCheckBox("undirected");
        e_show_u.addActionListener(this);
        e_show_u.setSelected(true);
        JPanel show_edge_panel = new JPanel(new GridLayout(1,0));
        show_edge_panel.setBorder(BorderFactory.createTitledBorder("Show edges"));
        show_edge_panel.add(e_show_u);
        show_edge_panel.add(e_show_d);
        
        shape_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        edge_panel.add(shape_panel);
        gradient_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        edge_panel.add(gradient_panel);
        show_edge_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        edge_panel.add(show_edge_panel);
        arrow_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        edge_panel.add(arrow_panel);
        
        e_color.setAlignmentX(Component.LEFT_ALIGNMENT);
        edge_panel.add(e_color);
        e_labels.setAlignmentX(Component.LEFT_ALIGNMENT);
        edge_panel.add(e_labels);

        // set up zoom controls
        zoom_at_mouse = new JCheckBox("<html><center>zoom at mouse<p>(wheel only)</center></html>");
        zoom_at_mouse.addActionListener(this);
        
        final ScalingControl scaler = new CrossoverScalingControl();

        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });

        Box zoomPanel = Box.createVerticalBox();
        zoomPanel.setBorder(BorderFactory.createTitledBorder("Zoom"));
        plus.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomPanel.add(plus);
        minus.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomPanel.add(minus);
        zoom_at_mouse.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomPanel.add(zoom_at_mouse);
        
        // add font and zoom controls to center panel
        font = new JCheckBox("bold text");
        font.addActionListener(this);
        font.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//        both_panel.add(zoomPanel);
//        both_panel.add(font);
        
        JComboBox modeBox = gm.getModeComboBox();
        modeBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel modePanel = new JPanel(new BorderLayout()) {
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        modePanel.setBorder(BorderFactory.createTitledBorder("Mouse Mode"));
        modePanel.add(modeBox);
        both_panel.add(modePanel);

//        v_color.setSelected(true);
//        
//        e_quad.setSelected(true);
        
        return control_panel;
    }

	private final class SeedColor implements VertexPaintFunction
    {
        protected PickedInfo pi;
        protected final static float dark_value = 0.5f;
        protected final static float light_value = 0.2f;
        protected boolean seed_coloring;
        
        public SeedColor(PickedInfo pi)
        {
            this.pi = pi;
            seed_coloring = false;
        }

        public void setSeedColoring(boolean b)
        {
            this.seed_coloring = b;
        }
        
        public Paint getDrawPaint(Vertex v)
        {
            return Color.ORANGE;
        }
        
        public synchronized Paint getFillPaint(Vertex v)
        {
            float alpha = transparency.getNumber(v).floatValue();
            if (pi.isPicked(v))
            {
                return new Color(1f, 1f, 0, alpha); 
            }
            else
            {
//                if (seed_coloring && v.containsUserDatumKey(BarabasiAlbertGenerator.SEED))
//            	int index = ng.getVertexVector().get(0).indexOf(v);
            	int index = displayedGraph.getIndexOf((VertexElement)v);
            	if (seed_coloring && displayedGraph.getElementVector().get(index).isDimer())
                {
                    Color dark = new Color(0, 0, dark_value, alpha);
                    Color light = new Color(0, 0, light_value, alpha);
                    return new GradientPaint( 0, 0, dark, 10, 0, light, true);
                }
                else
                    return new Color(1f, 0, 0, alpha);
            }
                
        }
    }
    
    private final static class EdgeWeightStrokeFunction
    implements EdgeStrokeFunction
    {
        protected static final Stroke basic = new BasicStroke(1);
        protected static final Stroke heavy = new BasicStroke(2);
        protected static final Stroke dotted = PluggableRenderer.DOTTED;
        
        protected boolean weighted = false;
        protected NumberEdgeValue edge_weight;
        
        public EdgeWeightStrokeFunction(NumberEdgeValue edge_weight)
        {
        	this.edge_weight = edge_weight;
        }
        
        public void setWeighted(boolean weighted)
        {
            this.weighted = weighted;
        }
        
        public Stroke getStroke(Edge e)
        {
            if (weighted)
            {
                if (drawHeavy(e))
                    return heavy;
                else
                    return dotted;
            }
            else
                return basic;
        }
        
        protected synchronized boolean drawHeavy(Edge e)
        {
//            double value = edge_weight.getNumber(e).doubleValue();
//            if (value > 0.7)
//            	return true;
//            else
//                return false;
        	
//        	VertexElement v1 = (VertexElement)e.getEndpoints().getFirst();
//        	VertexElement v2 = (VertexElement)e.getEndpoints().getSecond();
//        	v1.updateConnections();
//        	v2.updateConnections();
//        	
//        	boolean dimerising = false;
//        	
//        	if (v1.isDimer() ^ v2.isDimer()){
//        		if (v2.isDimer()){
//        			MyVector<OnewayInteraction> inputs = v2.getDimInputs();
//					for (OnewayInteraction input:inputs) {
//						if (input.getElement() == v1){
//							dimerising = true;
//							break;
//						}
//					}        			
//        		}
//        		if (v1.isDimer()){
//        			MyVector<OnewayInteraction> inputs = v1.getDimInputs();
//        			for (OnewayInteraction input:inputs) {
//	        			if (input.getElement() == v2){
//	        				dimerising = true;
//	        				break;
//	        			}
//        			}
//        		}
//        	}
        	
        	return (e instanceof RegulatoryEdge)? true:false;
//        	return dimerising? false:true;
        }
        
    }
    
    private final static class VertexStrokeHighlight implements VertexStrokeFunction
    {
        protected boolean highlight = false;
        protected Stroke heavy = new BasicStroke(5);
        protected Stroke medium = new BasicStroke(3);
        protected Stroke light = new BasicStroke(1);
        protected PickedInfo pi;
        
        public VertexStrokeHighlight(PickedInfo pi)
        {
            this.pi = pi;
        }
        
        public void setHighlight(boolean highlight)
        {
            this.highlight = highlight;
        }
        
        public Stroke getStroke(Vertex v)
        {
            if (highlight)
            {
                if (pi.isPicked(v))
                    return heavy;
                else
                {
                    for (Iterator iter = v.getNeighbors().iterator(); iter.hasNext(); )
                    {
                        Vertex w = (Vertex)iter.next();
                        if (pi.isPicked(w))
                            return medium;
                    }
                    return light;
                }
            }
            else{
            	if (((VertexElement)v).isSink())
            		return heavy;
            	else
            		return light; 
            }
               
        }
    }
    
    private final static class FontHandler implements VertexFontFunction, EdgeFontFunction
    {
        protected boolean bold = false;
        Font f = new Font("Helvetica", Font.PLAIN, 12);
        Font b = new Font("Helvetica", Font.BOLD, 12);
        
        public void setBold(boolean bold)
        {
            this.bold = bold;
        }
        
        public Font getFont(Vertex v)
        {
            if (bold)
                return b;
            else
                return f;
        }
        
        public Font getFont(Edge e)
        {
            if (bold)
                return b;
            else 
                return f;
        }
    }
    
    private final static class DirectionDisplayPredicate implements Predicate
    {
        protected boolean show_d;
        protected boolean show_u;
        
        public DirectionDisplayPredicate(boolean show_d, boolean show_u)
        {
            this.show_d = show_d;
            this.show_u = show_u;
        }
        
        public void showDirected(boolean b)
        {
            show_d = b;
        }
        
        public void showUndirected(boolean b)
        {
            show_u = b;
        }
        
        public boolean evaluate(Object arg0)
        {
            if (arg0 instanceof DirectedEdge && show_d)
                return true;
            if (arg0 instanceof UndirectedEdge && show_u)
                return true;
            return false;
        }
    }
    
    private final static class VertexDisplayPredicate implements Predicate
    {
        protected boolean filter_small;
        protected final static int MIN_DEGREE = 4;
        
        public VertexDisplayPredicate(boolean filter)
        {
            this.filter_small = filter;
        }
        
        public void filterSmall(boolean b)
        {
            filter_small = b;
        }
        
        public boolean evaluate(Object arg0)
        {
            Vertex v = (Vertex)arg0;
            if (filter_small)
                return (v.degree() >= MIN_DEGREE);
            else
                return true;
        }
    }
    
    /**
     * Controls the shape, size, and aspect ratio for each vertex.
     * 
     * @author Joshua O'Madadhain
     */
    private final static class VertexShapeSizeAspect 
    extends AbstractVertexShapeFunction 
    implements VertexSizeFunction, VertexAspectRatioFunction
    {
        protected boolean stretch = false;
        protected boolean scale = false;
        protected boolean funny_shapes = false;
        protected NumberVertexValue voltages;
        
        public VertexShapeSizeAspect(NumberVertexValue voltages)
        {
            this.voltages = voltages;
            setSizeFunction(this);
            setAspectRatioFunction(this);
        }
        
        public void setStretching(boolean stretch)
        {
            this.stretch = stretch;
        }
        
        public void setScaling(boolean scale)
        {
            this.scale = scale;
        }
        
        public void useFunnyShapes(boolean use)
        {
            this.funny_shapes = use;
        }
        
        public int getSize(Vertex v)
        {
            if (scale)
                return (int)(voltages.getNumber(v).doubleValue() * 30) + 20;
            else
                return 20;
        }
        
        public float getAspectRatio(Vertex v)
        {
            if (stretch)
                return (float)(v.inDegree() + 1) / (v.outDegree() + 1);
            else
                return 1.0f;
        }
        
        public Shape getShape(Vertex v)
        {
            if (funny_shapes)
            {
                if (v.degree() < 5)
                {
                    int sides = Math.max(v.degree(), 3);
                    return factory.getRegularPolygon(v, sides);
                }
                else
                    return factory.getRegularStar(v, v.degree());
            }
            else
                return factory.getEllipse(v);
        }
        
    }
    
    /**
     * a GraphMousePlugin that offers popup
     * menu support
     */
    protected class PopupGraphMousePlugin extends AbstractPopupGraphMousePlugin implements MouseListener {
        
        public PopupGraphMousePlugin() {
            this(MouseEvent.BUTTON3_MASK);
        }
        public PopupGraphMousePlugin(int modifiers) {
            super(modifiers);
        }
        
        /**
         * If this event is over a Vertex, pop up a menu to
         * allow the user to increase/decrease the voltage
         * attribute of this Vertex
         * @param e
         */
        protected void handlePopup(MouseEvent e) {
            final VisualizationViewer vv = 
                (VisualizationViewer)e.getSource();
            Point2D p = vv.inverseViewTransform(e.getPoint());
            
            PickSupport pickSupport = vv.getPickSupport();
            if(pickSupport != null) {
                final Vertex v = pickSupport.getVertex(p.getX(), p.getY());
                if(v != null) {
                    JPopupMenu popup = new JPopupMenu();
                    popup.add(new AbstractAction("Decrease Transparency") {
                        public void actionPerformed(ActionEvent e) {
                            MutableDouble value = (MutableDouble)transparency.getNumber(v);
                            value.setDoubleValue(Math.min(1, value.doubleValue() + 0.1));
                            vv.repaint();
                        }
                    });
                    popup.add(new AbstractAction("Increase Transparency"){
                        public void actionPerformed(ActionEvent e) {
                            MutableDouble value = (MutableDouble)transparency.getNumber(v);
                            value.setDoubleValue(Math.max(0, value.doubleValue() - 0.1));
                            vv.repaint();
                        }
                    });
                    popup.show(vv, e.getX(), e.getY());
                } else {
                    final Edge edge = pickSupport.getEdge(p.getX(), p.getY());
                    if(edge != null) {
                        JPopupMenu popup = new JPopupMenu();
                        popup.add(new AbstractAction(edge.toString()) {
                            public void actionPerformed(ActionEvent e) {
                                System.err.println("got "+edge);
                            }
                        });
                        popup.show(vv, e.getX(), e.getY());
                       
                    }
                }
            }
        }
    }
    
    public class ToolTips extends DefaultToolTipFunction {
        
        public String getToolTipText(Vertex v) {
        	String s = "<html>"+v.toString()+"<p>";
        	s += ((VertexElement)v).getFunction().getClass().getSimpleName()+" <p> ";
        	if (process.isPaused()){
        		
        		if (((VertexElement)v).isDimer()){
        			if (((VertexElement)v).getFunction() instanceof PoissonLastingDimerRule){
        				s += "stable: "+((PoissonLastingDimerRule)((VertexElement)v).getFunction()).getStableDuration()+" timesteps<p>";
        			}
        			if (((VertexElement)v).getFunction() instanceof VaryingPoissonLastingDimerRule){
        				s += "stable: "+((VaryingPoissonLastingDimerRule)((VertexElement)v).getFunction()).getStableDuration()+" timesteps<p>";
        			}
        			s += "dimerising inputs:<p>";
        			MyVector<OnewayInteraction> dimIns = ((VertexElement)v).getDimInputs();
        			for (OnewayInteraction input:dimIns){
        				s += ((VertexElement) input.getElement()).toString()+ " \n ";
        			}
        		}else{
        			s += "regulatory inputs:<p>";
        			MyVector<OnewayInteraction> regIns = ((VertexElement)v).getRegInputs();
        			for (OnewayInteraction input:regIns){
        				s += ((VertexElement) input.getElement()).toString()+ "(" +input.getValue()+ ") \n ";
        			}
        		}
        	s += "</html>";
       
        	}
           return s;
        }
        public String getToolTipText(Edge edge) {
            return edge.toString();
        }
    }
    
    public class GradientPickedEdgePaintFunction extends GradientEdgePaintFunction 
    {
        private PickedInfo pi;
        private EdgePaintFunction defaultFunc;
        private final Predicate self_loop = SelfLoopEdgePredicate.getInstance();
        protected boolean fill_edge = false;
        
        public GradientPickedEdgePaintFunction( EdgePaintFunction defaultEdgePaintFunction, HasGraphLayout vv, 
                LayoutTransformer transformer, PickedInfo pi ) 
        {
            super(Color.WHITE, Color.BLACK, vv, transformer);
            this.defaultFunc = defaultEdgePaintFunction;
            this.pi = pi;
        }
        
        public void useFill(boolean b)
        {
            fill_edge = b;
        }
        
        public Paint getDrawPaint(Edge e) {
            if (gradient_level == GRADIENT_NONE) {
            	
                return defaultFunc.getDrawPaint(e);
            } else {
                return super.getDrawPaint(e);
            }
        }
        
        protected Color getColor2(Edge e)
        {
//            return pi.isPicked(e)? Color.CYAN : c2;
//        	if(e instanceof RegulatoryEdge){
//        		if (((RegulatoryEdge)e).getValue() > 0){
//        			System.out.println("> 0");
//        			return Color.GREEN;
//        		}
//        		if (((RegulatoryEdge)e).getValue() < 0){
//        			return Color.RED;
//        		}
//        	}
        	System.out.println("picked!");
        	VertexElement v1 = (VertexElement) e.getEndpoints().getFirst();
        	VertexElement v2 = (VertexElement) e.getEndpoints().getSecond();
        	byte value = 0;
			value = displayedGraph.getInteractions().get(displayedGraph.getIndexOf(v2), displayedGraph.getIndexOf(v1));
			if (value == 0){
				System.out.println("edge not found in interactions! remove...");
				displayedGraph.removeEdge(e);
			}
        	
        	return pi.isPicked(e)? Color.CYAN : c2;
        }
        
        public Paint getFillPaint(Edge e)
        {
            if (self_loop.evaluate(e) || !fill_edge)
                return null;
            else
                return getDrawPaint(e);
        }
        
    }

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
		System.out.println("itemStateChanged:"+e.getSource());
		
	}

}
