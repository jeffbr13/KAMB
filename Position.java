
/**
 * 
 */

/**
 * @author BRJ
 *
 * Any object which has X and Y co-ordinates, and can be compared to another position.
 */
public interface Position
{

    public int getX();
    public int getY();
    
    public double distanceFrom(Position p);
}
