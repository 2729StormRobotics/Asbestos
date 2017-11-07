package autoModes;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.CommandGroup;


public class MotionProf1 extends CommandGroup {
    public MotionProf1(CANTalon left, CANTalon right) {
        addSequential(new Path1(left, right));
    }

}