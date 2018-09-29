/* Abstraktionsebene + Treiber + Hardwaresimulation des Roboters
 * Abstraktionsebene zwischen Kontrollsoftware(läuft auch auf Abstraktionsebene des Roboter)  
 * und simulierten Sensoren/Aktoren
 * 
 * ermoeglicht High-Level Befehle wie drive(distanz) oder turn(winkel)
*/

public class RobotB
{
    
    //general vars
    public String name;
    public Pose pose = new Pose(0,0,0);
    private Pose targetPose = new Pose(0,0,0);
    
    // sensors
    public SharpSensor  sharpSensor1; 
    public UsSensor     usSensor1; 
    public LineSensor   frontMiddleLinesensor;
    // actuators
    public DrivingMotor leftDrivingMotor;
    public DrivingMotor rightDrivingMotor;
    public Servo        sharpSensor1Servo;
    public BallThrower  ballThrower1;

    public RobotB(String robotName, Pose pose)
    {
        this.pose.setPose(pose);
        
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
        
        targetPose.setPose(this.pose);
    }
    
    public void update()
    {
        update_status();
        
        
        
        
        
        
    }
    
        //drive distance in mm
    public boolean drive(int distance, int speed)
    {
        targetPose.calculateTargetPose(pose,distance);
        
        leftDrivingMotor.setSpeed(speed);
        rightDrivingMotor.setSpeed(speed);
        return true;
    }   
    
    public boolean turn(double angle, double speed)
    {
        targetPose.phi = pose.phi + angle;
        
        if (angle > 0)
        {
            leftDrivingMotor.setSpeed(-speed/2);
            rightDrivingMotor.setSpeed(speed/2);
        } else
        {
            leftDrivingMotor.setSpeed(speed/2);
            rightDrivingMotor.setSpeed(-speed/2);
        }
        
        return true;
    }
    
    public boolean isMoving()
    {
        return ( (leftDrivingMotor.getSpeed()!=0)||(rightDrivingMotor.getSpeed()!=0) );
    }    
    
    //Status von diversen Sensoren und Aktoren aktualisieren(Motor nach x Sekunden ausschalten etc.)
    private void update_status()
    {   
        if ( isMoving() && pose.closeEnough(targetPose) ) 
        {
            leftDrivingMotor.setSpeed(0);
            rightDrivingMotor.setSpeed(0);
        }
        
        if (isMoving())
        {
            Move();
        }
    }
    
    private boolean Move()
    {
        //Ein wenig Mathemagie ;-) zur berechnung der neuen Pose
        // aus der aktuellen pose und den Radgeschwindigkeiten
        
        double sL       =  leftDrivingMotor.getSpeed()*Constants.timeStep/1000000;
        double sR       =  rightDrivingMotor.getSpeed()*Constants.timeStep/1000000;
        

        if (Math.abs(sL-sR)<0.000001)
        {
            double phi = pose.phi;
            double r = sR;
            double dX = r*Math.sin(phi);
            double dY = r*Math.cos(phi);
            pose.setPose(pose.x+dX,pose.y+dY, phi);

        } 
        else if (Math.abs(sL+sR)<0.000001)
        {
            double d = Constants.wheelbase;
            pose.setPose(pose.x,pose.y, pose.phi + (sR-sL)/(Math.PI*d ) );
        }else
        {

            /* FUNKTIONIERT NOCH NICHT!!!
             * 
             //TODO sL = 0 abfragen div by zero!!!
        
             // Kurvenradius nur berechnen wenn sL != sR -> sonst division durch 0
             * 
             * 
            double d = Constants.wheelbase;
            
            double a        = d/(sR/sL -1);
            double alpha    = sL/a;
            
            double beta     = Math.PI/2 - alpha/2;
            double r = 2*(d+a)*Math.sin(alpha/2);
            
            double phi = pose.phi + Math.PI/2 - beta;
            
            double dX = r*Math.sin(phi);
            double dY = r*Math.cos(phi);
            
            pose.setPose(pose.x+dX,pose.y+dY, phi);
            */
        }    
        
        
        
        return true;
    }    
}
