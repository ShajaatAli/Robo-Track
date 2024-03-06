package com.mycompany.a2;

public class FixedObject extends GameObject
{
	private static int objLoc;
	
	public int getObjLoc() 
	{
		objLoc++;
		return objLoc;
	}
	public FixedObject(double x, double y) 
    {
        super(x, y);
    }
	
	public FixedObject()
    {
        super(0, 0);
    }
	
}