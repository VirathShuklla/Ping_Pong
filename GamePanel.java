import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
// it is the panel in which we are going to play game
public class GamePanel extends JPanel implements Runnable{
    static final int GAME_WIDTH = 1200;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (5.0/9));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    int PADDLE_WIDTH = 25;
    int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){
        //        Our constructor
        newPaddles();
        newBall();
        score = new Score(GAME_HEIGHT,GAME_WIDTH);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread= new Thread(this);
        gameThread.start();
    }
    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),(GAME_HEIGHT/2)-(BALL_DIAMETER/2), BALL_DIAMETER,BALL_DIAMETER);
    }
    public void newPaddles(){
        random = new Random();

        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }
    public void newPaddles1(){
        random = new Random();
        PADDLE_HEIGHT= this.random.nextInt(100, 200);

        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
    }
    public void newPaddle2(){
        random = new Random();
        PADDLE_HEIGHT= this.random.nextInt(100, 200);

        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
    }
    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }
    public void checkCollision(){
        //stops paddles at window edges
        if (paddle1.y<=0){
            paddle1.y=0;
        }

        if (paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle1.y= GAME_HEIGHT-PADDLE_HEIGHT;
//            new Paddle(paddle1.x,paddle1.y, PADDLE_WIDTH,random.nextInt(100,1000), paddle1.id);
        }
        if (paddle2.y<=0){
            paddle2.y=0;
        }
        if (paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle2.y= GAME_HEIGHT-PADDLE_HEIGHT;
        }
        //ball
        if (ball.y<=0){
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= (GAME_HEIGHT-BALL_DIAMETER)){
            ball.setYDirection(-ball.yVelocity);
        }

        //ball paddle
        if (ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
//            ball.xVelocity *= (random.nextFloat(80,120))/100;
            ball.xVelocity *= 1.05122203;
            ball.setXDirection(+ball.xVelocity);
            newPaddle2();
        }
        if (ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity *= 1.05122203;
            ball.setXDirection(-ball.xVelocity);
            newPaddles1();
        }

        //score
        if (ball.x <=0){
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Player 1:  " + score.player1 +"    "+ "Player 2:  " + score.player2);
        }
        if (ball.x >= GAME_WIDTH){
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Player 1:  " + score.player1 +"    "+ "Player 2:  " + score.player2);
        }
    }
    public void run(){
        //game loop
        long lastTime = System.nanoTime();
        double amountofTicks=60.0;
        double ns = 1000000000/amountofTicks;
        double delta = 0;
        while(true)  {
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            if (delta >=1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
