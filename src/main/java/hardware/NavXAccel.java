package hardware;

import org.strongback.components.Accelerometer;
import org.strongback.components.ThreeAxisAccelerometer;

public class NavXAccel implements ThreeAxisAccelerometer{
    private static Accelerometer accelX;
    private static Accelerometer accelY;
    private static Accelerometer accelZ;

    public NavXAccel() {
        super();
        if (accelX == null) {
            accelX = NavXManager.getNavX()::getRawAccelX;
        }
        if (accelY == null) {
            accelY = NavXManager.getNavX()::getRawAccelY;
        }
        if (accelZ == null) {
            accelZ = NavXManager.getNavX()::getRawAccelZ;
        }
    }

    @Override
    public Accelerometer getZDirection() {
        return accelZ;
    }

    @Override
    public Accelerometer getXDirection() {
        return accelX;
    }

    @Override
    public Accelerometer getYDirection() {
        return accelY;
    }
}
