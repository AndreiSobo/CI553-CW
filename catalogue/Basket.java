package catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

/**
 * A collection of products from the CatShop.
 *  used to record the products that are to be/
 *   wished to be purchased.
 * @author  Mike Smith University of Brighton
 * @version 2.2
 *
 */
public class Basket extends ArrayList<Product> implements Serializable
{
  private static final long serialVersionUID = 1;
  private int    theOrderNum = 0;          // Order number
  
  /**
   * Constructor for a basket which is
   *  used to represent a customer order/ wish list
   */
  public Basket()
  {
    theOrderNum  = 0;
  }
  
  /**
   * Set the customers unique order number
   * Valid order Numbers 1 .. N
   * @param anOrderNum A unique order number
   */
  public void setOrderNum( int anOrderNum )
  {
    theOrderNum = anOrderNum;
  }

  /**
   * Returns the customers unique order number
   * @return the customers order number
   */
  public int getOrderNum()
  {
    return theOrderNum;
  }
  
  /**
   * Add a product to the Basket.
   * Product is appended to the end of the existing products
   * in the basket.
   * @param pr A product to be added to the basket
   * @return true if successfully adds the product
   */
  // Will be in the Java doc for Basket
  @Override
  public boolean add( Product pr )
  {                              
    return super.add( pr );     // Call add in ArrayList
  }

  /**
   * Returns a description of the products in the basket suitable for printing.
   * @return a string description of the basket products
   */
  /* Make changes to this function. With this version, I can successfully build testing methods for the Basket class */
  public String getDetails() {
    Locale uk = Locale.UK;
    StringBuilder sb = new StringBuilder(256);
    String csign = (Currency.getInstance(uk)).getSymbol();
    double total = 0.00;
    if (theOrderNum != 0)
        sb.append("Order number: ").append(theOrderNum).append("\n");

    if (this.size() > 0) {
        for (Product pr : this) {
            int number = pr.getQuantity();
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
