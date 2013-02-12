import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author jeffbr
 *
 */
public class Fleet
{
    private int x;
    private int y;

    // FIXME: These are basically stored orders for fleet movements, and so we'll make these less hacky.
    private int startX;
    private int startY;
    private int destinationX;
    private int destinationY;
    
    private BufferedImage bufferedImage;
    private int clickRadius = 10; // 10px click radius
    private int speed = 100; // arbitrary speed value
    
    
    public Fleet(int currentX, int currentY, int destinationX, int destinationY)
    {
        this.x = currentX;
        this.y = currentY;
        this.startX = currentX;
        this.startY = currentY;
        
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        
        try {
            File f = new File("resources/images/fleets/fleet1.png");
            this.bufferedImage = ImageIO.read(f);
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
     
    /**
     * @return the `x` co-ordinate of the fleet.
     */
    public int getX()
    {
        return this.x;
    }
     
    /**
     * @return the `y` co-ordinate of the fleet.
     */
    public int getY()
    {
        return this.y;
    }
    
    /**
     * @return a boolean stating whether or not the given coordinate is 'inside' the
     * fleet. Can be used for testing mouse-click-selection.
     */
    public boolean isClickInside(int x, int y)
    {
        //TODO Is the given coordinate's distance from the centre coordinate less than the clickRadius?
        return false;
    }
    
    
    /**
     * Draw the planet on the given Graphics2D object.
     */
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(this.bufferedImage, this.getX(), this.getY(), null);
    }
    
    /**
     * Update the internal state of the fleet based on how much time has passed.
     * Do this to a fleet before you draw it on screen, otherwise it won't update!
     */
    public void update(long nanosecondsPassed)
    {
        //TODO: change X and Y coords as a function of the time passed since the last
        // update, and `this.speed`
        //NOTE: system.nanoTime() is not necessarily accurate to the real world, but *is*
        // internally consistent. Just FYI...
    }
}
