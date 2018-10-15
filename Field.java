import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.Point;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;




public class Field
{
    private Pose closestPoint = new Pose(0,0,0);
    
    private BufferedImage background;
    private List<Opponent> opponents = new ArrayList<Opponent>();
    private GUI gui;
    
    //Konstruktor: Hintergrund abspeichern und Gegner erzeugen
    public Field(GUI gui,List<Pose> opponentPositions)
    {
        this.gui = gui;
        
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
        
        
    }
    
    //Liefert den Helligkeitswert des Gruenanteils eines Pixels
    //from https://stackoverflow.com/questions/22391353/get-color-of-each-pixel-of-an-image-using-bufferedimages
    public int getPixelBrightnessGreen(int x,int y)
    {
      // Getting pixel color by position x and y 
      int  color  =  background.getRGB(x,y); 
      int  red   = (color & 0x00ff0000) >> 16;
      int  green = (color & 0x0000ff00) >> 8;
      int  blue  =  color & 0x000000ff;
      
      return green;
    }
    
    
    
    
    public double getClosestDistanceToOpponents(SharpSensor sharpSensor, int beamWidth)
    {
        Pose sP = sharpSensor.pose;
        
        double closestDist = Double.MAX_VALUE;
        closestPoint = new Pose(0,0,0);
        
        //berechne mittlere Gerade (y = a*x + b) 
        double a = 1/Math.tan(sP.phi); // Steigung a = cotangens(phi)
        double b0 = sP.y-a*sP.x; // mittlerer Y-Achsenabschnitt b0 = Yp-a*Xp
        
        // Schneide jede Gerade im halben Abstand der Strahlbreite mit den Gegnern -> speichere die kleinste Distanz und den Schnittpunkt
        for(double offset = -beamWidth/2; offset < beamWidth/2; offset += 1 ) 
        {
            //Y-Achsenobschnitt verschieben
            double b = b0+offset/Math.sin(sP.phi);
            
            for(Opponent opponent : opponents)
            {
                List<Point> intersections = opponent.getIntersectionsWithLine(a,b);
                if (intersections.size()<2) continue; // Passante und Tangente interessieren nicht (Tangente wuerden wir sowieso nicht sicher erkennen)
                double objectAngle = Math.atan2(intersections.get(0).x-sP.x,intersections.get(0).y-sP.y);
                objectAngle = (objectAngle+2*Math.PI)%(2*Math.PI);
                if( Math.abs( objectAngle-sP.phi) > Math.PI/4) continue; // Falls die Schnittpunkte hinter dem Sensor liegen werden sie ignoriert
                
                //berechne kleinste Distanz zw Sensorposition und Objektschnittpunkten
                double dist0 = Math.hypot(intersections.get(0).x-sP.x, intersections.get(0).y-sP.y);
                double dist1 = Math.hypot(intersections.get(1).x-sP.x, intersections.get(1).y-sP.y);
                
                // kleinste Distanz speichern
                if (Math.min(dist0,dist1) < closestDist)
                {
                    if (dist0 < dist1)
                    {
                        closestDist = dist0; 
                        closestPoint =  new Pose(intersections.get(0).x,intersections.get(0).y,0);
                    }else
                    {
                        closestDist = dist1; 
                        closestPoint =  new Pose(intersections.get(1).x,intersections.get(1).y,0);                  
                    }
                }
                
            }            
        }
        sharpSensor.setClosestPoint(closestPoint);
        return closestDist;
    } 
    
    
    // from http://www.java2s.com/Code/Java/2D-Graphics-GUI/BlurringaBufferedImage.htm
    private BufferedImage Blur(BufferedImage image)
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
