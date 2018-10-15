import java.awt.Point;
public class LineSensor
{   
    private RobotB robot;
    public Pose pose;
    

    public LineSensor(RobotB robot, Pose pose)
    {
        this.robot = robot;
        this.pose  = pose;
    } 
    
    public Point getValues()
    {
       double r = Constants.lineSensorSpacing/2;
       Point leftSensorPos = new Point((int)(pose.x-r*Math.sin(pose.phi)),(int)(pose.y-r*Math.cos(pose.phi)));
       Point rightSensorPos = new Point((int)(pose.x+r*Math.sin(pose.phi)),(int)(pose.y+r*Math.cos(pose.phi)));
       int leftValue  = robot.getField().getPixelBrightnessGreen(leftSensorPos.x,leftSensorPos.y);
       int rightValue = robot.getField().getPixelBrightnessGreen(rightSensorPos.x,rightSensorPos.y);
       return new Point(0,0);           
    } 
}



