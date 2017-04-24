package Vista;

import controller.RegisterController;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de registro del cliente
 * Created by jorti on 31/03/2017.
 */
public class GameView extends JFrame{

    private JPanel[][]caselles;
    private JPanel centre;

    public GameView(){
        centre = new JPanel(new GridLayout(25,10));
        centre.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        getContentPane().add(centre);
        setSize(550,1260);
        caselles = new JPanel[25][10];

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel p = new JPanel();
                p.setBackground(Color.yellow);
                caselles[i][j] = p;
                caselles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                centre.add(p);
            }
        }
        getContentPane().add(centre);

    }

    /**
     *
     * @param controller
     */
    public void registerController(RegisterController controller){



    }


}
