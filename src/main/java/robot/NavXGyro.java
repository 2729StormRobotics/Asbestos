package robot;

import org.strongback.components.*;

public class NavXGyro implements Gyroscope {

    @Override
    public double getRate() {
        return 0;
    }

    @Override
    public double getAngle() {
        return 0;
    }
}
