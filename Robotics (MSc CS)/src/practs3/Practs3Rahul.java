package practs3;

import ch.aplu.robotsim.Gear;
import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.RobotContext;
import ch.aplu.robotsim.SensorPort;
import ch.aplu.robotsim.TouchSensor;

public class Practs3Rahul {
    public Practs3Rahul(){
        LegoRobot lr=new LegoRobot();
        Gear g=new Gear();
        TouchSensor ts=new TouchSensor(SensorPort.S3);
        lr.addPart(g);
        lr.addPart(ts);
        g.setSpeed(50);
        g.forward();
        while(true){
            if(ts.isPressed()){
                g.left(100);
                g.backward();
                g.forward();
                
            }
        }
        
    }
    public static void main(String[] args){
        RobotContext.useObstacle("sprites/bar3.gif",150,150);
        RobotContext.useObstacle("sprites/bar1.gif",300,150);
        RobotContext.useObstacle("sprites/bar0.gif",150,150);
        RobotContext.useObstacle("sprites/bar2.gif",150,300);
        RobotContext.showNavigationBar();
        new Practs3Rahul();
    }
}