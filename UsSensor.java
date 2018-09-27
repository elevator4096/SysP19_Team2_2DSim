public class UsSensor
{
    private final int maxDistance =     800; //mm
    private final int minDistance =     100; //mm
    private final int waitingTime =  100000; //us TO BE CHANGED 
    
    private int[][] maxDistances = {{0,2490},{10,2230},{20,2050},{30,1240},{40,340}}; 
    
    private int distance;
    private int startTime;

    public UsSensor()
    {
        
    }
    
    public void startMeasuring(int time)
    {
        startTime = time;
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
