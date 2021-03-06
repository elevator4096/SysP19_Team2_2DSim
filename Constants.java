public abstract class Constants
{   
    // Physik / Spielfeld
    public static final double g                = 9.81;
    public static final double opponentRadius   = 24;
    
    // Mechanik
    public static final int wheelbase           = 160; // Achsenabstand in mm
    public static final int throwDistance       = 220; // Wurfdistanz in mm
    
    // Sensoren
    public static final int lineSensorSpacing   = 6;   // Liniensensor Abstand in mm 
    
    // Simulation
    public static final double simulationSpeed  = 100; // % Realtime
    public static final int timeStep            = 900; // us
    
    // Feldgroesse in mm(unabhaengig von Bildschirmgroesse)
    public static final int fieldSizeMMX        = 760; // pixels
    public static final int fieldSizeMMY        = 1000; // pixels  
    
    public static final int maxXError           = 1;
    public static final int maxYError           = 1;
    public static final double maxPhiError      = 0.1*Math.PI/360;
    
    
}
