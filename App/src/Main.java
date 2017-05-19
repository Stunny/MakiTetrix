import Vista.GameView;
import controller.GameController;
import model.Partida;

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
                /* Parte del Login*/
                //LoginView loginView = new LoginView();
                // nota: quizas el segundo parametro, la UserAccessRepository, no deba de ser null
                //LoginController loginController = new LoginController(loginView, null);
                //loginView.registerController(loginController);
                //loginView.setVisible(true);


                // Parte del Juego

                GameView game = new GameView();
                game.setVisible(true);
                Partida partida = new Partida();
                GameController asdf = new GameController(game,partida);
                asdf.startReplay("19.05 - 22.38.txt");
                //asdf.startGame();
                //asdf.playGame();

                // Menu principal
                /*MainMenuView mmv = new MainMenuView();
                MenuController mc = new MenuController(mmv);
                mmv.registerActions(mc);
                mmv.setVisible(true);
                */
            }

        });
    }
}
