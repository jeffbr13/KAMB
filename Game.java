import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class Game extends JComponent implements Runnable, MouseListener
{
    BufferedImage background, fleetPic;
    Thread animThread;
    int Action = 0;

    Planet planet1 = new Planet (0,0,200);
    Planet planet2 = new Planet (600,400,200);

    int planet1x = planet1.x;
    int planet1y = planet1.y;
    int planet2x = planet2.x;
    int planet2y = planet2.y;

    Fleet fleet1 = new Fleet(0,0,600,400);
    Fleet fleet2 = new Fleet(600,400,0,0);

    int x1 = fleet1.getX();
    int y1 = fleet1.getY();
    int x2 = fleet2.getX();
    int y2 = fleet2.getY();

    public Game()
    {
        try 
        {
            background = ImageIO.read(new File("resources/images/backgrounds/galaxy1.jpg"));
            fleetPic = ImageIO.read(new File("resources/images/fleets/fleet1.png"));
        } catch (IOException e) { }
        
        

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setOpaque(true);
        setPreferredSize(screenSize);
        addMouseListener(this);

        animThread = new Thread(this);
        animThread.start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // sets the game interface
        g2.drawImage(background, 0, 0, null);
        g2.drawImage(planet1.getImage(), planet1x, planet1y, null);
        g2.drawImage(planet2.getImage(), planet2x, planet2y, null);
        g2.drawImage(fleetPic, x1, y1, null);
        g2.drawImage(fleetPic, x2, y2, null);

        if (Action ==1)
        {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet1x+50, planet1y+50, 200, 200);

        }
        if (Action ==2) 
        {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(10F));
            g2.drawOval(planet2x+50, planet2y+50, 200, 200);

        }
        g2.dispose();
    }

    public void run() {
        for(int i=0; i<1000; i++) {
            x2 += (double) (fleet2.destinationX - fleet2.startX) * fleet2.frame;
            y2 += (double) (fleet2.destinationY - fleet2.startY) * fleet2.frame;

            try { Thread.sleep(50); }
            catch (InterruptedException e) {
                System.out.println("error");

            }
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if((planet1.isCoordinateInside(mx -100, my-100))) Action = 1;
        if((planet2.isCoordinateInside(mx -100, my-100))) Action = 2;
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

}
