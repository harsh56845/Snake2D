import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePannel extends JPanel implements ActionListener , KeyListener {

    private int[] snakexlength = new int[750];
    private int[] snakeylength = new int[750];
    private  int lengthOfSnake = 3;

    private int[] xPos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] yPos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    private Random random = new Random();
    private int enemyX,enemyY;



    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private int moves = 0;
    private  int score = 0;
    private boolean gameOver = false;

    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));

    private Timer timer;
    private int delay=100;
    GamePannel(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay,this);
        timer.start();

        newEnemy();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);

        g.setColor(Color.black);

        g.fillRect(25,75,850,575);

        snaketitle.paintIcon(this,g,25,11);

        if(moves==0){
            snakexlength[0]=100;
            snakexlength[1]=75;
            snakexlength[2]=50;

            snakeylength[0]=100;
            snakeylength[1]=100;
            snakeylength[2]=100;
        }

        if(left){
            leftmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(right){
            rightmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(up){
            upmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(down){
            downmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }

        for(int i=1;i<lengthOfSnake;i++){
            snakeimage.paintIcon(this,g,snakexlength[i],snakeylength[i]);
        }

        enemy.paintIcon(this,g,enemyX,enemyY);

        if(gameOver){
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.drawString("Game Over",300,300);

            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Press SPACE To Restart",320,350);

//            g.setFont(new Font("Arial",Font.PLAIN,20));
//            g.drawString("Thanks For Playing",320,450);

            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Made By Harsh",730,640);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,14));
        g.drawString("Score : "+score,750,35);
        g.drawString("Length : "+lengthOfSnake,750,55);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i=lengthOfSnake-1;i>0;i--){
            snakexlength[i]=snakexlength[i-1];
            snakeylength[i]=snakeylength[i-1];
        }
        if(left){
            snakexlength[0]=snakexlength[0]-25;
        }
        if(right){
            snakexlength[0]=snakexlength[0]+25;
        }
        if(up){
            snakeylength[0]=snakeylength[0]-25;
        }
        if(down){
            snakeylength[0]=snakeylength[0]+25;
        }

        if(snakexlength[0]>850) snakexlength[0]=25;
        if(snakexlength[0]<25) snakexlength[0]=850;
        if(snakeylength[0]>625) snakeylength[0]=75;
        if(snakeylength[0]<75) snakeylength[0]=625;

        collidesWithEnemy();
        collidesWithBody();

        repaint();
    }

    // for the movement of snake up , down , left , right using keyEvent Method
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }
        // If pressed Left Key
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)){
            left = true;
            right = false;
            up = false;
            down = false;
            moves++;
        }
        // If pressed right Key
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;
        }
        // If pressed up Key
        if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;
        }
        // If pressed down Key
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void newEnemy() {
        enemyX=xPos[random.nextInt(34)];
        enemyY=yPos[random.nextInt(23)];

        for(int i=lengthOfSnake-1;i>=0;i--){
            if(snakexlength[i]==enemyX && snakeylength[i]==enemyY){
                newEnemy();
            }
        }
    }

    private void collidesWithEnemy() {
        if(snakexlength[0]==enemyX && snakeylength[0]==enemyY){
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }

    private void collidesWithBody() {
        for(int i=lengthOfSnake-1;i>0;i--){
            if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0]){
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void restart() {
        gameOver = false;
        moves = 0;
        score = 0;
        lengthOfSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }

}
