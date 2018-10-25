/** 
 * Simulation des Gesamten Spielablaufs durch serielles ausfuehren der einzelnen Updateroutinen der zu simulierenden Komponenten
 * und Darstellung der Zustaende simulierter Objekte durch entsprechende GUI Aufrufe
 * @author none
 * @version 22.10.2018
 */


import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class Simulation
{
    //Hardwareroboter ( robotB = Roboter Buchs, robotS = Roboter St. Gallen 
    private RobotB robotB ;
    private RobotS robotS ;
    
    //Software fuer Roboter
    private SoftwareB softwareB  = new SoftwareB();  
    private SoftwareS softwareS  = new SoftwareS();  
    
    //Grafische Oberflaeche
    private GUI gui;
    private Field field;
    private Ball ball = new Ball();
    private List<Pose> opponentPositions = new ArrayList<Pose>();
    
    //drehbare Bilder von Robotern
    private JLabelRot redRobotB;
    private JLabelRot blueRobotS;
    
    //Blauen punkt laden und offscreen darstellen
    JLabelRot frontSharpSensorPointB; 
    JLabelRot leftSharpSensorPointB; 
    JLabelRot rightSharpSensorPointB;
    
    JLabelRot frontSharpSensorPointS; 
    JLabelRot leftSharpSensorPointS; 
    JLabelRot rightSharpSensorPointS; 
    
    //drehbare Bilder von SharpSensoren
    public JLabelRot  frontSharpSensorLabelB; 
    public JLabelRot  leftSharpSensorLabelB; 
    public JLabelRot  rightSharpSensorLabelB; 
    
    public JLabelRot  frontSharpSensorLabelS; 
    public JLabelRot  leftSharpSensorLabelS; 
    public JLabelRot  rightSharpSensorLabelS; 
    
    //drehbare Bilder von Liniensensoren
    public JLabelRot  frontMiddleLineSensorLabelB;
    
    public JLabelRot  frontMiddleLineSensorLabelS;
    
    //drehbare Bilder von Ballwerfern
    public JLabelRot  ballThrowerLabelB;
    
    public JLabelRot  ballThrowerLabelS;
    
    public JLabelRot  ballLabel;
    
    
    //Hintergrundbild
    private JLabelRot background;
    
    //Schleifen Zaehlvariable
    private int counter = 0;
    
    public Clock clock = new Clock();
    
    public static void main(String[] args)
    {
        //Einzelne Simulation erzeugen
        Simulation simulation1;
        simulation1 = new Simulation(true);
        
    }
    
    public Simulation(boolean showGui)
    {
        //Gegnerpositionen manuell festlegen
        opponentPositions.add(new Pose(126,238,0));
        //opponentPositions.add(new Pose(296,237,0));
        opponentPositions.add(new Pose(459,237,0));
        opponentPositions.add(new Pose(458,496,0));
        opponentPositions.add(new Pose(628,497,0));
        
        /*     
        opponentPositions.add(new Pose(126,238,0));
        opponentPositions.add(new Pose(296,237,0));
        opponentPositions.add(new Pose(459,237,0));
        opponentPositions.add(new Pose(627,239,0));
        opponentPositions.add(new Pose(126,498,0));
        opponentPositions.add(new Pose(297,497,0));
        opponentPositions.add(new Pose(458,496,0));
        opponentPositions.add(new Pose(628,497,0));
        opponentPositions.add(new Pose(122,758,0));
        opponentPositions.add(new Pose(298,754,0));
        opponentPositions.add(new Pose(457,756,0));
        opponentPositions.add(new Pose(628,755,0));
         */

        //Roboter mit Name und Pose(Position und Richtung) erzeugen
        robotB  = new RobotB(this,"robotB_Red",new Pose(130,110,0));
        robotS = new RobotS(this,"robotS_Blue",new Pose(550,110,0));
        
        //RobotS Ball geben ( nur zum TESTEN!!!)
        robotB.ballThrower1.ballDetected = false;
        robotS.ballThrower1.ballDetected = true;
        ball.pose = new Pose(robotS.pose);
        
        //Grafische Oberflaeche erzeugen
        gui = new GUI(showGui);
        
        //drehbares Bild von Ball laden und darstellen
        ballLabel = gui.drawBall(ball);
        
        //drehbare Bilder von LineSensoren laden und darstellen
        frontMiddleLineSensorLabelB = gui.drawLineSensor(robotB.frontMiddleLineSensor);
        
        frontMiddleLineSensorLabelS = gui.drawLineSensor(robotS.frontMiddleLineSensor);
        
        //drehbare Bilder von Ballwerfern laden und darstellen
        ballThrowerLabelB = gui.drawBallThrower(robotB.ballThrower1);
        
        ballThrowerLabelS = gui.drawBallThrower(robotS.ballThrower1);
        
        //SharpMessPunkte laden
        frontSharpSensorPointB = gui.drawBluePoint(new Pose(0,0,0));
        leftSharpSensorPointB  = gui.drawBluePoint(new Pose(0,0,0));
        rightSharpSensorPointB = gui.drawBluePoint(new Pose(0,0,0));
        
        frontSharpSensorPointS = gui.drawBluePoint(new Pose(0,0,0));
        leftSharpSensorPointS  = gui.drawBluePoint(new Pose(0,0,0));
        rightSharpSensorPointS = gui.drawBluePoint(new Pose(0,0,0));
        
        //drehbare Bilder von SharpSensoren laden und darstellen
        frontSharpSensorLabelB = gui.drawSharpSensor(robotB.frontSharpSensor); 
        leftSharpSensorLabelB  = gui.drawSharpSensor(robotB.leftSharpSensor);  
        rightSharpSensorLabelB = gui.drawSharpSensor(robotB.rightSharpSensor);  
        
        frontSharpSensorLabelS = gui.drawSharpSensor(robotS.frontSharpSensor); 
        leftSharpSensorLabelS  = gui.drawSharpSensor(robotS.leftSharpSensor);  
        rightSharpSensorLabelS = gui.drawSharpSensor(robotS.rightSharpSensor);          
        
        //Drehbare Bilder von Robotern laden und darstellen
        redRobotB  = gui.drawRobotB(robotB);
        blueRobotS = gui.drawRobotS(robotS);
        

        
        

       
        //Spielfeld mit Gegnern erzeugen und darstellen
        field = new Field(gui,opponentPositions);
        
        //Software von Roboter initialisieren
        softwareB.init(robotB);
        softwareS.init(robotS);       
        
        //Hauptschleife der Simulation wird ausgefuehrt bis Zeit abgelaufen
        // 60 s Simulationszeit
        counter = 0;
        while(clock.tick()<60000000) mainLoop(showGui);
    }   
    
    private void mainLoop(boolean waiting)
    {
        counter++;
        
        //Nach x Zyklen Startsignal an Roboter geben(simulierte Bootzeit)
        if (counter==5)
        {
           //startsignal an Roboter senden
           softwareB.start();
           softwareS.start();
        }    
        
        //Simulationsschritt ausfuehren
        update();
        
        // Alle 20 ms simulierter Zeit 20ms warten um Echtzeitsimulation mit ca 50 FPS zu erhalten
        // Simulationsgeschwindigkeit kann ueber Constants.simulationSpeed veraendert werden
        if (counter*Constants.timeStep > 20000*Constants.simulationSpeed/100)
        {
            if (waiting) wait(20);
            counter -= 20000/Constants.timeStep;
        }
    }    
    
    //Einzelner Simulationsschritt
    private void update() 
    {
        //lasse Roboter seinen neuen veraenderten Zustand ermitteln(Positionsaenderung, Zeitaenderung, etc.)
        robotB.update();
        robotS.update();
        
        //Hauptschleife der Robotersoftware ausfuehren
        softwareB.mainLoop();
        softwareS.mainLoop();
        
        reposeRobotB();
        reposeRobotS();
        gui.repose(ballLabel,ball.pose);
        
        
        
    }    
    
    private void reposeRobotB()
    {
        //neue Pose(Position und Richtung) des Roboters darstellen
        gui.repose(redRobotB,robotB.pose);
        //neue Pose(Position und Richtung) der Sharpsensoren darstellen
        gui.repose(frontSharpSensorLabelB,robotB.frontSharpSensor.pose);
        gui.repose(leftSharpSensorLabelB ,robotB.leftSharpSensor .pose);
        gui.repose(rightSharpSensorLabelB,robotB.rightSharpSensor.pose);
        
        gui.repose(frontSharpSensorPointB,robotB.frontSharpSensor.getClosestPoint());
        gui.repose(leftSharpSensorPointB ,robotB.leftSharpSensor .getClosestPoint());
        gui.repose(rightSharpSensorPointB,robotB.rightSharpSensor.getClosestPoint());
        
        gui.repose(frontMiddleLineSensorLabelB,robotB.frontMiddleLineSensor.pose);
        gui.repose(ballThrowerLabelB,robotB.ballThrower1.pose);
    }
    
    private void reposeRobotS()
    {
        //neue Pose(Position und Richtung) des Roboters darstellen
        gui.repose(blueRobotS,robotS.pose);
        //neue Pose(Position und Richtung) der Sharpsensoren darstellen
        gui.repose(frontSharpSensorLabelS,robotS.frontSharpSensor.pose);
        gui.repose(leftSharpSensorLabelS ,robotS.leftSharpSensor .pose);
        gui.repose(rightSharpSensorLabelS,robotS.rightSharpSensor.pose);
        
        gui.repose(frontSharpSensorPointS,robotS.frontSharpSensor.getClosestPoint());
        gui.repose(leftSharpSensorPointS ,robotS.leftSharpSensor .getClosestPoint());
        gui.repose(rightSharpSensorPointS,robotS.rightSharpSensor.getClosestPoint());
        
        gui.repose(frontMiddleLineSensorLabelS,robotS.frontMiddleLineSensor.pose);
        gui.repose(ballThrowerLabelS,robotS.ballThrower1.pose);
    }
        
    
    //Wartefunktion um Simulation zu verlangsamen
    private void wait(int waitingTime)
    {
        try
        {
            Thread.sleep(waitingTime);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    } 
    
    public Field getField()
    {
        return field;
    }
    
    public Ball getBall()
    {
        return ball;
    }
}



