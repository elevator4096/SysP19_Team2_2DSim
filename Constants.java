public abstract class Constants
{   
    //Physik
    public static final double g = 9.81;
    
    //Mechanik
    public static final int wheelbase = 100; // Achsenabstand in mm
    
    //Simulation
    public static final double simulationSpeed = 100; // % Realtime
    
    public static final int fieldSizeX      = 760; // pixels
    public static final int fieldSizeY      = 1000; // pixels
    
    public static final double pixelPmm     = 1;
    public static final int timeStep        = 900; // us
    
    public static final int maxXError       = 2;
    public static final int maxYError       = 2;
    public static final double maxPhiError  = 2*Math.PI/360;
    
    
}
