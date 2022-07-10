package practs2;

import ch.aplu.robotsim.Gear;
import ch.aplu.robotsim.TurtleRobot;

public class RoboticsPracts2 {
	public RoboticsPracts2() {
		TurtleRobot robot = new TurtleRobot();
		Gear gear=new Gear();
		robot.addPart(gear);
		for (int i = 0; i < 4; i++) {
			gear.forward(2000);
			robot.drawStringAt("FORWARD", 0);
			gear.left(1800);
			robot.drawStringAt("LEFT", 0);
			gear.right(1200);
			robot.drawStringAt("RIGHT", 0);
			gear.backward(1000);
			robot.drawStringAt("BACKWARD", 0);
		}
	}

	public static void main(String[] args) {
		new RoboticsPracts2();

	}

}