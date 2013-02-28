import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;


public class Main
{
	JFrame      mainWindow;
	Game		GamePanel;
	int Action = 0;
	
	public Main() {
		mainWindow = new JFrame("KAMB");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setResizable(false);
		
		GamePanel = new Game();
		mainWindow.getContentPane().add(GamePanel);
        
		mainWindow.pack();
		mainWindow.setVisible(true);
	}
 
	public static void main(String[] args) 
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
					
	}  
}
