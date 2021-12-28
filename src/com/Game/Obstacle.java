package com.Game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Obstacle extends BoxCollider{
	private static final float WIDTH = 224;
	float speed = 0;
	BufferedImage obstacle;
	TypeObstacle type;
	
	public Obstacle(TypeObstacle type, float x, float y, float speed) {
		super(x, y, 15, 14);
		try {
			this.speed = speed;
			this.type = type;
			
			BufferedImage spriteSheets = ImageIO.read(getClass().getResource("/obstacle.png"));		
		
			switch (type) {
				case CAR_1:
					obstacle = spriteSheets.getSubimage(0, 0, 15, 14);
					break;
				case CAR_2:
					obstacle = spriteSheets.getSubimage(0, 14, 15, 14);
					break;
				case CAR_3:
					width = 14;
					obstacle = spriteSheets.getSubimage(0, 2*14, 14, 12);
					break;
				case CAR_4:
					this.speed = -this.speed;
					obstacle = spriteSheets.getSubimage(0, (int) (3*13.4), 15, (int)(10.8));
					break;
				case CAR_5:
					this.speed = -this.speed;
					width = 30;
					obstacle = spriteSheets.getSubimage(0, (int)(4*12.5), 30, (int)(10.5));
					break;
				case LOG:
				    width = 3*14;
				    obstacle = spriteSheets.getSubimage (0, (int)(5 * 12), 3 * 14, (int)(10.25));
				    break;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		x = x + speed;
		if(speed > 0 && x > WIDTH) {
			x = -obstacle.getWidth();
		}else if(speed < 0 && x < -obstacle.getWidth()) {
			x = WIDTH;
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(obstacle, (int) x, (int) y, obstacle.getWidth(), obstacle.getHeight(), null);
	}
}