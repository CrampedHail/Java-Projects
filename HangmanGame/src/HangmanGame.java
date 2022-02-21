import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class HangmanGame {

    static HangmanGUI gui;
    static WordBank wordBank;
    static String filePath = "words.dat";
    static String word;
    static String guessedLetters;
    static String hiddenWord;

    static int guesses;
    static int guessesRight;
    static int guessesWrong;
    public static void main(String[] args){
        gui = new HangmanGUI();
        wordBank = new WordBank();

    }

    static void StartNewGame(){
        Random r = new Random();
        guesses = 0;
        guessesRight = 0;
        guessesWrong = 0;
        gui.hangmanPanel.repaint();
        gui.EnableKeyboard();
        gui.ClearLetterLabels();
        gui.messages.setText("Spróbuj zgadnąć słowo:");
        word = ""+wordBank.words.get(r.nextInt(wordBank.words.size())).toString().toUpperCase();
        hiddenWord = word;
        guessedLetters = "";
        for (int i = 0; i < word.length(); i++) {
            guessedLetters = guessedLetters + " ";
            if(word.charAt(i)!=guessedLetters.charAt(i)) hiddenWord = hiddenWord.replaceAll(""+word.charAt(i), "_ ");
        }
        gui.wordLabel.setText(hiddenWord);
    }

    static String getWord(){
        return word;
    }

    static void StatusUpdate(String key){
        guesses++;
        if(word.toLowerCase().contains(key.toLowerCase())){
            hiddenWord = "";
            for (int i = 0; i < word.length(); i++) {
                if(String.valueOf(word.charAt(i)).equals(key)) {
                    guessedLetters = guessedLetters.substring(0, i)+word.charAt(i)+guessedLetters.subSequence(i+1, guessedLetters.length());
                }
                if(guessedLetters.charAt(i)==word.charAt(i)){
                    hiddenWord = hiddenWord.concat(String.valueOf(guessedLetters.charAt(i)).toUpperCase() + " ");
                }
                else hiddenWord = hiddenWord.concat("_"+" ");
            }
            gui.wordLabel.setText(hiddenWord);
            gui.letterLabels[guesses-1].setText(key);
            gui.letterLabels[guesses-1].setForeground(Color.GREEN);
            guessesRight++;
        }
        else{
            gui.letterLabels[guesses-1].setText(key);
            gui.letterLabels[guesses-1].setForeground(Color.RED);
            gui.hangmanPanel.repaint();
            guessesWrong++;
        }
        CheckWinner();
    }

    static void CheckWinner(){
        if(guessesWrong>=11){
            WordGuessed(false);
        }
        else if(word.equals(guessedLetters)){
            WordGuessed(true);
        }
    }

    static void WordGuessed(boolean guessed){
        if(guessed){
            gui.messages.setText("Brawo! Udało się!");
            gui.DisableKeyboard();
        }
        else{
            gui.ShowWord();
            gui.messages.setText("O NIE! Nie udało się :( Spróbuj ponownie...");
            gui.DisableKeyboard();
        }
    }

    static void EditWordBank(){
        new WordBankEditor();
    }
}

class WordBankEditor{

    JTextField textField;
    JButton add;
    JButton delete;
    JPanel wordsPanel;
    ArrayList wordLabel;
    JScrollPane wordsScrollPane;
    JPanel textFieldPanel;
    JPanel buttonPanel;
    JFrame frame;
    
