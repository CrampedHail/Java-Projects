import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ActionMapUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;


public class TicTacToe implements ActionListener{
    JFrame frame;
    JPanel gamePanel;
    JPanel buttonPanel;
    JPanel userXPanel;
    JPanel userOPanel;
    JPanel resetPanel;
    JPanel boardPanel;
    JPanel[] rowPanel;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JButton button5;
    JButton button6;
    JButton button7;
    JButton button8;
    JButton button9;
    String[] users={"Player","CPU"};
    JComboBox userX;
    JComboBox userO;
    JLabel userXLabel;
    JLabel userOLabel;
    JButton reset;
    JLabel label;
    JLabel winnerLabel;
    String user="X";
    static ArrayList<Integer> xPos = new ArrayList<>();
    static ArrayList<Integer> oPos = new ArrayList<>();
    int gameState=0;

    public TicTacToe(){

        button1=new JButton(" ");
        button2=new JButton(" ");
        button3=new JButton(" ");
        button4=new JButton(" ");
        button5=new JButton(" ");
        button6=new JButton(" ");
        button7=new JButton(" ");
        button8=new JButton(" ");
        button9=new JButton(" ");
        reset=new JButton("Reset");
        userXLabel=new JLabel("User X:");
        userX = new JComboBox(users);
        userOLabel=new JLabel("User O:");
        userO = new JComboBox(users);
        Font f = new Font("Helvetica Neue", Font.PLAIN, 50);
        button1.setFont(f);
        button2.setFont(f);
        button3.setFont(f);
        button4.setFont(f);
        button5.setFont(f);
        button6.setFont(f);
        button7.setFont(f);
        button8.setFont(f);
        button9.setFont(f);
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
        button6.addActionListener(this);
        button7.addActionListener(this);
        button8.addActionListener(this);
        button9.addActionListener(this);
        reset.addActionListener(this);
        userX.addActionListener(this);
        userO.addActionListener(this);
        reset.setAlignmentX(Component.CENTER_ALIGNMENT);
        userXLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userX.setAlignmentX(Component.CENTER_ALIGNMENT);
        userOLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userO.setAlignmentX(Component.CENTER_ALIGNMENT);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        boardPanel.add(button1);
        boardPanel.add(button2);
        boardPanel.add(button3);
        boardPanel.add(button4);
        boardPanel.add(button5);
        boardPanel.add(button6);
        boardPanel.add(button7);
        boardPanel.add(button8);
        boardPanel.add(button9);

        Font f2 = new Font("Helvetica Neue", Font.PLAIN, 25);
        label=new JLabel("Player's turn: "+user);
        label.setPreferredSize( new Dimension(250, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(f2);
        winnerLabel=new JLabel(" ");
        winnerLabel.setPreferredSize( new Dimension(250, 50));
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setFont(f2);

        resetPanel = new JPanel();
        resetPanel.setMaximumSize(new Dimension(100, 75));
        resetPanel.add(reset);
        reset.setAlignmentX(Component.CENTER_ALIGNMENT);
        reset.setAlignmentY(Component.CENTER_ALIGNMENT);


        userXPanel = new JPanel();
        userXPanel.setLayout(new BoxLayout(userXPanel, BoxLayout.Y_AXIS));
        userXPanel.setMaximumSize(new Dimension(100, 75));
        userXPanel.add(userXLabel);
        userXPanel.add(userX);

        userOPanel = new JPanel();
        userOPanel.setLayout(new BoxLayout(userOPanel, BoxLayout.Y_AXIS));
        userOPanel.setMaximumSize(new Dimension(100, 75));
        userOPanel.add(userOLabel);
        userOPanel.add(userO);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(resetPanel);
        buttonPanel.add(userXPanel);
        buttonPanel.add(userOPanel);

        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePanel.setMaximumSize(new Dimension(230, 180));
        gamePanel.setMinimumSize(new Dimension(230, 180));
        gamePanel.setPreferredSize(new Dimension(230, 180));
        gamePanel.add(boardPanel);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(100, 100));


        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("TicTacToe");
        frame.add(label, BorderLayout.NORTH);
        frame.add(emptyPanel, BorderLayout.EAST);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(winnerLabel, BorderLayout.SOUTH);
        frame.setMinimumSize(new Dimension(450,350));
        frame.setVisible(true);
    }
    public void changeUser(){
        if (user.equals("X")) {
            user = "O";
            label.setText("Player's turn: "+user);
            if(userO.getSelectedItem().toString().equals("CPU")){
                CPUmoves();
            }
        }
        else if (user.equals("O")) {
            user = "X";
            label.setText("Player's turn: "+user);
            if(userX.getSelectedItem().toString().equals("CPU")){
                CPUmoves();
            }
        }
    }
    public void setUser(String u){
        if (u.equals("X") && !user.equals("X")) {
            user = "X";
            label.setText("Player's turn: "+user);
        }
        if (u.equals("O") && !user.equals("O")) {
            user = "O";
            label.setText("Player's turn: "+user);
        }
    }

    void CPUmoves(){
        if(xPos.size()+oPos.size()<=9){
            Random r = new Random();
            int a;
            do {
                a = r.nextInt(9) + 1;
            } while ((xPos.contains(a) || oPos.contains(a)) && xPos.size() + oPos.size() < 9);
            switch (a) {
                case 1: {
                    button1.setEnabled(false);
                    button1.setText(user);
                    assignButtonToUser(1);
                    checkWinner();
                    break;
                }
                case 2: {
                    button2.setEnabled(false);
                    button2.setText(user);
                    assignButtonToUser(2);
                    checkWinner();
                    break;
                }
                case 3: {
                    button3.setEnabled(false);
                    button3.setText(user);
                    assignButtonToUser(3);
                    checkWinner();
                    break;
                }
                case 4: {
                    button4.setEnabled(false);
                    button4.setText(user);
                    assignButtonToUser(4);
                    checkWinner();
                    break;
                }
                case 5: {
                    button5.setEnabled(false);
                    button5.setText(user);
                    assignButtonToUser(5);
                    checkWinner();
                    break;
                }
                case 6: {
                    button6.setEnabled(false);
                    button6.setText(user);
                    assignButtonToUser(6);
                    checkWinner();
                    break;
                }
                case 7: {
                    button7.setEnabled(false);
                    button7.setText(user);
                    assignButtonToUser(7);
                    checkWinner();
                    break;
                }
                case 8: {
                    button8.setEnabled(false);
                    button8.setText(user);
                    assignButtonToUser(8);
                    checkWinner();
                    break;
                }
                case 9: {
                    button9.setEnabled(false);
                    button9.setText(user);
                    assignButtonToUser(9);
                    checkWinner();
                    break;
                }
                default:
                    break;
            }
        }
    }

    void checkWinner(){
        List topR = Arrays.asList(1, 2, 3);
        List midR = Arrays.asList(4, 5, 6);
        List btmR = Arrays.asList(7, 8, 9);
        List lefC = Arrays.asList(1, 4, 7);
        List midC = Arrays.asList(2, 5, 8);
        List rigC = Arrays.asList(3, 6, 9);
        List crs1 = Arrays.asList(1, 5, 9);
        List crs2 = Arrays.asList(3, 5, 7);
        List<List> win = new ArrayList<List>();
        win.add(topR);
        win.add(midR);
        win.add(btmR);
        win.add(lefC);
        win.add(midC);
        win.add(rigC);
        win.add(crs1);
        win.add(crs2);

        if (xPos.size()+oPos.size()<=9){
            for (List l : win) {
                if (xPos.containsAll(l)) {
                    winnerLabel.setText("Player X wins!");
                    disableAllButtons();
                    gameState=1;
                    break;
                }
                if (oPos.containsAll(l)) {
                    winnerLabel.setText("Player O wins!");
                    disableAllButtons();
                    gameState=1;
                    break;
                }
            }
        }
        if(xPos.size()+oPos.size()>=9 && gameState==0){
            winnerLabel.setText("Draw - no winners!");
            disableAllButtons();
            gameState=1;
        }
        if (gameState==0) {
            changeUser();
        }
    }


    void disableAllButtons(){
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
    }
    void enableAllButtons(){
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
    }
    void clearAllButtons(){
        button1.setText(" ");
        button2.setText(" ");
        button3.setText(" ");
        button4.setText(" ");
        button5.setText(" ");
        button6.setText(" ");
        button7.setText(" ");
        button8.setText(" ");
        button9.setText(" ");
    }

    void Reset(){
        enableAllButtons();
        clearAllButtons();
        winnerLabel.setText(" ");
        xPos.clear();
        oPos.clear();
        gameState=0;
        setUser("X");
    }

    void assignButtonToUser(int b){
        if (user.equals("X")){
            xPos.add(b);
        }
        else if (user.equals("O")){
            oPos.add(b);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==reset){
            Reset();
            if(userX.getSelectedItem().toString().equals("CPU") && user.equals("X")){
                CPUmoves();
            }
        }
        else if(e.getSource()==userX){
            if(userX.getSelectedItem().toString().equals("CPU") && user.equals("X")){
                CPUmoves();
            }
        }
        else if(e.getSource()==userO){
            if(userO.getSelectedItem().toString().equals("CPU") && user.equals("O")){
                CPUmoves();
            }
        }
        else {
            if (e.getSource()==button1){
                button1.setEnabled(false);
                button1.setText(user);
                assignButtonToUser(1);
            }
            else if (e.getSource()==button2){
                button2.setEnabled(false);
                button2.setText(user);
                assignButtonToUser(2);
            }
            else if (e.getSource()==button3){
                button3.setEnabled(false);
                button3.setText(user);
                assignButtonToUser(3);
            }
            else if (e.getSource()==button4){
                button4.setEnabled(false);
                button4.setText(user);
                assignButtonToUser(4);
            }
            else if (e.getSource()==button5){
                button5.setEnabled(false);
                button5.setText(user);
                assignButtonToUser(5);
            }
            else if (e.getSource()==button6){
                button6.setEnabled(false);
                button6.setText(user);
                assignButtonToUser(6);
            }
            else if (e.getSource()==button7){
                button7.setEnabled(false);
                button7.setText(user);
                assignButtonToUser(7);
            }
            else if (e.getSource()==button8){
                button8.setEnabled(false);
                button8.setText(user);
                assignButtonToUser(8);
            }
            else if (e.getSource()==button9){
                button9.setEnabled(false);
                button9.setText(user);
                assignButtonToUser(9);
            }
            checkWinner();

        }
    }

    public static void main(String args[]){
        new TicTacToe();
    }
    
}