import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
    public int planets;
    String rules, about;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Main() {
        mainWindow = new JFrame("KAMB");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        mainWindow.pack();
        mainWindow.setSize(screenSize);
        mainWindow.setVisible(true);
        
        ImageIcon logoImage = new ImageIcon("resources/images/backgrounds/KAMB1.png");
        final JLabel logo = new JLabel(logoImage);
        mainWindow.add(logo,BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        mainWindow.setJMenuBar(menuBar);
        
        JMenu Game1 = new JMenu("Game");
        JMenu Help = new JMenu("Help");
        menuBar.add(Game1);
        menuBar.add(Help);
        
        final JMenuItem newAction = new JMenuItem("New");
        JMenuItem exitAction = new JMenuItem("Exit");
        JMenuItem rulesAction = new JMenuItem("Rules");
        JMenuItem aboutAction = new JMenuItem("About");
                          
        Game1.add(newAction);
        Game1.add(exitAction);
        Help.add(rulesAction);
        Help.add(aboutAction);

        
        
        
        
        newAction.addActionListener(new ActionListener(){
            @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e)
            {
            	String input = JOptionPane.showInputDialog(mainWindow, "number of planets", "Enter number of planets", JOptionPane.PLAIN_MESSAGE);
            	planets = Integer.parseInt(input);
            	JOptionPane.showMessageDialog(mainWindow, "DONE", "Enjoy your game!", JOptionPane.PLAIN_MESSAGE);
            	System.out.println(planets);
            	
            	GamePanel = new Game();	
            	newAction.disable();
                mainWindow.getContentPane().add(GamePanel);
                mainWindow.pack();
                mainWindow.setSize(screenSize);                
            }
        });
        
        exitAction.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	System.exit(0);
            }
        });
        
        rulesAction.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	rules = "1) \n 2) \n 3) \n 4) \n 5) \n 6) \n 7) \n 8) \n"; 
            	JOptionPane.showMessageDialog(mainWindow,rules, "KAMB rules", JOptionPane.PLAIN_MESSAGE);
            }
        });
        
        aboutAction.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	about = "KAMB is a group project for OOP at The University of Edinburgh \n Team members: \n - Kim \n - Atanas \n - Matej \n - Ben \n \n HAVE FUN!!"; 
            	JOptionPane.showMessageDialog(mainWindow, about, "About KAMB", JOptionPane.PLAIN_MESSAGE);
            }
        });
        

       
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
