package com.Game;
import javax.swing.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class Game extends JPanel implements Runnable, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Frog frog;
	Lane[] lanes;
	Winner[] winners;
	BoxCollider[] walls;
	
	public int KodokLolos = 0;
	
	boolean isRunning;
	Thread thread;
	BufferedImage view, background;
	
	
	public int WIDTH = 224;
	public int HEIGHT = 256;
	public int SCALE = 2;
	
	public Game() {
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		addKeyListener(this);
	}
	
	public static void main(String[]args) {
		JOptionPane.showMessageDialog(null, "KELOMPOK BIAWAK:\n\nWilliam (5025201167)\nGabriel (5025201165)", "Kelompok Biawak Present", JOptionPane.INFORMATION_MESSAGE);
		JFrame w = new JFrame("KodokLompat");
		w.setResizable(false);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.add(new Game());
		w.pack();
		w.setLocationRelativeTo(null);
		w.setVisible(true);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			isRunning = true;
			thread.start();
		}
	}
	
	public void start() {
		try {
			view = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			background = ImageIO.read(getClass().getResource("/bg1.png"));
			frog = new Frog(112, 226, 14, 14);
			
			lanes = new Lane[10];
			
			lanes[0]= new Lane(TypeObstacle.LOG, 3, 3, -0.8f, 80);
			lanes[1]= new Lane(TypeObstacle.LOG, 4, 2, 0.5f, 80);
			lanes[2]= new Lane(TypeObstacle.LOG, 5, 3, -0.7f, 80);
			lanes[3]= new Lane(TypeObstacle.LOG, 6, 1, 0.4f, 80);
			lanes[4]= new Lane(TypeObstacle.LOG, 7, 3, -0.6f, 80);
			lanes[5]= new Lane(TypeObstacle.CAR_5, 9, 3, 0.4f, 60);
			lanes[6]= new Lane(TypeObstacle.CAR_3, 10, 2, 0.7f, 3*16);
			lanes[7]= new Lane(TypeObstacle.CAR_4, 11, 2, 0.5f, 30);
			lanes[8]= new Lane(TypeObstacle.CAR_2, 12, 3, 0.5f, 45);
			lanes[9]= new Lane(TypeObstacle.CAR_1, 13, 3, 0.5f, 80);

			winners = new Winner[5];
			for(int i = 0; i<5; i++) {
				winners[i]= new Winner((i * 48)+ 8, 32);
			}
			
			walls = new BoxCollider[6];
			for(int i = 0; i<6; i++) {
				walls[i] = new BoxCollider(-22 + (i*48) , 24, 28, 24);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		frog.update();
		
		for(Lane lane : lanes) {
			lane.update();
		}
		
		int laneIndex = (int)(frog.y/16);
		if(laneIndex >= 9 && laneIndex <=13) {
			laneIndex -=4;
			lanes[laneIndex].check(frog);
		}else if (laneIndex >=3 && laneIndex <=7) {
			laneIndex -=3;
			lanes[laneIndex].check(frog);
		}
		
		
		for(Winner winner : winners) {
			if(frog.intersects(winner)) {
				if(!winner.visible) {
					winner.visible = true;
					KodokLolos++;
					if(KodokLolos == 5) {
						updateWin();
					}
				}else {
					frog.resetFrog();
				}
			}				
		}
		
		for(BoxCollider wall : walls) {
			if(frog.intersects(wall)) {
				frog.resetFrog();
			}
		}
	}
	
	public boolean isWin() {
	    for(Winner winner : winners) {
			if(frog.intersects(winner)) {
				if(!winner.visible) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void updateWin() {
       if (isWin()) {
    	   JOptionPane.showMessageDialog(null, "YOU WIN", "Win Statement", JOptionPane.INFORMATION_MESSAGE);
    	   System.exit(0);
           }
       else {
           frog.resetFrog();
           }
    }

	
	
	public void draw() {
		Graphics2D g2 = (Graphics2D) view.getGraphics();
		g2.drawImage(background, 0, 0, background.getWidth(), background.getHeight(),null);
	
		for(Lane lane : lanes) {
			lane.draw(g2);
		}
		
		for(Winner winner : winners) {
			winner.draw(g2);
		}
		
		frog.draw(g2);
		
		Graphics g =getGraphics();
		g.drawImage(view, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
	}
	
	@Override
	public void run() {
		try {
			requestFocus();
			start();
			while(isRunning) {
				update();
				draw();
				Thread.sleep(1000/60);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		frog.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
