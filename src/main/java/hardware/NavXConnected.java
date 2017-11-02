package hardware;

import org.strongback.components.Switch;

public class NavXConnected implements Switch {
    @Override
    public boolean isTriggered() {
        return NavXManager.getNavX().isConnected();
    }
}
