package vt;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	static final int WIDTH = 500;
	static final int HEIGHT = 500;
	static final int UNIT_SIZE = 20;
	static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

	final int x[] = new int[NUMBER_OF_UNITS];
	final int y[] = new int[NUMBER_OF_UNITS];

	int length = 5;
	int foodEaten;
	int foodX;
	int foodY;
	String color;
	char direction = 'D';
	boolean running = false;
	Random random;

	private javax.swing.Timer timer;

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		run();
	}	

	public void run() {
		addFood();
		running = true;

		javax.swing.Timer timer = new javax.swing.Timer(100, this);
		timer.start();	
	}


	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
	}

	public void move() {
		for (int i = length; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}

		if (direction == 'L') {
			x[0] = x[0] - UNIT_SIZE;
		} else if (direction == 'R') {
			x[0] = x[0] + UNIT_SIZE;
		} else if (direction == 'U') {
			y[0] = y[0] - UNIT_SIZE;
		} else {
			y[0] = y[0] + UNIT_SIZE;
		}	
	}

	public void checkFood() {
		if(x[0] == foodX && y[0] == foodY) {
			length++;
			foodEaten++;
			addFood();
		}
	}

	public void draw(Graphics graphics) {

		if (running) {
			graphics.setColor(new Color(210, 115, 90));
			graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

			graphics.setColor(Color.white);
			graphics.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);

			for (int i = 1; i < length; i++) {
				graphics.setColor(new Color(40, 200, 150));
				graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}

			graphics.setColor(Color.white);
			graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
			FontMetrics metrics = getFontMetrics(graphics.getFont());
			graphics.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());

		} else {
			gameOver(graphics);
		}
	}

	

	public void addFood() {
		foodX = random.nextInt((int)(WIDTH / UNIT_SIZE))*UNIT_SIZE;
		foodY = random.nextInt((int)(HEIGHT / UNIT_SIZE))*UNIT_SIZE;
	}

	public void checkHit() {
		for (int i = length; i > 0; i--) {
			if (x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}

		if (x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT) {
			running = false;
		}

		if(!running) {
			javax.swing.Timer timer = new javax.swing.Timer(10,this);
			timer.stop();
			
		}
	}

	public void gameOver(Graphics graphics) {
		graphics.setColor(Color.red);
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);

		graphics.setColor(Color.white);
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
		metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());

	}

	public void actionPerformed(ActionEvent arg0) {
		if (running) {
			move();
			checkFood();
			checkHit();
		}
		repaint();
	}

	

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';
					}
					break;

				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';
					}
					break;

				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';
					}
					break;

				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';
					}
					break;		
			}
		}
	}
}


