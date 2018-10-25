public class SoftwareS
{
    public RobotS robot;
    int state = 0;
    boolean gameStarted = false;

    public SoftwareS()
    {

    }
    
    //initialisiert den Roboter sobald die Simulationsumgebung fertig geladen ist
    public void init(RobotS robotS)
    {
        robot = robotS;           
    }
    
    //wird ausgefuehrt sobald das Startsignal empfangen wurde
    public void start()
    {
        gameStarted = true;
    }
    
    public void mainLoop()
    { 
        if (gameStarted)
        {
            if (!robot.ballPosession)
            {
                //driveAround();
                driveUturn();
            }
            throwTest();
        }    
    }   
    
    public void throwTest()
    {
        if (robot.getTime()>12000000)
        {
            robot.throwBall();
        }    
    }
    
    //Fahre hin und her
    private void driveUturn()
    {
        //Schrittverkettung welche den Zustand wechselt sobald der Roboter wieder stillsteht 
        if (!robot.isMoving())
        {
            state += 1;
            switch (state) 
            {
                // Drehe dich um PI/4 rad (+ nach rechts drehen, - nach links drehen)
                case 1 : robot.turn(-Math.PI/2, Math.PI/4) ; break;
                case 2 : robot.drive(200,200)       ; break;
                
                case 3 : robot.turn(Math.PI, Math.PI/4) ; break;
                case 4 : robot.drive(200,100)       ; break;

                default: state = 3-1;
            }    
        }
        
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
                case 1 : robot.turn(-Math.PI/4, Math.PI/4) ; break;
                case 2 : robot.drive(250,200)       ; break;
                
                case 3 : robot.turn(Math.PI/2, Math.PI/4) ; break;
                //case 4 : robot.drive(220,150)       ; break;
                case 4 : robot.turn(Math.PI*2-0.1, Math.PI/5) ; break;

                default: state = 3-1;
            }    
        }
        
    } 
    
}
