import java.awt.Color;


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
    public Player(int number)
    {
        this.number = number;
        this.color = new Color(Universe.randomGenerator.nextInt(255),Universe.randomGenerator.nextInt(255),Universe.randomGenerator.nextInt(255));
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
