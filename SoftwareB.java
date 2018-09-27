public class SoftwareB
{
    //simulation time in us
    long time = 0;
    
    //Timer in us
    private long TimerLeftDrivingMotor = 0;
    private long TimerRightDrivingMotor = 0;
    
    RobotB robot;
    

    public SoftwareB()
    {
        
    }
    
    public void start(RobotB robotB)
    {
        robot = robotB;
        
        //Only for REAL robot
        //while(true) mainLoop();
    }
    
    public void mainLoop(long newTime)
    {
        time = newTime;
        drive(100,200);
        
    }    
    
    //drive distance in mm
    public boolean drive(int distance, int pwm)
    {
        robot.rightDrivingMotor.setSpeed(pwm);
        TimerRightDrivingMotor = time + Math.round(distance/robot.rightDrivingMotor.getSpeed());
        robot.leftDrivingMotor.setSpeed(pwm);
        TimerLeftDrivingMotor = time + Math.round(distance/robot.leftDrivingMotor.getSpeed());
        
        return true;
    }    
    
    //Status von diversen Sensoren und Aktoren aktualisieren(Motor nach x Sekunden ausschalten etc.)
    private void update_status()
    {
        //Motoren abschalten wenn timer abgelaufen
        if (TimerRightDrivingMotor<time)
        {
            robot.rightDrivingMotor.setSpeed(128);
        }
        if (TimerLeftDrivingMotor<time)
        {
            robot.leftDrivingMotor.setSpeed(128);
        }
    }    
}
