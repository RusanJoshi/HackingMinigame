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
public class BusChannelTop extends JLabel {
    ArrayList<CustomLine> port0BusLines = new ArrayList<>();
    ArrayList<CustomLine> port1BusLines = new ArrayList<>();
    CustomLine port2BusLines;
    ArrayList<CustomLine> port3BusLines = new ArrayList<>();
    ArrayList<CustomLine> port4BusLines = new ArrayList<>();

    Port port0 = new Port();
    Port port1 = new Port();
    Port port2 = new Port();
    Port port3 = new Port();
    Port port4 = new Port();
    Port cipherPort = new Port();

    int lineThickness = 3;
    int portLineTraversal = 2; // default starting port

    public BusChannelTop(){
        this.setSize(305,132);
//        this.setOpaque(true);
        this.setBackground(Color.GRAY.darker());

        secondarySetupBusLaneTop();
    }

    private void secondarySetupBusLaneTop(){
        this.add(port0);
        this.add(port1);
        this.add(port2);
        this.add(port3);
        this.add(port4);
        this.add(cipherPort);

        // Instantiating CustomLines into port specific ArrayLists
        port2BusLines = new CustomLine(); // middle bus line
        for(int count = 0; count < 3; count++){
            port0BusLines.add(new CustomLine());
            port1BusLines.add(new CustomLine());
            port3BusLines.add(new CustomLine());
            port4BusLines.add(new CustomLine());
        }
    }

    @Override
    public void doLayout(){
        super.doLayout();

        port0.setLocation(((this.getWidth()/5)/2)-(port1.getWidth()/2) + 3,0);
        port1.setLocation((port0.getX()+port0.getWidth()+25),0);
        port2.setLocation((this.getWidth()/2)-(port0.getWidth()/2),0);
        port3.setLocation((port2.getX()+port2.getWidth())+25,0);
        port4.setLocation((port3.getX()+port3.getWidth())+25,0);
        cipherPort.setSize(45,10);
        cipherPort.setLocation((this.getWidth()/2)-(cipherPort.getWidth()/2),(this.getHeight()-cipherPort.getHeight()));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        RenderingHints renderingHints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHints(renderingHints);

        graphics2D.setStroke(new BasicStroke(lineThickness));
        graphics2D.setPaint(Color.GRAY);

        // Initializing port specific CustomLine configurations
        port2BusLines.draw(g,this.getWidth()/2,this.getHeight(),this.getWidth()/2,0);
        port0BusLineConfiguration(g);
        port1BusLineConfiguration(g);
        port3BusLineConfiguration(g);
        port4BusLineConfiguration(g);

        // Recoloring port labels
        port2.setBackground(Color.WHITE.darker());
        port0.setBackground(Color.WHITE.darker());
        port1.setBackground(Color.WHITE.darker());
        port3.setBackground(Color.WHITE.darker());
        port4.setBackground(Color.WHITE.darker());

        if(portLineTraversal == 2){
            graphics2D.setPaint(Color.ORANGE);
            port2BusLines.draw(g,this.getWidth()/2,this.getHeight(),this.getWidth()/2,0);
            graphics2D.setPaint(Color.GRAY);
            port2.setBackground(Color.ORANGE);
        }
        else if(portLineTraversal == 0){
            port0.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port0BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 1){
            port1.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port1BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 3){
            port3.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port3BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
        else if(portLineTraversal == 4){
            port4.setBackground(Color.ORANGE);
            graphics2D.setPaint(Color.ORANGE);
            port4BusLineConfiguration(g);
            graphics2D.setPaint(Color.GRAY);
        }
    }

    public void newBusLineHighlight(int pPortLine){
        portLineTraversal = pPortLine;
        repaint();
    }

    // EVERYTHING BELOW IS HAND POSITIONED BUS LINE CONFIGURATIONS
    private void port0BusLineConfiguration(Graphics g){
        // 37 ~ 112/3 (space in between matrix and cipher ports)/(segments)
        port0BusLines.get(0).draw(g,
                port2BusLines.getX1()-21,
                cipherPort.getY(),
                port2BusLines.getX2()-21,
                cipherPort.getY()-37
        );
        port0BusLines.get(2).draw(g,
                port0.getX()+(port0.getWidth()/2),
                port0.getHeight(),
                port0.getX()+(port0.getWidth()/2),
                port0.getHeight()+37
        );
        port0BusLines.get(1).draw(g,
                port0BusLines.get(0).getX2()-1,
                port0BusLines.get(0).getY2()-1,
                port0BusLines.get(2).getX2()+1,
                port0BusLines.get(2).getY2()+1
        );
    }
    private void port1BusLineConfiguration(Graphics g){
        port1BusLines.get(0).draw(g,
                port2BusLines.getX1()-11,
                cipherPort.getY(),
                port2BusLines.getX2()-11,
                cipherPort.getY()-47
        );
        port1BusLines.get(2).draw(g,
                port1.getX()+(port1.getWidth()/2),
                port1.getHeight(),
                port1.getX()+(port1.getWidth()/2),
                port1.getHeight()+47
        );
        port1BusLines.get(1).draw(g,
                port1BusLines.get(0).getX2()-1,
                port1BusLines.get(0).getY2()-1,
                port1BusLines.get(2).getX2()+1,
                port1BusLines.get(2).getY2()+1
        );
    }
    private void port3BusLineConfiguration(Graphics g){
        port3BusLines.get(0).draw(g,
                port2BusLines.getX1()+11,
                cipherPort.getY(),
                port2BusLines.getX2()+11,
                cipherPort.getY()-47
        );
        port3BusLines.get(2).draw(g,
                port3.getX()+(port3.getWidth()/2),
                port3.getHeight(),
                port3.getX()+(port3.getWidth()/2),
                port3.getHeight()+47
        );
        port3BusLines.get(1).draw(g,
                port3BusLines.get(0).getX2(),
                port3BusLines.get(0).getY2()-1,
                port3BusLines.get(2).getX2(),
                port3BusLines.get(2).getY2()+1
        );
    }
    private void port4BusLineConfiguration(Graphics g){
        port4BusLines.get(0).draw(g,
                port2BusLines.getX1()+21,
                cipherPort.getY(),
                port2BusLines.getX2()+21,
                cipherPort.getY()-37
        );
        port4BusLines.get(2).draw(g,
                port4.getX()+(port4.getWidth()/2),
                port4.getHeight(),
                port4.getX()+(port4.getWidth()/2),
                port4.getHeight()+37
        );
        port4BusLines.get(1).draw(g,
                port4BusLines.get(0).getX2(),
                port4BusLines.get(0).getY2()-1,
                port4BusLines.get(2).getX2(),
                port4BusLines.get(2).getY2()+1
        );
    }
}
