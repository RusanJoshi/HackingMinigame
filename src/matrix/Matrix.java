package matrix;

import cipher.Cipher;
import misc.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.util.Random;
import auxiliary.DialogueBox;
import security.SecurityContainer;

public class Matrix extends JPanel{
    private final Timer CHECK_CLICKED_MATRIXCELL;
    private final Timer GLITCH_EFFECT;
    private final Timer DB_SPAM_TIMER;
    Random rand = new Random();
    OptionsPanel optionsPanelConnection;
    DetectionMeter detectionMeterConnection;
    Cipher cipherConnection;
    SecurityContainer securityContainerConnection;
    ArrayList<MatrixPartition> matrixPartitionArrayList = new ArrayList<>();
    MatrixCell highlightedCell;
    int matrixDimension;
    int matrixLocationX = 0;
    int matrixLocationY = 0;
    int leftBound = 0;
    int rightBound = 4;
    int upperBound = 0;
    int bottomBound = 4;

    String passkey = "";
    int passKeyProgress = 0;
    String selectedID;
    boolean correctChoice = false;
    int difficultySetting = 1;
    int letterRange, numberRange;
    int detectionLevel = 0;
    boolean toggleDetectionMeter = true;
    boolean laughedAtUserForFailing = false;
    boolean experimentalDifficultyUnlocked = false;

    ArrayList<DialogueBox>  dialogueBoxSpamArrayList = new ArrayList<>();
    String[] hackerGibberish = {"Breach", "Protocol", "Break in", "Cache", "Valid", "DB_01", "DB_99", "Keys", "Behind", "The", "Mask"};
    int colorCounter = 0;

    Action matrixUpKey = new matrixUpAction();
    Action matrixDownKey = new matrixDownAction();
    Action matrixLeftKey = new matrixLeftAction();
    Action matrixRightKey = new matrixRightAction();
    Action spaceKey = new confirmAction();
    
    Action secconUpKey = new secconUpAction();
    Action secconDownKey = new secconDownAction();
    Action secconLeftKey = new secconLeftAction();
    Action secconRightKey = new secconRightAction();

    Border highlightBorder = BorderFactory.createTitledBorder(null,"Jericho(mechanics_microcontroller_adapter)",2,0,null,Color.BLACK);
    String[] keyPressFilepathArray = {
            "resources/keypress_1.wav", "resources/keypress_2.wav", "resources/keypress_3.wav",
            "resources/keypress_4.wav", "resources/keypress_5.wav", "resources/keypress_6.wav",
            "resources/keypress_7.wav", "resources/keypress_8.wav", "resources/keypress_9.wav", };
    String[] particleJingleFilepathArray = {
            "resources/particle_jingle_deep_1.wav", "resources/particle_jingle_deep_1.wav", "resources/particle_jingle_deep_3.wav",
            "resources/particle_jingle_deep_4.wav", "resources/particle_jingle_high_1.wav", "resources/particle_jingle_high_2.wav",
            "resources/particle_jingle_high_3.wav"};

    public Timer getGLITCH_EFFECT() {
        return GLITCH_EFFECT;
    }
    public Timer getDB_SPAM_TIMER() {return DB_SPAM_TIMER;}
    public void setOptionsPanelConnection(OptionsPanel optionsPanelConnection) {
        this.optionsPanelConnection = optionsPanelConnection;
    }
    public void setCipherConnection(Cipher cipherConnection) {
        this.cipherConnection = cipherConnection;
    }
    public void setSecurityContainerConnection(SecurityContainer securityContainerConnection) {
        this.securityContainerConnection = securityContainerConnection;
    }
    public void setDetectionMeterConnection(DetectionMeter detectionMeterConnection) {
        this.detectionMeterConnection = detectionMeterConnection;
    }
    public int getPassKeyProgress() {return passKeyProgress;}
    public void setDifficultySetting(int difficultySetting) {
        this.difficultySetting = difficultySetting;
    }
    public int getDetectionLevel() {
        return detectionLevel;
    }

