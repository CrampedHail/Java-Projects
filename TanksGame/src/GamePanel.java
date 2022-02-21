import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    static final int GAME_WIDTH=1200;
    static final int GAME_HEIGHT=(int) (GAME_WIDTH * (5.0/9.0));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int PROJECTILE_DIAMETER=20;
    static final int PLAYERS_PLAYING=2;
    static final Dimension TANK_SIZE = new Dimension(30, 30);
    Thread GameThread;
    Image image;
    Graphics graphics;
    Random r;
    static Tank[] tank;
    Board board;
    Score score;
    Color[] playerColors = new Color[]{new Color(50, 50, 255), new Color(252, 67, 67), new Color(100, 173, 71), new Color(255, 235, 53)};
    int[][] PLAYER_CONTROLS = new int[][]{
            {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE},
            {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER}};

    GamePanel(){
        r = new Random();
        newTanks();
        board = new Board(GAME_WIDTH, GAME_HEIGHT);
        score = new Score(GAME_WIDTH, GAME_HEIGHT, playerColors[0], playerColors[1]);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        GameThread = new Thread(this);
        GameThread.start();
    }

    public void newTanks(){
        tank = new Tank[PLAYERS_PLAYING];
        for (int i = 0; i < PLAYERS_PLAYING; i++) {
            tank[i] = new Tank(((GAME_WIDTH-100- TANK_SIZE.width)*i)+50, 100, playerColors[i], PLAYER_CONTROLS[i][0], PLAYER_CONTROLS[i][1], PLAYER_CONTROLS[i][2], PLAYER_CONTROLS[i][3], PLAYER_CONTROLS[i][4]);
            System.out.println("PLAYER 1 shoot at: "+PLAYER_CONTROLS[i][4]);
        }
    }

    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }
    public void draw(Graphics g){
        score.draw(g);
        tank[0].draw(g);
        tank[1].draw(g);
        board.draw(g);
    }
    public void move(){
        tank[0].move();
        tank[1].move();
    }
    public void checkCollision(){
        for (int i = 0; i < PLAYERS_PLAYING; i++) {
            if(tank[i].projectile != null){
                if(tank[i].projectile.x > GAME_WIDTH ||
                        tank[i].projectile.x + Projectile.SIZE.width < 0 ||
                        tank[i].projectile.y + Projectile.SIZE.width <= 70 ||
                        tank[i].projectile.y>GAME_HEIGHT){
                    tank[i].projectile=null;
                }
            }
            int j = PLAYERS_PLAYING-1-i;

            if(tank[i].projectile != null && tank[i].projectile.intersects(tank[j])){
                tank[j].gotHit();
                if(tank[j].isDead()){
                    score.player[i]++;
                    tank[j].spawn((GAME_WIDTH-tank[i].x+r.nextInt(200))%GAME_WIDTH, (GAME_HEIGHT-tank[i].y+r.nextInt(200))%GAME_HEIGHT);
                    System.out.println("Tank "+j+" is dead, spawning new tank");
                }
                tank[i].projectile = null;
            }

            if(tank[i].x<=0) tank[i].x=0;
            if(tank[i].x>=GAME_WIDTH - TANK_SIZE.width) tank[i].x=GAME_WIDTH - TANK_SIZE.width;
            if(tank[i].y<=70) tank[i].y=70;
            if(tank[i].y>= GAME_HEIGHT - TANK_SIZE.height) tank[i].y=GAME_HEIGHT - TANK_SIZE.height;
        }
    }

    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta>=1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            tank[0].keyPressed(e);
            tank[1].keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            tank[0].keyReleased(e);
            tank[1].keyReleased(e);
        }
    }

}
