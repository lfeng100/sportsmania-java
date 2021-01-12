package sportsmania.game;

/**
 * This class describe a net in a game
 * @author FengLeo
 */
public class Net extends GameObject{
    
    /**
     * Constructed a Net object whose upper-left corner is specified as (x,y) 
     * and whose width and height are specified by the arguments of the same name 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param img
     */
    public Net(final int x, final int y, final int width, final int height, final String img){
        super(x, y, height, width, img);
    }

    @Override
    public void update(final Game game) {
    }
    
}
