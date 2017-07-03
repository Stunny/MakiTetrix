package Network;

import Model.GestioDades;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Admin on 21/03/2017.
 */
public class ThreadSocketServer extends Thread{
    private ServerSocket serverSocket;
    private ThreadServidorDedicat tsd;
    private GestioDades gestioDades;

    public ThreadSocketServer(GestioDades gestioDades){
        this.gestioDades = gestioDades;
    }

    @Override
    public void run(){
        try {
            //creem el nostre socket
            serverSocket = new ServerSocket(33333);
            while (true){
                //esperem a la conexio d'algun usuari dins d'un bucle infinit. A cada usuari li crearem un nou servidor dedicat
                Socket sClient = serverSocket.accept();
                generaNouServidorDedicat(sClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generaNouServidorDedicat(Socket sClient){
        tsd = new ThreadServidorDedicat(sClient, gestioDades);
        tsd.start();
    }
}