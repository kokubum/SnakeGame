package application;

import javax.swing.JFrame;
import snake.SnakeGame;

public class Game extends JFrame{
	
	
	private static final long serialVersionUID = 1L;

		
	public Game() {
		initGame();
	}
	
	public void initGame() {
		setTitle("Snake Game");
		getContentPane().add(new SnakeGame(this));
		setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
	}
	
	
	
	public static void main(String[] args) {
		Game game = new Game();
		game.setVisible(true);
			
	}
}
