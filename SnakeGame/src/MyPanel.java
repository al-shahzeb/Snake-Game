import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class MyPanel extends JPanel implements KeyListener, ActionListener {
    Image img = new ImageIcon("src\\gameTitle.jpg").getImage();
    Image left = new ImageIcon("src\\leftmouth.png").getImage();
    Image right = new ImageIcon("src\\rightmouth.png").getImage();
    Image up = new ImageIcon("src\\upmouth.png").getImage();
    Image down = new ImageIcon("src\\downmouth.png").getImage();
    Image food = new ImageIcon("src\\enemy.png").getImage();
    Image snakeBody = new ImageIcon("src\\snakeImage.png").getImage();

    boolean isUp = false;
    boolean isDown = false;
    boolean isRight = true;
    boolean isLeft = false;


    Random ran = new Random();
    boolean gameOver=false;
    int score=0;
    int[] snakeX = new int[35];
    int[] snakeY = new int[35];
    int xFoodPos = (ran.nextInt(32)+1)*25;
    int yFoodPos = (ran.nextInt(19)+3)*25;
    int move = 0;
    int lengthOfSnake = 3;

    Timer time;
    MyPanel() {
        addKeyListener(this);
        setFocusable(true);
        this.setBackground(Color.darkGray);
        time = new Timer(100, this);
        time.start();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);
        g.drawImage(img, 25, 11, this);
        g.setFont(new Font("MV Boli",Font.BOLD,20));
        g.setColor(Color.black);
        g.drawString("Score: "+score, 750,40);
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);

        if (move == 0) {
            snakeX[0] = 100;
            snakeX[1] = 75;
            snakeX[2] = 50;

            snakeY[0] = 100;
            snakeY[1] = 100;
            snakeY[2] = 100;
        }


        if (isRight)
            g.drawImage(right, snakeX[0], snakeY[0], this);
        if (isDown)
            g.drawImage(down, snakeX[0], snakeY[0], this);
        if (isLeft)
            g.drawImage(left, snakeX[0], snakeY[0], this);
        if (isUp)
            g.drawImage(up, snakeX[0], snakeY[0], this);

        for (int i = 1; i < lengthOfSnake; i++)
            g.drawImage(snakeBody, snakeX[i], snakeY[i], this);


        g.drawImage(food,xFoodPos,yFoodPos,this);

        if(gameOver){
            g.setColor(Color.white);
            g.setFont(new Font("Comic snas",Font.BOLD,50));
            g.drawString("GAME OVER", 300,300);
            g.setFont(new Font("Comic snas",Font.ITALIC,30));
            g.drawString("Press -Space- to restart",290,330);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE && gameOver){
            move=0;
            score=0;
            gameOver=false;
            time.start();
            lengthOfSnake=3;
            isRight=true; isLeft=false; isUp=false; isDown=false;
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && (!isLeft)) {
            isUp = false;
            isLeft = false;
            isDown = false;
            isRight = true;
            move++;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && (!isRight)) {
            isUp = false;
            isLeft = true;
            isDown = false;
            isRight = false;
            move++;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && (!isDown)) {
            isUp = true;
            isLeft = false;
            isDown = false;
            isRight = false;
            move++;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && (!isUp)) {
            isUp = false;
            isLeft = false;
            isDown = true;
            isRight = false;
            move++;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = lengthOfSnake-1; i >0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        if (isLeft)
            snakeX[0] = snakeX[0] - 25;
        if (isRight)
            snakeX[0] = snakeX[0] + 25;
        if (isUp)
            snakeY[0] = snakeY[0] - 25;
        if (isDown)
            snakeY[0] = snakeY[0] + 25;

        if (snakeX[0] > 850) snakeX[0] = 25;
        if (snakeX[0] < 25) snakeX[0] = 850;
        if (snakeY[0] > 625) snakeY[0] = 75;
        if (snakeY[0] < 75) snakeY[0] = 625;
        checkCollision();
        repaint();
    }

    private void checkCollision() {
        for(int i=1; i<lengthOfSnake; i++)
            if(snakeX[0]==snakeX[i] && snakeY[0]==snakeY[i]){
                time.stop();
                gameOver=true;
                repaint();
            }
        if(snakeX[0]==xFoodPos && snakeY[0]==yFoodPos){
            lengthOfSnake++;
            score++;
            snakeX[lengthOfSnake-1]=snakeX[lengthOfSnake-2];
            snakeY[lengthOfSnake-1]=snakeY[lengthOfSnake-2];
            xFoodPos= (ran.nextInt(32)+1)*25;
            yFoodPos= (ran.nextInt(19)+3)*25;
            for(int i=0; i<lengthOfSnake; i++)
                if(snakeX[i]==xFoodPos && snakeY[i]==yFoodPos){
                    xFoodPos= (ran.nextInt(32)+1)*25;
                    yFoodPos= (ran.nextInt(19)+3)*25;
                }
        }
    }
}