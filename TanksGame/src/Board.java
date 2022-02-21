import java.awt.*;

public class Board {
    Rectangle[] walls;
    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    public Board(int GAME_WIDTH, int GAME_HEIGHT){
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
    }

    public void draw(Graphics g){

        g.setColor(Color.WHITE);

        g.drawRect(0, 70, GAME_WIDTH-1, GAME_HEIGHT-71);

    }
}
