package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import java.util.Random;

public class GameObject implements ISelectable
{
    public Random rand = new Random();
    private Point2D location;
    private int rgb = 0;
    private int size = 0;
    private boolean isSelected;

    public GameObject() 
    {
    	double x = 1025.0 * rand.nextDouble();
    	double y = 769.0 * rand.nextDouble();

    	// Ensure the coordinates stay within the desired bounds
    	x = Math.min(x, 1024);
    	y = Math.min(y, 768);

    	// Create a new location with the constrained coordinates
    	location = new Point2D(x, y);

    }

    public GameObject(double x, double y)
    {
        location = new Point2D(x, y);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Point2D getLocation() {
        return location;
    }

    public double getLocationX() {
        return location.getX();
    }

    public double getLocationY() {
        return location.getY();
    }

    public void setLocation(double x, double y) {
        location.setX(x);
        location.setY(y);
    }

    public void setLocation(Point2D newLoc) {
        location.setX(newLoc.getX());
        location.setY(newLoc.getY());
    }

    public int getColor() {
        return rgb;
    }

    public void setColor(int R, int G, int B) {
        rgb = ColorUtil.rgb(R, G, B);
    }

    public void draw(Graphics g, Point parentOrigin) 
    {

    }

    public String toString() {
        String retval = "Location = " + ((location.getX() * 100) / 100) + ", " + ((location.getY() * 100) / 100)
                + " color = " + "[" + ColorUtil.red(rgb) + " , " + ColorUtil.green(rgb) + " , " + ColorUtil.blue(rgb)
                + "]" + " size = " + this.getSize();
        return retval;
    }

	@Override
	public void setSelected(boolean b) 
	{
        isSelected = b;
    }

	@Override
	public boolean isSelected()
	{
        return isSelected;
    }

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) 
	{
		return isSelected;
	}

}
