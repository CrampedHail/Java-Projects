import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class Battleships implements ActionListener {
    public static void main(String[] args){
        new Battleships();
    }
    JPanel panel;
    JFrame frame;
    JButton[][] myShipsBoard = new JButton[10][10];
    JButton[][] myShotsBoard = new JButton[10][10];
    JButton[][] cpuShipsBoard = new JButton[10][10];
    JButton[][] cpuShotsBoard = new JButton[10][10];
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JLabel info;
    JLabel myStats_label;
    JLabel shotsFired_label;
    JLabel shotsHit_label;
    JLabel shotsMissed_label;
    JLabel shotsAccuracy_label;
    JLabel cpuStats_label;
    JLabel cpuShotsFired_label;
    JLabel cpuShotsHit_label;
    JLabel cpuShotsMissed_label;
    JLabel cpuShotsAccuracy_label;
    int myShotsHit=0;
    int cpuShotsHit=0;
    JButton reset;
    JMenuBar menuBar;
    JMenu cpuMenu, cpuBoards, nextMoveHintsMenu, languageMenu, tutorialMenu;
    JMenuItem showTutorial;
    JCheckBoxMenuItem cpuShips, cpuShots, shipsLocationTips, whereToNotShootTips;
    JRadioButtonMenuItem englishButton, polishButton;
    int myShipCount=0;
    int curFieldsCount=0;
    int myShipsTotal;
    boolean isPlacingShip=false;
    position[] playerPos;
    position[] cpuPos;
    position[] playerShotPos;
    position[] cpuShotPos;
    position lastHitPos;
    int cpuFieldsCount;
    int myShotsFired=0;
    int cpuShotsFired=0;
    String winner="";
    int gamemode=0;


    Battleships(){

        panel = new JPanel();
        panel.setLayout (null);

        myShipCount=0;
        myShipsTotal=20;
        playerPos = new position[myShipsTotal];
        cpuPos = new position[myShipsTotal];
        playerShotPos = new position[100];
        cpuShotPos = new position[100];
        for(int i=0;i<myShipsTotal;i++){
            playerPos[i] = new position();
            cpuPos[i] = new position();
        }

        menuBar = new JMenuBar();                               //MENU layout settings
        cpuMenu = new JMenu("CPU");
        cpuBoards = new JMenu("Boards");
        nextMoveHintsMenu = new JMenu("Hints");
        cpuShips = new JCheckBoxMenuItem("Show ships");
        cpuShots = new JCheckBoxMenuItem("Show shots");
        shipsLocationTips = new JCheckBoxMenuItem("Ships Location Tips");
        whereToNotShootTips = new JCheckBoxMenuItem("Shooting Tips");
        reset = new JButton("RESET");
        languageMenu = new JMenu("Language");
        englishButton = new JRadioButtonMenuItem("English");
        englishButton.setSelected(true);
        polishButton = new JRadioButtonMenuItem("Polish");
        tutorialMenu = new JMenu("Tutorial");
        showTutorial = new JMenuItem("Show Tutorial");
        menuBar.add(cpuMenu);
        menuBar.add(nextMoveHintsMenu);
        menuBar.add(languageMenu);
        menuBar.add(tutorialMenu);
        menuBar.add(reset);
        cpuMenu.add(cpuBoards);
        cpuBoards.add(cpuShips);
        cpuBoards.add(cpuShots);
        nextMoveHintsMenu.add(shipsLocationTips);
        nextMoveHintsMenu.add(whereToNotShootTips);
        languageMenu.add(englishButton);
        languageMenu.add(polishButton);
        tutorialMenu.add(showTutorial);

        ButtonGroup bg = new ButtonGroup();
        bg.add(englishButton);
        bg.add(polishButton);

        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++){
                myShipsBoard[i][j] = new JButton("");
                myShotsBoard[i][j] = new JButton("");
                cpuShipsBoard[i][j] = new JButton("");
                cpuShotsBoard[i][j] = new JButton("");
                myShipsBoard[i][j].setFont(new Font(myShipsBoard[i][j].getFont().getName(), Font.PLAIN, 10));
                myShotsBoard[i][j].setFont(new Font(myShotsBoard[i][j].getFont().getName(), Font.PLAIN, 10));
                cpuShipsBoard[i][j].setFont(new Font(cpuShipsBoard[i][j].getFont().getName(), Font.PLAIN, 10));
                cpuShotsBoard[i][j].setFont(new Font(cpuShotsBoard[i][j].getFont().getName(), Font.PLAIN, 10));
                myShipsBoard[i][j].setBounds(500+(i*25),450+(j*25),25,25);
                myShotsBoard[i][j].setBounds(500+(i*25),75+(j*25),25,25);
                cpuShipsBoard[i][j].setBounds(100+(i*25),450+(j*25),25,25);
                cpuShotsBoard[i][j].setBounds(100+(i*25),75+(j*25),25,25);
                myShipsBoard[i][j].setEnabled(true);
                myShotsBoard[i][j].setEnabled(false);
                cpuShipsBoard[i][j].setEnabled(false);
                cpuShotsBoard[i][j].setEnabled(false);
                myShipsBoard[i][j].setOpaque(true);
                myShotsBoard[i][j].setOpaque(true);
                cpuShipsBoard[i][j].setOpaque(true);
                cpuShotsBoard[i][j].setOpaque(true);
                myShipsBoard[i][j].setBackground(Color.WHITE);
                myShotsBoard[i][j].setBackground(Color.WHITE);
                myShipsBoard[i][j].setForeground(Color.BLACK);
                myShotsBoard[i][j].setForeground(Color.BLACK);
                cpuShipsBoard[i][j].setBackground(Color.WHITE);
                cpuShipsBoard[i][j].setForeground(Color.BLACK);
                cpuShotsBoard[i][j].setBackground(Color.WHITE);
                cpuShotsBoard[i][j].setForeground(Color.BLACK);
                myShipsBoard[i][j].addActionListener(this);
                myShotsBoard[i][j].addActionListener(this);
                cpuShipsBoard[i][j].addActionListener(this);
                cpuShotsBoard[i][j].addActionListener(this);
                panel.add(myShipsBoard[i][j]);
                panel.add(myShotsBoard[i][j]);
                panel.add(cpuShipsBoard[i][j]);
                panel.add(cpuShotsBoard[i][j]);
                cpuShipsBoard[i][j].setVisible(false);
                cpuShotsBoard[i][j].setVisible(false);
            }
        generateCpuShips();
        cpuShips.addActionListener(this);
        cpuShots.addActionListener(this);
        reset.addActionListener(this);
        englishButton.addActionListener(this);
        polishButton.addActionListener(this);
        showTutorial.addActionListener(this);
        label1 = new JLabel("Your Shots Board");
        label2 = new JLabel("Your Ships Board");
        label3 = new JLabel("CPUs Shots Board");
        label4 = new JLabel("CPUs Ships Board");
        myStats_label = new JLabel("Your stats:");
        shotsFired_label = new JLabel("Shots Fired: 0");
        shotsHit_label = new JLabel("Shots Hit: 0");
        shotsMissed_label = new JLabel("Shots Missed: 0");
        shotsAccuracy_label = new JLabel("Shots Accuracy: 0%");
        cpuStats_label = new JLabel("CPUs stats:");
        cpuShotsFired_label = new JLabel("Shots Fired: 0");
        cpuShotsHit_label = new JLabel("Shots Hit: 0");
        cpuShotsMissed_label = new JLabel("Shots Missed: 0");
        cpuShotsAccuracy_label = new JLabel("Shots Accuracy: 0%");
        label3.setVisible(false);
        label4.setVisible(false);
        info = new JLabel();
        info.setBounds(400,350,450, 50 );
        info.setFont(new Font(info.getFont().getName(), Font.PLAIN, 20));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        setInfoText("Choose positions of your ships down below" , "Wybierz położenie twoich statków na dolnej planszy");
        label1.setBounds(570,45,150, 25 );
        label2.setBounds(570,420,150, 25 );
        label3.setBounds(170,45,150, 25 );
        label4.setBounds(170,420,150, 25 );
        myStats_label.setBounds(800, 75, 175, 25);
        shotsFired_label.setBounds(800, 100, 175, 25);
        shotsHit_label.setBounds(800, 125, 175, 25);
        shotsMissed_label.setBounds(800, 150, 175, 25);
        shotsAccuracy_label.setBounds(800, 175, 175, 25);
        cpuStats_label.setBounds(800, 450, 175, 25);
        cpuShotsFired_label.setBounds(800, 475, 175, 25);
        cpuShotsHit_label.setBounds(800, 500, 175, 25);
        cpuShotsMissed_label.setBounds(800, 525, 175, 25);
        cpuShotsAccuracy_label.setBounds(800, 550, 175, 25);
        info.setBounds(350,350,550, 50 );
        info.setFont(new Font(info.getFont().getName(), Font.PLAIN, 20));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(myStats_label);
        panel.add(shotsFired_label);
        panel.add(shotsHit_label);
        panel.add(shotsMissed_label);
        panel.add(shotsAccuracy_label);
        panel.add(cpuStats_label);
        panel.add(cpuShotsFired_label);
        panel.add(cpuShotsHit_label);
        panel.add(cpuShotsMissed_label);
        panel.add(cpuShotsAccuracy_label);
        panel.add(info);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Battleships");
        frame.add(panel);
        frame.setJMenuBar(menuBar);
        frame.setSize(1075,775);
        frame.setVisible(true);
    }

    void Reset(){
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++){
                myShipsBoard[i][j].setText("");
                myShotsBoard[i][j].setText("");
                cpuShipsBoard[i][j].setText("");
                cpuShotsBoard[i][j].setText("");
                myShipsBoard[i][j].setEnabled(true);
                myShotsBoard[i][j].setEnabled(false);
                cpuShipsBoard[i][j].setEnabled(false);
                cpuShotsBoard[i][j].setEnabled(false);
                myShipsBoard[i][j].setOpaque(true);
                myShotsBoard[i][j].setOpaque(true);
                myShipsBoard[i][j].setBackground(Color.WHITE);
                myShotsBoard[i][j].setBackground(Color.WHITE);
                myShipsBoard[i][j].setForeground(Color.BLACK);
                myShotsBoard[i][j].setForeground(Color.BLACK);
                cpuShipsBoard[i][j].setBackground(Color.WHITE);
                cpuShotsBoard[i][j].setBackground(Color.WHITE);
                cpuShipsBoard[i][j].setForeground(Color.BLACK);
                cpuShotsBoard[i][j].setForeground(Color.BLACK);

            }
        for(int k=0;k<myShipsTotal;k++) {
            playerPos[k] = new position();
        }
        setInfoText("Choose positions of your ships down below" , "Wybierz położenie twoich statków na dolnej planszy");
        info.setFont(new Font(info.getFont().getName(), Font.PLAIN, 20));
        myShipCount=0;
        curFieldsCount=0;
        isPlacingShip=false;
        myShotsFired=0;
        myShotsHit=0;
        cpuShotsFired=0;
        cpuShotsHit=0;
        winner="";
        statsUpdate();
        generateCpuShips();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==showTutorial){
            JPanel placingShipsPanel = new JPanel(new GridLayout(1, 1));
            String text = englishButton.isSelected()
                    ? "<h2>This is placing ships tutorial.</h2>" +
                    "<p>The first click on your ships' board defines where you choose to be " +
                    "one of the ship's ends and the second click places the other end of your ship. </p>" +
                    "<p>Note that the second click is limited to a maximum of 4 locations relative to the first click.</p>"+
                    "<p>Ships occupying more than one square require 2 clicks to completely place the ship, " +
                    "but only one click is required for the smallest single-grid ships.</p>" +
                    "<p>There must be at least one grid gap between the ships (also diagonally).</p>"

                    : "<h2>To jest samouczek umieszczania statków.</h2>" +
                    "<p>Pierwsze kliknięcie na planszy twojego statku określa, gdzie wybierasz jeden z końców " +
                    "statku, a drugie kliknięcie umieszcza drugi koniec twojego statku. </p>" +
                    "<p>Pamiętaj, że drugie kliknięcie jest ograniczone do maksymalnie 4 lokalizacji względem pierwszego kliknięcia.</p>"+
                    "<p>W przypadku statków zajmujących więcej niż jedną kratkę do całkowitego umieszczenia statku potrzebne są 2 kliknięcia, " +
                    "lecz w przypadku najmniejszych jednokratktowych statków wymagane jest tylko jedno kliknięcie na planszy.</p>" +
                    "<p>Pomiędzy statkami musi być co najmniej jedna kratka przerwy (po ukosie również).</p>";

            JLabel placingShipsLabel = new JLabel("<html>"+text+"</html>");
            placingShipsPanel.add(placingShipsLabel);

            JPanel shootingPanel = new JPanel(new GridLayout(1, 1));
            text = englishButton.isSelected()
                    ? "<h2>This is shooting tutorial.</h2>" +
                    "<p>To shoot, select the shooting location on the board and press it.</p>" +
                    "<p>Color markings:</p>"+
                    "<li>Grey      - shot off target.</li>" +
                    "<li>Orange    - shot on target, ship not sunk.</li>" +
                    "<li>Red       - shot on target, ship sunk.</li>"

                    : "<h2>To jest samouczek strzelania.</h2>" +
                    "<p>Aby wystrzelić wybierz miejsce strzału na planszy i naciśnij je.</p>" +
                    "<p>Oznaczenia kolorów:</p>"+
                    "<li>Szary           - strzał niecelny.</li>" +
                    "<li>Pomarańczowy    - strzał celny, okręt niezatopiony.</li>" +
                    "<li>Czerwony        - strzał celny, okręt zatopiony.</li>";

            JLabel shootingLabel = new JLabel("<html>"+text+"</html>");
            shootingPanel.add(shootingLabel);

            JTabbedPane tabbedPane = new JTabbedPane();
            text = englishButton.isSelected() ? "Placing Ships" : "Umieszczanie Statków";
            tabbedPane.addTab(text, placingShipsPanel);
            text = englishButton.isSelected() ? "Shooting" : "Strzelanie";
            tabbedPane.addTab(text, shootingPanel);

            JFrame tutorialFrame = new JFrame();
            tutorialFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            String title = englishButton.isSelected() ? "Tutorial" : "Poradnik";
            tutorialFrame.setTitle(title);
            tutorialFrame.setLayout(new GridLayout(1, 1));
            tutorialFrame.setSize(600,400);
            tutorialFrame.setResizable(false);
            tutorialFrame.add(tabbedPane);
            tutorialFrame.setVisible(true);
        }
        if(e.getSource()==englishButton || e.getSource()==polishButton){
            changeLanguage();
        }
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++){
                if (e.getSource()==myShipsBoard[i][j]){
                    shipButtonClicked(i,j);
                }
            }
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++){
                if (e.getSource()==myShotsBoard[i][j]){
                    shotButtonClicked(i,j);
                }
            }
        if (e.getSource()==reset){
            Reset();
        }
        if(e.getSource()==cpuShips){
            if(cpuShips.isSelected()){
                for(int i=0;i<10;i++)
                    for(int j=0;j<10;j++){
                        cpuShipsBoard[i][j].setVisible(true);
                    }
                label4.setVisible(true);
            }
            if(!cpuShips.isSelected()){
                for(int i=0;i<10;i++)
                    for(int j=0;j<10;j++){
                        cpuShipsBoard[i][j].setVisible(false);
                    }
                label4.setVisible(false);
            }
        }
        if(e.getSource()==cpuShots){
            if(cpuShots.isSelected()){
                for(int i=0;i<10;i++)
                    for(int j=0;j<10;j++){
                        cpuShotsBoard[i][j].setVisible(true);
                    }
                label3.setVisible(true);
            }
            if(!cpuShots.isSelected()){
                for(int i=0;i<10;i++)
                    for(int j=0;j<10;j++){
                        cpuShotsBoard[i][j].setVisible(false);
                    }
                label3.setVisible(false);
            }
        }
    }

    void setInfoText(String english, String polish){
        if(englishButton.isSelected()) info.setText(english);
        if(polishButton.isSelected()) info.setText(polish);
    }

    void changeLanguage(){

        if (info.getText().equals("Choose positions of your ships down below") || info.getText().equals("Wybierz położenie twoich statków na dolnej planszy"))
            setInfoText("Choose positions of your ships down below" , "Wybierz położenie twoich statków na dolnej planszy");

        boolean english = englishButton.isSelected();
        String text = english ? "CPU" : "Komputer";
        cpuMenu.setText(text);
        text = english ? "Boards" : "Plansze";
        cpuBoards.setText(text);
        text = english ? "Hints" : "Podpowiedzi";
        nextMoveHintsMenu.setText(text);
        text = english ? "Show Ships" : "Pokaż Statki";
        cpuShips.setText(text);
        text = english ? "Show Shots" : "Pokaż Strzały";
        cpuShots.setText(text);
        text = english ? "Ships Location Tips" : "Podpowiedzi Lokalizacji Statków";
        shipsLocationTips.setText(text);
        text = english ? "Shooting Tips" : "Podpowiedzi Strzałów";
        whereToNotShootTips.setText(text);
        text = english ? "Reset" : "Reset";
        reset.setText(text);
        text = english ? "Language" : "Język";
        languageMenu.setText(text);
        text = english ? "English" : "Angielski";
        englishButton.setText(text);
        text = english ? "Polish" : "Polski";
        polishButton.setText(text);
        text = english ? "Tutorial" : "Poradnik";
        tutorialMenu.setText(text);
        text = english ? "Show Tutorial" : "Pokaż Poradnik";
        showTutorial.setText(text);
        text = english ? "Your Shots Board" : "Twoja Plansza Strzałów";
        label1.setText(text);
        text = english ? "Your Ships Board" : "Twoja Plansza Statków";
        label2.setText(text);
        text = english ? "CPUs Shots Board" : "Plansza Strzałów Komputera";
        label3.setText(text);
        text = english ? "CPUs Ships Board" : "Plansza Statków Komputera";
        label4.setText(text);
        text = english ? "Your stats:" : "Twoje statystyki:";
        myStats_label.setText(text);
        text = english ? "CPUs stats" : "Statystyki komputera:";
        cpuStats_label.setText(text);
        statsUpdate();
    }

    void disableAllButtons(){
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++){
                myShipsBoard[i][j].setEnabled(false);
                myShotsBoard[i][j].setEnabled(false);
                cpuShipsBoard[i][j].setEnabled(false);
                cpuShotsBoard[i][j].setEnabled(false);
            }
    }

    void disableAllShipButtons(){
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++){
                myShipsBoard[i][j].setEnabled(false);
            }
    }
    void enableAllShipButtons(){
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++){
                if(myShipsBoard[i][j].getBackground()!=Color.BLACK) myShipsBoard[i][j].setEnabled(true);
            }
    }

    void enableAllShotButtons(){
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++){
                myShotsBoard[i][j].setEnabled(true);
            }
    }

    void generateCpuShips(){
        Random r = new Random();
        cpuFieldsCount=0;
        int shipSize=4;
        for(int ship=1;ship<=10;ship++){
            int a, b;
            if(cpuFieldsCount>=4) shipSize = 3;
            if(cpuFieldsCount>=10) shipSize = 2;
            if(cpuFieldsCount>=16) shipSize = 1;
            if(shipSize>1){
                int orient;
                do{
                    a=r.nextInt(10);
                    b=r.nextInt(10);
                    orient=r.nextInt(4);

                }while(!cpuShipCanBePlaced(a, b, orient, shipSize));
                placeCpuShip(a, b, orient, shipSize);

            }
            else{
                do{
                    a=r.nextInt(10);
                    b=r.nextInt(10);
                }while(!placeCanBeTakenByCPU(a, b));
                assignPlaceToCPU(a, b);

            }

        }
    }

    void assignPlaceToCPU(int x, int y){
        cpuShipsBoard[x][y].setEnabled(false);
        cpuShipsBoard[x][y].setOpaque(true);
        cpuShipsBoard[x][y].setBackground(Color.BLACK);
        cpuPos[cpuFieldsCount] = new position();
        cpuPos[cpuFieldsCount].x=x;
        cpuPos[cpuFieldsCount].y=y;
        cpuFieldsCount++;

    }

    boolean cpuShipCanBePlaced(int i, int j, int o, int ss){
        if (cpuFieldsCount>myShipsTotal || !placeCanBeTakenByCPU(i, j)){
            return false;
        }
        switch(o){
            case 0: {
                int up = j - ss + 1;
                if (up < 0) {
                    return false;
                }
                up--;
                if(up<0) up=0;
                for (int k = up; k < j; k++) {
                    if (!placeCanBeTakenByCPU(i, k)) {
                        return false;
                    }
                }
                break;
            }
            case 1: {
                int right = i + ss;
                if (right > 9) {
                    return false;
                }
                right++;
                if(right>9) right=9;
                for (int k = i; k < right + 1; k++) {
                    if (!placeCanBeTakenByCPU(k, j)) {
                        return false;
                    }
                }
                break;
            }
            case 2: {
                int down = j + ss;
                if (down > 9) {
                    return false;
                }
                down++;
                if(down>9) down=9;
                for (int k = j; k < down + 1; k++) {
                    if (!placeCanBeTakenByCPU(i, k)) {
                        return false;
                    }
                }
                break;
            }
            case 3: {
                int left = i - ss + 1;
                if (left < 0) {
                    return false;
                }
                left--;
                if(left<0) left=0;
                for (int k = left; k < i; k++) {
                    if (!placeCanBeTakenByCPU(k, j)) {
                        return false;
                    }
                }
                break;
            }
            default: {

                break;
            }
        }
        return true;
    }

    void placeCpuShip(int i, int j, int o, int ss){
        assignPlaceToCPU(i, j);
        switch(o){
            case 0:
                int up = j - ss;
                for(int k=up+1;k<j;k++){
                    assignPlaceToCPU(i, k);
                }

                break;
            case 1:
                int right = i + ss;
                for(int k=i+1;k<right;k++){
                    assignPlaceToCPU(k, j);
                }

                break;
            case 2:
                int down = j + ss;
                for(int k=j+1;k<down;k++){
                    assignPlaceToCPU(i, k);
                }

                break;
            case 3:
                int left = i - ss;
                for(int k=left+1;k<i;k++){
                    assignPlaceToCPU(k, j);
                }

                break;
            default:

                break;
        }
    }

    boolean placeCanBeTakenByCPU(int i, int j){
        if (cpuFieldsCount>myShipsTotal){
            return false;
        }
        int left = i - 1, up = j - 1, right = i + 1, down = j + 1;
        if (left < 0) left = 0;
        if (up < 0) up = 0;
        if (right > 9) right = 9;
        if (down > 9) down = 9;
        for (int a = left; a <= right; a++)
            for (int b = up; b <= down; b++) {
                if(cpuShipsBoard[a][b].getBackground()==Color.BLACK) {
                    return false;
                }
            }

        return true;
    }

    boolean placeCanBeTaken(int i, int j){
        if (myShipCount>myShipsTotal){
            return false;
        }
        int left = i - 1, up = j - 1, right = i + 1, down = j + 1;
        if (left < 0) left = 0;
        if (up < 0) up = 0;
        if (right > 9) right = 9;
        if (down > 9) down = 9;
        for (int a = left; a <= right; a++)
            for (int b = up; b <= down; b++) {
                if(myShipsBoard[a][b].getBackground()==Color.BLACK && !isPlacingShip) {
                    for(int c=0;c<myShipCount;c++){
                        if (c>0 && !(a == playerPos[c-1].x && b == playerPos[c-1].y)) return false;
                    }
                }
            }
        return true;
    }

    void shipButtonClicked(int x, int y){
        int checkPossibilities=0;
        if (placeCanBeTaken(x,y)){
            myShipsBoard[x][y].setEnabled(false);
            myShipsBoard[x][y].setOpaque(true);
            myShipsBoard[x][y].setBackground(Color.BLACK);
            playerPos[myShipCount].x=x;
            playerPos[myShipCount].y=y;
            curFieldsCount++;
            int shipSize=4;
            if (shipSize==4) {
                setInfoText("Place your Battleship." , "Umieść twój okręt bitewny.");
            }
            if(curFieldsCount>=4) {
                shipSize = 3;
                setInfoText("Place your Destroyer." , "Umieść twojego niszczyciela.");
            }
            if(curFieldsCount>=10) {
                shipSize = 2;
                setInfoText("Place your Submarine." , "Umieść twoją łódź podwodną.");
            }
            if(curFieldsCount>=16) {
                shipSize = 1;
                setInfoText("Place your Partol Boat." , "Umieśc twoją łódź patrolową.");
            }
            if(!isPlacingShip && shipSize>1) {
                if(curFieldsCount<=16) disableAllShipButtons();
                int limitLewy = x - (shipSize-1), limitPrawy = x + shipSize, limitGorny = y - (shipSize-1), limitDolny = y + shipSize;
                if (limitLewy < 0) limitLewy = 0;
                if (limitPrawy > 10) limitPrawy = 10;
                if (limitGorny < 0) limitGorny = 0;
                if (limitDolny > 10) limitDolny = 10;
                for (int i = limitLewy; i < limitPrawy; i++)
                    for (int j = limitGorny; j < limitDolny; j++) {
                        if ((i == x ^ j == y) && (i==x+shipSize-1 || i==x-shipSize+1 || j==y+shipSize-1 || j==y-shipSize+1) && myShipsBoard[i][j].getBackground()!=Color.BLACK) myShipsBoard[i][j].setEnabled(true);
                    }
                limitLewy = x - shipSize;
                limitPrawy = x + shipSize + 1;
                limitGorny = y - shipSize;
                limitDolny = y + shipSize + 1;
                if (limitLewy < 0) limitLewy = 0;
                if (limitPrawy > 10) limitPrawy = 10;
                if (limitGorny < 0) limitGorny = 0;
                if (limitDolny > 10) limitDolny = 10;
                for (int i = limitLewy; i < limitPrawy; i++)
                    for (int j = limitGorny; j < limitDolny; j++) {
                        if(myShipsBoard[i][j].getBackground()==Color.BLACK){
                            if(i!=x || j!=y) {
                                int left = i - 1, up = j - 1, right = i + 1, down = j + 1;
                                if (left < 0) left = 0;
                                if (up < 0) up = 0;
                                if (right > 9) right = 9;
                                if (down > 9) down = 9;
                                for (int a = left; a <= right; a++)
                                    for (int b = up; b <= down; b++) {
                                        myShipsBoard[a][b].setEnabled(false);
                                    }
                            }
                        }
                    }
                checkPossibilities=0;
                for(int i=0;i<10;i++)
                    for(int j=0;j<10;j++){
                        if(myShipsBoard[i][j].isEnabled()){
                            checkPossibilities++;
                        }
                    }
                isPlacingShip=true;
                if(checkPossibilities==0){
                    enableAllShipButtons();
                    myShipsBoard[x][y].setEnabled(true);
                    myShipsBoard[x][y].setOpaque(true);
                    myShipsBoard[x][y].setBackground(Color.WHITE);
                    playerPos[myShipCount] = new position();
                    curFieldsCount--;
                    isPlacingShip=false;
                }
            }
            else if(isPlacingShip && curFieldsCount<=16){
                setInfoText("Choose positions of your ships down below" , "Wybierz położenie twoich statków na dolnej planszy");
                if(playerPos[myShipCount-1].x==x){
                    if(playerPos[myShipCount-1].y>y){
                        for(int i=y+1;i<playerPos[myShipCount-1].y;i++){
                            myShipsBoard[x][i].setEnabled(false);
                            myShipsBoard[x][i].setOpaque(true);
                            myShipsBoard[x][i].setBackground(Color.BLACK);
                            curFieldsCount++;
                            playerPos[myShipCount].x=x;
                            playerPos[myShipCount].y=y;
                        }
                    }
                    else if(playerPos[myShipCount-1].y<y){
                        for(int i=playerPos[myShipCount-1].y+1;i<y;i++){
                            myShipsBoard[x][i].setEnabled(false);
                            myShipsBoard[x][i].setOpaque(true);
                            myShipsBoard[x][i].setBackground(Color.BLACK);
                            curFieldsCount++;
                            playerPos[myShipCount].x=x;
                            playerPos[myShipCount].y=y;
                        }
                    }
                }
                else if(playerPos[myShipCount-1].y==y){
                    if(playerPos[myShipCount-1].x>x){
                        for(int i=x+1;i<playerPos[myShipCount-1].x;i++){
                            myShipsBoard[i][y].setEnabled(false);
                            myShipsBoard[i][y].setOpaque(true);
                            myShipsBoard[i][y].setBackground(Color.BLACK);
                            curFieldsCount++;
                            playerPos[myShipCount].x=x;
                            playerPos[myShipCount].y=y;
                        }
                    }
                    else if(playerPos[myShipCount-1].x<x){
                        for(int i=playerPos[myShipCount-1].x+1;i<x;i++){
                            myShipsBoard[i][y].setEnabled(false);
                            myShipsBoard[i][y].setOpaque(true);
                            myShipsBoard[i][y].setBackground(Color.BLACK);
                            curFieldsCount++;
                            playerPos[myShipCount].x=x;
                            playerPos[myShipCount].y=y;
                        }
                    }
                }
                enableAllShipButtons();
                isPlacingShip=false;
            }
            if ((checkPossibilities!=0 && shipSize>1)) {
                myShipCount++;
            }
            else if (shipSize==1 && curFieldsCount<myShipsTotal) {
                myShipCount++;
            }
            if (curFieldsCount>=myShipsTotal) {
                setInfoText("Now shoot the opponent on board up above" , "Teraz strzelaj do przeciwnika na górnej planszy");
                disableAllShipButtons();
                enableAllShotButtons();
            }
        }
        else{
            setInfoText("You cannot place your ship in there" , "Nie możesz tu umieścić statku");
        }
    }

    void shotButtonClicked(int i, int j){
        myShotsBoard[i][j].setEnabled(false);
        myShotsBoard[i][j].setOpaque(true);
        playerShotPos[myShotsFired] = new position();
        playerShotPos[myShotsFired].x=i;
        playerShotPos[myShotsFired].y=j;
        if(!cpuShipsBoard[i][j].getBackground().equals(Color.BLACK)){
            myShotsBoard[i][j].setBackground(Color.GRAY);
            cpuShipsBoard[i][j].setText("O");
        }
        if(cpuShipsBoard[i][j].getBackground().equals(Color.BLACK)){
            boolean hitShipHasSunk=false;
            lastHitPos = playerShotPos[myShotsFired];
            int sSize=0, orient=0, up=j-1, down=j+1, left=i-1, right=i+1;
            if(up<0) up=down;
            if(right>9) right=left;
            if(down>9) down=up;
            if(left<0) left=right;
            if(cpuShipsBoard[i][j].getBackground().equals(Color.BLACK) && (cpuShipsBoard[i][up].getBackground().equals(Color.ORANGE) || cpuShipsBoard[i][down].getBackground().equals(Color.ORANGE) || cpuShipsBoard[i][up].getBackground().equals(Color.BLACK) || cpuShipsBoard[i][down].getBackground().equals(Color.BLACK))) {
                myShotsBoard[i][j].setBackground(Color.ORANGE);
                cpuShipsBoard[i][j].setBackground(Color.ORANGE);
                int limitUP=j-3, limitDOWN=j+3, shipsCount=0, shipsHit=0;
                if(limitUP<0) limitUP=0;
                if(limitDOWN>9) limitDOWN=9;
                int min=j, max=j;
                for(int k=j;k>=limitUP;k--){
                    if(cpuShipsBoard[i][k].getBackground().equals(Color.WHITE)){
                        break;
                    }
                    if(!cpuShipsBoard[i][k].getBackground().equals(Color.WHITE)) {
                        shipsCount++;
                        if(k<min) min=k;
                        if(k>max) max=k;
                    }
                    if(cpuShipsBoard[i][k].getBackground().equals(Color.ORANGE))  shipsHit++;
                }
                for(int k=j;k<=limitDOWN;k++){
                    if(cpuShipsBoard[i][k].getBackground().equals(Color.WHITE)){
                        break;
                    }
                    if(!cpuShipsBoard[i][k].getBackground().equals(Color.WHITE) && k!=j) {
                        shipsCount++;
                        if(k<min) min=k;
                        if(k>max) max=k;
                    }
                    if(cpuShipsBoard[i][k].getBackground().equals(Color.ORANGE) && k!=j)  shipsHit++;
                }
                if(shipsCount==shipsHit) {
                    for(int k=min;k<=max;k++){
                        if(!cpuShipsBoard[i][k].getBackground().equals(Color.WHITE)) {
                            cpuShipsBoard[i][k].setBackground(Color.RED);
                            myShotsBoard[i][k].setBackground(Color.RED);
                        }
                    }
                    hitShipHasSunk = true;
                }
            }
            else if(cpuShipsBoard[i][j].getBackground().equals(Color.BLACK) && (cpuShipsBoard[right][j].getBackground().equals(Color.ORANGE) || cpuShipsBoard[left][j].getBackground().equals(Color.ORANGE) || cpuShipsBoard[right][j].getBackground().equals(Color.BLACK) || cpuShipsBoard[left][j].getBackground().equals(Color.BLACK))) {
                myShotsBoard[i][j].setBackground(Color.ORANGE);
                cpuShipsBoard[i][j].setBackground(Color.ORANGE);
                int limitLEFT=i-3, limitRIGHT=i+3, shipsCount=0, shipsHit=0;
                if(limitLEFT<0) limitLEFT=0;
                if(limitRIGHT>9) limitRIGHT=9;
                int min=i, max=i;
                for(int k=i;k>=limitLEFT;k--){
                    if(cpuShipsBoard[k][j].getBackground().equals(Color.WHITE)){
                        break;
                    }
                    if(!cpuShipsBoard[k][j].getBackground().equals(Color.WHITE)) {
                        shipsCount++;
                        if(k<min) min=k;
                        if(k>max) max=k;
                    }
                    if(cpuShipsBoard[k][j].getBackground().equals(Color.ORANGE))  shipsHit++;
                }
                for(int k=i;k<=limitRIGHT;k++){
                    if(cpuShipsBoard[k][j].getBackground().equals(Color.WHITE)){
                        break;
                    }
                    if(!cpuShipsBoard[k][j].getBackground().equals(Color.WHITE) && k!=i) {
                        shipsCount++;
                        if(k<min) min=k;
                        if(k>max) max=k;
                    }
                    if(cpuShipsBoard[k][j].getBackground().equals(Color.ORANGE) && k!=i)  shipsHit++;
                }
                if(shipsCount==shipsHit) {
                    for(int k=min;k<=max;k++){
                        if(!cpuShipsBoard[k][j].getBackground().equals(Color.WHITE)) {
                            cpuShipsBoard[k][j].setBackground(Color.RED);
                            myShotsBoard[k][j].setBackground(Color.RED);
                        }
                    }
                    hitShipHasSunk = true;
                }
            }
            else{
                hitShipHasSunk=true;
            }
            if(hitShipHasSunk){
                myShotsBoard[i][j].setBackground(Color.RED);
                cpuShipsBoard[i][j].setBackground(Color.RED);
            }
            cpuShipsBoard[i][j].setText("X");
            myShotsHit++;
        }
        myShotsFired++;
        statsUpdate();
        if(shipsLocationTips.isSelected() && lastHitPos!=null) updateShipLocationTip();
        if(whereToNotShootTips.isSelected()) updateShootingTip();
        checkWinner("player");
        if(!winner.equals("player")){
            cpuMove();
            statsUpdate();
            checkWinner("cpu");
        }
    }

    void updateShipLocationTip(){
        for (int i = 0; i < myShotsBoard.length; i++) {             //Clearing tips from board
            for (int j = 0; j < myShotsBoard[0].length; j++) {
                if(myShotsBoard[i][j].getText().equals("?")) myShotsBoard[i][j].setText("");
            }
        }
        System.out.println("\n\n");
        for (int i = 0; i < myShotsBoard.length; i++) {             //Showing Tips
            for (int j = 0; j < myShotsBoard[0].length; j++) {
                if(i==lastHitPos.x && j== lastHitPos.y && myShotsBoard[i][j].getBackground().equals(Color.ORANGE)){
                    int hitCounter = 1;
                    for (int x = i+1; x <= i+3 && x<=9; x++) {
                        if(myShotsBoard[x][j].getBackground().equals(Color.ORANGE)) {
                            hitCounter++;
                            System.out.println("hit at x="+x+" y="+j+"    counter="+hitCounter);
                        }
                        else break;
                    }
                    for (int x = i-1; x >= i-3 && x>=0; x--) {
                        if(myShotsBoard[x][j].getBackground().equals(Color.ORANGE)) {
                            hitCounter++;
                            System.out.println("hit at x="+x+" y="+j+"    counter="+hitCounter);
                        }
                        else break;
                    }
                    boolean shipVertical = false, shipHorizontal = hitCounter>1;
                    System.out.println("ship horizontal - "+shipHorizontal);
                    if(!shipHorizontal) {
                        for (int y = j + 1; y <= j + 3 && y<=9; y++) {
                            if (myShotsBoard[i][y].getBackground().equals(Color.ORANGE)) {
                                hitCounter++;
                                System.out.println("hit at x="+i+" y="+y+"    counter="+hitCounter);
                            }
                            else break;
                        }
                        for (int y = j - 1; y >= j - 3 && y>=0; y--) {
                            if (myShotsBoard[i][y].getBackground().equals(Color.ORANGE)) {
                                hitCounter++;
                                System.out.println("hit at x="+i+" y="+y+"    counter="+hitCounter);
                            }
                            else break;
                        }
                        shipVertical = hitCounter > 1;
                        System.out.println("ship vertical - "+shipVertical);
                    }
                    if (shipHorizontal) {
                        for (int x = i+1; x <= i+3 && x<=9; x++) {
                            if (myShotsBoard[x][j].getBackground().equals(Color.WHITE) && myShotsBoard[x-1][j].getBackground().equals(Color.ORANGE)) {
                                myShotsBoard[x][j].setText("?");
                                break;
                            }
                        }
                        for (int x = i-1; x >= i-3 && x>=0; x--) {
                            if (myShotsBoard[x][j].getBackground().equals(Color.WHITE) && myShotsBoard[x+1][j].getBackground().equals(Color.ORANGE)) {
                                myShotsBoard[x][j].setText("?");
                                break;
                            }
                        }
                    }
                    else if(shipVertical){
                        for (int y = j + 1; y <= j + 3 && y<=9; y++) {
                        if (myShotsBoard[i][y].getBackground().equals(Color.WHITE) && myShotsBoard[i][y-1].getBackground().equals(Color.ORANGE)) {
                            myShotsBoard[i][y].setText("?");
                            break;
                        }
                    }
                        for (int y = j - 1; y >= j - 3 && y>=0; y--) {
                            if (myShotsBoard[i][y].getBackground().equals(Color.WHITE) && myShotsBoard[i][y+1].getBackground().equals(Color.ORANGE)){
                                myShotsBoard[i][y].setText("?");
                                break;
                            }
                        }
                    }
                    else{
                        if (i > 0 && myShotsBoard[i - 1][j].getBackground().equals(Color.WHITE))
                            myShotsBoard[i - 1][j].setText("?");
                        if (i < 9 && myShotsBoard[i + 1][j].getBackground().equals(Color.WHITE))
                            myShotsBoard[i + 1][j].setText("?");
                        if (j > 0 && myShotsBoard[i][j - 1].getBackground().equals(Color.WHITE))
                            myShotsBoard[i][j - 1].setText("?");
                        if (j < 9 && myShotsBoard[i][j + 1].getBackground().equals(Color.WHITE))
                            myShotsBoard[i][j + 1].setText("?");
                    }
                }
            }
        }
    }

    void updateShootingTip(){
        for (int i = 0; i < myShotsBoard.length; i++) {             //Clearing tips from board
            for (int j = 0; j < myShotsBoard[0].length; j++) {
                if(myShotsBoard[i][j].getText().equals("-")) myShotsBoard[i][j].setText("");
            }
        }
    }

    void cpuMove(){
        Random r = new Random();
        int a,b;
        do{
            a=r.nextInt(10);
            b=r.nextInt(10);
        }while(cpuShotsBoard[a][b].getBackground()!=Color.WHITE);
        cpuShotsBoard[a][b].setEnabled(false);
        cpuShotsBoard[a][b].setOpaque(true);
        cpuShotPos[cpuShotsFired] = new position();
        cpuShotPos[cpuShotsFired].x=a;
        cpuShotPos[cpuShotsFired].y=b;

        if(!myShipsBoard[a][b].getBackground().equals(Color.BLACK)){
            cpuShotsBoard[a][b].setBackground(Color.GRAY);
            myShipsBoard[a][b].setText("O");
        }
        if(myShipsBoard[a][b].getBackground().equals(Color.BLACK)){

            boolean hitShipHasSunk=false;
            int sSize=0, orient=0, up=b-1, down=b+1, left=a-1, right=a+1;
            if(up<0) up=down;
            if(right>9) right=left;
            if(down>9) down=up;
            if(left<0) left=right;
            if(myShipsBoard[a][b].getBackground().equals(Color.BLACK) && (myShipsBoard[a][up].getBackground().equals(Color.ORANGE) || myShipsBoard[a][down].getBackground().equals(Color.ORANGE) || myShipsBoard[a][up].getBackground().equals(Color.BLACK) || myShipsBoard[a][down].getBackground().equals(Color.BLACK))) {
                cpuShotsBoard[a][b].setBackground(Color.ORANGE);
                myShipsBoard[a][b].setBackground(Color.ORANGE);
                int limitUP=b-3, limitDOWN=b+3, shipsCount=0, shipsHit=0;
                if(limitUP<0) limitUP=0;
                if(limitDOWN>9) limitDOWN=9;
                int min=b, max=b;
                for(int k=b;k>=limitUP;k--){
                    if(myShipsBoard[a][k].getBackground().equals(Color.WHITE)){
                        break;
                    }
                    if(!myShipsBoard[a][k].getBackground().equals(Color.WHITE)) {
                        shipsCount++;
                        if(k<min) min=k;
                        if(k>max) max=k;
                    }
                    if(myShipsBoard[a][k].getBackground().equals(Color.ORANGE))  shipsHit++;
                }
                for(int k=b;k<=limitDOWN;k++){
                    if(myShipsBoard[a][k].getBackground().equals(Color.WHITE)){
                        break;
                    }
                    if(!myShipsBoard[a][k].getBackground().equals(Color.WHITE) && k!=b) {
                        shipsCount++;
                        if(k<min) min=k;
                        if(k>max) max=k;
                    }
                    if(myShipsBoard[a][k].getBackground().equals(Color.ORANGE) && k!=b)  shipsHit++;
                }
                if(shipsCount==shipsHit) {
                    for(int k=min;k<=max;k++){
                        if(!myShipsBoard[a][k].getBackground().equals(Color.WHITE)) {
                            myShipsBoard[a][k].setBackground(Color.RED);
                            cpuShotsBoard[a][k].setBackground(Color.RED);
                        }
                    }
                    hitShipHasSunk = true;
                }
            }
            else if(myShipsBoard[a][b].getBackground().equals(Color.BLACK) && (myShipsBoard[right][b].getBackground().equals(Color.ORANGE) || myShipsBoard[left][b].getBackground().equals(Color.ORANGE) || myShipsBoard[right][b].getBackground().equals(Color.BLACK) || myShipsBoard[left][b].getBackground().equals(Color.BLACK))) {
                cpuShotsBoard[a][b].setBackground(Color.ORANGE);
                myShipsBoard[a][b].setBackground(Color.ORANGE);
                int limitLEFT=a-3, limitRIGHT=a+3, shipsCount=0, shipsHit=0;
                if(limitLEFT<0) limitLEFT=0;
                if(limitRIGHT>9) limitRIGHT=9;
                int min=a, max=a;
                for(int k=a;k>=limitLEFT;k--){
                    if(myShipsBoard[k][b].getBackground().equals(Color.WHITE)){
                        break;
                    }
                    if(!myShipsBoard[k][b].getBackground().equals(Color.WHITE)) {
                        shipsCount++;
                        if(k<min) min=k;
                        if(k>max) max=k;
                    }
                    if(myShipsBoard[k][b].getBackground().equals(Color.ORANGE))  shipsHit++;
                }
                for(int k=a;k<=limitRIGHT;k++){
                    if(myShipsBoard[k][b].getBackground().equals(Color.WHITE)){
                        break;
                    }
                    if(!myShipsBoard[k][b].getBackground().equals(Color.WHITE) && k!=a) {
                        shipsCount++;
                        if(k<min) min=k;
                        if(k>max) max=k;
                    }
                    if(myShipsBoard[k][b].getBackground().equals(Color.ORANGE) && k!=a)  shipsHit++;
                }
                if(shipsCount==shipsHit) {
                    for(int k=min;k<=max;k++){
                        if(!myShipsBoard[k][b].getBackground().equals(Color.WHITE)) {
                            myShipsBoard[k][b].setBackground(Color.RED);
                            cpuShotsBoard[k][b].setBackground(Color.RED);
                        }
                    }
                    hitShipHasSunk = true;
                }
            }
            else{
                hitShipHasSunk=true;
            }
            if(hitShipHasSunk){
                cpuShotsBoard[a][b].setBackground(Color.RED);
                myShipsBoard[a][b].setBackground(Color.RED);
            }
            myShipsBoard[a][b].setText("X");
            cpuShotsHit++;
        }

        cpuShotsFired++;
    }

    void checkWinner(String user){
        if(user.equals("player")){
            int playerCheck=0;
            for(int i=0;i<=myShotsFired-1;i++) {
                for (int j = 0; j < cpuPos.length; j++) {
                    if (playerShotPos[i].x == cpuPos[j].x && playerShotPos[i].y == cpuPos[j].y) {
                        playerCheck++;
                    }
                }
            }
            if(playerCheck==cpuPos.length){
                disableAllButtons();
                info.setFont(new Font(info.getFont().getName(), Font.PLAIN, 25));
                setInfoText("You won! Congratulations!" , "Zwycięstwo! Gratulacje!");
                winner="player";
            }
        }
        if(user.equals("cpu")){
            int cpuCheck=0;
            for(int i=0;i<cpuShotsFired;i++)
                for(int j=0;j<playerPos.length;j++)
                    if(cpuShotPos[i].x==playerPos[j].x && cpuShotPos[i].y==playerPos[j].y)
                        cpuCheck++;


            if(cpuCheck==playerPos.length){
                disableAllButtons();
                setInfoText("CPU won! Sorry :/" , "Komputer wygrał! :/");
                winner="cpu";
            }
        }
    }

    void statsUpdate(){
        System.out.println("my shots fired: "+myShotsFired);
        boolean english = englishButton.isSelected();
        String text = english ? "Shots Fired: "+myShotsFired : "Oddanych Strzałów: "+myShotsFired;
        shotsFired_label.setText(text);
        text = english ? "Shots Hit: "+myShotsHit : "Celnych Strzałów: "+myShotsHit;
        shotsHit_label.setText(text);
        int shotsMissed=myShotsFired-myShotsHit;
        text = english ? "Shots Missed: "+shotsMissed : "Niecelnych Strzałów: "+shotsMissed;
        shotsMissed_label.setText(text);
        double accuracy = (myShotsHit*1.0) / (myShotsFired)*100.0;
        text = english ? "Shots Accuracy: "+Math.round(accuracy)+"%" : "Celność Strzałów: "+Math.round(accuracy)+"%";
        shotsAccuracy_label.setText(text);


        text = english ? "Shots Fired: "+(cpuShotsFired) : "Oddanych Strzałów: "+(cpuShotsFired);
        cpuShotsFired_label.setText(text);
        text = english ? "Shots Hit: "+cpuShotsHit : "Celnych Strzałów: "+cpuShotsHit;
        cpuShotsHit_label.setText(text);
        int cpuShotsMissed=(cpuShotsFired-cpuShotsHit);
        text = english ? "Shots Missed: "+cpuShotsMissed : "Niecelnych Strzałów: "+cpuShotsMissed;
        cpuShotsMissed_label.setText(text);
        double cpuAccuracy = (cpuShotsHit*1.0) / (cpuShotsFired*1.0)*100;
        text = english ? "Shots Accuracy: "+Math.round(cpuAccuracy)+"%" : "Celność Strzałów: "+Math.round(cpuAccuracy)+"%";
        cpuShotsAccuracy_label.setText(text);
    }
}

class position{
    int x;
    int y;
}
