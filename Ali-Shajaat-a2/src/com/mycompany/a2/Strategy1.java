package com.mycompany.a2;
import java.util.List;
import com.codename1.util.MathUtil;


public class Strategy1 implements IStrategy {
    @Override
    public void apply(NonPlayerRobot npr, Robot playerRobot, List<Bases> bases) {
        Bases nextBase = null;
        for (Bases base : bases) {
            if (base.getSeqNumb() == npr.getLastBaseReached() + 1) {
                nextBase = base;
                break;
            }
        }

        if (nextBase != null) {
            double dx = nextBase.getLocationX() - npr.getX();
            double dy = nextBase.getLocationY() - npr.getY();
            double radians = MathUtil.atan2(dy, dx);
            double idealHeading = Math.toDegrees(radians);

            double currentHeading = npr.getHeading();
            double steeringChange = idealHeading - currentHeading;
            npr.steeringHeading(steeringChange);
        }
    }
}
