/**
 * 
 */
package sim.datatype;

import java.io.Serializable;


/**
 * @author sikosek
 *
 */
public class OnewayInteraction implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3778847053142266733L;
	private byte value;
	private int elementIndex;
	private Element element;
	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public OnewayInteraction(byte value, int index){
		this.value = value;
		this.elementIndex = index;
	}
	public OnewayInteraction(byte value, Element element){
		this.value = value;
		this.element = element;
	}
	public OnewayInteraction(byte value, int index, Element element){
		this.value = value;
		this.elementIndex = index;
		this.element = element;
	}
	public int getElementIndex() {
		return elementIndex;
	}
	public void setElementIndex(int element) {
		this.elementIndex = element;
	}
	public byte getValue() {
		return value;
	}
	public void setValue(byte value) {
		this.value = value;
	}
	public Object clone() throws CloneNotSupportedException{
		OnewayInteraction clone = (OnewayInteraction) super.clone();
	
		return clone;
	}
}
