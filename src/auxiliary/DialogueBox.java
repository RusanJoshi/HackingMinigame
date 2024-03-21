package auxiliary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

// JLabel with text that "animates".
// Input is fed via parameter in preTalkSetup()
// Input String is set to String currentDialogue
// Timer is used to increment char's from currentDialogue to String conjugatedText
public class DialogueBox extends JLabel {
    private Timer dialogueBoxTimer;
    String currentDialogue;
    String conjugatedText = "";
    private String LeftTags = "<html><center>";
    private String RightTags = "<center><html>";
    private int alpha = 0;
    private int settledText = -1; // used to determine if the DialogueBox has already animated a particular string

    public Timer getDialogueBoxTimer() {
        return dialogueBoxTimer;
    }

    public int getSettledText() {
        return settledText;
    }
    public void setSettledText(int settledText) {
        this.settledText = settledText;
    }

    public DialogueBox(int pWidth, int pHeight){
        this.setSize(pWidth, pHeight);
        this.setOpaque(true);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
        this.setVerticalAlignment(SwingConstants.TOP);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setFont(new Font("Bahnschrift", Font.BOLD, 20));

        dialogueBoxTimer = new Timer(20,this::actionPerformedDialogueBoxTimer);
    }

    // SETUP, takes the String parameter and assigns it to currentDialogue
    // starts DIALOGUE_BOX_TIMER
    public void preTalkSetup(String pDialogue){
        currentDialogue = pDialogue;
        conjugatedText = "";
        alpha = 0;
        dialogueBoxTimer.start();
    }

    // method increments characters from the currentDialogue
    // adds each char to conjugatedText and increments alpha
    // when alpha value == currentDialogue.length-1, stop DIALOGUE_BOX_TIMER
    private void talking(){
        if(alpha == currentDialogue.length()-1){
            dialogueBoxTimer.stop();
        }

        conjugatedText = conjugatedText + currentDialogue.charAt(alpha);
        this.setText(LeftTags + conjugatedText + RightTags);
        alpha++;
    }

    public void customTags(String pLeftTags, String pRightTags){
        LeftTags = pLeftTags;
        RightTags = pRightTags;
    }
    public void customFontProperties(String pFontName, int pFontStyle, int pFontSize){
        this.setFont(new Font(pFontName, pFontStyle, pFontSize));
    }
    public void customTextSpeed(int pSpeed){
        dialogueBoxTimer = new Timer(pSpeed,this::actionPerformedDialogueBoxTimer);
    }

    // executes when DIALOGUE_BOX_TIMER starts
    private void actionPerformedDialogueBoxTimer(ActionEvent event) {
        talking();
    }
}
