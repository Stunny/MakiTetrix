package View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by angel on 12/07/2017.
 */
public class PointsGraph extends JPanel{
    int[] data = { 25, 60, 42, 75, 30, 30 ,30 ,30 ,30, 30 };
    final int PAD = 50;
    private double maxValue;

    public PointsGraph(double maxValue) {
        this.maxValue = maxValue;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        g2.drawLine(PAD, PAD, PAD, h-PAD);
        g2.drawLine(PAD, h-PAD, w-PAD, h-PAD);
        double xScale = (w - 2*PAD)/(data.length + 1);
        double yScale = (h - 2*PAD)/maxValue;
        // The origin location.
        int x0 = PAD;
        int y0 = h-PAD;
        g2.setPaint(Color.red);
        for(int j = 0; j < data.length; j++) {
            int x = x0 + (int)(xScale * (j+1));
            int y = y0 - (int)(yScale * data[j]);
            g2.fillOval(x-2, y-2, 4, 4);
        }

        g2.setColor(Color.GREEN);
        g2.fillRect(x0 + (int)(xScale * 1), y0 - 1, 30, 10);

        g2.setColor(Color.BLACK);
        g2.drawString("user1", x0 + (int)(xScale * 1), 15 + y0);
        g2.drawString("user2", x0 + (int)(xScale * 2), 15 + y0);
        g2.drawString("user3", x0 + (int)(xScale * 3), 15 + y0);
        g2.drawString("user4", x0 + (int)(xScale * 4), 15 + y0);
        g2.drawString("user5", x0 + (int)(xScale * 5), 15 + y0);
        g2.drawString("user6", x0 + (int)(xScale * 6), 15 + y0);
        g2.drawString("user7", x0 + (int)(xScale * 7), 15 + y0);
        g2.drawString("user8", x0 + (int)(xScale * 8), 15 + y0);
        g2.drawString("user9", x0 + (int)(xScale * 9), 15 + y0);
        g2.drawString("user10", x0 + (int)(xScale * 10), 15 + y0);

        g2.drawString(String.valueOf((int) maxValue), 10, (int) ((yScale/6)* maxValue + PAD));
        g2.drawString(String.valueOf((int) (maxValue  * 0.8)), 10, (int) ((yScale/6)* 2 * maxValue + PAD));
        g2.drawString(String.valueOf((int) (maxValue * 0.6)), 10, (int) ((yScale/6)* 3 * maxValue + PAD));
        g2.drawString(String.valueOf((int) (maxValue * 0.4)), 10, (int) ((yScale/6)* 4 * maxValue + PAD));
        g2.drawString(String.valueOf((int) (maxValue * 0.2)), 10, (int) ((yScale/6)* 5 * maxValue + PAD));
        g2.drawString("0", 10, (int) ((yScale/6)* 6 * maxValue + PAD));

    }

    public void PointsGraph() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new PointsGraph(maxValue));
        f.setSize(600,600);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}
