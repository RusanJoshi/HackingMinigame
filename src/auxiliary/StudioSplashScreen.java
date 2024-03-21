package auxiliary;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

// Studio splashscreen displayed at the very beginning of the game.
public class StudioSplashScreen extends JLabel {
    ImageIcon splashIcon = new ImageIcon(getClass().getClassLoader().getResource("BTM_Text_Invert_WithHead.jpg"));
    JLabel studioSplashImageLabel = new JLabel();
    FlashingText flashingText;

    public FlashingText getFlashingText() {
        return flashingText;
    }

    public StudioSplashScreen(){
        this.setSize(450,700);
        this.setOpaque(true);
        this.setBackground(Color.BLACK);

        secondarySetupStudioSplashScreen();
    }

    private void secondarySetupStudioSplashScreen(){
        studioSplashImageLabel.setSize(450,253);
        studioSplashImageLabel.setOpaque(true);
        studioSplashImageLabel.setBackground(Color.BLACK);
        studioSplashImageLabel.setIcon(imageResize(splashIcon, studioSplashImageLabel.getWidth(), studioSplashImageLabel.getHeight()));
        this.add(studioSplashImageLabel);

        flashingText = new FlashingText("click to continue...");
        this.add(flashingText);
    }

    @Override
    public void doLayout() {
        super.doLayout();

        studioSplashImageLabel.setLocation(0,(this.getHeight()/2)-(studioSplashImageLabel.getHeight()/2));
        flashingText.setLocation((this.getWidth()/2)-(flashingText.getWidth()/2),this.getHeight()-flashingText.getHeight());
    }

    // resizes image passed into the parameter with specified dimensions
    public ImageIcon imageResize(ImageIcon pIcon, int pWidth, int pHeight){
        Image image = pIcon.getImage();
        Image newImage = image.getScaledInstance(pWidth,pHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }
}
