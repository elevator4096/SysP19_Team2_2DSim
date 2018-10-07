/* Software welche spaeter direkt auf der Abstraktionsebene des echten Roboters laeuft
 * 
 * verwendet robot als Abstraktionsebene welche Low-Level Treiber enthaelt und Funktionen wie Drive(Distanz in mm) oder Turn(Winkel in Bogenmass) unterstuetzt
 * 
 * Soll mithilfe der Treiber den Roboter praezise steuern, Gegner umfahren, paesse spielen und sich dabei an die Spielregeln halten sowie den finalen Korb werfen
 */
public class SoftwareB
{
    
    RobotB robot;
    
    int state = 0;
    
    boolean gameStarted = false;

    public SoftwareB()
    {
        
    }
    
    
    //initialisiert den Roboter sobald die Simulationsumgebung fertig geladen ist
    public void init(RobotB robotB)
    {
        robot = robotB;
        
        //Only for REAL robot
        //while(true) mainLoop();     
    }
    
    //wird ausgefuehrt sobald das Startsignal empfangen wurde
    public void start()
    {
        gameStarted = true;
        
        //NUR ZUM TESTEN( Zielposition des Roboters weit ausserhalb setzen)
        robot.drive(2000,100)       ;
    }
    
    //Wird in der Hauptschleife ausgefuehrt(hier von Simulationsumgebung - Real in Endlosschleife)
    public void mainLoop()
    {
        
        //herumfahren wenn Spiel bereits gestartet ist
        //if (gameStarted) driveAround();
        
        if (gameStarted) testDrive();


    }    
    
    public void testDrive()
    {
        
        
        
        robot.rightDrivingMotor.setSpeed(0);
        robot.leftDrivingMotor.setSpeed(100);
    }
    
    //herumfahren um Fahrfunktionen zu testen
    public void driveAround()
    {
        //Schrittverkettung welche den Zustand wechselt sobald der Roboter wieder stillsteht 
        if (!robot.isMoving())
        {
            state += 1;
            switch (state) 
            {
                // Drehe dich um PI/4 rad (+ nach rechts drehen, - nach links drehen)
                case 1 : robot.turn(Math.PI/4, Math.PI/2) ; break;
                case 2 : robot.drive(400,200)       ; break;
                
                case 3 : robot.turn(Math.PI/2, Math.PI/4) ; break;
                case 4 : robot.drive(220,150)       ; break;

                default: state = 3-1;
            }    
        }
        
    }    
}
