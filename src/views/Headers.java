package views;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class Headers extends JPanel{
	public Headers(){
		JLabel itemlabel1 = new JLabel("                                         Qty");
		JLabel itemlabel2 = new JLabel("                                         Item");
		JLabel itemlabel3 = new JLabel("                                        Price");
		add(itemlabel1);add(itemlabel2);add(itemlabel3);
	}
}
