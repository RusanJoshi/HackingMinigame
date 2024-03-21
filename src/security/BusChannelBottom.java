package security;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JLabel;
import auxiliary.CustomLine;

// Lines are drawn connecting the SecurityTerminal to the Cipher
// Lines are hand placed.
public class BusChannelBottom extends JLabel {
    SecurityTerminal securityTerminalConnection;
    ArrayList<CustomLine> port0BusLines = new ArrayList<>();
    ArrayList<CustomLine> port1BusLines = new ArrayList<>();
    ArrayList<CustomLine> port2BusLines = new ArrayList<>();
    ArrayList<CustomLine> port3BusLines = new ArrayList<>();
    ArrayList<CustomLine> port4BusLines = new ArrayList<>();
    ArrayList<CustomLine> port5BusLines = new ArrayList<>();
    ArrayList<CustomLine> port6BusLines = new ArrayList<>();
    ArrayList<CustomLine> port7BusLines = new ArrayList<>();
    ArrayList<CustomLine> port8BusLines = new ArrayList<>();
    ArrayList<CustomLine> port9BusLines = new ArrayList<>();
    private ArrayList<ArrayList> collectionPortBusLines = new ArrayList<>();

    Graphics2D graphics2D;

    Port cipherPort = new Port();
    Port port0 = new Port();
    Port port1 = new Port();
    Port port2 = new Port();
    Port port3 = new Port();
    Port port4 = new Port();
    Port port5 = new Port();
    Port port6 = new Port();
    Port port7 = new Port();
    Port port8 = new Port();
    Port port9 = new Port();

    int adjustedBottom = 122; // this.height - port.height
    int lineThickness = 3;
    int spacing = 10;
    int portLineTraversal = 4; // default starting port

    public void setSecurityTerminalConnection(SecurityTerminal securityTerminalConnection) {
        this.securityTerminalConnection = securityTerminalConnection;
    }

    public BusChannelBottom(){
        this.setSize(450,132);
        this.setBackground(Color.GRAY.darker().darker());
        secondarySetupBusLaneBottom();
    }

    private void secondarySetupBusLaneBottom(){
        this.add(cipherPort);
        this.add(port0);
        this.add(port1);
        this.add(port2);
        this.add(port3);
        this.add(port4);
        this.add(port5);
        this.add(port6);
        this.add(port7);
        this.add(port8);
        this.add(port9);

        // instantiating CustomLines into port specific ArrayLists
        for(int count = 0; count < 3; count++){
            port0BusLines.add(new CustomLine());
            port2BusLines.add(new CustomLine());
            port1BusLines.add(new CustomLine());
            port3BusLines.add(new CustomLine());
            port4BusLines.add(new CustomLine());
            port5BusLines.add(new CustomLine());
            port6BusLines.add(new CustomLine());
            port7BusLines.add(new CustomLine());
            port8BusLines.add(new CustomLine());
            port9BusLines.add(new CustomLine());
        }
        port0BusLines.add(new CustomLine());
        port1BusLines.add(new CustomLine());
        port8BusLines.add(new CustomLine());
        port9BusLines.add(new CustomLine());

        addAllPortBusLinesArrayListToCollectionArrayList();
    }

    // adds all port ArrayLists into a collection ArrayList that holds all of them
    private void addAllPortBusLinesArrayListToCollectionArrayList(){
        collectionPortBusLines.add(port0BusLines);
        collectionPortBusLines.add(port1BusLines);
        collectionPortBusLines.add(port2BusLines);
        collectionPortBusLines.add(port3BusLines);
        collectionPortBusLines.add(port4BusLines);
        collectionPortBusLines.add(port5BusLines);
        collectionPortBusLines.add(port6BusLines);
        collectionPortBusLines.add(port7BusLines);
        collectionPortBusLines.add(port8BusLines);
        collectionPortBusLines.add(port9BusLines);
    }

