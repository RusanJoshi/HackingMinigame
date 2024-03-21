package misc;

import cipher.Cipher;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import auxiliary.CustomLine;
import auxiliary.Dialogue;
import auxiliary.DialogueBox;

/*
Notes:
    * tabbedPane panels (width=281, height=?)
 */
public class OptionsPanel extends JPanel implements MouseListener {
    Cipher cipherConnection;
    CustomLine divider = new CustomLine();
    JLabel optionsButton = new JLabel();
    boolean optionsButtonBool = false;
    ImageIcon optionsButtonIcon = new ImageIcon(getClass().getClassLoader().getResource("gearicon.png"));

    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    //[0] PANEL 1 (SETTINGS)
    //[1] Detection Meter Toggle
    JToggleButton detectionMeterToggleButton = new JToggleButton();
    //[1] Difficulty Setting Components
    JLabel difficultySettingDock = new JLabel();
    //[1] Disable Detection Meter Setting Components
    JLabel toggleDetectionMeterSettingDock = new JLabel();
    //[1] cipher.Cipher Color Scheme Setting Components
    JLabel cipherColorSchemeSettingDock = new JLabel();
    JLabel defaultSchemeLabel = new JLabel();
    JLabel starfieldSchemeLabel = new JLabel();
    JLabel gameboySchemeLabel = new JLabel();
    JLabel virtualboySchemeLabel = new JLabel();

    //[0] PANEL 2 (QUIT)
    JButton restartButton = new JButton();
    JButton quitButton = new JButton();
    JButton creditsButton = new JButton();

    DialogueBox dialogueBox;
    Dialogue dialogue = new Dialogue();

    Border optionsPanelBorder = BorderFactory.createTitledBorder(null,"options_submenu_v4202.03.01",3, TitledBorder.BOTTOM,null,Color.ORANGE);
    Border defaultSchemeLabelBorder = BorderFactory.createLineBorder(Color.WHITE,2);
    Border highlightSchemeLabelBorder = BorderFactory.createLineBorder(Color.ORANGE,4);

    public void setCipherConnection(Cipher cipherConnection) {
        this.cipherConnection = cipherConnection;
    }

    public boolean isOptionsButtonBool() {
        return optionsButtonBool;
    }
    public void setOptionsButtonBool(boolean optionsButtonBool) {
        this.optionsButtonBool = optionsButtonBool;
    }

    public JLabel getOptionsButton() {
        return optionsButton;
    }
    public JButton getRestartButton() {
        return restartButton;
    }
    public JButton getQuitButton() {
        return quitButton;
    }
    public JButton getCreditsButton() {
        return creditsButton;
    }

    public JToggleButton getDetectionMeterToggleButton() {
        return detectionMeterToggleButton;
    }

    public OptionsPanel(){
        this.setLayout(null);
        this.setSize(338,360);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
        this.setBorder(optionsPanelBorder);
        this.setVisible(false);

        secondarySetupOptionsPanel();
    }

