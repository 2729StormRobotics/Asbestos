package org.frc2729.robot;

import org.strongback.command.CommandGroup;
import org.strongback.drive.TankDrive;

public class MotionProf1 extends CommandGroup{
	public MotionProf1(TankDrive drive) {
		sequentially(new Path1(drive));
	}

}