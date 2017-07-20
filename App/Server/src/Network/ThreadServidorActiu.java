package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by angel on 17/07/2017.
 */
public class ThreadServidorActiu extends Thread {
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sServidor;

    /**
     * Sets necesary functionalities for client-server communication
     */
    private void connect(){
        // Averiguem quina direccio IP hem d'utilitzar
        InetAddress iAddress;
        try {
            iAddress = InetAddress.getLocalHost();
            String IP = iAddress.getHostAddress();

            //Socket sServidor = new Socket("172.20.31.90", 33333);
            sServidor = new Socket(String.valueOf(IP), 33334);
            doStream = new DataOutputStream(sServidor.getOutputStream());
            diStream = new DataInputStream(sServidor.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes necesary functionalities for client-server communication
     */
    private void disconnect()	{
        try {
            doStream.close();
        }	catch (IOException	|	NullPointerException	e)	{
            System.err.println("[DISCONNECT]	" + e.getMessage());
        }
        try {
            diStream.close();
        }	catch (IOException	|	NullPointerException	e)	{
            System.err.println("[DISCONNECT]	" + e.getMessage());
        }
        try {
            sServidor.close();
        }	catch (IOException	|	NullPointerException	e)	{
            System.err.println("[DISCONNECT]	" + e.getMessage());
        }
    }

/*
    public void test() {

        connect();

        try {
            doStream.writeUTF("test");
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }
*/

    /**
     * Rrtrieves selected user's current time
     * @param selectedUser selected user
     */
    public void getCurrentTime(String selectedUser) {
        connect();

        System.out.println("get current time from " + selectedUser);
        try {
            doStream.writeUTF("TIME");
            doStream.writeUTF(selectedUser);
            String time = diStream.readUTF();
            System.out.println("time: " + time);
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }

    /**
     * Retrieves a list of all GAMING users' time
     * @param users
     */
    public ArrayList<Integer> getTimeList(ArrayList<String> users) {
        connect();
        ArrayList<Integer> times = null;

        try {
            times = new ArrayList<>();
            for (int  i = 0; i < users.size(); i++){
                doStream.writeUTF("TIME");
                doStream.writeUTF(users.get(i));
                int time = diStream.readInt();
                times.add(time);
            }

            return times;
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
        return  times;
    }
}
