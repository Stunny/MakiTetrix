package network;

import com.google.gson.Gson;

import javax.security.auth.login.Configuration;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by angel on 17/07/2017.
 */
public class ThreadClientPasiu extends Thread{
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sClient;
    private String currentUser;

    public ThreadClientPasiu(String currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void run(){
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(33334);

            while (true) {
                //esperem a la conexio d'algun usuari dins d'un bucle infinit. A cada usuari li crearem un nou servidor dedicat
                sClient = serverSocket.accept();
                startComunication();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startComunication() throws IOException {
        doStream = new DataOutputStream(sClient.getOutputStream());
        diStream = new DataInputStream(sClient.getInputStream());
        String request = diStream.readUTF();
        readRequest(request);
    }

    private void readRequest(String request) {
        switch (request){
            case "TIME":
                try {
                    String selectedUser = diStream.readUTF();
                    if (selectedUser.equals(currentUser)){
                        while(true){
                            //TODO: MANDAR EL TIEMPO CORRESPONDIENTE A LA PARTIDA DEL USUARIO
                            //doStream.writeInt();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        System.out.println("request: " + request);
    }
}
