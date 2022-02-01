package SNakeg;

import javax.swing.JFrame;

public class Gameframe extends JFrame{
	Gameframe(){
		this.add(new Gamepanel());
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

}
