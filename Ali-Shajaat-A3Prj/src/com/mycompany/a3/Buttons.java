package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;

public class Buttons extends Button 
{
	 public Buttons(String string)
	 {
	        super(string);
	        setStyles();
	    }

	    private void setStyles()
	    {
	        getAllStyles().setFgColor(ColorUtil.WHITE);
	        getAllStyles().setBgColor(ColorUtil.BLACK);
	        getAllStyles().setBgTransparency(255);
	        getAllStyles().setMarginBottom(15);
	        getAllStyles().setPaddingTop(3);
	        getAllStyles().setPaddingBottom(3);
	        
	    }

}
