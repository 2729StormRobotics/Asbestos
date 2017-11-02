package hardware;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class NavXManager {

    private static AHRS ahrs;

    public static AHRS getNavX() {
        if (ahrs == null) {
            ahrs = new AHRS(SPI.Port.kMXP, (byte)200);
        }
        return ahrs;
    }
}
