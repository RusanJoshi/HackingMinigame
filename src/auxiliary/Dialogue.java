package auxiliary;

/*
Late into the program's development, I experimented with a class that primarily
holds dialogue. Strings are stored in an ArrayList that can be called by other classes.
Its only use in this project is limited to the OptionsPanel.
 */
public class Dialogue {
    private final String[] SETTINGS_EXPLANATIONS = {
            "Changes the difficulty. <br><font color='red'>(Not Available)</font>",
            "Toggles the Detection Meter.",
            "Changes the Cipher color scheme. <br>* Default<br>* Starfield<br>* Gameboy<br>* Virtual Boy",
            "Restarts the game.",
            "Quits the game.",
            "View developer credits."};

    public String getSETTINGS_EXPLANATIONS(int pIndex) {
        return SETTINGS_EXPLANATIONS[pIndex];
    }

    public Dialogue(){}
}
