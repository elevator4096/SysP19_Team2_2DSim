public class RobotB
{
    //general vars
    public String name;
    public Pose pose = new Pose(0,0,0);
    
    // sensors
    public SharpSensor  sharpSensor1; 
    public UsSensor     usSensor1; 
    public LineSensor   frontMiddleLinesensor;
    // actuators
    public DrivingMotor leftDrivingMotor;
    public DrivingMotor rightDrivingMotor;
    public Servo        sharpSensor1Servo;
    public BallThrower  ballThrower1;
    

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
