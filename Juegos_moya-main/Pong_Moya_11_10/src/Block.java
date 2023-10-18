import java.awt. *;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;




public class Block extends Rectangle{
	
	Block(int x, int y, int BLOCK_WIDTH, int BLOCK_HEIGHT)
	{
		super(x,y,BLOCK_WIDTH,BLOCK_HEIGHT);			
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.orange);
		g.fillRect(x,y,height,width);
	}
		
	}


