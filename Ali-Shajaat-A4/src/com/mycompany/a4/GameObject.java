package com.mycompany.a4;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import java.util.Random;
import com.codename1.ui.Transform;

public abstract class GameObject implements ICollider
{
	public Random rand = new Random();
    private Transform myTranslate;
    private Transform myRotate;
    private Transform myScale;
    private int size = 0;
    protected boolean isSelected;
    private int rgb = 0;
    protected GameWorld gw;
    protected int  maxX = 1024;
	protected int minX = 10;
	protected int maxY = 768;
	protected int minY = 10;
	private Point2D location;
	public int iShapeX;
	public int iShapeY;
	public int width;
	public int height;

	public GameObject() {
	    float x = (float)(new Random().nextInt(maxX - minX) + minX); 
	    float y = (float)(new Random().nextInt(maxY - minY) + minY); 
	    this.location  = new Point2D(x, y);

	    // initialize transformations
	    myTranslate = Transform.makeTranslation((float)x, (float)y, 0);
	    myRotate = Transform.makeIdentity();
	    myScale = Transform.makeIdentity();
	}

    /*public GameObject(float x, float y)
    {
        location = new Point2D(x, y);
    }*/
    
    public void setX(float x) {
		location.setX(x);
	}
    public float getX() {
		return (float) location.getX();
	}
	public void setY(float y) {
		location.setY(y);
	}
	public float getY() {
		return (float) location.getY();
	}
    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Point2D getLocation() {
        return new Point2D(myTranslate.getTranslateX(), myTranslate.getTranslateY());
    }

    public float getLocationX() {
        return myTranslate.getTranslateX();
    }

    public float getLocationY() {
        return myTranslate.getTranslateY();
    }

    public void setLocation(float x, float y) {
        myTranslate.setTranslation(x, y, 0);
    }

    public void setLocation(Point2D newLocation) {
        myTranslate.setTranslation((float)newLocation.getX(), (float)newLocation.getY());
    } 
    
    public void setLocation(Point point) {
        myTranslate.setTranslation((float)point.getX(), (float)point.getY(), 0);
    } 


    public int getColor() {
        return rgb;
    }

    public void setColor(int R, int G, int B) {
        rgb = ColorUtil.rgb(R, G, B);
    }

    @SuppressWarnings("deprecation")
	public void draw(Graphics g, Point parentOrigin) 
    {
        Transform gTransform = g.getTransform();

        // apply transformations
        g.getTransform().concatenate(myTranslate);
        g.getTransform().concatenate(myRotate);
        g.getTransform().concatenate(myScale);
        g.setTransform(gTransform);
    }

    

    public String toString() {
        String retval = "Location = " + ((getLocationX() * 100) / 100) + ", " + ((getLocationY() * 100) / 100)
                + " color = " + "[" + ColorUtil.red(rgb) + " , " + ColorUtil.green(rgb) + " , " + ColorUtil.blue(rgb)
                + "]" + " size = " + this.getSize();
        return retval;
    }

    
 // update transformations
    public void translate(float tx, float ty) {
        myTranslate.translate(tx, ty, 0);
    }

    public void rotate(float degrees) {
        myRotate.rotate((float)Math.toRadians(degrees), myTranslate.getTranslateX(), myTranslate.getTranslateY());
    }

    public void scale(float sx, float sy) {
        myScale.scale(sx, sy, 1);
    }
    
    public Transform getMyTranslation() {
        return myTranslate;
    }

    public Transform getMyRotation() {
        return myRotate;
    }

    public Transform getMyScale() {
        return myScale;
    }

    public boolean isCollision(GameObject other) {
        float x1 = this.getX();
        float y1 = this.getY();
        float x2 = other.getX();
        float y2 = other.getY();

        float distance = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        float collisionThreshold = (this.getSize() + other.getSize()) / 2.0f;

        return distance <= collisionThreshold;
    }

    public GameWorld getGW() { return gw; }

    @Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = false;
		float thisCenterX = this.getLocationX() + (otherObject.size/2);
		float thisCenterY = this.getLocationY()+ (otherObject.size/2);
		
		float otherCenterX = otherObject.getLocationX();
		float otherCenterY = otherObject.getLocationY();
		
		float dx = thisCenterX - otherCenterX;
		float dy = thisCenterY - otherCenterY;
		

		float distBetweenCentersSqr = (dx * dx + dy * dy);
		int thisRadius= this.getSize() / 2;
		int otherRadius= otherObject.getSize() / 2;
		int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) { 
			result = true;
		}
			return result;
		
	}
    
    public abstract boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
    
}
