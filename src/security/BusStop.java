package security;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.border.Border;

public class BusStop extends JLabel implements MouseListener {
    Random rand = new Random();
    final Timer HIGHLIGHT_ANIMATION_TIMER;
    final int SPEED = 3;
    int travelledDistance = 0;
    int minTravelDistance = 0;
    int maxTravelDistance = 24; // SPEED must also be changed into a divisible number of maxTravelSpeed
    int mechanism = -1; // -1 = closed, 1 = open
    boolean mouseHover = false;
    boolean clicked;

    Border highlightBorder = BorderFactory.createLineBorder(Color.ORANGE, 1);
    String[] electronicClickFilepathArray = {"resources/electronic_click_1.wav", "resources/electronic_click_2.wav"};

    public boolean isClicked() {
        return clicked;
    }
    public void setClicked(boolean pBoolean){
        this.clicked = pBoolean;
    }

    public BusStop(){
        this.setSize(35,50);
        this.setOpaque(true);
        this.setForeground(Color.BLACK);
        this.setBackground(Color.BLACK);
        this.setFont(new Font(null,Font.BOLD,13));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.BOTTOM);
        this.setText(rand.nextInt(101) + "%");
        this.addMouseListener(this);

        HIGHLIGHT_ANIMATION_TIMER = new Timer(10, this::actionPerformedHighlightAnimation);
    }

    // highlights and animates when hovered over
    public void toggleVisualHighlightBusStop(boolean pBinaryStatus){
        if(pBinaryStatus){ // hovering over/highlighted
            enableBorder();
            animateBus(pBinaryStatus);
        }
        else{
            disableBorder();
            animateBus(pBinaryStatus);
        }
    }

    // visual highlight
    private void enableBorder(){
        this.setForeground(Color.WHITE);
        this.setBorder(highlightBorder);
    }

    // removes visual highlight
    private void disableBorder(){
        this.setForeground(Color.BLACK);
        this.setBorder(null);
    }


    private void animateBus(boolean pMouseHover){
        mouseHover = pMouseHover;
        HIGHLIGHT_ANIMATION_TIMER.start();
    }

    public void toggleMouseListener(boolean pToggle){
        if(!pToggle){
            this.removeMouseListener(this);
        }
        else this.addMouseListener(this);
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
        this.clicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        enableBorder();
        animateBus(true);
        playSound(electronicClickFilepathArray[rand.nextInt(2)]);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        disableBorder();
        animateBus(false);
    }

    private void actionPerformedHighlightAnimation(ActionEvent aPHA){
        if(mouseHover){ // if closed, then open
            if(travelledDistance == maxTravelDistance){
                HIGHLIGHT_ANIMATION_TIMER.stop();
                mechanism *= -1;
            }
            else{
                travelledDistance += SPEED;
                this.setLocation(this.getX(), this.getY()+(SPEED));
            }
        }
        else if(!mouseHover){ // if open, then close
            if(travelledDistance == minTravelDistance){
                HIGHLIGHT_ANIMATION_TIMER.stop();
                mechanism *= -1;
            }
            else{
                travelledDistance -= SPEED;
                this.setLocation(this.getX(), this.getY()-(SPEED));
            }
        }
    }
}
