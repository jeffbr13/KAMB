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
     * This genera
     */
    public Universe(int width, int height, int numPlanets)
    {
        this.width = width;
        this.height = height;
        
        //TODO: generate planets.
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
