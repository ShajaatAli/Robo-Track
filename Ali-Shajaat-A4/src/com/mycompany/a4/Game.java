package com.mycompany.a4;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Point;
import com.codename1.ui.Container;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

public class Game extends Form implements Runnable
{
	private GameWorld gw;
	private MapView mv; // new in A2
	private ScoreView sv; // new in A2
	private UITimer timer;
	private Buttons accelerate;
    private Buttons left;
    private Buttons brake;
    private Buttons right;
    private Buttons changeStrategies;
    private Buttons Position;
    private Buttons Play;
    private boolean isPaused = false;
    private final int time = 20;

	
	public Game() 
		{
			//play();
			gw = new GameWorld();
			mv = new MapView(gw); 
			sv = new ScoreView(); 
			
			gw.addObserver(mv);
			gw.addObserver(sv);
			
			setUp();
			this.show();
			
			gw.init(); // initialize world
			timer = new UITimer(this);
	        timer.schedule(time, true, this);

	        
	        GameWorld.setGameWidth(mv.getWidth());
			GameWorld.setGameHeight(mv.getHeight());
			
			mv.setMapViewOrigin(new Point(mv.getX() , mv.getY()));
			MapView.setMapViewWidth(mv.getWidth());
			MapView.setMapViewHeight(mv.getHeight());
		}
	
