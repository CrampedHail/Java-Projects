import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class TicTacToe implements ActionListener{
    private final JFrame frame;
    private final JPanel gamePanel;
    private final JPanel buttonPanel;
    private final JPanel userXPanel;
    private final JPanel userOPanel;
    private final JPanel difficultyPanel;
    private final JPanel resetPanel;
    private final JPanel boardPanel;
    private final JButton[] buttons;
    private final String[] users={"Player","CPU"};
    private final JComboBox<String> userX;
    private final JComboBox<String> userO;
    private final String[] difficulties={"Easy", "Medium", "Hard"};
    private final JComboBox<String> difficulty;
    private final JLabel userXLabel;
    private final JLabel userOLabel;
    private final JLabel difficultyLabel;
    private final JButton reset;
    private final JLabel label;
    private final JLabel winnerLabel;
    private String user="X";
    private final static ArrayList<Integer> xPos = new ArrayList<>();
    private final static ArrayList<Integer> oPos = new ArrayList<>();
    private enum DifficultyLevel{
        EASY("easy"), MEDIUM("medium"), HARD("hard");
        DifficultyLevel(String v){}
    }
    private static DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
    private int gameState=0;

    public TicTacToe(){

        reset=new JButton("Reset");
        userXLabel=new JLabel("User X:");
        userX = new JComboBox<>(users);
        userOLabel=new JLabel("User O:");
        userO = new JComboBox<>(users);
        difficultyLabel = new JLabel("Difficulty:");
        difficulty = new JComboBox<>(difficulties);
        reset.addActionListener(this);
        userX.addActionListener(this);
        userO.addActionListener(this);
        difficulty.addActionListener(this);
        reset.setAlignmentX(Component.CENTER_ALIGNMENT);
        userXLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userX.setAlignmentX(Component.CENTER_ALIGNMENT);
        userOLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userO.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficulty.setAlignmentX(Component.CENTER_ALIGNMENT);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));


        buttons = new JButton[9];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(" ");
            buttons[i].setFont(new Font("Helvetica Neue", Font.PLAIN, 50));
            buttons[i].addActionListener(this);
            boardPanel.add(buttons[i]);
        }

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
        resetPanel.setMaximumSize(new Dimension(100, 40));
        resetPanel.add(reset);
        reset.setAlignmentX(Component.CENTER_ALIGNMENT);
        reset.setAlignmentY(Component.CENTER_ALIGNMENT);


        userXPanel = new JPanel();
        userXPanel.setLayout(new BoxLayout(userXPanel, BoxLayout.Y_AXIS));
        userXPanel.setMaximumSize(new Dimension(100, 50));
        userXPanel.add(userXLabel);
        userXPanel.add(userX);

        userOPanel = new JPanel();
        userOPanel.setLayout(new BoxLayout(userOPanel, BoxLayout.Y_AXIS));
        userOPanel.setMaximumSize(new Dimension(100, 50));
        userOPanel.add(userOLabel);
        userOPanel.add(userO);
        
        difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
        difficultyPanel.setMaximumSize(new Dimension(100, 50));
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficulty);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(resetPanel);
        buttonPanel.add(userXPanel);
        buttonPanel.add(userOPanel);
        buttonPanel.add(difficultyPanel);

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
            if(difficultyLevel.equals(DifficultyLevel.EASY)){
                assignRandom();
            }
            else if(difficultyLevel.equals(DifficultyLevel.MEDIUM) || difficultyLevel.equals(DifficultyLevel.HARD)){
                ArrayList<Integer> cpuPos;
                ArrayList<Integer> opponentPos;
                if(user.equals("X")) {
                    cpuPos = xPos;
                    opponentPos = oPos;
                }
                else {
                    cpuPos = oPos;
                    opponentPos = xPos;
                }

                // Try to Win: If you have two in a row, play the third to get three in a row.
                if(cpuPos.size()>=2){
                    if(cpuPos.stream().filter(i -> i>=1 && i<=3).count()==2){
                        for (int i = 0; i < 3; i++) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(cpuPos.stream().filter(i -> i>=4 && i<=6).count()==2){
                        for (int i = 3; i < 6; i++) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(cpuPos.stream().filter(i -> i>=7 && i<=9).count()==2){
                        for (int i = 6; i < 9; i++) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(cpuPos.stream().filter(i -> i==1 || i==4 || i==7).count()==2){
                        for (int i = 0; i < 9; i+=3) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(cpuPos.stream().filter(i -> i==2 || i==5 || i==8).count()==2){
                        for (int i = 1; i < 9; i+=3) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(cpuPos.stream().filter(i -> i==3 || i==6 || i==9).count()==2){
                        for (int i = 2; i < 9; i+=3) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(cpuPos.stream().filter(i -> i==1 || i==5 || i==9).count()==2){
                        for (int i = 0; i < 9; i+=4) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(cpuPos.stream().filter(i -> i==3 || i==5 || i==7).count()==2){
                        for (int i = 2; i < 9; i+=2) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                }

                // Block: If the opponent has two in a row, play the third to block them.
                if(opponentPos.size()>=2){
                    if(opponentPos.stream().filter(i -> i>=1 && i<=3).count()==2){
                        for (int i = 0; i < 3; i++) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(opponentPos.stream().filter(i -> i>=4 && i<=6).count()==2){
                        for (int i = 3; i < 6; i++) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(opponentPos.stream().filter(i -> i>=7 && i<=9).count()==2){
                        for (int i = 6; i < 9; i++) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(opponentPos.stream().filter(i -> i==1 || i==4 || i==7).count()==2){
                        for (int i = 0; i < 9; i+=3) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(opponentPos.stream().filter(i -> i==2 || i==5 || i==8).count()==2){
                        for (int i = 1; i < 9; i+=3) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(opponentPos.stream().filter(i -> i==3 || i==6 || i==9).count()==2){
                        for (int i = 2; i < 9; i+=3) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(opponentPos.stream().filter(i -> i==1 || i==5 || i==9).count()==2){
                        for (int i = 0; i < 9; i+=4) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                    if(opponentPos.stream().filter(i -> i==3 || i==5 || i==7).count()==2){
                        for (int i = 2; i < 9; i+=2) {
                            if(buttons[i].isEnabled()){
                                assignButtonToUser(i);
                                return;
                            }
                        }
                    }
                }

                if(difficultyLevel.equals(DifficultyLevel.MEDIUM)) assignRandom();
                else{
                    assignRandom();
                }
            }
        }
    }

    void assignRandom(){
        Random r = new Random();
        int a;
        do {
            a = r.nextInt(9) + 1;
        } while ((xPos.contains(a) || oPos.contains(a)) && xPos.size() + oPos.size() < 9);
        assignButtonToUser(a-1);
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
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(false);
        }
    }
    void enableAllButtons(){
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(true);
        }
    }
    void clearAllButtons(){
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(" ");
        }
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

    void assignButtonToUser(int i){
        if (user.equals("X")){
            xPos.add(i+1);
        }
        else if (user.equals("O")){
            oPos.add(i+1);
        }
        buttons[i].setEnabled(false);
        buttons[i].setText(user);
        checkWinner();
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
        else if(e.getSource()==difficulty){
            if(difficulty.getSelectedItem().toString().equalsIgnoreCase(DifficultyLevel.EASY.toString()))
                difficultyLevel = DifficultyLevel.EASY;
            if(difficulty.getSelectedItem().toString().equalsIgnoreCase(DifficultyLevel.MEDIUM.toString()))
                difficultyLevel = DifficultyLevel.MEDIUM;
            if(difficulty.getSelectedItem().toString().equalsIgnoreCase(DifficultyLevel.HARD.toString()))
                difficultyLevel = DifficultyLevel.HARD;
        }
        else {
            for (int i=0; i<9; i++) {
                if(e.getSource()==buttons[i]){
                    assignButtonToUser(i);
                    break;
                }
            }
        }
    }

    public static void main(String args[]){
        new TicTacToe();
    }
    
}