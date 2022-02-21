import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Polacz4 {
    MyFrame frame;
    public static void main(String args[]){
        new Polacz4();
    }
    Polacz4(){
        frame = new MyFrame();
    }

}

class GUI_Board extends JPanel {
    int width;
    int height;
    int centerX;
    int centerY;
    static int discSize;
    Color color1;
    Color color2;
    String field[][];

    GUI_Board(Color b) {
        this.setBackground(b);
        color1 = Color.RED;
        color2 = Color.YELLOW;
        repaint();
        field = new String[7][6];
        for(int i=0;i<7;i++)
            for(int j=0;j<6;j++){
                field[i][j] = "empty";
            }
    }

    void clearBoard(){
        for(int i=0;i<7;i++)
            for(int j=0;j<6;j++){
                field[i][j] = "empty";
            }
    }

    public void paint(Graphics g) {

        width = this.getWidth();
        height = this.getHeight();
        centerX = (int) Math.floor(width / 2.0);
        centerY = (int) Math.floor(height / 2.0);
        if (getWidth() > getHeight()) discSize = (int) Math.floor(getHeight() / 10.0);
        else discSize = (int) Math.floor(getWidth() / 10.0);

        super.paint(g);

        Graphics2D gr = (Graphics2D) g;

        gr.setPaint(Color.WHITE);
        for (int i = 0; i < 7; i++) {
            gr.setPaint(Color.BLACK);
            gr.fillRect(centerX - (3 * (discSize + 5)) + (i * (discSize + 5))-5, centerY - (int)(2.85 * (discSize + 5)), 10, 30);
            gr.setPaint(Color.WHITE);
            gr.drawString(""+(i+1), centerX - (3 * (discSize + 5)) + (i * (discSize + 5))-4, centerY - (int)(2.6 * (discSize + 5)));
            for (int j = 0; j < 6; j++) {
                gr.setPaint(Color.BLACK);
                gr.fillOval(centerX - (3 * (discSize + 5)) + (i * (discSize + 5)) - (int) (discSize / 2.0)-2, centerY - (2 * (discSize + 5)) + (j * (discSize + 5)) - (int) (discSize / 2.0)-2, discSize+4, discSize+4);
                gr.setPaint(Color.WHITE);
                if(field[i][j].equals("empty") || field[i][j].isEmpty() || field[i][j].isBlank()) gr.setPaint(Color.WHITE);
                else if(field[i][j].equals("color1")) gr.setPaint(color1);
                else if(field[i][j].equals("color2"))  gr.setPaint(color2);
                gr.fillOval(centerX - (3 * (discSize + 5)) + (i * (discSize + 5)) - (int) (discSize / 2.0), centerY - (2 * (discSize + 5)) + (j * (discSize + 5)) - (int) (discSize / 2.0), discSize, discSize);
                gr.setPaint(Color.WHITE);

            }
        }

    }

    void insertDisc(int col, int row, String color){
        field[col][row] = color;
        repaint();
    }

}

class Bottom extends JPanel{

    int width;
    int height;
    int centerX;
    int centerY;
    int buttonSize;
    public static JButton column[];

    Bottom(Color b) {
        super();
        setOpaque(true);
        setBackground(b);
        setLayout(new FlowLayout());
        repaint();


        column = new JButton[7];
        for(int i=0;i<7;i++){
            column[i] = new JButton(""+(i+1));
            column[i].setPreferredSize(new Dimension(25, 25));
            this.add(column[i]);
        }
    }

    public void paint(Graphics g) {
        buttonSize = GUI_Board.discSize;

        super.paint(g);
        for(int i=0;i<7;i++){
            column[i].setPreferredSize(new Dimension(buttonSize, 25));
        }
    }

    static void disableAllColumnButtons(){
        for(int i=0;i<7;i++){
            column[i].setEnabled(false);
        }
        System.out.println("Disabled all column buttons");
    }
    static void enableAllColumnButtons(){
        for(int i=0;i<7;i++){
            column[i].setEnabled(true);
        }
    }

}

class ActivePlayerIndicator extends JLabel implements ActionListener{
    Graphics2D gr;
    String activeColor;
    Color color1;
    Color color2;
    boolean colorChanged;
    Timer timer;
    int width;
    ActivePlayerIndicator(String activeColor){
        this.activeColor = activeColor;
        width = 80;
        color1 = Color.RED;
        color2 = Color.YELLOW;
    }

