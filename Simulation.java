/* Hinzugefügter Testkommentar von Chris
 * 
 */


import javax.swing.JLabel;

public class Simulation
{
    private RobotB robotB  = new RobotB("robotB_Red");  
    private RobotS robotS = new RobotS("robotS_Blue");
    
    private SoftwareB softwareB  = new SoftwareB();  
    private SoftwareS softwareS  = new SoftwareS();  
    
    private GUI gui;
    
    private JLabel redRobotB;
    private JLabel blueRobotS;
    
    
    public static void main()
    {
        Simulation simulation1;
        simulation1 = new Simulation(true);
        
    }
    
    public Simulation(boolean showGui)
    {
        //lege position der Roboter fest
        robotB.pose = new Pose(50,800,0);
        robotS.pose = new Pose(550,800,0);
        
        
        gui = new GUI();
        redRobotB  = gui.drawRobotB(robotB);
        blueRobotS = gui.drawRobotS(robotS);
        gui.createBackground();
        
        softwareB.start(robotB);
        softwareS.start(robotS);
        
        for( long time = 0; time < 10000000; time += 1000)
        {
            update(time);
        }
    }   
    
    private void update(long time) 
    {
        robotB.update(time);
        robotS.update(time);
        
        softwareB.mainLoop(time);
        
        gui.reposition(redRobotB,robotB.pose);
    }    
    
}



