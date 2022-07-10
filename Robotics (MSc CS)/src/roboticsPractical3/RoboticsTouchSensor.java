package roboticsPractical3;

import ch.aplu.robotsim.Gear;
import ch.aplu.robotsim.TouchSensor;
import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.SensorPort;
import ch.aplu.robotsim.RobotContext;

public class RoboticsTouchSensor {
	public RoboticsTouchSensor() {
		RobotContext.useObstacle("sprites/bar0.gif", 250, 150);
		RobotContext.useObstacle("sprites/bar1.gif", 400, 250);
		RobotContext.useObstacle("sprites/bar2.gif", 250, 400);
		RobotContext.useObstacle("sprites/bar3.gif", 100, 250);
		RobotContext.showNavigationBar();
		LegoRobot robot = new LegoRobot();
		TouchSensor tSensor = new TouchSensor(SensorPort.S3);
		Gear gear = new Gear();
		robot.addPart(gear);
		robot.addPart(tSensor);
		gear.setSpeed(60);
		gear.forward();
		while (true) {
			if (tSensor.isPressed()) {
				gear.left(90);
				gear.backward();
				gear.forward();

			}
		}

	}

	public static void main(String[] args) {
		new RoboticsTouchSensor();
	}
}