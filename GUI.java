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
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
    
    //Groesse des GUI
    private  int pixelPmm;
    private  int guiSizeX;
    private  int guiSizeY;

    // Fenstereinstellungen in Konstruktor anpassen( Groesse, Transparenz, etc.)
    protected GUI(boolean showGui) {
        
        if (Toolkit.getDefaultToolkit().getScreenSize().getHeight()>2000)
        {
            pixelPmm = 2;
        } else 
        {
            pixelPmm = 1;
        }    
        guiSizeX      = Constants.fieldSizeMMX*pixelPmm; // pixels
        guiSizeY     = Constants.fieldSizeMMY*pixelPmm; // pixels
        
        this.showGui = showGui;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JLayeredPane();        
        contentPane.setBackground(Color.WHITE);
        contentPane.setOpaque(true);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setContentPane(contentPane);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(showGui);
        
        frame.setSize(guiSizeX+15,guiSizeY+35);

    }
    
    //Hintergrundbild laden und darstellen - sowie Mausmonitor aktivieren
    public BufferedImage createBackground1()
    {
        String imagePath = "images/background1.png";
        JLabelRot background = createImage(imagePath,0,Constants.fieldSizeMMY,false);
        mouseMonitor = new MouseMonitor(background,pixelPmm);
        
        java.net.URL imageURL = GUI.class.getResource(imagePath);
        
        BufferedImage bufferedImage = null;
        try 
        {
            bufferedImage = ImageIO.read(imageURL);
        } catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
        
        return bufferedImage;
    }  
    
    //Bild von Gegner laden und darstellen
    public JLabelRot drawOpponent(Opponent opponent)
    {
        return createImage("images/opponent.png",(int)Math.round(opponent.pose.x),(int)Math.round(opponent.pose.y),true);
    }
    //Bild von rotem Roboter laden und darstellen
    public JLabelRot drawRobotB(RobotB robot)
    {
        return createImage("images/robotRed.png",(int)Math.round(robot.pose.x),(int)Math.round(robot.pose.y),true);
    }
    //Bild von blauen Roboter laden und darstellen
    public JLabelRot drawRobotS(RobotS robot)
    {
        return createImage("images/robotBlue.png",(int)Math.round(robot.pose.x),(int)Math.round(robot.pose.y),true);
    }
    //Bild von sharpSensor laden und darstellen
    public JLabelRot drawSharpSensor(SharpSensor sharpsensor)
    {
        return createImage("images/sharpSensor.png",(int)Math.round(sharpsensor.pose.x),(int)Math.round(sharpsensor.pose.y),true);
    }
    
    // Funktion um Bilder zu laden und darzustellen (pfad,minBreite,minHoehe, xPosition, yPosition, Bild zentriert plazieren)
    private JLabelRot createImage(String imagePath,int xPos, int yPos, boolean centered)
    {
        try {
            //ImageIcon image = new ImageIcon( ImageIO.read(new File(imagePath)) );
            
            xPos *= pixelPmm; 
            yPos *= pixelPmm; 
            
            java.net.URL imageURL = GUI.class.getResource(imagePath);
            ImageIcon image = new ImageIcon(imageURL);
            
            image = scaleImage(image,image.getIconWidth()*pixelPmm,image.getIconHeight()*pixelPmm);
            
            
            
            JLabelRot imageLabel = new JLabelRot(image); 
            
            imageLabel.setSize(imageLabel.getPreferredSize());
            
            //Wenn zentriert Koordinatenursprung fuer positionierung in Bildmitte verschieben
            if (centered)
            {
                imageLabel.setLocation(xPos-imageLabel.getPreferredSize().width/2,(guiSizeY-yPos)-imageLabel.getPreferredSize().height/2);  
            }else
            {
                imageLabel.setLocation(xPos,(guiSizeY-yPos)); 
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
        image.setLocation((int)Math.round(pose.x*pixelPmm)-image.getPreferredSize().width/2,guiSizeY-(int)Math.round(pose.y*pixelPmm)-image.getPreferredSize().height/2);
        image.setRot(pose.phi);
        //image.revalidate();
        image.repaint();
        return true;
    }
    
    //Bilder skalieren
    private ImageIcon scaleImage(ImageIcon icon, int w, int h)
    {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();
        
        /*
        if(icon.getIconWidth() > w)
        {
          nw = w;
          nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if(nh > h)
        {
          nh = h;
          nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }
        */
        return new ImageIcon(icon.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
    }
    
    // from https://stackoverflow.com/questions/11105915/java-get-an-image-of-a-jpanel
    public BufferedImage getScreenshot() {    
        int w = contentPane.getWidth();
        int h = contentPane.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        contentPane.paint(g);
        return bi;
    }
    
    public void imageToFile(BufferedImage bi)
    {
        try{
            File outputfile = new File("test.png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}
