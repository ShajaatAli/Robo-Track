package com.mycompany.a4;
import java.util.Observable;
import java.util.Random;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.geom.Point;
import com.codename1.util.MathUtil;


public class GameWorld extends Observable 
{
	private GameObjectCollection gameObjects;
	private int gameClock = 0;
	Robot robots = new Robot();
	Random rand = new Random();
	ShockWave shockwave = new ShockWave();
	private Sound collisionSound;
    private Sound energySound;
    private Sound loseLifeSound;
    private BGSound backgroundSound;
	private boolean soundOn = true;
	private boolean isPaused = false;
	private int timer;
	private static int gameHeight = 1024;
	private static int gameWidth = 768;
	private GameObject selectedObject;
	private boolean dragging;
    private Point originalPosition;
    private int capacity;

	
	public void init()
	{
		gameObjects = new GameObjectCollection();
		addObjects();
		//printGameObjects();
		soundOn = true;
		createSounds();
		backgroundSound.play();
        this.setChanged();
		this.notifyObservers(this);

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
	
	public void addObjects() {
		addBases();
		addRobots();
		addNonPlayerRobots();
		addEnergyStation();
		addDrones();
	}
	
	public void addNonPlayerRobots() 
	{
	    float[][] nprProperties = {
	        {350, 390},
	        {450, 300},
	        {550, 300}
	    };

	    for (int i = 0; i < 3; i++) {
	        float x = nprProperties[i][0];
	        float y = nprProperties[i][1];

	        NonPlayerRobot npr = new NonPlayerRobot(x, y, randomSpeed(), randomHeading());
	        if (i == 0 )
	        	npr.setStrategy(new Strategy1(npr,gameHeight, gameWidth));
	        else
	        	npr.setStrategy(new Strategy2(gameObjects,npr));
	        gameObjects.add(npr);
	    }
	}

	public void addShockWave(ShockWave shockwave)
	{
		gameObjects.add(shockwave);

	}

	public void addBases() 
	{
		Bases base1 = new Bases(1, 0, 0);
	    Bases base2 = new Bases(2, 700, 1000);
	    Bases base3 = new Bases(3,  500, 1000);
	    Bases base4 = new Bases(4,  1000, 0);
	   
	    gameObjects.add(base1);
	    gameObjects.add(base2);
	    gameObjects.add(base4);
	    gameObjects.add(base3);
	}
	
	public void addRobots() 
	{
		gameObjects.add(robots);
	}
	
	public void addEnergyStation() {
	    for (int i = 0; i < 2; i++) {
	    	capacity = rand.nextInt((50) + 10);
	        gameObjects.add(new EnergyStation(capacity,this));
	    }
	}
 
	public void addDrones() {
	    for (int i = 0; i < 2; i++) {
	    	float x = rand.nextInt(800)+100;
	    	float y = rand.nextInt(500)+100;
	        gameObjects.add(new Drone(x,y,randomSpeed(),randomHeading()));
	    }
	}
	
	public int randomSize()
	{
		int randomNum = rand.nextInt((50) + 1);
		return randomNum;
	}
	public int randomSpeed()
	{
		int randomNum =  rand.nextInt((10)+ 10) ;
		return randomNum;
	}
	public int randomHeading()
	{
		int randomNum =  rand.nextInt((359)+20);
		return randomNum;
	}
	
	public void exit(int number) {
		Command enter = new Command("Ok");
		Command answer = new Command("");
		switch(number) {
		case 0:
			answer = Dialog.show("Game Has Been Exited" ,"No body wins" , enter);
			break;
		case 1:
			answer = Dialog.show("You have lost the game!",  "NPR or drone has won", enter);
			getLoseLifeSound().play();
			break;
		case 2:
			answer = Dialog.show("You have lost the game!",  "You are out of lives!", enter);
			getLoseLifeSound().play();
			break;
		case 3: 
			Dialog.show("Alert",  "-1 Life", enter);
			break;
		case 4:
			answer = Dialog.show("Congrats", "You Win", enter);
			break;
		}
		if(answer.equals(enter)) {
			System.exit(0);
		}
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
	
	public void position() {
		
		System.out.println("Position Mode");
		if (dragging) {
	        dragging = false;
	        originalPosition = null;
	        selectedObject = null;
	        setChanged();
	        notifyObservers();
	    }
	}

    public GameObject getSelectedObject() {
        return selectedObject;
    }

    public void clearSelection() {
        selectedObject = null;
    }
    public Point getNewPosition() {
        return new Point(originalPosition.getX(), originalPosition.getY());
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
	/*public void energyStationsCollision()
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
	}*/
	public void dronesCollision()// This method execute the commands after the collision of drones
	{
		robots.collisionOfDrones();
		collisionSound.play();
		System.out.println("Robot and Drone have had a collision");
	}
	
	public void ticked(int elapsedTime) {
	    this.gameClock++;

	    // Move all movable objects and check for collisions
	    IIterator it1 = gameObjects.getIterator();
	    while (it1.hasNext()) {
	        GameObject obj1 = it1.getNext();
	        if (obj1 instanceof MoveableObject) {
	            ((MoveableObject) obj1).Move();
	            IIterator it2 = gameObjects.getIterator();
	            while (it2.hasNext()) {
	                GameObject obj2 = it2.getNext();
	                if (obj1 != obj2 && obj1.collidesWith(obj2)) {
	                    obj1.handleCollision(obj2);

	                    // Check if this is a NonPlayerRobot and the object is either a Robot or Drone
	                    if (obj1 instanceof NonPlayerRobot && (obj2 instanceof Robot || obj2 instanceof Drone)) {
	                        // Create a new shockwave
	                        ShockWave shockWave = new ShockWave();

	                        // Add the new ShockWave to the game world
	                        //this.addShockWave(shockWave);
	                    }
	                } 
	            }
	        }
	    }

	    checkCollision();
	    this.setChanged();
	    this.notifyObservers(this);
	}



	public void checkCollision() {
	    IIterator iter = gameObjects.getIterator();
	    while (iter.hasNext()) {
	        GameObject objs = iter.getNext();
	        IIterator iter2 = gameObjects.getIterator();
	        while (iter2.hasNext()) {
	            GameObject objs2 = iter2.getNext();
	            if (objs != objs2) {
	                if (objs.collidesWith(objs2)) {
	                    // Check if the objects' locations are too close
	                    if (areLocationsTooClose(objs, objs2)) {
	                        // Handle collision by adjusting the objects' locations
	                        adjustObjectLocations(objs, objs2);
	                    } else {
	                        // Perform the collision handling
	                        if (objs instanceof Robot && objs2 instanceof Drone) {
	                            if (getSound())
	                                collisionSound.play();
	                        } else if (objs instanceof Robot && objs2 instanceof NonPlayerRobot) {
	                            if (getSound())
	                                collisionSound.play();
	                        } else if (objs instanceof Robot && objs2 instanceof Bases) {
	                            if (getSound())
	                                collisionSound.play();
	                        } else if (objs instanceof Robot && objs2 instanceof EnergyStation) {
	                            if (getSound())
	                                energySound.play();
	                        }
	                        objs.handleCollision(objs2);
	                    }
	                }
	            }
	        }
	    }
	}

	private boolean areLocationsTooClose(GameObject object1, GameObject object2) {
	    float x1 = object1.getX();
	    float y1 = object1.getY();
	    float x2 = object2.getX();
	    float y2 = object2.getY();

	    float threshold = object1.getSize() / 2.0f + object2.getSize() / 2.0f;

	    float distance = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

	    return distance < threshold;
	}

	private void adjustObjectLocations(GameObject object1, GameObject object2) {
	    float x1 = object1.getX();
	    float y1 = object1.getY();
	    float x2 = object2.getX();
	    float y2 = object2.getY();

	    // Calculate the average x and y positions
	    float avgX = (x1 + x2) / 2.0f;
	    float avgY = (y1 + y2) / 2.0f;

	    // Calculate the displacement vector between the objects
	    float dx = x2 - x1;
	    float dy = y2 - y1;

	    // Normalize the displacement vector
	    float length = (float) Math.sqrt(dx * dx + dy * dy);
	    dx /= length;
	    dy /= length;

	    // Calculate the new positions by moving the objects away from each other
	    float newX1 = avgX - dx * object1.getSize() / 2.0f;
	    float newY1 = avgY - dy * object1.getSize() / 2.0f;
	    float newX2 = avgX + dx * object2.getSize() / 2.0f;
	    float newY2 = avgY + dy * object2.getSize() / 2.0f;

	    // Set the new positions for the objects
	    object1.setX(newX1);
	    object1.setY(newY1);
	    object2.setX(newX2);
	    object2.setY(newY2);
	}


	public boolean getSound() {
		return soundOn;
	}

	/*public void display() 
	{
		System.out.println("\nNumber of lives left is: " + lives + 
							"\nCurrent value of clock is: " + gameClock +
							"\nThe highest base number reached is: " + robots.getLastBaseReached() +	
							"\nEnergy level of Robot is: " +  robots.getEnergyLevel() + 
							"\nRobots Current Damage level is: " +  robots.getDamageLevel());
	}

	
	public void setGWHeight(double mapHeight) {
		GWHeight = mapHeight;
	}

	public  double getGWHeight() 
	{
		return GWHeight;
	}
	
	public void setGWWidth(double mapWidth) 
	{
		GWWidth = mapWidth;
	}
	
	public double getGWWidth() 
	{
		return GWWidth;
	}*/
	
	public int getClockValue() {
        return gameClock;
    }

   /* public int getLivesLeft() {
        return lives;
    }*/

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
        this.setChanged();
		this.notifyObservers(this);
    }
    
    public void changeStrategies()
    {
    	System.out.println("The Strategy has been changed");
    	IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject objs = iter.getNext();
			if (objs instanceof NonPlayerRobot)
			{
				NonPlayerRobot npr = (NonPlayerRobot)objs;
				if (npr.getStrategy() == true)
					npr.setStrategy(new Strategy1(npr,gameHeight, gameWidth));
				else
					npr.setStrategy(new Strategy2(gameObjects,npr));
			}	
		}

		this.setChanged();
		this.notifyObservers(this);
    }
    
    public GameObjectCollection getGameObjects() 
    {
        return gameObjects;
    }
    
    public void createSounds() 
    {
        // Create the sound objects
        collisionSound = new Sound("charging.wav");
        energySound = new Sound("charging.wav");
        setLoseLifeSound(new Sound("lose_life.wav"));
        backgroundSound = new BGSound("background.wav");
    }
	
	public boolean isPaused() 
	{
        return isPaused;
    }

    public boolean isLocationOccupied(double x, double y, double safeDistance) {
        IIterator iterator = gameObjects.getIterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            double distance = Math.sqrt(MathUtil.pow((x - obj.getLocationX()), 2.0) + MathUtil.pow((y - obj.getLocationY()), 2.0));
            double minimumSafeDistance = safeDistance + obj.getSize()/2.0;
            if (distance <= minimumSafeDistance) {
                return true;
            }
        }
        return false;
    }
    
    public int getTime() {
		return this.timer/100;
    }
    
    public static void setGameWidth(int w) { gameWidth = w; }
	public static void setGameHeight(int h) { gameHeight = h; }

	public static int getGameHeight() {
		return gameHeight;
	}

	public int getLivesLeft() {
		IIterator itr = gameObjects.getIterator();
		while(itr.hasNext()) {
			GameObject gameObject = (GameObject) itr.getNext();
			if (gameObject instanceof Robot)
			{
				return ((Robot)gameObject).getLife();
			}
		}
		return 0;
	}

	public Sound getLoseLifeSound() {
		return loseLifeSound;
	}

	public void setLoseLifeSound(Sound loseLifeSound) {
		this.loseLifeSound = loseLifeSound;
	}

}

