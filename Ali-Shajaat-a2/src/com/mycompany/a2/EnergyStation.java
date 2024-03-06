package com.mycompany.a2;

public class EnergyStation extends FixedObject 
{
	private int capacity;
	
	public EnergyStation(int size)
	{
		this.setSize(size);
		setColor(0,255,0);//set the station green
		setLocation(rand.nextInt(1000),rand.nextInt(700));//sets a random location
		this.setCapacity(size);
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
	
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = " Capacity = " + capacity;
		String retval = "EnergyStation: " + parentDesc + myDesc;
		
		return retval;
		
	}
}