/* Created Mon Sep 11 20:15:47 EDT 2017 */
package robot;

import autoModes.MotionProf1;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Robot extends IterativeRobot {

	private TankDrive drive;
	private ContinuousRange leftSpeed;
	private ContinuousRange rightSpeed;
	private SendableChooser autoChooser;
	private static final Map<String,Supplier<Command>> AUTONOMOUS_SELECTION = new HashMap<>();


	private static final CANTalon _leftMain = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_LEFT_MAIN);
	private static final CANTalon _left2 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_LEFT_2);

	private static final CANTalon _rightMain = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_MAIN);
	private static final CANTalon _right2 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_2);


	public static final class Auto{
		public static final String MOTION_PROF_1 = "Motion Profile 1";
	}

    @Override
    public void robotInit() {
    	Motor leftMain = Hardware.Motors.talonSRX(_leftMain);
    	Motor left2 = Hardware.Motors.talonSRX(_left2);
		Motor left = Motor.compose(leftMain, left2);

    	Motor rightMain = Hardware.Motors.talonSRX(_rightMain);
    	Motor right2 = Hardware.Motors.talonSRX(_right2);
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

    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.restart();
    }

    @Override
    public void teleopPeriodic() {

		Strongback.logger().warn("Left Speed: " + leftSpeed.read() + "          Right Speed: " + rightSpeed.read());
		drive.tank(leftSpeed.read(), rightSpeed.read());
		/*
		_leftMain.set(0.5);
		_left2.set(0.5);
		_rightMain.set(0.5);
		_right2.set(0.5);
		*/
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

}