package java.main.autoModes;

import jaci.pathfinder.modifiers.TankModifier;
import org.strongback.command.Command;
import org.strongback.drive.TankDrive;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class Path1 extends Command {
    private TankDrive drive;
    public Path1(TankDrive drive) {
        super(drive);

        this.drive = drive;
    }

    @Override
    public void initialize() {
        Waypoint[] points = new Waypoint[] {
                new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
                new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
                new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
        };

        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
        Trajectory trajectory = Pathfinder.generate(points, config);

        TankModifier modifier = new TankModifier(trajectory).modify(0.5);
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        return false;
    }



}