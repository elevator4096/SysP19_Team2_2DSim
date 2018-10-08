//Implementation eines stark vereinfachten Motortreibers + Motor + Encoder ->  spaeter vermutlich DRV8841 Motortreiber + Motor + Encoder

public class DrivingMotor
{  
    
    private double speed = 0; 

    public DrivingMotor()
    {
        
    }
    
    public void setSpeed(double speed)
    {
        this.speed = speed;
    }   
    
    public double getSpeed()
    {
        return speed;
    }    

}
