package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Bases extends FixedObject implements IDrawable, ICollider
{
	private int seqNum;
	private boolean collision;
	
	public Bases()
	{
		this(0,0,0);
	}
	public Bases(int seqNum, double x, double y)
	{
	    super(x, y);
	    setColor(70, 50, 200);
	    this.setSize(10);
	    this.seqNum = seqNum;
	}

	public int getSeqNumb()
	{
		return seqNum;
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
		String myDesc = " Seq num = " + seqNum;
		return "Base: " + parentDesc + myDesc;
	}
	
	@Override
	public void draw(Graphics g, Graphics n,Point pCmpRelPrnt)
	{
    		int x = (int) (getX() + pCmpRelPrnt.getX());
	        int y = (int) (getY() + pCmpRelPrnt.getY());
			g.setColor(this.getColor());
		    g.setColor(ColorUtil.rgb(70, 50, 200));
		    
		    g.drawRect(x, y, 120, 120);
		    g.fillRect(x, y, 120, 120);
		    
		    n.setColor(ColorUtil.BLACK);
	        n.drawString("B", x+46, y+46);

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
