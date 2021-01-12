/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportsmania;

import java.io.IOException;
import sportsmania.game.Game;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A class to perform SportsMania Game
 * @author FengLeo
 */
public class SportsMania {
    
    /**
     * The main methods to start the game
     * @param args 
     */
     public static void main(String[] args) {
         
        Game game = null;
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(SportsMania.class.getResource("/sportsmania/audio/menu.wav"));
            Clip music = AudioSystem.getClip();
            music.open(audio);
            music.loop(-1);           
            game = new Game();
            game.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("Error Occured with AudioInputStream -- " + e.getMessage());
            System.exit(-1);
        } catch (Throwable e) {
            System.out.println("Error Occured while starting the game -- " + e.getMessage());
            System.exit(-1);
        }
       
    }
}
