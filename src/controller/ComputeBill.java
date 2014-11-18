package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import views.Row;
import views.RowList;

/* entry point of the application*/
public class ComputeBill {
	private Set<String> exemptedItems = new HashSet<String>();
	public static void main(String[] args) {
		JFrame frame = new JFrame("Enter Items");
		RowList panel = new RowList();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

/*********************************************************
 * @param items : input by the user
 * @return Ouput class object contains all the output information
 ********************************************************/
	public Output computeBill(List<Row> items){
		loadData();
		List<String> itemOutput = new ArrayList<String>();
		BigDecimal totalBill = new BigDecimal(0);
		BigDecimal totalSalesTax = new BigDecimal(0);
		for(Row item : items){
			BigDecimal quantity = new BigDecimal(item.getQuantity());
			BigDecimal price = item.getPrice();
			String itemdesc = item.getItem();
			BigDecimal salesTax = new BigDecimal(0);
			boolean isExemptedItem = false;
			String itemDescInLowerCase = itemdesc.toLowerCase();
			if(itemDescInLowerCase.substring(itemDescInLowerCase.length()-2).equals("at")){
				itemdesc = itemdesc.substring(0, itemdesc.length()-2).trim();
			}
			
			if(itemDescInLowerCase.contains("imported")){
				BigDecimal importduty = new BigDecimal("0.05");
				BigDecimal totalPrice = price.multiply(quantity);
				salesTax = totalPrice.multiply(importduty);
			}
			
			for(String exemptedItem : exemptedItems){
			    exemptedItem = exemptedItem.toLowerCase();
			    if(itemDescInLowerCase.contains(exemptedItem)||
			    		itemDescInLowerCase.contains(exemptedItem + "s") ||
			    		itemDescInLowerCase.contains(exemptedItem + "es")){
			    	isExemptedItem = true;
			    	break;
			    }
			}
			/*check if the item is exempted i.e food, medical drug,books*/
			if(!isExemptedItem){
				BigDecimal tax = new BigDecimal("0.10");
				BigDecimal totalPrice = price.multiply(quantity);
				salesTax = salesTax.add(totalPrice.multiply(tax));
			}
			
			//round off salesTax component up by 5%
			BigDecimal divided = salesTax.divide(new BigDecimal("0.05"), 0, RoundingMode.UP);
	        salesTax = divided.multiply(new BigDecimal("0.05"));
	        
			itemOutput.add(quantity+ " " + itemdesc +":  " + (quantity.multiply(price).add(salesTax)));
			totalBill = totalBill.add(quantity.multiply(price).add(salesTax));
			totalSalesTax = totalSalesTax.add(salesTax);
		}
		return new Output(itemOutput, totalSalesTax, totalBill);
	}
    
	/************************************************
	 * Read expemptedItems.txt and popluates set with
	 * the exempted item
	 ************************************************/
	public void loadData(){
		try (
				InputStream is = getClass().getResourceAsStream("exemptedItems.txt");
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
			)
		   {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				for(String item: sCurrentLine.split(",")){
					exemptedItems.add(item.trim());
				}
			}
		} catch (IOException e) {
			System.out.println("here");
			e.printStackTrace();
		}
	}
	
	/*******************************************************
	 * verfiy user input to check if qty is an integer,
	 * price is a Big Decimal and item is a non-empty string 
	 *******************************************************/
	public void verifyInput(List<Row> items){
		for(Row item : items){
			try{
			    item.getQuantity();
				item.getPrice();
				String itemdesc = item.getItem();
				if(itemdesc.equals(null) || itemdesc=="" || itemdesc.length()<=3){
					throw new Exception("item desciption should not be empty");
				}
			}
			catch(NumberFormatException nfe){
				JOptionPane.showMessageDialog(null, "Error in input\n "+nfe.getMessage() +"\n Exiting system", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Error in input "+e.getMessage() + "\n Exiting system", "Error", JOptionPane.ERROR_MESSAGE);	
				System.exit(-1);
			}
		}
	}
}