    public void paint(Graphics g) {

        super.paint(g);

        gr = (Graphics2D) g;

        gr.setPaint(Color.BLACK);
        gr.fillOval(10-4, 10-4, 80+8, 80+8);
        gr.setPaint(Color.WHITE);
        gr.fillOval(10-2, 10-2, 80+4, 80+4);

        if(activeColor.equals("color1")) gr.setPaint(color1);
        if(activeColor.equals("color2")) gr.setPaint(color2);
        gr.fillOval((int)((this.getWidth()-width)/2.0), 10, width, 80);

    }

    void changePlayer(){
        Bottom.disableAllColumnButtons();
        colorChanged=false;
        timer = new Timer(1, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!colorChanged) width-=2;
        if(colorChanged) width+=2;
        if(width==0){
            System.out.println("active color = "+activeColor);
            colorChanged=true;
            if(activeColor.equals("color1")) activeColor="color2";
            else if(activeColor.equals("color2")) activeColor="color1";
            System.out.println("changed color = "+activeColor);
        }
        repaint();
        if(width==80) {
            timer.stop();
            Bottom.enableAllColumnButtons();
        }
    }
}

class PlayerColorWindow implements ActionListener{
    JFrame frame;
    JButton confirm;
    JLabel player1color_label;
    JComboBox player1color;
    JLabel player2color_label;
    JComboBox player2color;
    Color[] colorPalette1;
    Color[] colorPalette2;
    String[] colorPalette_label;

    PlayerColorWindow(){
        frame = new JFrame();

        colorPalette1 = new Color[5];
        colorPalette1[0] = Color.RED;
        colorPalette1[1] = Color.YELLOW;
        colorPalette1[2] = Color.BLUE;
        colorPalette1[3] = Color.GREEN;
        colorPalette1[4] = new Color(100, 0, 255);

        colorPalette2 = new Color[5];
        for(int i=0;i<colorPalette2.length;i++){
            colorPalette2[i]=colorPalette1[i];
        }

        colorPalette_label = new String[5];
        colorPalette_label[0] = "Czerwony";
        colorPalette_label[1] = "Żółty";
        colorPalette_label[2] = "Niebieski";
        colorPalette_label[3] = "Zielony";
        colorPalette_label[4] = "Fioletowy";

        player1color_label = new JLabel("Kolor gracza 1:");
        player1color_label.setBounds(50, 50, 125, 50);
        player1color = new JComboBox(colorPalette_label);
        player1color.addActionListener(this);
        player1color.setBounds(50, 100, 125, 50);
        player2color_label = new JLabel("Kolor gracza 2:");
        player2color_label.setBounds(225, 50, 100, 50);
        player2color = new JComboBox(colorPalette_label);
        player2color.addActionListener(this);
        player2color.setBounds(225, 100, 100, 50);

        for(int i=0;i<colorPalette1.length;i++) {
            if (MyFrame.color1.equals(colorPalette1[i])) player1color.setSelectedItem(colorPalette_label[i]);
        }
        for(int i=0;i<colorPalette1.length;i++) {
            if (MyFrame.color2.equals(colorPalette2[i])) player2color.setSelectedItem(colorPalette_label[i]);
        }

        confirm = new JButton("Zastosuj");
        confirm.setBounds(300, 340, 90, 25);
        confirm.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setResizable(false);
        frame.setLayout(null);
        frame.add(player1color_label);
        frame.add(player1color);
        frame.add(player2color_label);
        frame.add(player2color);
        frame.add(confirm);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==confirm){
            if(player1color.getSelectedItem()==player2color.getSelectedItem()) MyFrame.color2 = MyFrame.color2.darker();
            MyFrame.playerColors.setEnabled(true);
            MyFrame.castColors();
            MyFrame.repaintAllComponents();
            frame.dispose();
        }
        if(e.getSource()==player1color){
            for(int i=0;i<colorPalette1.length;i++) {
                if(player1color.getSelectedItem().equals(colorPalette_label[i])) 
                if (player1color.getSelectedItem().equals(colorPalette_label[i])) MyFrame.color1=colorPalette1[i];
            }
        }
        if(e.getSource()==player2color){
            for(int i=0;i<colorPalette2.length;i++) {
                if (player2color.getSelectedItem().equals(colorPalette_label[i])) MyFrame.color2=colorPalette2[i];
            }
        }
    }
}

