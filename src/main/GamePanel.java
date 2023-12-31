import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //Screen setting
    final int originalTileSize =16;//16x16
    final int scale=3;

    final int tileSize=originalTileSize*scale;//48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth=tileSize*maxScreenCol;//768 pixel
    final int screenHeight=tileSize*maxScreenRow;//576 pixel

    //FPS
    int FPS=60;
    KeyHandler keyH=new KeyHandler();
    Thread gameThread;
    //setPlayer position
    int playerX=100;
    int playerY=100;
    int playerSpeed=4;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval =1000000000/FPS;//0.0166666second
        double nextDrawTime =System.nanoTime()+drawInterval;
        while(gameThread != null){

            update();

            repaint();

            try {
                double remainingTime=nextDrawTime-System.nanoTime();
                remainingTime=remainingTime/1000000;
                if(remainingTime<0){
                    remainingTime=0;
                }
                Thread.sleep((long)remainingTime);

                nextDrawTime +=drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update(){
        if(keyH.upPress==true){
            playerY-=playerSpeed;
        }
        else if(keyH.downPress==true){
            playerY+=playerSpeed;
        }
        else if(keyH.leftPress==true){
            playerX-=playerSpeed;
        }
        else if(keyH.rightPress==true){
            playerX+=playerSpeed;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(playerX,playerY,100,100);
        g2.dispose();
    }
}
