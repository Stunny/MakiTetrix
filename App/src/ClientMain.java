import Vista.LoginView;
import controller.LoginController;
import network.Conexio;

import javax.swing.*;

/*
 * Clase principal de la aplicacion cliente
 * Created by jorti on 30/03/2017.
 */

public class ClientMain {
    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable (){

            @Override
            public void run(){
                LoginView loginView = new LoginView();
                // nota: quizas el segundo parametro, la UserAccessRepository, no deba de ser null o no sea necesario
                Conexio conexio = new Conexio();
                LoginController loginController = new LoginController(loginView, conexio, null);
                loginView.registerController(loginController);
                loginView.setVisible(true);
            }
        });
    }
}
