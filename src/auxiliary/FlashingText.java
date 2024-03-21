package auxiliary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

// JLabel with text that "flashes". Text oscillates between white and black foreground colors.
public class FlashingText extends JLabel {
    private final Timer TEXT_FLASHING_TIMER;
    private boolean flashBool = true;

    public FlashingText(String pText){
        this.setOpaque(false);
        this.setForeground(Color.WHITE);
        this.setSize(200,100);
        this.setVerticalAlignment(SwingConstants.TOP);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setFont(new Font("Bahnschrift", Font.BOLD, 20));
        this.setText(pText);

        TEXT_FLASHING_TIMER = new Timer(800, this::actionPerformedTextFlashing);
        TEXT_FLASHING_TIMER.start();
    }

    public void turnOffTextFlashingTimer(){
        TEXT_FLASHING_TIMER.stop();
    }

    // changes the color of the text, oscillates between white and black
    private void switchTheColors(){
        flashBool = !flashBool;

        if(flashBool){
            TEXT_FLASHING_TIMER.setDelay(800);
            this.setForeground(Color.WHITE);
        }
        else{
            TEXT_FLASHING_TIMER.setDelay(1000);
            this.setForeground(Color.BLACK);
        }
    }

    public void actionPerformedTextFlashing(ActionEvent e){
        switchTheColors();
    }
}
