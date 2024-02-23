package clients.cashier;

import catalogue.Basket;
import catalogue.BetterBasket;
import catalogue.Product;
import debug.DEBUG;
import middle.*;

// import LocanDateTime and the formatter for it
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
// Import the FileWriter class
import java.io.FileWriter;
import java.io.IOException;

import java.util.Observable;

import org.junit.platform.commons.function.Try;

/**
 * Implements the Model of the cashier client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */
public class CashierModel extends Observable
{
  private enum State { process, checked }

  private State       theState   = State.process;   // Current state
  private Product     theProduct = null;            // Current product
  private BetterBasket      theBasket  = null;            // Bought items

  private String      pn = "";                      // Product being processed

  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;

  

  /**
   * Construct the model of the Cashier
   * @param mf The factory to create the connection objects
   */

  public CashierModel(MiddleFactory mf)
  {
  try                                          
    {      
      theStock = mf.makeStockReadWriter();        // Database access
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      DEBUG.error("CashierModel.constructor\n%s", e.getMessage() );
    }
    theState   = State.process;                  // Current state
  }
  
  /**
   * Get the Basket of products
   * @return basket
   */
  // public Basket getBasket()
  public BetterBasket getBasket()
  {
    return theBasket;
  }

  /**
   * Check if the product is in Stock
   * @param productNum The product number
   * @param quantity The quantity intended to be bought
   */
  public void doCheck(String productNum, String quantity)
  {
    try {
      // int quan = Integer.parseInt(quantity);
      String theAction = "";
    theState  = State.process;                  // State process
    pn  = productNum.trim();                    // Product no.
    int    amount  = 1;                         //  & quantity
    int quan = Integer.parseInt(quantity);      // checks if this specific quantity is in stock.
    try
    {
      if ( theStock.exists( pn ) )              // Stock Exists?
      {                                         // T
        Product pr = theStock.getDetails(pn);   //  Get details
        if ( pr.getQuantity() >= quan )       //  In stock?
        {                                       //  T
          theAction =                           //   Display 
            String.format( "%s : %7.2f (%2d) ", //
              pr.getDescription(),              //    description
              pr.getPrice(),                    //    price
              pr.getQuantity() );               //    quantity     
          theProduct = pr;                      //   Remember prod.
          theProduct.setQuantity( amount );     //    & quantity
          theState = State.checked;             //   OK await BUY 
        } else {                                //  F
          theAction =                           //   Not in Stock
            pr.getDescription() +" not in stock";
        }
      } else {                                  // F Stock exists
        theAction =                             //  Unknown
          "Unknown product number " + pn;       //  product no.
      }
    } catch( StockException e )
    {
      DEBUG.error( "%s\n%s", 
            "CashierModel.doCheck", e.getMessage() );
      theAction = e.getMessage();
      // setChanged(); notifyObservers(theAction);
    }
    setChanged(); notifyObservers(theAction);
  } catch (NumberFormatException e) {
      System.out.println("Invalid quantity entered. Please use an integer.");
  }
  }

  /**
   * Buy the product
   * @param cant  quantity to buy
   */
  public void doBuy(String cant)
  {
    
    String theAction = "";
    try {
      int quant = Integer.parseInt(cant);
    try
    {
      if ( theState != State.checked )          // Not checked
      {                                         //  with customer
        theAction = "Check if OK with customer first";
      } else {
        for (int i=0; i< quant; i++)
        {
            boolean stockBought =                   // Buy
            theStock.buyStock(                    //  however
              theProduct.getProductNum(),         //  may fail              
              theProduct.getQuantity() );         //
          if ( stockBought )                      // Stock bought
          {                                       // T
            makeBasketIfReq();                    //  new Basket ?
            theBasket.add( theProduct );          //  Add to bought
            theAction = "Purchased " +            //    details
                    theProduct.getDescription();  //
          } else {                                // F
            theAction = "!!! Not in stock";       //  Not no stock
          }
        }
        
      }
    } catch( StockException e )
    {
      DEBUG.error( "%s\n%s", 
            "CashierModel.doBuy", e.getMessage() );
      theAction = e.getMessage();
    }
    theState = State.process;                   // All Done
    setChanged(); notifyObservers(theAction);
    } catch (NumberFormatException e ) {
      System.out.println("Invalid quantity entered. Please use an integer.");
    }
  }

