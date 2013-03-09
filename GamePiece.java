import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;


/**
 * The Planet class is constructed with the x and y coordinates, as well as a radius.
 */
public class GamePiece implements Position, Drawable
{
	protected int x,y;
	protected Player owner;
    protected BufferedImage image;
    
    
    public static BufferedImage resize(BufferedImage originalImage, int r)
    {
    	//Assumes that the image is a square. Also r is half the size we want.
    	BufferedImage scaledBI = new BufferedImage(r*2, r*2, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = scaledBI.createGraphics();
    	g.drawImage(originalImage, 0, 0, r*2, r*2, null); 
    	g.dispose();
    	return scaledBI;
    }

    /**
     * @return the `x` co-ordinate of the planet.
     */
    public int getX()
    {
        return x;
    }

    
    /**
     * @return the `y` co-ordinate of the planet.
     */
    public int getY()
    {
        return y;
    }
    
    public void setX (int x)
    {
    	this.x=x;
    }
    
    public void setY (int y)
    {
    	this.y=y;
    }
    
    public double distanceFrom(Position p) 
    {
        
        int dx = this.getX() - p.getX();
        int dy = this.getY() - p.getY();
        
        return Math.sqrt( (dx * dx) + (dy * dy) );
    }
    
    
    public BufferedImage getImage()
    {
     return image;
    }
    
    public Player getPlayer()
    {
     return owner;
    }
    
    public void setPlayer(Player p)
    {
     owner=p;
    }
    
    public boolean belongsTo(Player p)
    {
     return owner==p;
    }
    
	
}
