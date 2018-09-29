//Simulierte Uhr in Mikrosekunden fuer korrektes Timing aller Komponenten
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
