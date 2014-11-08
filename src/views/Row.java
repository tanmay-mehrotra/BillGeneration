package views;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
 

// remeber to add null pointer checks : in case user leaves a field blank and generate bill
@SuppressWarnings("serial")
public class Row extends JPanel {

    private JTextField quantity;
    private JTextField item;
    private JTextField price;
    private JButton plus;
    private JButton minus;
    private RowList parent;
    private GenerateBillButton generateBillButton;
    
    public Row(String initialQuantity, String initalPrice, String initialItem, RowList list) {
        this.parent = list;
        this.plus = new JButton(new AddRowAction());
        this.minus = new JButton(new RemoveRowAction());
        this.quantity = new JTextField(10);
        this.item = new JTextField(25);
        this.price = new JTextField(10);
        this.quantity.setText(initialQuantity);
        this.price.setText(initalPrice);
        this.item.setText(initialItem);
        add(this.plus);
        add(this.minus);
        add(this.quantity);
        add(this.item);
        add(this.price);
        generateBillButton = new GenerateBillButton(list.rows);
    }
    
  
	public int getQuantity() throws NumberFormatException{
		  	return Integer.parseInt(quantity.getText().trim());
	}
	public String getItem() {
		return item.getText().trim();
	}
	
	public BigDecimal getPrice() throws NumberFormatException{
		String trimmedPrice = price.getText().trim();
		BigDecimal d = new BigDecimal(trimmedPrice);
		d = d.setScale(2, BigDecimal.ROUND_HALF_UP);
		return d;
	}
	
	
	public GenerateBillButton getGenerateBillButton() {
		return generateBillButton;
	}

	public class AddRowAction extends AbstractAction {
 
        public AddRowAction() {
            super("+");
        }
 
        public void actionPerformed(ActionEvent e) {
        	parent.remove(parent.generateBillButton);
            parent.cloneRow();
            parent.add(parent.generateBillButton);
            generateBillButton.updateRows(parent.rows);
            ((JFrame) SwingUtilities.getRoot(parent)).pack();
        }
    }
 
	public class RemoveRowAction extends AbstractAction {
 
        public RemoveRowAction() {
            super("-");
        }
 
        public void actionPerformed(ActionEvent e) {
            parent.removeItem(Row.this);
            generateBillButton.updateRows(parent.rows);
            ((JFrame) SwingUtilities.getRoot(parent)).pack();
        }
    }
 
    public void enableAdd(boolean enabled) {
        this.plus.setEnabled(enabled);
    }
    public void enableMinus(boolean enabled) {
        this.minus.setEnabled(enabled);
    }
}
