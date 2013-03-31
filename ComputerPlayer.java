import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ComputerPlayer extends Player
{

	public ComputerPlayer(int number)
	{
		super(number);
		// TODO Auto-generated constructor stub
	}

	public ComputerPlayer(int number, Color color)
	{
		super(number, color);
		// TODO Auto-generated constructor stub
	}



	public void update(Universe u)
	{
//		System.out.println("Updating player " + this.getNumber());
		this.makeMoves(u);
	}

	/**
	 * @param u
	 * 
	 * Find the nearest neutral/enemy planet for every planet the ComputerPlayer owns.
	 * If the planet is deemed a valid target, and the ComputerPlayer isn't already
	 * attacking it, send a Fleet of up to 90% of ships available on the current planet.
	 */
	private void makeMoves(Universe u)
	{
		Planet[] ownedPlanets = u.getPlayerPlanets(this);
//		System.out.println("Making moves based on " + ownedPlanets.length + " planets.");
		// For each planet the ComputerPlayer owns:
		for (Planet launchPlanet : ownedPlanets)
		{
//			System.out.println("Considering options from " + launchPlanet + "...");
			Planet[] planetsArr = u.getPlanets();
			Planet closestValidTarget = planetsArr[0];
			// find closest planet which is a valid target
			for (int i=1; i < planetsArr.length; i++)
			{
				Planet possibleTarget = planetsArr[i];
				if (this.validTarget(possibleTarget) && (launchPlanet.distanceFrom(possibleTarget) < launchPlanet.distanceFrom(closestValidTarget)))
				{
					closestValidTarget = possibleTarget;
				}
			}
//			System.out.println("How about " + closestValidTarget + "?");
			// Is this a good attack from this planet?
			int numberOfAttackingShips = this.shipsToAttack(launchPlanet, closestValidTarget);
			if (numberOfAttackingShips == 0) continue;            

//			System.out.println("Setting move orders to " + closestValidTarget + "!");
			this.initiateMovement(launchPlanet, closestValidTarget, numberOfAttackingShips);
		}
	}

	private boolean validTarget(Planet p)
	{
		if (p.belongsTo(this)) {
			return false;
		}
		return true;
	}

	/**
	 * @param launchPlanet
	 * @param targetPlanet
	 * @return the number of ships you should send from the launchPlanet to
	 * attack the targetPlanet. 0 if you shouldn't attack.
	 */
	private int shipsToAttack(Planet launchPlanet, Planet targetPlanet)
	{
		Map<Player,Integer> targetShips = targetPlanet.getShips();
		int targetEnemyShips = 0;
		for (Player player : targetShips.keySet()) {
			if (player != this) targetEnemyShips += targetShips.get(player);
		}

		int shipsAvailable = launchPlanet.getPlayerShips(this) - 10; // keep >= 10 ships behind for defence
		int attackMargin = 5;   // we want to attack with this many more ships than the enemy
		int idealAttackStrength = (targetEnemyShips + attackMargin);

		if (shipsAvailable < idealAttackStrength) {
			return 0;
		}
		return idealAttackStrength;
	}

}
