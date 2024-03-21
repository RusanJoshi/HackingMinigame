package cipher;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class Cipher extends JPanel {
    private final Timer CIPHER_FLASH_TIMER;
    private final CipherPartition CIPHER_PARTITION;
    private final Color DEFAULT_COLOR = new Color(240,240,240);
    private Color flashColor; // used as a "cartridge" for correct/incorrect flash colors
    private int flashCount = 0; // increments with every flash and resets when finished
    private boolean flashBool = true; // oscillates between flashes
    private int cipherProgress = 0; // matrix.Matrix passes its cipherProgress here and misc.OptionsPanel uses it

    public CipherPartition getCIPHER_PARTITION() {
        return CIPHER_PARTITION;
    }

    // called by the matrix.Matrix
    // adjusts cipherProgress value when progressing through passkey and restarting the game
    public void setCipherProgress(int cipherProgress) {
        this.cipherProgress = cipherProgress;
    }
    // called by the misc.OptionsPanel
    // used to resume Cipher's visual progress when previewing or changing colors schemes
    public int getCipherProgress() {
        return cipherProgress;
    }

    public Cipher(int pPasskeyLength){
        this.setLayout(new GridLayout(1,0));
        this.setBackground(DEFAULT_COLOR);
        this.setSize(305, 82);
        Border highlightBorder = BorderFactory.createTitledBorder(null, "D_kryptonite_v3202.92.11", 2, 0, null, Color.BLACK);
        this.setBorder(highlightBorder);
        this.CIPHER_PARTITION = new CipherPartition(pPasskeyLength);
        this.CIPHER_FLASH_TIMER = new Timer(120, this::actionPerformedCipherFlashTimer);

        secondarySetupCipher();
    }

    private void secondarySetupCipher(){
        this.add(CIPHER_PARTITION);
    }

    // called by matrix.Matrix to reset the CipherCells
    public void resetCipher(){
        for(int count = 0; count < 5; count++){
            CIPHER_PARTITION.getCIPHER_CELL_ARRAYLIST().get(count).resetCipherCell();
        }
    }

    // called by the matrix.Matrix
    // flashes red if the player chooses incorrectly
    // flashes white-ish if player chooses correctly
    public void cipherFlash(boolean pCorrect){
        //TODO: flash a color from chosen color scheme
        if(!pCorrect) flashColor = Color.RED;
        else flashColor = Color.GRAY;

        CIPHER_FLASH_TIMER.start();
    }

    // executes when CIPHER_FLASH_TIMER starts
    // changes the color of the Cipher background between two colors for a flashing effect
    private void actionPerformedCipherFlashTimer(ActionEvent e){
        if(flashBool) this.setBackground(flashColor);
        else this.setBackground(DEFAULT_COLOR);

        flashBool = !flashBool;
        flashCount++;

        if(flashCount == 6){
            flashCount = 0;
            CIPHER_FLASH_TIMER.stop();
        }
    }
}
