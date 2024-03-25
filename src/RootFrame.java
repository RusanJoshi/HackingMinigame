import cipher.Cipher;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import matrix.Matrix;
import auxiliary.StudioSplashScreen;
import misc.DetectionMeter;
import misc.DevCredits;
import misc.OptionsPanel;
import security.SecurityContainer;

/*
@author Rusan Joshi
"Red Five standing by."
Hacking Minigame 1.0
03/03/24
FINISHED (Nov 29, 2023 - Mar 3, 2024) - [1.0]

TODO:
 -Main Ops
 [!]
 -Side Ops
 [!!] Music player
 [!] Highlight selected color scheme button
 [!] Change color scheme buttons
 [!] Change restart and quit buttons (The font too, please.)
 [!] Cycle around to the first/last security.BusStop when hitting the edge (QoL)
 -BUGS
 [*] Current color scheme does not preview
 [*] toggleDetectionMeter ToggleButton color reversal (Still functions correctly)
 [*] Not a huge issue, but when the credits are open, the OptionsPanel can be closed and the game still functions in the background
 */

public class RootFrame {
    private final int frameWidth = 450;
    private final int frameHeight = 700;
    ImageIcon windowIcon = new ImageIcon(getClass().getClassLoader().getResource("terminal.png"));

    public RootFrame(){
        JFrame root = new JFrame();
        root.setTitle("EncryptarK Jericho SafeLock™(mechanics_microcontroller_adapter)");
        root.setIconImage(windowIcon.getImage());
        root.add(new Composition());
        root.pack();
        root.setLocationRelativeTo(null);
        root.setVisible(true);
        root.setResizable(false);
        root.setAlwaysOnTop(true);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class Composition extends JPanel implements MouseListener {
        JLayeredPane rootLayers = new JLayeredPane();
        //CORE
        OptionsPanel optionsPanel = new OptionsPanel();
        OptionsPanel fauxOptionsPanel = new OptionsPanel();
        Matrix matrix = new Matrix(5);
        DetectionMeter detectionMeter = new DetectionMeter();
        Cipher cipher = new Cipher(5);
        SecurityContainer securityContainer = new SecurityContainer(frameWidth, 406); // Hardcoded value (406, frameHeight - (matrix.y+matrix.height))
        //EXTRA
        StudioSplashScreen studioSplashScreen = new StudioSplashScreen();
        DevCredits devCredits = new DevCredits();


        public Composition(){
            setLayout(null);
            this.setBackground(Color.BLACK);

            rootLayers.setSize(frameWidth, frameHeight);
            this.add(rootLayers);

            //CORE
            optionsPanel.setCipherConnection(cipher);
            this.add(optionsPanel);

            fauxOptionsPanel.setVisible(true);
            this.add(fauxOptionsPanel);

            matrix.setDetectionMeterConnection(detectionMeter);
            matrix.setCipherConnection(cipher);
            matrix.setSecurityContainerConnection(securityContainer);
            this.add(matrix);

            this.add(detectionMeter);

            this.add(cipher);

            securityContainer.setMatrixConnection(matrix);
            this.add(securityContainer);

            //EXTRA
            studioSplashScreen.addMouseListener(this);
            this.add(studioSplashScreen);

            devCredits.setOptionsPanelConnection(optionsPanel);

            secondarySetupComposition();
        }

        private void secondarySetupComposition(){
            playSound("resources/program_start.wav");

            rootLayers.add(studioSplashScreen, JLayeredPane.DRAG_LAYER);
            rootLayers.add(devCredits, JLayeredPane.DRAG_LAYER);
            rootLayers.add(optionsPanel,JLayeredPane.DRAG_LAYER);
            rootLayers.add(matrix,JLayeredPane.POPUP_LAYER);
            rootLayers.add(cipher,JLayeredPane.POPUP_LAYER);
            rootLayers.add(securityContainer,JLayeredPane.POPUP_LAYER);

            addMouseListenersToOptionPanelItems();

            // the code below is for the StudioSplashScreen
            // pauses the game until the StudioSplashScreen is clicked on
            //DISABLE
            matrix.getGLITCH_EFFECT().stop();
            matrix.getDB_SPAM_TIMER().stop();
            matrix.toggleMouseListenerForAllMatrixCells(false);
            matrix.toggleKeyControls(false);
            securityContainer.getSecurityTerminal().toggleMouseListenerForAllBusStops(false);
            securityContainer.toggleKeyControls(false);
            //OPEN
            optionsPanel.setOptionsButtonBool(true);
            optionsPanel.setVisible(true);
            fauxOptionsPanel.setOptionsButtonBool(true);
        }

        private void addMouseListenersToOptionPanelItems(){
            optionsPanel.getOptionsButton().addMouseListener(this);
            fauxOptionsPanel.getOptionsButton().addMouseListener(this);

            optionsPanel.getRestartButton().addMouseListener(this);
            optionsPanel.getQuitButton().addMouseListener(this);
            optionsPanel.getCreditsButton().addMouseListener(this);

            optionsPanel.getDetectionMeterToggleButton().addMouseListener(this);
        }

        @Override
        public void doLayout() {
            super.doLayout();
            int spaceBetweenMatrixAndTerminal;

            optionsPanel.setLocation(frameWidth-optionsPanel.getWidth(),0);
            fauxOptionsPanel.setLocation(this.getWidth()-50,0);

            matrix.setLocation((frameWidth/2)-(matrix.getWidth()/2), 24);
            detectionMeter.setLocation(matrix.getX()-detectionMeter.getWidth(),((matrix.getY()+matrix.getHeight())/2)-(detectionMeter.getHeight()/2));
            securityContainer.setLocation(0, (matrix.getY() + matrix.getHeight()));

            spaceBetweenMatrixAndTerminal = ((frameHeight-60) - (matrix.getY()+matrix.getHeight())); // Hardcoded value (60, from security.SecurityTerminal.height)
            cipher.setLocation(matrix.getX(), ((spaceBetweenMatrixAndTerminal/2)-(cipher.getHeight()/2))+(matrix.getY()+matrix.getHeight()));
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

        @Override
        public void mouseClicked(MouseEvent e) {
            //CLOSING options menu
            if(e.getComponent() == optionsPanel.getOptionsButton()){
                //ENABLE
                if(matrix.getDetectionLevel() == 5) matrix.getGLITCH_EFFECT().start();
                if(matrix.getPassKeyProgress() == 5) matrix.getDB_SPAM_TIMER().start();
                matrix.toggleMouseListenerForAllMatrixCells(true);
                matrix.toggleKeyControls(true);
                securityContainer.getSecurityTerminal().toggleMouseListenerForAllBusStops(true);
                securityContainer.toggleKeyControls(true);
                //CLOSE
                optionsPanel.setOptionsButtonBool(false);
                optionsPanel.setVisible(false);
                fauxOptionsPanel.setOptionsButtonBool(false);
            }
            //OPENING options menu
            if(e.getComponent() == fauxOptionsPanel.getOptionsButton()){
                if(!fauxOptionsPanel.isOptionsButtonBool()){
                    //DISABLE
                    matrix.getGLITCH_EFFECT().stop();
                    matrix.getDB_SPAM_TIMER().stop();
                    matrix.toggleMouseListenerForAllMatrixCells(false);
                    matrix.toggleKeyControls(false);
                    securityContainer.getSecurityTerminal().toggleMouseListenerForAllBusStops(false);
                    securityContainer.toggleKeyControls(false);
                    //OPEN
                    optionsPanel.setOptionsButtonBool(true);
                    optionsPanel.setVisible(true);
                    fauxOptionsPanel.setOptionsButtonBool(true);
                }
            }

            if(e.getComponent() == optionsPanel.getDetectionMeterToggleButton()){
                matrix.setToggleDetectionMeter(!matrix.isToggleDetectionMeter());

                if(matrix.isToggleDetectionMeter()){
                    optionsPanel.getDetectionMeterToggleButton().setText("tdm_on");
                    detectionMeter.setVisible(true);
                }
                else{
                    optionsPanel.getDetectionMeterToggleButton().setText("tdm_off");
                    detectionMeter.setVisible(false);
                }

            }

            if(e.getComponent() == optionsPanel.getRestartButton()){
                matrix.resetMatrix(); // Restart game
            }

            if(e.getComponent() == optionsPanel.getQuitButton()){
                System.exit(0); // Closes the program
            }
            if(e.getComponent() == optionsPanel.getCreditsButton()){
                devCredits.setVisible(true);
                devCredits.startCredits();
                optionsPanel.getRestartButton().setVisible(false);
                optionsPanel.getQuitButton().setVisible(false);
                optionsPanel.getCreditsButton().setVisible(false);
            }

            if(e.getComponent() == studioSplashScreen){
                studioSplashScreen.getFlashingText().turnOffTextFlashingTimer();
                studioSplashScreen.setVisible(false);
                this.remove(studioSplashScreen);
                playSound("resources/splash_screen_end.wav");

                //RESUME GAME
                //ENABLE
                if(matrix.getDetectionLevel() == 5) matrix.getGLITCH_EFFECT().start();
                if(matrix.getPassKeyProgress() == 5) matrix.getDB_SPAM_TIMER().start();
                matrix.toggleMouseListenerForAllMatrixCells(true);
                matrix.toggleKeyControls(true);
                securityContainer.getSecurityTerminal().toggleMouseListenerForAllBusStops(true);
                securityContainer.toggleKeyControls(true);
                //CLOSE
                optionsPanel.setOptionsButtonBool(false);
                optionsPanel.setVisible(false);
                fauxOptionsPanel.setOptionsButtonBool(false);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(frameWidth, frameHeight);
        }
    }
}
