import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class Opponent
{
    
    public Pose pose;

    public Opponent(Pose pose)
    {
        this.pose = new Pose(pose);
    }
    
    //finde Schnittpunkte mit Gerade y = ax + b
    public List<Point> getIntersectionsWithLine(double a,double b)
    {
        List<Point> intersections = new ArrayList<Point>();
        
        double x0 = pose.x;
        double y0 = pose.y;
        double r  = Constants.opponentRadius;
        
        double A = (a*a + 1);
        double B = (2*a*b -2*a*y0-2*x0);
        double C = (b*b -2*b*y0-r*r+x0*x0+y0*y0);
        
        double discriminant = (B*B - 4*A*C);
        
        // Wenn keine Schnittpunkte leere Liste zurueckgeben
        if (discriminant>=0)
        {
            double x1 = 1/(2*A) * (-B + Math.sqrt(discriminant)); 
            double x2 = 1/(2*A) * (-B - Math.sqrt(discriminant));
            double y1 = a*x1+b;
            double y2 = a*x2+b;
            
            intersections.add(new Point((int)x1,(int)y1));
            intersections.add(new Point((int)x2,(int)y2));
            
            return intersections;
        }
        else return intersections;
        
    }    

    
}
