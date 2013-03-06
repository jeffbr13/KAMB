import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

/* Main class creates MainWindow JFrame fit to the screen size
 * next step is to add a GamePanel which is in this case new Game class
 * This class also introduces Runnable interface so the thread could be run later
 * 
 * Matej
 */
public class Main
{
	JFrame      mainWindow;
	Game		GamePanel;
	int Action = 0;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Main() {
		mainWindow = new JFrame("KAMB");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setResizable(false);
		
		GamePanel = new Game();
		mainWindow.getContentPane().add(GamePanel);
        
		mainWindow.pack();
		mainWindow.setSize(screenSize);
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
