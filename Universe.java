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
    
    private static Random randomGenerator = new Random();
    
    
    //n= Number of planets.
    public int n;
    
    //Used for getting random values.
    private Random random;
    
    //How much do you want the distance between planets to be?
    private int planetDistance=20;
    
    private Drawable[] drawableEntities;
    
    
    
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
        
        // TODO: spin this out into a separate method
        for (int i=0; i < initialNumberOfPlanets; i++) {
            
            int minDistanceFromEdges = 100;
            int minPlanetSize = 20;
            int maxPlanetSize = 60;
            
            int planetX = Math.max(minDistanceFromEdges, Universe.randomGenerator.nextInt( (this.getWidth() - minDistanceFromEdges) ));
            int planetY = Math.max(minDistanceFromEdges, Universe.randomGenerator.nextInt( (this.getHeight() - minDistanceFromEdges) ));
            // planet must be at least minDistanceFromEdges units from the sides
            
            int planetR = Math.max(minPlanetSize, Universe.randomGenerator.nextInt(maxPlanetSize));
            // planet must have a radius larger than minPlanetSize and smaller than 
            
            Planet planet = new Planet(planetX, planetY, planetR);
            this.addPlanet(planet);
            i++;
        }
        
        
        int r;
        
        int x,y,img,i,j;
        for(i=0;i<n;i++)
        {
            //Chooses a random image for the planet.
            img=random.nextInt(Planet.imagesNum);

            r=50;
            /**
             * To do: Decide what we're going to do with the radiuses and how they are generated.
             * Atanas
             */

            do
            {
                //Chooses random coordinates.
                x=random.nextInt(width-r*2);
                y=random.nextInt(height-r*2);
                for(j=0;j<i-1;j++)
                {
                    //Checks if the current randoms are ok with all current planets one at a time:
                    if((planets[j].x-x)*(planets[j].x-x)+(planets[j].y-y)*(planets[j].y-y)<(2*planets[j].radius+planetDistance)*(2*planets[j].radius+planetDistance))
                    {
                        //If they aren't far enough from some existing planet, I stop the loop and j remains
                        //smaller than i-1; for the first planet it will be 0<0-1 so it'll always pass.
                        break;
                    }
                }
                //Checks if we reached the end of the loop and therefore the current coordinates are ok.
            }
            while(j<i-1);
            planets[i]=new Planet(x,y,r,img);
        }
    }
    
    /**
     * @return the width
     */
    public int getWidth()
    {
        return width;
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
        this.planets.add(p)
    }
    
    public void addFleet(Fleet f)
    {
    	/*
    	Can't add fleets like that either. You're basically trying to change a value which is outside
    	the array- remember an array is from 0 to it's length-1 and doesn't allow change of it's size.
        this.fleets[fleets.length] = f;
        */
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
