package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by angel on 12/07/2017.
 */
public class ViewersGraph extends JPanel{
    final int PAD = 70;
    private double maxViewers;
    private String [] userNames;
    private int[] data;

    public ViewersGraph(double maxValue, int[] data,  String[] userNames) {
        this.maxViewers = maxValue;
        this.userNames = userNames;
        this.data = data;
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
        double yScale = (h - 2*PAD)/maxViewers;
        // The origin location.
        int x0 = PAD;
        int y0 = h-PAD;
        g2.setPaint(Color.red);

        for(int j = 0; j < data.length; j++) {
            //g2.setPaint(Color.red);
            int x = x0 + (int)(xScale * (j+1));
            int y = y0 - (int)(yScale * data[j]);
            //g2.fillOval(x-2, y-2, 4, 4);

            g2.setColor(Color.GREEN);
            g2.fillRect(x, y, 30, h - y - PAD);
        }

        g2.setColor(Color.BLACK);

        g2.drawString(String.valueOf((int) maxViewers), 10, PAD);
        g2.drawString(String.valueOf((int) (maxViewers  * 0.85)), 10, (int) ((yScale/7)* 1 * maxViewers + PAD));
        g2.drawString(String.valueOf((int) (maxViewers  * 0.7)), 10, (int) ((yScale/7)* 2 * maxViewers + PAD));
        g2.drawString(String.valueOf((int) (maxViewers * 0.65)), 10, (int) ((yScale/7)* 3 * maxViewers + PAD));
        g2.drawString(String.valueOf((int) (maxViewers * 0.5)), 10, (int) ((yScale/7)* 4 * maxViewers + PAD));
        g2.drawString(String.valueOf((int) (maxViewers * 0.35)), 10, (int) ((yScale/7)* 5 * maxViewers + PAD));
        g2.drawString(String.valueOf((int) (maxViewers * 0.2)), 10, (int) ((yScale/7)* 6 * maxViewers + PAD));
        g2.drawString("0", 10, (int) ((yScale/6)* 7 * maxViewers + PAD));

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

    public void ViewersGraph() {
        JFrame f = new JFrame();
        f.getContentPane().add(new ViewersGraph(maxViewers, data, userNames));
        f.setSize(600,600);
        f.setLocation(800,200);
        f.setVisible(true);
        f.setTitle("Top 5 viewer's graph");
    }
}
