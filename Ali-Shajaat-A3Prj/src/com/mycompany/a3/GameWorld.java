package com.mycompany.a3;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import com.codename1.util.MathUtil;


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
	private Sound collisionSound;
    private Sound energySound;
    private Sound loseLifeSound;
    private BGSound backgroundSound;
	private boolean soundOn = true;
	private boolean isPaused = false;
	private boolean positionMode = false;
	
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
		createSounds();
        backgroundSound.play();

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
	        {180, 90, 5, 90},
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
	    gameObjects.add(new Bases(1, 200, 100));
	    gameObjects.add(new Bases(2, 300, 1000));
	    gameObjects.add(new Bases(3, 1000, 100));
	    gameObjects.add(new Bases(4, 1000, 1000));
	}
	
	public void addRobots()
	{
		gameObjects.add(robots);
	}
	public void addEnergyStation() {
	    for (int i = 0; i < 2; i++) {
	        double x, y;
	        do {
	            x = rand.nextDouble() * GWWidth;
	            y = rand.nextDouble() * GWHeight;
	        } while (isLocationOccupied(x, y, 50)); 

	        gameObjects.add(new EnergyStation(randomSize(), x, y));
	    }
	}
	public void addDrones() {
	    for (int i = 0; i < 2; i++) {
	        double x, y;
	        do {
	            x = rand.nextDouble() * GWWidth ;
	            y = rand.nextDouble() * GWHeight;
	        } while (isLocationOccupied(x, y, 50));

	        gameObjects.add(new Drone(x,y,randomSpeed(), randomSize(), randomHeading()));
	    }
	}
	public int randomSize()
	{
		int randomNum = rand.nextInt((50) + 1);
		return randomNum;
	}
	public int randomSpeed()
	{
		int randomNum =  rand.nextInt((10)+ 1) ;
		return randomNum;
	}
	public int randomHeading()
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
		collisionSound.play();
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
			gameObjects.add(new EnergyStation(randomSize(),50.0,50.0));
			energySound.play();
			System.out.println(" Robot has collided with energy station");	
	}
	public void dronesCollision()// This method execute the commands after the collision of drones
	{
		robots.collisionOfDrones();
		collisionSound.play();
		System.out.println("Robot and Drone have had a collision");
	}
	
	public void ticked(int elapsedTime)
	{
	    //this.gameClock++;
		//top line make the gameclock go by hella fast

	    // Move all movable objects and check for collisions
	    IIterator it1 = gameObjects.getIterator();
	    while (it1.hasNext()) {
	        GameObject obj1 = it1.getNext();
	        if (obj1 instanceof MoveableObject) {
	            ((MoveableObject) obj1).Move(elapsedTime);

	            // Check for collisions with other objects
	            //IIterator it2 = gameObjects.getIterator();
	            /*while (it2.hasNext()) {
	                GameObject obj2 = it2.getNext();
	                if (obj1 != obj2 && obj1.collidesWith(obj2)) {
	                    obj1.handleCollision(obj2);
	                }
	            }*/
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
		                npr.collisionWithRobot();
		                robots.collisionWithNpr();
		                this.setChanged();
		                this.notifyObservers();
		                collisionSound.play();
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
    
    public void soundToggle() 
    {
        soundOn = !soundOn;
        if (soundOn) {
            backgroundSound.setMute(false);
            backgroundSound.play();
        } else {
            backgroundSound.setMute(true);
            backgroundSound.pause();
        }
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
    
    public GameObjectCollection getGameObjects() 
    {
        return gameObjects;
    }
    
    public void createSounds() 
    {
        // Create the sound objects
        collisionSound = new Sound("collision.wav");
        energySound = new Sound("charging.wav");
        loseLifeSound = new Sound("lose_life.wav");
        backgroundSound = new BGSound("background.wav");
    }
    
    public Robot getRobot() {
        IIterator it = gameObjects.getIterator();
        while (it.hasNext()) {
            GameObject obj = it.getNext();
            if (obj instanceof Robot) {
                return (Robot) obj;
            }
        }
        return null;
    }
	
	public boolean isPaused() 
	{
        return isPaused;
    }

	public void position() {
        if (isPaused()) { 
            positionMode = true;
        }
    }

    public boolean isPositionMode() {
        return positionMode;
    }

    public void setPositionMode(boolean positionMode) {
        this.positionMode = positionMode;
    }

    public void moveSelectedObjectToPoint(double x, double y) {
        IIterator iterator = getGameObjects().getIterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof ISelectable && ((ISelectable) obj).isSelected()) {
                obj.setLocation(x,y);
                break;
            }
        }
    }
    
    public boolean isLocationOccupied(double x, double y, double safeDistance) {
        IIterator iterator = gameObjects.getIterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            double distance = Math.sqrt(MathUtil.pow((x - obj.getLocationX()), 2.0) + MathUtil.pow((y - obj.getLocationY()), 2.0));
            if (distance <= safeDistance) {
                return true;
            }
        }
        return false;
    }
    
}

