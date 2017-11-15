package autoModes;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.strongback.Strongback;


public class MotionProf1 extends CommandGroup {

    public MotionProf1(CANTalon left, CANTalon right) {
        Strongback.logger().warn("Inside MotionProf1.java");
        addSequential(new Path1(left, right));
    }


}