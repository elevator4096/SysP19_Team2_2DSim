import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class JLabelRot extends JLabel
{
    
    private double rot = 0;
    ImageIcon image;
    
    public JLabelRot(ImageIcon image)
    {
        super(image);
        this.image = image;
        
    }
    public void setImage(ImageIcon image)
    {
        this.image = image;
    }
    public void setRot(double rot) 
    {
        this.rot = rot;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(rot, image.getIconWidth() / 2, image.getIconHeight() / 2);
        g2.drawImage(image.getImage(), 0, 0, null);
    }
}
