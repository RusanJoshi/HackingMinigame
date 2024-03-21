package misc;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DetectionMeter extends JPanel {
    ArrayList<JLabel> securityAlertArrayList = new ArrayList<>();
    final int SPACER = 5; // int used to space the labels when setting their locations

    Border border = BorderFactory.createTitledBorder(null,"dM",0,0,null,Color.BLACK);

    public ArrayList<JLabel> getSecurityAlertArrayList() {
        return securityAlertArrayList;
    }

    public DetectionMeter(){
        this.setLayout(null);
        this.setSize(30,130);
        this.setBackground(Color.WHITE);
        this.setBorder(border);

        secondarySetupDetectionMeter();
    }

    private void secondarySetupDetectionMeter(){
        createSecurityAlertLevels();

        securityAlertArrayList.get(0).setLocation(SPACER, SPACER);
        securityAlertArrayList.get(1).setLocation(SPACER, (securityAlertArrayList.get(0).getY()+ securityAlertArrayList.get(0).getHeight())+ SPACER);
        securityAlertArrayList.get(2).setLocation(SPACER, (securityAlertArrayList.get(1).getY()+ securityAlertArrayList.get(1).getHeight())+ SPACER);
        securityAlertArrayList.get(3).setLocation(SPACER, (securityAlertArrayList.get(2).getY()+ securityAlertArrayList.get(2).getHeight())+ SPACER);
        securityAlertArrayList.get(4).setLocation(SPACER, (securityAlertArrayList.get(3).getY()+ securityAlertArrayList.get(3).getHeight())+ SPACER);
    }

    // instantiates, initializes, and modifies JLabels
    // the JLabels are added to securityAlertArrayList<JLabel>
    private void createSecurityAlertLevels(){
        for(int count = 0; count < 5; count++){
            JLabel tempLabel = new JLabel();
            tempLabel.setSize(20,20);
            tempLabel.setBackground(Color.BLACK);
            tempLabel.setOpaque(true);
            securityAlertArrayList.add(tempLabel);
            this.add(securityAlertArrayList.get(count));
        }
    }

    // called by the Matrix when restarting the game
    // resets the label's color to initial black value (visual reset)
    public void resetSecurityDetectionMeter(){
        for(int count = 0; count < 5; count++){
            securityAlertArrayList.get(count).setBackground(Color.BLACK);
        }
    }

}
