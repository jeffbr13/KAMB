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
  
@SuppressWarnings("serial")  
public class Main extends JFrame { 
	int x = 200;
    int y = 150;
  public void paintComponent(Graphics g) {
    g.setColor(Color.blue);
    g.fillOval(100, 100, 100, 100);
    g.setColor(Color.red);
    g.fillOval(700, 100, 100, 100);
    g.setColor(Color.gray);
    g.fillRect(x, y, 10, 10);  
 
  }
  JFrame window;
  BufferedImage img;
  
  class fleet extends JPanel
  {
	  JFrame window;
	  BufferedImage img;
	 
	    public fleet() {
	        img = new BufferedImage(1000, 200, BufferedImage.OPAQUE);
	        window = new JFrame() {
	 
	            public void paint(Graphics g) {
	                g.drawImage(img, 0, 0, rootPane);
	            }
	        };
	 
	        window.setSize(screenSize.width,screenSize.height);
	        window.setLocationRelativeTo(null);
	        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
	        window.setVisible(true);
	        animate();
	    }
	 
	    public void animate() {
	        Graphics g = img.getGraphics();
	        for (int i = 0; i < 640; i += 10) {
	            g.setColor(Color.BLACK);
	            g.fillRect(0, 0, 1000, 200);
	            g.setColor(Color.red);
	            g.fillOval(i, 100, 20, 20);
	            window.repaint();
	            try {
	                Thread.sleep(500);
	            } catch (InterruptedException ex) {
	                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
  }
  
  class DrawPane extends JPanel{
    public void paintComponent(Graphics g){
        g.setColor(Color.blue);
        g.fillOval(100, 100, 100, 100);
        g.setColor(Color.red);
        g.fillOval(900, 100, 100, 100);
     }
 }
  
  	public Main() { 
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(screenSize.width,screenSize.height);  
    setDefaultCloseOperation(EXIT_ON_CLOSE);  
    JLayeredPane lp = getLayeredPane();  
      
    //galaxy background  
    lp.setPreferredSize(new Dimension(screenSize.width,screenSize.height));  
    JPanel p1 = new JPanel();  
    p1.setLayout(new BorderLayout());  
    p1.setBounds(0, 0, screenSize.width,screenSize.height);  
    JLabel background=new JLabel(new ImageIcon("C:\\Documents\\school\\UoE\\inf1\\oop\\project KAMB\\galaxy.jpg")); 
    p1.setBackground(Color.red);  
    p1.add(background, BorderLayout.NORTH);  
    lp.add(p1, new Integer(1));  
      
    //planets  
    JPanel planet=new DrawPane();
    planet.setLayout(new BorderLayout());  
    planet.setBounds(100, 0, 1000,200); 
    lp.add(planet, new Integer(2));  
    
   // JPanel fleet=new fleet();
    //fleet.setLayout(new BorderLayout());  
    //fleet.setBounds(100, 0, 1000,200); 
    //lp.add(fleet, new Integer(3));  
      
      
    setVisible(true);  
  }  
  
  public static void main(String[] args) {  
    new Main();  
   
      
  }  
}  