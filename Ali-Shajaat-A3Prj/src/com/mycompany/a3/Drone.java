package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Drone extends MoveableObject implements IDrawable, ICollider
{
	private boolean collision;
	
	public Drone(double x, double y,int speed, int size, int heading)
	{
		super(x, y, speed, size, heading);
		setSpeed(speed);
		setHeading(heading);
		setSize(size);
		setColor(255,0,255);
		//this.speed = speed;
	}
	
	public Drone()
	{
		this(0,0,0,0,0);
	}
	public void randomHeading()//heading of drones should be random value between 0 and 359
	{
		this.setHeading(rand.nextInt(359));
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		return "Drone: " + parentDesc;
	}
	public double getX()
	{
	    return getLocation().getX();
	}


	public double getY()
	{
	    return getLocation().getY();
	}
	
	@Override
	public void draw(Graphics g, Graphics n, Point pCmpRelPrnt)
	{
    		int x = (int) (getX() + pCmpRelPrnt.getX());
	        int y = (int) (getY() + pCmpRelPrnt.getY());
	        int[] xPoints = { x, (x - 20), (x + 20), x };
			int[] yPoints = { (y + 30), (y - 30), (y - 30), (y + 30) };
			int nPoints = 4;     
			g.setColor(this.getColor());
		    g.setColor(ColorUtil.rgb(255,0,255));
	        g.drawPolygon(xPoints, yPoints, nPoints);
			g.fillPolygon(xPoints, yPoints, nPoints);
			n.setColor(ColorUtil.BLACK);
	        n.drawString("D", x-8, y-18);
              
    }
	@Override
	public boolean collidesWith(ICollider otherObject) {
		boolean result = false;
		
		double thisCenterX = this.getLocation().getX();
		double thisCenterY = this.getLocation().getY();
		
		double otherCenterX = ((GameObject)otherObject).getLocation().getX();
		double otherCenterY = ((GameObject)otherObject).getLocation().getY();
		
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		
		double distBetweenCentersSqr = (dx * dx + dy * dy);
		
		int thisRadius= this.getSize() / 2;
		int otherRadius= ((GameObject)otherObject).getSize() / 2;
		
		int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) { result = true ; }
		
		return result;
	}
	@Override
	public void handleCollision (ICollider otherObject) {
		// TODO Auto-generated method stub
		if ((otherObject instanceof Robot || otherObject instanceof NonPlayerRobot) || (otherObject instanceof Bases ))
		{
			collision(otherObject);
			otherObject.collision(otherObject);
		}
	}

	@Override
	public void collision(ICollider otherObject) {
		collision = true;
		
	}

	@Override
	public boolean getCollision(ICollider otherObject) {
		// TODO Auto-generated method stub
		return collision;
	}
}