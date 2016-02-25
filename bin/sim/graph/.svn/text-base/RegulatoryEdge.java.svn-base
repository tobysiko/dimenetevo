/**
 * 
 */
package sim.graph;

import java.io.Serializable;

import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;

/**
 * @author sikosek
 *
 */
public class RegulatoryEdge extends DirectedSparseEdge implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected byte value;
	/**
	 * @param from
	 * @param to
	 */
	public RegulatoryEdge(Vertex from, Vertex to) {
		super(from, to);
	
	}
	public RegulatoryEdge(Vertex from, Vertex to, byte value) {
		super(from, to);
		this.value = value;
	
	}
	public byte getValue() {
		return value;
	}
	public void setValue(byte value) {
		this.value = value;
	}

}
