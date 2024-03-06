package com.mycompany.a4;
import java.util.ArrayList;
import java.util.Random;

import com.codename1.ui.geom.Point2D;

public class GameObjectCollection implements ICollection {
    private ArrayList<GameObject> gameObjects;
    protected int  maxX = 1024;
	protected int minX = 10;
	protected int maxY = 768;
	protected int minY = 10;

    public GameObjectCollection() {
        gameObjects = new ArrayList<>();
    }

    @Override
    public void add(GameObject obj) {
        Point2D newLocation = obj.getLocation();
        while(isLocationOccupied(newLocation)) {
            // Generate a new random location so that no base or energy station spawn on each other
            float x = (float)(new Random().nextInt(maxX - minX) + minX); 
            float y = (float)(new Random().nextInt(maxY - minY) + minY);
            newLocation = new Point2D(x, y);
        }
        obj.setLocation(newLocation);
        gameObjects.add(obj);
    }
    
    public boolean isLocationOccupied(Point2D location) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IIterator getIterator() {
        return new GameObjectIterator();
    }

    private class GameObjectIterator implements IIterator {
        private int currentIndex;

        public GameObjectIterator() {
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < gameObjects.size();
        }

        @Override
        public GameObject getNext() {
            if (hasNext()) {
                GameObject gameObject = gameObjects.get(currentIndex);
                currentIndex++;
                return gameObject;
            }
            return null;
        }

        @Override
        public GameObject getCurrent() {
            return gameObjects.get(currentIndex);
        }

        @Override
        public void remove(GameObject gameObj) {
            gameObjects.remove(gameObj);
        }
    }
}
