package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class Bases extends FixedObject implements IDrawable, ICollider, ISelectable
{
	private int seqNum;

	public Bases(int seqNum, float x, float y) {
	    this.seqNum = seqNum;
	    setLocation(x, y);
	}


	public int getSeqNumb()
	{
		return seqNum;
	}
	
	public float getX()
	{
	    return (float) getLocation().getX();
	}


	public float getY()
	{
	    return (float) getLocation().getY();
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = " Seq num = " + seqNum;
		return "Base: " + parentDesc + myDesc;
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
	    g.setColor(this.getColor());
	    
	    if(super.isSelected()) {
	    	g.setColor(ColorUtil.rgb(0,255,0));
	    }
	    else {
	    	g.setColor(ColorUtil.rgb(70, 50, 200));
	 	    g.drawRect(x, y, 70, 70);
	 	    g.fillRect(x, y, 70, 70);
	    }
	   

	    g.setColor(ColorUtil.BLACK);
	    g.drawString(String.valueOf(seqNum), x + 30, y + 30);

	    g.setTransform(saveAT);
	}

	
	@Override
	  public void handleCollision(GameObject obj) {
		  if (this instanceof Bases && obj instanceof Robot || this instanceof Bases && obj instanceof NonPlayerRobot ) {
			 
			  if (((Robot) obj).getLastBaseReached() +1 == this.getSeqNumb() && this.getSeqNumb() != 4) {
					System.out.println("The Robot has reached the next base");
					((Robot) obj).setLastBaseReached(this.getSeqNumb());
				}
				else if (this.getSeqNumb() == 4 && obj instanceof Robot) {
					if (((Robot)obj).getLastBaseReached()+1 == 4) {
					System.out.println("Game Over, you win");
					gw.exit(0);
					}
				}
				else if (this.getSeqNumb()== 9 && obj instanceof NonPlayerRobot) {
					if (((NonPlayerRobot)obj).getLastBaseReached()+1 == 4) {
					System.out.println("Game Over, you lose");
					gw.exit(1);
					}
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
		int px = pPtrRelPrnt.getX(); 
		int py = pPtrRelPrnt.getY(); 

		int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();

		int size = this.getSize();

		if ((px >= xLoc) && (px <= xLoc + size) && (py >= yLoc) && (py <= yLoc + size)) {
			return true; 
		} else {
			return false;
		}
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
