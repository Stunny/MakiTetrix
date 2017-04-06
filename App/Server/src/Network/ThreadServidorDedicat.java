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
    private boolean flag = true;

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
            while (flag){
                String aux = diStream.readUTF();
                tractaResposta(aux);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tractaResposta(String resposta){
        String [] aux = resposta.split("-");
        if(aux[0].equals("LU")){
            //la trama es el nom del usuari al fer el login

        }else if (aux[0].equals("LP")){
            //la trama es la contrasenya del usuari al fer el login

        }else if(aux[0].equals("RE")){
            //la trama es el email del usuari al registrar-se

        }else if (aux[0].equals("RU")){
            //la trama es el nom del usuari al registrar-se

        }else if (aux[0].equals("RP")){
            //la trama es la contrasenya del usuari al registrar-se

        }
    }
}