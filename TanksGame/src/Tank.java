import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank extends Rectangle{

    int yVelocity;
    int xVelocity;
    int CONTROLS_UP;
    int CONTROLS_DOWN;
    int CONTROLS_LEFT;
    int CONTROLS_RIGHT;
    int CONTROLS_SHOOT;
    static int speed = 2;
    int shot;
    final static int MAX_HP=3;
    int HP;
    Projectile projectile;
    final static int SHOT_READY=120;
    int orientation;
    Color color;
    int canonX = x , canonY = y, canonWidth = 10, canonHeight = 15;

    Tank(int x, int y, Color c, int up, int down, int left, int right, int shoot){
        super(x, y, GamePanel.TANK_SIZE.width, GamePanel.TANK_SIZE.height);
        Random r = new Random();
        orientation = r.nextInt(4);
        shot = 0;
        color = c;
        CONTROLS_UP = up;
        CONTROLS_DOWN = down;
        CONTROLS_LEFT = left;
        CONTROLS_RIGHT = right;
        CONTROLS_SHOOT = shoot;
        canonX = x;
        canonY = y;
        canonWidth = 10;
        canonHeight = 15;
        HP = 3;
    }
    void spawn(int x, int y){
        this.x = x;
        this.y = y;
        Random r = new Random();
        orientation = r.nextInt(4);
        shot = 0;
        canonX = x;
        canonY = y;
        canonWidth = 10;
        canonHeight = 15;
        HP = 3;
    }

    void shoot(){
        projectile = new Projectile(canonX, canonY, orientation);
        reload();
    }

    void reload(){
        shot = 0;
    }

    void gotHit(){
        HP--;
    }

    boolean isDead(){
        return HP<=0;
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==CONTROLS_UP){
            setYDirection(-speed);
            move();
        }
        if(e.getKeyCode()==CONTROLS_DOWN){
            setYDirection(speed);
            move();
        }
        if(e.getKeyCode()==CONTROLS_LEFT){
            setXDirection(-speed);
            move();
        }
        if(e.getKeyCode()==CONTROLS_RIGHT){
            setXDirection(speed);
            move();
        }
        if(e.getKeyCode()==CONTROLS_SHOOT){
            if(shot == SHOT_READY) shoot();
        }
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==CONTROLS_UP && yVelocity<0){
            setYDirection(0);
            move();
        }
        if(e.getKeyCode()==CONTROLS_DOWN && yVelocity>0){
            setYDirection(0);
            move();
        }
        if(e.getKeyCode()==CONTROLS_LEFT && xVelocity<0){
            setXDirection(0);
            move();
        }
        if(e.getKeyCode()==CONTROLS_RIGHT && xVelocity>0){
            setXDirection(0);
            move();
        }
    }

    public void setYDirection(int yVelocity){
        this.yVelocity = yVelocity;
        if(yVelocity<0) orientation = 0;
        if(yVelocity>0) orientation = 2;
    }

    public void setXDirection(int xVelocity){
        this.xVelocity = xVelocity;
        if(xVelocity<0) orientation = 3;
        if(xVelocity>0) orientation = 1;
    }

    public void move(){
        if(Math.abs(yVelocity)!=Math.abs(xVelocity)){
            if(yVelocity!=0){
                if(yVelocity>0){
                    orientation=2;
                }
                if(yVelocity<0){
                    orientation=0;
                }
            }
            if(xVelocity!=0){
                if(xVelocity>0){
                    orientation=1;
                }
                if(xVelocity<0){
                    orientation=3;
                }
            }
        }
        if(shot<SHOT_READY) shot++;
        if(projectile != null) projectile.move();
        y += yVelocity;
        x += xVelocity;
    }

    public void draw(Graphics g){
        if(projectile != null) projectile.draw(g);

        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 3, 3);
        switch (orientation){
            case 0 ->{
                canonWidth = 10;
                canonHeight = 15;
                canonX = (int)(x+(width/2.0)-(canonWidth/2.0));
                canonY = y-10;
            }
            case 1 ->{
                canonWidth = 15;
                canonHeight = 10;
                canonX = x+width-10;
                canonY = (int)(y+(height/2.0)-(canonHeight/2.0));
            }
            case 2 ->{
                canonWidth = 10;
                canonHeight = 15;
                canonX = (int)(x+(width/2.0)-(canonWidth/2.0));
                canonY = y+height-10;
            }
            case 3 ->{
                canonWidth = 15;
                canonHeight = 10;
                canonX = x-10;
                canonY = (int)(y+(height/2.0)-(canonHeight/2.0));
            }
        }

        g.fillRect(canonX, canonY, canonWidth, canonHeight);
        g.setColor(GameFrame.background);
        g.drawRect(canonX, canonY, canonWidth, canonHeight);
    }
}
