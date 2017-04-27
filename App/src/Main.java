import Vista.GameView;

import javax.swing.*;

/*
 * Clase principal de la aplicacion cliente
 * Created by jorti on 30/03/2017.
 */

public class Main {
    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable (){

            @Override
            public void run(){
               // LoginView loginView = new LoginView();
               // nota: quizas el segundo parametro, la UserAccessRepository, no deba de ser null
               // LoginController loginController = new LoginController(loginView, null);
                //loginView.registerController(loginController);
                //loginView.setVisible(true);
                GameView game = new GameView();
                game.setVisible(true);
            }

        });
    }
}
