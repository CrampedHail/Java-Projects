import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DemoFrame extends JFrame {

    final public static Dimension SCREEN_SIZE = new Dimension(1000, 600);

    static JTabbedPane tabbedPane;
    static FlowLayoutDemoPanel flowLayoutDemoPanel;
    static GridLayoutDemoPanel gridLayoutDemoPanel;
    static BorderLayoutDemoPanel borderLayoutDemoPanel;
    static BoxLayoutDemoPanel boxLayoutDemoPanel;
    static ButtonDemoPanel buttonDemoPanel;
    static ProgressBarDemoPanel progressBarDemoPanel;
    static TextFieldDemoPanel textFieldDemoPanel;
    static CheckBoxDemoPanel checkBoxDemoPanel;
    static RadioButtonDemoPanel radioButtonDemoPanel;
    static ComboBoxDemoPanel comboBoxDemoPanel;
    static SliderDemoPanel sliderDemoPanel;
    static DragAndDropDemoPanel dragAndDropDemoPanel;
    static JavaPaintDemoPanel javaPaintDemoPanel;

    DemoFrame(){
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        flowLayoutDemoPanel = new FlowLayoutDemoPanel();

        gridLayoutDemoPanel = new GridLayoutDemoPanel();

        borderLayoutDemoPanel = new BorderLayoutDemoPanel();

        boxLayoutDemoPanel = new BoxLayoutDemoPanel();

        buttonDemoPanel = new ButtonDemoPanel();

        progressBarDemoPanel = new ProgressBarDemoPanel();

        textFieldDemoPanel = new TextFieldDemoPanel();

        checkBoxDemoPanel = new CheckBoxDemoPanel();

        radioButtonDemoPanel = new RadioButtonDemoPanel();

        comboBoxDemoPanel = new ComboBoxDemoPanel();

        sliderDemoPanel = new SliderDemoPanel();

        dragAndDropDemoPanel = new DragAndDropDemoPanel();

        javaPaintDemoPanel = new JavaPaintDemoPanel();

        tabbedPane.addTab("Flow Layout Demo", flowLayoutDemoPanel);
        tabbedPane.addTab("Grid Layout Demo", gridLayoutDemoPanel);
        tabbedPane.addTab("Border Layout Demo", borderLayoutDemoPanel);
        tabbedPane.addTab("Box Layout Demo", boxLayoutDemoPanel);
        tabbedPane.addTab("Button Demo", buttonDemoPanel);
        tabbedPane.addTab("Progress Bar Demo", progressBarDemoPanel);
        tabbedPane.addTab("Text Field Demo", textFieldDemoPanel);
        tabbedPane.addTab("Check Box Demo", checkBoxDemoPanel);
        tabbedPane.addTab("Radio Button Demo", radioButtonDemoPanel);
        tabbedPane.addTab("Combo Box Demo", comboBoxDemoPanel);
        tabbedPane.addTab("Slider Demo", sliderDemoPanel);
        tabbedPane.addTab("Drag&Drop Demo", dragAndDropDemoPanel);
        tabbedPane.addTab("Paint Demo", javaPaintDemoPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Java Swing Demo!");
        //setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(400, 250));
        setPreferredSize(SCREEN_SIZE);
        add(tabbedPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
}

class FlowLayoutDemoPanel extends JPanel{

    JLabel[] labels;

    FlowLayoutDemoPanel(){
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setToolTipText("Try to resize the window.");

        labels = new JLabel[6];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(" Label no. "+(i+1)+" ");
            labels[i].setFont(new Font("Helvetica Neue", Font.PLAIN, 35));
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.GRAY);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            add(labels[i]);
        }
    }
}

class GridLayoutDemoPanel extends JPanel{

    JLabel[] labels;

    GridLayoutDemoPanel(){
        setLayout(new GridLayout(3, 2, 2, 2));
        setToolTipText("Try to resize the window.");

        labels = new JLabel[6];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(" Label no. "+(i+1)+" ");
            labels[i].setFont(new Font("Helvetica Neue", Font.PLAIN, 33));
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.GRAY);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            add(labels[i]);
        }
    }
}

class BorderLayoutDemoPanel extends JPanel{

    JLabel[] labels;

