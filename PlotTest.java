import java.io.IOException;

import SimpleJavaPlot.Plot;
import SimpleJavaPlot.Plot.Line;


/**
 * Beschreiben Sie hier die Klasse PlotTest.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class PlotTest
{
    public PlotTest()
    {
        // configuring everything by default
        Plot plot = Plot.plot(null).
	// setting data
	series(null, Plot.data().
		xy(1, 2).
		xy(3, 4), null);

        // saving sample_minimal.png

        
        
        try{
            plot.save("sample_minimal", "png");
        }
        catch(IOException ex){
        }

    }    
}
