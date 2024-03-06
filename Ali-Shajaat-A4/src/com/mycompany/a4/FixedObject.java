package com.mycompany.a4;

import com.codename1.ui.geom.Point;

public abstract class FixedObject extends GameObject implements ISelectable {
    private static int objLoc;
    private boolean selected;

    public int getObjLoc() {
        objLoc++;
        return objLoc;
    }

    public FixedObject(float x, float y) {
        super();
        setLocation(x, y);
    }

    public FixedObject() {
        super();
        setLocation(0, 0);
    }
    
    //@Override
	public void setLocation(float X, float Y) {
		if(selected) 
			super.setLocation(X, Y);
		}
	public void setSelected(boolean y) {
		selected = y;
	}
	public boolean isSelected() {
		return selected;
	}
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int radius = super.getSize() / 2;
		int left = (int)Math.round(super.getLocationX() - radius + pCmpRelPrnt.getX());
		int right = left + super.getSize();
		int top = (int)Math.round(super.getLocationY() - radius + pCmpRelPrnt.getY());
		int bottom = top + super.getSize();
		boolean checkedLR = pPtrRelPrnt.getX() > left && pPtrRelPrnt.getX() < right;
		boolean checkedTB = pPtrRelPrnt.getY() > top && pPtrRelPrnt.getY() < bottom;
		return checkedLR && checkedTB;
	}

}
