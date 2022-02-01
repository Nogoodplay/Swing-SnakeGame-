package SNakeg;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.*;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Random;

public class Gamepanel extends JPanel implements ActionListener{
	
	
	static final int SCREEN_WIDTH =600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNIT =(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY =75;
	final int x[]=new int[GAME_UNIT];
	final int y[]=new int[GAME_UNIT];
	int bodyParts =6;
	int applesEatern;
	int appleX;
	int appleY;
	char direction='R';
	boolean running=false;
	Timer timer;
	Random random;
	
	

	Gamepanel(){
		random=new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	public void startGame() {
		newapple();
		running = true;
		timer=new Timer(DELAY,this);
		timer.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {
		if(running) {
//			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
//				g.drawLine(i*UNIT_SIZE,0 , i*UNIT_SIZE, SCREEN_HEIGHT);
//				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//			}
			//apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			//snake
			for(int i=0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.GREEN);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}else {
//					g.setColor(new Color(45,180,0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				g.setColor(Color.BLUE);
				g.setFont(new Font("Ink Free",Font.BOLD,50));
				FontMetrics metrics1=getFontMetrics(g.getFont());
				g.drawString("SCORE : "+applesEatern, (SCREEN_WIDTH - metrics1.stringWidth("SCORE : "+applesEatern))/2, g.getFont().getSize());
			}
		}else {
			gameover(g);
		}
		
	}
	public void move() {
		for(int i=bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0]=y[0]-UNIT_SIZE;
			break;
		case 'D':
			y[0]=y[0]+UNIT_SIZE;
			break;
		case 'L':
			x[0]=x[0]-UNIT_SIZE;
			break;
		case 'R':
			x[0]=x[0]+UNIT_SIZE;
			break;
		}
	}
	public void newapple() {
		appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void checkapple() {
		if((x[0]==appleX)&&(y[0]==appleY)) {
			bodyParts++;
			applesEatern++;
			newapple();
		}
		
	}
	public void checkcollision() {
		for(int i=bodyParts;i>0;i--) {
			if((x[0]==x[i] && y[0]==y[i])) {
				running=false;
			}
		}
		
		//stop when it touch left side
		if(x[0]<0) {
			running=false;
		}
		//stop when it touch right side
		if(x[0]>SCREEN_WIDTH) {
			running=false;
		}
		//stop when it touch up side
		if(y[0]<0) {
			running=false;
		}
		//stop when it touch down side
		if(y[0]>SCREEN_HEIGHT) {
			running=false;
		}
		if(running==false) {
			timer.stop();
		}
	}
	public void gameover(Graphics g) {
		g.setColor(Color.BLUE);
		g.setFont(new Font("Ink Free",Font.BOLD,70));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
		
		g.setColor(Color.BLUE);
		g.setFont(new Font("Ink Free",Font.BOLD,50));
		FontMetrics metrics1=getFontMetrics(g.getFont());
		g.drawString("SCORE : "+applesEatern, (SCREEN_WIDTH - metrics1.stringWidth("SCORE : "+applesEatern))/2, g.getFont().getSize());
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running){
			move();
			checkapple();
			checkcollision();
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
