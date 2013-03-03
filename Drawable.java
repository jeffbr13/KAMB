import java.awt.image.BufferedImage;

/**
 * Any object that provides an image.
 */
public interface Drawable
{
//    public void draw(Graphics2D g2d);
    public BufferedImage getImage();
}
