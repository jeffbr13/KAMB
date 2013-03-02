
/**
 * @author jeffbr
 *
 */
public class Player
{ 
    private int number;

    /**
     * Create a new player, with a number.
     */
    public Player(int number)
    {
        this.number = number;
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

}
