/**
 * A Universe object generates a game upon initialization,
 * and contains references to all the related objects, such
 * as Planets and Fleets.
 */

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Universe
{
    private int width;
    private int height;
    private BufferedImage backgroundImage;
    
    private ArrayList<Fleet> fleets; 
    private ArrayList<Planet> planets;

    
    static private int margin = 100;   // minimum distance from edges
    static private int minPlanetSize = 20;
    static private int maxPlanetSize = 60;
    static private int minPlanetSeparation = 80;
    
    private static Random randomGenerator = new Random();


    /*
     * This generates a universe of the appropriate width, height, and number
     * of planets present, and arranged in a satisfactory order.
     * 
     * BRJ
     */
    public Universe(int width, int height, int initialNumberOfPlanets, int minimumPlanetSeparation)
    {
        this.width = width;
        this.height = height;
        this.fleets = new ArrayList<Fleet>();
        this.planets = new ArrayList<Planet>();
        
        for (int i=0; i < initialNumberOfPlanets; i++) {

            Planet p = this.generatePlanet();
            while (this.planetsFarEnoughAwayFrom(p) != true) {
                p = this.generatePlanet();
            }
            this.addPlanet(p);
            i++;
        }

    }
    
    
    private boolean planetsFarEnoughAwayFrom(Position p)
    {
        ArrayList<Planet> ps = this.getPlanets();
        for (int i=0; i < ps.size(); i++) {
            if (p.distanceFrom(ps.get(i)) > Universe.minPlanetSeparation) {
                return false;
            }
        }
        return true;
    }
    
    
    private Planet generatePlanet()
    {
        int planetX = Universe.randomBetween(Universe.margin, (this.getWidth() - Universe.margin));
        int planetY = Universe.randomBetween(Universe.margin, (this.getHeight() - Universe.margin));
        // planet must be at least minDistanceFromEdges units from the sides
        
        int planetR = Math.max(Universe.minPlanetSize, Universe.randomGenerator.nextInt(maxPlanetSize));
        // planet must have a radius larger than minPlanetSize and smaller than 
        
        return new Planet(planetX, planetY, planetR);
    }
    
    
    static int randomBetween(int min, int max)
    {
        return Math.max(min, Universe.randomGenerator.nextInt(max));
    }

    /**
     * @return the width
     */
    public int getWidth()
    {
        return this.width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height)
    {
        this.height = height;
    }

    public void addPlanet(Planet p)
    {
        this.planets.add(p);
    }
    
    public ArrayList<Fleet> getFleets()
    {
        return this.fleets;
    }
    public ArrayList<Planet> getPlanets()
    {
        return this.planets;
    }
    public Drawable[] getDrawableEntities()
    {
        return this.drawableEntities;
    }


    /**
     * @return the backgroundImage
     */
    public BufferedImage getBackground()
    {
        return backgroundImage;
    }


    /**
     * @param backgroundImage the backgroundImage to set
     */
    public void setBackground(BufferedImage backgroundImage)
    {
        this.backgroundImage = backgroundImage;
    }
    
}
