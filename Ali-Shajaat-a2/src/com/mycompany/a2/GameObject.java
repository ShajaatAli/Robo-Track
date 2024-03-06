package com.mycompany.a2;
import com.codename1.charts.util.ColorUtil; 
import com.codename1.ui.geom.Point2D;
import java.util.Random;

public class GameObject 
{
	public Random rand = new Random();
	private Point2D location;
	private int rgb = 0;
	private int size = 0;
	
	public GameObject() 
	{
		double x = Math.round((1025.0* rand.nextDouble() * 10.0)) / 10.0;
		double y = Math.round((769.0* rand.nextDouble() * 10.0)) / 10.0;
		
		
		//accounting for edge case of something going off screen
		if( x > 1024)
			x = 1024;
		if(y > 768)
			y = 768;
		
		location = new Point2D(x,y);
	}
	
	public GameObject(double x, double y) 
	{
	    location = new Point2D(x, y);
	}

	
	public void setSize (int size)
	{
		this.size = size;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public Point2D getLocation() 
	{
		return location;
	}
	
	public double getLocationX()
	{
		return location.getX();
	}
	
	public double getLocationY()
	{
		return location.getY();
	}
	
	public void setLocation(double x, double y) 
	{
		location.setX(x);
		location.setY(y);
	}
	
	public void setLocation(Point2D newLoc)
	{
		
		location.setX(newLoc.getX());
		location.setY(newLoc.getY());
		
	}
	
	public int getColor() 
	{
		return rgb;
	}
	
	public void setColor(int R, int G, int B) 
	{
		rgb = ColorUtil.rgb(R,G,B);
	}
	
	public String toString() 
	{
		String retval = "Location = " + ((location.getX()*10)/10) + ", " +  ((location.getY()*10)/10)
						+" color = " + "[" + ColorUtil.red(rgb) + " , "
								  		+ ColorUtil.green(rgb) + " , "
								  		+ ColorUtil.blue(rgb) + "]" + 
										" size = "  + this.getSize();
		return retval;
	}
}
