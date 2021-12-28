package com.Game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Winner extends BoxCollider{
	boolean visible;
	BufferedImage image;
	
	public Winner(float x, float y) {
		super(x, y, 0, 0);
		try {
			image = ImageIO.read(getClass().getResource("/kodokwin.png"));
			width = image.getWidth();
			height = image.getHeight();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void draw(Graphics2D g) {
		if(visible) {
			g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
		}
	}
}