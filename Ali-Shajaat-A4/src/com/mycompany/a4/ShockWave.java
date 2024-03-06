package com.mycompany.a4;

import java.util.Random;
import java.util.Vector;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.GeneralPath;

public class ShockWave extends MoveableObject {
	private Point2D location;
    private double heading;
    private double speed;
    private long creationTime;
    private static final Random RAND = new Random();
    private static final double MAX_SPEED = 10.0;
    int startX = 50; 
    int startY = 50;
    int control1X = 100; 
    int control1Y = 200; 
    int control2X = 200; 
    int control2Y = 100; 
    int endX = 250; 
    int endY = 50; 

	
	public ShockWave() {
		this.location = location;
		this.heading = RAND.nextDouble() * 360;
		this.speed = RAND.nextDouble() * MAX_SPEED;
        this.creationTime = System.currentTimeMillis();
	}

	@Override
	public void handleCollision(GameObject otherObject) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void draw(Graphics g,  Point pCmpRelPrnt) {
        GeneralPath path = new GeneralPath();
        path.moveTo(location.getX(), location.getY());
        path.curveTo(control1X, control1Y, control2X, control2Y, endX, endY);
        
        g.setColor(ColorUtil.rgb(240,248,255));
        g.drawShape(path,null);
	}
	
	public Point2D getLocation() {
        return location;
    }
	
	public boolean isExpired(int worldWidth, int worldHeight) {
        double maxLifetime = Math.sqrt(worldWidth * worldWidth + worldHeight * worldHeight) / speed;
        return (System.currentTimeMillis() - creationTime) / 1000.0 > maxLifetime;
    }
}