    BorderLayoutDemoPanel(){
        setLayout(new BorderLayout());
        setToolTipText("Try to resize the window.");

        labels = new JLabel[5];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel();
            labels[i].setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
            labels[i].setPreferredSize(new Dimension(75, 75));
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.GRAY);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
        }

        labels[0].setBackground(Color.WHITE);
        labels[1].setBackground(Color.RED);
        labels[2].setBackground(Color.GREEN);
        labels[3].setBackground(Color.BLUE);
        labels[4].setBackground(Color.YELLOW);

        labels[0].setText("CENTER");
        labels[1].setText("EAST");
        labels[2].setText("WEST");
        labels[3].setText("NORHT");
        labels[4].setText("SOUTH");

        add(labels[0], BorderLayout.CENTER);
        add(labels[1], BorderLayout.EAST);
        add(labels[2], BorderLayout.WEST);
        add(labels[3], BorderLayout.NORTH);
        add(labels[4], BorderLayout.SOUTH);

    }
}

class BoxLayoutDemoPanel extends JPanel{

    JLabel[] labels;

    BoxLayoutDemoPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setToolTipText("Try to resize the window.");

        labels = new JLabel[6];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(" Label no. "+(i+1)+" ");
            labels[i].setFont(new Font("Helvetica Neue", Font.PLAIN, 33));
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.GRAY);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            labels[i].setAlignmentX(CENTER_ALIGNMENT);
            add(labels[i]);
        }
    }
}

class ButtonDemoPanel extends JPanel{

    JLabel label;
    JButton button;
    int counter;

    ButtonDemoPanel(){
        counter = 0;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        label = new JLabel("Counter: "+counter);
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 33));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setBorder(new EmptyBorder(60, 10, 40, 10));
        add(label);

        button = new JButton("Add 1");
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setFont(new Font("Helvetica Neue", Font.PLAIN, 23));
        button.setMargin(new Insets(20, 20, 20, 20));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                label.setText("Counter: "+counter);
            }
        });
        add(button);
    }
}

class ProgressBarDemoPanel extends JPanel{

    JLabel label;
    JPanel panel;
    JButton add;
    JButton subtract;
    JProgressBar progressBar;
    int percentage;

    ProgressBarDemoPanel(){
        percentage = 0;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(progressBar.getValue()==progressBar.getMinimum()){
                    label.setText("Progress Bar is empty.");
                }
                else if(progressBar.getValue()==progressBar.getMaximum()){
                    label.setText("Progress Bar is full.");
                }
                else {
                    percentage = (int) (progressBar.getValue() * 100.0 / progressBar.getMaximum());
                    label.setText(percentage + "%");
                }
            }
        });
        add(progressBar);

        label = new JLabel("Progress Bar is empty.");
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 33));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setBorder(new EmptyBorder(60, 10, 40, 10));
        add(label);

        panel = new JPanel();

        subtract = new JButton("<<");
        subtract.setAlignmentX(CENTER_ALIGNMENT);
        subtract.setFont(new Font("Helvetica Neue", Font.PLAIN, 23));
        subtract.setMargin(new Insets(20, 20, 20, 20));
        subtract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setValue(progressBar.getValue()-10);
            }
        });
        panel.add(subtract);

        add = new JButton(">>");
        add.setAlignmentX(CENTER_ALIGNMENT);
        add.setFont(new Font("Helvetica Neue", Font.PLAIN, 23));
        add.setMargin(new Insets(20, 20, 20, 20));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setValue(progressBar.getValue()+10);
            }
        });
        panel.add(add);

        add(panel);
    }
}

class TextFieldDemoPanel extends JPanel{

    JTextField textField;
    JLabel label;

    TextFieldDemoPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        textField = new JTextField();
        textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 30));
        textField.setMaximumSize(new Dimension(350, 75));
        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateLabel();
            }
            public void removeUpdate(DocumentEvent e) {
                updateLabel();
            }
            public void insertUpdate(DocumentEvent e) {
                updateLabel();
            }

            public void updateLabel() {
                if(textField.getText().isBlank() || textField.getText().isEmpty())
                    label.setText("Text Field is empty.");
                else label.setText("You entered: "+textField.getText());
            }
        });
        add(textField);

        label = new JLabel("Text Field is empty.");
        label.setBorder(new EmptyBorder(70, 10, 0, 10));
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 33));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        add(label);

    }
}

class CheckBoxDemoPanel extends JPanel{

    JCheckBox checkBox;
    JLabel label;

    CheckBoxDemoPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        label = new JLabel("Are you a robot? :O");
        label.setBorder(new EmptyBorder(70, 10, 40, 10));
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 33));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setAlignmentX(CENTER_ALIGNMENT);
        add(label);

        checkBox = new JCheckBox("I'm not a robot.");
        checkBox.setFont(new Font("Helvetica Neue", Font.PLAIN, 28));
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        checkBox.setAlignmentX(CENTER_ALIGNMENT);
        checkBox.setMaximumSize(new Dimension(350, 75));
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBox.isSelected())
                    label.setText("You are not a robot! :D");
                else label.setText("Are you a robot? :O");
            }
        });
        add(checkBox);
    }
}

class RadioButtonDemoPanel extends JPanel{

    ButtonGroup group;
    JRadioButton[] colorButtons;
    JLabel label;
    ActionListener al;
    String[] colorLabels = new String[]{"Red", "Green", "Blue", "Black"};
    Color[] colors = new Color[]{Color.RED, Color.GREEN.darker(), Color.BLUE, Color.BLACK};

    RadioButtonDemoPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        label = new JLabel("Selected Color");
        label.setBorder(new EmptyBorder(70, 10, 40, 10));
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 33));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setAlignmentX(CENTER_ALIGNMENT);
        add(label);

        al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < colorButtons.length; i++) {
                    if(colorButtons[i].isSelected()){
                        label.setForeground(colors[i]);
                    }
                }
            }
        };

        JPanel colorsPanel = new JPanel();

        group = new ButtonGroup();

        colorButtons = new JRadioButton[4];
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new JRadioButton(colorLabels[i]);
            colorButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            colorButtons[i].setVerticalTextPosition(SwingConstants.BOTTOM);
            colorButtons[i].addActionListener(al);
            colorsPanel.add(colorButtons[i]);
            group.add(colorButtons[i]);
        }
        colorButtons[colorButtons.length-1].setSelected(true);
        add(colorsPanel);
    }
}

class ComboBoxDemoPanel extends JPanel{

    JComboBox<String> comboBox;
    JLabel label;
    JPanel colorPanel;
    String[] colorsLabels = new String[]{"Black" ,"Red" ,"Orange" ,"Yellow" ,"Green" ,"Blue" ,"Purple" ,"Pink" ,"White"};
    Color[] colors = new Color[]{
            Color.BLACK, Color.RED,
            new Color(255, 140, 0), Color.YELLOW,
            Color.GREEN, Color.BLUE, new Color(140, 0, 255),
            new Color(235, 147, 255), Color.WHITE};

    ActionListener al;

    ComboBoxDemoPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        label = new JLabel("Choose your favorite colour:");
        label.setBorder(new EmptyBorder(70, 10, 40, 10));
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 33));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setAlignmentX(CENTER_ALIGNMENT);
        add(label);

        al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorPanel.setBackground(colors[comboBox.getSelectedIndex()]);
            }
        };

        comboBox = new JComboBox<>(colorsLabels);
        comboBox.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        comboBox.setAlignmentX(CENTER_ALIGNMENT);
        comboBox.setMaximumSize(new Dimension(350, 75));
        comboBox.addActionListener(al);
        add(comboBox);

        colorPanel = new JPanel();
        colorPanel.setOpaque(true);
        colorPanel.setBackground(Color.BLACK);
        colorPanel.setMaximumSize(new Dimension(350, 125));
        add(colorPanel);
    }
}

class SliderDemoPanel extends JPanel{

    JTabbedPane tabbedPane;
    RGBSliderDemoPanel rgbSliderDemoPanel;
    PointSliderDemoPanel pointSliderDemoPanel;

    SliderDemoPanel(){
        setLayout(new GridLayout(1, 1));
        tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);

        rgbSliderDemoPanel = new RGBSliderDemoPanel();
        tabbedPane.addTab("RGB Sliders", rgbSliderDemoPanel);

        pointSliderDemoPanel = new PointSliderDemoPanel();
        tabbedPane.addTab("Pointer Sliders", pointSliderDemoPanel);

        add(tabbedPane);
    }

}

class RGBSliderDemoPanel extends JPanel{

    JPanel slidersPanel;
    JSlider redSlider;
    JSlider greenSlider;
    JSlider blueSlider;
    JPanel colorPanel;
    Color background;
    ChangeListener cl;
    
    RGBSliderDemoPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        cl = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                background = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
                colorPanel.setBackground(background);
            }
        };

        slidersPanel = new JPanel();
        slidersPanel.setBorder(new EmptyBorder(50, 0, 30, 0));

        redSlider = new JSlider(0, 255);
        redSlider.setOrientation(SwingConstants.VERTICAL);
        redSlider.setValue(redSlider.getMaximum());
        redSlider.addChangeListener(cl);
        redSlider.setName("Red");
        slidersPanel.add(redSlider);

        greenSlider = new JSlider(0, 255);
        greenSlider.setOrientation(SwingConstants.VERTICAL);
        greenSlider.setValue(greenSlider.getMaximum());
        greenSlider.addChangeListener(cl);
        greenSlider.setName("Green");
        slidersPanel.add(greenSlider);

        blueSlider = new JSlider(0, 255);
        blueSlider.setOrientation(SwingConstants.VERTICAL);
        blueSlider.setValue(blueSlider.getMaximum());
        blueSlider.addChangeListener(cl);
        blueSlider.setName("Blue");
        slidersPanel.add(blueSlider);

        add(slidersPanel);

        colorPanel = new JPanel();
        colorPanel.setOpaque(true);
        colorPanel.setBackground(Color.WHITE);
        colorPanel.setPreferredSize(new Dimension(350, 125));

        add(colorPanel);
    }

}

class PointSliderDemoPanel extends JPanel{

    static JSlider xSlider;
    static JSlider ySLider;
    static CoordinateSystem coordinateSystem;
    ChangeListener cl;

    PointSliderDemoPanel(){
        cl = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                coordinateSystem.updatePoint(xSlider.getValue(), -ySLider.getValue());
            }
        };

        setLayout(new BorderLayout());

        xSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        xSlider.addChangeListener(cl);

        ySLider = new JSlider(JSlider.VERTICAL, -100, 100, 0);
        ySLider.addChangeListener(cl);

        coordinateSystem = new CoordinateSystem();
        add(coordinateSystem, BorderLayout.CENTER);
        add(xSlider, BorderLayout.SOUTH);
        add(ySLider, BorderLayout.EAST);
    }

}

class CoordinateSystem extends JPanel{

    Point2D.Double point;
    int x = getWidth()/2;
    int y = getHeight()/2;
    int xAxisPoint = (getWidth()-20)/20;
    int yAxisPoint = (getHeight()-20)/20;
    //Dimension Size;

    CoordinateSystem(){
        point = new Point2D.Double(0, 0);
        x = getWidth()/2;
        y = getHeight()/2;
        xAxisPoint = (getWidth()-20)/20;
        yAxisPoint = (getHeight()-20)/20;
        repaint();
    }

    @Override
    public void paint(Graphics g){
        Graphics2D gr = (Graphics2D) g;

        gr.setPaint(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());

        gr.setPaint(Color.BLACK);
        gr.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
        gr.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());

        xAxisPoint = (getWidth()-20)/20;
        yAxisPoint = (getHeight()-20)/20;

        for (int i = 0; i < 21; i++) {
            gr.drawLine(getWidth()/2-4, getHeight()/2-(10*yAxisPoint)+(i*yAxisPoint), getWidth()/2+4, getHeight()/2-(10*yAxisPoint)+(i*yAxisPoint));
            gr.drawLine(getWidth()/2-(10*xAxisPoint)+(i*xAxisPoint), getHeight()/2-4, getWidth()/2-(10*xAxisPoint)+(i*xAxisPoint), getHeight()/2+4);
        }

        x = getWidth()/2 + (int)(point.getX()*xAxisPoint/10);
        y = getHeight()/2 + (int)(point.getY()*yAxisPoint/10);

        gr.setPaint(Color.BLUE);
        gr.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0));
        gr.drawLine(x, getHeight()/2, x, y);
        gr.drawLine(getWidth()/2, y, x, y);
        gr.setPaint(Color.BLACK);

        gr.fillOval(x-5, y-5, 10, 10);
    }

    public void updatePoint(double x, double y){
        point.setLocation(x, y);
        repaint();
    }
}

class DragAndDropDemoPanel extends JPanel{

    Rectangle label;
    Point labelCorner;
    Point previousPoint;

    DragAndDropDemoPanel(){

        label = new Rectangle(200, 100);

        labelCorner = new Point(0, 0);

        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();

        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillRect(labelCorner.x, labelCorner.y, label.width, label.height);
    }

