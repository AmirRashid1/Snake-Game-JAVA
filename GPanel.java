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
import java.util.TimerTask;

import javax.swing.JPanel;

public class GPanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH=600;
	static final int SCREEN_HEIGHT=600;
	static final int UNITS=25;
	static final int GAME_UNITS=(SCREEN_WIDTH * SCREEN_HEIGHT)/UNITS;
	static final int DELAY=90;
	final int x[]=new int[GAME_UNITS];
	final int y[]=new int[GAME_UNITS];
	int bodyParts=6;
	int appleEaten;
	int applex;
	int appley;
	char direction ='R';
	boolean running=false;
	Timer timer;
	Random random;
	
	GPanel(){
		random =new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
		
	}
	
	public void startGame() {
		newApple();
		running=true;
		timer=new Timer();
		 timer.scheduleAtFixedRate(new TimerTask() {
		        @Override
		        public void run() {
		            actionPerformed(null);
		        }
		    }, 0, DELAY);
		
		
	}
		
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {
			//this is used for to to make grid in my project
			/*for(int i=0;i<SCREEN_HEIGHT/UNITS;i++) {
				g.drawLine(i*UNITS, 0, i*UNITS, SCREEN_HEIGHT);
				g.drawLine(0, i*UNITS, i*SCREEN_WIDTH, i*UNITS);
			}
			*/
			g.setColor(Color.red);
			
			g.fillOval(applex, appley, UNITS, UNITS);
			
			for(int i=0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNITS,UNITS);
				}else {
//					g.setColor(new Color(45,180,0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255) ));
					g.fillRect(x[i], y[i], UNITS,UNITS);
				}
				g.setColor(Color.red);
				g.setFont(new Font("Ink Free",Font.BOLD,40));
				FontMetrics matrics=getFontMetrics(g.getFont());
				g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - matrics.stringWidth("Score: "+appleEaten))/2,g.getFont().getSize());
			
			}
		}else {
			gameOver(g);
		}
	}
	
	public void newApple() {
		applex=random.nextInt((int)(SCREEN_WIDTH/UNITS))*UNITS;
		appley=random.nextInt((int)(SCREEN_HEIGHT/UNITS))*UNITS;
	}
	
	public void move() {
		for(int i=bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0]=y[0]-UNITS;
			break;
			
		case 'D':
			y[0]=y[0]+UNITS;
			break;
			
		case 'L':
			x[0]=x[0]-UNITS;
			break;
			
		case 'R':
			x[0]=x[0]+UNITS;
			break;	
			
		}
		
		
	}

	public void checkApple() {
		if((x[0]==applex) && (y[0]==appley)) {
			bodyParts++;
			appleEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		// checks if head collids with body
		for(int i=bodyParts;i>0;i--) {
			if((x[0]==x[i]) && (y[0]==y[i])) {
				running=false;
			}
		}
			//checks if head touches left border
			if(x[0]<0) {
				running=false;
			}
			
			//checks if head touches right border
			if(x[0]> SCREEN_WIDTH) {
				running=false;
			}
			
			//checks if head touches top border
			if(y[0]< 0) {
				running=false;
			}
			
			//checks if head touches bottom border
			if(y[0]> SCREEN_HEIGHT) {
				running=false;
			}
			
			if(!running) {
				timer.cancel();
			}
	}
	
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics matrics1=getFontMetrics(g.getFont());
		g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - matrics1.stringWidth("Score: "+appleEaten))/2,g.getFont().getSize());
	
		
		//Game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics matrics2=getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - matrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
		
//		 JButton tryAgainButton = new JButton("Try Again");
//		    tryAgainButton.addActionListener(new ActionListener() {
//		        public void actionPerformed(ActionEvent e) {
//		            // Reset the game
//		            resetGame();
//		        }
//		    });
//		    add(tryAgainButton);
//		    tryAgainButton.setLocation((SCREEN_WIDTH - tryAgainButton.getWidth()) / 2, SCREEN_HEIGHT / 2 + 50);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
					direction='L';
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(direction!='L') {
					direction='R';
				}
				break;
				
			case KeyEvent.VK_UP:
				if(direction!='D') {
					direction='U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(direction!='U') {
					direction='D';
				}
				break;
				
			}
		}
	}
}
