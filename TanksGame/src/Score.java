import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle {

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player[];
    Color player1Color, player2Color;

    Score(int GAME_WIDTH, int GAME_HEIGHT, Color c1, Color c2){
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        player1Color = c1;
        player2Color = c2;
        player = new int[GamePanel.PLAYERS_PLAYING];
        for (int i = 0; i < GamePanel.PLAYERS_PLAYING; i++) {
            player[i] = 0;
        }
    }

    public void draw(Graphics g){
        g.setColor(player1Color);
        g.fillRect(GAME_WIDTH/2-68, 10, 51, 50);
        g.setColor(player2Color);
        g.fillRect(GAME_WIDTH/2+17, 10, 51, 50);

        g.setColor(Color.WHITE);

        g.drawLine(0, 70, GAME_WIDTH, 70);

        g.setFont(new Font("Helvetica Neue", Font.PLAIN, 40));
        g.drawString(String.valueOf(player[0]/10)+String.valueOf(player[0]%10), GAME_WIDTH/2-65, 50);
        g.drawString(String.valueOf(player[1]/10)+String.valueOf(player[1]%10), GAME_WIDTH/2+20, 50);

        int player1ShotReady = (int)((GamePanel.tank[0].shot*1.0)/(Tank.SHOT_READY*1.0)*360.0);
        int player2ShotReady = (int)((GamePanel.tank[1].shot*1.0)/(Tank.SHOT_READY*1.0)*360.0);
        g.fillArc(20, 20, 30, 30, 90, player1ShotReady);
        g.fillArc(GAME_WIDTH-20-30, 20, 30, 30, 90, -player2ShotReady);
        //g.fillOval(20, 20, 30, 30);
        //g.fillOval(GAME_WIDTH-20-30, 20, 30, 30);

        for (int i = 0; i < GamePanel.tank[0].HP; i++) {
            g.fillRect(70+(i*31), 20, 30, 30);
        }

        for (int i = 0; i < GamePanel.tank[1].HP; i++) {
            g.fillRect(GAME_WIDTH-(120+(i*31)), 20, 30, 30);
        }


    }
}
