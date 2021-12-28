package com.Game;

import java.awt.Graphics2D;


public class Lane extends BoxCollider{
	
	
	private static final float WIDTH = 224;
	Obstacle[] obstacles;
	float speed;
	TypeObstacle type;
	
	
	public Lane(TypeObstacle type, int index, int n, float speed, float spacing) {
		super(0, index*16, WIDTH, 16);
		obstacles = new Obstacle[n];
		this.speed = speed;
		this.type = type;
		for(int i = 0; i<n; i++) {
			obstacles[i] = new Obstacle(type, spacing * i, y, speed);
		}
	}
	
	public void check(Frog frog) {
		if(type != TypeObstacle.LOG) {
			for(Obstacle obstacle : obstacles) {
				if(frog.intersects(obstacle)) {
					frog.resetFrog();
				}
			}
		} else {
			boolean ok = false;
			for(Obstacle obstacle : obstacles) {
				if(frog.intersects(obstacle)) {
					ok = true;
					frog.x += obstacle.speed;
				}
			}
			if(!ok) {
				frog.resetFrog();
			}
		}
	}
	
	public void update() {
		for(Obstacle obstacle : obstacles) {
			obstacle.update();
		}
	}
	
	public void draw(Graphics2D g2) {
		for(Obstacle obstacle : obstacles) {
			obstacle.draw(g2);
		}
	}
	
}