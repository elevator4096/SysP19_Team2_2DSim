public class Ball
{
    public Pose pose;
    private Pose targetPose;
    public Ball()
    {
        this.pose  = new Pose(0,0,0);
    }
    
    public void goTo(Pose pose)
    {
        //ball fliegt einfach sofort zum Ziel(bisher ohne Animation)
        this.pose = pose;
    }    
}
