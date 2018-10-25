
/**
 * Beschreiben Sie hier die Klasse BallThrower.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class BallThrower
{
    private RobotB robot;
    private Ball ball;
    public Pose pose;
    
    public boolean ballDetected = false;
    
    public BallThrower(Ball ball, Pose pose)
    {
        this.ball = ball;
        this.pose  = pose;
    }    
    
    public boolean getBallPosession()
    {
        return ballDetected;
    }
    
    public boolean throwBall()
    {
        Pose throwPose = new Pose(pose.x+Constants.throwDistance*Math.sin(pose.phi),pose.y+Constants.throwDistance*Math.cos(pose.phi),pose.addPhi(0)); 
        ball.goTo(throwPose);
        return true;
    }  
}
