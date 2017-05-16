package network;

import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Admin on 16/05/2017.
 */
public class ThreadSocketClient extends Thread implements UserAccessRepository{
    /*
    formato de las cadenas a mandar al server:
    Login: L-username o email--password
    Registro: R-username--pasword--email
     */
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private User user;

    public ThreadSocketClient(User user){
        this.user = user;
    }

    @Override
    public void run() {
        try {
            Socket sServidor = new Socket("172.20.31.90", 33333);
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
    }

    @Override
    public boolean login(User user) {
        try {
            doStream.writeUTF("L-" + user.getUserName() + "--" + user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("aqui me quedat. enviar al server la info del login");
        return true;
    }

    @Override
    public boolean register(User user) {
        System.out.println("aqui me quedat. enviar al server la info del registre");
        return true;
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
}
