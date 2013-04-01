import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import java.util.*;
import java.awt.*;


/**
 * The Planet class is constructed with the x and y coordinates, as well as a radius.
 */
public class Planet extends GamePiece
{
	//Center coordinates.
	private int xCenter,yCenter;


	private int resources;
	private String name;
	private int attRadius;
	private Player capturer;
	private double percentCaptured;
	private int newShip;

	HashMap<Player, Integer> ships= new HashMap<Player, Integer>();

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

	public Planet(int x, int y, int radius, int j)
	{
		owner=null;
		capturer=null;
		newShip=0;
		attRadius=radius*5;
		this.x = x;
		this.y = y;
		xCenter=x+radius;
		yCenter=y+radius;
		this.radius = radius;
		image=resize(images[j],radius);

		resources=radius;
		//name=???;
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

	public double distance(Planet p)
	{
		return Math.sqrt((xCenter-p.getXCenter())*(xCenter-p.getXCenter())+(yCenter-p.getYCenter())*(yCenter-p.getYCenter()));
	}

	public double distance2(Planet p)
	{
		return (xCenter-p.getXCenter())*(xCenter-p.getXCenter())+(yCenter-p.getYCenter())*(yCenter-p.getYCenter());
	}

	public int resourcesDifference(Planet p)
	{
		return Math.abs(resources-p.getResourceValue());
	}

	public int getResourceValue()
	{
		return resources; 
	}
	public String getName()
	{
		return name;
	}
	public int travelRadius()
	{
		return attRadius;
	}
	public boolean coordinateWithinTravelRadius(int x, int y)
	{
		return (xCenter-x)*(xCenter-x)+(yCenter-y)*(yCenter-y)<=attRadius*attRadius;
	}

	//Can we send fleets to p from this planet?
	public boolean canReach(Planet p)
	{
		double t=p.getRadius();
		if(distance2(p)>(t+attRadius)*(t+attRadius))return false;
		return true;
	}

	public Player getPlayer()
	{
		return this.getControllingPlayer();
	}

	public Player getControllingPlayer()
	{
		if (this.percentCaptured() >= 100) 
		{
			return this.owner;
		} 
		else 
		{
			return this.capturer;
		}
	}

	public Player getOwner()
	{
		return this.owner;
	}

	public void setControllingPlayer(Player p)
	{
		this.setPercentCaptured(100);
		this.owner = p;
		this.capturer = null;
	}

	public double percentCaptured()
	{
		return this.percentCaptured;
	}

	public Player[] getPlayers()
	{
		Set<Player> a=ships.keySet();
		Player[] b=new Player[a.size()];
		b=a.toArray(b);
		return b;
	}

	//Returns the only player who has ships on the planet. If more than 1 players have ships or
	//there are no players on the planet, it returns null.
	public Player getSinglePlayer()
	{
		if(getPlayers().length!=1)return null;
		return getPlayers()[0];
	}

	public int getPlayerShips(Player p)
	{
		if(ships.get(p)==null)return 0;
		return ships.get(p);
	}

	public void setPlayerShips(Player p, int n) // set the number of ships the given player has to be n, as long as n is less than 0
	{
		ships.put(p, Math.max(0, n));
	}

	public HashMap<Player,Integer> getShips()
	{
		return this.ships;
	}

	public void addFleet(Fleet f)
	{
		Player p=f.getPlayer();
		int s=f.getShips();
		if(ships.get(p)==null)ships.put(p,s);
		else
		{
			int sh=ships.get(p)+s;
			ships.put(p,sh);
		}
	}


	public Color getColor()
	{
		return owner.getColor();
	}


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
	public boolean isCoordinateInside(double x, double y)
	{
		//Is the coordinate's distance from the center coordinate less than (or equal) the radius?
		//With doubles.
		return (xCenter-x)*(xCenter-x)+(yCenter-y)*(yCenter-y)<=radius*radius;
	}

	private void setPercentCaptured(double x)
	{
		this.percentCaptured = x;
	}


	/**
	 * perform all battle, capture, or ship-building actions applicable to the planet
	 */
	public void update() {

		// check whether more than one player is on the planet. If so, perform a battle, and end this cycle
		if (this.getPlayers().length > 1) 
		{
			this.performBattle();
			int i;
			for(i=0;i<getPlayers().length;i++)
			{
				if(getPlayerShips(getPlayers()[i])<=0)
				{
					ships.remove(getPlayers()[i]);
				}
			}
			return;
		}

		// check whether the planet has already been captured. If it has not, perform capture actions then end the cycle.
		if(this.getPlayers().length == 1)
		{
			if (this.percentCaptured() < 100 || !belongsTo(getSinglePlayer()))
			{
				this.capturer = getPlayers()[0];
				this.performCapture();
				return;
			}

			if (this.getOwner() != null)
			{
				// NOTE: At this point, the planet must be peaceful and fully captured. Ships can now be generated.
				this.performShipBuilding();
			}
		}
	}


	/**
	 *  Update the ship counts due to battle, for one cycle.
	 * 
	 *  Each ship has a 0.5 chance of destroying an enemy ship/dealing one unit of damage.
	 *  Damage is equally between players, simultaneously, every time this is generated.
	 *  
	 *  1 unit of damage ~= 1 enemy ship destroyed (rounded)
	 */
	private void performBattle() {

		int[] damageDealtByPlayers = new int[this.getPlayers().length];

		// calculate the amount of damage/ships each player does in total  
		for (int playerNo=0; playerNo < this.getPlayers().length; playerNo++)
		{
			Player p = this.getPlayers()[playerNo];
			for (int i=0; i < this.getPlayerShips(p); i++)
			{
				if (Planet.random.nextBoolean())
				{
					damageDealtByPlayers[playerNo] += 1;
				}
			}
		}
		// for every player that isn't itself, set the damage dealt to it to be (damage done by other players / (total number of players - itself) ) 
		int[] damageDealtToPlayers = new int[this.getPlayers().length];
		for (int player=0; player < this.getPlayers().length; player++)
		{
			for (int playerDoingDamage=0; playerDoingDamage < this.getPlayers().length; playerDoingDamage++)
			{
				if (playerDoingDamage != player)
				{
					damageDealtToPlayers[player] += (int) (damageDealtByPlayers[playerDoingDamage] / (this.getPlayers().length - 1) );
				}
			}
		}
		// set number of ships for every player to be last number - damage just done to player
		for (int i=0; i < this.getPlayers().length; i++)
		{
			Player p = this.getPlayers()[i];
			int shipsLastCycle = this.getPlayerShips(p);
			this.setPlayerShips(p, Math.max(0, shipsLastCycle - damageDealtToPlayers[i]));
		}
	}


	public boolean belongsTo(Player p)
	{
		return this.getOwner() == p;
	}

	/**
	 * Add the number of new ships generated this cycle to the planet's owner
	 */
	private void performShipBuilding() {

		// ensure that there is only one player in charge.
		if (this.percentCaptured() >= 100)
		{
			Player p = this.getOwner();
			// number of new ships each cycle == planet resources
			newShip += this.getResourceValue();
			if (newShip >= 10000)
			{
				this.setPlayerShips(p, (this.getPlayerShips(p) + 1));
				newShip -= 10000;
			}
		}
	}

	/**
	 * Update the capture percentage, if there is only one player on the planet.
	 * If the planet is 100% captured, set the owner to be the currently dominant player. 
	 */
	private void performCapture()
	{
		if (this.getPlayers().length > 1) 
		{
			return;
		}

		//if (this.belongsTo(this.capturer)) 
		//{
		//	this.setControllingPlayer(this.capturer);
		//	return;
		//}

		// rate of capture is inversely proportional to the planet's resource value,
		// i.e. it's faster to capture a less valuable planet
		double rateOfCapture = Math.sqrt(getPlayerShips(getSinglePlayer()));
		double capturedThisCycle = rateOfCapture / this.getResourceValue();

		if (this.getOwner() != null) 
		{
			this.setPercentCaptured(this.percentCaptured() - capturedThisCycle);
		} 
		else 
		{
			this.setPercentCaptured(this.percentCaptured() + capturedThisCycle);
		}
		
		if(percentCaptured()<=0)
		{
			setPercentCaptured(0);
			setPlayer(null);
		}

		if (this.percentCaptured() >= 100 && getOwner()==null) 
		{
			this.setControllingPlayer(getSinglePlayer());
			return;
		}
	}
}
