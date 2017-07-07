package Network;

import Controller.ServerController;
import Model.GestioDades;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Admin on 21/03/2017.
 */
public class ThreadSocketServer extends Thread{
    private GestioDades gestioDades;
    private ServerController sController;

    public ThreadSocketServer(GestioDades gestioDades, ServerController sController){
        this.gestioDades = gestioDades;
        this.sController = sController;
    }

    @Override
    public void run(){
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(33333);
            while (true){
                //esperem a la conexio d'algun usuari dins d'un bucle infinit. A cada usuari li crearem un nou servidor dedicat
                Socket sClient = serverSocket.accept();
                System.out.println("User connected!");
                generaNouServidorDedicat(sClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generaNouServidorDedicat(Socket sClient){
        System.out.println("entro a fer un nou servidor dedicat");
        System.out.println(sController);
        ThreadServidorDedicat tsd = new ThreadServidorDedicat(sClient, gestioDades, sController);
        tsd.start();
    }

    public void controller(ServerController sController) {
        this.sController = sController;
        System.out.println("Valor de sController: " + sController.equals(null));
        System.out.println("Valor de sController: " + this.sController);
    }
}