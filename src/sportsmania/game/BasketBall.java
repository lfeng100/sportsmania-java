/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportsmania.game;

/**
 * A class define a basketball in the game
 * @author FengLeo
 */
public class BasketBall extends Ball {

    /**
     * Construct a new Basketball object
     * 
     * @param net
     * @param goalie
     * @param x
     * @param y
     * @param width
     * @param height
     * @param img
     */
    public BasketBall(Net net, Goalie goalie, int x, int y, int width, int height, String img) {
        super(net, goalie, x, y, width, height, img);
    }

    /**
     * Update the score of basketball game game, which using the principle of
     * Polymorphism to invoke this method while playing basketball
     * @param game
     */
    public void updateScore(Game game) {
        super.updateScore(game);
    }
}
