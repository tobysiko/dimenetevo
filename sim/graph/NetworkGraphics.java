/**
 * 
 */
package sim.graph;

import sim.datatype.MyVector;
import sim.datatype.Network;
import sim.datatype.NetworkVector;
import sim.datatype.OnewayInteraction;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseGraph;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;

/**
 * @author sikosek
 *
 */
public class NetworkGraphics {
	
	private MyVector<Graph> graphs;
	private MyVector<MyVector<Vertex>> vertexVector;
	
	public NetworkGraphics(){
		graphs = new MyVector<Graph>();
		vertexVector = new MyVector<MyVector<Vertex>>();
	}
	public void convertToGraphs(NetworkVector pop){
		for(Network net:pop){
			addGraph(net);
		}
	}
	
	
	public void addGraph(Network net){
		SparseGraph graph = new SparseGraph();
		MyVector<Vertex> verteces = new MyVector<Vertex>();
		for(int i = 0; i < net.getElementVector().size(); i++){
			Vertex v = new SparseVertex();
			if (net.getElementVector().get(i).isDimer()){
				
			}
			graph.addVertex(v);
			verteces.add(v);
		}
		
		for(int i = 0; i < net.getElementVector().size(); i++){
			
			if (!net.getElementVector().get(i).isDimer()){
				MyVector<OnewayInteraction> inputs = net.getRegulatoryInputsOf(net.get(i));
				for (OnewayInteraction input:inputs){
					input.getElementIndex();
					DirectedSparseEdge e = new DirectedSparseEdge(verteces.get(input.getElementIndex()), verteces.get(i));
					graph.addEdge(e);
				}
			}
			else{
				MyVector<OnewayInteraction> inputs = net.getDimerisingInputsOf(net.get(i));
				for (OnewayInteraction input:inputs){
					input.getElementIndex();
					UndirectedSparseEdge e = new UndirectedSparseEdge(verteces.get(input.getElementIndex()), verteces.get(i));
					
					graph.addEdge(e);
				}
			}
		}
		
		graphs.add(graph);
		vertexVector.add(verteces);
	}
	public MyVector<Graph> getGraphs() {
		return graphs;
	}
	public void setGraphs(MyVector<Graph> graphs) {
		this.graphs = graphs;
	}
	public MyVector<MyVector<Vertex>> getVertexVector() {
		return vertexVector;
	}
	public void setVertexVector(MyVector<MyVector<Vertex>> indexedGraph) {
		this.vertexVector = indexedGraph;
	}
}
