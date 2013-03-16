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
import javax.swing.JLabel;


public class Game extends JComponent implements Runnable, MouseListener, MouseMotionListener
{
    Thread animThread;
    int mouseX, mouseY;
    int attack = 25;
    int lastClicked, hoveredOver = 25;
    boolean enabled = true;
    boolean fleet = false;
    BufferedImage bufferedImage;
    int draw = 0;

    private Universe universe;
    private Player[] players;


    /**
     * Run a game with 2 players
     */
    public Game()
    {
        this(2);
    }

    public Game(int numberOfPlayers)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setOpaque(true);
        setPreferredSize(screenSize);
        addMouseListener(this);
        addMouseMotionListener(this);

        // create a universe with 12 planets and a minimum separation of 50 units
        this.universe = new Universe(screenSize.width, screenSize.height, 12, 50);

        // add a human player, then computer players.
        this.players = new Player[numberOfPlayers];
        this.players[0] = new Player(1);
        this.universe.addPlayer(this.players[0]);

        for (int i=1; i < numberOfPlayers; i++) {
            players[i] = new ComputerPlayer(i+1);
            this.universe.addPlayer(this.players[i]);
        }

        // get the Universe to set up the players with planets, etc.
        this.universe.setUpPlayers();
    }



    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.drawImage(this.universe.getBackground(), 0, 0, null);
        BufferedImage b;
        try {
            File f = new File("resources/images/fleets/fleet1.png");
            b = ImageIO.read(f);
            bufferedImage=GamePiece.resize(b, 10);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        setOpaque(true);
        // draw all planets
        Planet[] planets = this.universe.getPlanets();
        for (int i=0; i < planets.length; i++) {
            Planet p = planets[i];
            g2.drawImage(p.getImage(), p.getX(), p.getY(), null);
            /*
             * each planet has number of ships next to it, so we can keep track 
             */
            g2.setColor(Color.YELLOW);
            g2.drawString("S: "+ p.getPlayerShips(p.getControllingPlayer()), p.getX(), p.getY());

            g2.drawString("R: "+ p.getResourceValue(), p.getX() + 2*p.getRadius(), p.getY());

        }

        if (fleet == true) g2.drawImage(bufferedImage, (int)fleet1.getXDouble(),(int)fleet1.getYDouble(), null);
        g2.drawImage(bufferedImage, universe.getPlanets()[2].getX(),universe.getPlanets()[2].getY(), null);
        /*
         * fleet generated by hand has number of ships 100 written next to it, so we can keep track 
         */
        g2.setColor(Color.YELLOW);
        if (fleet == true) g2.drawString(""+fleet1.getShips(), (int)fleet1.getXDouble(), (int)fleet1.getYDouble());

        if(draw == 1) g2.drawOval(200,200,400,400);
        /* draw all fleets
        ArrayList<Fleet> fleets = this.universe.getFleets();
        for (int i=0; i < fleets.size(); i++) {
            Fleet f = fleets.get(i);
            g2.drawImage(f.getImage(), f.getX(), f.getY(), null);
            g2.drawString(""+f.getShips(), f.getX(), f.getY());
        }

         * generates 2 fleets by hand for planet1 and planet2
         */

        /* highlighting planets based on values from mousePressed and mouseMoved
         * showing the line of attack
         *  
         * Matej
         */

        for(int i=0; i<universe.getPlanets().length; i++)
        {
            if(lastClicked == i)
            {
                g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(10F));
                g2.drawOval(universe.getPlanets()[i].getX(), universe.getPlanets()[i].getY(), universe.getPlanets()[i].radius*2, universe.getPlanets()[i].radius*2);
                for(int j=0;j<universe.getPlanets().length; j++)
                {
                    if(hoveredOver == j)
                    {
                        g2.setColor(Color.BLUE);
                        g2.setStroke(new BasicStroke(10F));
                        g2.drawOval(universe.getPlanets()[j].getX(), universe.getPlanets()[j].getY(), universe.getPlanets()[j].radius*2, universe.getPlanets()[j].radius*2);

                        g2.setColor(Color.red);
                        g2.drawLine(universe.getPlanets()[i].getXCenter(), universe.getPlanets()[i].getYCenter(), universe.getPlanets()[j].getXCenter(), universe.getPlanets()[j].getYCenter());
                    }
                }
            }
        }


        /* highlighting planets based on values from mousePressed
         *  
         * Matej
         */
        for(int i=0; i<universe.getPlanets().length; i++)
        {
            if(lastClicked == i)
            {
                g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(10F));
                g2.drawOval(universe.getPlanets()[i].getX(), universe.getPlanets()[i].getY(), universe.getPlanets()[i].radius*2, universe.getPlanets()[i].radius*2);
            }
        }

        g2.dispose();
    }

    public void run()
    {
        while(!(universe.getPlanets()[attack].isCoordinateInside((int)fleet1.getXDouble(),(int)fleet1.getYDouble())))
        {
            //System.out.print(fleet1.getXDouble() + ", " + fleet1.getYDouble() +"\n   ");
            fleet1.update();
            try { Thread.sleep(10); }
            catch (InterruptedException e) {
                System.out.println("error");

            }
            repaint();
            System.out.println(fleet1.getXDouble() + ", " + fleet1.getYDouble()+"\n");
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

        for(int i=0; i<universe.getPlanets().length; i++)
        {
            if((universe.getPlanets()[i].isCoordinateInside(mx, my)) && enabled==true) 
            {
                if(lastClicked == 1)
                {
                    attack = i;
                    this.fleet1 = this.player1.createFleet(universe.getPlanets()[1].getX(),universe.getPlanets()[1].getY(),universe.getPlanets()[i].getXCenter(),universe.getPlanets()[i].getYCenter());                        
                    animThread = new Thread(this);
                    animThread.start();
                    enabled = false;
                    fleet = true;
                    System.out.println("coordinates of planet "+ i + ": " + universe.getPlanets()[i].getX() +" " + universe.getPlanets()[i].getX());
                }
                else
                {
                    lastClicked = i;
                    System.out.println("coordinates of planet "+ i + ": " + universe.getPlanets()[i].getX() +" " + universe.getPlanets()[i].getX());
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
            for(int i=0; i<universe.getPlanets().length; i++)
            {
                if((universe.getPlanets()[i].isCoordinateInside(mouseX, mouseY))) hoveredOver = i;
            }

            repaint();
        }
    }

}
