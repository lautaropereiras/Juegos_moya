import java.awt. *;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable {
		
	
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	static final int initialSpeed = 2;

	
		GamePanel()
		{
			
			NewPaddle();
			NewBall();
			score = new Score(GAME_WIDTH, GAME_HEIGHT, 3, 0); 
			this.setFocusable(true);
			this.addKeyListener(new AL());
			this.setPreferredSize(SCREEN_SIZE);
			
			gameThread = new Thread(this);
			gameThread.start();
			
		}
		public void NewPaddle()
		{
			paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
			//paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
		}
		public void NewBall()
		{
			random = new Random();
			ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
			
		}
		
		public void move()
		{
			paddle1.move();
			//paddle2.move();
			ball.move();
		}
		public void run()
		{
			//game loop
			long lastTime = System.nanoTime();
			double amountOfTicks =60.0;
			double ns = 1000000000 / amountOfTicks;
			double delta = 0;
			while(true) {
				long now = System.nanoTime();
				delta += (now -lastTime)/ns;
				lastTime = now;
				if(delta >=1) {
					move();
					CheckCollision();
					repaint();
					delta--;
				}
			}
		}
		public void paint(Graphics g)
		{
			image = createImage(getWidth(), getHeight());
			graphics = image.getGraphics();
			draw(graphics);
			g.drawImage(image,0,0,this);
		

		}
		public void draw(Graphics g)
		{
			paddle1.draw(g);
			//paddle2.draw(g);
			ball.draw(g);
			score.draw(g);
			Toolkit.getDefaultToolkit().sync(); 
		}
		public void CheckCollision()
		{
			
			
			/*
			if(paddle1.y<=0)
			{
				paddle1.y=0;
			}
			if(paddle1.y>=(GAME_HEIGHT-PADDLE_HEIGHT))
			{
				paddle1.y= GAME_HEIGHT-PADDLE_HEIGHT;
			}
			/*if(paddle2.y<=0)
			{
				paddle2.y=0;
			}
			if(paddle2.y>=(GAME_HEIGHT-PADDLE_HEIGHT))
			{
				paddle2.y= GAME_HEIGHT-PADDLE_HEIGHT;
			}*/
			
/*			if(ball.y<=0)
			{
				ball.setYDirection(-ball.yVelocity);
			} 
			if(ball.y>= GAME_HEIGHT-BALL_DIAMETER)
			{
				ball.setYDirection(-ball.yVelocity);
			}
			
			if(ball.intersects(paddle1))
			{
				ball.xVelocity = Math.abs(ball.xVelocity);
				//ball.xVelocity++; aumenta la velocidad de la pelota cuando le pegas 
				/*if(ball.yVelocity>0)
					//ball.yVelocity++; aumenta la velocidad de la pelota cuando le pegas 
				else 
					ball.yVelocity--;*/
			//	ball.setXDirection(ball.xVelocity);
			//	ball.setYDirection(ball.yVelocity);			
	//		}
			
			/*if (ball.x <= 0) {
				score.playerLosesLife();
		        if (score.playerLives > 0) {
		        	ball.x = (GAME_WIDTH / 2) - (BALL_DIAMETER / 2);
		        	ball.y = random.nextInt(GAME_HEIGHT - BALL_DIAMETER);
		            paddle1.y = (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2);
		            ball.setYDirection(-initialSpeed);
		            ball.setXDirection(random.nextBoolean() ? initialSpeed : -initialSpeed);


		        } else {
		            JOptionPane.showMessageDialog(this, "¡Perdiste todas tus vidas! Fin del Juego");
		        	System.exit(0); 
		    }
			
			
			
			
			
			
		}*/
			
			
			
			if (ball.intersects(paddle1)) {
		        ball.xVelocity = Math.abs(ball.xVelocity);
		        ball.xVelocity++;
		        if (ball.yVelocity > 0)
		            ball.yVelocity++;
		        else
		            ball.yVelocity--;
		        ball.setXDirection(ball.xVelocity);
		        ball.setYDirection(ball.yVelocity);

		    }
			
			if (ball.x <= 0) {
		        score.playerLosesLife();
		        if (score.playerLives > 0) {
		        	ball.x = (GAME_WIDTH / 2) - (BALL_DIAMETER / 2);
		        	ball.y = random.nextInt(GAME_HEIGHT - BALL_DIAMETER);
		        	paddle1.y = (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2);
		            ball.setXDirection(-initialSpeed);
		            ball.setYDirection(random.nextBoolean() ? initialSpeed : -initialSpeed);


		        } else {
		            JOptionPane.showMessageDialog(this, "¡Perdiste todas tus vidas! Fin del Juego");
		        	System.exit(0); 
		    }
			}

			if(ball.y <=0) {
				ball.setYDirection(-ball.yVelocity);
			}
			   if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
				   ball.setXDirection(-ball.xVelocity);
			    }

			if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
				ball.setYDirection(-ball.yVelocity);
			}
			if(ball.intersects(paddle1)) {
				score.increaseScore();
				ball.xVelocity = Math.abs(ball.xVelocity);
				ball.xVelocity++; 
				
				if(ball.yVelocity>0)
					ball.yVelocity++; 
				else
					ball.yVelocity--;
				ball.setXDirection(ball.xVelocity);
				ball.setYDirection(ball.yVelocity);
				
				
				
			}
			if(ball.x <=0) {
				//score.player2++;
				NewPaddle();
				NewBall();
				System.out.println("Player 2: "+score.player2);
			}
			if(paddle1.y<=0)
				paddle1.y=0;
			if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
				paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
		}	
		public class AL extends KeyAdapter
		{
			public void keyPressed(KeyEvent e)
			{
				paddle1.keyPressed(e);
				//paddle2.keyPressed(e);
			}
			public void keyReleased(KeyEvent e)
			{
				paddle1.keyReleased(e);
				//paddle2.keyReleased(e);
			}
		}
		
}
