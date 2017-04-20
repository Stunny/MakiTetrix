package Network;

import Model.GestioDades;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Admin on 24/03/2017.
 */
public class ThreadServidorDedicat extends Thread {
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sClient;
    private GestioDades gestioDades = new GestioDades();

    public ThreadServidorDedicat(Socket sClient){
        this.sClient = sClient;
    }

    @Override
    public void run(){
        try {
            //creem les instancies necesaries per rebre i enviar dades
            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());
            //llegiexo les trames del client i les analitzo
            while (true){
                String aux = diStream.readUTF();
                tractaResposta(aux);
                enviaResposta();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tractaResposta(String resposta){
        String email;
        String usuari;
        String password;

        String [] aux = resposta.split("-");
        if(aux[0].equals("LU")){
            //la trama es el nom del usuari al fer el login
            //int mirant si existeix i si contra guay
            //0:ok, 1:usuari/mail no existeix 2:contra no

        }else if (aux[0].equals("LP")){
            //la trama es la contrasenya del usuari al fer el login

        }else if(aux[0].equals("RE")){
            email = aux[0];
            //la trama es el email del usuari al registrar-se
            //0:ok 3:usuari existeix 4:mail existeix 5:both
        }else if (aux[0].equals("RU")){
            usuari = aux[0];
            //la trama es el nom del usuari al registrar-se

        }else if (aux[0].equals("RP")){
            password = aux[0];
            //la trama es la contrasenya del usuari al registrar-se

        }
    }

    public void enviaResposta() throws IOException {
        int error = gestioDades.gestionaResposta();
        switch(error){
            case 0:
                doStream.writeUTF("OK");
                break;
            case 1:
                doStream.writeUTF("KO-Usuari/email no existeix");
                break;
            case 2:
                doStream.writeUTF("KO-La contrasenya no es correcta");
                break;
            case 3:
                doStream.writeUTF("KO-L'usuari ja existeix");
                break;
            case 4:
                doStream.writeUTF("KO-El email ja existeix");
                break;
            case 5:
                doStream.writeUTF("KO-El email i l'usuari ja existeixen");
                break;
        }
    }
}