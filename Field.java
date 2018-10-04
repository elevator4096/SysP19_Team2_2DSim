import java.util.ArrayList;
import java.util.List;
public class Field
{
    private JLabelRot background; 
    private List<Opponent> opponents = new ArrayList<Opponent>();
    
    //Konstruktor: Hintergrund abspeichern und Gegner erzeugen
    public Field(GUI gui,List<Pose> opponentPositions)
    {
        
        // fuer jede in opponentPositions gespeicherte Position einen Gegner erzeugen 
        for(Pose pose : opponentPositions)
        {
            opponents.add(new Opponent(pose));
        }     
        
        //jeden Gegner darstellen
        for(Opponent opponent : opponents)
        {
            gui.drawOpponent(opponent);
        }
        
        //Hintergrundbild laden und darstellen
        background = gui.createBackground1();
        //background = gui.createBackground2();
    }
}
