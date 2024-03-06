package com.mycompany.a2;

public class Drone extends MoveableObject
{
	public Drone(int speed, int size, int heading)
	{
		super(heading);
		super.setSize(size);
		super.setColor(255,0,255);
		this.speed = speed;
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
	
	@Override
	public void Move()
	{
		super.Move();
	}

	
}