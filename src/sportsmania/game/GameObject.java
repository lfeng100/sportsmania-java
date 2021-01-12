package sportsmania.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

/**
 * A GameObject is the base class for a number of subclasses which encapsulate object during playing a game 
 * @author FengLeo
 */
public abstract class GameObject {
    private Rectangle rect;
    private int x;
    private int y;
    private int height;
    private int width;
    private Image img;
    
    /**
     *
     * @param x
     * @param y
     * @param height
     * @param width
     * @param img
     */
    protected GameObject(int x, int y, int height, int width, String img) {
        this.setX(x);
        this.setY(y);
        this.setHeight(height);
        this.setWidth(width);
        this.setRect(new Rectangle(x, y, width, height));
        this.setImg(this.getImage(img));
    }
    
    public abstract void update(final Game game);
    
    /**
     *
     * @param g
     */
    protected void draw(Graphics g) {
        g.drawImage(getImg(), getX(), getY(), getWidth(), getHeight(), null);
    }
      
    /**
     *
     * @param img
     * @return
     */
    protected Image getImage(String img) {
        
        Image image = null;
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream(img));
        } catch (Exception ex) {
            System.out.println("Error Occured with Load Image " + img);
            System.exit(-1);
        }
        
        return image;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @param img
     */
    public void setImg(Image img) {
        this.img = img;
    }
    
     /**
     *
     * @param rect
     */
    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return
     */
    public Image getImg() {
        return img;
    }

    /**
     *
     * @return
     */
    public Rectangle getRect() {
        return rect;
    }
    
    /**
     *
     * @param a
     * @param sign
     */
    protected void addToX(int a, boolean sign) {
        if (sign) {
            this.setX(this.getX() + a);
        } else {
            this.setX(this.getX() - a);
        }
    }
    
    /**
     *
     * @param a
     * @param sign
     */
    protected void addToY(int a, boolean sign) {
        if (sign) {
            this.setY(this.getY() + a);
        } else {
            this.setY(this.getY() - a);
        }
    }
    
    /**
     *
     * @param a
     * @param sign
     */
    protected void addToWidth(int a, boolean sign) {
        if (sign) {
            this.setWidth(this.getWidth() + a);
        } else {
            this.setWidth(this.getWidth() - a);
        }
    }
    
    /**
     *
     * @param a
     * @param sign
     */
    protected void addToHeight(int a, boolean sign) {
        if (sign) {
            this.setHeight(this.getHeight() + a);
        } else {
            this.setHeight(this.getHeight() - a);
        }
    }
}
