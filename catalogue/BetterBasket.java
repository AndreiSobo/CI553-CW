package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Write a description of class BetterBasket here.
 * 
 * @author  POSBOSS's boss. Boss of the poss
 * @version 1.0
 */
public class BetterBasket extends Basket
{
  private static final long serialVersionUID = 1L;
  private int    theOrderNum = 0;          // Order number

  public BetterBasket()
  {
    theOrderNum = 0;
  }

  // You need to add code here

  public String getDetails() {
    Locale uk = Locale.UK;
    StringBuilder sb = new StringBuilder(256);
    String csign = (Currency.getInstance(uk)).getSymbol();
    double total = 0.00;
    if (theOrderNum != 0)
        sb.append("Order number: ").append(theOrderNum).append("\n");

    if (this.size() > 0) {
        Map<String, List<Product>> groupedProducts = this.stream()
            .collect(Collectors.groupingBy(Product::getProductNum));

        for (Map.Entry<String, List<Product>> entry : groupedProducts.entrySet()) {
            int number = entry.getValue().stream().mapToInt(Product::getQuantity).sum();
            Product pr = entry.getValue().get(0);
            sb.append(pr.getProductNum()).append(" ");
            sb.append(pr.getDescription()).append(" ");
            sb.append("(").append(number).append(") ");
            sb.append(csign).append(pr.getPrice() * number).append("\n");
            total += pr.getPrice() * number;
        }
        sb.append("----------------------------\n");
        sb.append("Total ").append(csign).append(total).append("\n");
    }
    return sb.toString();
}
}
