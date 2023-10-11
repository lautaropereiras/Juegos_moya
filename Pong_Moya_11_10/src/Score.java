import java.awt. *;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class Score extends Rectangle {


	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	int playerLives;
	int playerScore;
	int player1;
	int player2;
	
	

		


	Score(int GAME_WIDTH, int GAME_HEIGHT, int initialLives, int initialpoints) {
		Score.GAME_WIDTH = GAME_WIDTH;
		Score.GAME_HEIGHT = GAME_HEIGHT;
        this.playerLives = initialLives;
        this.playerScore = initialpoints;

    }

    public void playerLosesLife() {
        playerLives--;
    }
    
    public void increaseScore() {
        playerScore++; 
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        g.drawString("Vidas: " + playerLives, GAME_WIDTH / 2 - 50, 40);
        g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
        g.drawString("Puntaje: " + playerScore, GAME_WIDTH / 2 - 60, 70);


    }
    
    
}

