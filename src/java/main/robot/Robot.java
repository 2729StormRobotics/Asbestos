/* Created Mon Sep 11 20:15:47 EDT 2017 */
package java.main.robot;

import org.strongback.Strongback;


import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

//	private TankDrive drive;
//	private ContinuousRange leftSpeed;
//	private ContinuousRange rightSpeed;
//	private SendableChooser autoChooser;
//	private static final Map<String,Supplier<Command>> AUTONOMOUS_SELECTION = new HashMap<>();

	
	public static final class Auto{
		//public static final String MOTION_PROF_1 = "Motion Profile 1";
	}
	
    @Override
    public void robotInit() {
        /*
    	Motor leftMain = Hardware.Motors.talon(RobotMap.PORT_MOTOR_DRIVE_LEFT_MAIN);
    	Motor left2 = Hardware.Motors.talon(RobotMap.PORT_MOTOR_DRIVE_LEFT_2);
    	//Motor left3 = Hardware.Motors.talon(RobotMap.PORT_MOTOR_DRIVE_LEFT_3);
    	//Motor left = Motor.compose(leftMain, left2, left3);
		Motor left = Motor.compose(leftMain, left2);

    	Motor rightMain = Hardware.Motors.talon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_MAIN);
    	Motor right2 = Hardware.Motors.talon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_2);
    	//Motor right3 = Hardware.Motors.talon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_3);
    	//Motor right = Motor.compose(rightMain, right2, right3).invert();
		Motor right = Motor.compose(rightMain, right2);

    	Gamepad xboxDrive = Hardware.HumanInterfaceDevices.logitechDualAction(RobotMap.PORT_XBOX_DRIVE);
    	leftSpeed = xboxDrive.getLeftY();
    	rightSpeed = xboxDrive.getRightY();
    	
    	drive = new TankDrive(left, right);
    	

    	autoChooser = new SendableChooser();
    	autoChooser.addDefault(Auto.MOTION_PROF_1, Auto.MOTION_PROF_1);
    	SmartDashboard.putData("Autonomous Modes", autoChooser);
    	AUTONOMOUS_SELECTION.clear();
    	AUTONOMOUS_SELECTION.put(Auto.MOTION_PROF_1, ()->new MotionProf1(drive));
        */
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.restart();
    }

    @Override
    public void teleopPeriodic() {

		//drive.tank(leftSpeed.read(), rightSpeed.read());
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

}