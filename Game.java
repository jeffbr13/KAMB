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
    int attack = 25;
    int lastClicked, hoveredOver = 25;
    boolean enabled = true;
    BufferedImage bufferedImage;
    int draw = 0;

    Universe universe;

    private Fleet fleet1;

    
    public Game()
    {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setOpaque(true);
        setPreferredSize(screenSize);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.universe = new Universe(screenSize.width, screenSize.height, 12, 50);
        
        this.fleet1 = new Fleet(universe.getPlanets().get(1).getX(),universe.getPlanets().get(1).getY(),universe.getPlanets().get(2).getXCenter(),universe.getPlanets().get(2).getYCenter());
        universe.addFleet(fleet1);

    }


    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.drawImage(this.universe.getBackground(), 0, 0, null);
        BufferedImage b;
        try {
            File f = new File("resources/images/fleets/fleet1.png");
            b = ImageIO.read(f);
            bufferedImage=GamePiece.resize(b, 5);
            } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
           
        setOpaque(true);
        // draw all planets
        ArrayList<Planet> planets = this.universe.getPlanets();
        for (int i=0; i < planets.size(); i++) {
            Planet p = planets.get(i);
            g2.drawImage(p.getImage(), p.getX(), p.getY(), null);
        }

        g2.drawImage(bufferedImage, (int)fleet1.getXDouble(),(int)fleet1.getYDouble(), null);
        g2.drawImage(bufferedImage, universe.getPlanets().get(2).getX(),universe.getPlanets().get(2).getY(), null);
        
        
        if(draw == 1) g2.drawOval(200,200,400,400);
        /* draw all fleets
        ArrayList<Fleet> fleets = this.universe.getFleets();
        for (int i=0; i < fleets.size(); i++) {
            Fleet f = fleets.get(i);
            g2.drawImage(f.getImage(), f.getX(), f.getY(), null);
        }
        
         * generates 2 fleets by hand for planet1 and planet2
         */

        /* highlighting planets based on values from mousePressed and mouseMoved
         * showing the line of attack
    	 *  
    	 * Matej
    	 */
       
        for(int i=0; i<universe.getPlanets().size(); i++)
        {
        	if(lastClicked == i)
        	{
        		g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(10F));
                g2.drawOval(universe.getPlanets().get(i).getX(), universe.getPlanets().get(i).getY(), universe.getPlanets().get(i).radius*2, universe.getPlanets().get(i).radius*2);
                for(int j=0;j<universe.getPlanets().size(); j++)
                {
                	if(hoveredOver == j)
                	{
                		g2.setColor(Color.BLUE);
                        g2.setStroke(new BasicStroke(10F));
                        g2.drawOval(universe.getPlanets().get(j).getX(), universe.getPlanets().get(j).getY(), universe.getPlanets().get(j).radius*2, universe.getPlanets().get(j).radius*2);
                        
                        g2.setColor(Color.red);
                        g2.drawLine(universe.getPlanets().get(i).getXCenter(), universe.getPlanets().get(i).getYCenter(), universe.getPlanets().get(j).getXCenter(), universe.getPlanets().get(j).getYCenter());
                	}
                }
        	}
        }
        
               
        /* highlighting planets based on values from mousePressed
    	 *  
    	 * Matej
    	 */
        for(int i=0; i<universe.getPlanets().size(); i++)
        {
        	if(lastClicked == i)
        	{
        		g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(10F));
                g2.drawOval(universe.getPlanets().get(i).getX(), universe.getPlanets().get(i).getY(), universe.getPlanets().get(i).radius*2, universe.getPlanets().get(i).radius*2);
        	}
        }
          
        g2.dispose();
    }

    public void run()
    {
    	while(!(universe.getPlanets().get(2).isCoordinateInside(fleet1.getXDouble(),fleet1.getYDouble())))
    		{
    		fleet1.update();
            try { Thread.sleep(50); }
            catch (InterruptedException e) {
                System.out.println("error");

            }
            repaint();
    		System.out.println(fleet1.x + ", " + fleet1.y);
    		}
    	
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
    
        for(int i=0; i<universe.getPlanets().size(); i++)
        {
        	if((universe.getPlanets().get(i).isCoordinateInside(mx, my)) && enabled==true) 
        	{
        		if(lastClicked == 1)
                {
                	if((universe.getPlanets().get(2).isCoordinateInside(mx, my)) && enabled==true) 
                	{
                		attack = 2;
                		animThread = new Thread(this);
                        animThread.start();
               	        enabled = false;
               	     System.out.println("coordinates of planet "+ i + ": " + universe.getPlanets().get(2).getX() +" " + universe.getPlanets().get(2).getX());
                	}
                }
        		else
        		{
        			lastClicked = i;
        			System.out.println("coordinates of planet "+ i + ": " + universe.getPlanets().get(i).getX() +" " + universe.getPlanets().get(i).getX());
        		}
        				
        	}
        }     
        
        System.out.println("lastCLicked: " + lastClicked);
        System.out.println("attack: " + attack);
    
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
		
		if(enabled==true)
		{
		for(int i=0; i<universe.getPlanets().size(); i++)
	    	{
	        	if((universe.getPlanets().get(i).isCoordinateInside(mouseX, mouseY))) hoveredOver = i;
	        }
        
        repaint();
		}
	}

}
