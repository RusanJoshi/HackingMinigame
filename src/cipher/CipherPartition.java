package cipher;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CipherPartition extends JPanel {
    private final ArrayList<CipherCell> CIPHER_CELL_ARRAYLIST = new ArrayList<>();
    private final int PASSKEY_PROGRESS;

    private final ArrayList<Color> DEFAULT_COLORS = new ArrayList<>();
    private final ArrayList<Color> STARFIELD_COLORS = new ArrayList<>();
    private final ArrayList<Color> GAMEBOY_COLORS = new ArrayList<>();
    private final ArrayList<Color> VIRTUALBOY_COLORS = new ArrayList<>();
    private ArrayList<Color> currentColorScheme, previewColorScheme;

    public ArrayList<CipherCell> getCIPHER_CELL_ARRAYLIST() {
        return CIPHER_CELL_ARRAYLIST;
    }
    public ArrayList<Color> getCurrentColorScheme() {
        return currentColorScheme;
    }

    public CipherPartition(int pPasskeyLength){
        this.setLayout(new GridLayout(0,pPasskeyLength));
        this.setBackground(Color.WHITE);
        this.PASSKEY_PROGRESS = pPasskeyLength;

        secondarySetupCipherPartition();
    }

    private void secondarySetupCipherPartition(){
        addCipherCellsToCipherPartition();
        initializeColorsToArrayLists();
        currentColorScheme = DEFAULT_COLORS;
        setColors();
    }

    // instantiates new CipherCells as they are added to an ArrayList of CipherCells
    private void addCipherCellsToCipherPartition(){
        for(int count = 0; count < PASSKEY_PROGRESS; count++){
            CIPHER_CELL_ARRAYLIST.add(new CipherCell());
            this.add(CIPHER_CELL_ARRAYLIST.get(count));
        }
    }

    // adds color values to the appropriate ArrayLists
    private void initializeColorsToArrayLists(){
        DEFAULT_COLORS.add(Color.ORANGE);
        DEFAULT_COLORS.add(Color.ORANGE.darker());
        DEFAULT_COLORS.add(Color.ORANGE.darker().darker());
        DEFAULT_COLORS.add(Color.ORANGE.darker().darker().darker());
        DEFAULT_COLORS.add(Color.ORANGE.darker().darker().darker().darker());

        STARFIELD_COLORS.add(new Color(244,245,247));
        STARFIELD_COLORS.add(new Color(199,33,56));
        STARFIELD_COLORS.add(new Color(224,98,54));
        STARFIELD_COLORS.add(new Color(215,166,75));
        STARFIELD_COLORS.add(new Color(48,76,122));

        GAMEBOY_COLORS.add(new Color(155,188,15));
        GAMEBOY_COLORS.add(new Color(139,172,15));
        GAMEBOY_COLORS.add(new Color(48,98,48));
        GAMEBOY_COLORS.add(new Color(15,56,15));
        GAMEBOY_COLORS.add(new Color(15,56,15));

        VIRTUALBOY_COLORS.add(new Color(239, 0, 0));
        VIRTUALBOY_COLORS.add(new Color(164, 0, 0));
        VIRTUALBOY_COLORS.add(new Color(85, 0, 0));
        VIRTUALBOY_COLORS.add(new Color(85, 0, 0));
        VIRTUALBOY_COLORS.add(Color.BLACK);
    }

    // indefinite change to the CipherCells' color
    public void setColors(){
        for(int count = 0; count < 5; count++){
            CIPHER_CELL_ARRAYLIST.get(count).getMAIN_LABEL().setBackground(currentColorScheme.get(count));
            CIPHER_CELL_ARRAYLIST.get(count).getMAIN_LABEL().setOpaque(false);
        }
    }

    // changes to color of the CipherCells to show a preview of the color scheme
    public void previewColors(){
        for(int count = 0; count < 5; count++){
            CIPHER_CELL_ARRAYLIST.get(count).getMAIN_LABEL().setBackground(previewColorScheme.get(count));
            CIPHER_CELL_ARRAYLIST.get(count).getMAIN_LABEL().setOpaque(true);
        }
        repaint();
    }

    // used when choosing different color scheme options
    // changes to values of the currentColorScheme ArrayList<Colors>
    public void changeCurrentColorScheme(int pScheme){
        if(pScheme == 0) currentColorScheme = DEFAULT_COLORS;
        else if(pScheme == 1) currentColorScheme = STARFIELD_COLORS;
        else if(pScheme == 2) currentColorScheme = GAMEBOY_COLORS;
        else if(pScheme == 3) currentColorScheme = VIRTUALBOY_COLORS;
    }

    // used when hovering over different color scheme options
    // changes to values of the previewColorScheme ArrayList<Colors>
    public void changePreviewColorScheme(int pScheme){
        // (?) if current and preview color schemes are the same, it doesn't function
        if(pScheme == 0) previewColorScheme = DEFAULT_COLORS;
        else if(pScheme == 1) previewColorScheme = STARFIELD_COLORS;
        else if(pScheme == 2) previewColorScheme = GAMEBOY_COLORS;
        else if(pScheme == 3) previewColorScheme = VIRTUALBOY_COLORS;
    }

    // needed after previewing color schemes
    // resets the CipherCells to its state before player previewed color schemes
    public void resumeColorProgress(int pProgress){
        for(int count = 0; count < pProgress; count++){
            CIPHER_CELL_ARRAYLIST.get(count).getMAIN_LABEL().setOpaque(true);
//            CIPHER_CELL_ARRAYLIST.get(count).getMAIN_LABEL().setOpaque(count < pProgress);
        }
        repaint();
    }
}
