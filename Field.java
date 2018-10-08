import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Field
{
    private BufferedImage background;
    private List<Opponent> opponents = new ArrayList<Opponent>();
    
    //Konstruktor: Hintergrund abspeichern und Gegner erzeugen
    public Field(GUI gui,List<Pose> opponentPositions)
    {
        
        // fuer jede in opponentPositions gespeicherte Position einen Gegner erzeugen 
        for(Pose pose : opponentPositions)
        {
            opponents.add(new Opponent(pose));
        }     
        
        //jeden Gegner darstellen
        for(Opponent opponent : opponents)
        {
            gui.drawOpponent(opponent);
        }
        
        //Hintergrundbild laden und darstellen
        background = gui.createBackground1();
        
        //Hintergrund Weichzeichnen um Helligkeitssensoren besser simulieren zu koennen
        background = Blur(background);
        
        try{
            File outputfile = new File("test.png");
            ImageIO.write(background, "png", outputfile);
        } catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        //background.
    }
    
    //Liefert den Helligkeitswert des Gruenanteils eines Pixels
    //from https://stackoverflow.com/questions/22391353/get-color-of-each-pixel-of-an-image-using-bufferedimages
    public int getPixelBrightnessGreen(int x,int y)
    {
      // Getting pixel color by position x and y 
      int color  =  background.getRGB(x,y); 
      int  red   = (color & 0x00ff0000) >> 16;
      int  green = (color & 0x0000ff00) >> 8;
      int  blue  =  color & 0x000000ff;
      return green;
    }
    
    // from http://www.java2s.com/Code/Java/2D-Graphics-GUI/BlurringaBufferedImage.htm
    public BufferedImage Blur(BufferedImage image)
    {
        Kernel kernel = new Kernel(5, 5, new float[] { 
            0.003765f, 0.015019f, 0.023792f, 0.015019f, 0.003765f,
            0.015019f, 0.059912f, 0.094907f, 0.059912f, 0.015019f,
            0.023792f, 0.094907f, 0.150342f, 0.094907f, 0.023792f,
            0.015019f, 0.059912f, 0.094907f, 0.059912f, 0.015019f,
            0.003765f, 0.015019f, 0.023792f, 0.015019f, 0.003765f
        });
        BufferedImageOp op = new ConvolveOp(kernel);
        image = op.filter(image, null);
        return image;
    }    
}
