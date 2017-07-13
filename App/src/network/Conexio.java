package network;

import model.User;
import model.utils.Encrypter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Admin on 16/05/2017.
 */
public class Conexio extends Thread {

    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sServidor;

    private String responseFlag;
    private String KOMessage;
    private boolean ResponseSuccess = true;

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
            sServidor = new Socket (String.valueOf(IP), 33333);
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

    /**
     * Manages starting client user request (login or register) for a server communication
     * @param user User that desires to login or register
     */
    public void startingLoginRegister(User user){
        connect();

        if (user.getEmail() == null){
            login(user);
        }else{
            register(user);
        }

        disconnect();
    }

    /**
     * Comunicates with server when a login request is done
     * @param user User to login
     */
    public void login(User user) {
        try {
            Encrypter encrypter = new Encrypter();
            //el usuario ha logueado usando el email
            if (user.getUserName().contains("@")){
                String emailAux = encrypter.encrypt(user.getUserName());
                String passwordAux = encrypter.encrypt(user.getPassword());

                doStream.writeUTF("L");
                doStream.writeUTF(emailAux);
                doStream.writeUTF(passwordAux);

            }else{
                //el usuario ha logueado usando el nombre de usuario
                String nameAux = encrypter.encrypt(user.getUserName());
                String passwordAux = encrypter.encrypt(user.getPassword());

                doStream.writeUTF("L");
                doStream.writeUTF(nameAux);
                doStream.writeUTF(passwordAux);
            }
            tractaResposta(diStream.readUTF());
        } catch (Exception a){
            a.printStackTrace();
        }
    }

    /**
     * Comunicates with server when a register request is done
     * @param user User to register
     */
    public void register(User user) {
        try {
            Encrypter encrypter = new Encrypter();
            String nameAux = encrypter.encrypt(user.getUserName());
            String emailAux = encrypter.encrypt(user.getEmail());
            String passwordAux = encrypter.encrypt(user.getPassword());

            doStream.writeUTF("R");
            doStream.writeUTF(nameAux);
            doStream.writeUTF(emailAux);
            doStream.writeUTF(passwordAux);
        } catch (Exception a){
            a.printStackTrace();
        }
    }

    /**
     * Sets the specified user's status to "Offline"
     */
    public void disconnectUser(User user) {
        connect();

        try {
            doStream.writeUTF("DISCONNECT");
            doStream.writeUTF(user.getUserName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }


    /**
     * Checks server's answer to client requests
     * @param s String indicating the server response
     * @throws IOException
     */
    public void tractaResposta(String s) throws IOException {
        if (s.equals("OK")){
            ResponseSuccess = true;
        }else{
            KOMessage = diStream.readUTF();
            ResponseSuccess = false;
        }
    }

    /**
     * Sends the desired user to espectate
     * @param userNameToEspectate User to spectate name
     */
    public void sendUserToEspectate(String userNameToEspectate) {
        connect();
        try {
            doStream.writeUTF("ESPECTATE");
            doStream.writeUTF(userNameToEspectate);
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }

    /**
     * Sends the desired user
     * @param userNameReplays
     */
    public void sendDesiredUserReplay(String userNameReplays) {
        connect();
        try {
            doStream.writeUTF("ReplaySelect");
            doStream.writeUTF(userNameReplays);
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }

    public ArrayList<String> getOnlineUsers() {
        connect();
        ArrayList<String> onlineUsers = new ArrayList<>();
        try {
            doStream.writeUTF("LIVE_USERS");

            int size = diStream.readInt();
            for (int i = 0; i < size; i++){
                onlineUsers.add(diStream.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
        return onlineUsers;
    }

    public String[] getReplays(String userName) {
        connect();

        try {
            doStream.writeUTF("REPLAY_LIST");
            doStream.writeUTF(userName);
/*            String data = null;
            int replay_number = diStream.readInt();

            for (int i = 0; i < replay_number; i++){
                //TODO: ARREGLAR LA COMUNICACION CLIENTE SERVIDOR EN ESTE PUNTO. EL CLIENTE NO LEE BIEN LOS DATOS
                System.out.println("reading data");
                data = diStream.readUTF();
                System.out.println(data);

            }
  */
String data = diStream.readUTF();

            System.out.println("data: " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();

        //retornar lo
        return null;
    }

    public String getResponse() {
        return KOMessage;
    }

    public boolean isResponseSuccess() {
        return ResponseSuccess;
    }

}
