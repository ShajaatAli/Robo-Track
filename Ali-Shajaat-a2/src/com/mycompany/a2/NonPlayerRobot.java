package com.mycompany.a2;
import java.util.List;

public class NonPlayerRobot extends Robot 
{
	private int energyLevel;
	private int damageLevel;
	private boolean isDead;
	private IStrategy strategy;
    private List<IStrategy> availableStrategies;
	
    public NonPlayerRobot()
    {
        this(0, 0, 0, 0); // Set default values for x, y, speed, and heading
    }
    
	public NonPlayerRobot(double x, double y, int speed, int heading) 
	{
		super(x, y);
        setSpeed(speed);
        setHeading(heading);
        this.setSize(50);
        this.setColor(255, 0, 0);

        this.energyLevel = 100;
        this.damageLevel = 0;
        this.isDead = false;
    }
	
	public void collisionWithRobot() 
	{
        super.setDamageLevel(super.getDamageLevel() + 15);
        super.setColor(255, 69, 0);
        super.updateRobotsSpeed();
    }
	
	public void setStrategy(IStrategy strategy) 
	{
        this.strategy = strategy;
    }

    public IStrategy getStrategy() 
    {
        return strategy;
    }

    public List<IStrategy> getAvailableStrategies() 
    {
        return availableStrategies;
    }

	public String toString()
	{
		String parentDesc = super.toString();
		return "NonPlayer" + parentDesc;		
		
	}

}
