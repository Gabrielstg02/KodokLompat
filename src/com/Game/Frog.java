package com.Game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.event.MenuKeyEvent;


public class Frog extends BoxCollider{
	BufferedImage frog;
	BufferedImage[] anim, frogAnimLeft, frogAnimRight, frogAnimUp, frogAnimDown;
	
	public final int WIDTH = 224;
	public final int HEIGHT = 256;
	public final int SCALE = 2;
	
	private int frameIndex;
	private boolean jumping;
	
	public Frog(float x, float y, float width, float height) {
		super(x,  y,  width, height);
		try {
			BufferedImage frogSpriteSheet = ImageIO.read(getClass().getResource("/Kodok.png"));
			frogAnimDown = new BufferedImage[3];
			frogAnimUp = new BufferedImage[3];
			frogAnimRight = new BufferedImage[3];
			frogAnimLeft = new BufferedImage[3];
			anim = new BufferedImage[3];
			
			int frogTileSize = 14;
			for(int i = 0; i<3; i++) {
				frogAnimDown[i] = frogSpriteSheet.getSubimage(
						i* frogTileSize,
						0,
						frogTileSize,
						frogTileSize
						);
				
				frogAnimUp[i] = frogSpriteSheet.getSubimage(
						i*frogTileSize,
						frogTileSize,
						frogTileSize,
						frogTileSize
						);
				
				frogAnimRight[i] = frogSpriteSheet.getSubimage(
						i*frogTileSize,
						frogTileSize*2,
						frogTileSize,
						frogTileSize
						);
				
				frogAnimLeft[i] = frogSpriteSheet.getSubimage(
						i*frogTileSize,
						frogTileSize*3,
						frogTileSize,
						frogTileSize
						);		
			}
			
			frog = frogAnimUp[2];
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resetFrog() {
		x = 112;
		y = 226;
		frog = frogAnimUp[2];
	}
	
	public void update() {
		if(jumping) {
			frameIndex++;
			frog = anim[(int) ((frameIndex/5.0)*(anim.length-1))];
			if(frameIndex > 5) {
				frameIndex = 0;
				jumping = false;
			}
		}
		x = Math.min(Math.max(x, 0), WIDTH - width);
		y = Math.min(Math.max(y, 0), HEIGHT - height);
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(frog,(int)x , (int) y-1, (int)width, (int) height, null);
	}
	
	public void jump(int xDir, int yDir) {
		frameIndex = 0;
		x += 16 * xDir;
		y += 16 * yDir;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			jumping =true;
			anim = frogAnimUp;
			jump(0,-1);
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			jumping = true;
			anim = frogAnimDown;
			jump(0,1);
		}else if(e.getKeyCode() == MenuKeyEvent.VK_RIGHT) {
			jumping = true;
			anim = frogAnimRight;
			jump(1,0);
		}else if(e.getKeyCode() == MenuKeyEvent.VK_LEFT) {
			jumping = true;
			anim = frogAnimLeft;
			jump(-1,0);
		}
	}
}