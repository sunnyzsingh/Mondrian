import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, ActionListener, Constants {

    Player player = null;
    Memory memory = null;
    Timer timer = null;
  //  Queue<Integer> my = new LinkedList<Integer>();
    /**
     * TODO Computer Gegner erstellen
     *
     */

    /**
     * TODO Aufgaben
     * - Spielabbruch wenn Computer Player oder Player-Linie(Queue) erwischt
     * - Spiel ist beendet wenn 80 % erreicht ( 32000 Einser) => Nachricht anzeigen
     *
     *
     */

    private int posx;
    /**
     * y Position of the Center of the Ball
     */
    private int posy;

    private double posxenemy;

    private double posyenemy;

    private double speedxenemy;
    private double speedyenemy;

    public static final int sizeenemy = 3;
    public static final int sizeenemycenter=(sizeenemy/2)+1;


    private static final int speed=1;//Movementspeed
    public static final int size = 3;
    /**
     * Size of the own Ball
     */
    private int sizecenter=(size/2)+1;

    private int[][] area;//Game area

    private boolean[][] areafill;//Array to help Fill an area

    Random rand = new Random();

    private boolean drawing = false;

    protected boolean lost=false;
    private int firstx;
    private int firsty;



    public Board() {
        super();

        addKeyListener(this);
        setFocusable(true);

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setDoubleBuffered(true);

        player = new Player(new Point(1,1));
        memory = new Memory(MEMORY_HEIGHT, MEMORY_WIDTH);

        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP :
                if(player.currentPosition().getY() > 0)
                    player.setPosition(0,-1);
                break;
            case KeyEvent.VK_LEFT :
                if(player.currentPosition().getX() > 0)
                    player.setPosition(-1,0);
                break;
            case KeyEvent.VK_DOWN :
                if(player.currentPosition().getY() < BOARD_HEIGHT-1)
                    player.setPosition(0,+1);
                break;
            case KeyEvent.VK_RIGHT :
                if(player.currentPosition().getX() < BOARD_WIDTH-1)
                    player.setPosition(+1,0);
                break;
            case KeyEvent.VK_ENTER :
                System.out.println(memory);
                break;
        }


        //player.exiting(memory);
       // player.reEnterying(memory);

        if(memory.checkPosition((int)player.currentPosition().getX(),(int)player.currentPosition().getY(),0)){
            player.addToRoute();
            memory.visited((int)player.currentPosition().getX(),(int)player.currentPosition().getY());
        }else {

            player.clearRoute();
        }
        /*if(!player.isOnUnsecureField()) {
            Point[] temp = getneighbours();
            if(temp != null){
                for(Point p: temp){
                    if(p != null){
                        memory.setChar((int)p.getX(),(int)p.getY(),5);
                    }

                }
            }
            //Floodfill aufruf
            player.clearRoute();
            memory.cleanUp();
            if(memory.hasPlayerWon()){
                System.out.println("Gewonnen");
                System.exit(0);
            }

        }*/

        System.out.println("Bewegt");
    }

    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFields(g);
        drawRoute(g);
        drawPlayer((Graphics2D) g);




    }

    public void drawPlayer(Graphics2D g){
        g.setStroke(new BasicStroke(PLAYER_SIZE));
        g.setColor(Color.BLUE);
        g.drawLine((int)player.currentPosition().getX(),(int)player.currentPosition().getY(),(int)player.currentPosition().getX(),(int)player.currentPosition().getY());
    }

    public void drawRoute(Graphics g){
        ((Graphics2D) g).setStroke(new BasicStroke(PLAYER_SIZE));
        g.setColor(Color.BLACK);
        g.drawPolyline(parseToArray(player.getRoute(),true),parseToArray(player.getRoute(),false), player.getRoute().size());
    }

    public void drawFields(Graphics g){
        g.setColor(Color.YELLOW);
        for (int i = 0; i < memory.getMemory().length; i++) {
            for (int j = 0; j < memory.getMemory()[i].length; j++) {
                if(memory.getMemory()[j][i] == 1)
                    g.drawRect(i, j, 1, 1);

                if(memory.getMemory()[j][i] == 2) {
                    g.drawRect(i, j, 1, 1);

                }
            }
        }
    }


    /**
     * Hilfsmethode um ein X oder Y int - Array aus einer Queue zu erhalten ( für Polygonline )
     */
    public static int[] parseToArray(Deque<Point> deque, boolean getX){
        int[] result = new int[deque.size()];
        int i = 0;
        if(getX){
            for (Point point : deque) {
                result[i] = (int)point.getX();
                i++;
            }
        } else {
            for (Point point : deque) {
                result[i] = (int)point.getY();
                i++;
            }
        }
        return result;
    }

    /*
     * Methode um iterativ die Fläche zu füllen
     */
    private boolean filliter(int x,int y) {
        /*
        if (areafill[posx][posy]||area[posx][posy]!=0)
            return true;
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(posx, posy));
        while (!queue.isEmpty())
        {
            Point n = queue.poll();
            areafill[n.x][n.y]=true;
            for (int i = 1; i <= 8; i++)
            {
                if (getneighbors(i, n.x, n.y) == 0 && !areafill[getcoordsofIndex(i, n.x, n.y).x][getcoordsofIndex(i, n.x, n.y).y])
                {
                    queue.offer(new Point(getcoordsofIndex(i, n.x, n.y).x, getcoordsofIndex(i, n.x, n.y).y));
                    areafill[getcoordsofIndex(i, n.x, n.y).x][getcoordsofIndex(i, n.x, n.y).y]=true;
                }
                else if (getneighbors(i,n.x, n.y)==3)
                    return false;
            }
        }
        for (int i = 0; i < area.length; i++)
        {
            for (int j = 0; j < area[i].length; j++)
            {
                if (areafill[i][j])
                    area[i][j]=1;
            }
        }*/
        return true;
    }

    public Point[] getneighbours() {
        int x = (int) player.currentPosition().getX();
        int y = (int) player.currentPosition().getY();
        Point[] result = new Point[8]; // Im Uhrzeigersinn befüllen
        int i = 0;
        int[][] neighbours = {{-1,-1},{0,-1},{+1,-1},{+1,0},{+1,+1},{0,+1},{-1,+1},{-1,0}};
        while (i < 8){
            int tempX = x + neighbours[i][0];
            int tempY = y + neighbours[i][1];
            if (tempX < 0 || tempX >= BOARD_WIDTH) {
                i++;
                continue;
            }
            if (tempY < 0 || tempY >= BOARD_HEIGHT) {
                i++;
                continue;
            }

            if (!memory.checkPosition(tempX, tempY, 0)) {
                i++;
                continue;
            } else {
                result[i] = new Point(tempX, tempY);
                i++;
            }


        }
        return result;
    }

    private void moveenemy()
    {
        double[] speedpos = new double[4];
        area[(int)posxenemy][(int)posyenemy]=0;
        for (int i = 0; i < speedpos.length; i++)
        {
            switch (i)
            {
                case 0:
                    speedpos[i]=speedxenemy;
                    break;
                case 1:
                    speedpos[i]=speedyenemy;
                    break;
                case 2:
                    speedpos[i]=posxenemy;
                    break;
                case 3:
                    speedpos[i]=posyenemy;
                    break;
            }
        }
        EnemyMove.move(speedpos);
        for (int i = 0; i < speedpos.length; i++)
        {
            switch (i)
            {
                case 0:
                    speedxenemy=speedpos[i];
                    break;
                case 1:
                    speedyenemy=speedpos[i];
                    break;
                case 2:
                    posxenemy=speedpos[i];
                    break;
                case 3:
                    posyenemy=speedpos[i];
                    break;
            }
        }

        if (posxenemy==posx&&posyenemy==posy||area[(int)posxenemy][(int)posyenemy]==2)
            lost();
        area[(int)posxenemy][(int)posyenemy]=3;
    }

    private void lost()
    {
        System.out.println("Lost the Game");
        lost=true;
    }

}