package Model;

import javax.swing.*;
import Controller.ServerController;
import Network.ThreadSocketServer;
import View.*;

/**
 * Created by Admin on 20/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                //creem la vista
                View view = new View();
                //creem el socket
                ThreadSocketServer threadSocketServer = new ThreadSocketServer();
                //creem el model
                GestioDades gestioDades = new GestioDades();

                //creem el controlador
                ServerController sController = new ServerController(view, gestioDades, threadSocketServer);
                //printa el numero segons si es pot fer un adduser: 1:ok 2:usuari existeix 3:mail existeix 4:both
                view.controladorBoto(sController);
                view.setVisible(true);

            }
        });
    }
}
