package com.mycompany.a2;

public class Bases extends FixedObject 
{
	private int seqNum;
	
	public Bases()
	{
		this(0,0,0);
	}
	public Bases(int seqNum, double x, double y)
	{
	    super(x, y);
	    setColor(0, 0, 255);
	    this.setSize(10);
	    this.seqNum = seqNum;
	}

	public int getSeqNumb()
	{
		return seqNum;
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = " Seq num = " + seqNum;
		return "Base: " + parentDesc + myDesc;
	}

}
