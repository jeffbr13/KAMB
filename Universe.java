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
    
    private Fleet[] fleets; 
    
    //n= Number of planets.
    public int n;
    
    //Used for getting random values.
    private Random random;
    
    //How much do you want the distance between planets to be?
    private int planetDistance=20;
    
    private Planet[] planets;
    private Drawable[] drawableEntities;
    private BufferedImage[] planetImgs;
    
    /*
     * This generates a universe of the appropriate width, height, and number
     * of planets present, and arranged in a satisfactory order.
     */
    public Universe(int width, int height, int numPlanets)
    {
        this.width = width;
        this.height = height;
        n=numPlanets;
        this.planets = new Planet[n];
        
        /*
        Make an array that holds the planet images and make the 3-rd value of the planet
        constructor be the image. NOT the radius since it's gonna be the same for all planets
        anyway. Also all the images need to be the same size. I've named that array planetImgs.
        It should have predetermined "values" and size depending on the number of images we have
        and they have to be enteres manualy each. I'm not gonna outright do this until you guys agree.
        */
        int x,y,img,i,j;
        for(i=0;i<n;i++)
        {
         //Chooses a random image for the planet.
         img=random.nextInt(planetImgs.length);
         do
         {
          //Chooses random coordinates.
          x=random.nextInt(width-planetImgs[img].getWidth());
          y=random.nextInt(height-planetImgs[img].getWidth());
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
         //Make them take image as a third construction parameter and delete this while
         //uncommenting the other one.
         planets[i]=new Planet(x,y,40);
         //planets[i]=new Planet(x,y,planetImgs[img]);
        }
    }
    
    public void addPlanet(Planet p)
    {
    	//You can't add planets like this, trying to do that will give a runtime error.
    	//We don't need to add planets (for now) anyway.
        //this.planets[planets.length] = p;
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
