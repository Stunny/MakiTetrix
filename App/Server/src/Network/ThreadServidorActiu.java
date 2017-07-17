package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

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
    public void getCurrentTime(String selectedUser) {
        connect();

        try {
            doStream.writeUTF("TIME");
            doStream.writeUTF(selectedUser);
            while (true){
                int time = diStream.readInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }
}
