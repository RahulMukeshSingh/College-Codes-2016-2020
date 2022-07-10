package InnovativePracts;

import java.awt.Color;

import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GGMouseListener;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import ch.aplu.robotsim.Gear;
import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.LightSensor;
import ch.aplu.robotsim.SensorPort;
import ch.aplu.util.Monitor;

public class RoboticsPractical3 {
	private static int xOld,yOld;
	public RoboticsPractical3(){
		LegoRobot robot=new LegoRobot();
		Gear gear=new Gear();
		LightSensor ls=new LightSensor(SensorPort.S3);
		robot.addPart(gear);
		robot.addPart(ls);
		Monitor.putSleep();
		while(true){
			if(ls.getValue() > 800){
				gear.leftArc(0.1);
			}else{
				gear.rightArc(0.1);
			}
		}
	}
	public static void main(String[] args) {
		new RoboticsPractical3();

	}
	private static void _init(final GameGrid gg){
		gg.setTitle("Drag with left mouse");
		gg.addMouseListener(new GGMouseListener() {
			
			@Override
			public boolean mouseEvent(GGMouse mouse) {
				Location loc=gg.toLocationInGrid(mouse.getX(), mouse.getY());
				switch(mouse.getEvent()){
				case GGMouse.lPress:
					xOld=loc.x;
					yOld=loc.y;
					break;
				case GGMouse.lDrag:
					gg.getBg().drawLine(xOld, yOld, loc.x, loc.y);
					xOld=loc.x;
					yOld=loc.y;
					break;
				case GGMouse.lRelease:
					Monitor.wakeUp();
					break;
				}
				return true;
			}
		}, GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease);
		gg.getBg().setPaintColor(Color.BLACK);
		gg.getBg().setLineWidth(33);
	}

}
