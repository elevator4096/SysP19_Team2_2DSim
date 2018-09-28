public class SoftwareB
{
    
    RobotB robot;
    

    public SoftwareB()
    {
        
    }
    
    public void start(RobotB robotB)
    {
        robot = robotB;
        
        //Only for REAL robot
        //while(true) mainLoop();
        
        robot.drive(500,100);
    }
    
    public void mainLoop()
    {
        
        
    }    
    
    
}
