/**
 * A Universe object generates a game upon initialisation,
 * and contains references to all the related objects, such
 * as Planets and Fleets.
 */
public class Universe
{
    private int width;
    private int height;
    
    private Fleet[] fleets; 
    private Planet[] planets;
    private Drawable[] drawableEntities;
    
    /*
     * This generates a universe of the appropriate width, height, and number
     * of planets present, and arranged in a satisfactory order.
     */
    public Universe(int width, int height, int numPlanets)
    {
        this.width = width;
        this.height = height;
        
        this.planets = new Planet[numPlanets];
        for (int i=0; i < numPlanets; i++) {
            // FIXME: default values place a planet at (50,50), with a radius of 40. 
            this.planets[i] = new Planet(50,50,40);
        }
        //TODO: ensure generated planets are a sensible distance apart, and have a sensible radius.
    }
    
    public void addPlanet(Planet p)
    {
        this.planets[planets.length] = p;
    }
    
    public void addFleet(Fleet f)
    {
        this.fleets[fleets.length] = f;
    }

    
    public Fleet[] getFleets()
    {
        return this.fleets;
    }
    public Planet[] getPlanets()
    {
        return this.planets;
    }
    public Drawable[] getDrawableEntities()
    {
        return this.drawableEntities;
    }
    
}
