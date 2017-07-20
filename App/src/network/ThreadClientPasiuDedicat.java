package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by angel on 17/07/2017.
 */
public class ThreadClientPasiuDedicat extends Thread{
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sClient;
    private String currentUser;
    private int time;

    public ThreadClientPasiuDedicat(String currentUser, int time, Socket socket) {
        this.currentUser = currentUser;
        this.time = time;
        this.sClient = socket;
    }

    @Override
    public void run(){
        try {
            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());
            String request = diStream.readUTF();
            readRequest(request);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRequest(String request) {
        System.out.println("request: " + request);
        switch (request){
            case "TIME":
                try {
                    String selectedUser = diStream.readUTF();
                    System.out.println("selectedUser: " + selectedUser);
                    System.out.println("current user: " + currentUser);
                    if (selectedUser.equals(currentUser) || selectedUser.equals("ALL")){
                        System.out.println("time " + time);
                        doStream.writeInt(time);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
