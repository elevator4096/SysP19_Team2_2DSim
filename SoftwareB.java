/* Software welche spaeter direkt auf der Abstraktionsebene des echten Roboters laeuft
 * 
 * verwendet robot als Abstraktionsebene welche Low-Level Treiber enthaelt und Funktionen wie Drive(Distanz in mm) oder Turn(Winkel in Bogenmass) unterstuetzt
 * 
 * Soll mithilfe der Treiber den Roboter praezise steuern, Gegner umfahren, paesse spielen und sich dabei an die Spielregeln halten sowie den finalen Korb werfen
 */
public class SoftwareB
{
    public RobotB robot;
    int state = 0;
    boolean gameStarted = false;

    public SoftwareB()
    {
        
    }
    
    
    //initialisiert den Roboter sobald die Simulationsumgebung fertig geladen ist
    public void init(RobotB robotB)
    {
        robot = robotB;           
    }
    
    //wird ausgefuehrt sobald das Startsignal empfangen wurde
    public void start()
    {
        gameStarted = true;
    }
    
    //Wird in der Hauptschleife ausgefuehrt(hier von Simulationsumgebung - Real in Endlosschleife)
    public void mainLoop()
    { 
        if (gameStarted)
        {
            if (!robot.ballPosession)
            {
                driveAround();
                //motorTestDriv();
                //sensorDrive();
            }
        }    
    }    
    
    private void sensorDrive()
    {      
       //Schrittverkettung welche den Zustand wechselt sobald der Roboter wieder stillsteht 
       
       if ((robot.getLastSharpSensorDistance(1)<48)&&(robot.getLastSharpSensorDistance(1)>0))
       {
           //System.out.println(robot.getLastSharpSensorDistance(1));
       }  
       
        if (!robot.isMoving())
        {
            state += 1;
            switch (state) 
            {
                // Drehe dich um PI/4 rad (+ nach rechts drehen, - nach links drehen)
                case 1 : robot.turn(Math.PI/2, Math.PI/4) ; break;
        
                default: state = 3-1;
            }    
       }
    }
    
    private void motorTestDrive()
    {      
        robot.rightDrivingMotor.setSpeed(-100);
        robot.leftDrivingMotor.setSpeed(100);
    }
    
    //herumfahren um Fahrfunktionen zu testen
    private void driveAround()
    {
        //Schrittverkettung welche den Zustand wechselt sobald der Roboter wieder stillsteht 
        if (!robot.isMoving())
        {
            state += 1;
            switch (state) 
            {
                // Drehe dich um PI/4 rad (+ nach rechts drehen, - nach links drehen)
                case 1 : robot.turn(Math.PI/2, Math.PI/4) ; break;
                case 2 : robot.drive(120,200)       ; break;
                
                case 3 : robot.turn(-Math.PI/2, Math.PI/4) ; break;
                case 4 : robot.drive(240,150)       ; break;
                case 5 : robot.turn(Math.PI/2, Math.PI/4) ; break;
                case 6 : robot.drive(300,150)       ; break;
                case 7 : robot.turn(Math.PI/2, Math.PI/4) ; break;
                
                
                default: state = 42/*3-1*/;
            }    
        }
        
    }    
}
