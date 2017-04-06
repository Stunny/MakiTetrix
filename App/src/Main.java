import Vista.LoginView;
import controller.LoginController;

import javax.swing.*;

/**
 * Clase principal de la aplicacion cliente
 * Created by jorti on 30/03/2017.
 */

public class Main {
    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable (){

            @Override
            public void run(){
                LoginView vis = new LoginView();
                LoginController lc = LoginController.getInstance(vis);

                vis.registerController(lc);
                vis.setVisible(true);
            }

        });
    }
}
