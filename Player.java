import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * @author jeffbr
 *
 */
public class Player
{ 
    private int number;
    private Color color;

    /**
     * Create a new player, with the given number and a random colour.
     */
    
    public static Color[] colors;
    public static int numColors;
    
    static
    {
     numColors=18;
     colors=new Color[numColors];
     int i;
     Color t;
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
    
    public Player(int number)
    {
        this(number, new Color(Universe.randomGenerator.nextInt(255),Universe.randomGenerator.nextInt(255),Universe.randomGenerator.nextInt(255)));
    }

    public Player(int number, Color color)
    {
        this.number = number;
        this.color = color;
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

    public Color getColor() {
        return this.color;
    }
}