    WordBankEditor(){

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 35));

        add = new JButton("Dodaj");

        delete = new JButton("Usuń");

        wordsPanel = new JPanel();
        wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.Y_AXIS));

        wordLabel = new ArrayList<JLabel>();
        for (int i = 0; i < HangmanGame.wordBank.words.size(); i++) {
            JLabel label = new JLabel(""+HangmanGame.wordBank.words.get(i));
            label.setPreferredSize(new Dimension(175, 60));
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
            label.setToolTipText("Kliknij na słowo aby je wybrać.");
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    textField.setText(label.getText());
                }
            });
            wordLabel.add(label);
            wordsPanel.add(label);
        }
        wordsPanel.setPreferredSize(new Dimension(300, 30*HangmanGame.wordBank.words.size()));

        wordsScrollPane = new JScrollPane(wordsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        textFieldPanel.setPreferredSize(new Dimension(300, 50));
        textFieldPanel.add(textField);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(100, 400));
        buttonPanel.add(add);
        buttonPanel.add(delete);

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setMaximumSize(new Dimension(300, 700));
        frame.setTitle("Edit Word Bank");
        frame.setLocationRelativeTo(null);
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(textFieldPanel, BorderLayout.NORTH);
        frame.add(wordsScrollPane, BorderLayout.CENTER);
        frame.setVisible(true);


        add.setMnemonic(KeyEvent.VK_ENTER);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                if(text.isBlank() || text.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Pole tekstowe jest puste...", "Błąd dodawania słowa", JOptionPane.ERROR_MESSAGE);
                }
                else if(HangmanGame.wordBank.words.contains(text.toLowerCase())){
                    JOptionPane.showMessageDialog(null, "Podane słowo już jest w banku słów", "Błąd dodawania słowa", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    AddWord();
                    JOptionPane.showMessageDialog(null, "Dodano słowo do bazy", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    HangmanGame.wordBank.Save();
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                if(text.isBlank() || text.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Pole tekstowe jest puste...", "Błąd usuwania słowa", JOptionPane.ERROR_MESSAGE);
                }
                else if(!HangmanGame.wordBank.words.contains(text.toLowerCase())){
                    JOptionPane.showMessageDialog(null, "Podanego słowa nie ma w banku słów", "Błąd usuwania słowa", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    DeleteWord();
                    JOptionPane.showMessageDialog(null, "Usunięto słowo z bazy", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    HangmanGame.wordBank.Save();
                }
            }
        });
    }

    void AddWord(){
        HangmanGame.wordBank.words.add(textField.getText().toLowerCase());
        JLabel label = new JLabel(""+HangmanGame.wordBank.words.get(HangmanGame.wordBank.words.size()-1));
        label.setPreferredSize(new Dimension(175, 60));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        wordLabel.add(label);
        wordsPanel.setPreferredSize(new Dimension(300, 30*HangmanGame.wordBank.words.size()));
        frame.dispose();
        HangmanGame.EditWordBank();
    }

    void DeleteWord(){
        HangmanGame.wordBank.words.remove(textField.getText().toLowerCase());
        wordLabel.remove(wordLabel.get(wordLabel.size()-1));
        JLabel label = new JLabel(""+HangmanGame.wordBank.words.get(HangmanGame.wordBank.words.size()-1));
        label.setPreferredSize(new Dimension(175, 60));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        wordsPanel.remove(label);
        wordsPanel.setPreferredSize(new Dimension(300, 30*HangmanGame.wordBank.words.size()));
        frame.dispose();
        HangmanGame.EditWordBank();
    }
}

class HangmanGUI implements ActionListener, KeyListener{

    private final static int WIDTH = 1000;
    private final static int HEIGHT = 600;

    final private JMenuItem editWordBank;
    JButton reset;
    JButton tryToGuess;
    JLabel messages;
    JLabel wordLabel;
    MyPanel hangmanPanel;
    JPanel lettersPanel;
    JLabel[] letterLabels;
    private final JButton[] keyboard;

    private final String letters = "AĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWYZŹŻ";

