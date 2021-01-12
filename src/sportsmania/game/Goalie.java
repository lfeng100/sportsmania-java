package sportsmania.game;


/**
 * This class describe a goalie in a game
 * @author FengLeo
 */
public class Goalie extends GameObject {

    private int count = 0;

    /**
     *
     * Constructed a Goalie object whose upper-left corner is specified as (x,y) 
     * and whose width and height are specified by the arguments of the same name 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param img
     */
    public Goalie(int x, int y, int width, int height, String img) {
        super(x, y, height, width, img);
    }

    @Override
    public void update(final Game game) {
        if (game.isShotBall()) {
            if (game.getGoaliePos() == 0) {
                this.setImg(getImage("/sportsmania/images/soccerGoalieLeft.png"));
                if (count % 2 == 0) {
                    this.addToX(1, false);
                    this.getRect().x -= 1;
                }
            } else if (game.getGoaliePos() == 1) {
                if (count % 3 == 0) {
                    this.addToY(1, false);
                    this.getRect().y -= 1;
                }
            } else if (game.getGoaliePos() == 2) {
                this.setImg(getImage("/sportsmania/images/soccerGoalieRight.png"));
                if (count % 2 == 0) {
                    this.addToX(1, true);
                    this.getRect().x += 1;
                }
            }
            count++;
        } else {
            this.setImg(getImage("/sportsmania/images/soccerGoalieMiddle.png"));
            this.setX(361);
            this.setY(135);
            count = 0;
        }
    }
}