class BackgroundColorWindow implements ActionListener{

    JFrame frame;
    JPanel demo;
    JButton colorButton;
    JButton confirm;
    Color backgroundDemo;
    BackgroundColorWindow(Color bg){
        backgroundDemo=bg;

        frame = new JFrame();

        confirm = new JButton("Zastosuj");
        confirm.setBounds(300, 340, 90, 25);
        confirm.addActionListener(this);

        colorButton = new JButton("Wybierz kolor");
        colorButton.setBounds(150, 175, 100, 50);
        colorButton.addActionListener(this);

        demo = new JPanel();
        demo.setPreferredSize(new Dimension(400, 400));
        demo.setBackground(backgroundDemo);
        demo.setLayout(null);
        demo.add(confirm);
        demo.add(colorButton);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.add(demo);
        frame.setLocationRelativeTo(demo);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==confirm){
            frame.dispose();
            MyFrame.background = backgroundDemo;
            MyFrame.messages.setBackground(backgroundDemo);
            MyFrame.leftPanel.setBackground(backgroundDemo);
            MyFrame.rightPanel.setBackground(backgroundDemo);
            MyFrame.boardPanel.setBackground(backgroundDemo);
            MyFrame.bottomPanel.setBackground(backgroundDemo);
            MyFrame.activePlayerIndicator.setBackground(backgroundDemo);
            MyFrame.backgroundColorChooserButton.setEnabled(true);
        }
        if(e.getSource()==colorButton){
            backgroundDemo = JColorChooser.showDialog(null, "Wybierz kolor", new Color(17, 39, 80));

            demo.setBackground(backgroundDemo);
        }
    }
}

class CreditsPanel extends JPanel implements ActionListener{

    static JButton skip;
    Timer timer;
    String[] credits;
    final String pb = "Paweł Brandt";
    final String ks = "Kuba Swisłocki";
    int width;
    int height;
    int y;

    CreditsPanel(){
        width=600;
        height=600;
        y=650;


        credits = new String[]{
                "GRA POŁĄCZ4", "",
                "Reżyseria:", pb, "",
                "Zespół programistów:", pb, ks, "",
                "Programista główny:", ks, "",
                "Grafika:", pb, "",
                "Pomysłodawca:", pb, "",
                "Projekt wspierany przez:", "niezliczoną ilość wypitych monsterów"};

        skip = new JButton("Pomiń");
        skip.setOpaque(true);
        skip.setBackground(Color.BLACK);

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setOpaque(true);
        this.add(skip);
    }

    void playAnimation(){
        timer =  new Timer(5, this);
        timer.start();
    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D gr = (Graphics2D) g;

        gr.setPaint(Color.BLACK);
        gr.fillRect(0, 0, 500, 600);

        gr.setPaint(Color.YELLOW);
        int stringWidth;
        for(int i=0;i<credits.length;i++) {
            if(i==0) gr.setFont(new Font("Copperplate", Font.BOLD, 35));
            else if(!credits[i].equals(pb) && !credits[i].equals(ks)) gr.setFont(new Font("Copperplate", Font.BOLD, 30));
            else gr.setFont(new Font("Copperplate", Font.BOLD, 25));
            stringWidth = gr.getFontMetrics().stringWidth(credits[i]);
            gr.drawString(credits[i], (int) ((width - stringWidth) / 2.0), y + (i * 75));

        }

        skip.setBounds(width-100, height-50, 75, 25);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        y--;
        repaint();
        System.out.println(y);
        if(y==-1500){
            skip.doClick();
        }
    }
}

class CreditsWindow extends JFrame implements ActionListener{

    CreditsPanel creditsPanel;
    int width;
    int height;

    CreditsWindow(){
        width = 600;
        height = 600;

        creditsPanel = new CreditsPanel();

        CreditsPanel.skip.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.add(creditsPanel);
        this.pack();
        this.setVisible(true);
        creditsPanel.playAnimation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==CreditsPanel.skip){
            creditsPanel.timer.stop();
            this.dispose();
            MyFrame.playCredits.setEnabled(true);
        }
    }
}

