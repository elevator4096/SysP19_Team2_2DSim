import java.util.ArrayList;
import java.util.List;
public class Field
{
    private JLabelRot background; 
    private List<Opponent> opponents = new ArrayList<Opponent>();
    
    //Konstruktor: Hintergrund abspeichern und Gegner erzeugen
    public Field(JLabelRot background,List<Pose> opponentPositions)
    {
        this.background = background;
        
        // fuer jede in opponentPositions gespeicherte Position einen Gegner erzeugen 
        for(Pose pose : opponentPositions)
        {
            opponents.add(new Opponent(pose));
        }      
    }
}
