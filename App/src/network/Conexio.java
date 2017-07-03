package network;

import model.User;
import model.utils.Encrypter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Admin on 16/05/2017.
 */
public class Conexio extends Thread {
    /*
    formato de las cadenas a mandar al server:
    Login: L-username o email#password
    Registro: R-username#pasword#email
     */
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sServidor;

    private String responseFlag;
    private String KOMessage;
    private boolean aux = true;

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

    public void startingLoginRegister(User user){
        connect();

        if (user.getEmail() == null){
            login(user);
        }else{
            register(user);
        }

        try {
            responseFlag = diStream.readUTF();
            if (responseFlag.equals("KO")){
                KOMessage = diStream.readUTF();
                aux = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //aux = tractaResposta();

        disconnect();
    }

    public void login(User user) {
        try {
            Encrypter encrypter = new Encrypter();
            if (user.getUserName().contains("@")){
                //el usuario ha logueado usando el email
                System.out.println("user login con email");
                String emailAux = encrypter.encrypt(user.getUserName());
                String passwordAux = encrypter.encrypt(user.getPassword());

                doStream.writeUTF("L");
                doStream.writeUTF(emailAux);
                doStream.writeUTF(passwordAux);
            }else{
                //el usuario ha logueado usando el nombre de usuario
                System.out.println("user login con userName");
                String nameAux = encrypter.encrypt(user.getUserName());
                String passwordAux = encrypter.encrypt(user.getPassword());

                doStream.writeUTF("L");
                doStream.writeUTF(nameAux);
                doStream.writeUTF(passwordAux);
            }
        } catch (Exception a){
            a.printStackTrace();
        }
    }

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


    public boolean tractaResposta() {
        System.out.println("response: " + responseFlag);

        if (responseFlag.equals("OK")){
            return true;
        }else{
            responseFlag = responseFlag.substring(3);
            return false;
        }
    }

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


    /**
     * Set the current user's status to "Offline"
     */
    public void setDisconnected(User currentUser) {
        connect();

        try {
            doStream.writeUTF("STATUS");
            doStream.writeUTF(currentUser.getUserName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }


    public void getOnlineUsers() {
        connect();

        try {
            doStream.writeUTF("LIVE_USERS");
            doStream.writeUTF("PENDIENTE DE IMPLEMENTACION");
        } catch (IOException e) {
            e.printStackTrace();
        }

        disconnect();
    }

    public boolean logout() {
        return false;
    }

    public boolean checkUserName(String userName) {
        return false;
    }

    public boolean checkEmail(String userEmail) {
        return false;
    }

    private String response() {
        if (responseFlag.equals("OK")){
            return "OK";
        }else{
            String[] aux = responseFlag.split("-");
            return aux[1];
        }
    }

    public String getResponse() {
        return KOMessage;
    }

    public boolean isAux() {
        return aux;
    }

}
