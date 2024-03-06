package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;


public class EnergyStation extends FixedObject implements IDrawable, ICollider
{
	private int capacity = 50 ;
	private boolean collision;
	
	public EnergyStation(int size,double x, double y)
	{
		this.setSize(size);
		setColor(0,255,0);//set the station green
		setLocation(rand.nextInt(1000),rand.nextInt(700));//sets a random location
		this.setCapacity(capacity);
	}
	
	public void setCapacity(int capacity)//sets capacity
	{
		this.capacity = capacity;
	}
	
	public void reduceCapacity()
	{
		this.capacity = 0;
	}
	
	
	public int getCapacity()
	{
		
		return capacity;
		
	}
	
	public double getX()
	{
	    return getLocation().getX();
	}


	public double getY()
	{
	    return getLocation().getY();
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = " Capacity = " + capacity;
		String retval = "EnergyStation: " + parentDesc + myDesc;
		
		return retval;
		
	}
	
	@Override
	public void draw(Graphics g, Graphics n, Point pCmpRelPrnt) 
	{
	        int x = (int) (getX() + pCmpRelPrnt.getX());
	        int y = (int) (getY() + pCmpRelPrnt.getY());
	        
	        g.setColor(this.getColor());
	        g.setColor(ColorUtil.rgb(0,255,0));
	        g.fillArc(x, y,150,150, 0, 360);
	        n.setColor(ColorUtil.BLACK);
	        n.drawString("E", x+56, y+56);
	        
	    }

	public int getEnergyLevel() {
		// TODO Auto-generated method stub
		return capacity;
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
		if ((otherObject instanceof Robot || otherObject instanceof NonPlayerRobot) || (otherObject instanceof Drone ))
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