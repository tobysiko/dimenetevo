package sim.datatype;

import java.io.Serializable;



/**
 * This class contains a NxN matrix that stores the interactions between all N elements (mono- and dimeric transcription factors) in the network.
 * Values are of type <tt>byte</tt> instead of <tt>int</tt> in order to save memory. 
 * <br>The elements in the columns receive inputs from the elements in the rows.
 * <br>This class also contains a reference to the <tt>ElementVector</tt> of its Network.
 * 
 * <p>The following values are valid:
 * 
 * <p>'0' ==> no interaction
 * 
 * <p>For regulatory inputs:
 * <br> '1' ==> activation by a monomer
 * <br> '2' ==> activation by a dimer
 * <br>'-1' ==> repression by a monomer
 * <br>'-2' ==> repression by a dimer
 * 
 * <p>For dimerising inputs:
 * <br> '3' ==> input is part of a heterodimer
 * <br> '4' ==> input forms a homodimer
 *  
 * <p>NOTE: there are no regulatory inputs to dimers directly, only via their monomers.
 *  
 * @author sikosek
 * @version 0.1
 * 
 */
public class InteractionMatrix implements Cloneable, Serializable{
	private byte[][] matrix;
	
//	private Network net;
	
//	Vector-based version of the matrix:
//	private Vector<Vector<Byte>> m;
//	
//	private void createMatrix(int n){
//		m = new Vector<Vector<Byte>>(n);
//		for(Vector<Byte> v:m){
//			v = new Vector<Byte>(n);
//		}
//	}
	
	private ElementVector elements;
	
	private static final long serialVersionUID = 1L;

	/**
	 * The parameterless constructor creates a <tt>byte</tt> matrix of size zero and generates a new empty <tt>ElementVector</tt>.
	 */
//	public InteractionMatrix (Network net) {
//		this.net = net;
//		matrix = new byte[0][0];
//		if (this.elements == null) this.elements = new ElementVector();
//	}
	
	/**
	 * Creates a NxN <tt>byte</tt> matrix and an <tt>ElementVector</tt> of size N.
	 * @param n the number N of elements.
	 */
//	public InteractionMatrix(Network net, int n){
//		this.net = net;
//		matrix = new byte[n][n];
//		if (this.elements == null) this.elements = new ElementVector(n);
//	}
	
	/**
	 * @param elements
	 */
	public InteractionMatrix(Network net) {
//		this.net = net;
		int n = net.getElementVector().size();
		matrix = new byte[n][n];
		this.elements = net.getElementVector();
	}
	
//	/**
//	 * @param n
//	 * @param elements
//	 */
//	public InteractionMatrix(int n, ElementVector elements){
//		matrix = new byte[n][n];
//		this.elements = elements;
//	}
	
	/**
	 * Sets the value of the matrix cell specified by column index i and row index j.
	 * @param i index of the target element that receives input from j = the column index.
	 * @param j index of source element that provides an input for i = the row index.
	 * @param value see class description for valid values.
	 */
	public void set(int i, int j, byte value){
		matrix[i][j] = value;
	}
	
	/**
	 * Sets matrix cell value and converts a boolean value to byte according to properties of input j (dimer or not).
	 * @param i index of the target element that receives input from j = the column index.
	 * @param j index of source element that provides an input for i = the row index.
	 * @param value true = activating input, false = inhibiting input.
	 */
	public void set(int i, int j, boolean value){
		byte bytevalue;
		if(this.elements.get(j).isDimer()) bytevalue = 2;
		else bytevalue = 1;
		if(!value) bytevalue *= -1;
		matrix[i][j] = bytevalue;
	}
	
	/**
	 * Returns the value at column i and row j.
	 * @param i the column index.	
	 * @param j the row index.
	 * @return the value at the specified position in the matrix.
	 */
	public byte get(int i, int j){
		return matrix[i][j];
	}
	
	/**
	 * Checks whether or not the matrix has a value different from '0' in the specified cell.
	 * @param i the column index.
	 * @param j the row index.
	 * @return <tt>true</tt> if specified cell contains value <> 0, <tt>false</tt> otherwise.
	 */
	public boolean hasValueAt(int i, int j){
		if (this.get(i,j) != 0) return true;
		else return false;
	}
	
	/**
	 * Returns N of a NxN matrix.
	 * @return the number of columns(= rows) of the matrix.
	 */
	public int length(){
		return matrix.length;
	}
	
	/**
	 * Removes one Element from the NxN matrix by deleting the corresponding row and column. N will be reduced to N-1.
	 * @param index The index of the element that will be removed.
	 */
	public void remove(int index){
		byte[][] newmatrix = new byte[matrix.length-1][matrix.length-1];//create new matrix that is smaller by one
		for(int i = 0; i < index; i++){
			for(int j = 0; j < index; j++){
				newmatrix[i][j] = matrix[i][j];
			}
			for(int j = index +1; j < matrix.length; j++){
				newmatrix[i][j-1] = matrix[i][j];
			}
		}
		for(int i = index +1; i < matrix.length; i++){
			for(int j = 0; j < index; j++){
				newmatrix[i-1][j] = matrix[i][j];
			}
			for(int j = index +1; j < matrix.length; j++){
				newmatrix[i-1][j-1] = matrix[i][j];
			}
		}
		matrix = newmatrix;
	}
	
	public int add(){
		byte[][] newmatrix = new byte[matrix.length+1][matrix.length+1];//create new matrix that is larger by one
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix.length; j++){
				newmatrix[i][j] = matrix[i][j];
			}
		}
		matrix = newmatrix;
		return matrix.length-1;//index of last=new element
	}
	
	//CAREFUL: mind the indices! don't swap rows and columns!!
	public String toString(){
		String str = new String("\t");
		for (int i = 0; i < this.length(); i++){
			str += i + "\t";
		}
		str += "\n";
		for (int i = 0; i < this.length()+1; i++){
			str += "------------" + "\t";
		}
		for(int j = 0; j < this.length(); j++){
			str += "\n" + j + " |\t";
			for (int i = 0; i < this.length(); i++){
				str += this.get(i, j) + "\t";
			}
		}
		return str;
	}
	
	public Object clone() throws CloneNotSupportedException{
		InteractionMatrix clone = (InteractionMatrix) super.clone();
		clone.matrix = matrix.clone();
		for (int i = 0; i < matrix.length; i++){
			clone.matrix[i] = matrix[i].clone();
		}
		return clone;
	}

	public ElementVector getElements() {
		return elements;
	}

	public void setElements(ElementVector elements) {
		this.elements = elements;
	}
}
