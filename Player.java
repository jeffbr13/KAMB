import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


/**
 * @author jeffbr
 *
 */
public class Player
{ 
	protected int number;
	protected Color color;


	public static Color[] colors;
	public static int numColors;
	protected static int nextColor=0;

	protected ArrayList<Planet> currentlyAttackingPlanets;
	private ArrayList<Fleet> fleets;


	static
	{
		numColors=18;
		colors=new Color[numColors];
		int i;
		BufferedImage image = null;
		for(i=0;i<numColors;i++)
		{
			try
			{
				image=ImageIO.read(new File("resources/images/fleets/fleet"+(i+1)+".png"));
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			colors[i]=new Color(image.getRGB(image.getTileHeight()/2, image.getWidth()/2));
		}
	}

	/**
	 * Create a new player, with the given number and a random colour.
	 */
	public Player(int number)
	{
		this(number, colors[nextColor++]);
	}

	public Player(int number, Color color)
	{
		this.number = number;
		this.color = color;
		this.currentlyAttackingPlanets = new ArrayList<Planet>();
		this.fleets = new ArrayList<Fleet>();
	}

	/**
	 * @return get the Player's number
	 */
	public int getNumber()
	{
		return number;
	}

	public boolean owns(Fleet f)
	{
		return f.belongsTo(this);
	}

	public boolean owns(Planet p)
	{
		return p.belongsTo(this);
	}

	public Color getColor()
	{
		return this.color;
	}

	public Fleet createFleet(int currentX, int currentY, int destinationX, int destinationY)
	{
		Fleet f = new Fleet(currentX,currentY,destinationX,destinationY);
		f.setPlayer(this);
		return f;
	}

	/**
	 * @param launchPlanet
	 * @param targetPlanet
	 * 
	 * Make a new Fleet, set its destination to the targetPlanet, update the
	 * launchPlanets ship numbers, and update the list of ships we're attacking.
	 */
	public void initiateMovement(Planet launchPlanet, Planet targetPlanet, int attackingShips)
	{		
		int startingShips = launchPlanet.getPlayerShips(this);
		int shipsToSend = Math.min(attackingShips, startingShips);

		Fleet f = new Fleet(launchPlanet.getXCenter(), launchPlanet.getYCenter(), targetPlanet.getXCenter(), targetPlanet.getYCenter());
		f.setShips(shipsToSend);
		f.setPlayer(this);
		f.setTarger(targetPlanet);
		this.addFleet(f);
		launchPlanet.setPlayerShips(this, (startingShips - shipsToSend));
	}

	private void addFleet(Fleet f) {
		this.fleets.add(f);
	}

	/**
	 * Dummy method for human Players, used in ComputerPlayer to do all computation/strategy stuff 
	 */
	public void update() {
		return;
	}

	public Fleet[] getFleets() {
		Fleet[] fleetsArr = new Fleet[this.fleets.size()];
		return this.fleets.toArray(fleetsArr);
	}

	public void deleteFleet(Fleet f) {
		int i = this.fleets.indexOf(f);
		if(i != -1)
		{
			this.fleets.remove(i);
		}

	}
}
