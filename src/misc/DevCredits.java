package misc;

import auxiliary.DialogueBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DevCredits extends JLabel implements MouseListener {
    OptionsPanel optionsPanelConnection;
    DialogueBox creditsDialogueBox;
    JButton exitCreditsButton = new JButton();

    public void setOptionsPanelConnection(OptionsPanel optionsPanelConnection) {
        this.optionsPanelConnection = optionsPanelConnection;
    }

    public DevCredits(){
        this.setSize(450,730);
        this.setOpaque(true);
        this.setBackground(Color.BLACK);
        this.setVisible(false);

        secondarySetupDevCredits();
    }

    private void secondarySetupDevCredits(){
        creditsDialogueBox = new DialogueBox(this.getWidth(), this.getHeight());
        creditsDialogueBox.customTags("<html>","</html>");
        creditsDialogueBox.customFontProperties("Bahnschrift", Font.BOLD, 12);
        creditsDialogueBox.customTextSpeed(5);
        creditsDialogueBox.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(creditsDialogueBox);

        exitCreditsButton.setSize(100,20);
        exitCreditsButton.setForeground(Color.BLACK);
        exitCreditsButton.setBackground(Color.ORANGE);
        exitCreditsButton.setText("Go back");
        exitCreditsButton.addMouseListener(this);
        this.add(exitCreditsButton);
    }

    @Override
    public void doLayout() {
        super.doLayout();

        exitCreditsButton.setLocation((this.getWidth()-exitCreditsButton.getWidth()),0);
        creditsDialogueBox.setLocation(0,20);
    }

    public void startCredits(){
        creditsDialogueBox.preTalkSetup(
                "Design: <font color='orange'>Rusan Joshi</font><br><br>" +
                        "Programming: <font color='orange'>Rusan Joshi</font><br><br>" +
                        "Artwork: <font color='orange'>Rusan Joshi</font><br><br>" +
                        "Sounds Effects:<br><br>" +
                        "Freesound, freesound.org-<br><br>" +
                        "Powerup/success.wav by <font color='orange'>GabrielAraujo</font><br><br>" +
                        "sf3-sfx-menu-select.wav by <font color='orange'>broumbroum</font><br><br>" +
                        "Evil laugh 04 - Gen 4.wav by <font color='orange'>Nanakisan</font> (Edited)" +
                        "https://freesound.org/people/Nanakisan/sounds/366169/<br><br>" +
                        "Level Up/Mission Complete 1 (Cyberpunk).wav by <font color='orange'>SilverIllusionist</font> (Edited)<br>" +
                        "https://freesound.org/people/SilverIllusionist/sounds/660871/<br><br>" +
                        "Level Up/Mission Complete (Cyberpunk Vibe) 2 by <font color='orange'>SilverIllusionist</font> (Edited)<br>" +
                        "https://freesound.org/people/SilverIllusionist/sounds/661240/<br><br>" +
                        "glitch_08_jeniusjelinek_(suonho).wav by <font color='orange'>suonho</font> (Edited)<br>" +
                        "https://freesound.org/people/suonho/sounds/3795/<br><br>" +
                        "Hard-drive spin-up/spin-down by <font color='orange'>SamsterBirdies</font><br><br>" +
                        "Single clicks Keyboard Sound by <font color='orange'>StrikeWhistler</font><br><br>" +
                        "<font color='orange'>ZapSplat</font>, www.zapsplat.com-<br><br>" +
                        "Video game console, Nintendo Wii, electronic whirr, click noise 1<br>" +
                        "https://www.zapsplat.com/music/video-game-console-nintendo-wii-electronic-whirr-click-noise-1/<br><br>" +
                        "Video game console, Nintendo Wii, electronic whirr, click noise 2<br>" +
                        "https://www.zapsplat.com/music/video-game-console-nintendo-wii-electronic-whirr-click-noise-2/<br><br>" +
                        "Person typing on a computer keyboard, single spacebar press<br>" +
                        "https://www.zapsplat.com/music/person-typing-on-a-computer-keyboard-single-spacebar-press/<br><br>");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent() == exitCreditsButton){
            creditsDialogueBox.getDialogueBoxTimer().stop();
            this.setVisible(false);
            optionsPanelConnection.getRestartButton().setVisible(true);
            optionsPanelConnection.getQuitButton().setVisible(true);
            optionsPanelConnection.getCreditsButton().setVisible(true);
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
}
