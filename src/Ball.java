import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by Manpreet on 03.07.2015.
 */
public class Ball extends JFrame {


    private double posX, posY; //position
    private double geX, geY; //geschwindigkeit;
    private final double radius;
    public Ball()
    {
      //  super();
        this.posX = 0;
        this.posY = 0;
        this.geX = 0.015 - Math.random() * 0.03;
        this.geY = 0.015 - Math.random() * 0.03;
        this.radius = 0.015 - Math.random() * 0.03;

    }

    public void  bounceOffVerticalWall()
    {
        this.geX = -this.geX;
    }
    public void bounceOffHorizontalWall()
    {
        this.geY = -this.geY;
    }

    public void move()
    {
        if (Math.abs(this.posX + this.geX) + radius > 1.0) bounceOffVerticalWall();
        if (Math.abs(this.posY + this.geY) + radius > 1.0) bounceOffHorizontalWall();
        this.posX = this.posX + this.geX;
        this.posY = this.posY + this.geY;

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponents(g);


    }



}
