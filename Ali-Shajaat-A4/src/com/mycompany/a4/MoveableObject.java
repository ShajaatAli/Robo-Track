package com.mycompany.a4;
public abstract class MoveableObject extends GameObject
{

	protected int speed;
	private float objHeading;
	private boolean col = false;
	
	public MoveableObject(float x, float y) {
        super();
        setLocation(x, y);
    }

    public MoveableObject() {
        super();
        setLocation(0, 0);
    }
    
	public MoveableObject(int objHeading) 
	{
		this.objHeading= objHeading;
	}

	public void Move() {
	    float newX = this.getX() + (float) Math.cos(Math.toRadians(90 - this.objHeading)) * this.speed;
	    float newY = this.getY() + (float) Math.sin(Math.toRadians(90 - this.objHeading)) * this.speed;

	    int offset = 0;
	    if (this instanceof Drone || this instanceof Robot || this instanceof NonPlayerRobot) {
	        offset = 50;
	    }

	    int originalX = (int) getX();
	    int originalY = (int) getY();

	    // Check for wall collisions
	    if (originalX + newX + offset >= MapView.getMapViewWidth() + originalX) {
	        newX = MapView.getMapViewWidth() - offset - originalX;
	    } else if (originalX + newX - offset <= originalX) {
	        newX = originalX + offset;
	    }

	    if (originalY + newY + offset >= originalY + GameWorld.getGameHeight()) {
	        newY = GameWorld.getGameHeight() - offset - originalY;
	    } else if (originalY + newY - offset <= originalY) {
	        newY = originalY + offset;
	    }

	    this.setLocation(newX, newY);
	}

	public boolean getcol() {
		return col;
	}public void setcol(boolean col) {
		this.col = col;
	}

	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	public int getSpeed()
	{
		return speed;
	}
	public void setHeading(float d)
	{
		this.objHeading = d;
	}
	public float getHeading()
	{
		return objHeading;
	}
	public String toString() 
	{
		String parentDesc = super.toString();
		String myDesc = " objHeading = " + objHeading + " speed = " + speed ;
		return parentDesc + myDesc;
	}

}