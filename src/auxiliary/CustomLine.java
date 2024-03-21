package auxiliary;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class CustomLine{
    Stroke stroke = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
    private int x1, y1, x2, y2;

    public int getX1() {
        return x1;
    }
    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }
    public int getY2() {
        return y2;
    }

    public CustomLine() {}

    public void saveCoordinates(int pX1, int pY1, int pX2, int pY2){
        this.x1 = pX1;
        this.y1 = pY1;
        this.x2 = pX2;
        this.y2 = pY2;
    }

    public void draw(Graphics g, int pX1, int pY1, int pX2, int pY2) {
        saveCoordinates(pX1, pY1, pX2, pY2);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(stroke);
        g2d.drawLine(pX1, pY1, pX2, pY2);
    }
}
