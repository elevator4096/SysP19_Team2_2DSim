/* MouseMonitor registriert Mausklicks und speichert die Position des letzten Mausklicks
 * dies ermoeglicht eine einfache Interaktion mit der Simulationsumgebung
*/
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
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

public class MouseMonitor {
    
    private JLabelRot background;
    private Point location = new Point(0,0);

    public MouseMonitor(JLabelRot background) {
        MouseTracker tracker = new MouseTracker();
        
        this.background = background;
        tracker.makeTrackable(this.background);
    }

    public class MouseTracker extends MouseAdapter {
        private Component clickedComponent;

        @Override
        public void mousePressed(MouseEvent e) {
            clickedComponent = e.getComponent();
            location = SwingUtilities.convertPoint(clickedComponent, e.getPoint(), clickedComponent.getParent());
            location = new Point((int)location.getX(),Constants.fieldSizeY-(int)location.getY());
            System.out.println(location);
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            clickedComponent = null;
        }

        public void makeTrackable(Component component) {
            component.addMouseListener(this);
            component.addMouseMotionListener(this);
        }

    }
}
