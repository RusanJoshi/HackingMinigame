package security;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;

public class SecurityTerminal extends JPanel {
    ArrayList<BusStop> busStopArrayList = new ArrayList<>();
    private int stWidth;
    private int stHeight;
    private int busStopCount;

    public ArrayList<BusStop> getBusStopArrayList() {
        return busStopArrayList;
    }

    public SecurityTerminal(int pWidth, int pHeight, int pBusStopCount){
        this.setLayout(null);
        this.setSize(pWidth, pHeight);
        this.setBackground(Color.GRAY.darker());
        this.stWidth = pWidth;
        this.stHeight = pHeight;
        this.busStopCount = pBusStopCount;

        secondarySetupSecuritySetup();
    }

    private void secondarySetupSecuritySetup(){
        addBusStopsToSecurityTerminal();
    }

    private void addBusStopsToSecurityTerminal(){
        // determine size and space to allocate
        int gapCount = busStopCount + 1;
        int gapDistance = (stWidth-(busStopCount*new BusStop().getWidth()))/gapCount;
        int busStopPosition = gapDistance;

        // add to security terminal
        for(int count = 0; count < busStopCount; count++){
            busStopArrayList.add(new BusStop());
            busStopArrayList.get(count).setLocation(busStopPosition, -25);
            this.add(busStopArrayList.get(count));

            busStopPosition += busStopArrayList.get(count).getWidth() + gapDistance;
        }
    }

    public void toggleMouseListenerForAllBusStops(boolean pToggle){
        for(int busCount = 0; busCount < busStopArrayList.size(); busCount++){
            busStopArrayList.get(busCount).toggleMouseListener(pToggle);
        }
    }
}
