package views;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.ComputeBill;
import controller.Output;

/*******************************************
 * class resposible for Generate Bill button 
 * activity
 *******************************************/
@SuppressWarnings("serial")
class GenerateBillButton extends JPanel{
	private JButton generateBill;
	private List<Row> rows= null;
	
	public GenerateBillButton(List<Row> rowList){
		this.rows = rowList;
		this.generateBill = new JButton(new GenerateBillAction());
		add(this.generateBill);
	}
	
	public void updateRows(List<Row> rows){
		this.rows = rows;
	}
	
	public class GenerateBillAction extends AbstractAction {
        
		public GenerateBillAction() {
            super("Generate Bill");
        }
		
		
        public void actionPerformed(ActionEvent e) {
        	ComputeBill produceBill = new ComputeBill();
        	produceBill.verifyInput(rows);
        	String output = printOutput(produceBill.computeBill(rows));
        	JOptionPane.showMessageDialog(null, output,"final bill", JOptionPane.INFORMATION_MESSAGE);
        }
        
        public String printOutput(Output output){
        	String printOutput = "";
        	for (String item : output.getOutputRows()){
        		printOutput += item + "\n";
        	}
        	printOutput +="Sales Taxes:  " + output.getSalesTax() +"\n";
        	printOutput +="Total:  " + output.getTotal() + "\n";
        	return printOutput;
        }
    }
}
