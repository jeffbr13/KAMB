import java.awt.Graphics2D;

/**
 * Any object that can be drawn on a Graphics2D object through
 * the `draw() method.
 */
public interface Drawable
{
//    public void draw(Graphics2D g2d);
    public BufferedImage getImage();
}
