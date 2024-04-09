package matrix;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class MatrixCell extends JPanel implements MouseListener {
    JLabel mainLabel = new JLabel();
    Border highlightBorder;
    int rowNumber;

    Random rand = new Random();
    String letterList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String numberList = "0123456789!~#$%&*()-_=+";
    String address;
    boolean clicked;
    boolean flagged = false; // var for a feature not fully fleshed out. Flagging MatrixCells (right click)

    String[] filepathArray = {
            "resources/keypress_1.wav", "resources/keypress_2.wav", "resources/keypress_3.wav",
            "resources/keypress_4.wav", "resources/keypress_5.wav", "resources/keypress_6.wav",
            "resources/keypress_7.wav", "resources/keypress_8.wav", "resources/keypress_9.wav", };

    Color[] randomColorArray = {Color.WHITE, Color.BLACK, Color.ORANGE, Color.RED.darker()};

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
    public String getAddress() {
        return address;
    }
    public boolean isClicked() {
        return clicked;
    }
    public void setClicked(boolean pBoolean){
        this.clicked = pBoolean;
    }
    public JLabel getMainLabel() {
        return mainLabel;
    }

    public MatrixCell(){
        this.setLayout(null);
        this.setSize(50,50);
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        this.clicked = false;

        secondarySetupMatrixCell();
    }

    private void secondarySetupMatrixCell(){
        //Preliminary setup
        highlightBorder = BorderFactory.createTitledBorder(null,"=",0,0,null,Color.WHITE);
        address = String.valueOf(letterList.charAt(rand.nextInt(10))) + String.valueOf(numberList.charAt(rand.nextInt(10)));

        //mainLabel
        mainLabel.setSize(this.getWidth(), this.getHeight());
        mainLabel.setOpaque(true);
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setBackground(Color.BLACK);
        mainLabel.setFont(new Font(null,Font.BOLD,25));
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabel.setText(address);
        this.add(mainLabel);
    }

    // highlights cell when hovering over it
    public void visualHighlightCell(){ // visual highlight
        mainLabel.setBorder(highlightBorder);
        mainLabel.setForeground(Color.ORANGE);
        mainLabel.setLocation(mainLabel.getX(), mainLabel.getY()-4);
    }

    // removes highlight from cell when hovering another cell
    public void visualUnhighlightCell(){ // remove visual highlight
        mainLabel.setBorder(null);
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setLocation(mainLabel.getX(), mainLabel.getY()+4);
    }

    // upon losing the game, this method is used on every MatrixCell to visually "corrupt" it
    public void randomVisualGlitchChange(){
        address = letterList.charAt(rand.nextInt(10)) + String.valueOf(numberList.charAt(rand.nextInt(10)));
        mainLabel.setText(address);
        mainLabel.setForeground(randomColorArray[rand.nextInt(4)]);
        mainLabel.setBackground(randomColorArray[rand.nextInt(4)]);
    }

    // method is used to toggle the ability to interact with the MatrixCell with the mouse
    public void toggleMouseListener(boolean pToggle){
        if(!pToggle){
            this.removeMouseListener(this);
        }
        else this.addMouseListener(this);
    }

    // executes when restarting the game
    // resets the Matrix to default visual values and gives it a new random address(int) value
    public void resetMatrixCell(int pLetterRange, int pNumberRange){
        address = letterList.charAt(rand.nextInt(pLetterRange)) + String.valueOf(numberList.charAt(rand.nextInt(pNumberRange)));
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setBackground(Color.BLACK);
        mainLabel.setText(address);
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
        if (SwingUtilities.isLeftMouseButton(e)) {
//            System.out.println("Left mouse button clicked");
            this.clicked = true;
            playSound("resources/spacebar_press.wav");
        } else if (SwingUtilities.isRightMouseButton(e)) {
//            System.out.println("Right mouse button clicked");
            if(!flagged){
                mainLabel.setBackground(new Color(123,100,0));
                flagged = true;
            }
            else{
                mainLabel.setBackground(Color.BLACK);
                flagged = false;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        visualHighlightCell();
        playSound(filepathArray[rand.nextInt(9)]);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        visualUnhighlightCell();
    }
}
