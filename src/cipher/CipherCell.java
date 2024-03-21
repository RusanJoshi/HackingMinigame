package cipher;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CipherCell extends JPanel{
    private final JLabel MAIN_LABEL = new JLabel(); // holds visual data

    public JLabel getMAIN_LABEL() {
        return MAIN_LABEL;
    }

    public CipherCell(){
        this.setLayout(null);
        this.setSize(59,59);
        this.setBackground(Color.BLACK);

        secondarySetupMatrixCell();
    }

    private void secondarySetupMatrixCell(){
        MAIN_LABEL.setSize(this.getWidth(), this.getHeight());
        MAIN_LABEL.setOpaque(false);
        MAIN_LABEL.setForeground(Color.WHITE);
        MAIN_LABEL.setBackground(Color.PINK);
        MAIN_LABEL.setFont(new Font(null,Font.BOLD,26));
        MAIN_LABEL.setHorizontalAlignment(SwingConstants.CENTER);
        MAIN_LABEL.setText("*");
        this.add(MAIN_LABEL);
    }

    // "reveals" the MAIN_LABEL
    // the MAIN_LABEL holds the color from the selected color scheme
    public void correctChoiceVisualChange(String pSelectedID){
        MAIN_LABEL.setText(pSelectedID);
        MAIN_LABEL.setOpaque(true);
    }

    // called by the matrix.Matrix>Cipher when the game is restarted
    // CipherCell is reset to initial properties/values
    public void resetCipherCell(){
        MAIN_LABEL.setForeground(Color.WHITE);
        MAIN_LABEL.setText("*");
    }
}
