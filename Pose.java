//Klasse fuer Pose von Objekten (x,y,drehwinkel) 
public class Pose
{
            
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    public double x;
    public double y;
    public double phi; // Drehwinkel
    
    public Pose(double x, double y, double phi)
    {
        setPose(x, y, phi);
    }
    
    public boolean calculateTargetPose(Pose startPose, int distance)
    {
        this.x = startPose.x + distance*Math.sin(startPose.phi);
        this.y = startPose.y + distance*Math.cos(startPose.phi);
        
        this.phi = startPose.phi;
        
        return true;
    }    
    
    public boolean closeEnough(Pose pose)
    {
        return ( (Math.abs(x-pose.x) < Constants.maxXError)&&(Math.abs(y-pose.y) < Constants.maxYError)&&(Math.abs(phi-pose.phi) < Constants.maxPhiError) );
    }  
    
    public boolean setPose(double x, double y, double phi)
    {
        this.x = x;
        this.y = y;
        this.phi = phi;
        
        return true;
    }
    
    public boolean setPose(Pose pose)
    {
        this.x = pose.x;
        this.y = pose.y;
        this.phi = pose.phi;
        
        return true;
    }
}
