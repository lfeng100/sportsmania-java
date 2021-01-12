package sportsmania.game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * A class to manage playing a game
 *
 * @author FengLeo
 */
public class Game extends JFrame implements KeyListener, MouseListener {

    private final int FRAME_HEIGHT = 800;
    private final int FRAME_WIDTH = 800;
    private final String GAME_TITLE = "Sports Mania";
    private final Color LIGHT_BLUE = new Color(204, 255, 255);
    private final Color LIGHT_ORANGE = new Color(255, 204, 153);
    private final Color BRONZE = new Color(80, 50, 20);
    private final String B_BALL_IMAGE = "/sportsmania/images/bball.png";
    private final String B_NET_IMAGE = "/sportsmania/images/backboard.png";
    private final String S_BALL_IMAGE = "/sportsmania/images/soccerball.png";
    private final BufferedImage ARROW_1 = initBufferedImage("/sportsmania/images/arrow1.png");
    private final BufferedImage ARROW_2 = initBufferedImage("/sportsmania/images/arrow2.png");
    private final BufferedImage ARROW_3 = initBufferedImage("/sportsmania/images/arrow3.png");
    private final BufferedImage ARROW_1_GREEN = initBufferedImage("/sportsmania/images/arrow1green.png");
    private final BufferedImage ARROW_2_GREEN = initBufferedImage("/sportsmania/images/arrow2green.png");
    private final BufferedImage ARROW_3_GREEN = initBufferedImage("/sportsmania/images/arrow3green.png");
    private final BufferedImage B_BALL_NET = initBufferedImage("/sportsmania/images/basketballNet.png");
    private final BufferedImage FACT_1 = initBufferedImage("/sportsmania/images/fact1.png");
    private final BufferedImage FACT_2 = initBufferedImage("/sportsmania/images/fact2.png");
    private final BufferedImage FACT_3 = initBufferedImage("/sportsmania/images/fact3.png");
    private final BufferedImage FACT_4 = initBufferedImage("/sportsmania/images/fact4.png");
    private final BufferedImage FACT_5 = initBufferedImage("/sportsmania/images/fact5.png");
    private final BufferedImage FACT_6 = initBufferedImage("/sportsmania/images/fact6.png");
    private final BufferedImage LOCK_IMG = initBufferedImage("/sportsmania/images/locked.png");
    private final CopyOnWriteArrayList<CustomButton> menuButtons;
    private final CopyOnWriteArrayList<CustomButton> gameButtons;
    private final CustomButton backButton = new CustomButton("Back", 10, 710, 200, 75, 60, 0);
    private final CustomButton playAgain = new CustomButton("Play Again?", FRAME_WIDTH / 2 - 250, 580, 500, 100, 60, 20);
    private final int gameTime = 30000;
    private final int barSpeed = 16;
    private final Random rand = new Random();
    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            secondsPassed++;
        }
    };

    private Ball ball;
    private Net net;
    private Goalie goalie;
    private int shotPower = -1;
    private boolean overshot = false;
    private boolean onBasketball = false;
    private boolean onSoccer = false;
    private boolean gamePaused = false;
    private int pauseTime;
    private int secondsPassed = 0;
    private int shotTimer = 0;
    private boolean scored = false;
    private int score;
    private boolean aiming = false;
    private boolean onScoreScreen = false;
    private boolean onScores = false;
    private boolean onHockey = false;
    private boolean shotBall = false;
    private boolean shotLeft = false;
    private boolean shotMiddle = false;
    private boolean shotRight = false;
    private int goaliePos = -1;
    private int overshotTimer = 0;
    private int freezeSecond = 0;
    private boolean startup = true;
    private boolean loading = true;
    private boolean onMenu = false;
    private boolean onSelectGame = false;
    private boolean onGame = false;
    private boolean gameStarted = false;
    private boolean onInstructions = false;
    private String currentBall;
    private String currentNet;
    private ArrayList<Integer> basketballScores;
    private ArrayList<Integer> soccerScores;
    private ArrayList<Integer> hockeyScores;
    private BufferedImage background;
    private BufferedImage[] facts;
    private BufferedImage currentFact;

    /**
     * increase the score of the current game
     */
    public void increaseScore() {
        this.setScore(this.getScore() + 1);
    }

    /**
     * Construct a Game class which initialize game properties
     */
    public Game() {

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setSize(800, 800);  // or whatever your full size is
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                setLocation(0, 0);
            }
        });

        setTitle(GAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        addKeyListener(this);
        addMouseListener(this);
        setLocationRelativeTo(null);
        setLocation(0, 0);

        facts = new BufferedImage[]{FACT_1, FACT_2, FACT_3, FACT_4, FACT_5, FACT_6};

        basketballScores = new ArrayList<>();
        soccerScores = new ArrayList<>();
        hockeyScores = new ArrayList<>();

        menuButtons = new CopyOnWriteArrayList<>();
        gameButtons = new CopyOnWriteArrayList<>();
        CustomButton selectGame = new CustomButton("Select Game", FRAME_WIDTH / 2 - 250, 150, 500, 100, 80, -30);
        menuButtons.add(selectGame);
        CustomButton highScores = new CustomButton("High Scores", FRAME_WIDTH / 2 - 250, 300, 500, 100, 60, 20);
        menuButtons.add(highScores);
        CustomButton instructions = new CustomButton("Instructions", FRAME_WIDTH / 2 - 250, 450, 500, 100, 80, -10);
        menuButtons.add(instructions);
        CustomButton quit = new CustomButton("Quit", FRAME_WIDTH / 2 - 250, 600, 500, 100, 65, -10);
        menuButtons.add(quit);
        CustomButton basketball = new CustomButton("Basketball", FRAME_WIDTH / 2 - 250, 300, 500, 100, 80, 0);
        gameButtons.add(basketball);
        CustomButton soccer = new CustomButton("Soccer", FRAME_WIDTH / 2 - 250, 450, 500, 100, 80, -20);
        gameButtons.add(soccer);
        CustomButton hockey = new CustomButton("Hockey", FRAME_WIDTH / 2 - 250, 600, 500, 100, 80, 0);
        gameButtons.add(hockey);

        readToArray("basketballHS.txt", basketballScores);
        readToArray("soccerHS.txt", soccerScores);
        readToArray("hockeyHS.txt", hockeyScores);
        sortArray(basketballScores);
        sortArray(soccerScores);
        sortArray(hockeyScores);
        this.setVisible(true);
        timer.scheduleAtFixedRate(task, 0, 25);
    }

    private void readToArray(String file, ArrayList<Integer> array) {
        FileReader fr = null;
        Scanner s = null;
        try {
            fr = new FileReader(file);
            s = new Scanner(fr);
            while (s.hasNextLine()) {
                if (s.hasNextInt()) {
                    array.add(s.nextInt());
                }
                s.nextLine();
            }
            s.close();
        } catch (IOException e) {
            System.out.println("Error Occurred with FileReader -- " + e.getMessage());
            System.exit(-1);
        }
    }

    private BufferedImage initBufferedImage(String str) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Game.class.getResourceAsStream(str));
        } catch (IOException ex) {
            System.out.println("Error Occurred with BufferedImage");
            System.exit(-1);
        }
        return image;
    }

    private void paintComponent(Graphics g) {
        boolean tooSlow = false;
        if (loading) {
            if (secondsPassed < 100) {
                g.setColor(LIGHT_BLUE);
                g.fillRect(0, 0, 800, 800);
                g.setColor(LIGHT_ORANGE);
                g.fillRect(100, 600, 600, 75);
                g.setFont(new Font("Garamond", Font.PLAIN, 40));
                g.drawImage(currentFact, 150, 75, 500, 500, null);
                g.setColor(LIGHT_ORANGE);
                g.drawString("Loading...", 100, 720);
                g.drawString(secondsPassed + "%", 630, 720);
                g.setColor(Color.ORANGE);
                g.fillRect(100, 600, (int) (secondsPassed / 100.0 * 600), 75);
            } else {
                loading = false;
                if (startup) {
                    onMenu = true;
                    startup = false;
                } else {
                    initGame();
                    onGame = true;
                }
            }
            repaint();
        } else if (onMenu) {
            background = initBufferedImage("/sportsmania/images/menu.png");
            g.drawImage(background, 0, 0, 800, 800, null);
            for (CustomButton but : menuButtons) {
                but.draw(g);
            }
        } else if (onSelectGame) {
            background = initBufferedImage("/sportsmania/images/selectGameScreen.png");
            g.drawImage(background, 0, 0, 800, 800, null);
            for (CustomButton but : gameButtons) {
                but.draw(g);
            }
            g.drawImage(LOCK_IMG, 255, 600, 285, 105, null);
            backButton.draw(g);
        } else if (onScores) {
            background = initBufferedImage("/sportsmania/images/scoreScreen.png");
            g.drawImage(background, 0, 0, 800, 800, null);
            g.setFont(new Font("Garamond", Font.PLAIN, 75));
            g.setColor(Color.yellow);
            g.drawString("" + basketballScores.get(0), 375, 320);
            g.drawString("" + soccerScores.get(0), 375, 450);
            g.drawString("" + hockeyScores.get(0), 375, 580);
            g.setFont(new Font("Garamond", Font.PLAIN, 55));
            g.setColor(Color.gray);
            g.drawString("" + basketballScores.get(1), 495, 320);
            g.drawString("" + soccerScores.get(1), 495, 450);
            g.drawString("" + hockeyScores.get(1), 495, 580);
            g.setColor(BRONZE);
            g.drawString("" + basketballScores.get(2), 600, 320);
            g.drawString("" + soccerScores.get(2), 600, 450);
            g.drawString("" + hockeyScores.get(2), 600, 580);
            g.drawImage(LOCK_IMG, 355, 525, 285, 105, null);
            backButton.draw(g);
        } else if (onGame) {
            g.drawImage(background, 0, 0, 800, 800, null);
            g.setFont(new Font("Garamond", Font.PLAIN, 80));
            if (gameStarted && secondsPassed < gameTime / 25) { // 60000 (1 minute)/25(our timer interval is 225ms) = 2400
                if (onBasketball) {
                    g.drawImage(B_BALL_NET, (FRAME_WIDTH / 2) - 50, 200, 100, 100, this);
                } else if (onSoccer) {
                    goalie.draw(g);
                    goalie.update(this);
                    if (aiming) {
                        if (shotTimer + 40 == secondsPassed) {
                            aiming = false;
                            shotBall = false;
                            tooSlow = true;
                        } else {
                            g.setColor(LIGHT_BLUE);
                            g.drawString("Aim your shot!", 200, 300);
                            g.drawImage(ARROW_1, (FRAME_WIDTH / 2) - 50, 350, 100, 150, this);
                            g.drawImage(ARROW_2, (FRAME_WIDTH / 2) + 150, 350, 100, 150, this);
                            g.drawImage(ARROW_3, (FRAME_WIDTH / 2) - 250, 350, 100, 150, this);
                            int mouseX = MouseInfo.getPointerInfo().getLocation().x;
                            if (mouseX > 300 && mouseX < 500) {
                                g.drawImage(ARROW_1_GREEN, (FRAME_WIDTH / 2) - 50, 350, 100, 150, this);
                            } else if (mouseX > 400) {
                                g.drawImage(ARROW_2_GREEN, (FRAME_WIDTH / 2) + 150, 350, 100, 150, this);
                            } else {
                                g.drawImage(ARROW_3_GREEN, (FRAME_WIDTH / 2) - 250, 350, 100, 150, this);
                            }
                        }
                    }
                    if (tooSlow && shotTimer + 50 >= secondsPassed) {
                        g.setColor(LIGHT_BLUE);
                        g.drawString("TOO SLOW!", 200, 300);
                    } else {
                        tooSlow = false;
                    }
                }
                if (ball.getWidth() > 35) {
                    ball.draw(g);
                }
                ball.update(this);
                g.setColor(LIGHT_ORANGE);
                g.fillRect(700, 300, 75, 450);
                g.setColor(Color.ORANGE);
                if (gamePaused) {
                    System.out.println("in");
                    if ((int) (pauseTime * barSpeed / 450) % 2 == 0) {
                        g.fillRect(700, 300 + ((pauseTime * barSpeed) % 450), 75, 450 - ((pauseTime * barSpeed) % 450));
                    } else {
                        g.fillRect(700, 750 - (pauseTime * barSpeed) % 450, 75, (pauseTime * barSpeed) % 450);
                    }
                } else if (!shotBall && secondsPassed - 20 > overshotTimer && !aiming) {
                    if ((int) (secondsPassed * barSpeed / 450) % 2 == 0) {
                        g.fillRect(700, 300 + ((secondsPassed * barSpeed) % 450), 75, 450 - ((secondsPassed * barSpeed) % 450));
                    } else {
                        g.fillRect(700, 750 - (secondsPassed * barSpeed) % 450, 75, (secondsPassed * barSpeed) % 450);
                    }
                } else {
                    if ((int) (freezeSecond * barSpeed / 450) % 2 == 0) {
                        g.fillRect(700, 300 + ((freezeSecond * barSpeed) % 450), 75, 450 - ((freezeSecond * barSpeed) % 450));
                    } else {
                        g.fillRect(700, 750 - (freezeSecond * barSpeed) % 450, 75, (freezeSecond * barSpeed) % 450);
                    }
                }
                if (onBasketball && ball.getWidth() < 60) {
                    g.drawImage(B_BALL_NET, (FRAME_WIDTH / 2) - 50, 200, 100, 100, this);
                }
                g.setColor(Color.BLUE);
                g.fillRect(700, 508, 75, 34);

                g.setColor(Color.pink);
                g.setFont(new Font("Garamond", Font.PLAIN, 50));
                g.drawString("Score: " + score, 10, 75);
                g.drawString("Time: " + (gameTime / 1000 - (int) (secondsPassed / 40.0)), 600, 75);
            } else if (!gameStarted) {
                g.drawImage(background, 0, 0, 800, 800, null);
            } else {
                onGame = false;
                setScores();
                onScoreScreen = true;
            }
            if (scored) {;
                try {
                    URL url = Game.class
                            .getResource("/sportsmania/audio/score.wav");
                    AudioInputStream audio = AudioSystem.getAudioInputStream(url);
                    Clip music = AudioSystem.getClip();
                    music.open(audio);
                    music.loop(0);
                } catch (Exception e) {
                    System.out.println("Error Occured with AudioInputStream -- " + e.getMessage());
                    System.exit(-1);
                }
                scored = false;
            }
            if (gamePaused) {
                secondsPassed = pauseTime;
                background = initBufferedImage("/sportsmania/images/pauseScreen.png");
                g.drawImage(background, 0, 0, 800, 800, null);
            }
            backButton.draw(g);
            repaint();
        } else if (onScoreScreen) {
            if (onSoccer) {
                background = initBufferedImage("/sportsmania/images/soccerScoreScreen.png");
            } else if (onBasketball) {
                background = initBufferedImage("/sportsmania/images/basketballScoreScreen.png");
            }
            g.drawImage(background, 0, 0, 800, 800, null);
            g.setColor(Color.red);
            g.setFont(new Font("Garamond", Font.PLAIN, 80));
            g.drawString("" + score, 500, 110);
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Garamond", Font.PLAIN, 50));
            g.drawString("YOUR TOP 5 SCORES:", 175, 285);
            if (onBasketball) {
                if (score == basketballScores.get(0)) {
                    g.drawString("HIGH SCORE!", 275, 200);
                } else {
                    g.drawString("NICE TRY!", 290, 200);
                }
                for (int i = 0; i < 5; i++) {
                    g.drawString("" + (i + 1) + ".               " + basketballScores.get(i), 300, 350 + i * 50);
                }
            } else if (onSoccer) {
                if (score == soccerScores.get(0)) {
                    g.drawString("HIGH SCORE!", 275, 200);
                } else {
                    g.drawString("NICE TRY!", 290, 200);
                }
                for (int i = 0; i < 5; i++) {
                    g.setColor(LIGHT_ORANGE);
                    g.drawString("" + (i + 1) + ".               " + soccerScores.get(i), 300, 350 + i * 50);
                }
            }
            backButton.draw(g);
            playAgain.draw(g);
        } else if (onInstructions) {
            background = initBufferedImage("/sportsmania/images/instructionsScreen.png");
            g.drawImage(background, 0, 0, 800, 800, null);
            backButton.draw(g);
        } else {
            g.drawRect(50, 50, 50, 50);
        }
        g.dispose();
    }

    private void setScores() {
        BufferedWriter writer = null;
        if (onBasketball) {
            try {
                writer = new BufferedWriter(new FileWriter("basketballHS.txt", true));
            } catch (IOException e) {
                System.out.println("Error Occurred with BufferedWriter -- " + e.getMessage());
                System.exit(-1);
            }
            basketballScores.add(score);
            String tempString = Integer.toString(score);
            try {
                writer.write(Integer.toString(score));
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                System.out.println("Error occurred with Files.write -- " + e.getMessage());
                System.exit(-1);
            }
            sortArray(basketballScores);
        } else if (onSoccer) {
            try {
                writer = new BufferedWriter(new FileWriter("soccerHS.txt", true));
            } catch (IOException e) {
                System.out.println("Error Occurred with BufferedWriter -- " + e.getMessage());
                System.exit(-1);
            }
            soccerScores.add(score);
            try {
                writer.write(Integer.toString(score));
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                System.out.println("Error occurred with Files.write -- " + e.getMessage());
                System.exit(-1);
            }
            sortArray(soccerScores);
        }
    }

    private void sortArray(ArrayList<Integer> array) {
        Integer currentItem;
        for (int top = 1; top < array.size(); top++) {
            currentItem = array.get(top);
            int i = top;
            while (i > 0 && currentItem > array.get(i - 1)) {
                array.set(i, array.get(i - 1));
                i--;
            }
            array.set(i, currentItem);
        }
    }

    private void initLoadingScreen() {
        currentFact = facts[rand.nextInt(facts.length)];
        secondsPassed = 0;
        loading = true;
    }

    private void resetGame() {
        onBasketball = false;
        onSoccer = false;
        onHockey = false;
    }

    private void initGame() {
        score = 0;
        secondsPassed = 0;
        overshotTimer = -50;
        freezeSecond = 0;
        overshotTimer = 0;
        shotTimer = 0;
        freezeSecond = 0;
        goaliePos = -1;
        shotLeft = false;
        shotMiddle = false;
        shotRight = false;
        aiming = false;
        overshot = false;
        scored = false;
        shotBall = false;
        gameStarted = false;
        background = initBufferedImage("/sportsmania/images/enterScreen.png");
        if (onBasketball) {
            currentBall = B_BALL_IMAGE;
            currentNet = B_NET_IMAGE;
            net = new Net(FRAME_WIDTH / 2 - 125, 50, 250, 175, currentNet);
        } else if (onSoccer) {
            currentBall = S_BALL_IMAGE;
        }
        goalie = new Goalie(FRAME_WIDTH / 2 - 39, 125, 68, 84, "/sportsmania/images/soccerGoalieMiddle.png");
    }

    @Override
    public void paint(Graphics g) {
        Image image = createImage(FRAME_WIDTH, FRAME_HEIGHT);
        Graphics graphics = image.getGraphics();
        //   g.clearRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        paintComponent(graphics);
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (onGame) {
            if (gameStarted) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (!shotBall && secondsPassed - 30 > overshotTimer) {
                        freezeSecond = secondsPassed;
                        shotPower = (int) (100.0 * ((barSpeed * secondsPassed % 450.0) / 450));
                        double barPower = (secondsPassed * barSpeed / 450.0) % 2;
                        if ((int) barPower == 1) {
                            if (barPower > 1.5) {
                                overshot = true;
                                overshotTimer = secondsPassed;
                            } else {
                                overshot = false;
                            }
                        } else {
                            if (barPower > 0.5) {
                                overshot = false;
                            } else {
                                overshot = true;
                                overshotTimer = secondsPassed;
                            }
                        }
                        if (onBasketball) {
                            shotBall = true;
                            ball = new BasketBall(net, goalie, FRAME_WIDTH / 2 - 75, FRAME_HEIGHT - 175, 150, 150, currentBall);
                            ball.updateScore(this);
                        } else {
                            aiming = true;
                            goaliePos = rand.nextInt(3);
                            shotBall = true;
                            shotTimer = secondsPassed;
                        }
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    gamePaused = !gamePaused;
                    if (onBasketball) {
                        background = initBufferedImage("/sportsmania/images/basketballBackground.png");
                    } else if (onSoccer) {
                        background = initBufferedImage("/sportsmania/images/soccerBackground.png");
                    }
                    pauseTime = secondsPassed;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                initGame();
                gameStarted = true;
                if (onBasketball) {
                    ball = new BasketBall(net, goalie, 0, 0, 0, 0, "/sportsmania/images/nothing.png");
                    background = initBufferedImage("/sportsmania/images/basketballBackground.png");
                } else if (onSoccer) {
                    ball = new SoccerBall(net, goalie, 0, 0, 0, 0, "/sportsmania/images/nothing.png");
                    background = initBufferedImage("/sportsmania/images/soccerBackground.png");
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int xPos = e.getX();
        int yPos = e.getY();
        if (onMenu) {
            if (xPos >= (FRAME_WIDTH / 2 - 250) && xPos <= (FRAME_WIDTH / 2 + 250) && yPos >= 150 && yPos <= 250) { // clicked select game
                onMenu = false;
                onSelectGame = true;
                repaint();
            } else if (xPos >= (FRAME_WIDTH / 2 - 250) && xPos <= (FRAME_WIDTH / 2 + 250) && yPos >= 300 && yPos <= 400) {
                onMenu = false;
                onScores = true;
                repaint();
            } else if (xPos >= (FRAME_WIDTH / 2 - 250) && xPos <= (FRAME_WIDTH / 2 + 250) && yPos >= 450 && yPos <= 550) {
                onMenu = false;
                onInstructions = true;
                repaint();
            } else if (xPos >= (FRAME_WIDTH / 2 - 250) && xPos <= (FRAME_WIDTH / 2 + 250) && yPos >= 600 && yPos <= 700) {
                System.exit(0);
            }
        } else if (onSelectGame) {
            if (xPos >= (FRAME_WIDTH / 2 - 250) && xPos <= (FRAME_WIDTH / 2 + 250) && yPos >= 300 && yPos <= 400) { // clicked select game
                onSelectGame = false;
                onBasketball = true;
                initLoadingScreen();
                repaint();
            } else if (xPos >= (FRAME_WIDTH / 2 - 250) && xPos <= (FRAME_WIDTH / 2 + 250) && yPos >= 450 && yPos <= 550) {
                onSelectGame = false;
                onSoccer = true;
                initLoadingScreen();
                repaint();
            } else if (xPos >= 10 && xPos <= 210 && yPos >= 710 && yPos <= 785) {
                onSelectGame = false;
                onMenu = true;
                repaint();
            }
        } else if (onGame) {
            if (aiming) {
                if (xPos > 300 && xPos < 500) {
                    shotMiddle = true;
                } else if (xPos > 400) {
                    shotRight = true;
                } else {
                    shotLeft = true;
                }
                aiming = false;
                overshotTimer = secondsPassed;
                if (isOnBasketball()) {
                    ball = new BasketBall(net, goalie, FRAME_WIDTH / 2 - 75, FRAME_HEIGHT - 175, 150, 150, currentBall);
                } else if (isOnSoccer()) {
                    ball = new SoccerBall(net, goalie, FRAME_WIDTH / 2 - 75, FRAME_HEIGHT - 175, 150, 150, currentBall);
                }
                ball.updateScore(this);
            }
            if (xPos >= 10 && xPos <= 210 && yPos >= 710 && yPos <= 785) {
                resetGame();
                onGame = false;
                onMenu = true;
                repaint();
            }
        } else if (onScoreScreen) {
            if (xPos >= (FRAME_WIDTH / 2 - 250) && xPos <= (FRAME_WIDTH / 2 + 250) && yPos >= 580 && yPos <= 680) { // playAgain
                onScoreScreen = false;
                initLoadingScreen();
                repaint();
            } else if (xPos >= 10 && xPos <= 210 && yPos >= 710 && yPos <= 785) {
                onScoreScreen = false;
                resetGame();
                onMenu = true;
                repaint();
            }
        } else if (onInstructions) {
            if (xPos >= 10 && xPos <= 210 && yPos >= 710 && yPos <= 785) {
                onInstructions = false;
                onMenu = true;
                repaint();
            }
        } else if (onScores) {
            if (xPos >= 10 && xPos <= 210 && yPos >= 710 && yPos <= 785) {
                onScores = false;
                onMenu = true;
                repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * the shooting power of the ball
     *
     * @return shotPower
     */
    public int getShotPower() {
        return shotPower;
    }

    /**
     * set he shooting power of the ball
     *
     * @param shotPower
     */
    public void setShotPower(int shotPower) {
        this.shotPower = shotPower;
    }

    /**
     * return if the ball is over shooting
     *
     * @return true when the ball over shots
     */
    public boolean isOvershot() {
        return overshot;
    }

    /**
     * set to true if the ball over shots
     *
     * @param overshot
     */
    public void setOvershot(boolean overshot) {
        this.overshot = overshot;
    }

    /**
     * return ture when playing soccer game
     *
     * @return onSoccer
     */
    public boolean isOnSoccer() {
        return onSoccer;
    }

    /**
     * set ture when playing soccer game
     *
     * @param onSoccer
     */
    public void setOnSoccer(boolean onSoccer) {
        this.onSoccer = onSoccer;
    }

    /**
     * return ture when playing basketball game
     *
     * @return
     */
    public boolean isOnBasketball() {
        return onBasketball;
    }

    /**
     * set ture when playing basketball game
     *
     * @param onBasketball
     */
    public void setOnBasketball(boolean onBasketball) {
        this.onBasketball = onBasketball;
    }

    /**
     * return ture when ball scores
     *
     * @return
     */
    public boolean isScored() {
        return scored;
    }

    /**
     * set ture when ball scores
     *
     * @param scored
     */
    public void setScored(boolean scored) {
        this.scored = scored;
    }

    /**
     * return ture when the current scores
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * set ture when the current scores
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * return ture when the ball is aiming
     *
     * @return aiming
     */
    public boolean isAiming() {
        return aiming;
    }

    /**
     * set ture when the ball is aiming
     *
     * @param aiming
     */
    public void setAiming(boolean aiming) {
        this.aiming = aiming;
    }

    /**
     * return ture when current is on score screen
     *
     * @return onScoreScreen
     */
    public boolean isOnScoreScreen() {
        return onScoreScreen;
    }

    /**
     * set to ture when current is on score screen
     *
     * @param onScoreScreen
     */
    public void setOnScoreScreen(boolean onScoreScreen) {
        this.onScoreScreen = onScoreScreen;
    }

    /**
     * return ture when ball scores
     *
     * @return onScores
     */
    public boolean isOnScores() {
        return onScores;
    }

    /**
     * set to ture when ball scores
     *
     * @param onScores
     */
    public void setOnScores(boolean onScores) {
        this.onScores = onScores;
    }

    /**
     * return ture when playing hockey game
     *
     * @return onHockey
     */
    public boolean isOnHockey() {
        return onHockey;
    }

    /**
     * set to ture when playing hockey game
     *
     * @param onHockey
     */
    public void setOnHockey(boolean onHockey) {
        this.onHockey = onHockey;
    }

    /**
     * return ture when ball scores
     *
     * @return
     */
    public boolean isShotBall() {
        return shotBall;
    }

    /**
     * set to ture when ball scores
     *
     * @param shotBall
     */
    public void setShotBall(boolean shotBall) {
        this.shotBall = shotBall;
    }

    /**
     * return ture when ball shots left
     *
     * @return shotLeft
     */
    public boolean isShotLeft() {
        return shotLeft;
    }

    /**
     * set to ture when ball shots left
     *
     * @param shotLeft
     */
    public void setShotLeft(boolean shotLeft) {
        this.shotLeft = shotLeft;
    }

    /**
     * return ture when ball shots middle
     *
     * @return
     */
    public boolean isShotMiddle() {
        return shotMiddle;
    }

    /**
     * set ture when ball shots middle
     *
     * @param shotMiddle
     */
    public void setShotMiddle(boolean shotMiddle) {
        this.shotMiddle = shotMiddle;
    }

    /**
     * return ture when ball shots right
     *
     * @return
     */
    public boolean isShotRight() {
        return shotRight;
    }

    /**
     * set to ture when ball shots right
     *
     * @param shotRight
     */
    public void setShotRight(boolean shotRight) {
        this.shotRight = shotRight;
    }

    /**
     * return the current goalie position
     *
     * @return
     */
    public int getGoaliePos() {
        return goaliePos;
    }

    /**
     * set the current goalie position
     *
     * @param goaliePos
     */
    public void setGoaliePos(int goaliePos) {
        this.goaliePos = goaliePos;
    }

    /**
     * Call this method to start a game
     */
    public void start() {
        initLoadingScreen();
    }
}
