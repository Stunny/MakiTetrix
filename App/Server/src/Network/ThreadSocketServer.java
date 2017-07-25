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

    public static int PORT;

    private GestioDades gestioDades;
    private ServerController sController;

    public ThreadSocketServer(GestioDades gestioDades, ServerController sController){
        this.gestioDades = gestioDades;
        this.sController = sController;
    }

    @Override
    public synchronized void run(){
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true){
                //esperem a la conexio d'algun usuari dins d'un bucle infinit. A cada usuari li crearem un nou servidor dedicat
                Socket sClient = serverSocket.accept();
                generaNouServidorDedicat(sClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera un nuevo servidor dedicado por cada cliente que se quiera conectar
     * @param sClient Socket del cliente que se quiere conectar
     */
    private void generaNouServidorDedicat(Socket sClient){
        //System.out.println("entro a fer un nou servidor dedicat");
        ThreadServidorDedicat tsd = new ThreadServidorDedicat(sClient, gestioDades, sController);

        tsd.start();

    }

}