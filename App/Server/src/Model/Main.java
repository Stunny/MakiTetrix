package Model;

import javax.swing.*;
import Controller.ServerController;
import Network.ThreadSocket;
import View.*;

/**
 * Created by Admin on 20/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                /* Averiguem quina direccio IP hem d'utilitzar
                InetAddress iAddress = null;
                try {
                    iAddress = InetAddress.getLocalHost();
                    String IP = iAddress.getHostAddress();
                    System.out.println("Server IP address : " + IP);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                */

                //creem la vista
                View view = new View();
                //creem el socket
                ThreadSocket threadSocket = new ThreadSocket();
                //creem el model
                GestioDades gestioDades = new GestioDades();

                //creem el controlador
                ServerController sController = new ServerController(view, gestioDades,  threadSocket);
                //MouseController mController = new MouseController();

                view.controladorBoto(sController);
                //view.controladorMouse(mController);
                view.setVisible(true);
                gestioDades.conectar();
            }
        });
    }
}
