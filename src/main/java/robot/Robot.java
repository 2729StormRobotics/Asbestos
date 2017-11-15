/* Created Mon Sep 11 20:15:47 EDT 2017 */
package robot;

import autoModes.MotionProf1;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import org.strongback.Strongback;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import autoModes.MoveForward;

public class Robot extends IterativeRobot {

	private ContinuousRange forwardSpeed;
	private ContinuousRange reverseSpeed;
    private ContinuousRange turnSpeed;
	private SendableChooser autoChooser;

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


		_rightMain.setInverted(true);

    	Gamepad xboxDrive = Hardware.HumanInterfaceDevices.xbox360(RobotMap.PORT_XBOX_DRIVE);

    	forwardSpeed = xboxDrive.getRightTrigger();
		reverseSpeed = xboxDrive.getLeftTrigger();
        turnSpeed = xboxDrive.getRightX();

    	//drive = new TankDrive(_leftMain, _rightMain);


    	autoChooser = new SendableChooser<>();
        autoChooser.addDefault(Auto.MOTION_PROF_1, new MotionProf1(_leftMain, _rightMain));
    	autoChooser.addObject(Auto.MOTION_PROF_1, new MotionProf1(_leftMain, _rightMain));
        //autoChooser.addObject(Auto.MOVE_FORWARD, new MoveForward());


    	SmartDashboard.putData("Autonomous Modes", autoChooser);

        Waypoint[] points = new Waypoint[] {
                new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
                new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
                new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
        };

        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
        Trajectory trajectory = Pathfinder.generate(points, config);

        TankModifier modifier = new TankModifier(trajectory).modify(0.5);
        File myFile = new File("/home/lvuser/myfile.traj");
        Pathfinder.writeToFile(myFile, trajectory);
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.restart();
    }

    @Override
    public void teleopPeriodic() {
        Strongback.restart();
        Drives drive = new Drives(_leftMain, _rightMain);

		//Strongback.logger().warn("Left Speed: " + leftSpeed.read() + "          Right Speed: " + rightSpeed.read());

		double combinedSpeed = forwardSpeed.read() - reverseSpeed.read();
		double turn = turnSpeed.read();

		drive.stormDrive(combinedSpeed, turn, true);


    }

    @Override
    public void autonomousInit() {
        Strongback.restart();


        Command autonomousCommand = (Command) autoChooser.getSelected();
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

}