    public boolean isToggleDetectionMeter() {
        return toggleDetectionMeter;
    }
    public void setToggleDetectionMeter(boolean toggleDetectionMeter) {
        this.toggleDetectionMeter = toggleDetectionMeter;
    }


    public Matrix(int pMatrixDimension){
        this.setLayout(new GridLayout(0,pMatrixDimension));
        this.setSize(305,270);
        this.setBackground(Color.WHITE);
        this.setBorder(highlightBorder);
        this.matrixDimension = pMatrixDimension;
        this.setFocusable(true);
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("Matrix has focus.");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        this.CHECK_CLICKED_MATRIXCELL = new Timer(10, this::actionPerformedCheckClickedMatrixCell);
        this.GLITCH_EFFECT = new Timer(500, this::actionPerformedGlitchEffect);
        this.DB_SPAM_TIMER = new Timer(125, this::actionPerformedDBSpamTimer);

        secondarySetupMatrix();
    }

    private void secondarySetupMatrix(){
        addPartitionsToMatrix();
        moveHighlight();
        creatingRandomPasskey();
        updateOccurrenceDisplay();
        CHECK_CLICKED_MATRIXCELL.start();

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

        this.getInputMap().put(KeyStroke.getKeyStroke('w'), "secconUpAction");
        this.getActionMap().put("secconUpAction", secconUpKey);
        this.getInputMap().put(KeyStroke.getKeyStroke('s'),"secconDownAction");
        this.getActionMap().put("secconDownAction", secconDownKey);
        this.getInputMap().put(KeyStroke.getKeyStroke('a'),"secconLeftAction");
        this.getActionMap().put("secconLeftAction", secconLeftKey);
        this.getInputMap().put(KeyStroke.getKeyStroke('d'),"secconRightKey");
        this.getActionMap().put("secconRightKey", secconRightKey);
    }

    // adds MatrixPartitions to the Matrix
    // initial setup with medium difficulty (M:10,10)
    private void addPartitionsToMatrix(){
        for(int count = 0; count < matrixDimension; count++){
            matrixPartitionArrayList.add(new MatrixPartition(matrixDimension));
            matrixPartitionArrayList.get(count).setColumnNumber(count);
            this.add(matrixPartitionArrayList.get(count));
        }
    }

    // used to navigate the Matrix, moving between different MatrixPartitions and the MatrixCells within them
    // highlights newly hovered MatrixCell and removes the highlight from the previous MatrixCell
    public void navigationLogic(int pInputX, int pInputY){
        // checks to see if the player is moving out of bounds
        if((pInputX + matrixLocationX) >= leftBound && (pInputX + matrixLocationX) <= rightBound) matrixLocationX += pInputX;
        if((pInputY + matrixLocationY) <= bottomBound && (pInputY + matrixLocationY) >= upperBound) matrixLocationY += pInputY;

        unHighlight(); // removes the highlight from the previously hovered matrix cell
        moveHighlight(); // highlights the newly hovered matrix cell
    }

    /*
     first checks to see if player has reached a detectionLevel of 5
     if detectionLevel of 5 is reached than glitch/corruption effect begins
     also has a 20% of being laughed at
     else-if progresses the game, checking for selection parity
     and progressing the passKey if selection was correct
     finally, checks to see if passKeyProgress equals 5
     if so, player wins the game and spam animation begins
     */
    public void confirmSelection(){
        if(detectionLevel == 5){ // LOSE SCENARIO
            System.out.println("Ya done lost, son.");
            playSound("resources/hack_fail_1_3.wav");
            GLITCH_EFFECT.start(); // starts random color glitch effect
            if(!laughedAtUserForFailing){
                if(rand.nextInt(5) == 0){
                    playSound("resources/gaspysnicker.wav");
                    laughedAtUserForFailing = true;
                }
            }
        }
        else if(passKeyProgress <= 4){
            parityCheck();
            progressKey(correctChoice);
        }
        if(passKeyProgress == 5){ // WIN SCENARIO
            System.out.println("I'm in.");
            if(toggleDetectionMeter && detectionLevel == 0 && !experimentalDifficultyUnlocked){
                optionsPanelConnection.getDifficultyButtonHardExperimental().setVisible(true);
                experimentalDifficultyUnlocked = true;
                System.out.println("[UNLOCKED EXPERIMENTAL DIFFICULTY]");
            }
            // Disable MatrixPartitions visibility
            for(int column = 0; column < matrixDimension; column++){
                matrixPartitionArrayList.get(column).setVisible(false);
            }
            // Initialize misc.DialogueBox
            DB_SPAM_TIMER.start();
        }
    }