    private class ClickListener extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            if (label.intersects(new Rectangle(e.getPoint().x, e.getPoint().y, 1, 1))) {
                previousPoint = e.getPoint();
            }
        }
    }

    private class DragListener extends MouseMotionAdapter{

        public void mouseDragged(MouseEvent e){
            if (label.intersects(new Rectangle(e.getPoint().x, e.getPoint().y, 1, 1))) {
                Point currentPoint = e.getPoint();
                labelCorner.translate(
                        currentPoint.x - previousPoint.x,
                        currentPoint.y - previousPoint.y
                );
                label.setLocation(labelCorner.x, labelCorner.y);
                previousPoint = currentPoint;
                repaint();
            }
        }
    }
}

class JavaPaintDemoPanel extends JPanel implements ActionListener{

    private final ToolPanel toolPanel;
    private final PaintPanel panel;

    JavaPaintDemoPanel(){
        panel = new PaintPanel();

        toolPanel = new ToolPanel(this);
        toolPanel.getBrushSizeSlider().setValue(panel.getBrushSize());
        toolPanel.getBrushSizeSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                panel.setBrushSize(toolPanel.getBrushSizeSlider().getValue());
                panel.repaint();
            }
        });
        for (int i = 0; i < toolPanel.getColors().length; i++) {
            if(i == toolPanel.getColors().length-1){
                toolPanel.getColors()[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        panel.setSelectedColor(JColorChooser.showDialog(null, "Wybierz kolor pędzla.", Color.BLACK));
                    }
                });
            }
            else toolPanel.getColors()[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    panel.setSelectedColor(e.getComponent().getBackground());
                }
            });
        }
        toolPanel.getBrushTool().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setSelectedTool("brush");
            }
        });
        toolPanel.getRectangleTool().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setSelectedTool("rect");
            }
        });
        toolPanel.getOvalTool().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setSelectedTool("oval");
            }
        });

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(toolPanel, BorderLayout.EAST);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(toolPanel.getReset())){
            panel.EraseAll();
        }
    }
}

class ToolPanel extends JPanel{

    private final JButton reset;
    private final JToggleButton brushTool;
    private final JToggleButton rectangleTool;
    private final JToggleButton ovalTool;
    private final JSlider brushSizeSlider;
    private final JLabel[] colors;

    ToolPanel(ActionListener listener){
        setPreferredSize(new Dimension(150, 0));

        reset = new JButton("Wymaż wszystko");
        reset.addActionListener(listener);
        add(reset);

        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.Y_AXIS));
        add(toolsPanel);

        JLabel toolsLabel = new JLabel("Narzędzie:");
        toolsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        toolsLabel.setAlignmentX(CENTER_ALIGNMENT);
        toolsPanel.add(toolsLabel);

        JPanel tools = new JPanel();
        tools.setAlignmentX(CENTER_ALIGNMENT);
        tools.setLayout(new GridLayout(1, 3));
        tools.setPreferredSize(new Dimension(140, 30));
        toolsPanel.add(tools);

        ButtonGroup group = new ButtonGroup();

        brushTool = new JToggleButton();
        ImageIcon brushIcon = new ImageIcon("brushIcon.png");
        brushTool.setIcon(brushIcon);
        brushTool.setSelected(true);
        group.add(brushTool);
        tools.add(brushTool);

        rectangleTool = new JToggleButton();
        ImageIcon rectangleIcon = new ImageIcon("rectangleIcon.png");
        rectangleTool.setIcon(rectangleIcon);
        group.add(rectangleTool);
        tools.add(rectangleTool);

        ovalTool = new JToggleButton();
        ImageIcon ovalIcon = new ImageIcon("ovalIcon.png");
        ovalTool.setIcon(ovalIcon);
        group.add(ovalTool);
        tools.add(ovalTool);

        JPanel brushSizePanel = new JPanel();
        brushSizePanel.setLayout(new BoxLayout(brushSizePanel, BoxLayout.Y_AXIS));

        JLabel brushSizeLabel = new JLabel("Rozmiar pędzla:");
        brushSizeLabel.setAlignmentX(CENTER_ALIGNMENT);
        brushSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        brushSizePanel.add(brushSizeLabel);

        brushSizeSlider = new JSlider(2, 100);
        brushSizeSlider.setAlignmentX(CENTER_ALIGNMENT);
        brushSizeSlider.setBorder(new EmptyBorder(0, 15, 0, 15));
        brushSizePanel.add(brushSizeSlider);

        add(brushSizePanel);

        JPanel colorsPanel = new JPanel();
        colorsPanel.setLayout(new GridLayout(2, 5, 2, 2));
        colorsPanel.setOpaque(true);
        colorsPanel.setBackground(Color.lightGray);
        colorsPanel.setPreferredSize(new Dimension(140, 60));

        colors = new JLabel[10];
        Color[] c = new Color[]{Color.WHITE, Color.BLACK, Color.RED, new Color(255, 122, 0), Color.YELLOW, Color.GREEN, Color.BLUE, new Color(155, 0, 255), new Color(255, 100, 255), Color.GRAY};
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new JLabel("");
            if(i == colors.length-1)
                colors[i] = new JLabel("...");
            colors[i].setOpaque(true);
            colors[i].setBackground(c[i]);
            colors[i].setHorizontalAlignment(SwingConstants.CENTER);
            colorsPanel.add(colors[i]);
        }
        add(colorsPanel);
    }

    public JButton getReset() {
        return reset;
    }

    public JToggleButton getBrushTool() {
        return brushTool;
    }

    public JToggleButton getRectangleTool() {
        return rectangleTool;
    }

    public JToggleButton getOvalTool() {
        return ovalTool;
    }

    public JSlider getBrushSizeSlider() {
        return brushSizeSlider;
    }

    public JLabel[] getColors() {
        return colors;
    }
}

