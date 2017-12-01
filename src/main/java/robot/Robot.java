package robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

    private static final CANTalon _leftMain = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_LEFT_MAIN);
    private static final CANTalon _left2 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_LEFT_2);

    private static final CANTalon _rightMain = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_MAIN);
    private static final CANTalon _right2 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_2);

    @Override
    public void robotInit() {

        _left2.changeControlMode(CANTalon.TalonControlMode.Follower);
        _left2.set(_leftMain.getDeviceID());

        _right2.changeControlMode(CANTalon.TalonControlMode.Follower);
        _right2.set(_rightMain.getDeviceID());


        _rightMain.setInverted(true);

    }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() { }

    @Override
    public void teleopInit() { }

    @Override
    public void testInit() { }


    @Override
    public void disabledPeriodic() { }
    
    @Override
    public void autonomousPeriodic() { }

    @Override
    public void teleopPeriodic() { }

    @Override
    public void testPeriodic() { }
}