    /*
     checks if the selected MatrixCell's address has a char match
     with the passkey at the relevant passkeyProgress point
     if not, detectionLevel(int) increments
     if detectionMeter has been disabled in-game, then the detectionLevel will not increment
     */
    private void parityCheck(){
        selectedID = highlightedCell.getAddress();
        if(selectedID.charAt(0) == passkey.charAt(passKeyProgress)){
            System.out.println("Correct! (" + selectedID.charAt(0) + ")");
            correctChoice = true;
            playSound(particleJingleFilepathArray[rand.nextInt(7)]);
            cipherConnection.cipherFlash(correctChoice);
        }
        else if(selectedID.charAt(1) == passkey.charAt(passKeyProgress)){
            System.out.println("Correct! (" + selectedID.charAt(1) + ")");
            correctChoice = true;
            playSound(particleJingleFilepathArray[rand.nextInt(7)]);
            cipherConnection.cipherFlash(correctChoice);
        }
        else{
            if(detectionLevel <= 4){
                if(detectionLevel == 2){
                    playSound("resources/hack_fail_1_1.wav");
                }
                if(detectionLevel == 3){
                    playSound("resources/hack_fail_1_2.wav");
                }
                if(toggleDetectionMeter){
                    detectionMeterConnection.getSecurityAlertArrayList().get(detectionLevel).setBackground(Color.RED.darker());
                    detectionLevel++;
                }
                if(detectionLevel == 5){
                    confirmSelection();
                    // one time lose-related code can go here.
                }
            }
            correctChoice = false;
            cipherConnection.cipherFlash(correctChoice);
        }
    }

    /*
     when the user makes a correct choice
     this method reveals the next CipherCell, making its mainLabel visible
     then increments passKeyProgress(int) and passes that value to the Cipher
     */
    private void progressKey(boolean pCorrectChoice){ // also changes Cipher Cell color
        if(pCorrectChoice){
            cipherConnection.getCIPHER_PARTITION().getCIPHER_CELL_ARRAYLIST().get(passKeyProgress).correctChoiceVisualChange(selectedID);

            if(passKeyProgress == 0) cipherConnection.getCIPHER_PARTITION().getCIPHER_CELL_ARRAYLIST().get(passKeyProgress).getMAIN_LABEL().setForeground(Color.BLACK);

            passKeyProgress++;
            cipherConnection.setCipherProgress(passKeyProgress);
            if(passKeyProgress <= 4) updateOccurrenceDisplay();
        }
    }

    // goes through every partition and updates the occurrence count
    private void updateOccurrenceDisplay(){
        String cellAddress;
        int occurrenceCount = 0;

        for(int alphaX = 0; alphaX < matrixDimension; alphaX++){
            for(int bravoY = 0; bravoY < matrixDimension; bravoY++){
                cellAddress = matrixPartitionArrayList.get(alphaX).getMatrixCellArrayList().get(bravoY).getAddress();
                if(cellAddress.charAt(0) == passkey.charAt(passKeyProgress)) occurrenceCount++;
                if(cellAddress.charAt(1) == passkey.charAt(passKeyProgress)) occurrenceCount++;
            }
            matrixPartitionArrayList.get(alphaX).updateOccurrenceValue(occurrenceCount);
            occurrenceCount = 0;
        }
    }

