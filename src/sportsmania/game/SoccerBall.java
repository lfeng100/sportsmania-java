/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportsmania.game;

/**
 * A class define a soccerball in the game
 * @author FengLeo
 */
public class SoccerBall extends Ball {

    /**
     * Construct a new SoccerBall object
     * 
     * @param net
     * @param goalie
     * @param x
     * @param y
     * @param width
     * @param height
     * @param img
     */
    public SoccerBall(Net net, Goalie goalie, int x, int y, int width, int height, String img) {
        super(net, goalie, x, y, width, height, img);
    }

    /**
     * Update the score of soccer game, which using the principle of Polymorphism 
     * to invoke this method while playing soccer
     * @param game
     */
    public void updateScore(Game game) {
        super.updateScore(game);
        this.setInitYVel(this.getInitYVel() + 2);
    }

}