    private void secondarySetupOptionsPanel(){
        optionsButton.setSize(30,30);
        optionsButton.setOpaque(true);
        optionsButton.setBackground(Color.PINK);
        optionsButton.setIcon(imageResize(optionsButtonIcon, optionsButton.getWidth(), optionsButton.getHeight()));
        this.add(optionsButton);

        //[0] PANEL 1 (SETTINGS)
        panel1.setLayout(null);
        panel1.setBackground(Color.BLACK);
        tabbedPane.addTab("Settings", panel1);

        //[1] Difficulty Setting Components
        difficultySettingDock.setSize(281, 54);
        difficultySettingDock.setOpaque(true);
        difficultySettingDock.setForeground(Color.WHITE);
        difficultySettingDock.setBackground(new Color(15,15,15));
        difficultySettingDock.setVerticalAlignment(SwingConstants.TOP);
        difficultySettingDock.setHorizontalAlignment(SwingConstants.LEFT);
        difficultySettingDock.setText("Difficulty (N/A)");
        difficultySettingDock.addMouseListener(this);
        panel1.add(difficultySettingDock);

        //[1] Disable Detection Meter Setting Components
        toggleDetectionMeterSettingDock.setSize(281, 54);
        toggleDetectionMeterSettingDock.setOpaque(true);
        toggleDetectionMeterSettingDock.setForeground(Color.WHITE);
        toggleDetectionMeterSettingDock.setBackground(new Color(30,30,30));
        toggleDetectionMeterSettingDock.setVerticalAlignment(SwingConstants.TOP);
        toggleDetectionMeterSettingDock.setHorizontalAlignment(SwingConstants.LEFT);
        toggleDetectionMeterSettingDock.setText("Toggle Detection Meter");
        toggleDetectionMeterSettingDock.addMouseListener(this);
        panel1.add(toggleDetectionMeterSettingDock);

        detectionMeterToggleButton.setSize(80,30);
        detectionMeterToggleButton.setBackground(Color.ORANGE);
        detectionMeterToggleButton.setVerticalAlignment(SwingConstants.TOP);
        detectionMeterToggleButton.setHorizontalAlignment(SwingConstants.LEFT);
        detectionMeterToggleButton.setText("tdm_on");
        detectionMeterToggleButton.setFocusable(false);
        toggleDetectionMeterSettingDock.add(detectionMeterToggleButton);

        //[1] Cipher Color Scheme Setting Components
        cipherColorSchemeSettingDock.setSize(281, 54);
        cipherColorSchemeSettingDock.setOpaque(true);
        cipherColorSchemeSettingDock.setForeground(Color.WHITE);
        cipherColorSchemeSettingDock.setBackground(new Color(15,15,15));
        cipherColorSchemeSettingDock.setVerticalAlignment(SwingConstants.TOP);
        cipherColorSchemeSettingDock.setHorizontalAlignment(SwingConstants.LEFT);
        cipherColorSchemeSettingDock.setText("Cipher Color Scheme");
        cipherColorSchemeSettingDock.addMouseListener(this);
        panel1.add(cipherColorSchemeSettingDock);

        defaultSchemeLabel.setSize(20,30);
        defaultSchemeLabel.setOpaque(true);
        defaultSchemeLabel.setBackground(Color.BLACK);
        defaultSchemeLabel.addMouseListener(this);
        defaultSchemeLabel.setBorder(defaultSchemeLabelBorder);
        cipherColorSchemeSettingDock.add(defaultSchemeLabel);

        starfieldSchemeLabel.setSize(20,30);
        starfieldSchemeLabel.setOpaque(true);
        starfieldSchemeLabel.setBackground(Color.BLACK);
        starfieldSchemeLabel.addMouseListener(this);
        starfieldSchemeLabel.setBorder(defaultSchemeLabelBorder);
        cipherColorSchemeSettingDock.add(starfieldSchemeLabel);

        gameboySchemeLabel.setSize(20,30);
        gameboySchemeLabel.setOpaque(true);
        gameboySchemeLabel.setBackground(Color.BLACK);
        gameboySchemeLabel.setBorder(defaultSchemeLabelBorder);
        gameboySchemeLabel.addMouseListener(this);
        cipherColorSchemeSettingDock.add(gameboySchemeLabel);

        virtualboySchemeLabel.setSize(20,30);
        virtualboySchemeLabel.setOpaque(true);
        virtualboySchemeLabel.setBackground(Color.BLACK);
        virtualboySchemeLabel.setBorder(defaultSchemeLabelBorder);
        virtualboySchemeLabel.addMouseListener(this);
        cipherColorSchemeSettingDock.add(virtualboySchemeLabel);

        //PANEL 2 (QUIT)
        panel2.setLayout(null);
        panel2.setBackground(Color.BLACK);
        tabbedPane.addTab("Quit", panel2);

        restartButton.setSize(100,35);
        restartButton.setForeground(Color.BLACK);
        restartButton.setBackground(Color.ORANGE);
        restartButton.setText("Restart");
        restartButton.setFocusable(false);
        restartButton.addMouseListener(this);
        panel2.add(restartButton);

        quitButton.setSize(100,35);
        quitButton.setForeground(Color.BLACK);
        quitButton.setBackground(Color.ORANGE);
        quitButton.setText("Quit");
        quitButton.setFocusable(false);
        quitButton.addMouseListener(this);
        panel2.add(quitButton);

        creditsButton.setSize(100,20);
        creditsButton.setForeground(Color.BLACK);
        creditsButton.setBackground(Color.ORANGE);
        creditsButton.setText("Credits");
        creditsButton.setFocusable(false);
        creditsButton.addMouseListener(this);
        panel2.add(creditsButton);

        tabbedPane.setSize(this.getWidth()-52, this.getHeight()-170);
        tabbedPane.setForeground(Color.BLACK);
        tabbedPane.setBackground(Color.ORANGE);
        this.add(tabbedPane);

        dialogueBox = new DialogueBox(tabbedPane.getWidth()-4,155);
        this.add(dialogueBox);
    }