    /*
     method generates random x and y values
     random x and y values are used to grab corresponding MatrixCell
     gets the MatrixCell's address and randomly chooses one of two characters
     to add to the passkey String
     */
    private void creatingRandomPasskey(){
        passkey = "";
        for(int count = 0; count < matrixDimension; count++){
            int randX = rand.nextInt(5);
            int randY = rand.nextInt(5);
            int index = rand.nextInt(2 );

            System.out.print(matrixPartitionArrayList.get(randX).getMatrixCellArrayList().get(randY).getAddress());
            passkey += matrixPartitionArrayList.get(randX).getMatrixCellArrayList().get(randY).getAddress().charAt(index);
        }
        System.out.println("\nPasskey: " + passkey);
    }

    // highlights newly hovered MatrixCell
    private void moveHighlight(){ // backend highlight
        highlightedCell = matrixPartitionArrayList.get(matrixLocationX).getMatrixCellArrayList().get(matrixLocationY);
        highlightedCell.visualHighlightCell();
    }

    // removes highlight from MatrixCell
    private void unHighlight(){ // remove backend highlight
        highlightedCell.visualUnhighlightCell();
    }

    // toggles mouse controls and interactions
    public void toggleMouseListenerForAllMatrixCells(boolean pToggle){
        for(int partitionCount = 0; partitionCount < matrixDimension; partitionCount++){
            for(int cellCount = 0; cellCount < matrixDimension; cellCount++){
                matrixPartitionArrayList.get(partitionCount).getMatrixCellArrayList().get(cellCount).toggleMouseListener(pToggle);
            }
        }
    }

    // right-clicking on a cell will highlight it, flagging it
    // this method resets all flags
    public void resetAllCellFlags(){
        for(int partitionCount = 0; partitionCount < matrixDimension; partitionCount++){
            for(int cellCount = 0; cellCount < matrixDimension; cellCount++){
                matrixPartitionArrayList.get(partitionCount).getMatrixCellArrayList().get(cellCount).getMainLabel().setBackground(Color.BLACK);
            }
        }
    }

    // toggles key controls
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

