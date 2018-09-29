//We don't have another robot Software just use the Software of RobotB
public class SoftwareS extends SoftwareB
{
    RobotS robot;
    boolean active = true;
    
    public void start(RobotS robotS)
    {
        robot = robotS;
        
        //Only for REAL robot
        //while(true) mainLoop();
        robot.turn(Math.PI/4, 100);
        
    }

    public SoftwareS()
    {

    }
}
