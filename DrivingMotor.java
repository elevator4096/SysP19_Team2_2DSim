//Implementation eines stark vereinfachten Motortreibers + Motor + Encoder ->  spaeter vermutlich DRV8841 Motortreiber + Motor + Encoder

public class DrivingMotor
{  
    //speed in mm/us
    private final double maxSpeed = (500/1000000)/128;
    
    private double speed = 0; 

    public DrivingMotor()
    {
        
    }
    
    public void setSpeed(int pwmValue)
    {
        if(pwmValue>=0 && pwmValue<=255)
        {
            speed = (pwmValue-128)*maxSpeed;
        }
    }   
    
    public double getSpeed()
    {
        return speed;
    }    

}
