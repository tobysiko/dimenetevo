/**
 * 
 */
package sim.gui;

import javax.swing.JTextArea;

import sim.datatype.Network;

/**
 * @author sikosek
 *
 */
public class Outputter {
	protected NetworkPanel fr;
	protected JTextArea ta;
	public Outputter(){
		fr = new NetworkPanel();
		ta = fr.getJTextArea1();
	}
	public void display(Network net){
		fr.setVisible(true);
		fr.setSize(1200, 300);
		
		this.update(net);
	}
	public void update(Network net){
		if (fr != null && fr.isVisible()){
			ta.setText("");
			ta.setText(net.getInteractions().toString());
			
		}
		
	}
	public void terminate(){
//		fr.dispose();
	}
	public static void main(String[] args){
//		Network net;
//		Parameters p = new Parameters();
//		NetworkGenerator gen = new RandomNetworkGenerator(p);
//		net = gen.generateNewNetwork();
//		Outputter out = new Outputter();
//		out.display(net);
//		for (int i = 0; i < 5; i++){
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			net.removeElement(2);
//			out.update(net);
//		}
		
		
	}
}
