/* Created Mon Sep 11 20:15:47 EDT 2017 */
package robot;

import autoModes.MotionProf1;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import hardware.NavXAccel;
import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.components.Accelerometer;
import org.strongback.components.Motor;
import org.strongback.components.TalonSRX;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.TankDrive;
// import org.strongback.hardware.Hardware;
import org.strongback.hardware.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import autoModes.MoveForward;

public class Robot extends IterativeRobot {

	private TankDrive drive;
	private ContinuousRange forwardSpeed;
	private ContinuousRange reverseSpeed;
    private ContinuousRange turnSpeed;
	private SendableChooser autoChooser;

	private ThreeAxisAccelerometer threeAccel = new NavXAccel();

	private static final Map<String,Supplier<Command>> AUTONOMOUS_SELECTION = new HashMap<>();


	private static final CANTalon _leftMain = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_LEFT_MAIN);
	private static final CANTalon _left2 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_LEFT_2);

	private static final CANTalon _rightMain = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_MAIN);
	private static final CANTalon _right2 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_2);

	

	public static final class Auto{
		public static final String MOTION_PROF_1 = "Motion Profile 1";
		public static final String MOVE_FORWARD = "Move Forward";
	}

    @Override
    public void robotInit() {

		_left2.changeControlMode(CANTalon.TalonControlMode.Follower);
		_left2.set(_leftMain.getDeviceID());

		_right2.changeControlMode(CANTalon.TalonControlMode.Follower);
		_right2.set(_rightMain.getDeviceID());

		TalonSRX leftMain = Hardware.Motors.talonSRX(_leftMain);


		TalonSRX left2 = Hardware.Motors.talonSRX(_left2);
		Motor left = Motor.compose(leftMain, left2);

    	TalonSRX rightMain = Hardware.Motors.talonSRX(_rightMain);
		TalonSRX right2 = Hardware.Motors.talonSRX(_right2);
		right2.invert();
		Motor right = Motor.compose(rightMain, right2).invert();

    	Gamepad xboxDrive = Hardware.HumanInterfaceDevices.xbox360(RobotMap.PORT_XBOX_DRIVE);

    	forwardSpeed = xboxDrive.getRightTrigger();
		reverseSpeed = xboxDrive.getLeftTrigger();
        turnSpeed = xboxDrive.getRightX();

    	drive = new TankDrive(left, right);


    	autoChooser = new SendableChooser();
        autoChooser.addDefault(Auto.MOTION_PROF_1, Auto.MOTION_PROF_1);
    	autoChooser.addObject(Auto.MOTION_PROF_1, Auto.MOTION_PROF_1);
        autoChooser.addObject(Auto.MOVE_FORWARD, Auto.MOVE_FORWARD);


    	SmartDashboard.putData("Autonomous Modes", autoChooser);

    	AUTONOMOUS_SELECTION.clear();
    	AUTONOMOUS_SELECTION.put(Auto.MOTION_PROF_1, ()->new MotionProf1(drive, left, right, threeAccel));
        AUTONOMOUS_SELECTION.put(Auto.MOVE_FORWARD, () ->new MoveForward(drive));
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.restart();
    }

    @Override
    public void teleopPeriodic() {

		//Strongback.logger().warn("Left Speed: " + leftSpeed.read() + "          Right Speed: " + rightSpeed.read());
		double combinedSpeed = forwardSpeed.read() - reverseSpeed.read();
		int mult = -1;
		if (combinedSpeed < 0)
			mult = 1;
		else
			mult = -1;
		drive.arcade(combinedSpeed, mult*turnSpeed.read(), true);
        //drive.tank(0.2, 0.2);
		/*
		_leftMain.set(0.5);
		_left2.set(0.5);
		_rightMain.set(0.5);
		_right2.set(0.5);
		*/
    }

    @Override
    public void autonomousInit() {
        // Start Strongback functions ...
        Strongback.restart();

        // Figure out which autonomous command we will use ...
        String selected = (String) autoChooser.getSelected();
        Supplier<Command> commandSupplier = selected == null ? null : AUTONOMOUS_SELECTION.get(selected);
        if ( commandSupplier == null ) commandSupplier = AUTONOMOUS_SELECTION.get(Auto.MOTION_PROF_1);
        System.out.println("Running autonomous: " + selected);
        Strongback.submit(commandSupplier.get());
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

}