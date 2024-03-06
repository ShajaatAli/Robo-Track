package com.mycompany.a2;

public class Robot extends MoveableObject implements ISteerable
{ 
	private int energyLevel = 20;
	private int energyConsumptionRate = 2;
	private int maximumSpeed = 40;
	private int steeringHeading = 5;
	private int damageLevel = 0;
	private int lastBaseReached = 1;
	private boolean isDead =  false;
	
	
	 
	public Robot(double x, double y) 
	{
		super(x, y);
		
	}
	
	public Robot() 
	{
        super(0, 0); 
        this.setSpeed(0);
		this.setSize(50);
		this.setColor(255,0,0);
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
	public void reset()//reset robot after losing a life
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
	public void steeringHeading(int HeadingChange) {
		// TODO Auto-generated method stub
		
	}


}