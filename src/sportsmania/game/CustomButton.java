package sportsmania.game;

import java.awt.*;
import javax.swing.JButton;

/**
 * A class define a button used in this game. It extends from JButton
 * @author FengLeo
 */
public class CustomButton extends JButton {

    private Dimension size;

    private int x;
    private int y;
    private int width;
    private int height;
    private int fontSize;
    private int textShift;

    private Image image;
    private Graphics graphics;

    boolean hover = false;
    boolean click = false;

    String text = "";

    /**
     * Construct a CustomButton whose upper-left corner is specified as (x,y) 
     * and whose width and height are specified by the arguments of the same name 
     * 
     * @param text
     * @param x
     * @param y
     * @param width
     * @param height
     * @param fontSize
     * @param textShift
     */
    public CustomButton(String text, int x, int y, int width, int height, int fontSize, int textShift) {
        setVisible(true);
        setFocusable(true);
        setContentAreaFilled(false);
        setBorderPainted(false);

        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fontSize = fontSize;
        this.textShift = textShift;
        size = new Dimension(width, height);

    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /**
     * return the font size for this button
     * @return fontSize
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * return the text shifted replacement for this button
     * @return textShift
     */
    public int getTextShift() {
        return textShift;
    }

    @Override
    public String getText() {
        return text;
    }
    
    /**
     * return the image of this button
     * @return image
     */
    public Image getImage() {
        return image;
    }

    /**
     * return the image which renders the button
     * @return graphics
     */
    public Graphics getGraphics() {
        return graphics;
    }

    /**
     * return if mouse hover on this button
     * @return boolean
     */
    public boolean isHover() {
        return hover;
    }

    /**
     * return if mouse clicks this button
     * @return boolean
     */
    public boolean isClick() {
        return click;
    }

    @Override
    public void setSize(Dimension size) {
        this.size = size;
    }

    /**
     * set upper-left x position
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * set upper-left y position
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * set width of this button
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * set height of this button
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * set font size for this button
     * @param fontSize
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * set text shifted replacement of this button
     * @param textShift
     */
    public void setTextShift(int textShift) {
        this.textShift = textShift;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    /**
     * set the image for rendering this button
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * set graphics for rendering this button
     * @param graphics
     */
    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    /**
     * set true when mouse is hover on this button
     * @param hover
     */
    public void setHover(boolean hover) {
        this.hover = hover;
    }

    /**
     * set true when mouse clicks on this button
     * @param click
     */
    public void setClick(boolean click) {
        this.click = click;
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    @Override
    public Dimension getMaximumSize() {
        return size;
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }
    
    /**
     * a method to render this button
     * @param g
     */
    public void draw(Graphics g) {
        if (click) {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }

        g.setColor(new Color(0, 50, hover ? 255 : 180));
        g.fillRect(x, y, width, 7);
        g.fillRect(x, y + height - 7, width, 7);
        g.fillRect(x, y, 7, height);
        g.fillRect(x + width - 7, y, 7, height);

        g.setColor(new Color(255, 153, 51));

        g.fillRect(x + 14, y + 14, width - 28, height - 28);

        g.setColor(Color.WHITE);

        g.setFont(new Font("Garamond", Font.PLAIN, fontSize));
        g.drawString(text, x + width / 2 - (text.length() * 14) + textShift, y + height / 2 + (30 * fontSize / 100));
    }
}
