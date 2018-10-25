
/**
 * Beschreiben Sie hier die Klasse BallThrower.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class BallThrower
{
    private RobotB robot;
    public Pose pose;
    
    public BallThrower(RobotB robot, Pose pose)
    {
        this.robot = robot;
        this.pose  = pose;
    }    
    
    public boolean getBallPosession()
    {
        return true;
    }
}
