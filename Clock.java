public class Clock
{
    private long time = 0;
    
    
    public Clock()
    {
        
    }
    
    public long getTime()
    {
        return time;
    }
    
    public long tick()
    {
        time += Constants.timeStep;
        return time;
    }    

}
