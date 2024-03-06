package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
import java.util.Random;


public class EnergyStation extends FixedObject implements IDrawable, ICollider, ISelectable
{
	private int capacity;
	private Random rand = new Random();
	private boolean isHighlighted;
	//private GameWorld gw;
	
	public EnergyStation(int capacity,GameWorld gw)
	{
		
		capacity = rand.nextInt((50) + 10);
		this.setCapacity(capacity);
	}
	
	public void setCapacity(int capacity)//sets capacity
	{
		this.capacity = capacity;
	}
	
	public void reduceCapacity()
	{
		this.capacity = 0;
	}
	
	
	public int getCapacity()
	{
		
		return capacity;
		
	}
	
	public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }
	
	public float getX()
	{
	    return (float) getLocationX();
	}


	public float getY()
	{
	    return (float) getLocationY();
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = " Capacity = " + capacity;
		String retval = "EnergyStation: " + parentDesc + myDesc;
		
		return retval;
		
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
	    int x = (int) (getX() + pCmpRelPrnt.getX());
	    int y = (int) (getY() + pCmpRelPrnt.getY());
	    Transform saveAT = g.getTransform();
	    g.translate(Math.round(getMyTranslation().getTranslateX()), Math.round(getMyTranslation().getTranslateY()));
	    g.transform(getMyRotation());
	    g.transform(getMyScale());
	    g.translate(-Math.round(getMyTranslation().getTranslateX()), -Math.round(getMyTranslation().getTranslateY()));
	    if(super.isSelected()) {
	    	g.setColor(ColorUtil.rgb(0,255,0));
	    }
	    else {
	    	g.setColor(ColorUtil.rgb(0,255,0));
		    g.fillArc(x, y,100,100, 0, 360);
		   
	    }
	    
	    g.setColor(ColorUtil.BLACK);
	    g.drawString(String.valueOf(capacity), x + 30, y + 30);
	    g.setTransform(saveAT);
	}


	public int getEnergyLevel() {
		// TODO Auto-generated method stub
		return capacity;
	}
	
	@Override
	public boolean collidesWith(GameObject otherObject) {
	    boolean result = false;
	    
	    double thisCenterX = this.getLocation().getX();
	    double thisCenterY = this.getLocation().getY();
	    
	    if (otherObject == null || otherObject.getLocation() == null) {
	        return false;
	    }

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
	  public void handleCollision(GameObject object) {
		if (this instanceof EnergyStation && object instanceof Robot)  {
			if(this.getCapacity() !=0) {
				int temp = this.getCapacity();
				((Robot)object).setEnergyLevel(temp+((Robot)object).getEnergyLevel());
				this.setCapacity(0);
				this.setColor(255,240,240);
			}
		}
	  }
	
	@Override
    public void setSelected(boolean yesNo) {
        this.isSelected = yesNo;
    }

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
	    // Get the center of this circle
	    double centerX = this.getLocation().getX();
	    double centerY = this.getLocation().getY();
	    
	    // Get the radius of this circle
	    int radius = this.getSize() / 2; // if size is diameter
	    
	    // Calculate the distance from the center of this circle to the point
	    double dx = pPtrRelPrnt.getX() - centerX;
	    double dy = pPtrRelPrnt.getY() - centerY;
	    double distance = Math.sqrt(dx * dx + dy * dy);
	    
	    // Return true if the distance is less than or equal to the radius
	    return distance <= radius;
	}

	@Override
	public boolean collided(GameObject obj) {
		int L1 = obj.iShapeX; int L2 = iShapeX;
		int R1 = obj.iShapeX + obj.width;
		int R2 = iShapeX + width;
		int T1 = obj.iShapeY; int T2 = iShapeY;
		int B1 = obj.iShapeY + obj.height;
		int B2 = iShapeY + height;
		if ( (R1 < L2) || (R2 < L1) || (T2 > B1) || (T1 > B2) )
		return false;
		else
		return true;
	}


}