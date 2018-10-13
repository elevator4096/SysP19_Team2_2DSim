public class SharpSensor
{
    public final int maxDistance =     300; //mm
    public final int minDistance =      40; //mm
    public final int waitingTime =   25200; //us WORST CASE( datasheet https://www.pololu.com/file/0J713/GP2Y0A41SK0F.pdf)
    
    private int pendingDistance;
    private int lastDistance;
    private long startTime;
    
    private RobotB robot;
    public Pose pose;
    

    public SharpSensor(RobotB robot, Pose pose)
    {
        this.robot = robot;
        this.pose = pose;
    } 
    
    public int getMaxDistance()
    {
        return maxDistance;
    }    
    
    public int getMinDistance()
    {
        return minDistance;
    }    
    
    public int getDistance()
    {
        if (robot.getTime() > startTime+waitingTime)
        { 
            return lastDistance;
        }
        else 
        {
            return 0;
        }
            
    }  
    
    public void update()
    {
        if (robot.getTime() >= startTime+waitingTime)
        { 
            lastDistance = pendingDistance;
            pendingDistance = measureDistance();
        }
    }
    
    private int measureDistance()
    {
        robot.getField().getClosestDistanceToOpponents(pose, 16);
        
        
        return 0;
    }    
}
