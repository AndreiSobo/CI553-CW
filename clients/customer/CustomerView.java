package clients.customer;

import catalogue.Basket;
import catalogue.BetterBasket;
import clients.CatPawButton;
import clients.Picture;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
//make import for the sound files
import javax.sound.sampled.*;


/**
 * Implements the Customer view.
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class CustomerView implements Observer
{
  class Name                              // Names of buttons
  {
    public static final String CHECK  = "Check";
    public static final String CLEAR  = "Clear";
  }

  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  // private final JButton     theBtCheck = new JButton( Name.CHECK );
  // private final JButton     theBtClear = new JButton( Name.CLEAR );

  // making changes to the intreface so that the view class uses the CatPawButton rather than the jButton
  private final CatPawButton     theBtCheck = new CatPawButton( Name.CHECK );
  private final CatPawButton     theBtClear = new CatPawButton( Name.CLEAR );

  private Picture thePicture = new Picture(80,80);
  private StockReader theStock   = null;
  private CustomerController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  public void playSound(String soundFileName) {
    try {
        File soundFile = new File(soundFileName);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
    }
}
  public CustomerView( RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             
  {      
    theStock  = mf.makeStockReader();             // Database Access
  } catch ( Exception e )
  {
    System.out.println("Exception: " + e.getMessage() );
  }

  // Load the background image
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

    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );    // Check button
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText() ) );
    theBtCheck.addActionListener(
      // e -> playSound("C:/Users/soboa/OneDrive - University of Brighton/Documents/1.University of Brighton - Comp Science/YEAR 2/CI 553 - Object Oriented development and testing/CI553-CW/sounds/cat-meow6.wav"));
      // e -> playSound("C:\\Users\\soboa\\Downloads\\mixkit-angry-cartoon-kitty-meow-94.wav"));
      e -> playSound("resources/mixkit-angry-cartoon-kitty-meow-94.wav"));
    cp.add( theBtCheck );                           //  Add to canvas

    theBtClear.setBounds( 16, 25+60*1, 80, 40 );    // Clear button
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    
    cp.add( theBtClear );                           //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        //  Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 270, 40 );         // Product no area
    theInput.setText("Insert product code here");                           // Blank
    cp.add( theInput );                             //  Add to canvas
    
    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea

    thePicture.setBounds( 16, 25+60*2, 80, 80 );   // Picture area
    cp.add( thePicture );                           //  Add to canvas
    thePicture.clear();
    
    rootWindow.setVisible( true );                  // Make visible);
    theInput.requestFocus();                        // Focus is here
  }
  

   /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CustomerController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
   
  public void update( Observable modelC, Object arg )
  {
    CustomerModel model  = (CustomerModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    ImageIcon image = model.getPicture();  // Image of product
    if ( image == null )
    {
      thePicture.clear();                  // Clear picture
    } else {
      thePicture.set( image );             // Display picture
    }
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();               // Focus is here
  }

}