	private void setUp() 
	{

		CustomCommand menu = new CustomCommand(gw, this);
		Toolbar toolbar = new Toolbar();
		this.setToolbar(toolbar);
		toolbar.setTitle("Robo Track Game");
		
		Command exitC = menu.Exit();
		toolbar.addCommandToSideMenu(exitC);
		
		Command aboutC = menu.About();
		toolbar.addCommandToSideMenu(aboutC);
		
		CheckBox soundCheck = new CheckBox();
		toolbar.addComponentToSideMenu(soundCheck);
		Command soundC= menu.Sound();
		soundCheck.setCommand(soundC);
		
		Container westContainer = new Container();
		westContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLACK));
		westContainer.setLayout(new BoxLayout(2));
		westContainer.setWidth(100);
		
		accelerate = new Buttons ("Accelerate");
		westContainer.add(accelerate);
		accelerate.getAllStyles().setMarginTop(400);
		Command accelerateC= menu.Accelerate();
		addKeyListener('a', accelerateC);
		accelerate.setCommand(accelerateC);
		toolbar.addCommandToSideMenu(accelerateC);
		
		left = new Buttons ("Left");
		westContainer.add(left);
		Command leftC= menu.Left();
		addKeyListener('l', leftC);
		left.setCommand(leftC);
		
		
		changeStrategies = new Buttons ("Change Strategies");
		westContainer.add(changeStrategies);
		Command changeC = menu.changeStrategies();
		changeStrategies.setCommand(changeC);
		
		// Creating East Container
		Container eastContainer = new Container();
		eastContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLACK));
		eastContainer.setLayout(new BoxLayout(2));
		
		brake = new Buttons ("Brake");
		eastContainer.add(brake);
		brake.getAllStyles().setMarginTop(400);
		brake.getAllStyles().setPaddingRight(9);
		brake.getAllStyles().setPaddingLeft(9);
		Command brakeC = menu.Brake();
		addKeyListener('b', brakeC);
		brake.setCommand(brakeC);

		
		right = new Buttons ("Right");
		eastContainer.add(right);
		right.getAllStyles().setPaddingRight(9);
		right.getAllStyles().setPaddingLeft(9);
		Command rightC= menu.Right();
		addKeyListener('r', rightC);
		right.setCommand(rightC);
		
		
		// Creating South Container
		Container southContainer = new Container();
		southContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLACK));
		southContainer.setLayout(new FlowLayout(Component.CENTER));
		
		
		/*Buttons nprCollision = new Buttons ("Collide With NPR");
		southContainer.add(nprCollision);
		nprCollision.getAllStyles().setMarginLeft(10);
		nprCollision.getAllStyles().setMarginRight(15);
		Command nprC = menu.nprCollision();
		nprCollision.setCommand(nprC);
		
		
		Buttons baseCollision = new Buttons ("Collide With Base");
		southContainer.add(baseCollision);
		baseCollision.getAllStyles().setMarginRight(15);
		Command baseC = menu.baseCollision();
		baseCollision.setCommand(baseC);
		
		
		Buttons eSCollision = new Buttons ("Collide With Energy Station");
		southContainer.add(eSCollision);
		eSCollision.getAllStyles().setMarginRight(15);
		Command ecs = menu.EnergyStationCollision();
		addKeyListener('e', ecs);
		eSCollision.setCommand(ecs);
		
		
		Buttons droneCollision = new Buttons ("Collide With Drone");
		southContainer.add(droneCollision);
		droneCollision.getAllStyles().setMarginRight(15);
		Command droneC = menu.DroneCollision();
		addKeyListener('g',droneC);
		droneCollision.setCommand(droneC);
		
		
		Buttons tick = new Buttons ("Tick");
		southContainer.add(tick);
		tick.getAllStyles().setPaddingRight(7);
		tick.getAllStyles().setPaddingLeft(7);
		Command ticked = menu.Ticked();
		addKeyListener('a', ticked);
		tick.setCommand(ticked);*/
		Position = new Buttons ("Position");
		southContainer.add(Position);
		Position.getAllStyles().setPaddingRight(7);
		Position.getAllStyles().setPaddingLeft(7);
		Position.getAllStyles().setMarginLeft(10);
		Position.getAllStyles().setMarginRight(15);
		Command position = menu.position();
		Position.setCommand(position);
		
		Play = new Buttons ("Play/Pause");
		southContainer.add(Play);
		Play.getAllStyles().setPaddingRight(7);
		Play.getAllStyles().setPaddingLeft(7);
		Command play = menu.togglePausePlay();
		Play.setCommand(play);
		
		
		Command helpButton = menu.Help();
		toolbar.addCommandToRightBar(helpButton);
		
		
		
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST, westContainer);
		this.add(BorderLayout.EAST, eastContainer);
		this.add(BorderLayout.SOUTH, southContainer);
		this.add(BorderLayout.NORTH, sv);
		this.add(BorderLayout.CENTER, mv);

	}
	
	/*private void play() //code from A1 not being used in A2
	{
		Label myLabel=new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField=new TextField();
		this.addComponent(myTextField);
		this.show();
		myTextField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) 
			{
				String sCommand = myTextField.getText().toString();
				myTextField.clear();
				
				if(!sCommand.equals(null) && sCommand.length() > 0 && !gw.isEndGame()) //&& !endGame
				{
					switch (sCommand.charAt(0)) 
					{
						case 'y':
							System.exit(0);
					        break;
	
					    case 'n':
					        System.out.println("The Game Has Not been exited");
					        break;
					        
						case 'a'://accelerates
							gw.accelerate();
							//increase the speed of the PS by a small amount
							break;
							
						case 'b'://breaks
							gw.brake();
							//increase the speed of the PS by a small amount
							break;
						
						case 'l'://changes direction left
							
							gw.left();
							break;
							
						case 'r'://changes directions right
							
							gw.right();
							break;
							
						case 't'://tells game world that time has ticked
							
							//gw.ticked();
							//game clock has ticked
							//updated game state variables
							break;
							
						case 'd'://displays text
							
							gw.display();
							break;
							
						case 'm'://displays map info
							
							//gw.map();
							break;
							
						case 'x'://terminate program by using System.exit(0);
							
							System.out.println("Are you sure you want to exit? (y/n)");
							//gw.endsGame('x');
							break;
							
						default:
							System.out.println("\nInput is invalid\n");
							break;
						
					}//switch
					
				}
				
				if(gw.isEndGame())
				{
					System.out.println("Exiting Game Now!");
					System.exit(0);
				}
			} //actionPerformed
		 } //new ActionListener()
		); //addActionListener
	} //play*/

	@Override
	public void run() {
		if (!isPaused) { // check if game is paused before ticking
			gw.ticked(time);
		}
	}
	
	public void pause() 
	{
	    isPaused = !isPaused;
	    if (isPaused) {
	    	System.out.println("The Game is Paused");
	        timer.cancel(); 
	        accelerate.setEnabled(false);
	        left.setEnabled(false);
	        brake.setEnabled(false);
	        right.setEnabled(false);
	        changeStrategies.setEnabled(false);
	        Position.setEnabled(true);
	    } else {
	    	System.out.println("The Game has resumed");
	        timer.schedule(10, true, this); 
	        accelerate.setEnabled(true);
	        left.setEnabled(true);
	        brake.setEnabled(true);
	        right.setEnabled(true);
	        changeStrategies.setEnabled(true);
	        Position.setEnabled(false);
	    }
	}


}