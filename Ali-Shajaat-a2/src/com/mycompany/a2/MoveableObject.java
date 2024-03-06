package com.mycompany.a2;
import com.codename1.ui.geom.Point2D;

public abstract class MoveableObject extends GameObject
{

	protected int speed;
	private double objHeading;
	
	public MoveableObject(double x, double y)
	{
        super(x, y);
    }

    public MoveableObject() 
    {
        super(0, 0);
    }
	public MoveableObject(int objHeading) 
	{
		this.objHeading= objHeading;
	}
	
	public void Move() 
	{
		Point2D oldLocation = getLocation(); 
		
		Point2D newLocation = new Point2D(0,0);
		
		double propAng = 90-objHeading;
		double deltaX = 0;
		double deltaY = 0;
		
		if(objHeading == 0 || objHeading == 180) 
		{
			deltaY = Math.sin(Math.toRadians(propAng)) * speed;
		}
		else if( objHeading == 90 || objHeading == 270 )
			deltaX = Math.cos(Math.toRadians(propAng)) * speed;
		else 
		{
			deltaX = Math.cos(Math.toRadians(propAng)) * speed; 
			deltaY = Math.sin(Math.toRadians(propAng)) * speed;
		}
		
		
		newLocation.setX(deltaX + oldLocation.getX()); 
		newLocation.setY(deltaY + oldLocation.getY());
		
		setLocation(newLocation);
		
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	public int getSpeed()
	{
		return speed;
	}
	public void setHeading(double d)
	{
		this.objHeading = d;
	}
	public double getHeading()
	{
		return objHeading;
	}
	public String toString() 
	{
		String parentDesc = super.toString();
		String myDesc = " heading = " + objHeading + " speed = " + speed ;
		return parentDesc + myDesc;
	}

}