    HangmanGUI(){


        JMenuBar menuBar = new JMenuBar();
        JMenu wordBankMenu = new JMenu("Baza słów");
        wordBankMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editWordBank = new JMenuItem("Edytuj bazę słów");
        editWordBank.addActionListener(this);
        editWordBank.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        wordBankMenu.add(editWordBank);
        menuBar.add(wordBankMenu);
        reset = new JButton("Start");
        reset.addActionListener(this);
        reset.setFocusable(false);
        reset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuBar.add(reset);
        tryToGuess = new JButton("Spróbuj zgadnąć całe słowo");
        tryToGuess.setFocusable(false);
        tryToGuess.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tryToGuess.setEnabled(false);
        tryToGuess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer = JOptionPane.showInputDialog(null, "Spróbuj zgadnąć!", "Jakie to słowo?", JOptionPane.INFORMATION_MESSAGE);
                if(!answer.isEmpty()){
                    if(HangmanGame.getWord().equalsIgnoreCase(answer)){
                        tryToGuess.setEnabled(false);
                        ShowWord();
                        HangmanGame.WordGuessed(true);
                    }
                    else{
                        tryToGuess.setEnabled(false);
                        ShowWord();
                        HangmanGame.WordGuessed(false);
                    }
                }
            }
        });
        menuBar.add(tryToGuess);

        messages = new JLabel("Witaj w grze Wisielec!");
        messages.setPreferredSize(new Dimension(WIDTH, 100));
        messages.setHorizontalAlignment(JLabel.CENTER);
        messages.setFont(new Font("Helvetiva Neue", Font.PLAIN, 30));

        hangmanPanel = new MyPanel();
        hangmanPanel.setPreferredSize(new Dimension(150, HEIGHT));
        hangmanPanel.repaint();

        wordLabel = new JLabel("Aby rozpocząć naciśnij przycisk Start");
        wordLabel.setHorizontalAlignment(JLabel.CENTER);
        wordLabel.setFont(new Font("Helvetiva Neue", Font.PLAIN, 30));

        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new FlowLayout());
        keyboardPanel.setPreferredSize(new Dimension(WIDTH, 190));
        keyboardPanel.setToolTipText("Możesz też wpisywać litery z klawiatury");

        keyboard = new JButton[letters.length()];
        for (int i = 0; i < keyboard.length; i++) {
            keyboard[i] = new JButton(""+letters.charAt(i));
            keyboard[i].setPreferredSize(new Dimension(80, 40));
            keyboard[i].addActionListener(this);
            keyboard[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            keyboardPanel.add(keyboard[i]);
        }


        lettersPanel = new JPanel();
        lettersPanel.setLayout(new FlowLayout());
        lettersPanel.setPreferredSize(new Dimension(150, HEIGHT));

        letterLabels = new JLabel[letters.length()];
        for (int i = 0; i < letterLabels.length; i++) {
            letterLabels[i] = new JLabel();
            letterLabels[i].setPreferredSize(new Dimension(30, 35));
            letterLabels[i].setVerticalAlignment(SwingConstants.CENTER);
            letterLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            letterLabels[i].setFont(new Font("Helvetiva Neue", Font.PLAIN, 20));
            lettersPanel.add(letterLabels[i]);
        }

        JFrame frame = new JFrame();
        frame.setJMenuBar(menuBar);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setFocusable(true);
        frame.setMinimumSize(new Dimension(700, 700));
        frame.setTitle("HangmanGame!");
        frame.requestFocusInWindow();
        frame.addKeyListener(this);
        frame.add(messages, BorderLayout.NORTH);
        frame.add(wordLabel, BorderLayout.CENTER);
        frame.add(hangmanPanel, BorderLayout.WEST);
        frame.add(lettersPanel, BorderLayout.EAST);
        frame.add(keyboardPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        DisableKeyboard();

    }

    void EnableKeyboard(){
        for (JButton jButton : keyboard) {
            jButton.setEnabled(true);
        }
    }

    void DisableKeyboard(){
        for (JButton jButton : keyboard) {
            jButton.setEnabled(false);
        }
    }

    void ClearLetterLabels(){
        for (JLabel letterLabel : letterLabels) {
            letterLabel.setText("");
            letterLabel.setForeground(Color.BLACK);
        }
    }

    void ShowWord(){
        String shownWord = "";
        for (int i = 0; i < HangmanGame.word.length(); i++) {
            shownWord = shownWord.concat(""+HangmanGame.word.charAt(i)+" ");
        }
        wordLabel.setText(shownWord);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==editWordBank){
            HangmanGame.EditWordBank();
        }
        if(e.getSource()==reset){
            if(reset.getText().equals("Start")) {
                reset.setText("Spróbuj ponownie");
            }
            tryToGuess.setEnabled(true);
            HangmanGame.StartNewGame();
        }
        for (int i = 0; i < keyboard.length; i++) {
            if(e.getSource()==keyboard[i]){
                keyboard[i].setEnabled(false);
                HangmanGame.StatusUpdate(keyboard[i].getText());
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char ch = (String.valueOf(e.getKeyChar()).toUpperCase()).charAt(0);
        try{
            if(letters.contains(""+ch)) keyboard[letters.indexOf(ch)].doClick();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

class MyPanel extends JPanel{
    public void paint(Graphics g){
        Graphics2D gr = (Graphics2D) g;

        gr.setPaint(Color.BLACK);
        gr.setStroke(new BasicStroke(4));

        if(HangmanGame.guessesWrong>=11) {
            gr.setPaint(Color.RED);
            gr.fillOval(0, 20, 100, 100);
            gr.setPaint(Color.BLACK);
        }
        if(HangmanGame.guessesWrong>=1) gr.drawLine(10, 100, 60, 100);
        if(HangmanGame.guessesWrong>=2) gr.drawLine(35, 100, 35, 30);
        if(HangmanGame.guessesWrong>=3) gr.drawLine(35, 30, 75, 30);
        if(HangmanGame.guessesWrong>=4) gr.drawLine(35, 50, 55, 30);
        if(HangmanGame.guessesWrong>=5) gr.drawLine(75, 30, 75, 45);
        if(HangmanGame.guessesWrong>=6) gr.drawOval(70, 45, 10, 10);
        if(HangmanGame.guessesWrong>=7) gr.drawLine(75, 55, 75, 75);
        if(HangmanGame.guessesWrong>=8) gr.drawLine(75, 55, 65, 70);
        if(HangmanGame.guessesWrong>=9) gr.drawLine(75, 55, 85, 70);
        if(HangmanGame.guessesWrong>=10) gr.drawLine(75, 75, 65, 90);
        if(HangmanGame.guessesWrong>=11) gr.drawLine(75, 75, 85, 90);

    }
}

class WordBank {
    ArrayList words;

    WordBank(){
        words = new ArrayList<String>();
        Load();
    }

    void DeleteForEmpty(){
        words.forEach(w -> {
            if(w.toString().isEmpty() || w.toString().isBlank()){
                words.remove(w);
            }
        });
    }

    void Save(){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(HangmanGame.filePath));
            out.writeObject(words);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    void Load(){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(HangmanGame.filePath));
            words = (ArrayList) in.readObject();
            in.close();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}