package com.mycompany.a4;
import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

public class ScoreView extends Container implements Observer
{
	private Label timeValue;
	private Label livesValue;
	private Label lbrValue;
	private Label energyValue;
	private Label damageValue;
	private Label soundValue;
	
	public ScoreView() 
	{
		
		this.setLayout(new FlowLayout(Component.CENTER));
		this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLACK));
		this.setUp();

	}

	public void setUp()
	{
		Label time = new Label("Time:");
		timeValue = new Label(" ");
		time.getAllStyles().setFgColor(ColorUtil.BLACK);
		timeValue.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
		this.add(time);
		this.add(timeValue);
		
		Label lives = new Label("Lives Left:");
		livesValue = new Label(" ");
		lives.getAllStyles().setFgColor(ColorUtil.BLACK);
		livesValue.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
		this.add(lives);
		this.add(livesValue);
		
		Label lbr = new Label("Player Last Base Reached:");
		lbrValue = new Label(" ");
		lbr.getAllStyles().setFgColor(ColorUtil.BLACK);
		lbrValue.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
		this.add(lbr);
		this.add(lbrValue);
		
		Label energy = new Label("Player Energy Level:");
		energyValue = new Label(" ");
		energy.getAllStyles().setFgColor(ColorUtil.BLACK);
		energyValue.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
		this.add(energy);
		this.add(energyValue);
		
		Label damage = new Label("Player Damage Level:");
		damageValue = new Label(" ");
		damage.getAllStyles().setFgColor(ColorUtil.BLACK);
		damageValue.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
		this.add(damage);
		this.add(damageValue);
		
		Label sound = new Label("Sound:");
		soundValue = new Label(" ");
		sound.getAllStyles().setFgColor(ColorUtil.BLACK);
		soundValue.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
		this.add(sound);
		this.add(soundValue);
		
	}
	
	public ScoreView(GameWorld gw)
	{
        this();
        gw.addObserver(this); 
    }
		
	@Override
	public void update (Observable o, Object arg) 
	{
		if (o instanceof GameWorld) {
            GameWorld gw = (GameWorld) o;

            timeValue.setText(String.valueOf(gw.getClockValue()));
            livesValue.setText(String.valueOf(gw.getLivesLeft()));
            lbrValue.setText(String.valueOf(gw.getLastBaseReached()));
            energyValue.setText(String.valueOf(gw.getEnergyLevel()));
            damageValue.setText(String.valueOf(gw.getDamageLevel()));
            soundValue.setText(gw.isSoundOn() ? "ON" : "OFF");
            
            this.repaint();
        }
		
	}

}
