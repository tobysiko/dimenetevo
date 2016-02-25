package sim.datatype;

import java.io.Serializable;

import sim.Parameters;
import sim.operation.function.BooleanFunction;


/** 
 * @uml.stereotype uml_id="Standard::Type" 
 * @uml.stereotype uml_id="Standard::Realization" 
 */
public class MatrixElement extends AbstractElement implements Serializable{

	private static final long serialVersionUID = 1L;

	public MatrixElement(Network net, Parameters p){
		super(net, p);
		
	}
	
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}
}
