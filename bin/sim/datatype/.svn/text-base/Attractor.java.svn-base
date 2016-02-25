package sim.datatype;
import java.io.Serializable;


/**
 * 
 */

/**
 * @author sikosek
 *
 */
public class Attractor extends TimeStepSeries implements Serializable,
		Cloneable {


	private static final long serialVersionUID = 1L;
	
	private String attractorProfile;
	private byte[] transitionProfile;
	private int netsize;

	public int getElements() {
		return netsize;
	}

	public void setElements(int elements) {
		this.netsize = elements;
	}

	public byte[] getTransitionProfile() {
		return transitionProfile;
	}

	public void setTransitionProfile(byte[] transitionProfile) {
		this.transitionProfile = transitionProfile;
	}

	public Attractor() {
		this(10);
	}
	
	public Attractor(int initialCapacity){
		super(initialCapacity);
		this.netsize = initialCapacity;
		attractorProfile = "";
		transitionProfile = new byte[netsize];
	}
	
	public String getAttractorProfile() {
		return attractorProfile;
	}
	
	public void setAttractorProfile(String attractorProfile) {
		this.attractorProfile = attractorProfile;
	}
	
	public void generateAttractorProfile(){
		attractorProfile = "";
		if (! this.isEmpty()){
			int elements = this.get(0).length;
			int length = this.size();
			for (int element = 0; element < elements; element++){
				double count = 0;
				for (int state = 0; state < length; state++){
					if (this.get(state)[element])
						count += 1;
				}
				count /= length;
				if (count == 0)
					attractorProfile += "0";
				else if (count == 1)
					attractorProfile += "1";
				else{
					
					if (count < 0.33)
						attractorProfile += "L";
					else {
						if (count < 0.66)
							attractorProfile += "M";
						else 
							attractorProfile += "H";
					}
					
//					if (count < 0.5)
//						attractorProfile += "0";
//					else
//						attractorProfile += "1";
					
//					if (count < 0.5)
//						attractorProfile += "L";
//					else
//						attractorProfile += "H";
					
//					attractorProfile += "a";
					
					
				}
			}
		}
//		System.out.println("generated attractor profile: "+attractorProfile);
	}
	
	public void generateAttractorProfileWithTransitions(){
		
		if (! this.isEmpty()){
			int elements = this.get(0).length;
			int length = this.size();
			transitionProfile = new byte[elements];
			for (int element = 0; element < elements; element++){
				double count = 0;
				int transitionCount = 0;
				boolean current = this.get(0)[element];
				for (int state = 0; state < length; state++){
					if (this.get(state)[element] != current){
						transitionCount += 1;
						current = this.get(state)[element];
					}
					if (this.get(state)[element]){
						count += 1;
						
					}
					
				}
				count /= length;
				if (count == 0)
					transitionProfile[element] = 0;
				else if (count == 1)
					transitionProfile[element] = -1;
				else{
					transitionProfile[element] = (byte)transitionCount;
					
					
				}
			}
		}
	}
	public String transitionProfileToString(){
		String str = "(";
		for (byte b:transitionProfile){
			str += b+",";
		}
		str += ")\n";
		return str;
	}
	public Object clone(){
		Attractor clone = new Attractor();
		clone.attractorProfile = ""+attractorProfile;
		for (boolean[] b:this){
			clone.add(b.clone());
		}
		
		return clone;
	}
}
