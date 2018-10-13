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
    public Pose closestPoint = new Pose(0,0,0);
    

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
       return lastDistance;           
    } 
    
    public void setClosestPoint(Pose closestPoint)
    {
        this.closestPoint = closestPoint;
    }
    
    public Pose getClosestPoint()
    {
        return closestPoint;
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
        robot.getField().getClosestDistanceToOpponents(this,16);
        
        
        return 0;
    }    
}
