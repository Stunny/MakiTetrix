package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by angel on 12/07/2017.
 */
public class PointsGraph extends JPanel{
    private final int PAD = 70;
    private double maxValue;
    private int[] data;
    private String[] userNames;

    public PointsGraph(double maxValue, int[] data, String[] userNames) {
        this.maxValue = maxValue;
        this.data = data;
        this.userNames = userNames;
    }

    /**
     * Displays graph
     * @param g Abstract class necesary for all graphs
     */
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

        for(int j = 0; j < data.length; j++) {
            //g2.setPaint(Color.red);
            int x = x0 + (int)(xScale * (j+1));
            int y = y0 - (int)(yScale * data[j]);
            //g2.fillOval(x-2, y-2, 4, 4);

            g2.setColor(Color.GREEN);
            g2.fillRect(x, y, 30, h - y - PAD);
        }

        g2.setColor(Color.BLACK);

        g2.drawString(String.valueOf((int) maxValue), 10, PAD);
        g2.drawString(String.valueOf((int) (maxValue  * 0.85)), 10, (int) ((yScale/7)* 1 * maxValue + PAD));
        g2.drawString(String.valueOf((int) (maxValue  * 0.7)), 10, (int) ((yScale/7)* 2 * maxValue + PAD));
        g2.drawString(String.valueOf((int) (maxValue * 0.65)), 10, (int) ((yScale/7)* 3 * maxValue + PAD));
        g2.drawString(String.valueOf((int) (maxValue * 0.5)), 10, (int) ((yScale/7)* 4 * maxValue + PAD));
        g2.drawString(String.valueOf((int) (maxValue * 0.35)), 10, (int) ((yScale/7)* 5 * maxValue + PAD));
        g2.drawString(String.valueOf((int) (maxValue * 0.2)), 10, (int) ((yScale/7)* 6 * maxValue + PAD));
        g2.drawString("0", 10, (int) ((yScale/6)* 7 * maxValue + PAD));

        for (int i = 0; i < data.length; i++){
            if (userNames[i] == null){
                break;
            }
            Font font = new Font(null, Font.PLAIN, 10);
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(45), 0, 0);
            Font rotatedFont = font.deriveFont(affineTransform);
            g2.setFont(rotatedFont);
            g2.drawString(userNames[i], x0 + (int)(xScale * (i+1)), 15 + y0);
        }
    }

    public void PointsGraph() {
        JFrame f = new JFrame();
        f.getContentPane().add(new PointsGraph(maxValue, data, userNames));
        f.setSize(600,600);
        f.setLocation(200,200);
        f.setVisible(true);
        f.setTitle("Top 10 game scores");
    }
}
