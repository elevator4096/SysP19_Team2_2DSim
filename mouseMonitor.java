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

public class mouseMonitor {

    protected void initUI() throws MalformedURLException {

        ImageIcon image = new ImageIcon(new URL("http://www.arexx.nl/data/images/asuro/extension3.jpg"));
        MouseDragger dragger = new MouseDragger();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            JLabel draggableImage = new JLabel(image);
            draggableImage.setSize(draggableImage.getPreferredSize());
            draggableImage.setLocation(random.nextInt(contentPane.getWidth() - draggableImage.getWidth()),
                    random.nextInt(contentPane.getHeight() - draggableImage.getHeight()));

            dragger.makeDraggable(draggableImage);
            contentPane.add(draggableImage);
        }
        contentPane.repaint();
    }

    public static class MouseDragger extends MouseAdapter {
        private Point lastLocation;
        private Component draggedComponent;

        @Override
        public void mousePressed(MouseEvent e) {
            draggedComponent = e.getComponent();
            lastLocation = SwingUtilities.convertPoint(draggedComponent, e.getPoint(), draggedComponent.getParent());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point location = SwingUtilities.convertPoint(draggedComponent, e.getPoint(), draggedComponent.getParent());
            if (draggedComponent.getParent().getBounds().contains(location)) {
                Point newLocation = draggedComponent.getLocation();
                newLocation.translate(location.x - lastLocation.x, location.y - lastLocation.y);
                newLocation.x = Math.max(newLocation.x, 0);
                newLocation.x = Math.min(newLocation.x, draggedComponent.getParent().getWidth() - draggedComponent.getWidth());
                newLocation.y = Math.max(newLocation.y, 0);
                newLocation.y = Math.min(newLocation.y, draggedComponent.getParent().getHeight() - draggedComponent.getHeight());
                draggedComponent.setLocation(newLocation);
                lastLocation = location;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            lastLocation = null;
            draggedComponent = null;
        }

        public void makeDraggable(Component component) {
            component.addMouseListener(this);
            component.addMouseMotionListener(this);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new mouseMonitor().initUI();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}