package clients.collection;

import middle.MiddleFactory;
import middle.OrderProcessing;
import clients.CatPawButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class CollectView implements Observer
{
 private static final String COLLECT = "Collect";
  
  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final CatPawButton     theBtCollect= new CatPawButton( COLLECT );
 
  private OrderProcessing   theOrder = null;
  private CollectController cont     = null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  public CollectView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                           // 
    {      
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

    theBtCollect.setBounds( 16, 25+60*0, 80, 40 );  // Check Button
    theBtCollect.addActionListener(                 // Call back code
      e -> cont.doCollect( theInput.getText()) );
    cp.add( theBtCollect );                         //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 270, 40 );         // Input Area
    theInput.setText("Insert order number here");                           // Blank
    cp.add( theInput );                             //  Add to canvas

    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }  
  
  public void setController( CollectController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override 
  public void update( Observable modelC, Object arg )
  {
    CollectModel model  = (CollectModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    theOutput.setText( model.getResponce() );
    theInput.requestFocus();               // Focus is here
  }

}