class MyFrame extends JFrame implements ActionListener {

    Connect4Game game;

    JMenuBar menuBar;
    JMenu gameplay;
    static JButton playerColors;
    static Color color1;
    static Color color2;
    JMenu backgroundColorMenu;
    static JButton backgroundColorChooserButton;
    JColorChooser backgroundColorChooser;
    static Color background;
    JMenu credits;
    static JButton playCredits;

    static JPanel rightPanel;
    static ActivePlayerIndicator activePlayerIndicator;
    JLabel turn;

    static JPanel  leftPanel;
    JButton reset;

    static Bottom bottomPanel;

    static GUI_Board boardPanel;

    static JLabel messages;


    MyFrame(){

        menuBar = new JMenuBar();
        gameplay = new JMenu("Rozgrywka");
        playerColors = new JButton("Dopasuj kolory graczy");
        color1 = Color.RED;
        color2 = Color.YELLOW;
        gameplay.add(playerColors);
        playerColors.addActionListener(this);
        menuBar.add(gameplay);
        backgroundColorMenu = new JMenu("Tło");
        backgroundColorChooserButton = new JButton("Zmień kolor tła");
        backgroundColorChooser = new JColorChooser();
        background = new Color(17, 39, 80);
        backgroundColorChooserButton.addActionListener(this);
        backgroundColorMenu.add(backgroundColorChooserButton);
        menuBar.add(backgroundColorMenu);
        credits = new JMenu("Napisy końcowe");
        playCredits = new JButton("Odtwórz napisy końcowe");
        playCredits.addActionListener(this);
        credits.add(playCredits);
        menuBar.add(credits);

        messages = new JLabel();
        messages.setPreferredSize(new Dimension(100, 50));
        messages.setHorizontalAlignment(SwingConstants.CENTER);
        messages.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        messages.setForeground(Color.WHITE);
        messages.setText("Aby rozpocząć grę należy nacisnąć przycisk 'Start'");
        messages.setOpaque(true);
        messages.setBackground(background);
        messages.repaint();

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(100, 100));
        rightPanel.setBackground(background);
        rightPanel.setLayout(null);

