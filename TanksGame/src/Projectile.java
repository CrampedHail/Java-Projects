import java.awt.*;

public class Projectile extends Rectangle {

    final static Dimension SIZE = new Dimension(10, 2);
    int xVelocity = 0;
    int yVelocity = 0;
    final int speed = 25;
    int orientation;

    Projectile(int x, int y, int o){
        int a = SIZE.width, b = SIZE.height;
        switch (o){
            case 0 ->{
                this.width = b;
                this.height = a;
                this.x = x+4;
                this.y = y;
                yVelocity = -speed;
            }
            case 1 ->{
                this.width = a;
                this.height = b;
                this.x = x;
                this.y = y+4;
                xVelocity = speed;
            }
            case 2 ->{
                this.width = b;
                this.height = a;
                this.x = x+4;
                this.y = y-height;
                yVelocity = speed;
            }
            case 3 ->{
                this.width = a;
                this.height = b;
                this.x = x-width;
                this.y = y+4;
                xVelocity = -speed;
            }
        }
    }

    public void move(){
        y += yVelocity;
        x += xVelocity;
    }


    public void draw(Graphics g){
        g.setColor(Color.WHITE);

        g.fillRect(x, y, width, height);


    }

}
