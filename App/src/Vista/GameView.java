package Vista;

import controller.RegisterController;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de registro del cliente
 * Created by jorti on 31/03/2017.
 */
public class GameView extends JFrame{

    private JLabel[][]caselles;
    private JPanel centre;

    public GameView(){
        centre = new JPanel(new GridLayout(25,10));
        centre.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        getContentPane().add(centre);
        setSize(550,460);
         caselles = new JPanel[][]();

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 10; i++) {
                JLabel p = new JLabel();
                caselles[i][j] =p;
                caselles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }

    }

    /**
     *
     * @param controller
     */
    public void registerController(RegisterController controller){

    }


}