  /**
   * Generate a receipt for the purchase
   */
  public void doReceipt()
  {
    BetterBasket basket = getBasket();                                                      // create a betterbasket object
    if (basket != null) {                                                                   // checks if items added to basket
      LocalDateTime now = LocalDateTime.now ();                                             // current date time. used on receipt
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern ("yyyy_MM_dd HH-mm-ss");  // formatting for terminal window
      DateTimeFormatter dtfReceipts = DateTimeFormatter.ofPattern ("yyyy_MM_dd--HH-mm-ss"); //formatting for name of files
      String datern = dtf.format(now);                                                      // datetime for terminal
      String dateReceipts = dtfReceipts.format(now);                                        // datetime for filenames
    try {
      File directory = new File("receipts");                                        //ensures code can be ran on another machine and 
      if (! directory.exists())                                                              // not give an error related to receipt files pathing
      {
        directory.mkdir();
      }
      String receiptName = "receipts/" + dateReceipts + ".txt";
      FileWriter myWriter = new FileWriter(receiptName);                                      // creates writer object
      // FileWriter myWriter2 = new FileWriter("receipts/filename2.txt");
      myWriter.write("Thank you for shopping with Cat Shop! \n \n" + basket.getDetails() + "\nYour purchase was made at " + datern + ". No refunds.");
      myWriter.close();                                                                       // closes writer object
      
      System.out.println("\nThank you for your purchase made at: "+ datern + 
      "\nBelow are the details of your purchase and your receipt named " + dateReceipts + " is in the receipt folder\n" +
      basket.getDetails());                                                                     // console output to notify of receipt
    } catch (IOException e) {
      System.out.println("An error occurred while writing receipt.");
      e.printStackTrace();
    }
    } else {
      System.out.println("Cannot print receipt - Basket is empty.");                          // notifies user of basket status
    }
  }
  
  /**
   * Customer pays for the contents of the basket
   */
  public void doBought()
  {
    String theAction = "";
    int    amount  = 1;                       //  & quantity
    try
    {
      if ( theBasket != null &&
           theBasket.size() >= 1 )            // items > 1
      {                                       // T
        theOrder.newOrder( theBasket );       //  Process order
        theBasket = null;                     //  reset
      }                                       //
      theAction = "Next customer";            // New Customer
      theState = State.process;               // All Done
      theBasket = null;
    } catch( OrderException e )
    {
      DEBUG.error( "%s\n%s", 
            "CashierModel.doCancel", e.getMessage() );
      theAction = e.getMessage();
    }
    theBasket = null;
    setChanged(); notifyObservers(theAction); // Notify
  }

  /**
   * ask for update of view callled at start of day
   * or after system reset
   */
  public void askForUpdate()
  {
    setChanged(); notifyObservers("Welcome");
  }
  
  /**
   * make a Basket when required
   */
  private void makeBasketIfReq()
  {
    if ( theBasket == null )
    {
      try
      {
        int uon   = theOrder.uniqueNumber();     // Unique order num.
        theBasket = makeBasket();                //  basket list
        theBasket.setOrderNum( uon );            // Add an order number
      } catch ( OrderException e )
      {
        DEBUG.error( "Comms failure\n" +
                     "CashierModel.makeBasket()\n%s", e.getMessage() );
      }
    }
  }

  /**
   * return an instance of a new Basket
   * @return an instance of a new Basket
   */
  protected BetterBasket makeBasket()
  {
    return new BetterBasket();
  }
}
  
