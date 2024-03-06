package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.plaf.Border;


public class MapView extends Container implements Observer {

    private GameWorld gw; 

    public MapView(GameWorld gw) 
    {
        this.gw = gw; 
        this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255, 0, 0)));
    }

    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        Point parentOrigin = new Point(getX(), getY());
        IIterator iterator = gw.getGameObjects().getIterator(); 
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof IDrawable) {
                ((IDrawable) obj).draw(g, g, parentOrigin);
            }
        }
    }

    public void update(Observable o, Object arg) 
    {
        repaint();
    }
    
    @Override
    public void pointerPressed(int x, int y) 
    {
        x = x - getParent().getAbsoluteX();
        y = y - getParent().getAbsoluteY();
        Point pPtrRelPrnt = new Point(x, y);
        IIterator iterator = gw.getGameObjects().getIterator();
        while (iterator.hasNext()) 
        {
        	GameObject obj = iterator.getNext();
        	if (obj instanceof ISelectable)
        	{
                ISelectable selectableObj = (ISelectable) obj;
                if (selectableObj.contains(pPtrRelPrnt, pPtrRelPrnt)) 
                {
                    selectableObj.setSelected(true);
                } else {
                    selectableObj.setSelected(false);
                }
        	}
         }
        repaint();
    }


}
