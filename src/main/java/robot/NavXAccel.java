package robot;

import org.strongback.components.Accelerometer;
import org.strongback.components.ThreeAxisAccelerometer;

public class NavXAccel implements ThreeAxisAccelerometer{
    @Override
    public Accelerometer getZDirection() {
        return null;
    }

    @Override
    public Accelerometer getXDirection() {
        return null;
    }

    @Override
    public Accelerometer getYDirection() {
        return null;
    }
}
