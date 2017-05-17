package network;

import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Admin on 16/05/2017.
 */
public class ThreadSocketClient extends Thread implements UserAccessRepository{
    /*
    formato de las cadenas a mandar al server:
    Login: L-username o email#password
    Registro: R-username#pasword#email
     */
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private User user;
    private String response;
    private boolean aux;

    public ThreadSocketClient(User user){
        this.user = user;
    }

    @Override
    public void run() {
        // Averiguem quina direccio IP hem d'utilitzar
        InetAddress iAddress;
        try {
            iAddress = InetAddress.getLocalHost();
            String IP = iAddress.getHostAddress();

            //Socket sServidor = new Socket("172.20.31.90", 33333);
            Socket sServidor = new Socket (String.valueOf(IP), 33333);
            doStream = new DataOutputStream(sServidor.getOutputStream());
            diStream = new DataInputStream(sServidor.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (user.getEmail() == null){
            login(user);
        }else{
            register(user);
        }

        try {
            response = diStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        aux = tractaResposta();

        System.out.println("resposta que arrive al client: " + response);
    }

    @Override
    public boolean tractaResposta() {
        System.out.println("response: " + response);

        if (response.equals("OK")){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public void login(User user) {
        try {
            doStream.writeUTF("L-" + user.getUserName() + "#" + user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(User user) {
        try {
            doStream.writeUTF("R-" + user.getUserName() + "#" + user.getEmail() + "#" + user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public boolean checkUserName(String userName) {
        return false;
    }

    @Override
    public boolean checkEmail(String userEmail) {
        return false;
    }

    @Override
    public String response() {
        if (response.equals("OK")){
            return "OK";
        }else{
            String[] aux = response.split("-");
            return aux[1];
        }
    }

    public String getResponse() {
        return response;
    }

    public boolean isAux() {
        return aux;
    }
}
