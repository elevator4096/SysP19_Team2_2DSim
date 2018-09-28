import javax.swing.JLabel;


public class Simulation
{
    private RobotB robotB ;
    private RobotS robotS ;
    
    private SoftwareB softwareB  = new SoftwareB();  
    private SoftwareS softwareS  = new SoftwareS();  
    
    private GUI gui;
    
    private JLabel redRobotB;
    private JLabel blueRobotS;
    
    Clock clock = new Clock();
    
    public static void main()
    {
        Simulation simulation1;
        simulation1 = new Simulation(true);
        
    }
    
    public Simulation(boolean showGui)
    {
        
        //lege position der Roboter fest
        //robotB.pose = new Pose(50,800,0);
        //robotS.pose = new Pose(550,800,0);
        
        robotB  = new RobotB("robotB_Red",new Pose(50,200,0));
        robotS = new RobotS("robotS_Blue",new Pose(550,200,0));
        
        gui = new GUI();
        redRobotB  = gui.drawRobotB(robotB);
        blueRobotS = gui.drawRobotS(robotS);
        gui.createBackground();
        
        softwareB.start(robotB);
        //softwareS.start(robotS);
        
        int counter = 0;
        while(clock.tick()<60000000) // 60 s Simulationszeit
        {
            counter++;
            update();
            
            if (counter*Constants.timeStep > 20000*Constants.simulationSpeed/100)
            {
                wait(20);
                counter=0;
            }
        }
    }   
    
    private void update() 
    {
        robotB.update();
        robotS.update();
        
        softwareB.mainLoop();
        
        gui.reposition(redRobotB,robotB.pose);
    }    
    
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



