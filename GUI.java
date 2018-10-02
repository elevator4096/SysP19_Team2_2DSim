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
    //Fenster sichtbar/unsichtbar
    private boolean showGui;
    
    //Mausmonitor(registriert Mausklicks)
    private MouseMonitor mouseMonitor;

    // Fenstereinstellungen in Konstruktor anpassen( Groesse, Transparenz, etc.)
    protected GUI(boolean showGui) {
        this.showGui = showGui;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JLayeredPane();        
        contentPane.setBackground(Color.WHITE);
        contentPane.setOpaque(true);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setContentPane(contentPane);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(showGui);

    }
    
    //Hintergrundbild laden und darstellen - sowie Mausmonitor aktivieren
    public JLabelRot createBackground()
    {
        JLabelRot background = createImage("images/background.png",Constants.fieldSizeX+15,Constants.fieldSizeY+35,0,Constants.fieldSizeY,false);
        mouseMonitor = new MouseMonitor(background);
        return background;
    }  
    //Bild von Gegner laden und darstellen
    public JLabelRot drawOpponent(Opponent opponent)
    {
        return createImage("images/opponent.png",0,0,(int)Math.round(opponent.pose.x),(int)Math.round(opponent.pose.y),true);
    }
    //Bild von rotem Roboter laden und darstellen
    public JLabelRot drawRobotB(RobotB robot)
    {
        return createImage("images/robotRed.png",0,0,(int)Math.round(robot.pose.x),(int)Math.round(robot.pose.y),true);
    }
    //Bild von blauen Roboter laden und darstellen
    public JLabelRot drawRobotS(RobotS robot)
    {
        return createImage("images/robotBlue.png",0,0,(int)Math.round(robot.pose.x),(int)Math.round(robot.pose.y),true);
    }
    
    // Funktion um Bilder zu laden und darzustellen (pfad,minBreite,minHoehe, xPosition, yPosition, Bild zentriert plazieren)
    private JLabelRot createImage(String imagePath,int width, int height, int xPos, int yPos, boolean centered)
    {
        try {
            //ImageIcon image = new ImageIcon( ImageIO.read(new File(imagePath)) );
            
            java.net.URL imageURL = GUI.class.getResource(imagePath);
            ImageIcon image = new ImageIcon(imageURL);
            
            frame.setSize(width,height);
            
            JLabelRot imageLabel = new JLabelRot(image); 
            
            imageLabel.setSize(imageLabel.getPreferredSize());
            //Wenn zentriert Koordinatenursprung fuer positionierung in Bildmitte verschieben
            if (centered)
            {
                imageLabel.setLocation(xPos-imageLabel.getPreferredSize().width/2,(Constants.fieldSizeY-yPos)-imageLabel.getPreferredSize().height/2);  
            }else
            {
                imageLabel.setLocation(xPos,(Constants.fieldSizeY-yPos)); 
            }    
            contentPane.add(imageLabel);
         
            contentPane.repaint();
            
            return imageLabel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }    
    }
    
    //aendert die Pose(Position und Richtung) eines Bildes - automatisch zentriert
    public boolean repose(JLabelRot image,Pose pose)
    {
        image.setLocation((int)Math.round(pose.x)-image.getPreferredSize().width/2,Constants.fieldSizeY-(int)Math.round(pose.y)-image.getPreferredSize().height/2);
        image.setRot(pose.phi);
        //image.revalidate();
        image.repaint();
        return true;
    }
}
