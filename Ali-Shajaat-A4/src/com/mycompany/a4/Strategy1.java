package com.mycompany.a4;

public class Strategy1 implements IStrategy {
    private NonPlayerRobot npr;
    private int borderPadding = 50;
    private int mapWidth;
    private int mapHeight;

    public Strategy1(NonPlayerRobot npr, int mapWidth, int mapHeight) {
        this.npr = npr;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    @Override
    public void apply() {
        // Ensure robot doesn't go out of bounds
        if (npr.getX() <= 0) {
            npr.setX(0);
            npr.setSteeringHeading(90); // turn right
        } else if (npr.getX() >= mapWidth) {
            npr.setX(mapWidth);
            npr.setSteeringHeading(90); // turn right
        }

        if (npr.getY() <= 0) {
            npr.setY(0);
            npr.setSteeringHeading(90); // turn right
        } else if (npr.getY() >= mapHeight) {
            npr.setY(mapHeight);
            npr.setSteeringHeading(90); // turn right
        }

        // If NonPlayerRobot is near the border, turn
        if(nearBorder()) {
            npr.setSteeringHeading(90); // 90 degree turn
        }
        
        npr.setSpeed(15);
    }


    // Check if NonPlayerRobot is near the border of the map
    private boolean nearBorder() {
        double x = npr.getX();
        double y = npr.getY();
        return x <= borderPadding 
            || x >= mapWidth - borderPadding
            || y <= borderPadding
            || y >= mapHeight - borderPadding;
    }
}
