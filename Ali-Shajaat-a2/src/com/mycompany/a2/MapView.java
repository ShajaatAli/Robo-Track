package com.mycompany.a2;

import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.plaf.Border;


public class MapView extends Container implements Observer 
{

	public MapView()
	{
		this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255, 0, 0)));	
	}
	
	public void update (Observable o, Object arg) 
	{
		GameWorld gameWorld = (GameWorld) o;
        System.out.println("Map view updated:");
        gameWorld.printGameObjects();
		
	}

}