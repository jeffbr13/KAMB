import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class Game extends JComponent implements Runnable, MouseListener, MouseMotionListener
{
    Thread animThread;
    int mouseX, mouseY;
    int lastClicked, hoveredOver = 0;
    boolean enabled = true;

    Universe universe;


    private Planet planet1;
    private Planet planet2;
    private Planet planet3;

    private Fleet fleet1;
    private Fleet fleet2;
    
    public Game()
    {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setOpaque(true);
        setPreferredSize(screenSize);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.universe = new Universe(screenSize.width, screenSize.height, 3, 500);
        
        
        this.planet1 = new Planet (0,0,100);
        universe.addPlanet(planet1);
        this.planet2 = new Planet (600,400,100);
        universe.addPlanet(planet2);
        this.planet3 = new Planet (300,0,100);
        universe.addPlanet(planet3);
       
        
        this.fleet1 = new Fleet(0,0,600,400);
        universe.addFleet(fleet1);
        this.fleet2 = new Fleet(600,400,0,0);
        universe.addFleet(fleet2);


        animThread = new Thread(this);
        animThread.start();
    }


    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.drawImage(this.universe.getBackground(), 0, 0, null);
        
        g2.drawImage(planet1.getImage(), planet1.getX(), planet1.getY(), null);
        g2.drawImage(planet2.getImage(), planet2.getX(), planet2.getY(), null);
  //      g2.drawImage(fleet1.getImage(), fleet1.getX(), fleet1.getY(), null);
//        g2.drawImage(fleet2.getImage(), fleet2.getX(), fleet2.getY(), null);

        // draw all planets
        ArrayList<Planet> planets = this.universe.getPlanets();
        for (int i=0; i < planets.size(); i++) {
            Planet p = planets.get(i);
            g2.drawImage(p.getImage(), p.getX(), p.getY(), null);
        }

        // draw all fleets
        ArrayList<Fleet> fleets = this.universe.getFleets();
        for (int i=0; i < fleets.size(); i++) {
            Fleet f = fleets.get(i);
            g2.drawImage(f.getImage(), f.getX(), f.getY(), null);
        }

       
        if (lastClicked == 1)
        {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet1.getX(), planet1.getY(), 200, 200);

        }
        if (lastClicked == 2) 
        {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet2.getX(), planet2.getY(), 200, 200);

        }
        if (lastClicked == 3) 
        {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet3.getX(), planet3.getY(), 200, 200);

        }
        
        /* highlighting planets based on values from mousePressed and mouseMoved
    	 *  
    	 * Matej
    	 */
        
        if (lastClicked==2 && hoveredOver == 1)
        {
        	g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet2.getX(), planet2.getY(), 200, 200);
            
            g2.setColor(Color.red);
            g2.drawLine(planet2.getXCenter(), planet2.getYCenter(), planet1.getXCenter(), planet1.getYCenter());

        	g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet1.getX(), planet1.getY(), 200, 200);
        }
        
        if (lastClicked==2 && hoveredOver == 3)
        {
        	g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet2.getX(), planet2.getY(), 200, 200);

            g2.setColor(Color.red);
            g2.drawLine(planet2.getXCenter(), planet2.getYCenter(), planet3.getXCenter(), planet3.getYCenter());
            
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet3.getX(), planet3.getY(), 200, 200);
        }
        
        if (lastClicked==1 && hoveredOver == 2)
        {
        	g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet1.getX(), planet1.getY(), 200, 200);
        	
            g2.setColor(Color.red);
            g2.drawLine(planet1.getXCenter(), planet1.getYCenter(), planet2.getXCenter(), planet2.getYCenter());
            
        	g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet2.getX(), planet2.getY(), 200, 200);
        }
        
        if (lastClicked==1 && hoveredOver == 3)
        {
        	g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet1.getX(), planet1.getY(), 200, 200);
        	
            g2.setColor(Color.red);
            g2.drawLine(planet1.getXCenter(), planet1.getYCenter(), planet3.getXCenter(), planet3.getYCenter());
            
        	g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet3.getX(), planet3.getY(), 200, 200);
        }
        
        if (lastClicked==3 && hoveredOver == 2)
        {
        	g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet3.getX(), planet3.getY(), 200, 200);
        	
            g2.setColor(Color.red);
            g2.drawLine(planet3.getXCenter(), planet3.getYCenter(), planet2.getXCenter(), planet2.getYCenter());
            
        	g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet2.getX(), planet2.getY(), 200, 200);
        }
        
        if (lastClicked==3 && hoveredOver == 1)
        {
        	g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet3.getX(), planet3.getY(), 200, 200);
        	
            g2.setColor(Color.red);
            g2.drawLine(planet3.getXCenter(), planet3.getYCenter(), planet1.getXCenter(), planet1.getYCenter());
            
        	g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet1.getX(), planet1.getY(), 200, 200);
        }
     
        g2.dispose();
    }

    
    /**
     * Edited by KM
     
    public void run() {
        for(int i=0; i<100; i++) {
            fleet2.setX( (int) (fleet2.getX() + (double) (fleet2.getDestinationX() - fleet2.getStartX()) * fleet2.getFrame()));
            fleet2.setY( (int) (fleet2.getY() + (double) (fleet2.getDestinationY() - fleet2.getStartY()) * fleet2.getFrame()));

            try { Thread.sleep(50); }
            catch (InterruptedException e) {
                System.out.println("error");

            }
            repaint();
        }
    }
    */
    
    public void run(){
    	
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /* mousePressed is checking coordinates of mouse whenever it was pressed
	 * in this case whether the cursor is inside of either planet, calls appropriate action
	 *  
	 * Matej
	 */
    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
    /*
        if (enabled==true && lastClicked == 2)
        {
        	if((planet1.isCoordinateInside(mx, my))) 
        		{
        	        animThread = new Thread(this);
        	     //   animThread.start();
        	        lastClicked = 0;
        	     //   enabled = false;
        	        
        		}
        }
        
        if (enabled==true && lastClicked == 1)
        {
        	if((planet2.isCoordinateInside(mx, my))) 
        		{
        	        animThread = new Thread(this);
        	       // animThread.start();
        	        lastClicked = 0;
        	      //  enabled = false;
        		}
        }
        
        if (fleet2.getX()==fleet2.getDestinationX() && fleet2.getY()==fleet2.getDestinationY()) enabled = true;
        if (fleet1.getX()==fleet1.getDestinationX() && fleet1.getY()==fleet1.getDestinationY()) enabled = true;
		*/
        if((planet2.isCoordinateInside(mx, my)) && enabled==true) lastClicked = 2;

        if((planet1.isCoordinateInside(mx, my)) && enabled==true) lastClicked = 1;

        if((planet3.isCoordinateInside(mx, my)) && enabled==true) lastClicked = 3;

        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }



	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	
	/* mouseMoved is checking coordinates of mouse whenever it was moved
	 * in this case whether the cursor is inside of either planet , calls appropriate action
	 * 
	 * Matej
	 */
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if((planet1.isCoordinateInside(mouseX, mouseY))) 
		{
			hoveredOver = 1;
		}
        if((planet2.isCoordinateInside(mouseX, mouseY)))
        {
        	hoveredOver = 2;
        }
        if((planet3.isCoordinateInside(mouseX, mouseY)))
        {
        	hoveredOver = 3;
        }
       
        
        repaint();
		
	}

}
