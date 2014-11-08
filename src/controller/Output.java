package controller;

import java.math.BigDecimal;
import java.util.List;

public class Output {
   private List<String> outputRows;
   private BigDecimal totalSalesTax;
   private BigDecimal total;

   public Output(List<String> outputRows, BigDecimal totalSalesTax, BigDecimal total) {
		this.outputRows = outputRows;
		this.totalSalesTax = totalSalesTax;
		this.total = total;
   }

	public List<String> getOutputRows() {
		return outputRows;
	}
	
	
	public BigDecimal getSalesTax() {
		return totalSalesTax;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
}
