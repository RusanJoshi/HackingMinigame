package security;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import matrix.Matrix;


public class SecurityContainer extends JPanel {
    Timer CHECK_CLICKED_BUSSTOP;
    Random rand = new Random();
    Matrix matrixConnection;
    SecurityTerminal securityTerminal;
    BusChannelTop busChannelTop = new BusChannelTop();
    BusChannelBottom busChannelBottom = new BusChannelBottom();
    private JLabel busChannelTopHighlight = new JLabel();
    private JLabel busChannelBottomHighlight = new JLabel();
    BusStop highlightedBusStop;
    int topChannelLocationX = 2;
    int bottomChannelLocationX = 4;
    int universalChannelLeftBound = 0;
    int bottomChannelRightBound = 9;
    int topChannelRightBound = 4;
    int channelFlip = -1;

    Action secconUpKey = new secconUpAction();
    Action secconDownKey = new secconDownAction();
    Action secconLeftKey = new secconLeftAction();
    Action secconRightKey = new secconRightAction();

    Action matrixUpKey = new matrixUpAction();
    Action matrixDownKey = new matrixDownAction();
    Action matrixLeftKey = new matrixLeftAction();
    Action matrixRightKey = new matrixRightAction();
    Action spaceKey = new confirmAction();

    String[] keyPressFilepathArray = {
            "resources/keypress_1.wav", "resources/keypress_2.wav", "resources/keypress_3.wav",
            "resources/keypress_4.wav", "resources/keypress_5.wav", "resources/keypress_6.wav",
            "resources/keypress_7.wav", "resources/keypress_8.wav", "resources/keypress_9.wav", };
    String[] electronicClickFilepathArray = {"resources/electronic_click_1.wav", "resources/electronic_click_2.wav"};

    public void setMatrixConnection(Matrix matrixConnection) {
        this.matrixConnection = matrixConnection;
    }
    public SecurityTerminal getSecurityTerminal() {
        return securityTerminal;
    }
    public JLabel getBusChannelTopHighlight() {
        return busChannelTopHighlight;
    }
    public JLabel getBusChannelBottomHighlight() {
        return busChannelBottomHighlight;
    }
    public void setChannelFlip(int channelFlip) {
        this.channelFlip = channelFlip;
    }

