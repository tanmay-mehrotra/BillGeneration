package views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RowList extends JPanel{
	List<Row> rows;
    GenerateBillButton generateBillButton = null;
	
    public RowList() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Headers hd = new Headers();
		add(hd);
		this.rows = new ArrayList<Row>();
		Row initial = new Row("1","0.00","", this);
		addItem(initial);
		generateBillButton = new GenerateBillButton(rows);
		add(generateBillButton);
	}


	public void addGenerateBillButton(){
		JButton billGenerationButton = new JButton("Generate Bill");
		add(billGenerationButton);
	}

	public void cloneRow() {
		Row theClone = new Row("1","0.00","", this);
		addItem(theClone);
	}
	
	private void addItem(Row row) {
		rows.add(row);
		add(row);
		refresh();
	}
	
	public void removeItem(Row entry) {
		rows.remove(entry);
		remove(entry);
		refresh();
	}

	private void refresh() {
		revalidate();
		repaint();
		if (rows.size() == 1) {
			rows.get(0).enableMinus(false);
		}
		else {
			for (Row e : rows) {
				e.enableMinus(true);
			}
		}
	}
}
