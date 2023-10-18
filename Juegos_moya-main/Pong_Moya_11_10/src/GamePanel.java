import java.awt. *;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.ArrayList;  
import java.util.List;


public class GamePanel extends JPanel implements Runnable {
		
	
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	static final int BLOCK_WIDTH = 75;
	static final int BLOCK_HEIGHT = 50;
	 List<Block> block;
		 

	    int currentLevelIndex;
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
			NewBlock(1);
			currentLevelIndex = 1;
		    NewBlock(currentLevelIndex);
			score = new Score(GAME_WIDTH, GAME_HEIGHT, 3, 0); 
			this.setFocusable(true);
			this.addKeyListener(new AL());
			this.setPreferredSize(SCREEN_SIZE);
			
			gameThread = new Thread(this);
			gameThread.start();
			
		    }
		
		private int Niveles(int nivel) {
			block.clear();
		    int numBlocks = 2;

		    if (nivel == 1) {
		        numBlocks = 2;
		    } else if (nivel == 2) {
		        numBlocks = 5;
		    } else if (nivel == 3) {
		        numBlocks = 10;
		    }

		    return numBlocks;
		}
		public void NewBlock(int nivel) {
		    block = new ArrayList<>();
		    int numBlocks = Niveles(nivel);

		    int blockX = GAME_WIDTH - BLOCK_WIDTH;   
		    int initialBlockY = 100;  

		    for (int i = 0; i < numBlocks; i++) {
		        int blockY = initialBlockY + i * (BLOCK_HEIGHT + 30);  
		        block.add(new Block(blockX, blockY, BLOCK_WIDTH, BLOCK_HEIGHT));
		    }
		}
		public void NewPaddle()
		{
			paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		}
		public void NewBall()
		{
			random = new Random();
			ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
			
		}
		
		
		public void move()
		{
			paddle1.move();
			
			ball.move();
		}
		public void run()
		{
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
			 for (Block block : block) {
		            block.draw(g);
		        }
			ball.draw(g);
			score.draw(g);
			Toolkit.getDefaultToolkit().sync(); 
		}
		public void CheckCollision()
		{
						
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
				
				NewPaddle();
				NewBall();
				System.out.println("Player 2: "+score.player2);
			}
			if(paddle1.y<=0)
				paddle1.y=0;
			if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
				paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
			
			 for (Block block : block) {
		            if (ball.intersects(block)) {
		            	score.increaseScore();
		                block.x += 3000; 
		                ball.setYDirection(-ball.yVelocity); 
		            }
		        }
			 
			 if (levelCompleted()) {
			        currentLevelIndex++;
			        if (currentLevelIndex <= 3) {  // Agrega más niveles según sea necesario
			            NewBlock(currentLevelIndex);
			        } else {
			            // Has completado todos los niveles, puedes manejar la lógica de juego terminado aquí
			        }
			    }
			
			
		}	
		public class AL extends KeyAdapter
		{
			public void keyPressed(KeyEvent e)
			{
				paddle1.keyPressed(e);
				
			}
			public void keyReleased(KeyEvent e)
			{
				paddle1.keyReleased(e);
				
			}
		}
		private boolean levelCompleted() {
		    // Lógica para determinar si el nivel actual ha sido completado
		    // Puedes definir tus propios criterios para esto
		    return false;
		}
		
		

		
		
}
