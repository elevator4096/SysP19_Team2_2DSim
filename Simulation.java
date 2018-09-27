public class Simulation
{
    private RobotB robotB  = new RobotB("robotB_Red");  
    private RobotS robotS = new RobotS("robotS_Blue");
    
    private SoftwareB softwareB  = new SoftwareB();  
    private SoftwareS softwareS  = new SoftwareS();  
    
    
    public static void main()
    {
        Simulation simulation1;
        simulation1 = new Simulation(true);
        
    }
    
    public Simulation(boolean showGui)
    {
        softwareB.run(robotB);
        softwareS.run(robotS);
        
        if (showGui)
        {
            GUI gui;
            gui = new GUI();
            gui.drawRobotB(robotB);
            gui.drawRobotS(robotS);
            gui.createBackground();
        }
        
    }    
}


