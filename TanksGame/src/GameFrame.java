import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    GamePanel panel;
    public static Color background = new Color(60, 60, 60);

    GameFrame(){
        panel = new GamePanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Tanks!");
        setBackground(background);
        add(panel);
        pack();
        setVisible(true);
    }
}
