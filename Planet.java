import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


/**
 * The Planet class is constructed with the x and y co-ordinates, as well as a radius.
 */
public class Planet implements Drawable
{
    private int x;
    private int y;
    private int radius;
    private int ships;
    private BufferedImage bufferedImage;
    
    
    public Planet(int x, int y, int radius)
    {
	this.x = x;
	this.y = y;
	this.radius = radius;
	
	try {
	    File f = new File("resources/images/planets/planet1.png");
	    this.bufferedImage = ImageIO.read(f);
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
    
    
    /**
     * @return the radius of the planet.
     */
    public int getRadius()
    {
        return radius;
    }


    /**
     * @return the number of ships present on the planet.
     */
    public int getShips()
    {
        return ships;
    }

    /**
     * @return draw the planet.
     */
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(this.bufferedImage, this.getX(), this.getY(), null);
    }
    

    public void setImage(String path) {
        try {
            File f = new File(path);
            this.bufferedImage = ImageIO.read(f);
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * @return a boolean stating whether or not the given coordinate is 'inside' the
     * planet. Can be used for testing mouse-click-selection.
     */
    public boolean isCoordinateInside(int x, int y)
    {
        //TODO Is the coordinate's distance from the centre coordinate less than the radius?
        return false;
    }
	
}
