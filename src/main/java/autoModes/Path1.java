package autoModes;

import com.ctre.CANTalon;
import hardware.NavXManager;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.strongback.Strongback;
import edu.wpi.first.wpilibj.command.Command;
import org.strongback.drive.TankDrive;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import java.io.File;

public class Path1 extends Command {
    private TankDrive drive;
    private CANTalon left;
    private CANTalon right;
    private EncoderFollower leftEncoder;
    private EncoderFollower rightEncoder;
    private double leftSpeed;
    private double rightSpeed;

    public Path1(CANTalon left, CANTalon right) {

        this.drive = drive;
        this.left = left;
        this.right = right;
        File myFile = new File("/home/lvuser/myfile.traj");
        Trajectory trajectory = Pathfinder.readFromFile(myFile);

        TankModifier modifier = new TankModifier(trajectory).modify(0.5);

        leftEncoder = new EncoderFollower(modifier.getLeftTrajectory());
        rightEncoder = new EncoderFollower(modifier.getRightTrajectory());




        leftEncoder.configureEncoder(left.getEncPosition(), 1024, 4);
        rightEncoder.configureEncoder(right.getEncPosition(), 1024, 4);

        leftEncoder.configurePIDVA(1.0, 0.0, 0.0, 1 / 10, 0);

    }

    @Override
    public void initialize() {


    }

    @Override
    public void execute() {

        double leftSpeed = 1;
        double rightSpeed = 1;

        //while (true) {

        //while (leftSpeed != 0 && rightSpeed != 0) {
            Strongback.logger().warn("running Path1");
            double l = leftEncoder.calculate(left.getEncPosition());
            double r = rightEncoder.calculate(right.getEncPosition());

            double gyro_heading = NavXManager.getNavX().getRawGyroX();   // Assuming the gyro is giving a value in degrees
            double desired_heading = Pathfinder.r2d(leftEncoder.getHeading());  // Should also be in degrees

            double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
            double turn = 0.8 * (-1.0 / 80.0) * angleDifference;

            leftSpeed = l + turn;
            rightSpeed = r - turn;

            left.set(leftSpeed);
            right.set(rightSpeed);
        //}



        // TODO Auto-generated method stub
    }

    public boolean isFinished(){
        if (leftSpeed != 0 && rightSpeed != 0) {
            return true;
        }
        return false;
    }



}