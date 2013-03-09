import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;


/**
 * The Planet class is constructed with the x and y coordinates, as well as a radius.
 */
public class Planet extends GamePiece
{

    //Center coordinates.
    private int xCenter,yCenter;

    public int radius;
    public int ships;



    //Number of planet images we have.
    public static int imagesNum=18;
    //The planet images.
    public static BufferedImage[] images;

    //Min and Max radius
    public static int minRadius;
    public static int maxRadius;


    //Randomness.
    private static Random random=new Random();

    //Reads all the planet images from their image files.
    static
    {
        minRadius=Universe.getMinPlanetSize();
        maxRadius=Universe.getMaxPlanetSize();

        images=new BufferedImage [imagesNum];
        for(int i=1;i<=imagesNum;i++)
        {
            try
            {
                images[i-1] = ImageIO.read(new File("resources/images/planets/planet"+i+".png"));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public Planet(int x, int y)
    {
        this(x, y, Universe.randomBetween(minRadius,maxRadius));
    }

    public Planet(int x, int y, int radius)
    {
        this(x, y, radius, random.nextInt(imagesNum));
    }

    public Planet(int x, int y, int radius, int i)
    {
        this.x = x;
        this.y = y;
        xCenter=x+radius;
        yCenter=y+radius;
        this.radius = radius;
        image=resize(images[i],radius);
    }

    public int getXCenter()
    {
        return xCenter; 
    }
    public int getYCenter()
    {
        return yCenter;
    }


    /**
     * @return the radius of the planet.
     */
    public int getRadius()
    {
        return radius;
    }


    /**
     * @return the number of ships present on the planet.
     */
    public int getShips()
    {
        return ships;
    }

    /**
     * @return draw the planet.
     */
    /*public void draw(Graphics2D g2d)
    {
        g2d.drawImage(this.bufferedImage, this.getX(), this.getY(), null);
    }*/


    /* public void setImage(String path) {
        try {
            File f = new File(path);
            this.bufferedImage = ImageIO.read(f);
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    /**
     * @return a boolean stating whether or not the given coordinate is 'inside' the
     * planet. Can be used for testing mouse-click-selection.
     */

    public boolean isFarEnoughAwayFrom(Planet p, int distance) 
    {

        int dx = xCenter - p.getXCenter();
        int dy = yCenter - p.getYCenter();

        return (dx*dx)+(dy*dy)>=(distance+radius+p.radius)*(distance+radius+p.radius);
    }

    public boolean isCoordinateInside(int x, int y)
    {
        //Is the coordinate's distance from the center coordinate less than (or equal) the radius?
        return (xCenter-x)*(xCenter-x)+(yCenter-y)*(yCenter-y)<=radius*radius;
    }



    /**
     *  Update the ship counts due to battle, for one cycle.
     * 
     *  Each ship has a 0.5 chance of destroying an enemy ship/dealing one unit of damage.
     *  Damage is equally between players, simultaneously, every time this is generated.
     *  
     *  1 unit of damage ~= 1 enemy ship destroyed (rounded)
     */
    public void performBattle() {

        int[] damageDealtByPlayers = new int[this.getPlayer().length];

        // calculate the amount of damage/ships each player does in total 
        for (int player=0; player < this.getPlayers().length; player++) {
            // TODO: implement getPlayers: players present on planet

            for (int ship : this.getPlayerShips(player)) {
                if (Planet.random.nextBoolean()) damageDealtByPlayers[player] += 1;
            }
        }

        // for every player that isn't itself, set the damage dealt to it to be (damage done by other players / (total number of players - itself) ) 
        int[] damageDealtToPlayers = new int[this.getPlayer().length];
        for (int player=0; player < this.getPlayers().length; player++) {
            for (int playerDoingDamage=0; playerDoingDamage < this.getPlayers().length; playerDoingDamage++) {
                if (playerDoingDamage != player) {
                    damageDealtToPlayers[player] += (int) (damageDealtByPlayers[playerDoingDamage] / (this.getPlayers().length - 1) );
                }
            }
        }

        // set number of ships for every player to be last number - damage just done to player
        for (int i=0; i < this.getPlayers().length; i++) {
            Player p = this.getPlayers()[i]
                    int shipsLastCycle = this.getPlayerShips(p);
            this.setPlayerShips(p, shipsLastCycle - damageDealtToPlayers[i]);
        }
    }

    /**
     * @return the number of new ships generated this cycle
     */
    public int generateShips() {

        // TODO: ensure that there is only one player in charge.
        // TODO: this method
    }
}