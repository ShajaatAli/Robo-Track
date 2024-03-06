package com.mycompany.a3;
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
	
	public MoveableObject(double x, double y, int speed2, int size, int heading) {
		// TODO Auto-generated constructor stub
	}

	public void Move(int elapsedTime) {
	    Point2D oldLocation = getLocation(); 

	    double propAng = 90 - objHeading;
	    double distance = speed * elapsedTime / 1000.0; 
	    double deltaX = 0;
	    double deltaY = 0;

	    if (objHeading == 0 || objHeading == 180) { 
	        deltaY = Math.sin(Math.toRadians(propAng)) * distance;
	    } else if (objHeading == 90 || objHeading == 270) { 
	        deltaX = Math.cos(Math.toRadians(propAng)) * distance;
	    } else { 
	        deltaX = Math.cos(Math.toRadians(propAng)) * distance;
	        deltaY = Math.sin(Math.toRadians(propAng)) * distance;
	    }

	    Point2D newLocation = new Point2D(oldLocation.getX() + deltaX, oldLocation.getY() + deltaY);

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