    @Override
    public void doLayout() {
        super.doLayout();

        optionsButton.setLocation(10,10);

        tabbedPane.setLocation(52,0);
        //[0] PANEL 1 (SETTINGS)
        //[1] Difficulty Setting Components
        difficultySettingDock.setLocation(0,0);

        //[1] Disable Detection Meter Setting Components
        toggleDetectionMeterSettingDock.setLocation(0,(difficultySettingDock.getY()+ difficultySettingDock.getHeight()));
        detectionMeterToggleButton.setLocation((toggleDetectionMeterSettingDock.getWidth()/2)+15,(toggleDetectionMeterSettingDock.getHeight()/2)-(detectionMeterToggleButton.getHeight()/2));

        //[1] cipher.Cipher Color Scheme Setting Components
        cipherColorSchemeSettingDock.setLocation(0,(toggleDetectionMeterSettingDock.getY()+ toggleDetectionMeterSettingDock.getHeight()));
        defaultSchemeLabel.setLocation((cipherColorSchemeSettingDock.getWidth()/2)+15,(cipherColorSchemeSettingDock.getHeight()/2)-(defaultSchemeLabel.getHeight()/2));
        starfieldSchemeLabel.setLocation(defaultSchemeLabel.getX()+30,defaultSchemeLabel.getY());
        gameboySchemeLabel.setLocation(starfieldSchemeLabel.getX()+30, defaultSchemeLabel.getY());
        virtualboySchemeLabel.setLocation(gameboySchemeLabel.getX()+30, defaultSchemeLabel.getY());

        //PANEL 2 (QUIT)
        restartButton.setLocation((panel2.getWidth()/2)-(restartButton.getWidth()/2),20);
        quitButton.setLocation((panel2.getWidth()/2)-(restartButton.getWidth()/2),(restartButton.getY()+ restartButton.getHeight())+5);
        creditsButton.setLocation((panel2.getWidth()/2)-(creditsButton.getWidth()/2), (quitButton.getY()+ quitButton.getHeight())+5);

        dialogueBox.setLocation(tabbedPane.getX(),190);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D;

        RenderingHints renderingHints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHints(renderingHints);

        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setPaint(Color.GRAY);

        divider.draw(g,
                (optionsButton.getX()+optionsButton.getWidth())+11,
                10,
                (optionsButton.getX()+optionsButton.getWidth())+11,
                this.getHeight()-10
                );

    }

