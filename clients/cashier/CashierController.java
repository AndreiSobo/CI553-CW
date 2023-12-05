package clients.cashier;


/**
 * The Cashier Controller
 * @author M A Smith (c) June 2014
 */

public class CashierController
{
  private CashierModel model = null;
  private CashierView  view  = null;

  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public CashierController( CashierModel model, CashierView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Check interaction from view
   * @param pn The product number to be checked
   */
  public void doCheck( String pn, String quantity)
  {
    model.doCheck(pn, quantity);
  }

   /**
   * Buy interaction from view
   */
  public void doBuy(String cant)
  {
    model.doBuy(cant);
  }
  // making the doReceipt() method
  public void doReceipt()
  {
    model.doReceipt();
  }
  
   /**
   * Bought interaction from view
   */
  public void doBought()
  {
    model.doBought();
  }

  public int doGetQuantityNo()
  {
    return view.getQuantityNo();
  }
}
