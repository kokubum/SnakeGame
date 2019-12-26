package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import application.Game;

public class SnakeGame extends JPanel implements ActionListener{

	
	private static final long serialVersionUID = 1L;
	
	private final int WINDOW_WIDTH = 500;
	private final int WINDOW_HEIGHT = 500;
	private final int DOT_SIZE = 10;
	private final int LIMIT_DOT = 900;
	private final int DELAY = 100;
	private final int BASE = 10;

	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean gameOver = false;
	
	private final int positionDotX[] = new int[LIMIT_DOT];
	private final int positionDotY[] = new int[LIMIT_DOT];	
	
	private Timer timer;
	private int dotsOfTheSnake;
	private int dotOfThePieceX;
	private int dotOfThePieceY;

	private JFrame frameGame;
	
	
	public SnakeGame(JFrame frameGame) {
		
		this.frameGame = frameGame;
		addKeyListener(new KeyUser());
		this.createPanel();
		this.initSnake();
		this.setTimer();
	}
	
	public void createPanel() {
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
	}
	
	public void initSnake() {
		this.dotsOfTheSnake = 3;
		
		for(int i=0;i<dotsOfTheSnake;i++) {
			this.positionDotX[i] = 100 - DOT_SIZE*i;
			this.positionDotY[i] = 100;
		}
		
		generatedOfDots();
	}
	
	public void setTimer() {
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void drawSnake(Graphics g) {
		g.setColor(Color.green);
		for(int i=0;i<dotsOfTheSnake;i++) {
			g.drawRect(positionDotX[i], positionDotY[i], DOT_SIZE, DOT_SIZE);
			g.fillRect(positionDotX[i], positionDotY[i], DOT_SIZE, DOT_SIZE);		
		}
		
		g.drawRect(dotOfThePieceX, dotOfThePieceY, DOT_SIZE, DOT_SIZE);
		g.fillRect(dotOfThePieceX, dotOfThePieceY, DOT_SIZE, DOT_SIZE);
		
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void generatedOfDots() {
		int generatedXPosition = (int)(Math.random() * ((WINDOW_WIDTH-DOT_SIZE)/BASE)); 
		int generatedYPosition = (int)(Math.random() * ((WINDOW_HEIGHT-DOT_SIZE)/BASE));
		
		
		dotOfThePieceX = generatedXPosition * DOT_SIZE;
		dotOfThePieceY = generatedYPosition * DOT_SIZE;
		
	}
	
	public void move() {
		for(int i=dotsOfTheSnake;i>0;i--) {
			positionDotX[i] = positionDotX[i-1];
			positionDotY[i] = positionDotY[i-1];
		}
		
		if(rightDirection == true) {
			positionDotX[0] = positionDotX[0]+DOT_SIZE;
		}
		else if(leftDirection == true) {
			positionDotX[0] = positionDotX[0] - DOT_SIZE;
		}
		else if(upDirection == true) {
			positionDotY[0] = positionDotY[0] - DOT_SIZE;
		
		}
		else if (downDirection == true) {
			positionDotY[0] = positionDotY[0] + DOT_SIZE;
			
		}
	}
	
	public void colisionCheck() {
		
		for(int i=dotsOfTheSnake;i>0 && dotsOfTheSnake>4;i--) {
			if(positionDotX[0] == positionDotX[i] && positionDotY[0] == positionDotY[i]) {
				gameOver = true;
			}
		}
		
		if(!gameOver && (positionDotX[0] == WINDOW_WIDTH  || positionDotY[0] == WINDOW_HEIGHT || positionDotX[0] < 0 || positionDotY[0] < 0)) {
			gameOver = true;
		}
		
		if(gameOver) {
			timer.stop();
			disposeTheGame();
		}
		
	}
	
	public void disposeTheGame() {
		
		if(JOptionPane.showConfirmDialog(frameGame, "DO YOU WANNA PLAY AGAIN?","GAME OVER",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			frameGame.dispose();
			Game.main(null);
		}
		else {
			System.exit(0);
		}
	}
	
	public void gotTheDotPiece() {
		if(positionDotX[0] == dotOfThePieceX && positionDotY[0] == dotOfThePieceY) {
			dotsOfTheSnake++;
			
			generatedOfDots();
		}
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawSnake(g);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(gameOver == false) {
			gotTheDotPiece();
			colisionCheck();
			move();
			
		}
		repaint();
			
		
	}
	
	private class KeyUser extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent event) {
			
			if((event.getKeyCode() == KeyEvent.VK_RIGHT) && leftDirection == false) {
				
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			else if ((event.getKeyCode() == KeyEvent.VK_LEFT) && rightDirection == false) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			else if ((event.getKeyCode() == KeyEvent.VK_UP) && downDirection == false) {

				upDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
			else if ((event.getKeyCode() == KeyEvent.VK_DOWN) && upDirection == false) {
				downDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
			
		}

	
		
	}
	
	public int getWINDOW_WIDTH() {
		return WINDOW_WIDTH;
	}

	public int getWINDOW_HEIGHT() {
		return WINDOW_HEIGHT;
	}
	
	
	


	
	
	
}
