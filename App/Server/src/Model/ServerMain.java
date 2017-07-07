package Model;

import javax.swing.*;
import Controller.ServerController;
import Network.ThreadSocketServer;
import View.*;

/**
 * Created by Admin on 20/03/2017.
 */
public class ServerMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                //creem la vista
                ServerAdminView serverAdminView = new ServerAdminView();
                //creem el model
                GestioDades gestioDades = new GestioDades();
                //creem el controlador
                ServerController sController = new ServerController(serverAdminView, gestioDades);
                //creem el socket
                ThreadSocketServer threadSocketServer = new ThreadSocketServer(gestioDades, sController);
                threadSocketServer.start();
                //printa el numero segons si es pot fer un adduser: 1:ok 2:usuari existeix 3:mail existeix 4:both
                serverAdminView.controladorBoto(sController);
                serverAdminView.setVisible(true);
                //añadimos el servidor al thread
                //threadSocketServer.controller(sController);
            }
        });
    }
}
