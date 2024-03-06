package com.mycompany.a4;
import java.util.List;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
//red filled triangles
public class NonPlayerRobot extends Robot 
{
	private int energyLevel;
	private int damageLevel;
	private boolean isDead;
	private IStrategy strategy;
    private boolean collision;
    private boolean flag = false;
	
    public NonPlayerRobot()
    {
        this(0, 0, 0, 0); // Set default values for x, y, speed, and heading
    }
    
	public NonPlayerRobot(float x, float y, int speed, int heading) 
	{
		super(x, y, speed, heading);
        this.setSize(50);
        //setColor(255, 0, 0);
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

    public boolean getStrategy() 
    {
    	if (strategy instanceof Strategy1)
			return true;
		else
			return false;
    }

    public void invokeStrategy() 
	{
		strategy.apply();
	}

	public String toString()
	{
		String parentDesc = super.toString();
		return "NonPlayer" + parentDesc;		
		
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
	    int x = (int) (getX() + pCmpRelPrnt.getX());
	    int y = (int) (getY() + pCmpRelPrnt.getY());
	    int[] xPoints = { x, (x - 20), (x + 20), x };
	    int[] yPoints = { (y + 40), (y - 40), (y - 40), (y + 40) };
	    int nPoints = 4;
	    Transform saveAT = g.getTransform();
	    g.translate(Math.round(getMyTranslation().getTranslateX()), Math.round(getMyTranslation().getTranslateY()));
	    g.transform(getMyRotation());
	    g.transform(getMyScale());
	    g.translate(-Math.round(getMyTranslation().getTranslateX()), -Math.round(getMyTranslation().getTranslateY()));
	    if (damageLevel == 0) {
	        g.setColor(this.getColor());
	    } else if (damageLevel == 1) {
	        g.setColor(ColorUtil.rgb(255, 200, 200)); 
	    } else if (damageLevel == 2) {
	        g.setColor(ColorUtil.rgb(255, 150, 150)); 
	    } else {
	        g.setColor(ColorUtil.rgb(255, 100, 100));
	    }
	    g.setColor(ColorUtil.rgb(222, 49, 99));
	    g.drawPolygon(xPoints, yPoints, nPoints);
	    g.fillPolygon(xPoints, yPoints, nPoints);
	    g.setColor(ColorUtil.BLACK);
	    g.drawString("N", x - 8, y - 30);
	    g.setTransform(saveAT);
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
	  public void handleCollision(GameObject object) {
			  if (this instanceof NonPlayerRobot && object instanceof Robot) {
					if (((Robot)object).isMaxDamageLevel() == true) {
						System.out.println("Your Robot is dead. ");
						if (((Robot)object).getLife() != 0){
							super.getGW().exit(3);
							super.getGW().getLoseLifeSound().play();;
							int lastbase = ((Robot) object).getLastBaseReached();
							IIterator itr2 = super.getGW().getGameObjects().getIterator();
							while(itr2.hasNext()){
								GameObject objs = itr2.getNext();
								if(objs instanceof Bases) {
									if(lastbase == ((Bases)objs).getSeqNumb()) {
										((Robot) object).reset();
									}
								}
							}
							
						}
						else {
							System.out.println("Game is over!!!");
							//System.out.println("Test npr");
							super.getGW().exit(2);
						}	
					}
					else {
						((Robot)object).increaseDamageLevel();
						((NonPlayerRobot)this).increaseDamageLevel();
						((Robot)object).checkDamageLevel();
						((NonPlayerRobot)this).checkDamageLevel();
					}
				}
				else if (this instanceof NonPlayerRobot && object instanceof NonPlayerRobot) {
					if (((NonPlayerRobot)object).isMaxDamageLevel() == true || ((NonPlayerRobot)this).isMaxDamageLevel() == true) {

						if (((NonPlayerRobot)object).isMaxDamageLevel() == true) {
						}
						else if (((NonPlayerRobot)this).isMaxDamageLevel() == true) {
						}
					}
					
					else {
						((NonPlayerRobot)object).increaseDamageLevel();
						((NonPlayerRobot)this).increaseDamageLevel();
					}
				}
				else if (this instanceof NonPlayerRobot && object instanceof Drone) {
					((NonPlayerRobot)this).increaseDamageLevel();
				}
				
	  }
    
    @Override
    public void Move() {
        float oldX = getX();
        float oldY = getY();

        // Calculate the new location based on the current heading and speed
        float deltaX = (float) (Math.cos(Math.toRadians(getHeading())) * getSpeed());
        float deltaY = (float) (Math.sin(Math.toRadians(getHeading())) * getSpeed());
        float newX = oldX + deltaX;
        float newY = oldY + deltaY;

        int offset = 0;
        if (this instanceof Robot || this instanceof NonPlayerRobot) {
            offset = 50;
        }

        // Check for wall collisions
        if (newX + offset >= MapView.getMapViewWidth()) {
            newX = MapView.getMapViewWidth() - offset;
        } else if (newX - offset <= 0) {
            newX = offset;
        }

        if (newY + offset >= GameWorld.getGameHeight()) {
            newY = GameWorld.getGameHeight() - offset;
        } else if (newY - offset <= 0) {
            newY = offset;
        }

        // Check if the new location is within the game world boundaries
        Random rand = new Random();
        int newHeading = rand.nextInt(360); // Generate a random heading between 0 and 359 degrees

        if (newX >= minX && newX <= maxX && newY >= minY && newY <= maxY) {
            setLocation(newX, newY);
        } else {
            setHeading(newHeading);
        }
    }

    public boolean getFlag() {
		return flag;
	}
    public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
