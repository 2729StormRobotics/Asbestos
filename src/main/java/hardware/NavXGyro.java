package hardware;

import org.strongback.components.*;

public class NavXGyro implements Gyroscope {

    @Override
    public double getRate() {
        return NavXManager.getNavX().getRate();
    }

    @Override
    public double getAngle() {
        return NavXManager.getNavX().getAngle();
    }
}
