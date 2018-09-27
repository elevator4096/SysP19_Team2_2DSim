public class RobotB
{
    //general vars
    public String name; 
    
    // sensors
    private SharpSensor  sharpSensor1; 
    private UsSensor     usSensor1; 
    private LineSensor   frontMiddleLinesensor;
    // actuators
    private DrivingMotor leftDrivingMotor;
    private DrivingMotor rightDrivingMotor;
    private Servo        sharpSensor1Servo;
    private BallThrower  ballThrower1;
    

    public RobotB(String robotName)
    {
        
        name = robotName;
        
        //create Sensors
        frontMiddleLinesensor   = new LineSensor();
        sharpSensor1            = new SharpSensor();
        usSensor1               = new UsSensor();
        
        //create actuators
        leftDrivingMotor        = new DrivingMotor();
        rightDrivingMotor       = new DrivingMotor();
        sharpSensor1Servo       = new Servo();
        ballThrower1            = new BallThrower();
        
    }
    
}
