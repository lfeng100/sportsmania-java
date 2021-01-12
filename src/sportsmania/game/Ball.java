package sportsmania.game;
/** @author Leo Feng
 * @since November 25, 2019
 * Last Edited: November 30, 2019
 * Project Name: Feng_leo_Rectangle
 * Class Name: Rectangle.java
 * Description: This is a base abstract class for Ball Game Objects. The update() and updateScore method defines the abstracted behavrors of this object.
 */

public abstract class Ball extends GameObject {

    //The net for the respective ball game
    private Net net;
    // The goalie for the respective ball game
    private Goalie goalie;
    // The initial velocity of the ball
    private double initYVel;
    // Indicates if the ball has been scored
    private boolean hit = false;
    // Count that Increments each time update() is called to keep track of iterations
    private int count = 0;
    // Indicate the shot power of the ball dependent on the power bar
    private int shotPower = -1;

    /**
     *Description: This constructor is called by subclass to set up abstract 
     *attributes of a Ball
     * 
     * @param net
     * @param goalie
     * @param x
     * @param y
     * @param width
     * @param height
     * @param img
     */
    protected Ball(Net net, Goalie goalie, int x, int y, int width, int height, String img) {
        super(x, y, height, width, img);
        
        this.setNet(net);
        this.setGoalie(goalie);
    }
    
    private void resetVar(Game game) {
        game.setShotRight(false);
        game.setShotLeft(false);
        game.setShotMiddle(false);
        game.setShotBall(false);
        this.setWidth(0);
        this.setHit(false);
        this.setCount(0);
    }

    
    /**
     * It is extended by subclasses to update the abstracted attribute of the Ball
     * @param game the game of playing
     */
    public void updateScore(Game game) {
        this.setShotPower(game.getShotPower());
        if (this.getShotPower() < 60 & this.getShotPower() > 40) {
            this.setShotPower(50);
            this.setHit(true);
        } else {
            if (game.isOvershot()) {
                if (this.getShotPower() < 50) {
                    this.setShotPower (100 - this.getShotPower());
                }
                if (this.getShotPower() > 75) {
                    this.setShotPower(57);
                } else {
                    this.setShotPower (54);
                }
            } else {
                if (this.getShotPower() > 50) {
                    this.setShotPower (100 - this.getShotPower());
                }
                if (this.getShotPower() < 25) {
                    this.setShotPower (30);
                } else {
                    this.setShotPower(40);
                }
            }
        }
        
        this.setInitYVel(this.getShotPower() / 100.0 * 68);
    }

    /**
     * Description: Updates the position of the ball.
     * @param game
     */
    @Override
    public void update(final Game game) {
        if (game.isShotBall() && !game.isAiming()) {
            if (hit && getWidth() < 35 && (game.isOnBasketball() || ((game.isShotLeft()) && (game.getGoaliePos() == 1 || game.getGoaliePos() == 2))
                    || (game.isShotMiddle() && (game.getGoaliePos() == 0 || game.getGoaliePos() == 2))
                    || (game.isShotRight() && (game.getGoaliePos() == 0 || game.getGoaliePos() == 1)))) {
                game.increaseScore();
                game.setScored(true);
                System.out.println("scored");
                resetVar(game);
            } else if (this.getY() < -50) {
                resetVar(game);
            } else if (this.getY() > 800) {
                resetVar(game);
            } else {
//                if (count % 2 == 0) {
                    this.addToY((int) getInitYVel(), false);
                    this.getRect().y -= getInitYVel();
                    this.addToWidth(2, false);
                    this.getRect().width -= 2;
                    this.addToHeight(2, false);
                    this.getRect().height -= 2;
                    if (this.getWidth() % 2 == 0) {
                        this.addToX(1, true);
                        this.getRect().x += 1;
                    }
                    if (game.isOnSoccer() || game.isOnHockey()) {
                        if (this.getWidth() % 2 == 0) {
                            if (game.isShotRight()) {
                            this.addToX(1, true);
                            this.getRect().x += 1;
                            } else if (game.isShotLeft()) {
                                this.addToX(1, false);
                                this.getRect().x -= 1;
                            }
                        }
                    }
                    setInitYVel(getInitYVel()-1);
//                }
                count++;
            }
        }
    }


    /**
     * Sets net using for this ball
     * @param net
     */
    public void setNet(Net net) {
        this.net = net;
    }

    /**
     * set goalie to catch this ball
     * @param goalie
     */
    public void setGoalie(Goalie goalie) {
        this.goalie = goalie;
    }

    /**
     * set the initial position of the ball
     * @param initYVel
     */
    public void setInitYVel(double initYVel) {
        this.initYVel = initYVel;
    }

    /**
     * set to true if the ball scores, otherwise set to false
     * @param hit
     */
    public void setHit(boolean hit) {
        this.hit = hit;
    }

    /**
     * set the count of the score
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * get the net for this ball
     * @return net
     */
    public Net getNet() {
        return net;
    }

    /**
     * get the goalie for this ball
     * @return goalie for this ball game
     */
    public Goalie getGoalie() {
        return goalie;
    }

    /**
     * get the initial position of the ball
     * @return initYVel
     */
    public double getInitYVel() {
        return initYVel;
    }

    /**
     * return true if the ball scores, otherwise return false
     * @return hit
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * return the total counts of hits in the game
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * return the power of the ball
     * @return shotPower
     */
    public int getShotPower() {
        return shotPower;
    }

    /**
     * set the power of th ball
     * @param shotPower
     */
    public void setShotPower(int shotPower) {
        this.shotPower = shotPower;
    }
}