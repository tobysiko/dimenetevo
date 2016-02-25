/**
 * 
 */
package sim.graph;

import java.io.Serializable;

import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;

/**
 * @author sikosek
 *
 */
public class DimerisingEdge extends UndirectedSparseEdge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param from
	 * @param to
	 */
	public DimerisingEdge(Vertex from, Vertex to) {
		super(from, to);
		// TODO Auto-generated constructor stub
	}

}
