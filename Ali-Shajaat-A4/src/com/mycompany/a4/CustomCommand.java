package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.table.TableLayout;

public class CustomCommand 
{
	private GameWorld gw;
	private Game game;

	public CustomCommand(GameWorld gw, Game game) 
	{
        this.gw = gw;
        this.game = game;
    }

	public Command Accelerate() 
	{
		return new Command("Accelerate") 
		{
			public void actionPerformed(ActionEvent e) 
			{
				gw.accelerate();
			}
		};
	}

	public Command Brake() 
	{
		return new Command("Brake") 
		{
			public void actionPerformed(ActionEvent e) 
			{
				gw.brake();
			}
		};
	}

	public Command Left()
	{
		return new Command("Left") 
		{
			public void actionPerformed(ActionEvent e)
			{
				gw.left();
			}
		};
	}

	public Command Right() 
	{
		return new Command("Right") 
		{
			public void actionPerformed(ActionEvent e) 
			{
				gw.right();
			}
		};
	}
	public Command DroneCollision() 
	{
		return new Command("Collide with Drone") 
		{
			public void actionPerformed(ActionEvent e) 
			{
				gw.dronesCollision();
			}
		};
	}

	/*public Command EnergyStationCollision() 
	{
		return new Command("Collide with Energy Station") 
		{
			public void actionPerformed(ActionEvent e)
			{
				gw.energyStationsCollision();
			}
		};
	}*/

	public Command Exit() 
	{
		return new Command("Exit")
		{
			public void actionPerformed(ActionEvent e) 
			{
				Command yes = new Command("Yes");
				Command no = new Command("No");
				Label LabelOne = new Label("");
				Command exit = Dialog.show("  Are you sure you want to exit  ", LabelOne, yes, no);

				if (exit == yes) 
				{
					gw.exit(0);
				} 
				else if (exit == no) 
				{
					return;
				}
			}
		};

	}

	public Command About()
	{
		return new Command("About")
		{
			public void actionPerformed(ActionEvent e)
			{
				Dialog about = new Dialog("About", new TableLayout(4, 1));
				Command ok = new Command("ok");

				String[] labels = {"  Shajaat Ali  ", "    CSC 133    ", "Robo Track Game"};
				for (String label : labels) {
				    about.add(new Label(label));
				}
				Command c = Dialog.show("", about, ok);
				if (c == ok) 
				{
					return;
				}
			}
		};
	}

	/*public Command Ticked()
	{
		return new Command("Tick")
		{
			public void actionPerformed(ActionEvent e) 
			{
				gw.ticked();
			}
		};
	}*/

	public Command Help()
	{
		return new Command("Help")
		{
			public void actionPerformed(ActionEvent e)
			{
				Dialog helpBox = new Dialog("Help", new TableLayout(10, 2));
				Label title = new Label("PLAYER CONTROLS");
				title.getAllStyles().setFgColor(ColorUtil.BLACK);
				helpBox.add(title).add(new Label());
				helpBox.add(new Label("Press 'a' to accelerate")).add(new Label());
				helpBox.add(new Label("Press 'b' to brake")).add(new Label());
				helpBox.add(new Label("Press 'l' to turn left")).add(new Label());
				helpBox.add(new Label("Press 'r' to turn right")).add(new Label());
				//helpBox.add(new Label("Press 'e' for energy station collision")).add(new Label());
				//helpBox.add(new Label("Press 'g' for drone collision")).add(new Label());
				//helpBox.add(new Label("Press 't' for ticked")).add(new Label());
				
				Command okCommand = new Command("OK");
				Command c = Dialog.show("", helpBox, okCommand);
				if (c == okCommand) {
					return;
				}
			}
		};
	}


	public Command nprCollision() 
	{
		return new Command("Collide with NPR") 
		{
			public void actionPerformed(ActionEvent e) {
				//gw.nprCollision();
			}
		};
	}

	public Command Sound() 
	{
		return new Command("Sound") {
			public void actionPerformed(ActionEvent e) 
			{
				gw.soundToggle();
			}
		};
	}

	public Command baseCollision() 
	{
		return new Command("Collide with Base") 
		{
			public void actionPerformed(ActionEvent e)
			{
				Command enterCommand = new Command("Confirm");
				TextField emptyText = new TextField();
				Command c = Dialog.show("Enter a Base number between 1-4", emptyText, enterCommand);
				int seq = 1;
				if (c == enterCommand) 
				{
					try {
						seq = Integer.parseInt(emptyText.getText());
					} catch (Exception ex) {
						Dialog.show("Error", "Enter a Number between 1-4", "OK", null);
						return;
					}
					if (seq > 4 || seq < 1) {
						Dialog.show("Error", "Enter a Number between 1-4", "OK", null);
						return;
					}
					gw.baseCollision(seq);
				}
			}
		};
	}
	
	public Command changeStrategies()
	{
		return new Command("Change Strategies")
		{
			public void actionPerformed(ActionEvent e)
			{
				gw.changeStrategies();
			}
		};
	}
	
	public Command togglePausePlay() 
	{
        return new Command("Pause")
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                game.pause();
            }
        };
    }
	
	public Command position()
	{
		return new Command("Position")
		{
			public void actionPerformed(ActionEvent evt)
			{
				gw.position();
		}
	 };
	}
}
	