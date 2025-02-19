package com.mycompany.a4;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public interface ISelectable 
{

	public void setSelected(boolean b);

	public boolean isSelected();

	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);

	public void draw(Graphics g, Point pCmpRelPrnt);
	
	public boolean collided(GameObject obj);
}