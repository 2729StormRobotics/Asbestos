package autoModes;

import org.strongback.command.Command;
import org.strongback.drive.TankDrive;


public class MoveForward extends Command{
    private TankDrive drive;

    public  MoveForward(TankDrive _drive){
        drive = _drive;
    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean execute() {
        drive.tank(.2, .2);
        return false;
    }
}