        reset = new JButton("Start");
        reset.setBounds(10, 200, 80, 40);
        reset.addActionListener(this);
        rightPanel.add(reset);

        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 100));
        leftPanel.setBackground(background);
        leftPanel.setLayout(null);
        activePlayerIndicator = new ActivePlayerIndicator("color1");
        activePlayerIndicator.setBounds(0, 50, 100, 100);
        turn = new JLabel("Tura gracza:");
        turn.setBounds(0, 0, 100, 50);
        turn.setForeground(Color.WHITE);
        turn.setHorizontalAlignment(SwingConstants.CENTER);
        turn.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        leftPanel.add(activePlayerIndicator);
        leftPanel.add(turn);

        boardPanel = new GUI_Board(background);
        boardPanel.setPreferredSize(new Dimension(100, 100));

        bottomPanel = new Bottom(background);
        bottomPanel.setPreferredSize(new Dimension(200, 75));
        for(int i=0;i<7;i++){
            Bottom.column[i].addActionListener(this);
            Bottom.column[i].setEnabled(false);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(3, 3));
        this.setTitle("Połącz4!");
        this.setMinimumSize(new Dimension(675, 550));
        this.setPreferredSize(new Dimension(700, 700));
        this.add(messages, BorderLayout.NORTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(boardPanel, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        this.pack();
        this.setVisible(true);
        game = new Connect4Game("color1","color2");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==playerColors){
            playerColors.setEnabled(false);
            PlayerColorWindow pcw = new PlayerColorWindow();
        }
        if(e.getSource()==backgroundColorChooserButton){
            backgroundColorChooserButton.setEnabled(false);
            BackgroundColorWindow bcw = new BackgroundColorWindow(background);
        }
        if(e.getSource()==playCredits){
            playCredits.setEnabled(false);
            CreditsWindow cw = new CreditsWindow();
        }
        if(e.getSource()==reset){
            reset.setText("Reset");
            if(this.getWidth()<700) messages.setFont(new Font("Helvetica Neue", Font.BOLD, 15));
            if(this.getWidth()>=700) messages.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
            messages.setForeground(Color.WHITE);
            messages.setText("Aby wrzucić krążek na planszę naciśnij przycisk na dolnym panelu.");
            activePlayerIndicator.activeColor="color1";
            activePlayerIndicator.repaint();
            bottomPanel.enableAllColumnButtons();
            boardPanel.clearBoard();
            game = new Connect4Game("color1", "color2");
            boardPanel.repaint();
        }
        for(int i=0;i<7;i++){
            if(e.getSource()==bottomPanel.column[i]){
                if(game.board.addPiece(i, game.activeColor)){
                    boardPanel.insertDisc(i, game.board.checkRow(i)+1,  game.activeColor);
                    game.board.printBoard();
                    System.out.println("winner? "+game.checkForWinner(i, game.board.checkRow(i)+1)+" col="+i+" row="+(game.board.checkRow(i)+1));
                    if(game.checkForWinner(i, game.board.checkRow(i)+1)){
                        if(game.is1playing){
                            messages.setForeground(color1);
                            messages.setText("Gracz 1 wygrał");
                        }else{
                            messages.setForeground(color2);
                            messages.setText("Gracz 2 wygrał");
                        }
                        bottomPanel.disableAllColumnButtons();
                    }
                    else {
                        activePlayerIndicator.changePlayer();
                        game.changePlayer();
                    }
                }
            }
        }
    }

    static void castColors(){
        messages.setForeground(Color.WHITE);
        boardPanel.color1=color1;
        boardPanel.color2=color2;
        activePlayerIndicator.color1=color1;
        activePlayerIndicator.color2=color2;
    }

    static void repaintAllComponents(){
        messages.repaint();
        leftPanel.repaint();
        rightPanel.repaint();
        boardPanel.repaint();
        bottomPanel.repaint();
        activePlayerIndicator.repaint();
    }
}

class Board {

    private static final int rows = 6;
    private static final int columns = 7;
    Piece[][] ourBoard = new Piece[rows][columns];
    public static int getColumns(){
        return columns;
    }

    public static int getRows(){
        return rows;
    }

    public boolean checkForWinner(int col,int rowy, String winningColor){
        boolean someoneWon = false;
        System.out.println("checking win of color "+winningColor);
        System.out.println(""+ourBoard[rowy][col].getColor());
        for(int row = 0; row < rows;row++){
            if(ourBoard[row][col] != null) {
                int winningstreak = 3;
                // w dol
                for (int winRow = row + 1; winRow < rows; winRow++) {
                    if (ourBoard[winRow][col].getColor() == winningColor) {
                        winningstreak--;
                        if (winningstreak == 0) {
                            someoneWon = true;
                            System.out.println("W GORE");
                        }
                    } else {
                        winningstreak = 3;
                    }
                }
                winningstreak = 4;

                // w boki
                for (int winCol = col - 3; winCol <= col + 3; winCol++) {
                    if (winCol < 0) continue;
                    if (winCol >= columns) break;
                    if (ourBoard[row][winCol] != null && ourBoard[row][winCol].getColor() == winningColor) {
                        winningstreak--;
                        if (winningstreak == 0) {
                            someoneWon = true;
                            System.out.println("W BOK");
                        }
                    } else {
                        winningstreak = 4;

                    }
                }

                //UKOSY


            }}


        int winningstreak=4;
        for(int i=0; i <= 4; i ++){
            if((rowy+i)>=rows || (col+i)>=columns || ourBoard[rowy+i][col + i] == null) {
                break;
            }
            if( ourBoard[rowy+i][col+i].getColor() == winningColor){
                winningstreak--;
            }
            if( ourBoard[rowy+i][col+i].getColor() != winningColor){
                break;
            }
        }
        for(int i=1; i <= 4; i ++){
            if((rowy-i)<=0 || (col-i)<0 || ourBoard[rowy-i][col - i] == null) {
                break;
            }
            if( ourBoard[rowy-i][col-i].getColor()==winningColor){
                winningstreak--;
            }
            if( ourBoard[rowy-i][col-i].getColor()!=winningColor){
                break;
            }
        }

        if(winningstreak==0){
            someoneWon=true;
        }

        winningstreak=4;

        for(int i=0; i <= 4; i ++){
            if((rowy-i)<0 || (col+i)>=columns || ourBoard[rowy-i][col + i] == null) {
                break;
            }
            if( ourBoard[rowy-i][col+i].getColor() == winningColor){
                winningstreak--;
            }
            if( ourBoard[rowy-i][col+i].getColor() != winningColor){
                break;
            }
        }

        for(int i=1; i <= 4; i ++){
            if((rowy+i)>=rows || (col-i)<0 || ourBoard[rowy+i][col - i] == null) {
                break;
            }
            if( ourBoard[rowy+i][col-i].getColor()==winningColor){
                winningstreak--;
            }
            if( ourBoard[rowy+i][col-i].getColor()!=winningColor){
                break;
            }
        }


        if(winningstreak==0){
            someoneWon=true;
        }




        return someoneWon;
    }