    // resizes image passed into the parameter with specified dimensions
    public ImageIcon imageResize(ImageIcon pIcon, int pWidth, int pHeight){
        Image image = pIcon.getImage();
        Image newImage = image.getScaledInstance(pWidth,pHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }

    // plays specified sound file passed into the parameter
    private void playSound(String pFilepath){
        try{
            File soundPath = new File(pFilepath);

            if(soundPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else{
                System.out.println("Cannot find file.");
            }
        }catch(Exception eFP){
            System.out.println(eFP);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent() == defaultSchemeLabel) {
            cipherConnection.getCIPHER_PARTITION().changeCurrentColorScheme(0);
            cipherConnection.getCIPHER_PARTITION().setColors();
            cipherConnection.getCIPHER_PARTITION().resumeColorProgress(cipherConnection.getCipherProgress());
        }
        if (e.getComponent() == starfieldSchemeLabel) {
            cipherConnection.getCIPHER_PARTITION().changeCurrentColorScheme(1);
            cipherConnection.getCIPHER_PARTITION().setColors();
            cipherConnection.getCIPHER_PARTITION().resumeColorProgress(cipherConnection.getCipherProgress());
        }
        if (e.getComponent() == gameboySchemeLabel) {
            cipherConnection.getCIPHER_PARTITION().changeCurrentColorScheme(2);
            cipherConnection.getCIPHER_PARTITION().setColors();
            cipherConnection.getCIPHER_PARTITION().resumeColorProgress(cipherConnection.getCipherProgress());
        }
        if (e.getComponent() == virtualboySchemeLabel) {
            cipherConnection.getCIPHER_PARTITION().changeCurrentColorScheme(3);
            cipherConnection.getCIPHER_PARTITION().setColors();
            cipherConnection.getCIPHER_PARTITION().resumeColorProgress(cipherConnection.getCipherProgress());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        //[0] PANEL 1 (SETTINGS)
        //[1] Difficulty Setting Components
        if(e.getComponent() == difficultySettingDock){
            difficultySettingDock.setForeground(Color.ORANGE);
            if(!(dialogueBox.getSettledText() == 0)) dialogueBox.preTalkSetup(dialogue.getSETTINGS_EXPLANATIONS(0));
            playSound("resources/options_menu_settings.wav");
        }
        //[1] Disable Detection Meter Setting Components
        if(e.getComponent() == toggleDetectionMeterSettingDock){
            toggleDetectionMeterSettingDock.setForeground(Color.ORANGE);
            if(!(dialogueBox.getSettledText() == 1)) dialogueBox.preTalkSetup(dialogue.getSETTINGS_EXPLANATIONS(1));
            playSound("resources/options_menu_settings.wav");
        }
        //[1] cipher.Cipher Color Scheme Components
        if(e.getComponent() == cipherColorSchemeSettingDock){
            cipherColorSchemeSettingDock.setForeground(Color.ORANGE);
            if(!(dialogueBox.getSettledText() == 2)) dialogueBox.preTalkSetup(dialogue.getSETTINGS_EXPLANATIONS(2));
            playSound("resources/options_menu_settings.wav");
        }
        if(e.getComponent() == defaultSchemeLabel){
            defaultSchemeLabel.setBorder(highlightSchemeLabelBorder);
            cipherConnection.getCIPHER_PARTITION().changePreviewColorScheme(0);
            cipherConnection.getCIPHER_PARTITION().previewColors();
        }
        if(e.getComponent() == starfieldSchemeLabel){
            starfieldSchemeLabel.setBorder(highlightSchemeLabelBorder);
            cipherConnection.getCIPHER_PARTITION().changePreviewColorScheme(1);
            cipherConnection.getCIPHER_PARTITION().previewColors();
        }
        if(e.getComponent() == gameboySchemeLabel){
            gameboySchemeLabel.setBorder(highlightSchemeLabelBorder);
            cipherConnection.getCIPHER_PARTITION().changePreviewColorScheme(2);
            cipherConnection.getCIPHER_PARTITION().previewColors();
        }
        if(e.getComponent() == virtualboySchemeLabel){
            virtualboySchemeLabel.setBorder(highlightSchemeLabelBorder);
            cipherConnection.getCIPHER_PARTITION().changePreviewColorScheme(3);
            cipherConnection.getCIPHER_PARTITION().previewColors();
        }
        //PANEL 2 (QUIT)
        if(e.getComponent() == restartButton){
            if(!(dialogueBox.getSettledText() == 3)) dialogueBox.preTalkSetup(dialogue.getSETTINGS_EXPLANATIONS(3));
            playSound("resources/options_menu_settings.wav");
        }
        if(e.getComponent() == quitButton){
            if(!(dialogueBox.getSettledText() == 4)) dialogueBox.preTalkSetup(dialogue.getSETTINGS_EXPLANATIONS(4));
            playSound("resources/options_menu_settings.wav");
        }
        if(e.getComponent() == creditsButton){
            if(!(dialogueBox.getSettledText() == 5)) dialogueBox.preTalkSetup(dialogue.getSETTINGS_EXPLANATIONS(5));
            playSound("resources/options_menu_settings.wav");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //[0] PANEL 1 (SETTINGS)
        //[1] Difficulty Setting Components
        if(e.getComponent() == difficultySettingDock){
            difficultySettingDock.setForeground(Color.WHITE);
            dialogueBox.setSettledText(0);
        }
        //[1] Disable Detection Meter Setting Components
        if(e.getComponent() == toggleDetectionMeterSettingDock){
            toggleDetectionMeterSettingDock.setForeground(Color.WHITE);
            dialogueBox.setSettledText(1);
        }
        //[1] cipher.Cipher Color Scheme Components
        if(e.getComponent() == cipherColorSchemeSettingDock){
            cipherColorSchemeSettingDock.setForeground(Color.WHITE);
            dialogueBox.setSettledText(2);
        }
        if(e.getComponent() == defaultSchemeLabel){
            defaultSchemeLabel.setBorder(defaultSchemeLabelBorder);
            cipherConnection.getCIPHER_PARTITION().setColors();
            cipherConnection.getCIPHER_PARTITION().resumeColorProgress(cipherConnection.getCipherProgress());
        }
        if(e.getComponent() == starfieldSchemeLabel){
            starfieldSchemeLabel.setBorder(defaultSchemeLabelBorder);
            cipherConnection.getCIPHER_PARTITION().setColors();
            cipherConnection.getCIPHER_PARTITION().resumeColorProgress(cipherConnection.getCipherProgress());
        }
        if(e.getComponent() == gameboySchemeLabel){
            gameboySchemeLabel.setBorder(defaultSchemeLabelBorder);
            cipherConnection.getCIPHER_PARTITION().setColors();
            cipherConnection.getCIPHER_PARTITION().resumeColorProgress(cipherConnection.getCipherProgress());
        }
        if(e.getComponent() == virtualboySchemeLabel){
            virtualboySchemeLabel.setBorder(defaultSchemeLabelBorder);
            cipherConnection.getCIPHER_PARTITION().setColors();
            cipherConnection.getCIPHER_PARTITION().resumeColorProgress(cipherConnection.getCipherProgress());
        }
        //PANEL 2 (QUIT)
        if(e.getComponent() == restartButton) dialogueBox.setSettledText(3);
        if(e.getComponent() == quitButton) dialogueBox.setSettledText(4);
        if(e.getComponent() == creditsButton) dialogueBox.setSettledText(5);
    }
}
