package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by angel on 19/07/2017.
 */
public class ThreadClientPasiu extends Thread {
    private String userName;
    private int time;
    private static int PORT =  33334;

    public ThreadClientPasiu(String userName, int time){
        this.userName = userName;
        this.time = time;
    }

    @Override
    public void run() {
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

    private void generaNouServidorDedicat(Socket socket){
        ThreadClientPasiuDedicat tcpd = new ThreadClientPasiuDedicat(userName, time, socket);
        tcpd.start();
    }
}
