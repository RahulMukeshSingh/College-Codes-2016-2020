package practs1;

import ch.aplu.robotsim.TurtleRobot;

public class RoboticsPracts1 {
	public RoboticsPracts1(){
		TurtleRobot robot=new TurtleRobot();
		for (int i = 0; i < 4; i++) {
			robot.forward(100);
			robot.drawStringAt("FORWARD", 0);
			robot.left(90);
			robot.drawStringAt("LEFT", 0);
			robot.right(60);
			robot.drawStringAt("RIGHT", 0);
			robot.backward(50);
			robot.drawStringAt("BACKWARD", 0);
		}
	}
	public static void main(String[] args) {
		new RoboticsPracts1();
		
	}

}
