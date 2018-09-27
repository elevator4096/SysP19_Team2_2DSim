import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GUI {

    private JLayeredPane contentPane; 
    private JFrame frame = new JFrame(GUI.class.getSimpleName());
    public int time = 0;
    

    

    protected GUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JLayeredPane();        
        contentPane.setBackground(Color.WHITE);
        contentPane.setOpaque(true);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setContentPane(contentPane);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
       
        //createImage("robotRed.png",0,0,50,760);
        //createImage("robotBlue.png",0,0,550,760);

    }
    
    public void createBackground()
    {
        createImage("images/background.png",760+15,1000+35,0,0);
    }    
    
    public void drawRobotB(RobotB robot)
    {
        createImage("images/robotRed.png",0,0,50,800);
    }
    
    public void drawRobotS(RobotS robot)
    {
        createImage("images/robotBlue.png",0,0,550,800);
    }
    
    public Component createImage(String imagePath,int width, int height, int xPos, int yPos)
    {
        try {
            ImageIcon image = new ImageIcon( ImageIO.read(new File(imagePath)) );
            frame.setSize(width,height);
            
            JLabel imageLabel = new JLabel(image);
            
            imageLabel.setSize(imageLabel.getPreferredSize());
            imageLabel.setLocation(xPos,yPos);  
            contentPane.add(imageLabel);
         
            contentPane.repaint();
            
            return imageLabel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }    
    }

}
