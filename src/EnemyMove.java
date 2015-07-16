/**
 * Created by Manpreet on 16.07.2015.
 */
public class EnemyMove implements  Constants{


        public static double[] move(double[] speedpos)//[0]speedx[1]speedy[2]posx[3]posy
        {
            int sizeenemy=PLAYER_SIZE;
            double speedx= 0;
            double speedy= 0;
            double posx= 0;
            double posy= 0;
            for (int i = 0; i < speedpos.length; i++)
            {
                switch (i)
                {
                    case 0:
                        speedx=speedpos[i];
                        break;
                    case 1:
                        speedy=speedpos[i];
                        break;
                    case 2:
                        posx=speedpos[i];
                        break;
                    case 3:
                        posy=speedpos[i];
                        break;
                }
            }

            //Bewegung + Randkontrolle
            if (posx+speedx>sizeenemy)//ganzlinks
            {
                if (posx+speedx<Board.WIDTH-sizeenemy)//ganzrechts
                {
                    posx += speedx;
                }
                else
                {
                    posx=Board.WIDTH-sizeenemy;
                    speedx=-speedx;
                }
            }
            else
            {
                posx=sizeenemy;
                speedx=-speedx;
            }
            if (posy+speedy>sizeenemy)//ganzoben
            {
                if (posy+speedy<Board.HEIGHT-sizeenemy)//ganzunten
                {
                    posy += speedy;
                }
                else
                {
                    posy=Board.HEIGHT-sizeenemy;
                    speedy=-speedy;
                }
            }
            else
            {
                posy=sizeenemy;
                speedy=-speedy;
            }


            for (int i = 0; i < speedpos.length; i++)
            {
                switch (i)
                {
                    case 0:
                        speedpos[i]=speedx;
                        break;
                    case 1:
                        speedpos[i]=speedy;
                        break;
                    case 2:
                        speedpos[i]=posx;
                        break;
                    case 3:
                        speedpos[i]=posy;
                        break;
                }
            }

            return speedpos;
        }

}
