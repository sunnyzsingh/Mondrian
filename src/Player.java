import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

public class Player implements Constants {

    private int x;
    private int y;
    private Deque<Point> route;
    private boolean onUnsecureField = false;
    private boolean drawing = false;

    public Player(Point p){
        this.x = (int) p.getX();
        this.y = (int) p.getY();
        route = new LinkedList<Point>();

    }

    public Point currentPosition(){
        return new Point(x,y);
    }

    public void setPosition(int x, int y){
        this.x += x;
        this.y += y;
    }

    public void addToRoute(){
        route.add(currentPosition());
    }

    public Deque<Point> getRoute(){
        return route;
    }

    public void clearRoute(){
        route.clear();
    }

    public void exiting(Memory memory){
        if( memory.getMemory()[(int)currentPosition().getY()][(int)currentPosition().getX()] == 0
                && memory.getMemory()[(int)route.peek().getY()][(int)route.peek().getX()] == 1){
            onUnsecureField = true;
        }
    }

    public void reEnterying(Memory memory){
        if(memory.getMemory()[(int)currentPosition().getY()][(int)currentPosition().getX()] == 1
                && memory.getMemory()[(int)route.peek().getY()][(int)route.peek().getX()] == 2){
            onUnsecureField = false;
        }
    }

    public boolean isOnUnsecureField(){
        return onUnsecureField;
    }



}