class PaintPanel extends JPanel{

    private BufferedImage image;
    private final ArrayList<Point> points;
    private Point lastPoint;
    private int brushSize = 10;
    private Graphics2D gr;
    private Color selectedColor;
    private String selectedTool;
    private Point shapePoint1;
    private Point shapePoint2;

    PaintPanel(){
        selectedTool = "brush";

        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();

        addMouseListener(clickListener);
        addMouseMotionListener(dragListener);
        setPreferredSize(new Dimension(800, 600));
        setOpaque(true);
        setBackground(Color.WHITE);

        points = new ArrayList<>();

        selectedColor = Color.BLACK;

        image = new BufferedImage(getPreferredSize().width, getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
        gr = image.createGraphics();
        gr.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
    }

    public void paint(Graphics g){

        gr.setColor(selectedColor);


        g.drawImage(image, 0, 0, null);
    }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e){
            if(selectedTool.equals("brush")){
                lastPoint = e.getPoint();
                points.add(lastPoint);
                gr.fillOval(lastPoint.x - (brushSize / 2) - (brushSize % 2), lastPoint.y - (brushSize / 2) - (brushSize % 2), brushSize, brushSize);
                lastPoint = null;
                repaint();
            }
            if(selectedTool.equals("rect") || selectedTool.equals("oval")){
                shapePoint1=e.getPoint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(selectedTool.equals("rect") || selectedTool.equals("oval")){
                Rectangle r = new Rectangle(shapePoint1.x, shapePoint1.y, Math.abs(shapePoint1.x-shapePoint2.x), Math.abs(shapePoint1.y-shapePoint2.y));
                if(shapePoint1.x>shapePoint2.x) r.x = shapePoint2.x;
                if(shapePoint1.y>shapePoint2.y) r.y = shapePoint2.y;
                if(selectedTool.equals("rect")) gr.fillRect(r.x, r.y, r.width, r.height);
                if(selectedTool.equals("oval")) gr.fillOval(r.x, r.y, r.width, r.height);
                repaint();
            }
        }
    }

    private class DragListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent e){
            if(selectedTool.equals("brush")){
                lastPoint = e.getPoint();
                points.add(lastPoint);
                gr.drawLine(points.get(points.size()-1).x, points.get(points.size()-1).y, points.get(points.size()-2).x, points.get(points.size()-2).y);
                repaint();
            }
            if(selectedTool.equals("rect") || selectedTool.equals("oval")){
                shapePoint2=e.getPoint();
                repaint();
            }
        }
    }

    public void setSelectedTool(String selectedTool) {
        this.selectedTool = selectedTool;
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        gr.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void EraseAll(){
        //points.clear();
        image = new BufferedImage(getPreferredSize().width, getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
        gr = image.createGraphics();
        gr.setColor(Color.WHITE);
        gr.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        gr.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
        lastPoint=null;
        repaint();
    }
}