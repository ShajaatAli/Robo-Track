package com.mycompany.a3;
import com.codename1.util.MathUtil;
import java.util.List;

public class Strategy2 implements IStrategy {
    @Override
    public void apply(NonPlayerRobot npr, Robot playerRobot, List<Bases> bases) {

        double dx = playerRobot.getX() - npr.getX();
        double dy = playerRobot.getY() - npr.getY();
        double radians = MathUtil.atan2(dy, dx);
        double idealHeading = Math.toDegrees(radians);

        double currentHeading = npr.getHeading();
        double steeringChange = idealHeading - currentHeading;
        npr.steeringHeading(steeringChange);
    }
}
