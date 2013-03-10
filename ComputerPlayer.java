import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ComputerPlayer extends Player
{

    private ArrayList<Planet> currentlyAttackingPlanets;

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



    /**
     * @param u
     * 
     * Find the nearest neutral/enemy planet for every planet the ComputerPlayer owns.
     * If the planet is deemed a valid target, and the ComputerPlayer isn't already
     * attacking it, send a Fleet of up to 90% of ships available on the current planet.
     */
    public void makeMove(Universe u)
    {
        Planet[] ownedPlanets = u.getPlayerPlanets(this);

        // For each planet the ComputerPlayer owns:
        for (Planet launchPlanet : ownedPlanets) {

            Planet closestValidTarget = new Planet(10000,10000); // VERY FAR AWAY

            // find closest planet which is a valid target
            for (Planet otherPlanet : u.getPlanets()) {
                if (this.validTarget(otherPlanet) && (launchPlanet.distanceFrom(otherPlanet) < launchPlanet.distanceFrom(closestValidTarget))) {
                    closestValidTarget = otherPlanet;
                }
            }
            if (!this.validTarget(closestValidTarget)) break;            
            // Is this a good attack from this planet?
            int numberOfAttackingShips = this.shipsToAttack(launchPlanet, closestValidTarget);
            if (numberOfAttackingShips == 0) break;            

            this.initiateAttack(launchPlanet, closestValidTarget, numberOfAttackingShips);
        }
    }

    private boolean validTarget(Planet p)
    {
        if (p.belongsTo(this) || this.currentlyAttackingPlanets.contains(p)) {
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


    /**
     * @param launchPlanet
     * @param targetPlanet
     * 
     * Make a new Fleet, set its destination to the targetPlanet, update the
     * launchPlanets ship numbers, and update the list of ships we're attacking.
     */
    private void initiateAttack(Planet launchPlanet, Planet targetPlanet, int attackingShips)
    {
        int shipsOnPlanet = launchPlanet.getPlayerShips(this);

        // Make a new Fleet, and send it to the planet
        Fleet f = new Fleet(launchPlanet.getX(), launchPlanet.getY(),
                targetPlanet.getX(), targetPlanet.getY());
        f.setShips(attackingShips);
        launchPlanet.setPlayerShips(this, (shipsOnPlanet - attackingShips));
        // Add the planet to list of planets the ComputerPlayer is attacking
        this.currentlyAttackingPlanets.add(targetPlanet);
    }

}
