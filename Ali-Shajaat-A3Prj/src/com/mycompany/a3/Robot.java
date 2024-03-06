package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Robot extends MoveableObject implements ISteerable, IDrawable, ICollider
{ 
	private int energyLevel;
	private int energyConsumptionRate = 2;
	private int maximumSpeed = 40;
	private int steeringHeading = 5;
	private int damageLevel = 0;
	private int lastBaseReached = 1;
	private boolean isDead =  false;	
	private boolean collision;
	 
	public Robot(double x, double y,int speed, int heading) 
	{
		super(x, y);
		setSpeed(speed);
	    setHeading(heading);
	   //this.setSize(50);
	    setColor(255, 0, 0);
	     this.energyLevel = 20;
	     this.damageLevel = 0;
	     this.isDead = false;
	}
	
	public Robot() 
	{
        this(50,50,0,0);
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
	
	public void steeringHeading (double steeringChange)// Updating the robot direction
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
		this.setLocation(512.0,384.0);
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

	public double getX()
	{
	    return getLocation().getX();
	}


	public double getY()
	{
	    return getLocation().getY();
	}


	public void collisionWithRobot() 
	{
	    collisionOfRobots();
	}


	public void collisionWithNpr() {
	    collisionOfRobots();
	}
	


	@Override
	public void draw(Graphics g, Graphics n, Point pCmpRelPrnt) 
	{
	        int x = (int) (getX() + pCmpRelPrnt.getX());
	        int y = (int) (getY() + pCmpRelPrnt.getY());
	        int[] xPoints = { x, (x - 40), (x + 40), x };
			
			int[] yPoints = { (y + 50), (y - 50), (y - 50), (y + 50) };
			
			int nPoints = 4;
	        
	        g.setColor(this.getColor());
	        g.setColor(ColorUtil.rgb(0, 0, 255));
	        
	        g.drawPolygon(xPoints, yPoints, nPoints);
			g.fillPolygon(xPoints, yPoints, nPoints);
			
			n.setColor(ColorUtil.BLACK);
	        n.drawString("R", x-10, y-20);
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
		if ((otherObject instanceof NonPlayerRobot || otherObject instanceof Drone) || (otherObject instanceof Bases ))
		{
			collision(otherObject);
			((Robot) otherObject).collision(otherObject);
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