    @Override
    public void doLayout(){
        super.doLayout();

        cipherPort.setSize(92,10);
        cipherPort.setLocation((this.getWidth()/2)-(cipherPort.getWidth()/2),0);

        port0.setLocation(securityTerminalConnection.getBusStopArrayList().get(0).getX(),adjustedBottom);
        port1.setLocation(securityTerminalConnection.getBusStopArrayList().get(1).getX(),adjustedBottom);
        port2.setLocation(securityTerminalConnection.getBusStopArrayList().get(2).getX(),adjustedBottom);
        port3.setLocation(securityTerminalConnection.getBusStopArrayList().get(3).getX(),adjustedBottom);
        port4.setLocation(securityTerminalConnection.getBusStopArrayList().get(4).getX(),adjustedBottom);
        port5.setLocation(securityTerminalConnection.getBusStopArrayList().get(5).getX(),adjustedBottom);
        port6.setLocation(securityTerminalConnection.getBusStopArrayList().get(6).getX(),adjustedBottom);
        port7.setLocation(securityTerminalConnection.getBusStopArrayList().get(7).getX(),adjustedBottom);
        port8.setLocation(securityTerminalConnection.getBusStopArrayList().get(8).getX(),adjustedBottom);
        port9.setLocation(securityTerminalConnection.getBusStopArrayList().get(9).getX(),adjustedBottom);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        RenderingHints renderingHints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHints(renderingHints);

        graphics2D.setStroke(new BasicStroke(lineThickness));
        graphics2D.setPaint(Color.GRAY);

        // Initializing port specific CustomLine configurations
        port4BusLineConfiguration(g);
        port3BusLineConfiguration(g);
        port2BusLineConfiguration(g);
        port1BusLineConfiguration(g);
        port0BusLineConfiguration(g);

        port5BusLineConfiguration(g);
        port6BusLineConfiguration(g);
        port7BusLineConfiguration(g);
        port8BusLineConfiguration(g);
        port9BusLineConfiguration(g);

        // Recoloring port labels
        port4.setBackground(Color.WHITE.darker());
        port3.setBackground(Color.WHITE.darker());
        port2.setBackground(Color.WHITE.darker());
        port1.setBackground(Color.WHITE.darker());
        port0.setBackground(Color.WHITE.darker());

        port5.setBackground(Color.WHITE.darker());
        port6.setBackground(Color.WHITE.darker());
        port7.setBackground(Color.WHITE.darker());
        port8.setBackground(Color.WHITE.darker());
        port9.setBackground(Color.WHITE.darker());

        // I know this sucks. I lack the knowledge to do this more efficiently.
        if(portLineTraversal == 4){
            port4.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port4BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 3){
            port3.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port3BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 2){
            port2.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port2BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 1){
            port1.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port1BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 0){
            port0.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port0BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }

        if(portLineTraversal == 5){
            port5.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port5BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 6){
            port6.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port6BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 7){
            port7.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port7BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 8){
            port8.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port8BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 9){
            port9.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port9BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
    }

    // highlights the newly selected Bus Line
    public void newBusLineHighlight(int pPortLine){
        portLineTraversal = pPortLine;
        repaint();
    }

    // EVERYTHING BELOW IS HAND POSITIONED BUS LINE CONFIGURATIONS
    private void port0BusLineConfiguration(Graphics g){
        port0BusLines.get(0).draw(g,
                cipherPort.getX()+1,
                cipherPort.getHeight(),
                cipherPort.getX()+1,
                cipherPort.getHeight()+11
                );
        port0BusLines.get(2).draw(g,
                port0.getX() + (port0.getWidth()/2),
                port0.getY(),
                port0.getX() + (port0.getWidth()/2),
                ((port1BusLines.get(1).getY1()+port1BusLines.get(1).getY2())/2)+11
        );
        port0BusLines.get(3).draw(g,
                port0BusLines.get(2).getX2(),
                port0BusLines.get(2).getY2(),
                port1BusLines.get(3).getX2()-5,
                port0BusLines.get(2).getY2()
        );
        port0BusLines.get(1).draw(g,
                port0BusLines.get(0).getX2(),
                port0BusLines.get(0).getY2(),
                port0BusLines.get(3).getX2(),
                port0BusLines.get(3).getY2()
        );
    }
    private void port1BusLineConfiguration(Graphics g){
        port1BusLines.get(0).draw(g,
                cipherPort.getX()+(spacing),cipherPort.getHeight(),
                cipherPort.getX()+(spacing),cipherPort.getHeight()+15
        );
        port1BusLines.get(2).draw(g,
                port1.getX() + (port1.getWidth()/2), port1.getY(),
                port1.getX() + (port1.getWidth()/2),
                (port2BusLines.get(1).getY1()+port2BusLines.get(1).getY2())/2
        );
        port1BusLines.get(3).draw(g,
                port1BusLines.get(2).getX2(), port1BusLines.get(2).getY2(),
                port1BusLines.get(2).getX2()+71, port1BusLines.get(2).getY2()
                );
        port1BusLines.get(1).draw(g,
                port1BusLines.get(0).getX2(), port1BusLines.get(0).getY2(),
                port1BusLines.get(3).getX2(), port1BusLines.get(3).getY2()
        );
    }
    private void port2BusLineConfiguration(Graphics g){
        port2BusLines.get(0).draw(g,
                cipherPort.getX()+(spacing*2),
                cipherPort.getHeight(),
                cipherPort.getX()+(spacing*2),
                cipherPort.getHeight()+17
        );
        port2BusLines.get(2).draw(g,
                port2.getX() + (port2.getWidth()/2),
                port2.getY(),
                port2.getX() + (port2.getWidth()/2),
                port2.getY()-22
        );
        port2BusLines.get(1).draw(g,
                port2BusLines.get(0).getX2(),
                port2BusLines.get(0).getY2(),
                port2BusLines.get(2).getX2(),
                port2BusLines.get(2).getY2()
        );
    }
    private void port3BusLineConfiguration(Graphics g){
        port3BusLines.get(0).draw(g,
                cipherPort.getX()+(spacing*3),
                cipherPort.getHeight(),
                cipherPort.getX()+(spacing*3),
                cipherPort.getHeight()+37
        );
        port3BusLines.get(2).draw(g,
                port3.getX() + (port3.getWidth()/2),
                port3.getY(),
                port3.getX() + (port3.getWidth()/2),
                port3.getY()-22
        );
        port3BusLines.get(1).draw(g,
                port3BusLines.get(0).getX2(),
                port3BusLines.get(0).getY2(),
                port3BusLines.get(2).getX2(),
                port3BusLines.get(2).getY2()
        );
    }
    private void port4BusLineConfiguration(Graphics g){
        port4BusLines.get(0).draw(g,
                cipherPort.getX()+(spacing*4),
                cipherPort.getHeight(),
                cipherPort.getX()+(spacing*4),
                cipherPort.getHeight()+67
        );
        port4BusLines.get(2).draw(g,
                port4.getX() + (port4.getWidth()/2),
                port4.getY(),
                port4.getX() + (port4.getWidth()/2),
                port4.getY()-22
        );
        port4BusLines.get(1).draw(g,
                port4BusLines.get(0).getX2(),
                port4BusLines.get(0).getY2(),
                port4BusLines.get(2).getX2(),
                port4BusLines.get(2).getY2()
                );
    }
    private void port5BusLineConfiguration(Graphics g){
        port5BusLines.get(0).draw(g,
                cipherPort.getX()+(spacing*5),
                cipherPort.getHeight(),
                cipherPort.getX()+(spacing*5),
                cipherPort.getHeight()+67
        );
        port5BusLines.get(2).draw(g,
                port5.getX() + (port5.getWidth()/2),
                port5.getY(),
                port5.getX() + (port5.getWidth()/2),
                port5.getY()-22
        );
        port5BusLines.get(1).draw(g,
                port5BusLines.get(0).getX2(),
                port5BusLines.get(0).getY2(),
                port5BusLines.get(2).getX2(),
                port5BusLines.get(2).getY2()
        );
    }
    private void port6BusLineConfiguration(Graphics g){
        port6BusLines.get(0).draw(g,
                cipherPort.getX()+(spacing*6),
                cipherPort.getHeight(),
                cipherPort.getX()+(spacing*6),
                cipherPort.getHeight()+37
        );
        port6BusLines.get(2).draw(g,
                port6.getX() + (port6.getWidth()/2),
                port6.getY(),
                port6.getX() + (port6.getWidth()/2),
                port6.getY()-22
        );
        port6BusLines.get(1).draw(g,
                port6BusLines.get(0).getX2(),
                port6BusLines.get(0).getY2(),
                port6BusLines.get(2).getX2(),
                port6BusLines.get(2).getY2()
        );
    }
    private void port7BusLineConfiguration(Graphics g){
        port7BusLines.get(0).draw(g,
                cipherPort.getX()+(spacing*7),
                cipherPort.getHeight(),
                cipherPort.getX()+(spacing*7),
                cipherPort.getHeight()+17
        );
        port7BusLines.get(2).draw(g,
                port7.getX() + (port7.getWidth()/2),
                port7.getY(),
                port7.getX() + (port7.getWidth()/2),
                port7.getY()-22
        );
        port7BusLines.get(1).draw(g,
                port7BusLines.get(0).getX2(),
                port7BusLines.get(0).getY2(),
                port7BusLines.get(2).getX2(),
                port7BusLines.get(2).getY2()
        );
    }
    private void port8BusLineConfiguration(Graphics g){

        port8BusLines.get(0).draw(g,
                cipherPort.getX()+(spacing*8),cipherPort.getHeight(),
                cipherPort.getX()+(spacing*8),cipherPort.getHeight()+15
        );
        port8BusLines.get(2).draw(g,
                port8.getX() + (port8.getWidth()/2), port8.getY(),
                port8.getX() + (port8.getWidth()/2),
                (port7BusLines.get(1).getY1()+port7BusLines.get(1).getY2())/2
        );
        port8BusLines.get(3).draw(g,
                port8BusLines.get(2).getX2(), port8BusLines.get(2).getY2(),
                port8BusLines.get(2).getX2()-71, port8BusLines.get(2).getY2()
        );
        port8BusLines.get(1).draw(g,
                port8BusLines.get(0).getX2(), port8BusLines.get(0).getY2(),
                port8BusLines.get(3).getX2(), port8BusLines.get(3).getY2()
        );
    }
    private void port9BusLineConfiguration(Graphics g){
        port9BusLines.get(0).draw(g,
                cipherPort.getX()+(spacing*9),
                cipherPort.getHeight(),
                cipherPort.getX()+(spacing*9),
                cipherPort.getHeight()+11
        );
        port9BusLines.get(2).draw(g,
                port9.getX() + (port9.getWidth()/2),
                port9.getY(),
                port9.getX() + (port9.getWidth()/2),
                ((port8BusLines.get(1).getY1()+port8BusLines.get(1).getY2())/2)+11
        );
        port9BusLines.get(3).draw(g,
                port9BusLines.get(2).getX2(),
                port9BusLines.get(2).getY2(),
                port8BusLines.get(3).getX2()+5,
                port9BusLines.get(2).getY2()
        );
        port9BusLines.get(1).draw(g,
                port9BusLines.get(0).getX2(),
                port9BusLines.get(0).getY2(),
                port9BusLines.get(3).getX2(),
                port9BusLines.get(3).getY2()
        );
    }
}
