package com.mycompany.a2;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {
    private ArrayList<GameObject> gameObjects;

    public GameObjectCollection() {
        gameObjects = new ArrayList<>();
    }

    @Override
    public void add(GameObject obj) {
        gameObjects.add(obj);
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
    }
}