    public boolean addPiece(int colToAdd, String color) {

        if (colToAdd >= 0 && colToAdd < columns) {
            if (ourBoard[0][colToAdd] == null) {
                boolean addedThePiece = false;
                for (int row = rows - 1; row >= 0; row--) {
                    if (ourBoard[row][colToAdd] == null) {
                        ourBoard[row][colToAdd] = new Piece();
                        ourBoard[row][colToAdd].setColor(color);
                        addedThePiece = true;
                        break;
                    }
                }
                return addedThePiece;
            } else {
                System.out.println("==================");
                System.out.println("Pełna kolumna");
                System.out.println("==================");
                return false;
            }
        } else {
            System.out.println("==================");
            System.out.println("Poza tablica");
            System.out.println("==================");
            return false;
        }
    }

    public void printBoard(){
        System.out.println("    Kolumny   ");
        System.out.println(" 1 2 3 4 5 6 7");
        for (int row =0 ; row < rows; row++){
            System.out.print("|");
            for(int col=0; col < columns; col++){
                if(ourBoard[row][col] == null) {
                    System.out.print("_");
                }
                else{
                    System.out.print(ourBoard[row][col].getColor());
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    public Board(){
        for (int row =0 ; row < rows; row++){
            for(int col=0; col < columns; col++){
                ourBoard[row][col] = null;
            }
        }
    }
    public int checkRow(int col){
        int rowy=-1;
        for(int i=0; i < rows ; i++){
            if(ourBoard[i][col] == null){
                rowy=rowy+1;
            }
        }
        return rowy;
    }
}

class Connect4Game {
    public Board board;
    static String color1;
    static String color2;
    String activeColor;
    //true tura 1 gracza
    //false tura 2 gracza
    public static boolean is1playing;

    public Connect4Game(String color1, String color2){
        board = new Board();
        this.color1 = color1;
        this.color2 = color2;
        activeColor = this.color1;

        is1playing = true;
    }

    public boolean checkForWinner(int col,int rowy){

        String winningColor = is1playing ? color1 : color2;

        System.out.println("checked win of color "+activeColor+" - "+board.checkForWinner(col,rowy, activeColor));
        return board.checkForWinner(col,rowy, activeColor);
    }

    public void changePlayer(){
        is1playing = !is1playing;
        if(is1playing){
            activeColor = color1;
        }else{
            activeColor = color2;
        }
        System.out.println("Active: "+activeColor);
    }

    public void StartGame(){
        boolean running = true;

        //tury
        while(running){
            board.printBoard();
            System.out.println(" Tura "+activeColor+" gracza ");
            System.out.println("Wybierz kolumne");
            System.out.print("Wybór pomiedzy 1 a "+board.getColumns()+ ": ");

            Scanner sc = new Scanner(System.in);
            int column = sc.nextInt() - 1;
            int rowy = board.checkRow(column);


            boolean succes = board.addPiece(column,activeColor);
            if(succes){
                if(checkForWinner(column,rowy)){
                    running = false;
                    if(is1playing){
                        System.out.println("Gracz 1 wygrał");
                        board.printBoard();
                    }else{
                        System.out.println("Gracz 2 wygrał");
                        board.printBoard();
                    }
                }
            }
            changePlayer();

        }

    }
}

class Piece {
    private String color;

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }
}
