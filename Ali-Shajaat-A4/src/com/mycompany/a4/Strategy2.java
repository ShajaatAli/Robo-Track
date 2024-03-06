package com.mycompany.a4;

public class Strategy2 implements IStrategy {

	private NonPlayerRobot npr;
	private Bases base;
	private NonPlayerRobot closestEnemy;

	public Strategy2(GameObjectCollection gameObjects, NonPlayerRobot npr) {
		this.npr = npr;
		int baseTarget = npr.getLastBaseReached() + 1;
		double minDist = Double.MAX_VALUE;
		IIterator itr = gameObjects.getIterator();
		while(itr.hasNext()) {
			GameObject temp = (GameObject) itr.getNext();
			if(temp instanceof Bases && ((Bases)temp).getSeqNumb() == baseTarget) {
				base = ((Bases)temp);
			}
			if(temp instanceof NonPlayerRobot && temp != this.npr) {
				double dist = Math.sqrt(((temp.getX() - npr.getX()) * (temp.getX() - npr.getX())) + ((temp.getY() - npr.getY()) * (temp.getY() - npr.getY())));
				if(dist < minDist) {
					minDist = dist;
					closestEnemy = (NonPlayerRobot) temp;
				}
			}
		}
	}

	@Override
	public void apply() {
		// The direction adjustment part would be similar as before but now considering the position of enemy robot as well
		// Here you'd calculate dx, dy to the base, and ex, ey to the enemy
		// Then you'd combine these two directions with some weighting to get the new direction

		double dx = Math.abs(base.getX() - npr.getX());
		double dy = Math.abs(base.getY() - npr.getY());
		double ex = Math.abs(closestEnemy.getX() - npr.getX());
		double ey = Math.abs(closestEnemy.getY() - npr.getY());
		
		// Calculate the distances and adjust the steer as per the new strategy
		
		double distanceToEnemy = Math.sqrt(ex*ex+ey*ey);

		// Based on the distance to the enemy robot, set the speed
		// If the enemy robot is too close, increase the speed to escape
		if(distanceToEnemy <= 30) {
			npr.setSpeed(5);
		}else {
			// Same speed modulation based on distance to the base
			double distanceToBase = Math.sqrt(dx*dx+dy*dy);
			if(distanceToBase <= 50) {
				npr.decreaseSpeed();
			}else {
				npr.setSpeed(3);
			}
		}
	}
}
