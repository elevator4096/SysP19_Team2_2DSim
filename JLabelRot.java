// angepasst Version von JLabel welche das Rotieren von Bildern unterstuetzt
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class JLabelRot extends JLabel
{
    
    private double rot = 0;
    ImageIcon image;
    //Bild in Konstruktor festlegen
    public JLabelRot(ImageIcon image)
    {
        super(image);
        this.image = image;
        
    }
    //Bild aendern
    public void setImage(ImageIcon image)
    {
        this.image = image;
    }
    //Rotationswinkel von Bild festlegen
    public void setRot(double rot) 
    {
        this.rot = rot;
    }
    
    //Paint Methode von JPanel ueberschreiben um ein rotiertes Bild anzuzeigen
    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(rot, image.getIconWidth() / 2, image.getIconHeight() / 2);
        g2.drawImage(image.getImage(), 0, 0, null);
    }
}
