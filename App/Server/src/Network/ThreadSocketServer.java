package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Admin on 21/03/2017.
 */
public class ThreadSocketServer extends Thread{
    private ServerSocket serverSocket;
    private ThreadServidorDedicat tsd;

    @Override
    public void run(){
        try {
            //creem el nostre socket
            serverSocket = new ServerSocket(33333);
            while (true){
                //esperem a la conexio d'algun usuari dins d'un bucle infinit. A cada usuari li crearem un nou servidor dedicat
                Socket sClient = serverSocket.accept();
                if (sClient.isConnected()) {
                    generaNouServidorDedicat(sClient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generaNouServidorDedicat(Socket sClient){
        tsd = new ThreadServidorDedicat(sClient);
        tsd.start();
    }
}