package com.mycompany.a2;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import com.codename1.util.MathUtil;
import java.lang.*;



public class GameWorld extends Observable 
{
	private GameObjectCollection gameObjects;
	private boolean endGame;
	private int lives = 3;
	private int gameClock = 0;
	Robot robots = new Robot();
	Random rand = new Random();
	private double GWWidth;
	private double GWHeight;
	private boolean soundOn;
	
	public void init()
	{
		gameObjects = new GameObjectCollection();
		lives = 3;
		gameClock = 0; 
		addBases();
		addRobots();
		addNonPlayerRobots();
		addEnergyStation();
		addDrones();
		printGameObjects();
		soundOn = true;

	}
	
	public void printGameObjects() 
    {
        IIterator iterator = gameObjects.getIterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.getNext();
            System.out.println(gameObject.toString());
            System.out.println();
        }
    }
	
	public void addNonPlayerRobots() 
	{

	    IStrategy[] strategies = new IStrategy[] { new Strategy1(), new Strategy2() };

	    double[][] nprProperties = {
	        {120, 120, 5, 90},
	        {400, 300, 7, 45},
	        {600, 150, 10, 270}
	    };

	    for (int i = 0; i < 3; i++) {
	        double x = nprProperties[i][0];
	        double y = nprProperties[i][1];
	        int speed = (int) nprProperties[i][2];
	        int heading = (int) nprProperties[i][3];
	        NonPlayerRobot npr = new NonPlayerRobot(x, y, speed, heading);
	        npr.setStrategy(strategies[rand.nextInt(strategies.length)]);
	        gameObjects.add(npr);
	    }
	}


	public void addBases() 
	{
	    gameObjects.add(new Bases(1, 100, 100));
	    gameObjects.add(new Bases(2, 300, 100));
	    gameObjects.add(new Bases(3, 500, 100));
	    gameObjects.add(new Bases(4, 700, 100));
	}
	
	public void addRobots()
	{
		gameObjects.add(robots);
	}
	public void addEnergyStation()
	{
		for (int i = 0; i < 2; i++)
		{
			gameObjects.add(new EnergyStation(randomSize()));
		}
		
	}
	public void addDrones()
	{
		for (int i = 0; i < 2; i++)
		{
			gameObjects.add(new Drone(randomSpeed(), randomSize(), randomHeading()));
		}
		
	}
	public int randomSize()// Generating the random number between 10 and 50
	{
		int randomNum = rand.nextInt((50) + 1);
		return randomNum;
	}
	public int randomSpeed()// Generating the random number between 5 and 10
	{
		int randomNum =  rand.nextInt((10)+ 1) ;
		return randomNum;
	}
	public int randomHeading()// Generating the random number between 0 and 359
	{
		int randomNum =  rand.nextInt((359));
		return randomNum;
	}
	
	public void exit()
	{
		System.exit(0);
	}
	
	public boolean isEndGame() 
	{
		return endGame;
	}
	
	public void setEndGame(boolean endGame) 
	{
		this.endGame = endGame;
	}
	
	
	public void accelerate() 
	{
		int speedIncrease  = 5;
	    robots.setRobotsSpeed(robots.getSpeed() + speedIncrease);
	    System.out.println("The robot has accelerated");
	    setChanged();
	    notifyObservers();
	}
	
	public void brake() 
	{
		int speedDecrease  = -5;
	    robots.setRobotsSpeed(robots.getSpeed() + speedDecrease);
	    System.out.println("The robot has reduced speed");
	    setChanged();
	    notifyObservers();
	}
		
	public void left() 
	{
		int leftChange = -5;
	    robots.steeringHeading(leftChange);
	    System.out.println("The robot has gone left");
	    setChanged();
	    notifyObservers();
	}
	
	public void right() 
	{
		int rightChange = 5;
	    robots.steeringHeading(rightChange);
	    System.out.println("The robot has gone right");
	    setChanged();
	    notifyObservers();
			
	}
	public void robotsCollision()
	{
		robots.collisionOfRobots();
		System.out.println("Two Robots have collided");		
	}
	public void baseCollision(int x)
	{
		if ((x - robots.getLastBaseReached()== 1))
		{
			robots.setLastBaseReached(x);
			System.out.println("There has been a collision with base");
		}
	}
	public void energyStationsCollision()
	{
		 	GameObject energyStation = null; 
	        for (IIterator iterator = gameObjects.getIterator(); iterator.hasNext();) {
	            GameObject energy = iterator.getNext();
				if(energy instanceof EnergyStation)
				{
					if(((EnergyStation) energy).getCapacity() != 0)
					{
						energyStation = energy;
					}
				}
			}
			//collision with energy station increase robot energy
			robots.setEnergyLevel(robots.getEnergyLevel() + ((EnergyStation) energyStation).getCapacity());
			((EnergyStation) energyStation).setCapacity(0);
			gameObjects.add(new EnergyStation(randomSize()));
			System.out.println(" Robot has collided with energy station");	
	}
	public void dronesCollision()// This method execute the commands after the collision of drones
	{
		robots.collisionOfDrones();
		System.out.println("Robot and Drone have had a collision");
	}
	
	public void ticked() 
	{
		 this.gameClock++;
		    IIterator it = gameObjects.getIterator();
		    while (it.hasNext()) {
		        GameObject obj = it.getNext();
		        if (obj instanceof Drone) {
		            ((Drone) obj).randomHeading();
		        }
		        if (obj instanceof MoveableObject) {
		            ((MoveableObject) obj).Move();
		        }
		    }
		    setChanged();
		    notifyObservers();
	}

	
	public void display() 
	{
		System.out.println("\nNumber of lives left is: " + lives + 
							"\nCurrent value of clock is: " + gameClock +
							"\nThe highest base number reached is: " + robots.getLastBaseReached() +	
							"\nEnergy level of Robot is: " +  robots.getEnergyLevel() + 
							"\nRobots Current Damage level is: " +  robots.getDamageLevel());
	}
	
	public void map() 
	{
		/*for(GameObject objs : gameObjects) 
		{
			System.out.println(objs);
		}*/
	}
	
	public void nprCollision() 
	{
			IIterator iterator = gameObjects.getIterator();
			while (iterator.hasNext()) 
			{
		        GameObject obj = iterator.getNext();
		        if (obj instanceof NonPlayerRobot) 
		        {
		        		NonPlayerRobot npr = (NonPlayerRobot) obj;
		        		//double distance = Math.hypot(npr.getX() - robots.getX(), npr.getY() - robots.getY());
		                npr.collisionWithRobot();
		                robots.collisionWithNpr();
		                this.setChanged();
		                this.notifyObservers();
		                System.out.println("NPR has collided with player's robot");
		            
		        }
	    }
	}
	
	public void setGWHeight(double mapHeight) {
		GWHeight = mapHeight;
	}

	public double getGWHeight() 
	{
		// TODO Auto-generated method stub
		return GWHeight;
	}
	
	public void setGWWidth(double mapWidth) 
	{
		// TODO Auto-generated method stub
		GWWidth = mapWidth;
	}
	
	public double getGWWidth() 
	{
		// TODO Auto-generated method stub
		return GWWidth;
	}
	
	public int getClockValue() {
        return gameClock;
    }

    public int getLivesLeft() {
        return lives;
    }

    public int getLastBaseReached() {
        return robots.getLastBaseReached();
    }

    public int getEnergyLevel() {
        return robots.getEnergyLevel();
    }

    public int getDamageLevel() {
        return robots.getDamageLevel();
    }

    public boolean isSoundOn() {
        return soundOn;
    }
    
    public void soundToggle() {
        // Toggle the sound state between ON and OFF
        soundOn = !soundOn;
        setChanged();
        notifyObservers();
    }
    
    public void changeStrategies()
    {
        Robot playerRobot = null;
        List<Bases> bases = new ArrayList<>();

        IIterator iterator = gameObjects.getIterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof Robot) {
                playerRobot = (Robot) obj;
            } else if (obj instanceof Bases) {
                bases.add((Bases) obj);
            }
        }

        iterator = gameObjects.getIterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof NonPlayerRobot) {
                NonPlayerRobot npr = (NonPlayerRobot) obj;
                IStrategy currentStrategy = npr.getStrategy();

                // Choose a new strategy
                IStrategy newStrategy;
                if (currentStrategy instanceof Strategy1) {
                    newStrategy = new Strategy2();
                } else {
                    newStrategy = new Strategy1();
                }

                
                // Set the new strategy
                npr.setStrategy(newStrategy);

                // Apply the new strategy
                newStrategy.apply(npr, playerRobot, bases);

                // Set the next last base reached value for the robot
                npr.setLastBaseReached((npr.getLastBaseReached() % 4) + 1);

                // Set the robot's energy level to a reasonable value
                if (npr.getEnergyLevel() < 50) {
                    npr.setEnergyLevel(50);
                }
            }
        }
    }
}

