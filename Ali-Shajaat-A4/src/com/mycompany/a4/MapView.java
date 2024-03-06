package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.Transform;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;


public class MapView extends Container implements Observer {

    private GameWorld gw; 
    private Transform theVTM;
    private static final float zoomFactor = 0.05f; 
    private static final float panFactor = 5.0f; 
    private static Point mapViewOrigin;
    int startX = 0, startY = 0, endX = 0, endY = 0;
    private static int height;
	private static int width;
    private Transform currentTransform = Transform.makeIdentity();


    public MapView(GameWorld gw) 
    {
    	this.gw = gw; 
        this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255, 0, 0)));
		MapView.height = this.getHeight();
	    MapView.width = this.getWidth();
	    MapView.mapViewOrigin = new Point(0,0);
	    this.addPointerDraggedListener(new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent event) {
                float dx = event.getX() - startX;
                float dy = event.getY() - startY;

                pan(dx, dy);
                startX = event.getX();
                startY = event.getY();
            }
        });

        this.addPointerPressedListener(new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent event) {
                startX = event.getX();
                startY = event.getY();
            }
        });

        this.addPointerReleasedListener(new ActionListener<ActionEvent>() {
            public void actionPerformed(ActionEvent event) {
                startX = event.getX();
                startY = event.getY();
            }
        });
        
    }
    private void updateVTM() 
    {
    	
    	float mapViewWidth = getWidth(); //=1484
    	float mapViewHeight = getHeight();//=1243
    	//System.out.println(mapViewWidth);
    	//System.out.println(mapViewHeight);
    	float worldWidth = (mapViewWidth);  
    	float worldHeight = (mapViewHeight);
    	theVTM = Transform.makeIdentity();

    	// Translate
    	float translateX = -10.0f;
    	float translateY = -10.0f;
    	theVTM.translate(translateX, translateY);

    	// Rotate
    	float rotationAngle = (float) Math.toRadians(0);
    	float rotationCenterX = 50.0f;
    	float rotationCenterY = 50.0f;
    	theVTM.rotate(rotationAngle, rotationCenterX, rotationCenterY);

    	// Scale
    	float scaleX = 1.2f;
    	float scaleY = 1.25f;
    	theVTM.scale(scaleX, scaleY);

    	// Apply translation to center the scaled and rotated object
    	float translateCenterX = worldWidth / 60;
    	float translateCenterY = worldHeight / 80;
    	theVTM.translate(translateCenterX, translateCenterY);

        
        
    }
    
    private void pan(float dx, float dy) {
        theVTM.translate(dx / getWidth(), dy / getHeight());
        repaint();
    }
    
    @Override
    public void layoutContainer() 
    {
        super.layoutContainer();
        updateVTM();
    }

    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        g.setColor(ColorUtil.BLACK);
        g.drawRect(startX - getParent().getAbsoluteX(),
    			startY - getParent().getAbsoluteX(),
    			endX - startX, endY - startY);
        Point pCmpRelPrnt = new Point(getX(), getY());
        Transform oldTransform = g.getTransform();
        g.setTransform(theVTM);
        IIterator iterator = gw.getGameObjects().getIterator(); 
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof IDrawable) {
                ((IDrawable) obj).draw(g, pCmpRelPrnt);
            }
        }
        g.setTransform(oldTransform);
        g.setTransform(currentTransform);
    }

    public void update(Observable o, Object arg) 
    {
        this.repaint();
    }
    
    @Override
    public void pointerPressed(int x, int y) {
        x = x - getParent().getAbsoluteX();
        y = y - getParent().getAbsoluteY();
        Point pPtrRelPrnt = new Point(x, y);
        Point pCmpRelPrnt = new Point(getX(), getY());
            IIterator iterator = gw.getGameObjects().getIterator(); 
            while (iterator.hasNext()) {
                GameObject obj = iterator.getNext();
                if (obj instanceof ISelectable) {
                    ISelectable selectableObj = (ISelectable) obj;
                    if (selectableObj.contains(pPtrRelPrnt, pCmpRelPrnt)) {
                        selectableObj.setSelected(true);
                    } else {
                        selectableObj.setSelected(false);
                    }
                }
            }
            repaint();
        
    }


    public void zoomIn() {
        float scaleX = theVTM.getScaleX() * (1 - zoomFactor);
        float scaleY = theVTM.getScaleY() * (1 - zoomFactor);
        theVTM.scale(scaleX / theVTM.getScaleX(), scaleY / theVTM.getScaleY());
        repaint();
    }

    public void zoomOut() {
        float scaleX = theVTM.getScaleX() / (1 - zoomFactor);
        float scaleY = theVTM.getScaleY() / (1 - zoomFactor);
        theVTM.scale(scaleX / theVTM.getScaleX(), scaleY / theVTM.getScaleY());
        repaint();
    }

    public void panLeft() {
        float tx = theVTM.getTranslateX() + panFactor;
        theVTM.translate(tx - theVTM.getTranslateX(), 0);
        repaint();
    }

    public void panRight() {
        float tx = theVTM.getTranslateX() - panFactor;
        theVTM.translate(tx - theVTM.getTranslateX(), 0);
        repaint();
    }

    public void panUp() {
        float ty = theVTM.getTranslateY() + panFactor;
        theVTM.translate(0, ty - theVTM.getTranslateY());
        repaint();
    }

    public void panDown() {
        float ty = theVTM.getTranslateY() - panFactor;
        theVTM.translate(0, ty - theVTM.getTranslateY());
        repaint();
    }
    
    
    public static int getMapViewWidth() { return width; }
	public static int getMapViewHeight() { return height; }
	public static void setMapViewWidth(int width) { MapView.width = width; }
	public static void setMapViewHeight(int height) { MapView.height = height; }
	
	public void setMapViewOrigin(Point p) { MapView.mapViewOrigin = p; }
	public static Point getMapViewOrigin() { return mapViewOrigin; }
	
}
