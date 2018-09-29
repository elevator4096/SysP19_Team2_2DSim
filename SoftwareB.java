public class SoftwareB
{
    
    RobotB robot;
    
    boolean active = true;

    public SoftwareB()
    {
        
    }
    
    public void start(RobotB robotB)
    {
        robot = robotB;
        
        //Only for REAL robot
        //while(true) mainLoop();
        robot.turn(Math.PI/4, 100);
        
    }
    
    public void mainLoop()
    {
        if (active)
        {
            if (!robot.isMoving())
            {
                robot.drive(500,100);
                active = false;
            }
            
            
        }
        
    }    
    
    
}
