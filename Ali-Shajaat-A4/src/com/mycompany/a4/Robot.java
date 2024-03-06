package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.AffineTransform;
import com.codename1.ui.geom.Point;

public class Robot extends MoveableObject implements ISteerable, IDrawable, ICollider
{ 
	private int energyLevel;
	private int energyConsumptionRate;
	private int maximumSpeed = 40;
	private int steeringHeading;
	private int damageLevel;
	private int lastBaseReached;
	private boolean isDead =  false;	
	//private boolean collision;
	private float localX;
	private float localY;
	private int maxDamage;
	private int life;

	 
	public Robot(float x, float y,int speed, int heading) 
	{
		super(x, y);
		setSpeed(speed);
	    //setHeading(heading);
	   //this.setSize(50);
	    setColor(255, 0, 0);
	     this.energyLevel = 20;
	     this.damageLevel = 0;
	     this.isDead = false;
	     this.life = 3;
	    // this.setSpeed(0);
		this.setHeading(0);
		this.life = 3;
		this.setSteeringHeading(0);
		//this.setMaximumSpeed(20);
		this.setEnergyConsumptionRate(1);
		this.setEnergyLevel(2000);
		this.setDamageLevel(0);
		this.setLastBaseReached(1);
		this.setMaxDamage(500);
	}
	
	public Robot() 
	{
        this(0,0,0,0);
    }

		
	public void setEnergyConsumptionRate(int energyConsumptionRate)
	{
		
		this.energyConsumptionRate = energyConsumptionRate;
	}
	public int energyConsumptionRate()
	{
		return energyConsumptionRate;
	}
	
	public int getEnergyLevel()
	{
		return energyLevel;
	}
	
	public void steeringHeading (float steeringChange)// Updating the robot direction
	{
		this.setHeading(this.getHeading()+ steeringChange );
	}
	
	public void setRobotsSpeed(int speed)//Checker to make sure speed is less than maximum speed
	{
		if (speed < this.maximumSpeed )
		{
			this.speed = speed;
		}
		else 
		{
			System.out.println ("Speed is over the max");
		}
	}
	
	public  void setSteeringHeading(int steeringHeading)
    {
    	this.steeringHeading = steeringHeading;
    }
	
	public int getSteeringHeading()
	{
		return steeringHeading;
	}
		
	public void setMaximumSpeed(int maximumSpeed)
	{
		
		this.maximumSpeed = maximumSpeed;
	}
	public int getMaximumSpeed ()
	{
		return maximumSpeed;
	}
	
	public void setEnergyLevel(int energyLevel)
	{
		
		this.energyLevel = energyLevel;
	}
	
	public void setDamageLevel(int damageLevel)
	{
		
		this.damageLevel = damageLevel;
	}
	public int getDamageLevel()
	{
		return damageLevel;
	}
	
	public void setLastBaseReached(int lastBaseReached)
	{
		this.lastBaseReached = lastBaseReached;
	}
	public int getLastBaseReached()
	{
		return lastBaseReached;
	}
	
