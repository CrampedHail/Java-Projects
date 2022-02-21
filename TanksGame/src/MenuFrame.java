import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MenuFrame {

    JFrame frame;
    JPanel panel;
    JLabel titleLabel;
    JButton startButton;

    MenuFrame(){
        titleLabel = new JLabel("Tanks Game!");
        titleLabel.setMinimumSize(new Dimension(400, 200));
        titleLabel.setFont(new Font("Courier New", Font.PLAIN, 60));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(60, 60, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(new EmptyBorder(50, 10, 100, 10));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(100, 35));
        startButton.setFont(new Font("Courier New", Font.PLAIN, 30));
        startButton.setOpaque(true);
        startButton.setBackground(new Color(60, 60, 60));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new GameFrame();
            }
        });

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(800, 450));
        panel.setOpaque(true);
        panel.setBackground(new Color(60, 60, 60));
        panel.add(titleLabel);
        panel.add(startButton);

        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.pack();
        frame.setTitle("TanksGame!");
        frame.setVisible(true);
    }
}
