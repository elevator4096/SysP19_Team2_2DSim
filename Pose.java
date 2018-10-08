//Klasse fuer Pose(Position und Richtung) von Objekten (x,y,drehwinkel) 
public class Pose
{
            
    public double x;    // x koord
    public double y;    // y koord
    public double phi;  // Drehwinkel
    
    // Konstruktor: Anfangspose mit x,y,drehwinkel festlegen
    public Pose(double x, double y, double phi)
    {
        setPose(x, y, phi);
    }
    
    // Konstruktor: Anfangspose direkt zuweisen
    public Pose(Pose pose)
    {
        this.setPose(pose);
    }
    
    // neue Zielposition anhand von bekannter Richtung und Distanz ermitteln
    public boolean calculateTargetPose(Pose startPose, int distance)
    {
        this.x = startPose.x + distance*Math.sin(startPose.phi);
        this.y = startPose.y + distance*Math.cos(startPose.phi);
        
        this.phi = startPose.phi;
        
        return true;
    }    
    
    //ermitteln ob eine Pose mit hinreichender Genauigkeit mit einer anderen uebereinstimmt 
    public boolean closeEnough(Pose pose)
    {
        return ( (Math.abs(x-pose.x) < Constants.maxXError)&&(Math.abs(y-pose.y) < Constants.maxYError)&&(Math.abs(phi-pose.phi) < Constants.maxPhiError) );
    }  
    
    // Pose anhand von x,y und Richtung neu setzten
    public boolean setPose(double x, double y, double phi)
    {
        this.x = x;
        this.y = y;
        this.phi = phi;
        
        return true;
    }
    
    // Pose einer anderen direkt zuweisen
    public boolean setPose(Pose pose)
    {
        this.x = pose.x;
        this.y = pose.y;
        this.phi = pose.phi;
        
        return true;
    }
}