	public void checkDamageLevel()// sets the speed based on damage
	{
		if (this.getDamageLevel() == 0)
		{
			this.setSpeed(0);
		}
		if (this.getEnergyLevel() == 0)
		{
			this.setSpeed(0);
		}
		if (this.getSpeed() == 0)
		{
			this.isDead= true;
		}
	}
	
	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}
	public int getMaxDamage() {
		return this.maxDamage;
	}
	public boolean getIsDead()
	{
		return isDead;
	}
	public void setIsDead(boolean isDead)
	{
		this.isDead = isDead;
	}
	
	public void collisionOfRobots()
	{
		this.damageLevel = (damageLevel + 1) ;				
		this.updateRobotsSpeed();
	
	}
	public void collisionOfDrones()
	{
		this.damageLevel = (damageLevel + 1) ;				
		this.updateRobotsSpeed();
	}
	
	
	public void updateRobotsSpeed() 
	{
		
		if(this.getSpeed() < this.getMaximumSpeed() * this.getDamageLevel())
		{
			
		}
		else
		{
			this.setSpeed(this.getMaximumSpeed() * (this.getDamageLevel()/2));
		}
		this.checkDamageLevel();
	}
	public void reduceEnergy() 
	{
		energyLevel = energyLevel - energyConsumptionRate;
		if(energyLevel == 0)
		{
			this.setRobotsSpeed(0);
		}
	}
	public void reset()
	{
		this.setColor(255,0,0);
		this.setHeading(0);
		this.setSpeed(1);
		this.setLastBaseReached(1);
		this.setLocation(300,300);
		this.setEnergyLevel(20);
		this.setIsDead(false);
		this.setDamageLevel(0);
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = " steeringDirection = " + getSteeringHeading() 
		+ " energyLevel = " + energyLevel + " damageLevel = " + damageLevel;
		return "Robot: " + parentDesc + myDesc;		
		
	}

	public float getX()
	{
	    return (float) getLocation().getX();
	}


	public float getY()
	{
	    return (float) getLocation().getY();
	}


	public void collisionWithRobot() 
	{
	    collisionOfRobots();
	}


	public void collisionWithNpr() {
	    collisionOfRobots();
	}
	


	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		
		int x = (int) (getX() + pCmpRelPrnt.getX());
        int y = (int) (getY() + pCmpRelPrnt.getY());
	    int[] xPoints = { x, (x - 20), (x + 20), x };
	    int[] yPoints = { (y + 40), (y - 40), (y - 40), (y + 40) };
	    int nPoints = 4;

	    g.setColor(this.getColor());
	    g.setColor(ColorUtil.rgb(0, 0, 255));
	    Transform saveAT = g.getTransform();
	    g.translate(Math.round(getMyTranslation().getTranslateX()), Math.round(getMyTranslation().getTranslateY()));
	    g.transform(getMyRotation());
	    g.transform(getMyScale());
	    g.translate(-Math.round(getMyTranslation().getTranslateX()), -Math.round(getMyTranslation().getTranslateY()));
	    g.drawPolygon(xPoints, yPoints, nPoints);
	    g.fillPolygon(xPoints, yPoints, nPoints);
	    g.setColor(ColorUtil.BLACK);
	    g.drawString("R", x-10, y-20);
	    g.setTransform(saveAT);
	}

	


	@Override
	public void steeringHeading(int HeadingChange) 
	{
		// TODO Auto-generated method stub
		if(HeadingChange < 0 && getHeading() + HeadingChange < 0)
			setHeading(getHeading() + HeadingChange + 360);
		else
		{
			if(getHeading() + HeadingChange >= 360)
			{
				setHeading(getHeading() + HeadingChange - 360);
				
			}
			else
				setHeading(getHeading()+HeadingChange);
		}
		
	}
	
	@Override
	public boolean collidesWith(GameObject otherObject) {
	    boolean result = false;
	    
	    float thisCenterX = (float) this.getLocation().getX();
	    float thisCenterY = (float) this.getLocation().getY();
	    
	    if (otherObject == null || otherObject.getLocation() == null) {
	        return false;
	    }

	    float otherCenterX = (float) ((GameObject)otherObject).getLocation().getX();
	    float otherCenterY = (float) ((GameObject)otherObject).getLocation().getY();
	    
	    float dx = thisCenterX - otherCenterX;
	    float dy = thisCenterY - otherCenterY;
	    
	    float distBetweenCentersSqr = (dx * dx + dy * dy);
	    
	    int thisRadius= this.getSize() / 2;
	    int otherRadius= ((GameObject)otherObject).getSize() / 2;
	    
	    int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
	    
	    if (distBetweenCentersSqr <= radiiSqr) { result = true ; }
	    
	    return result;
	}

	
	@Override
	public void handleCollision (GameObject otherObject) {

	}
	
	@Override
	public void setLocation(float x, float y) {
	    super.setLocation(x, y); // Update global coordinates
	    localX = 0; // Set local coordinates to the origin (0, 0)
	    localY = 0;
	}
	
	public void setLife(int x)
	{
		life = x;
	}
	public int getLife() {
		return life;
	}
	public boolean isMaxDamageLevel() {
		if (this.getDamageLevel() == this.getMaxDamage())
		{
			return true;
		}
		return false;
	}
	
	public void increaseDamageLevel() {
		int temp = this.getDamageLevel() + 1;
		this.setDamageLevel(temp);
	}
	
	public void decreaseSpeed() {
		int currSpeed = getSpeed();
		if (currSpeed > 0)
		{
			this.setSpeed(--currSpeed);
		}
		else {

		}
	}
	
	public void move(int elapsedTime, int worldWidth, int worldHeight) {
	    // Calculate the new location
	    float angle = (float) Math.toRadians(90 - getHeading());
	    float deltaX = (float) (Math.cos(angle) * getSpeed() * ((float)elapsedTime / 1000));
	    float deltaY = (float) (Math.sin(angle) * getSpeed() * ((float)elapsedTime / 1000));
	    float newX = (float) (getX() + deltaX);
	    float newY = (float) (getY() + deltaY);

	    // Check if the new location is out of bounds and adjust it if necessary
	    if (newX < 0) {
	        newX = 0;
	    } else if (newX > worldWidth) {
	        newX = worldWidth;
	    }

	    if (newY < 0) {
	        newY = 0;
	    } else if (newY > worldHeight) {
	        newY = worldHeight;
	    }

	    // Set the new location
	    setLocation(newX, newY);
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = pPtrRelPrnt.getX(); // pointer location relative to
		int py = pPtrRelPrnt.getY(); 
	    int width = getSize();
	    int height = getSize();
	    int xLoc = pCmpRelPrnt.getX()+ height;// shape location relative
	    int yLoc = pCmpRelPrnt.getY()+ width;
	    
	    if ( (px >= xLoc) && (px <= xLoc+width)
	    		&& (py >= yLoc) && (py <= yLoc+height) )
	    		return true; else return false;
	}
}