/* Abstraktionsebene + Treiber + Hardwaresimulation des Roboters
 * Abstraktionsebene zwischen Kontrollsoftware(läuft auch auf Abstraktionsebene des Roboters)  
 * und simulierten Sensoren/Aktoren
 * 
 * ermoeglicht High-Level Befehle wie drive(distanz) oder turn(winkel)
*/

public class RobotB
{
    
    public Simulation simulation;
    
    //allgemeine Variablen
    public String name;
    public Pose pose = new Pose(0,0,0);
    private Pose targetPose = new Pose(0,0,0);
    
    // Sensoren
    public SharpSensor  frontSharpSensor; 
    public SharpSensor  leftSharpSensor; 
    public SharpSensor  rightSharpSensor; 
    public UsSensor     usSensor1; 
    public LineSensor   frontMiddleLinesensor;
    
    // Aktoren
    public DrivingMotor leftDrivingMotor;
    public DrivingMotor rightDrivingMotor;
    public BallThrower  ballThrower1;

    //Konstruktor um Namen und Pose(Position und Richtung) des Roboters festzulegen
    public RobotB(Simulation simulation,String robotName, Pose pose)
    {
        this.simulation = simulation;
        
        this.pose.setPose(pose);
        
        name = robotName;
        
        //Erzeuge Sensoren
        frontMiddleLinesensor   = new LineSensor();
        //Sensoren KORREKT anordnen
        frontSharpSensor        = new SharpSensor(this,new Pose(pose));
        leftSharpSensor         = new SharpSensor(this,new Pose(pose));
        rightSharpSensor        = new SharpSensor(this,new Pose(pose));
        usSensor1               = new UsSensor();
        
        //Erzeuge Aktoren
        leftDrivingMotor        = new DrivingMotor();
        rightDrivingMotor       = new DrivingMotor();
        ballThrower1            = new BallThrower();
        
        //Zielpose(Position und Richtung) auf aktuelle pose setzen
        targetPose.setPose(this.pose);
    }
    
    //Updatefunktion wird von Simulation periodisch aufgerufen um Roboter seinen Zustand aendern zu lassen
    public void update()
    {
        updateStatus();
        updateDevices();
        
    }
    
    //Fahre distanz in mm mit geschwindigkeit in mm/s
    public boolean drive(int distance, int speed)
    {
        targetPose.calculateTargetPose(pose,distance);
        
        leftDrivingMotor.setSpeed(speed);
        rightDrivingMotor.setSpeed(speed);
        return true;
    }   
    
    //drehe um Mittelpunkt des Roboters um Winkel in rad und Winkelgeschwindigkeit in rad/s (+ rechts drehen, - links drehen) 
    public boolean turn(double angle, double angularSpeed)
    {
        targetPose.phi = pose.addPhi(angle);
        
        double motorSpeed = Math.signum(angle)*angularSpeed*Constants.wheelbase*Math.PI/2;
        leftDrivingMotor.setSpeed(motorSpeed);
        rightDrivingMotor.setSpeed(-motorSpeed);
        
        return true;
    }
   
    public int getLastSharpSensorDistance(int sensorNr)
    {
        
        switch (sensorNr) 
        {
            // Drehe dich um PI/4 rad (+ nach rechts drehen, - nach links drehen)
            case 1 : return rightSharpSensor.getDistance() ;

            default: return 0;
        }    
    }
    
    public long getTime()
    {
        return simulation.clock.getTime();
    }
    
    public Field getField()
    {
        return simulation.getField();
    }
    
    //Gibt nur wahr zurueck wenn sich keines der Raeder bewegt
    public boolean isMoving()
    {
        return ( (leftDrivingMotor.getSpeed()!=0)||(rightDrivingMotor.getSpeed()!=0) );
    }    
    
    //Status von diversen Sensoren und Aktoren aktualisieren(Motor ausschalten wenn Zielposition erreicht, etc.)
    private void updateStatus()
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
    
    private void updateDevices()
    {
        frontSharpSensor.pose        = new Pose(pose.x+50*Math.sin(pose.phi),pose.y+50*Math.cos(pose.phi),pose.phi+0);
        leftSharpSensor.pose         = new Pose(pose.x-50*Math.cos(pose.phi),pose.y+50*Math.sin(pose.phi),pose.phi-Math.PI/2);
        rightSharpSensor.pose        = new Pose(pose.x+50*Math.cos(pose.phi),pose.y-50*Math.sin(pose.phi),pose.phi+Math.PI/2);
        
        frontSharpSensor.update();
        leftSharpSensor.update();
        rightSharpSensor.update();
    }
    
    // Berechne Bewegung in einem Simulationsschritt anhand der Radgeschwindigkeiten
    private boolean Move()
    {
        //Ein wenig Mathemagie ;-) zur Berechnung der neuen Pose
        // aus der aktuellen pose und den Radgeschwindigkeiten
        
        //gefahrene Distanz mit linkem Rad berechnen
        double sL       =  leftDrivingMotor.getSpeed()*Constants.timeStep/1000000;
        //gefahrene Distanz mit rechtem Rad berechnen
        double sR       =  rightDrivingMotor.getSpeed()*Constants.timeStep/1000000;
        
        
        // Radgeschwindigkeiten sind praktisch gleich? - reine Translation
        if (Math.abs(sL-sR)<0.00000001)
        {
            double phi = pose.phi;
            double r = sR;
            double dX = r*Math.sin(phi);
            double dY = r*Math.cos(phi);
            pose.setPose(pose.x+dX,pose.y+dY, phi);

        } 
        // Radgeschwindigkeiten sind praktisch genau entgegengesetzt? - reine Rotation
        else if ( (Math.abs(sL+sR)<0.000001))
        {
            double d = Constants.wheelbase;
            pose.setPose(pose.x,pose.y, pose.addPhi((sL-sR)/(Math.PI*d ) ));
        }
        //Radgeschwindigkeiten sind unterschiedlich -> Fahre teil eines Kreisabschnittes -> Funktioniert noch nicht richtig
        else
        {

            /*
             * 
        
             // Kurvenradius nur berechnen wenn sL != sR -> sonst division durch 0
             * 
             */
            
            // Division durch 0 vermeiden
            if (Math.abs(sR)<0.000000001)
            {
                sR = 0.000000001;
            }
            double d = Constants.wheelbase;
            
            double a        = (d/(sL/sR-1));
            double alpha    = (sR/a)/(Math.PI/2);
            
            double r = (d+2*a)*Math.sin(alpha/2)/2;
            
            double phi = pose.addPhi(alpha/2);
            
            double dX = r*Math.sin(phi);
            double dY = r*Math.cos(phi);
            
            //debug
            double alpaDeg = alpha*180/Math.PI;
            double phiDeg = phi*180/Math.PI;
            
            pose.setPose(pose.x+dX,pose.y+dY, phi);
            
        }    
        
        
        
        return true;
    }    
}
