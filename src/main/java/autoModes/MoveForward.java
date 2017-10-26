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
        //hi simmar hi buddy boy. how was your meme today?  8/8.  we improvised adapted and overcame
    }

    @Override
    public boolean execute() {
        drive.tank(.2, .2);
        return false;
    }
}
