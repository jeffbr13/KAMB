import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class Main extends JFrame 
{
	static int x = 100;
	int y = 150;

	JFrame window;
	BufferedImage img;

	class fleet extends JPanel
	{
		JFrame window;
		BufferedImage img;

	    public fleet() 
	    {
	        img = new BufferedImage(1000, 200, BufferedImage.OPAQUE);
	        window = new JFrame() 
	        {
	            public void paint(Graphics g) 
	            {
	                g.drawImage(img, 0, 0, rootPane);
	            }
	        };

	        window.setSize(1000,200);
	        window.setLocationRelativeTo(null);
	        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        window.setVisible(true);
	        animate();
	    }

	    public void animate() 
	    {
	        Graphics g = img.getGraphics();
	        for (int i = 0; i < 640; i += 10) 
	        {
	            g.setColor(Color.BLACK);
	            g.fillRect(0, 0, 1000, 200);
	            g.setColor(Color.red);
	            g.fillOval(i, 100, 20, 20);
	            window.repaint();
	            try 
	            {
	                Thread.sleep(500);
	            } catch (InterruptedException ex) 
	            {
	                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
  }

public Main() 
{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(screenSize.width,screenSize.height);
    setTitle("Project_KAMB");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    JLayeredPane lp = getLayeredPane();
    
    //galaxy background
    lp.setPreferredSize(new Dimension(screenSize.width,screenSize.height));
    JPanel p1 = new JPanel();
    p1.setLayout(new BorderLayout());
    p1.setBounds(0, 0, screenSize.width,screenSize.height);
    p1.setOpaque(true);
    JLabel background=new JLabel(new ImageIcon("resources/images/backgrounds/galaxy1.jpg"));
    p1.add(background, BorderLayout.NORTH);
    lp.add(p1, new Integer(1));
    
    //planet1
    JPanel p2 = new JPanel();
    p2.setBounds(200, 100, 300,300);
    p2.setOpaque(false);
    JLabel planet1=new JLabel(new ImageIcon("resources/images/planets/planet1.png"));
    p2.add(planet1, BorderLayout.NORTH);
    lp.add(p2, new Integer(2));
    
    //planet2
    JPanel p3 = new JPanel();
    p3.setBounds(600, 100, 800,300);
    p3.setOpaque(false);
    JLabel planet2=new JLabel(new ImageIcon("resources/images/planets/planet2.png"));
    p3.add(planet2, BorderLayout.NORTH);
    lp.add(p3, new Integer(2));
    
    //fleet1
    JPanel p4 = new JPanel();
    p4.setBounds(80, 100, 800,300);
    p4.setOpaque(false);
    JLabel fleet=new JLabel(new ImageIcon("resources/images/fleets/fleet1.png"));
    p4.add(fleet, BorderLayout.NORTH);
    lp.add(p4, new Integer(3));
    
    //fleet2
    JPanel p5 = new JPanel();
    p5.setBounds(500, 300, 800,300);
    p5.setOpaque(false);
    JLabel fleet2=new JLabel(new ImageIcon("resources/images/fleets/fleet1.png"));
    p5.add(fleet2, BorderLayout.NORTH);
    lp.add(p5, new Integer(3));
    
    
//   JPanel fleet=new fleet();
//    fleet.setLayout(new BorderLayout());
//    fleet.setBounds(100, 0, 1000,200);
//    lp.add(fleet, new Integer(3));


    setVisible(true);
  }
	
  
	public static void main(String[] args) 
	{
		new Main();
		Planet planet1 = new Planet(200, 100, 100);
		Planet planet2 = new Planet(600, 100, 100);
		Fleet fleet1 = new Fleet(planet1.x, planet1.y, planet2.x, planet2.y);
		Fleet fleet2 = new Fleet(planet2.x, planet2.y, planet1.x, planet1.y);
			
	}
  
}
