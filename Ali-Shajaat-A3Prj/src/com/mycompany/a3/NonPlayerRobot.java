package com.mycompany.a3;
import java.util.List;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
//red filled triangles
public class NonPlayerRobot extends Robot 
{
	private int energyLevel;
	private int damageLevel;
	private boolean isDead;
	private IStrategy strategy;
    private List<IStrategy> availableStrategies;
    private boolean collision;
	
    public NonPlayerRobot()
    {
        this(0, 0, 0, 0); // Set default values for x, y, speed, and heading
    }
    
	public NonPlayerRobot(double x, double y, int speed, int heading) 
	{
		super(x, y, speed, heading);
        //setSpeed(speed);
        //setHeading(heading);
        this.setSize(50);
        setColor(255, 0, 0);

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
	
    @Override
	public void draw(Graphics g, Graphics n, Point pCmpRelPrnt)
	{
    		int x = (int) (getX() + pCmpRelPrnt.getX());
	        int y = (int) (getY() + pCmpRelPrnt.getY());
	        int[] xPoints = { x, (x - 20), (x + 20), x };
			int[] yPoints = { (y + 40), (y - 40), (y - 40), (y + 40) };
			int nPoints = 4;     
			g.setColor(this.getColor());
		    g.setColor(ColorUtil.rgb(255, 0, 0));
	        g.drawPolygon(xPoints, yPoints, nPoints);
	        
	        n.setColor(ColorUtil.BLACK);
	        n.drawString("N", x-8, y-30);
		
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
		if ((otherObject instanceof Robot || otherObject instanceof Drone) || (otherObject instanceof Bases ))
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
