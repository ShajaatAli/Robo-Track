package com.mycompany.a4;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class Drone extends MoveableObject implements IDrawable, ICollider
{	
	public Drone(float x, float y,int speed,  int heading)
	{
		//super(x, y, speed, size, heading);
		setSpeed(speed);
		setHeading(heading);
		setColor(255,0,255);
		this.setLocation(rand.nextInt(625)+300,rand.nextInt(468)+300);//sets a random location
	}
	
	public Drone()
	{
		this(0,0,0,0);
	}
	
	public void randomHeading()//heading of drones should be random value between 0 and 359
	{
		this.setHeading(rand.nextInt(359));
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		return "Drone: " + parentDesc;
	}
	public float getX()
	{
	    return (float) getLocationX();
	}


	public float getY()
	{
	    return (float) getLocationY();   
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
	    int x = (int) (getX() + pCmpRelPrnt.getX());
	    int y = (int) (getY() + pCmpRelPrnt.getY());
	    int[] xPoints = {x, (x - 20), (x + 20), x};
	    int[] yPoints = {(y + 30), (y - 30), (y - 30), (y + 30)};
	    int nPoints = 4;

	    Transform saveAT = g.getTransform();
	    g.translate(Math.round(getMyTranslation().getTranslateX()), Math.round(getMyTranslation().getTranslateY()));
	    g.transform(getMyRotation());
	    g.transform(getMyScale());
	    g.translate(-Math.round(getMyTranslation().getTranslateX()), -Math.round(getMyTranslation().getTranslateY()));
	    g.setColor(ColorUtil.rgb(255,0,255));
	    g.drawPolygon(xPoints, yPoints, nPoints);
	    g.fillPolygon(xPoints, yPoints, nPoints);
	    g.setColor(ColorUtil.BLACK);
	    g.drawString("D", x - 8, y - 18);
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
			if (this instanceof Drone && object instanceof Robot) {
				if (((Robot)object).isMaxDamageLevel() == true) {
					System.out.println("Your Robot is dead. ");
					if (((Robot)object).getLife() != 0){
						super.getGW().exit(3);
						super.getGW().getLoseLifeSound().play();
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
						//System.out.println("Test drone");
						super.getGW().exit(2);
					}	
				}
				else {
					((Robot)object).increaseDamageLevel();
					((Robot)object).checkDamageLevel();
				}
			}
			else if (this instanceof Drone && object instanceof NonPlayerRobot) {
				if (((NonPlayerRobot)object).isMaxDamageLevel() == true) {
					((NonPlayerRobot)object).setSpeed(0);
				}
				else {
					((NonPlayerRobot)object).increaseDamageLevel();
					((NonPlayerRobot)object).checkDamageLevel();
			
				}
			}
	
	}
	@Override
    public void Move() {
        float oldX = getX();
        float oldY = getY();

        float deltaX = (float) (Math.cos(Math.toRadians(getHeading())) * getSpeed());
        float deltaY = (float) (Math.sin(Math.toRadians(getHeading())) * getSpeed());
        float newX = oldX + deltaX;
        float newY = oldY + deltaY;

        int offset = 0;
        if (this instanceof Drone) {
            offset = 50;
        }

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