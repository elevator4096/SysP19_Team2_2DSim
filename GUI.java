/* Grafische Oberflaeche zur Darstellung von Zustaenden der einzelnen Objekte
 * 
 * 
 */

//import von AWT Grafikkomponenten
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
//import von Swing Grafikkomponenten
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

//import von Datei IO Komponenten
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class GUI {
    
     // Standard-Fenster erzeugen
    private JFrame frame = new JFrame(GUI.class.getSimpleName());
    // JLayeredPane dient dazu, in unterschiedlichen Ebenen verschiedene Komponenten darzustellen
    private JLayeredPane contentPane; 
    

    // Fenstereinstellungen in Konstruktor anpassen( Groesse, Transparenz, etc.)
    protected GUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JLayeredPane();        
        contentPane.setBackground(Color.WHITE);
        contentPane.setOpaque(true);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setContentPane(contentPane);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }
    
    //Hintergrundbild laden und darstellen
    public JLabelRot createBackground()
    {
        return createImage("images/background.png",Constants.fieldSizeX+15,Constants.fieldSizeY+35,0,Constants.fieldSizeY);
    }    
    //Bild von rotem Roboter laden und darstellen
    public JLabelRot drawRobotB(RobotB robot)
    {
        return createImage("images/robotRed.png",0,0,(int)Math.round(robot.pose.x),(int)Math.round(robot.pose.y));
    }
    //Bild von blauen Roboter laden und darstellen
    public JLabelRot drawRobotS(RobotS robot)
    {
        return createImage("images/robotBlue.png",0,0,(int)Math.round(robot.pose.x),(int)Math.round(robot.pose.y));
    }
    // Funktion um Bilder zu laden und darzustellen
    public JLabelRot createImage(String imagePath,int width, int height, int xPos, int yPos)
    {
        try {
            ImageIcon image = new ImageIcon( ImageIO.read(new File(imagePath)) );
            frame.setSize(width,height);
            
            JLabelRot imageLabel = new JLabelRot(image); 
            
            imageLabel.setSize(imageLabel.getPreferredSize());
            imageLabel.setLocation(xPos,Constants.fieldSizeY-yPos);  
            contentPane.add(imageLabel);
         
            contentPane.repaint();
            
            return imageLabel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }    
    }
    
    //aendert die Pose(Position und Richtung) eines Bildes
    public boolean repose(JLabelRot image,Pose pose)
    {
        image.setLocation((int)Math.round(pose.x),Constants.fieldSizeY-(int)Math.round(pose.y));
        image.setRot(pose.phi);
        //image.revalidate();
        image.repaint();
        return true;
    }
}