    // resets the Matrix and Cipher along with necessary components within
    public void resetMatrix(){ // related to restart
        MatrixCell tempCell;

        passKeyProgress = 0;
        cipherConnection.setCipherProgress(passKeyProgress);

        switch(difficultySetting){
            case 0:
                letterRange = 5;
                numberRange = 5;
                System.out.println("Difficulty: Easy, " + difficultySetting + ", (" + letterRange + "," + numberRange + ")");
                break;
            case 1:
                letterRange = 10;
                numberRange = 10;
                System.out.println("Difficulty: Default, " + difficultySetting + ", (" + letterRange + "," + numberRange + ")");
                break;
            case 2:
                letterRange = 26;
                numberRange = 10;
                System.out.println("Difficulty: Hard, " + difficultySetting + ", (" + letterRange + "," + numberRange + ")");
                break;
            case 3:
                letterRange = 26;
                numberRange = 23;
                System.out.println("Difficulty: Hard(experimental), " + difficultySetting + ", (" + letterRange + "," + numberRange + ")");
                break;
            default:
                System.out.println("Error (Matrix.java, switch case: default).");
                break;
        }

        this.setBackground(Color.WHITE);
        for(int partitionCount = 0; partitionCount < matrixDimension; partitionCount++){
            matrixPartitionArrayList.get(partitionCount).setVisible(true);
            matrixPartitionArrayList.get(partitionCount).setBackground(Color.BLACK);
            for(int cellCount = 0; cellCount < matrixDimension; cellCount++){
                tempCell = matrixPartitionArrayList.get(partitionCount).getMatrixCellArrayList().get(cellCount);
                tempCell.resetMatrixCell(letterRange,numberRange);
            }
        }
        unHighlight();
        moveHighlight();
        creatingRandomPasskey();
        updateOccurrenceDisplay();

        detectionMeterConnection.resetSecurityDetectionMeter();
        detectionLevel = 0;
        laughedAtUserForFailing = false;

        cipherConnection.resetCipher();
        cipherConnection.getCIPHER_PARTITION().setColors();

        // removes all misc.DialogueBox spam from the matrix. However, it does not remove it from the ArrayList;
        for(int count = 0; count < dialogueBoxSpamArrayList.size(); count++){
            this.remove(dialogueBoxSpamArrayList.get(count));
        }
        dialogueBoxSpamArrayList.clear();
        colorCounter = 0;

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

    // key events used to navigate the Matrix
    public class matrixUpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            navigationLogic(0,-1);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class matrixDownAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            navigationLogic(0,1);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class matrixLeftAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            navigationLogic(-1,0);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class matrixRightAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            navigationLogic(1,0);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class confirmAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            confirmSelection();
            playSound("resources/spacebar_press.wav");
        }
    }

    // key events used to give focus to SecurityContainer and navigate the BusChannels
    public class secconUpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            securityContainerConnection.requestFocusInWindow();
            securityContainerConnection.setEnabled(true);
            securityContainerConnection.setChannelFlip(1);
            securityContainerConnection.getBusChannelTopHighlight().setVisible(true);
            securityContainerConnection.getBusChannelBottomHighlight().setVisible(false);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class secconDownAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            securityContainerConnection.requestFocusInWindow();
            securityContainerConnection.setEnabled(true);
            securityContainerConnection.setChannelFlip(-1);
            securityContainerConnection.getBusChannelTopHighlight().setVisible(false);
            securityContainerConnection.getBusChannelBottomHighlight().setVisible(true);
            playSound(keyPressFilepathArray[rand.nextInt(9)]);
        }
    }
    public class secconLeftAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            securityContainerConnection.requestFocusInWindow();
            securityContainerConnection.setEnabled(true);
            securityContainerConnection.navigationLogic(-1);
        }
    }
    public class secconRightAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            securityContainerConnection.requestFocusInWindow();
            securityContainerConnection.setEnabled(true);
            securityContainerConnection.navigationLogic(1);
        }
    }

    // checks for MatrixCells selected with the mouse
    private void actionPerformedCheckClickedMatrixCell(ActionEvent e) {
        MatrixCell tempCell;

        for(int partitionCount = 0; partitionCount < matrixDimension; partitionCount++){
            for(int cellCount = 0; cellCount < matrixDimension; cellCount++){
                tempCell = matrixPartitionArrayList.get(partitionCount).getMatrixCellArrayList().get(cellCount);

                if(tempCell.isClicked()){
                    matrixLocationX = partitionCount;
                    matrixLocationY = cellCount;
                    unHighlight();
                    moveHighlight();
                    confirmSelection();
                }
                tempCell.setClicked(false);

            }
        }
    }

    // when the game is lost, every MatrixCell is "corrupted" repeatedly (lose screen)
    private void actionPerformedGlitchEffect(ActionEvent e){
        for(int partitionCount = 0; partitionCount < matrixDimension; partitionCount++){
            matrixPartitionArrayList.get(partitionCount).setBackground(Color.RED.darker());
            for(int cellCount = 0; cellCount < matrixDimension; cellCount++){
                matrixPartitionArrayList.get(partitionCount).getMatrixCellArrayList().get(cellCount).randomVisualGlitchChange();
            }
        }
    }

    // when the game is won, and the MatrixPartitions have lost their visibility
    // the Matrix is spammed with DialogueBoxes with random words (win screen)
    private void actionPerformedDBSpamTimer(ActionEvent e){
        DialogueBox dialogueBoxCartridge = new DialogueBox(this.getWidth(), this.getHeight());
        dialogueBoxCartridge.preTalkSetup(hackerGibberish[rand.nextInt(hackerGibberish.length)]);
        dialogueBoxSpamArrayList.add(dialogueBoxCartridge);
        if(dialogueBoxSpamArrayList.size() >= 10){
            if(dialogueBoxSpamArrayList.size() == 10) playSound("resources/win_beep.wav");

            dialogueBoxSpamArrayList.get(dialogueBoxSpamArrayList.size()-10).setBackground(cipherConnection.getCIPHER_PARTITION().getCurrentColorScheme().get(colorCounter));
            this.setBackground(cipherConnection.getCIPHER_PARTITION().getCurrentColorScheme().get(colorCounter));
            colorCounter++;
        }
        if(colorCounter == 5) colorCounter = 0;
        this.add(dialogueBoxCartridge);
    }
}
