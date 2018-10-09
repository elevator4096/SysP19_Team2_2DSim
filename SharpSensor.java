public class SharpSensor
{
    public final int maxDistance =     300; //mm
    public final int minDistance =      40; //mm
    public final int waitingTime =   42000; //us TO BE CHANGED 
    
    private int distance;
    private int startTime;

    public SharpSensor()
    {
        
    }
    
    public void startMeasuring(int time)
    {
        startTime = time;
        //distance =
    }    
    
    public int getMaxDistance()
    {
        return maxDistance;
    }    
    
    public int getMinDistance()
    {
        return minDistance;
    }    
    
    public int getDistance(int time)
    {
        if (time > startTime+waitingTime)
        { 
            return distance;
        }
        else 
        {
            return 0;
        }
            
    }    

}
