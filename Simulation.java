
/* Simulation des Gesamten Spielablaufs durch serielles ausfuehren der einzelnen Updateroutinen der zu simulierenden Komponenten
 * und Darstellung der Zustaende simulierter Objekte durch entsprechende GUI Aufrufe
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
    private List<Pose> opponentPositions = new ArrayList<Pose>();
    
    //drehbare Bilder von Robotern
    private JLabelRot redRobotB;
    private JLabelRot blueRobotS;
    
    //drehbare Bilder von SharpSensoren
    public JLabelRot  frontSharpSensorLabel; 
    public JLabelRot  leftSharpSensorLabel; 
    public JLabelRot  rightSharpSensorLabel; 
    
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
        opponentPositions.add(new Pose(296,237,0));
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
        robotB  = new RobotB(this,"robotB_Red",new Pose(130,120,0));
        robotS = new RobotS(this,"robotS_Blue",new Pose(630,120,0));
        
        //Grafische Oberflaeche erzeugen
        gui = new GUI(showGui);
        
        //drehbare Bilder von SharpSensoren laden und darstellen
        frontSharpSensorLabel = gui.drawSharpSensor(robotB.frontSharpSensor); 
        leftSharpSensorLabel  = gui.drawSharpSensor(robotB.leftSharpSensor);  
        rightSharpSensorLabel = gui.drawSharpSensor(robotB.rightSharpSensor);  
        
        //Drehbare Bilder von Robotern laden und darstellen
        redRobotB  = gui.drawRobotB(robotB);
        blueRobotS = gui.drawRobotS(robotS);
        
        

       
        //Spielfeld mit Gegnern erzeugen und darstellen
        field = new Field(gui,opponentPositions);
        
        //Software von Roboter initialisieren
        softwareB.init(robotB);
        //softwareS.init(robotS);       
             
        //startsignal an Roboter senden
        softwareB.start();
        //softwareS.start();
        
        //Hauptschleife der Simulation wird ausgefuehrt bis Zeit abgelaufen
        // 60 s Simulationszeit
        counter = 0;
        while(clock.tick()<60000000) mainLoop(showGui);
    }   
    
    private void mainLoop(boolean waiting)
    {
        counter++;
        
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
        //robotS.update();
        
        //Hauptschleife der Robotersoftware ausfuehren
        softwareB.mainLoop();
        //softwareS.mainLoop();
        
        //neue Pose(Position und Richtung) des Roboters darstellen
        gui.repose(redRobotB,robotB.pose);
        //neue Pose(Position und Richtung) der Sharpsensoren darstellen
        gui.repose(frontSharpSensorLabel,robotB.frontSharpSensor.pose);
        gui.repose( leftSharpSensorLabel,robotB.leftSharpSensor.pose);
        gui.repose(rightSharpSensorLabel,robotB.rightSharpSensor.pose);
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
    
}



