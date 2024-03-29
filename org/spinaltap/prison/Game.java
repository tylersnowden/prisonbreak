package org.spinaltap.prison;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/**
 * Prison Break Game
 * 
 * @author Tyler Snowden
 */
public class Game extends Canvas implements KeyListener, MouseListener {
	
	private BufferStrategy strategy; // The buffered strategy used for accelerated rendering
	
	/** True if corresponding key is pressed */
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	private Map map;
        private int WIDTH = 800;
        private int HEIGHT = 600;
	private Entity player;
        private Interface ui;
	
	/**
	 * Create the simple game - this also starts the game loop
	 */
	public Game() throws IOException {
		Frame frame = new Frame("Prison Break");
		frame.setLayout(null);
		setBounds(0,0,WIDTH,HEIGHT);
		frame.add(this);
		frame.setSize(WIDTH,HEIGHT);
		frame.setResizable(false);
		
                // Exit on Close
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// Key Listener
		frame.addKeyListener(this);
		addKeyListener(this);
                frame.addMouseListener(this);
                addMouseListener(this);
		
		frame.setVisible(true);
		
		// Strategy for 2D Rendering
		createBufferStrategy(2);
		strategy = getBufferStrategy();
                
                //Opening
                if (false) {
                    
                }
		
		// Level 1
                if (true) {
                    map = new Map("level1",20,15);
                    ui = new Interface(WIDTH, 100);
                    player = new Entity("tyler", map, 6f, 6f, 34, 46, 9);
                    gameLoop();
                }
	}
	
	/**
	 * Animation and Movement Loop
	 */
	public void gameLoop() {
		boolean gameRunning = true;
		long last = System.nanoTime();
		
		while (gameRunning) {
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			
			// Clear Screen
			g.setColor(Color.black);
			g.fillRect(0,0,WIDTH,HEIGHT);
			
			// Render Objects
			g.translate(2,25);
			map.paint(g);
                        ui.paint(g);
			player.paint(g);
                        
			// Clear the Buffer
			g.dispose();
			strategy.show();
			
			// Pause to Prevent Over Processing
			try { Thread.sleep(4); } catch (Exception e) {};
			
			// Checks how long its been since last run
			long delta = (System.nanoTime() - last) / 1000000;
			last = System.nanoTime();
		
			for (int i=0;i<delta / 5;i++) logic(5);
			if ((delta % 5) != 0) logic(delta % 5);
		}
	}
	
	/**
	 * Check the keyboard, and attempt to move the player
	 * 
	 * @param delta The amount of time to update for (in milliseconds)
	 */
	public void logic(long delta) {
		// Determine Direction of Move
		float dx = 0;
		float dy = 0;
		if (left) dx -= 1;
		if (right) dx += 1;
		if (up) dy -= 1;
		if (down) dy += 1;
		
		if ((dx != 0) || (dy != 0)) {
			player.move(dx * delta * 0.003f, dy * delta * 0.003f); // Move based on lapsed calculation
                        player.updateFrame(dx,dy,delta); // Determine Sprite Stage
		} else player.reset(); // Reset to Down Position
	}
	
	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
		if (e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
		if (e.getKeyCode() == KeyEvent.VK_UP) up = true;
	}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
		if (e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
		if (e.getKeyCode() == KeyEvent.VK_UP) up = false;
	}
        
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            ui.hearClick(x,y);
        }
	
	/**
	 * Entry Point
	 * 
	 * @param argv
	 */
	public static void main(String[] argv) throws IOException {
		new Game();
	}

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
