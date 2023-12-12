package clients.cashier;

import catalogue.Basket;
import catalogue.BetterBasket;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;
import clients.CatPawButton;
import clients.customer.CustomerView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


/**
 * View of the model
 * @author  M A Smith (c) June 2014  
 */
public class CashierView implements Observer
{
  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels
  
  private static final String CHECK  = "Check";
  private static final String BUY    = "Buy";
  private static final String BOUGHT = "Bought";
  private static final String RECEIPT = "Receipt";

  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();

  // adding another input field where the quantity of the items will be
  private final JTextField  quantityNo   = new JTextField();

  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final CatPawButton     theBtCheck = new CatPawButton( CHECK );
  private final CatPawButton     theBtBuy   = new CatPawButton( BUY );
  private final CatPawButton     theBtBought= new CatPawButton( BOUGHT );
  private final CatPawButton    theBtReceipt = new CatPawButton(RECEIPT);

  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;
  private CashierController cont       = null;
  
  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-coordinate of position of window on screen 
   * @param y     y-coordinate of position of window on screen  
   */

   // add function that will return the value of the quantityNo inout field
  public int getQuantityNo() 
  {
    String quantityNoString = quantityNo.getText();
    int quantityNoInt = Integer.parseInt(quantityNoString);
    return quantityNoInt;
  }
          
  public CashierView(  RootPaneContainer rpc,  MiddleFactory mf, int x, int y  )
  {
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // Database access
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    
    Image bgImage = null;
  try {
    bgImage = ImageIO.read(getClass().getResource("/resources/cats_pic3.jpg"));
  } catch (IOException e) {
    e.printStackTrace();
  }

  // Create a new content pane with a custom paintComponent method
  final Image finalBgImage = bgImage;
  JPanel contentPane = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(finalBgImage, 0, 0, null);
    }
  };

  contentPane.setLayout(null);
  rpc.setContentPane(contentPane); // Set the new content pane

  Container cp         = rpc.getContentPane();    // Content Pane
  Container rootWindow = (Container) rpc;         // Root Window
  rootWindow.setSize( W, H );                     // Size of Window
  rootWindow.setLocation( x, y );

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );    // Check Button
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText(), quantityNo.getText()) );
    theBtCheck.addActionListener(
        e -> CustomerView.playSound("resources/mixkit-angry-cartoon-kitty-meow-94.wav"));
    cp.add( theBtCheck );                           //  Add to canvas

    // adding the receipt button
    theBtReceipt.setBounds(16, 145+60*0, 80, 40);
    theBtReceipt.addActionListener(
      e -> cont.doReceipt() );
    theBtReceipt.addActionListener(
        e -> CustomerView.playSound("resources/mixkit-angry-cartoon-kitty-meow-94.wav"));
    cp.add( theBtReceipt);

    theBtBuy.setBounds( 16, 25+60*1, 80, 40 );      // Buy button 
    theBtBuy.addActionListener(                     // Call back code
      e -> cont.doBuy(quantityNo.getText()) );
    theBtBuy.addActionListener(
        e -> CustomerView.playSound("resources/mixkit-angry-cartoon-kitty-meow-94.wav"));
    cp.add( theBtBuy );                             //  Add to canvas

    theBtBought.setBounds( 16, 25+60*3, 80, 40 );   // Clear Button
    theBtBought.addActionListener(                  // Call back code
      e -> cont.doBought() );
    theBtBought.addActionListener(
        e -> CustomerView.playSound("resources/mixkit-angry-cartoon-kitty-meow-94.wav"));
    cp.add( theBtBought );                          //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    // theInput.setBounds( 110, 50, 270, 40 );         // Input Area
    theInput.setBounds( 110, 50, 120, 40 );  
    theInput.setText("Item code here");                           // Blank
    cp.add( theInput );                             //  Add to canvas

    quantityNo.setBounds(260, 50, 120, 40);
    quantityNo.setText("Quantity here");
    cp.add(quantityNo);

    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }

  /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CashierController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */

   /* MAKE CHANGE HERE TO REFLECT INTRODUCING BETTER BASKET */
  @Override
  public void update( Observable modelC, Object arg )
  {
    CashierModel model  = (CashierModel) modelC;
    String      message = (String) arg;
    theAction.setText( message );

    BetterBasket basket = model.getBasket();
    if ( basket == null )
      theOutput.setText( "Customer orders will be added here." );
    else
      theOutput.setText( basket.getDetails() );
    
    theInput.requestFocus();               // Focus is here
  }

}