    public SecurityContainer(int pWidth, int pHeight) {
        this.setLayout(null);
        this.setSize(pWidth, pHeight);
        this.setBackground(Color.GRAY.darker().darker());
        this.setOpaque(false);
        this.securityTerminal = new SecurityTerminal(pWidth, 60,10);
        this.setFocusable(true);
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("SECCON has focus.");
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });
        this.CHECK_CLICKED_BUSSTOP = new Timer(10, this::actionPerformedCheckClickedBusStop);

        secondarySetupSecurityContainer();
    }

    private void secondarySetupSecurityContainer(){
        moveHighlight();

        this.add(busChannelTop);

        busChannelBottom.setSecurityTerminalConnection(securityTerminal);
        this.add(busChannelBottom);

        busChannelTopHighlight.setSize(this.getWidth(), 20);
        busChannelTopHighlight.setOpaque(true);
        busChannelTopHighlight.setBackground(new Color(255,200,0,51));
        busChannelTopHighlight.setVisible(false);
        this.add(busChannelTopHighlight);

        busChannelBottomHighlight.setSize(this.getWidth(), 20);
        busChannelBottomHighlight.setOpaque(true);
        busChannelBottomHighlight.setBackground(new Color(255,200,0,51));
        busChannelBottomHighlight.setVisible(true);
        this.add(busChannelBottomHighlight);

        this.add(securityTerminal);

        this.getInputMap().put(KeyStroke.getKeyStroke('w'), "secconUpAction");
        this.getActionMap().put("secconUpAction", secconUpKey);
        this.getInputMap().put(KeyStroke.getKeyStroke('s'),"secconDownAction");
        this.getActionMap().put("secconDownAction", secconDownKey);
        this.getInputMap().put(KeyStroke.getKeyStroke('a'),"secconLeftAction");
        this.getActionMap().put("secconLeftAction", secconLeftKey);
        this.getInputMap().put(KeyStroke.getKeyStroke('d'),"secconRightAction");
        this.getActionMap().put("secconRightAction", secconRightKey);

        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "matrixUpAction");
        this.getActionMap().put("matrixUpAction", matrixUpKey);
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"matrixDownAction");
        this.getActionMap().put("matrixDownAction", matrixDownKey);
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"matrixLeftAction");
        this.getActionMap().put("matrixLeftAction", matrixLeftKey);
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"matrixRightAction");
        this.getActionMap().put("matrixRightAction", matrixRightKey);
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "confirmAction");
        this.getActionMap().put("confirmAction", spaceKey);

        CHECK_CLICKED_BUSSTOP.start();
    }

    public void navigationLogic(int pInputX){
        if(channelFlip == -1){
            if((pInputX + bottomChannelLocationX) >= universalChannelLeftBound && (pInputX + bottomChannelLocationX) <= bottomChannelRightBound) bottomChannelLocationX += pInputX;
            unHighlight();
            moveHighlight();

            busChannelBottom.newBusLineHighlight(bottomChannelLocationX); // changes bus line highlight
        }
        else{
            if((pInputX + topChannelLocationX) >= universalChannelLeftBound && (pInputX + topChannelLocationX) <= topChannelRightBound) topChannelLocationX += pInputX;
            busChannelTop.newBusLineHighlight(topChannelLocationX);
        }
    }
    private void moveHighlight(){ // backend highlight
        highlightedBusStop = securityTerminal.getBusStopArrayList().get(bottomChannelLocationX);
        highlightedBusStop.toggleVisualHighlightBusStop(true);
    }
    private void unHighlight(){ // remove backend highlight
        highlightedBusStop.toggleVisualHighlightBusStop(false);
    }

    @Override
    public void doLayout(){
        super.doLayout();

        busChannelTop.setLocation((this.getWidth()/2)-(busChannelTop.getWidth()/2),0);
        busChannelBottom.setLocation(0, (this.getHeight()-(securityTerminal.getHeight() + busChannelBottom.getHeight())));

        busChannelTopHighlight.setLocation(0,(busChannelTop.getY()+ busChannelTop.getHeight()));
        busChannelBottomHighlight.setLocation(0,(busChannelBottom.getY()- busChannelBottomHighlight.getHeight()));

        securityTerminal.setLocation(0,(this.getHeight() - securityTerminal.getHeight()));
    }

    public void toggleKeyControls(boolean pToggle){
        matrixUpKey.setEnabled(pToggle);
        matrixDownKey.setEnabled(pToggle);
        matrixLeftKey.setEnabled(pToggle);
        matrixRightKey.setEnabled(pToggle);
        spaceKey.setEnabled(pToggle);
        secconUpKey.setEnabled(pToggle);
        secconDownKey.setEnabled(pToggle);
        secconLeftKey.setEnabled(pToggle);
        secconRightKey.setEnabled(pToggle);
    }

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

    public class secconUpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            channelFlip = 1;
            busChannelTopHighlight.setVisible(true);
            busChannelBottomHighlight.setVisible(false);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class secconDownAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            channelFlip = -1;
            busChannelTopHighlight.setVisible(false);
            busChannelBottomHighlight.setVisible(true);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class secconLeftAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            navigationLogic(-1);
            playSound(electronicClickFilepathArray[rand.nextInt(2)]);
        }
    }
    public class secconRightAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            navigationLogic(1);
            playSound(electronicClickFilepathArray[rand.nextInt(2)]);
        }
    }

    public class matrixUpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            matrixConnection.requestFocusInWindow();
            matrixConnection.setEnabled(true);
            matrixConnection.navigationLogic(0,-1);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class matrixDownAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            matrixConnection.requestFocusInWindow();
            matrixConnection.setEnabled(true);
            matrixConnection.navigationLogic(0,1);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class matrixLeftAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            matrixConnection.requestFocusInWindow();
            matrixConnection.setEnabled(true);
            matrixConnection.navigationLogic(-1,0);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class matrixRightAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            matrixConnection.requestFocusInWindow();
            matrixConnection.setEnabled(true);
            matrixConnection.navigationLogic(1,0);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class confirmAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            matrixConnection.requestFocusInWindow();
            matrixConnection.setEnabled(true);
            matrixConnection.confirmSelection();
            playSound("resources/spacebar_press.wav");
        }
    }

    private void actionPerformedCheckClickedBusStop(ActionEvent e){
        BusStop tempStop;
        for(int count = 0; count < 10; count++){
            tempStop = securityTerminal.getBusStopArrayList().get(count);
            if(tempStop.isClicked()){
                bottomChannelLocationX = count;
                unHighlight();
                moveHighlight();
                busChannelBottom.newBusLineHighlight(bottomChannelLocationX); // changes bus line highlight
                playSound("resources/spacebar_press.wav");
            }
            tempStop.setClicked(false);
        }
    }
}
